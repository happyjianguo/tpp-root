package com.fxbank.tpp.esb.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

public class ESBTradeExecuteException extends SysTradeExecuteException {

	
	private static final long serialVersionUID = -4396151878176541080L;

	public final static String ESB_E_000001 = "ESB_E_000001";
	
	public final static Map<String, String> ESBERRCODECONV = new HashMap<String, String>() {
		
		private static final long serialVersionUID = -6069828754523545158L;

		{
			put(ESB_E_000001, "调用加密平台加密失败");
			
			
		}
	};

	public ESBTradeExecuteException(String rspCode) {
		super(rspCode, ESBERRCODECONV.get(rspCode) == null ? "响应码未定义" : ESBERRCODECONV.get(rspCode));
	}

	public ESBTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
