/**   
* @Title: QrBankNo.java 
* @Package com.fxbank.tpp.bocm.trade.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月28日 上午8:48:36 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb.simu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30011000104;
import com.fxbank.tpp.bocm.dto.esb.REP_30043003001;
import com.fxbank.tpp.bocm.dto.esb.REQ_30043003001;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: QrBankNo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年4月28日 上午8:48:36 
*  
*/
@Service("REQ_30043003001")
public class QrBankNo implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(QrBankNo.class);

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
		logger.info(" 模拟人行行号获取 ");
		MyLog myLog = logPool.get();
		try {
			REQ_30043003001 req = (REQ_30043003001) dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		REP_30043003001 rep = new REP_30043003001();
		REP_30043003001.REP_BODY repBody = rep.getRepBody();
		repBody.setBankNumber("313221099020");
		repBody.setBnkNmT("阜新银行沈阳分行营业部");
		repBody.setLqtnBnkNmT1("阜新银行结算中心");
		repBody.setSettlementBankNo("313229000008");

		
//		if(repBody.getAvailBal()==null){
			//模拟交易执行异常
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000007);
//			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_000000);
//			throw e;
//		}
		
		
		return rep;
	}
}

