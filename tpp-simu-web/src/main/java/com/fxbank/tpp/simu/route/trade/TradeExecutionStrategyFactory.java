package com.fxbank.tpp.simu.route.trade;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


/** 
* @ClassName: TradeExecutionStrategyFactory 
* @Description: 交易策略工厂 
* @author ZhouYongwei
* @date 2018年4月2日 下午5:16:23 
*  
*/
@Service("tradeExecutionFactory")
public class TradeExecutionStrategyFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public TradeExecutionStrategy getTradeExecution(String txCode)
			throws SysTradeExecuteException {

		TradeExecutionStrategy tradeExecution = (TradeExecutionStrategy) this.applicationContext.getBean(txCode);
		if (tradeExecution == null) {
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
		}
		return tradeExecution;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
