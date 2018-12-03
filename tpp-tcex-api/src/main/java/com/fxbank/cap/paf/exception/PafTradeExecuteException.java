package com.fxbank.cap.paf.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: PafTradeExecuteException 
* @Description: 异常类及响应码、响应信息定义
* @author ZhouYongwei
* @date 2018年4月3日 下午3:40:26 
*  
*/
public class PafTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 2915820465784358202L;

	public final static String PAF_E_10001 = PAF.BANK_CODE+"10001";
	public final static String PAF_E_10002 = PAF.BANK_CODE+"10002";
	public final static String PAF_E_10003 = PAF.BANK_CODE+"10003";
	public final static String PAF_E_10004 = PAF.BANK_CODE+"10004";
	public final static String PAF_E_10005 = PAF.BANK_CODE+"10005";
	public final static String PAF_E_10006 = PAF.BANK_CODE+"10006";
	public final static String PAF_E_10007 = PAF.BANK_CODE+"10007";
	public final static String PAF_E_10008 = PAF.BANK_CODE+"10008";
	public final static String PAF_E_10009 = PAF.BANK_CODE+"10009";
	public final static String PAF_E_10010 = PAF.BANK_CODE+"10010";
	public final static String PAF_E_10011 = PAF.BANK_CODE+"10011";
	public final static String PAF_E_10012 = PAF.BANK_CODE+"10012";
	public final static String PAF_E_10013 = PAF.BANK_CODE+"10013";
	public final static String PAF_E_10014 = PAF.BANK_CODE+"10014";
	public final static String PAF_E_10015 = PAF.BANK_CODE+"10015";
	public final static String PAF_E_10016 = PAF.BANK_CODE+"10016";
	public final static String PAF_E_10017 = PAF.BANK_CODE+"10017";
	public final static String PAF_E_10018 = PAF.BANK_CODE+"10018";
	public final static String PAF_E_10019 = PAF.BANK_CODE+"10019";
	public final static String PAF_E_10020 = PAF.BANK_CODE+"10020";
	public final static String PAF_E_10021 = PAF.BANK_CODE+"10021";
	public final static String PAF_E_10022 = PAF.BANK_CODE+"10022";
	public final static String PAF_E_10023 = PAF.BANK_CODE+"10023";

	public final static Map<String, String> PAFERRCODECONV = new HashMap<String, String>() {
		private static final long serialVersionUID = -8667135437237760216L;

		{
			put(PAF_E_10001, "业务不支持");
			put(PAF_E_10002, "不支持的清算模式");
			put(PAF_E_10003, "核心系统记账失败");
			put(PAF_E_10004, "生成发送请求报文失败");
			put(PAF_E_10005, "接收往账应答报文失败");
			put(PAF_E_10006, "解析往账应答报文失败");
			put(PAF_E_10007, "付款账户户名与实际不符");
			put(PAF_E_10008, "收款账户户名与实际不符");
			put(PAF_E_10009, "下载核心文件失败");
			put(PAF_E_10010, "账户变动通知失败");
			put(PAF_E_10011, "签约账户通知失败");
			put(PAF_E_10012, "活期账户户名与实际不符");
			put(PAF_E_10013, "定期账户户名与实际不符");
			put(PAF_E_10014, "存期不在规定范围");
			put(PAF_E_10015, "上传明细文件失败");
			put(PAF_E_10016, "付款交易不支持利息");
			put(PAF_E_10017, "批量文件类型不支持跨行");
			put(PAF_E_10018, "批量交易总数与明细文件里的总数不符");
			put(PAF_E_10019, "批量交易总金额与明细文件里的总金额不符");
			put(PAF_E_10020, "批量文件不支持混合类型");
			put(PAF_E_10021, "查询没有返回结果");
			put(PAF_E_10022, "批量编号格式不正确");
			put(PAF_E_10023, "批量交易正在处理中");
		}
	};

	public PafTradeExecuteException(String rspCode) {
		super(rspCode, PAFERRCODECONV.get(rspCode) == null ? "响应码未定义" : PAFERRCODECONV.get(rspCode));
	}

	public PafTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
