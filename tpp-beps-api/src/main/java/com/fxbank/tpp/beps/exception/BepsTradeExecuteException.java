package com.fxbank.tpp.beps.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/**
 * @Description: 异常类及响应码、响应信息定义
 * @Author: 周勇沩
 * @Date: 2019-04-20 21:29:29
 */
public class BepsTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 1767308581692333362L;

	public final static String BEPS_E_10001 = "beps_0001";
	public final static String BEPS_E_10002 = "beps_0002";
	public final static String BEPS_E_10003 = "beps_0003";
	
	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(BEPS_E_10001, "同步等待人行响应超时");
			put(BEPS_E_10002, "人行拒绝受理");
			put(BEPS_E_10003, "行内拒绝受理");
			
		}
	};

	public BepsTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public BepsTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
