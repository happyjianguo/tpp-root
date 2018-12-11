package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
/**
 * 村镇冲正业务
 * @author liye
 *
 */
public class TownReversal implements TradeExecutionStrategy {

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		String lsh = ""; //渠道流水号
		
		//插入流水表
		boolean b = true;
		if(b) {
			//调用核心接口进行冲正
			
			//更新流水表核心记账状态
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		return null;
	}

}
