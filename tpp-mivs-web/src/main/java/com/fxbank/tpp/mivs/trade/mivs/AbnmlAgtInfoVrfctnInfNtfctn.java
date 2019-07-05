package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_330_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsAbnmlVrfctnModel;
import com.fxbank.tpp.mivs.model.response.MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsAbnmlVrfctnService;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 机构异常核查通知交易
 * @Author: 王鹏
 * @Date: 2019/6/6 15:52
 */
@Service("MIVS_330_001_01")
public class AbnmlAgtInfoVrfctnInfNtfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference
    IMivsAbnmlVrfctnService abnmlVrfctnService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_330_001_01 mivs330 = (MIVS_330_001_01) dto;
        MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn.MsgHdr msgHdr = mivs330.getAbnmlAgtInfoVrfctnInfNtfctn().getMsgHdr();
        MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn.AbnmlVrfctnInf abnmlVrfctnInf = mivs330.getAbnmlAgtInfoVrfctnInfNtfctn().getAbnmlVrfctnInf();
        myLog.info(logger, "收到人行机构异常核查通知交易");
        MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel = new MivsAbnmlVrfctnModel();
        mivsAbnmlVrfctnModel.setPlat_date(mivs330.getSysDate());
        mivsAbnmlVrfctnModel.setPlat_trace(mivs330.getSysTraceno());
        mivsAbnmlVrfctnModel.setPlat_time(mivs330.getSysTime());
        mivsAbnmlVrfctnModel.setMivs_sts("06");
        mivsAbnmlVrfctnModel.setMsg_id(msgHdr.getMsgId());
        mivsAbnmlVrfctnModel.setCre_dt_tm(msgHdr.getCreDtTm());
        mivsAbnmlVrfctnModel.setInstg_drct_pty(msgHdr.getInstgPty().getInstgDrctPty());
        mivsAbnmlVrfctnModel.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
        mivsAbnmlVrfctnModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        mivsAbnmlVrfctnModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        mivsAbnmlVrfctnModel.setOrgnl_instg_pty(abnmlVrfctnInf.getOrgnlInstgPty());
        mivsAbnmlVrfctnModel.setAbnml_type(abnmlVrfctnInf.getAbnmlType());
        mivsAbnmlVrfctnModel.setDescrip(abnmlVrfctnInf.getDESC());

        //信息落地
        abnmlVrfctnService.insertStart(mivsAbnmlVrfctnModel);

        //返回990报文
        CCMS_990_001_02 ccms990 = new CCMS_990_001_02();
        ccms990.getComConf().getConfInf().setOrigSndr(mivs330.getHead().getOrigSender());
        ccms990.getComConf().getConfInf().setOrigSndDt(mivs330.getHead().getOrigSendDate().toString());
        ccms990.getComConf().getConfInf().setMT(mivs330.getHead().getMesgType());
        ccms990.getComConf().getConfInf().setMsgId(mivs330.getHead().getMesgID());
        ccms990.getComConf().getConfInf().setMsgRefId(mivs330.getHead().getMesgRefID());
        ccms990.getComConf().getConfInf().setMsgPrcCd("PR01");
        ccms990 = (CCMS_990_001_02) pmtsService.sendToPmtsNoWait(ccms990);

        return mivs330;
    }

}