package com.fxbank.tpp.beps.exception;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 周勇沩
 * @description: 异常类及响应码、响应信息定义
 * @Date : 2019/4/20 21:29
 */
public class PmtsTradeExecuteException extends SysTradeExecuteException {

    private static final long serialVersionUID = 1767308581692333362L;

    public final static String PMTS_E_10001 = "PMTS_E_10001";
    public final static String PMTS_E_10002 = "PMTS_E_10002";

    public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

        private static final long serialVersionUID = -3713497985727043375L;

        {
            put(PMTS_E_10001, "同步等待人行响应超时");
            put(PMTS_E_10002, "生成报文签名错误");

        }
    };

    public PmtsTradeExecuteException(String rspCode) {
        super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
    }

    public PmtsTradeExecuteException(String rspCode, String rspMsg) {
        super(rspCode, rspMsg);
    }

}
