package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10001;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10001;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30033000203;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30033000203;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104.Fee;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/** 
* @ClassName: WD_FxMag 
* @Description: 交行向本行发起磁条卡通兑记账请求
* @author Duzhenduo
* @date 2019年4月18日 上午10:59:30 
*  
*/
@Service("REQ_10001")
public class WD_FxMag implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(WD_FxMag.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10001 req = (REQ_10001) dto;
		//1.插入流水表
		initRecord(req);		
		//磁条卡二磁道校验
		try {
			validateMag(req);
		} catch (SysTradeExecuteException e) {
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10007);
			throw e2;
		}
		//2.核心记账
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		try {
			esbRep_30011000104 = hostCharge(req);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();
		} catch (SysTradeExecuteException e) {
			updateHostRecord(req, "", "", "2", e.getRspCode(), e.getRspMsg());
			myLog.error(logger, "交行代理我行账户存款（磁条卡），本行核心记账失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004);
			throw e2;
		}
		//3.更新流水表核心记账状态
		updateHostRecord(req, hostDate, hostTraceno, "1", retCode, retMsg);
		myLog.info(logger, "交行代理我行账户存款（磁条卡），本行核心记账成功，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
		REP_10001 rep = new REP_10001();
		rep.setoTxnAmt(req.getTxnAmt());
		List<Fee> feeList = esbRep_30011000104.getRepBody().getFeeDetail();
		//JHF1-异地手续费JHF2-代理手续费
		BigDecimal fee = new BigDecimal(0);
		for(Fee temp:feeList) {
			if("JHF2".equals(temp.getFeeType())) {
				fee = new BigDecimal(temp.getFeeAmt());
			}
		}
		rep.setFee(fee);
		rep.setActBal(new BigDecimal(esbRep_30011000104.getRepBody().getAvailBal()));
		return rep;
	}
	/** 
	* @Title: hostCharge 
	* @Description: 本行核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000104 hostCharge(REQ_10001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqDto.getrActNo());
		reqBody_30011000104.setAcctName(reqDto.getRecNam());
		reqBody_30011000104.setTranType("JH02");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqDto.getTxnAmt().toString());
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setOthBaseAcctNo(reqDto.getpActNo());
		reqBody_30011000104.setOthBaseAcctName(reqDto.getPayNam());
		reqBody_30011000104.setChannelType("");
		reqBody_30011000104.setSendBankCode(reqDto.getHeader().getsBnkNo());
		reqBody_30011000104.setBankCode(reqDto.getRecBnk());
		reqBody_30011000104.setOthBankCode(reqDto.getHeader().getsBnkNo());
		reqBody_30011000104.setSettlementDate(reqDto.getHeader().gettTxnDat().toString());
		reqBody_30011000104.setCollateFlag("Y");

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	private void initRecord(REQ_10001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmRcvTraceInitModel record = new BocmRcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getHeader().getsBnkNo());
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("0");
		record.setTxAmt(reqDto.getTxnAmt().toString());
		//现转标志；0现金、1转账
		record.setTxInd(reqDto.getTxnMod());
		record.setHostState("0");
		record.setBocmState("0");
		record.setPayerAcno(reqDto.getpActNo());
		record.setPayerName(reqDto.getPayNam());
		record.setPayeeAcno(reqDto.getrActNo());
		record.setPayeeName(reqDto.getRecNam());
		record.setPrint("0");
		record.setCheckFlag("1");
		bocmRcvTraceService.rcvTraceInit(record);
	}
	private BocmRcvTraceUpdModel updateHostRecord(REQ_10001 reqDto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		if(!"".equals(hostDate)) {
		record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmRcvTraceService.rcvTraceUpd(record);
		return record;
	}
	private ESB_REP_30033000203 validateMag(REQ_10001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30033000203 esbReq_30033000203 = new ESB_REQ_30033000203(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30033000203.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30033000203.setReqSysHead(reqSysHead);

		ESB_REQ_30033000203.REQ_BODY reqBody_30033000203 = esbReq_30033000203.getReqBody();
		reqBody_30033000203.setCardNo(reqDto.getrActNo());
		reqBody_30033000203.setScdTrk(reqDto.getSecMag());
		reqBody_30033000203.setCurDate(reqDto.getHeader().gettTxnDat().toString());

		ESB_REP_30033000203 esbRep_30033000203 = forwardToESBService.sendToESB(esbReq_30033000203, reqBody_30033000203,
				ESB_REP_30033000203.class);
		return esbRep_30033000203;
	}
}