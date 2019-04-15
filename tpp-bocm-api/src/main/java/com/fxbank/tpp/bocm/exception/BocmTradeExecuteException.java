package com.fxbank.tpp.bocm.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/**
 * @Description: 异常类及响应码、响应信息定义
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:20:18
 */
public class BocmTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 1767308581692333362L;

	public final static String BOCM_E_10001 = "FX0001";

	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(BOCM_E_10001, "业务不支持");
			
		}
	};

	public BocmTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public BocmTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
