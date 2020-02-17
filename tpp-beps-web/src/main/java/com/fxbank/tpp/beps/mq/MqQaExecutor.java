/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-07-10 08:16:39
 * @LastEditTime: 2019-08-26 11:45:14
 * @LastEditors: Please set LastEditors
 */
package com.fxbank.tpp.beps.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.controller.TradeDispatcherExecutor;
import com.fxbank.tpp.beps.dto.beps.DTO_BASE;
import com.fxbank.tpp.beps.model.PMTS_HEAD;
import com.fxbank.tpp.beps.model.PMTS_SIGN;
import com.fxbank.tpp.beps.util.PmtsXmlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;
import cn.highsuccess.connPool.api.tssc.secondpayment.HisuTSSCAPIForSecondPayment;

@Component
public class MqQaExecutor {

    private static Logger logger = LoggerFactory.getLogger(MqQaExecutor.class);

    @Resource
    private TradeDispatcherExecutor tradeDispatcherExecutor;

    @Resource
    private HisuTSSCAPIForSecondPayment hisuTSSCAPIForSecondPayment;

    @Resource
    private LogPool logPool;

    public void execute(String message) {
        MyLog myLog = null;
        try {
            myLog = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
        } catch (UnknownHostException e1) {
            myLog = new MyLog(UUID.randomUUID().toString(), "UnknownHost");
        }
        logPool.init(myLog);
        myLog.info(logger, "收到人行报文[" + message + "]");
        PMTS_HEAD head = new PMTS_HEAD();
        PMTS_SIGN sign = new PMTS_SIGN();
        String[] splitPack = message.split("}\r?\n");
        String sHead = null;
        String sSign = null;
        String xml = null;
        if (splitPack.length == 2) { // 无签名
            sHead = splitPack[0];
            xml = splitPack[1];
        } else if (splitPack.length == 3) { // 有签名
            sHead = splitPack[0];
            sSign = splitPack[1];
            xml = splitPack[2];
            sign.chanFixPack(sSign);
        }
        head.chanFixPack(sHead);
        String txCode = head.getMesgType().replaceAll("\\.", "_").toUpperCase();
        myLog.info(logger, "交易代码=[" + txCode + "]");

        DTO_BASE dtoBase = null;
        Class<?> bepsClass = null;
        String className = "com.fxbank.tpp.beps.dto.beps" + "." + txCode;
        try {
            bepsClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            myLog.error(logger, "类文件[" + className + "]未定义", e);
            throw new RuntimeException(e);
        }

        try {
            dtoBase = (DTO_BASE) PmtsXmlUtil.xmlToObject(bepsClass, xml);
            myLog.info(logger, "交易描述=[" + dtoBase.getTxDesc() + "]");
            dtoBase.setTxCode(txCode);
            dtoBase.setSourceType("beps");
            dtoBase.setOthDate(head.getOrigSendDate());
            dtoBase.setOthTraceno(head.getMesgID());
            dtoBase.setHead(head);
            dtoBase.setSign(sign);

//            if (sign.getDigitalSignature() != null) {
//                String signData = dtoBase.signData();
//                try {
//                    HisuTSSCAPIResult result = this.hisuTSSCAPIForSecondPayment.hisuUniversalVerifyDataSign("CNAPS2",
//                            head.getOrigSender(), "X509", sign.getDigitalSignature(), signData.getBytes());
//                    if (result.getErrCode() < 0) {
//                        myLog.error(logger, "验证签名错误[" + signData + "][" + sign.getDigitalSignature().toString() + "]");
//                        throw new RuntimeException("验证签名错误");
//                    }
//                } catch (Exception e) {
//                    myLog.error(logger, "验证签名错误[" + signData + "][" + sSign + "]", e);
//                    throw new RuntimeException("验证签名错误");
//                }
//                myLog.info(logger,"验证签名成功");
//            }
        } catch (RuntimeException e) {
            myLog.error(logger, "解析报文失败[" + xml + "]", e);
            throw new RuntimeException(e);
        }

        tradeDispatcherExecutor.txMainFlowController(dtoBase);
    }

}