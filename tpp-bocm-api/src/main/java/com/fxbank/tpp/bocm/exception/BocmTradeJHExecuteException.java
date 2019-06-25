package com.fxbank.tpp.bocm.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: BocmTradeJHExecuteException 
* @Description: 模拟交行返回异常信息
* @author YePuLiang
* @date 2019年6月18日 下午1:40:32 
*  
*/
public class BocmTradeJHExecuteException extends SysTradeExecuteException {
		
		private static final long serialVersionUID = 1767308581692333362L;		
		
		public final static String JH_6203 = "JH6203";

		public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

			private static final long serialVersionUID = -3713497985727043375L;

			{
				put(JH_6203, "交易超时");

				
			}
		};

		public BocmTradeJHExecuteException(String rspCode) {
			super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
		}

		public BocmTradeJHExecuteException(String rspCode, String rspMsg) {
			super(rspCode, rspMsg);
		}

}
