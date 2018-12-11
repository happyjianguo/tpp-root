package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
/**
 * 商行通存业务
 * @author liye
 *
 */
public class CityDeposit implements TradeExecutionStrategy {

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		String lsh = ""; //渠道流水号
		
		//插入流水表
		boolean b = true;
		
		if(b) {
			//核心记账：由商行核心将客户存入金额转至头寸。
			
			
			//更新流水表核心记账状态
			b=true;
			
			if(b) {
				//通知村镇进行记账
				String rst = "";
				
				//更新流水表村镇记账状态
				
				
				if(rst.equals("success")) {
					//结束
				}else if(rst.equalsIgnoreCase("timeout")) {
					//发送存款确认
					
					//更新流水表村镇存款确认状态
				}else {
					//核心撤销：冲正

					//更新流水表核心记账冲正状态
				}
				
			}else {
				throw new SysTradeExecuteException("1111");
			}
			
		}else {
			throw new SysTradeExecuteException("1111");
		}
		
		
		
		return null;
	}

}
