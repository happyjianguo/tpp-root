package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.tcex.dto.esb.REP_TR003;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR003;
import com.fxbank.tpp.tcex.dto.esb.REQ_30043002701;
/**
 * 村镇存款确认业务
 * @author liye
 *
 */
public class TownDepositConfirm implements TradeExecutionStrategy {

	//流程待确认
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		REQ_TR003 reqDto = (REQ_TR003) dto;
		
		REP_TR003 repDto = new REP_TR003();
		
		
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
