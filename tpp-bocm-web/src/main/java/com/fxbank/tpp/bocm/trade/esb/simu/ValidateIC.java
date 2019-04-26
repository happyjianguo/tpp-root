/**   
* @Title: ValidateIC.java 
* @Package com.fxbank.tpp.bocm.trade.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月24日 上午8:20:22 
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
import com.fxbank.tpp.bocm.dto.esb.REP_30033000202;
import com.fxbank.tpp.bocm.dto.esb.REQ_30033000202;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.trade.bocm.DP_FxICC;
import com.fxbank.tpp.esb.service.IForwardToESBService;

/** 
* @ClassName: ValidateIC 
* @Description: IC卡校验模拟验证 
* @author YePuLiang
* @date 2019年4月24日 上午8:20:22 
*  
*/
@Service("REQ_30033000202")
public class ValidateIC implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(DP_FxICC.class);

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
		logger.info(" IC卡校验模拟验证 ");
		MyLog myLog = logPool.get();
		try {
			REQ_30033000202 req = (REQ_30033000202) dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		REP_30033000202 rep = new REP_30033000202();
		return rep;
	}

}
