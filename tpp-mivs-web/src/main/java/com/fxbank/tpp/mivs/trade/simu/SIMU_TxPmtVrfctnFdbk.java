package com.fxbank.tpp.mivs.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_348_001_01;
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
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/16 17:34
 */
@Service("MIVS_348_001_01")
public class SIMU_TxPmtVrfctnFdbk implements TradeExecutionStrategy {

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
        // 1、接收348请求
        MIVS_348_001_01 mivs348 = (MIVS_348_001_01) dto;
        // 2、根据347内容模拟返回990
        CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), 20190909, 122321, 12);
        mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs.getHeader().setOrigReceiver("0000");
        mivs.getHeader().setMesgID(mivs348.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMT("MT");
        mivs.getComConf().getConfInf().setMsgId(mivs348.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
        mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
        mivs.getComConf().getConfInf().setOrigSndDt("2019-09-09");
        mivs.getComConf().getConfInf().setOrigSndr("origSndr");

        try {
            pmtsService.sendToPmtsNoWait(mivs);
        } catch (SysTradeExecuteException e) {
            e.printStackTrace();
        }

        // 3、根据347内容模拟返回900
        CCMS_900_001_02 ccms900 = new CCMS_900_001_02(new MyLog(), 20190909, 122321, 13);
//
//        SimpleDateFormat forMatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        String creDtTm = forMatter.toString();
//        myLog.debug(logger, "啊啊啊啊forMatter = " + forMatter);
//        myLog.debug(logger, "creDtTm = " + creDtTm);
        ccms900.getCmonConf().getGrpHdr().setMsgId(mivs348.getHead().getMesgID());
        ccms900.getCmonConf().getGrpHdr().setCreDtTm(mivs348.getTxPmtVrfctnFdbk().getMsgHdr().getCreDtTm());
        ccms900.getCmonConf().getGrpHdr().getInstgPty().setInstgDrctPty("313131000008");
        ccms900.getCmonConf().getGrpHdr().getInstgPty().setInstgPty("313131000008");
        ccms900.getCmonConf().getGrpHdr().getInstdPty().setInstdDrctPty("313131000008");
        ccms900.getCmonConf().getGrpHdr().getInstdPty().setInstdPty("313131000008");
        ccms900.getCmonConf().getGrpHdr().setSysCd("MIVS");
        ccms900.getCmonConf().getGrpHdr().setRmk("我是个备注");
        ccms900.getCmonConf().getOrgnlGrpHdr().setOrgnlMsgId(mivs348.getTxPmtVrfctnFdbk().getMsgHdr().getMsgId());
        ccms900.getCmonConf().getOrgnlGrpHdr().setOrgnlInstgPty(mivs348.getTxPmtVrfctnFdbk().getMsgHdr().getInstgPty().getInstgDrctPty());
        ccms900.getCmonConf().getOrgnlGrpHdr().setOrgnlMT("mivs.348.001.01");
        ccms900.getCmonConf().getCmonConfInf().setPrcSts("PR07");
        ccms900.getCmonConf().getCmonConfInf().setPrcCd("00000000");
        ccms900.getCmonConf().getCmonConfInf().setPtyId("313131000008");
        ccms900.getCmonConf().getCmonConfInf().setPtyPrcCd("PR07");
        ccms900.getCmonConf().getCmonConfInf().setRjctInf("");
        ccms900.getCmonConf().getCmonConfInf().setPrcDt(mivs348.getTxPmtVrfctnFdbk().getMsgHdr().getCreDtTm());


        pmtsService.sendToPmtsNoWait(ccms900);

        /**
         //3、根据347内容模拟返回911
         CCMS_911_001_02 ccms911 = new CCMS_911_001_02(new MyLog(), 20190909, 122321, 13);

         ccms911.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
         ccms911.getHeader().setOrigReceiver("0000");
         ccms911.getHeader().setMesgID(mivs348.getHead().getMesgID());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT("mivs.321.001.01");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(mivs348.getGetIdVrfctn().getMsgHdr().getMsgId());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId("msgRefId");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt("20190909");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr("origSndr");
         pmtsService.sendToPmtsNoWait(ccms911);
         **/
        return mivs348;
    }
}