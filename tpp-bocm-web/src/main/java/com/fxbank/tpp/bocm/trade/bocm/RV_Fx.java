/**   
* @Title: RV_Fx.java 
* @Package com.fxbank.tpp.bocm.trade.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 上午8:09:42 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.bocm;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.dto.bocm.REP_10009;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10000;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10009;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: RV_Fx 
* @Description: 交行向本行发起抹账交易
* @author YePuLiang
* @date 2019年5月6日 上午8:09:42 
*  
*/
@Service("REQ_10009")
public class RV_Fx implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService bocmRcvTraceService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10009 req = (REQ_10009) dto;
		REP_10009 rep = new REP_10009();

	
		int platDate = req.getSysDate();
		int platTraceno = req.getSysTraceno();
		Integer sysDate = publicService.getSysDate("CIP");
		if(sysDate.compareTo(platDate)!=0) {
			myLog.error(logger, "不能隔日冲正，渠道日期" + platDate + 
					"渠道流水号" + platTraceno);
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10011);
			throw e;
		}
		
		String hostReversalCode = null;
		String hostReversalMsg = null;
		String hostDate = null;
		String hostTraceno = null;
		try {
			BocmRcvTraceQueryModel model = queryRcvTrace(req);
			if(model==null){
				myLog.error(logger, "交行向本行发起抹账交易，本行渠道无交易记录，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_11007);
				throw e2;
			}
			//2.核心冲正
			ESB_REP_30014000101 esbRep_30014000101 = null;
			esbRep_30014000101 = hostReversal(req,model);	
			hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
			hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
			hostDate = esbRep_30014000101.getRepSysHead().getTranDate();
			hostTraceno = esbRep_30014000101.getRepSysHead().getReference();
			//插入流水表
			initRecord(req, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg);
		} catch (SysTradeExecuteException e) {
			if("CIP_E_000004".equals(e.getRspCode())) {
				//FX6203
				updateHostRecord(req, "", "", "6", e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行向本行发起抹账交易，本行核心抹账超时，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_16203,e.getRspMsg()+"(抹账超时)");
				throw e2;
			}else{
				//交易失败
				updateHostRecord(req, "", "", "5", e.getRspCode(), e.getRspMsg());
				myLog.error(logger, "交行向本行发起抹账交易，本行核心抹账失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10004,e.getRspMsg()+"(抹账失败)");
				throw e2;
			}
		}
		return rep;
	}
	
	private void initRecord(REQ_10009 reqDto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmRcvTraceInitModel record = new BocmRcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getSbnkNo());
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("0");
		record.setTxAmt(reqDto.getTxnAmt().toString());
		//现转标志；0现金、1转账
		record.setTxInd(reqDto.getTxnMod());
		record.setHostState(hostState);
		if(hostDate!=null){
			record.setHostDate(Integer.parseInt(hostDate));
		}		
		record.setHostTraceno(hostTraceno);

		record.setPayerAcno(reqDto.getPactNo());
		record.setPayerName(reqDto.getPayNam());
		record.setPayeeAcno(reqDto.getRactNo());
		record.setPayeeName(reqDto.getRecNam());
		record.setPrint("0");
		record.setCheckFlag("1");
		
		record.setBocmState("0");
		//交行流水号
		record.setBocmTraceNo(reqDto.getSlogNo());		
		record.setBocmDate(reqDto.getTtxnDat());
		record.setBocmTime(reqDto.getTtxnTim());
		//发起行行号
		record.setBocmBranch(reqDto.getSbnkNo());
		//交易码
		record.setTxCode(reqDto.getTtxnCd());
		bocmRcvTraceService.rcvTraceInit(record);
	}
	
	private BocmRcvTraceUpdModel updateHostRecord(REQ_10009 reqDto, String hostDate, String hostTraceno,
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
	
	/** 
	* @Title: hostReversal 
	* @Description: 本行核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	public ESB_REP_30014000101 hostReversal(DataTransObject dto,BocmRcvTraceQueryModel model)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10009 reqDto = (REQ_10009)dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		
		reqSysHead.setSourceType(dto.getSourceType());
		
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+model.getPlatDate()+String.format("%08d",model.getPlatTrace()));
		reqBody_30014000101.setReversalReason("交行发起抹账请求");
		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}
	
	private BocmRcvTraceQueryModel queryRcvTrace(REQ_10009 req) throws SysTradeExecuteException{
		MyLog myLog = logPool.get();
		BocmRcvTraceQueryModel model = null;
		int townDate = req.getTtxnDat();
		String townTraceno = req.getSlogNo();
		model = bocmRcvTraceService.getConfirmTrace(myLog, townDate, townTraceno);
		return model;
	}
	


}
