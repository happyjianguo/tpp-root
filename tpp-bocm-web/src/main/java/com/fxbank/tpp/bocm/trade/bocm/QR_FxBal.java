package com.fxbank.tpp.bocm.trade.bocm;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.tpp.esb.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10101;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10101;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000201;
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
		
		
//		String sbnkNo = req.getSbnkNo();//发起行行号
//		if(sbnkNo.substring(0, 3).equals("313")){
//			myLog.info(logger, "交易发起行为本行，启用挡板数据");
//			REP_10101 rep = new REP_10101();
//			rep.setActNo("623166000009897");
//			rep.setActNam("挡板用户");
//			rep.setActBal(new Double(100));
//			return rep;
//		}
		ESB_REP_30013000201 esbRep_30013000201 = null;
		REP_10101 rep = new REP_10101();
	
		try {
			//1.调用核心查询余额
			esbRep_30013000201 = hostQuery(req);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "交行查询本行卡余额，本行核心查询失败，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10009);
			throw e2;
		}
		
		String acctStatus = esbRep_30013000201.getRepBody().getAcctStatus();
		myLog.error(logger, "账户状态：" + acctStatus);
		//2.设置返回报文	
		if("A".equals(acctStatus)){
			//4.设置返回报文		
			rep.setActNo(req.getActNo());
			double actBal = Double.parseDouble(esbRep_30013000201.getRepBody().getBalance());
			rep.setActBal(NumberUtil.addPoint(actBal));
			rep.setActNam(esbRep_30013000201.getRepBody().getAcctName());
		}else{
			myLog.error(logger, "交行查询本行卡余额，卡状态异常，渠道日期" + req.getSysDate() + "渠道流水号" + req.getSysTraceno());
			BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10015);
			throw e2;
		}
		return rep;
		

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
			txBrno = jedis.get(COMMON_PREFIX + "TXBRNO");
			txTel = jedis.get(COMMON_PREFIX + "TXTEL");
		}

		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		/** Add by 叶浦亮 At 2019/12/3 15:48 For 不同渠道平台调用核心接口使用不一样的systemID */
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), reqDto.getSourceType(),reqDto.getSysDate(),reqDto.getSysTime(),reqDto.getSysTraceno())
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