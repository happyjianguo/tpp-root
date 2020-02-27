package com.fxbank.tpp.beps.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.beps.constant.CONST;
import com.fxbank.tpp.beps.dto.pmts.BEPS_352_001_01;
import com.fxbank.tpp.beps.pmts.CCMS_900_001_02;
import com.fxbank.tpp.beps.pmts.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.beps.pmts.CCMS_911_001_02;
import com.fxbank.tpp.beps.pmts.CCMS_990_001_02;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import com.fxbank.tpp.beps.trade.pmts.TradeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;


@Service("BEPS_352_001_01")
public class P35201_payrcv extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(P35201_payrcv.class);

    @Resource
    private LogPool logPool;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService forwardToPmtsService;

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        Integer sysDate = publicService.getSysDate("CIP");
        Integer sysTime = publicService.getSysTime();
        Integer sysTraceno = publicService.getSysTraceno();
        myLog.info(logger, "352报文-仿真程序--------------------------------------------");
        BEPS_352_001_01 beps352 = (BEPS_352_001_01) dto;

        if (new Random().nextBoolean()) {
            CCMS_990_001_02 ccms990 = new CCMS_990_001_02(myLog, sysDate, sysTime, sysTraceno);
            ccms990.getComConf().getConfInf().setOrigSndr(beps352.getHead().getOrigSender());
            ccms990.getComConf().getConfInf().setOrigSndDt(String.valueOf(beps352.getHead().getOrigSendDate()));
            ccms990.getComConf().getConfInf().setMT(beps352.getHead().getMesgType());
            ccms990.getComConf().getConfInf().setMsgId(beps352.getHead().getMesgID());
            ccms990.getComConf().getConfInf().setMsgRefId(beps352.getHead().getMesgRefID());
            ccms990.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
            try {
                forwardToPmtsService.sendToPmtsNoWait(ccms990);
            } catch (SysTradeExecuteException e) {
                myLog.error(logger, "程序异常", e);
                throw e;
            }

            CCMS_900_001_02 ccms900 = new CCMS_900_001_02(myLog, sysDate, sysTime, sysTraceno);
            CCMS_900_001_02_CmonConf.GrpHdr grpHdr = ccms900.getCmonConf().getGrpHdr();
            grpHdr.getInstgPty().setInstgPty("000000000000");
            grpHdr.getInstgPty().setInstgDrctPty("000000000000");
            grpHdr.getInstdPty().setInstdPty(CONST.SABKNO);
            grpHdr.getInstdPty().setInstdDrctPty(CONST.SABKNO);
            grpHdr.setSysCd("BEPS");
            CCMS_900_001_02_CmonConf.OrgnlGrpHdr orgnlGrpHdr = ccms900.getCmonConf().getOrgnlGrpHdr();
            orgnlGrpHdr.setOrgnlMsgId(beps352.getResFrPtcSn().getGrpHdr().getMsgId());
            orgnlGrpHdr.setOrgnlInstgPty(beps352.getResFrPtcSn().getGrpHdr().getInstgPty().getInstgDrctPty());
            orgnlGrpHdr.setOrgnlMT(beps352.getHead().getMesgType());
            CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();
            cmonConfInf.setPrcSts("PR00");
            cmonConfInf.setPrcCd("PM1I0000");
            try {
                forwardToPmtsService.sendToPmtsNoWait(ccms900);
            } catch (SysTradeExecuteException e) {
                myLog.error(logger, "程序异常", e);
                throw e;
            }
        } else {
            CCMS_911_001_02 ccms911 = new CCMS_911_001_02(myLog, sysDate, sysTime, sysTraceno);
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr(beps352.getHead().getOrigSender());
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt(String.valueOf(beps352.getHead().getOrigSendDate()));
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT(beps352.getHead().getMesgType());
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(beps352.getHead().getMesgID());
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId(beps352.getHead().getMesgRefID());
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
            ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
            try {
                forwardToPmtsService.sendToPmtsNoWait(ccms911);
            } catch (SysTradeExecuteException e) {
                myLog.error(logger, "程序异常", e);
                throw e;
            }
        }

        return beps352;
    }

}