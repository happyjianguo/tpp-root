package com.fxbank.tpp.bocm.trade.bocm;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10101;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10101;
import com.fxbank.tpp.bocm.dto.bocm.REQ_20000;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/**
 * @Description: 交行查询本行卡余额
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:16:10
 */
@Service("REQ_10101")
public class QR_FxBal implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(QR_FxBal.class);

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
		REQ_10101 req = (REQ_10101) dto;
		//1.插入流水表
		//initRecord(req);
		//2.调用ESB余额查询
		ESB_REP_30013000201 esbRep_30013000201 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;
		REP_10101 rep = new REP_10101();
		/**
		try {
			//调用核心查询余额
			esbRep_30013000201 = hostQuery(req);
			hostDate = esbRep_30013000201.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30013000201.getRepSysHead().getReference();
			retCode = esbRep_30013000201.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30013000201.getRepSysHead().getRet().get(0).getRetMsg();
		} catch (SysTradeExecuteException e) {
			updateHostRecord(req, "", "", "2", e.getRspCode(), e.getRspMsg());
			myLog.error(logger, "交行查询本行卡余额，本行核心查询失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10009);
			throw e2;
		}
		
		//3.更新流水表核心记账状态
		updateHostRecord(req, hostDate, hostTraceno, "1", retCode, retMsg);
		
		//4.设置返回报文		
		rep.setActNo(esbRep_30013000201.getRepBody().getBaseAcctNo());
		rep.setActBal(new BigDecimal(esbRep_30013000201.getRepBody().getBalance()));
		rep.setActNam(esbRep_30013000201.getRepBody().getAcctName());
		**/
		rep.setActNo("623166000009897");
		rep.setActNam("ZZZ");
		rep.setActBal(new Double(34.45));
		return rep;
	}
	
	private void initRecord(REQ_10101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmRcvTraceInitModel record = new BocmRcvTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqDto.getSourceType());
		record.setTxBranch(reqDto.getSbnkNo());
		record.setHostState("0");
		record.setPrint("0");
		record.setCheckFlag("0");
		
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
	private BocmRcvTraceUpdModel updateHostRecord(REQ_10101 reqDto, String hostDate, String hostTraceno,
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
	* @Title: hostQuery
	* @Description: 本行核心基本账户信息查询 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	private ESB_REP_30013000201 hostQuery(REQ_10101 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}

		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);

		ESB_REQ_30013000201.REQ_BODY reqBody_30013000201 = esbReq_30013000201.getReqBody();
		reqBody_30013000201.setBaseAcctNo(reqDto.getActNo());
		reqBody_30013000201.setProdType("");//产品类型
		reqBody_30013000201.setCcy(reqDto.getCcyCod());//币种
		reqBody_30013000201.setAcctSeqNo("");//账户 序号
		reqBody_30013000201.setPassword(reqDto.getPin());
		ESB_REP_30013000201 esbRep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201,
				ESB_REP_30013000201.class);
		return esbRep_30013000201;
	}
}