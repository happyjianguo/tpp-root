package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
/**
 * 村镇存款确认业务
 * @author liye
 *
 */
public class TownDepositConfirm implements TradeExecutionStrategy {

	//流程待确认
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		String lsh = ""; //渠道流水号
		
		//插入流水表
		boolean b = true;
		if(b) {
			//自查流水表
			String rst = "";
			
			if(rst.equals("success")){
				
			}else{
				//调用核心接口确认该笔流水是否入账成功
			}
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		return null;
	}

}
