package com.fxbank.tpp.tcex.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: TcexTradeExecuteException 
* @Description: 异常类及响应码、响应信息定义
* @author ZhouYongwei
* @date 2018年4月3日 下午3:40:26 
*  
*/
public class TcexTradeExecuteException extends SysTradeExecuteException {
	
	public final static String TCEX_E_10001 = "TCEX_E_10001";


	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {
		private static final long serialVersionUID = -8667135437237760216L;

		{
			put(TCEX_E_10001, "业务不支持");
			
		}
	};

	public TcexTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public TcexTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
