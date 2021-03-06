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
	
	private static final long serialVersionUID = -8094410202484019310L;
	public final static String TCEX_E_10001 = "TCEX_E_10001";
	public final static String TCEX_E_10002 = "TCEX_E_10002";
	public final static String TCEX_E_10003 = "TCEX_E_10003";
	public final static String TCEX_E_10004 = "TCEX_E_10004";
	public final static String TCEX_E_10005 = "TCEX_E_10005";
	public final static String TCEX_E_10006 = "TCEX_E_10006";
	public final static String TCEX_E_10007 = "TCEX_E_10007";
	public final static String TCEX_E_10008 = "TCEX_E_10008";
	public final static String TCEX_E_10009 = "TCEX_E_10009";
	public final static String TCEX_E_10010 = "TCEX_E_10010";
	public final static String TCEX_E_10011 = "TCEX_E_10011";
	public final static String TCEX_E_10012 = "TCEX_E_10012";
	public final static String TCEX_E_10013 = "TCEX_E_10013";
	public final static String TCEX_E_10014 = "TCEX_E_10014";
	public final static String TCEX_E_10015 = "TCEX_E_10015";
	public final static String TCEX_E_10016 = "TCEX_E_10016";
	public final static String TCEX_E_10017 = "TCEX_E_10017";

	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {
		private static final long serialVersionUID = -8667135437237760216L;

		{
			put(TCEX_E_10001, "业务不支持");
			put(TCEX_E_10002, "获取村镇账户信息失败");
			put(TCEX_E_10003, "柜面通对账失败");
			put(TCEX_E_10004, "村镇记账失败");
			put(TCEX_E_10005, "村镇头寸查询失败");
			put(TCEX_E_10006, "商行账户信息查询失败");
			put(TCEX_E_10007, "村镇冲正失败");
			put(TCEX_E_10008, "商行核心记账失败");
			put(TCEX_E_10009, "下载ESB文件失败");
			put(TCEX_E_10010, "商行冲正失败");
			put(TCEX_E_10011, "村镇存款确认失败");
			put(TCEX_E_10012, "村镇冲正成功");
			put(TCEX_E_10013, "核心冲正成功");
			put(TCEX_E_10014, "待确认存款信息不存在");
			put(TCEX_E_10015, "不能隔日冲正");
			put(TCEX_E_10016, "核心冲正超时");
			put(TCEX_E_10017, "核心冲正失败");
			
		}
	};

	public TcexTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public TcexTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
