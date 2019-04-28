package com.fxbank.tpp.mivs.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/**
 * @Description: 异常类及响应码、响应信息定义
 * @Author: 周勇沩
 * @Date: 2019-04-20 21:29:29
 */
public class MivsTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 1767308581692333362L;

	public final static String MIVS_E_10001 = "MIVS_0001";
	public final static String MIVS_E_10002 = "MIVS_0002";
	
	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(MIVS_E_10001, "同步等待人行响应超时");
			put(MIVS_E_10002, "人行拒绝受理");
			
		}
	};

	public MivsTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public MivsTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
