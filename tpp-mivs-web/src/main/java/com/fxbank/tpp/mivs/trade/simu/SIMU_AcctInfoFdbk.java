package com.fxbank.tpp.mivs.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_326_001_01;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.sim.CCMS_900_001_02;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业开销户状态反馈
 * @Author: 王鹏
 * @Date: 2019/6/14 16:48
 */
//@Service("MIVS_326_001_01")
public class SIMU_AcctInfoFdbk implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        // 1、接收326请求
        MIVS_326_001_01 mivs326 = (MIVS_326_001_01) dto;
        // 2、根据320内容模拟返回990
        CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), 20190909, 122321, 12);
        mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs.getHeader().setOrigReceiver("0000");
        mivs.getHeader().setMesgID(mivs326.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMT("MT");
        mivs.getComConf().getConfInf().setMsgId(mivs326.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
        mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
        mivs.getComConf().getConfInf().setOrigSndDt("20190909");
        mivs.getComConf().getConfInf().setOrigSndr("origSndr");

        try {
            pmtsService.sendToPmtsNoWait(mivs);
        } catch (SysTradeExecuteException e) {
            e.printStackTrace();
        }

        // 3、根据326内容模拟返回900
        CCMS_900_001_02 ccms900 = new CCMS_900_001_02(new MyLog(), 20190909, 122321, 13);
        CCMS_900_001_02_CmonConf.GrpHdr grpHdr = ccms900.getCmonConf().getGrpHdr();
        CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();
        CCMS_900_001_02_CmonConf.OrgnlGrpHdr orgnlGrpHdr = ccms900.getCmonConf().getOrgnlGrpHdr();

        ccms900.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        ccms900.getHeader().setOrigReceiver("0000");
        ccms900.getHeader().setMesgID(mivs326.getHead().getMesgID());
        grpHdr.getInstgPty().setInstgDrctPty("0000");
        grpHdr.getInstgPty().setInstgPty("000012345678");
        grpHdr.setMsgId(mivs326.getHead().getMesgID());
        grpHdr.setCreDtTm(mivs326.getAcctInfoFdbk().getMsgHdr().getCreDtTm());
        grpHdr.setSysCd("ACCT");
        grpHdr.setRmk("900回复");
        orgnlGrpHdr.setOrgnlMsgId(mivs326.getHead().getMesgID());
        orgnlGrpHdr.setOrgnlInstgPty(mivs326.getAcctInfoFdbk().getMsgHdr().getInstgPty().getInstgDrctPty());
        orgnlGrpHdr.setOrgnlMT("FBDK");
        cmonConfInf.setPrcSts("PR07");
        cmonConfInf.setPrcCd("00000008");
        /**
         ccms900.getRtrIdVrfctn().getRspsn().getOprlErr().setProcSts("1111");
         ccms900.getRtrIdVrfctn().getRspsn().getOprlErr().setProcCd("O3048");
         ccms900.getRtrIdVrfctn().getRspsn().getOprlErr().setRjctinf("手机号码核查业务本行当日总量超限");
         **/

        pmtsService.sendToPmtsNoWait(ccms900);

        /**
         //3、根据320内容模拟返回911
         CCMS_911_001_02 ccms911 = new CCMS_911_001_02(new MyLog(), 20190909, 122321, 13);

         ccms911.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
         ccms911.getHeader().setOrigReceiver("0000");
         ccms911.getHeader().setMesgID(mivs326.getHead().getMesgID());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT("mivs.321.001.01");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(mivs326.getGetIdVrfctn().getMsgHdr().getMsgId());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId("msgRefId");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt("20190909");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr("origSndr");
         pmtsService.sendToPmtsNoWait(ccms911);
         **/
        return mivs326;
    }
}