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
	public final static String BOCM_E_10002 = "FX0002";
	public final static String BOCM_E_10003 = "FX0003";
	public final static String BOCM_E_10004 = "FX0004";
	public final static String BOCM_E_10005 = "FX0005";
	public final static String BOCM_E_10006 = "FX0006";
	public final static String BOCM_E_10007 = "FX0007";
	public final static String BOCM_E_10008 = "FX0008";

	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(BOCM_E_10001, "业务不支持");
			put(BOCM_E_10002, "交行记账失败");	
			put(BOCM_E_10003, "交行第一次记账超时，重发记账失败");
			put(BOCM_E_10004, "核心记账失败");
			put(BOCM_E_10005, "交行冲正失败");
			put(BOCM_E_10006, "交行冲正成功");
			put(BOCM_E_10007, "磁条卡二磁道校验失败");
			put(BOCM_E_10008, "IC卡信息校验失败");
			
		}
	};

	public BocmTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public BocmTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
