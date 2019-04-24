package com.fxbank.tpp.mivs.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.controller.TradeDispatcherExecutor;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.model.PMTS_HEAD;
import com.fxbank.tpp.mivs.model.PMTS_SIGN;
import com.fxbank.tpp.mivs.util.PmtsXmlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqQaExecutor{

    private static Logger logger = LoggerFactory.getLogger(MqQaExecutor.class);

    @Resource
    private TradeDispatcherExecutor tradeDispatcherExecutor;

    @Resource
    private LogPool logPool;

    public void execute(String message){
        MyLog myLog = null;
        try {
            myLog = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
        } catch (UnknownHostException e1) {
            myLog = new MyLog(UUID.randomUUID().toString(), "UnknownHost");
        }
		logPool.init(myLog);
        myLog.info(logger, "收到人行报文["+message+"]");
        PMTS_HEAD head = new PMTS_HEAD();
        PMTS_SIGN sign = new PMTS_SIGN();
        String[] splitPack = message.split("\r\n");
        String sHead = null;
        String sSign = null;
        String xml = null;
        if (splitPack.length == 2) {    //无签名
            sHead = splitPack[0];
            xml = splitPack[1];
        } else if (splitPack.length == 3) { //有签名
            sHead = splitPack[0];
            sSign = splitPack[1];
            xml = splitPack[2];
            sign.chanFixPack(sSign);
        }
        head.chanFixPack(sHead);
        String txCode = head.getMesgType().replaceAll("\\.", "_").toUpperCase();
        myLog.info(logger, "交易代码=[" + txCode + "]");

        DTO_BASE dtoBase = null;
		Class<?> mivsClass = null;
		String className = "com.fxbank.tpp.mivs.dto.mivs"+"." + txCode;
		try {
			mivsClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
            myLog.error(logger, "类文件[" + className + "]未定义",e);
            throw new RuntimeException(e);
		}

		try {
            dtoBase = (DTO_BASE) PmtsXmlUtil.xmlToObject(mivsClass, xml);
            myLog.info(logger, "交易描述=[" + dtoBase.getTxDesc() + "]");
            String signData = dtoBase.signData();
            //TODO 验证签名
			dtoBase.setTxCode(txCode);
			dtoBase.setSourceType("MIVS");
		    dtoBase.setOthDate(head.getOrigSendDate());
            dtoBase.setOthTraceno(head.getMesgID());
            dtoBase.setHead(head);
            dtoBase.setSign(sign);
		} catch (RuntimeException e) {
			myLog.error(logger, "解析报文失败[" + xml + "]",e);
            throw new RuntimeException(e);
		}

        tradeDispatcherExecutor.txMainFlowController(dtoBase);
    }

}