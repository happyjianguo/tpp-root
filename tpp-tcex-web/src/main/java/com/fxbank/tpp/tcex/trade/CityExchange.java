package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
/**
 * 商行通兑业务
 * @author liye
 *
 */
public class CityExchange implements TradeExecutionStrategy {

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		String lsh = ""; //渠道流水号
		
		//插入流水表
		boolean b = true;
		if(b) {
			//通知村镇记账
			String rst = "";
			
			//更新流水表村镇记账状态
			
			if(rst.equals("success")) {
				//核心记账：将金额从头寸中划至指定账户
				rst = "";
				
				//更新流水表核心记账状态
				if(rst.equals("success")) {
					//结束
				}else  {
					if(rst.equals("timeout")) {
						//多次查询核心，确认是否是延迟原因
						b = true;
					}else {
						//核心记账失败
						b = false;
					}
					
					if(!b) {
						//村镇冲正

						//更新流水表村镇记账状态
					}
						
					
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
