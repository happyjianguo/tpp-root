package com.fxbank.tpp.beps.service.impl;

import cn.highsuccess.connPool.api.tssc.secondpayment.HisuTSSCAPIForSecondPayment;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.constant.CONST;
import com.fxbank.tpp.beps.exception.PmtsTradeExecuteException;
import com.fxbank.tpp.beps.mq.MqQaClient;
import com.fxbank.tpp.beps.pmts.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.beps.pmts.MODEL_BASE;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import com.fxbank.tpp.beps.sync.SyncCom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service(version = "1.0.0")
public class ForwardToPmtsService implements IForwardToPmtsService {

    private static Logger logger = LoggerFactory.getLogger(ForwardToPmtsService.class);

    @Resource
    private MqQaClient mqQaClient;

    @Resource
    private SyncCom syncCom;

    @Resource
    private MyJedis myJedis;

    @Resource
    private HisuTSSCAPIForSecondPayment hisuTSSCAPIForSecondPayment;

    @Override
    public Object sendToPmtsRtnWait(MODEL_BASE modelBase) throws SysTradeExecuteException {
        MyLog myLog = modelBase.getMylog();
        sendToPmts990Wait(modelBase);

        Integer timeout = 0;
        try (Jedis jedis = myJedis.connect()) {
            String stimeout = jedis.get(CONST.PREFIX+CONST.TIMEOUT_RTN);
            if (stimeout == null) {
                timeout = 60;
            } else {
                try {
                    timeout = Integer.valueOf(stimeout);
                } catch (Exception e) {
                    myLog.error(logger, "ccms990报文同步等待超时时间配置异常，取默认值60");
                    timeout = 60;
                }
            }
        }
        Object obj = syncCom.get(myLog, "rtn_" + modelBase.getHead().getMesgID(), timeout,
                TimeUnit.SECONDS);

        return obj;
    }


    @Override
    public Object sendToPmts990Wait(MODEL_BASE modelBase) throws SysTradeExecuteException {
        MyLog myLog = modelBase.getMylog();
        sendToPmtsNoWait(modelBase);
        CCMS_990_001_02_ComConf ccms990ComConf = waitPmts990(modelBase);
        return ccms990ComConf;
    }

    private CCMS_990_001_02_ComConf waitPmts990(MODEL_BASE modelBase) throws SysTradeExecuteException {
        MyLog myLog = modelBase.getMylog();
        Integer timeout = 0;
        try (Jedis jedis = myJedis.connect()) {
            String stimeout = jedis.get(CONST.PREFIX+CONST.TIMEOUT_990);
            if (stimeout == null) {
                timeout = 60;
            } else {
                try {
                    timeout = Integer.valueOf(stimeout);
                } catch (Exception e) {
                    myLog.error(logger, "ccms990报文同步等待超时时间配置异常，取默认值60");
                    timeout = 60;
                }
            }
        }
        CCMS_990_001_02_ComConf ccms990ComConf = syncCom.get(myLog, "990_" + modelBase.getHead().getMesgID(), timeout,
                TimeUnit.SECONDS);
        String msgPrcCd = ccms990ComConf.getConfInf().getMsgPrcCd();
        if (!msgPrcCd.substring(4).equals("0000")) {
            myLog.error(logger, "收到990错误应答" + msgPrcCd);
            throw new PmtsTradeExecuteException(msgPrcCd, "请求发送失败");
        }

        return ccms990ComConf;
    }

    @Override
    public void sendToPmtsNoWait(MODEL_BASE modelBase) throws SysTradeExecuteException {
        MyLog myLog = modelBase.getMylog();

        modelBase.getHead().setOrigSendDate(modelBase.getSysDate());
        modelBase.getHead().setOrigSendTime(modelBase.getSysTime());
        modelBase.getHead().setMesgType(modelBase.getMesgType());
        modelBase.getHead().setMesgID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));
        modelBase.getHead()
                .setMesgRefID(String.format("%08d%08d", modelBase.getSysDate(), modelBase.getSysTraceno()));

        String signData = modelBase.signData(); // 待签名数据
        myLog.info(logger, "签名字段为：" + signData.toString());
        /*
        if (signData != null) { // 需要签名
            String signature = null;
            try {
                HisuTSSCAPIResult result = this.hisuTSSCAPIForSecondPayment.hisuUniveralGenDataSign("CNAPS2",
                        CONST.SABKNO, "SM2", signData.getBytes());
                if (result.getErrCode() < 0) {
                    myLog.error(logger, "计算签名错误[" + result.getErrCode() + "]");
                    throw new PmtsTradeExecuteException(PmtsTradeExecuteException.PMTS_E_10002);
                }
                signature = result.getProperties("sign");
            } catch (Exception e) {
                myLog.error(logger, "计算签名错误", e);
                throw new PmtsTradeExecuteException(PmtsTradeExecuteException.PMTS_E_10002);
            }
            myLog.info(logger, "计算签名成功");
            modelBase.getSign().setDigitalSignature(signature);
        }
        */
        String pack = modelBase.creaPack();
        myLog.info(logger, "发送至PMTS的请求报文=[" + pack + "]");
        mqQaClient.put(myLog, pack);
    }

}
