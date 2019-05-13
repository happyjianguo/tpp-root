package com.fxbank.tpp.mivs.trade.simu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_345_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.sim.MIVS_346_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业信息联网核查查业务受理时间查询（测试）
 * @Author: 王鹏
 * @Date: 2019/5/10 8:51
 */
@Service("MIVS_345_001_01")
public class SIMU_GetSysSts implements TradeExecutionStrategy {

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
        // 1、接收345请求
        MIVS_345_001_01 mivs345 = (MIVS_345_001_01) dto;
        // 2、根据345内容模拟返回990
        CCMS_990_001_02 mivs = new CCMS_990_001_02(new MyLog(), 20190909, 122321, 12);
        mivs.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs.getHeader().setOrigReceiver("0000");
        mivs.getHeader().setMesgID(mivs345.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMT("MT");
        mivs.getComConf().getConfInf().setMsgId(mivs345.getHead().getMesgID());
        mivs.getComConf().getConfInf().setMsgPrcCd("PM1I0000");
        mivs.getComConf().getConfInf().setMsgRefId("msgRefId");
        mivs.getComConf().getConfInf().setOrigSndDt("20190909");
        mivs.getComConf().getConfInf().setOrigSndr("origSndr");

        try {
            pmtsService.sendToPmtsNoWait(mivs);
        } catch (SysTradeExecuteException e) {
            e.printStackTrace();
        }

        // 3、根据345内容模拟返回346
        MIVS_346_001_01 mivs346 = new MIVS_346_001_01(new MyLog(), 20190909, 122321, 13);
        mivs346.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
        mivs346.getHeader().setOrigReceiver("0000");
        mivs346.getHeader().setMesgID(mivs345.getHead().getMesgID());
        mivs346.getRtrSysSts().getMsgHdr().getInstgPty().setInstgDrctPty("0000");
        mivs346.getRtrSysSts().getMsgHdr().getInstgPty().setInstgPty("00000001200197");
        mivs346.getRtrSysSts().getMsgHdr().setMsgId(mivs345.getSysSts().getMsgHdr().getMsgId());
        mivs346.getRtrSysSts().getOrgnlQryInf().setMsgId(mivs345.getHead().getMesgID());
        mivs346.getRtrSysSts().getOrgnlQryInf().getInstgPty().setInstgDrctPty("313871000007");
        mivs346.getRtrSysSts().getOrgnlQryInf().getInstgPty().setInstgPty("313871000007");

        mivs346.getRtrSysSts().getRplyInf().setOrgnlQueDt("2019-03-01");
        mivs346.getRtrSysSts().getRplyInf().setProcSts("PR07");
        mivs346.getRtrSysSts().getRplyInf().getSvcInf().setSysInd("MIIT");
        mivs346.getRtrSysSts().getRplyInf().getSvcInf().setSvcInd("ENBL");
        mivs346.getRtrSysSts().getRplyInf().getSvcInf().setSysOpTm("2019-03-01T08:30:00");
        mivs346.getRtrSysSts().getRplyInf().getSvcInf().setSysClTm("2019-03-01T17:30:00");

        pmtsService.sendToPmtsNoWait(mivs346);

        /**
         //3、根据322内容模拟返回911
         CCMS_911_001_02 ccms911 = new CCMS_911_001_02(new MyLog(), 20190909, 122321, 13);

         ccms911.getHeader().setOrigSender("313131000008"); // TODO 通过机构号查询渠道接口获取（机构号查行号）
         ccms911.getHeader().setOrigReceiver("0000");
         ccms911.getHeader().setMesgID(mivs345.getHead().getMesgID());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMT("mivs.321.001.01");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgId(mivs345.getGetIdVrfctn().getMsgHdr().getMsgId());
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setPrcCd("O1106");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setRjctInf("原报文类型非法");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setMsgRefId("msgRefId");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndDt("20190909");
         ccms911.getDscrdMsgNtfctn().getDscrdInf().setOrigSndr("origSndr");
         pmtsService.sendToPmtsNoWait(ccms911);
         **/
        return mivs345;
    }
}