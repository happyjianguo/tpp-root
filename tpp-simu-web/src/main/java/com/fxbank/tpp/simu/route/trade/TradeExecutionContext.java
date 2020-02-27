package com.fxbank.tpp.simu.route.trade;


import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;


/** 
* @ClassName: TradeExecutionContext 
* @Description: 交易策略的容器
* @author ZhouYongwei
* @date 2018年4月3日 下午3:41:31 
*  
*/
public class TradeExecutionContext {

	private TradeExecutionStrategy tradeExecutionStrategy;
	
	public TradeExecutionContext(TradeExecutionStrategy tradeExecutionStrategy){
		this.tradeExecutionStrategy = tradeExecutionStrategy;
	}
	
	public ESB_BASE execute(String txCode, ESB_BASE dto) throws SysTradeExecuteException{
		return this.tradeExecutionStrategy.execute(txCode, dto);
	}
	
}
