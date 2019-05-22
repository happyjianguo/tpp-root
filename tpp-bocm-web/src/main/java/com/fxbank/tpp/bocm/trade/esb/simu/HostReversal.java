/**   
* @Title: HostReversal.java 
* @Package com.fxbank.tpp.bocm.trade.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月29日 上午8:39:06 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.trade.esb.simu;

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
import com.fxbank.tpp.bocm.dto.esb.REP_30014000101;
import com.fxbank.tpp.bocm.dto.esb.REP_30043003001;
import com.fxbank.tpp.bocm.dto.esb.REQ_30014000101;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: HostReversal 
* @Description:模拟核心冲正
* @author YePuLiang
* @date 2019年4月29日 上午8:39:06 
*  
*/
@Service("REQ_30014000101")
public class HostReversal implements TradeExecutionStrategy {

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
			REQ_30014000101 req = (REQ_30014000101) dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		REP_30014000101 rep = new REP_30014000101();		
		
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