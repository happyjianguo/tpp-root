package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_331_001_01;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_331_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsAbnmlVrfctnModel;
import com.fxbank.tpp.mivs.model.response.MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsAbnmlVrfctnService;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业异常核查通知报文
 * @Author: 王鹏
 * @Date: 2019/6/6 16:23
 */
@Service("MIVS_331_001_01")
public class AbnmlCoInfoVrfctnInfNtfctn extends TradeBase implements TradeExecutionStrategy {

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
        MIVS_331_001_01 mivs331 = (MIVS_331_001_01) dto;
        MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn.MsgHdr msgHdr = mivs331.getAbnmlCoInfoVrfctnInfNtfctn().getMsgHdr();
        MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn.AbnmlVrfctnInf abnmlVrfctnInf = mivs331.getAbnmlCoInfoVrfctnInfNtfctn().getAbnmlVrfctnInf();
        myLog.info(logger, "收到人行企业异常核查通知交易");
        MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel = new MivsAbnmlVrfctnModel();
        mivsAbnmlVrfctnModel.setPlat_date(mivs331.getSysDate());
        mivsAbnmlVrfctnModel.setPlat_trace(mivs331.getSysTraceno());
        mivsAbnmlVrfctnModel.setPlat_time(mivs331.getSysTime());
        mivsAbnmlVrfctnModel.setMivs_sts("06");
        mivsAbnmlVrfctnModel.setMsg_id(msgHdr.getMsgId());
        mivsAbnmlVrfctnModel.setCre_dt_tm(msgHdr.getCreDtTm());
        mivsAbnmlVrfctnModel.setInstg_drct_pty(msgHdr.getInstgPty().getInstgDrctPty());
        mivsAbnmlVrfctnModel.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
        mivsAbnmlVrfctnModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        mivsAbnmlVrfctnModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        mivsAbnmlVrfctnModel.setCo_nm(abnmlVrfctnInf.getAbnmlCo().getCoNm());
        mivsAbnmlVrfctnModel.setUni_soc_cdt_cd(abnmlVrfctnInf.getAbnmlCo().getUniSocCdtCd());
        mivsAbnmlVrfctnModel.setMob_nb(abnmlVrfctnInf.getAbnmlPhNb().getPhNb());
        mivsAbnmlVrfctnModel.setNm(abnmlVrfctnInf.getAbnmlPhNb().getNm());
        mivsAbnmlVrfctnModel.setAbnml_type(abnmlVrfctnInf.getAbnmlType());
        mivsAbnmlVrfctnModel.setDescrip(abnmlVrfctnInf.getDESC());

        //信息落地
        abnmlVrfctnService.insertStart(mivsAbnmlVrfctnModel);

        //返回990报文
        CCMS_990_001_02 ccms990 = new CCMS_990_001_02();
        ccms990.getComConf().getConfInf().setOrigSndr(mivs331.getHead().getOrigSender());
        ccms990.getComConf().getConfInf().setOrigSndDt(mivs331.getHead().getOrigSendDate().toString());
        ccms990.getComConf().getConfInf().setMT(mivs331.getHead().getMesgType());
        ccms990.getComConf().getConfInf().setMsgId(mivs331.getHead().getMesgID());
        ccms990.getComConf().getConfInf().setMsgRefId(mivs331.getHead().getMesgRefID());
        ccms990.getComConf().getConfInf().setMsgPrcCd("PR01");
        ccms990 = (CCMS_990_001_02) pmtsService.sendToPmtsNoWait(ccms990);

        return mivs331;
    }

}