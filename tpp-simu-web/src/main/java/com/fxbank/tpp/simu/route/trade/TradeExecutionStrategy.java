package com.fxbank.tpp.simu.route.trade;


import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;


/** 
* @ClassName: TradeExecutionStrategy 
* @Description: 交易策略接口
* @author ZhouYongwei
* @date 2018年4月3日 下午3:41:42 
*  
*/
public interface TradeExecutionStrategy {

	public ESB_BASE execute(String txCode, ESB_BASE dto) throws SysTradeExecuteException;

}
