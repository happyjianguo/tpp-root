package com.fxbank.tpp.simu.route;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.tpp.simu.route.trade.TradeExecutionContext;
import com.fxbank.tpp.simu.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.simu.route.trade.TradeExecutionStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/** 
* @ClassName: TradeMainFlowHelper 
* @Description: 交易执行封装类
* @author ZhouYongwei
* @date 2018年4月2日 下午5:14:22 
*  
*/
@Service("tradeMainFlowHelper")
public class TradeMainFlowHelper {

	private static Logger logger = LoggerFactory.getLogger(TradeMainFlowHelper.class);

	@Resource(name = "tradeExecutionFactory")
	private TradeExecutionStrategyFactory tradeExecutionFactory;

	public ESB_BASE invoke(String txCode, ESB_BASE reqDto) throws SysTradeExecuteException {

		TradeExecutionStrategy tradeExecutionStrategy = null;
		TradeExecutionContext tradeExecutionContext = null;
		try {
			tradeExecutionStrategy = tradeExecutionFactory.getTradeExecution(txCode);
			tradeExecutionContext = new TradeExecutionContext(tradeExecutionStrategy);
		} catch (Exception e) {
			logger.error("交易执行异常，获取交易执行类失败，调用通用交易");
			tradeExecutionStrategy = tradeExecutionFactory.getTradeExecution("ESB_REQ_COMMON");
			tradeExecutionContext = new TradeExecutionContext(tradeExecutionStrategy);
		}

		ESB_BASE repDto = null;
		try {
			// 执行策略容器中的策略
			repDto = tradeExecutionContext.execute(txCode, reqDto);
			return repDto;
		}catch (Throwable e) {
			logger.error("交易执行异常",e);
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			throw e1;
		} finally {
		}

	}

}
