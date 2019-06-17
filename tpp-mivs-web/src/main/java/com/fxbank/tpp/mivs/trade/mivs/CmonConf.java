package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsCmonConfModel;
import com.fxbank.tpp.mivs.service.IMivsCmonConfService;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 900
 * @Author: 王鹏
 * @Date: 2019/5/16 16:26
 */
@Service("CCMS_900_001_02")
public class CmonConf extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(CmonConf.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference(version = "1.0.0")
    private IMivsCmonConfService mivsCmonConfService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_900_001_02 ccms900 = (CCMS_900_001_02) dto;
        CCMS_900_001_02_CmonConf.GrpHdr grpHdr = ccms900.getCmonConf().getGrpHdr();
        CCMS_900_001_02_CmonConf.OrgnlGrpHdr orgnlGrpHdr = ccms900.getCmonConf().getOrgnlGrpHdr();
        CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();
        myLog.info(logger, "收到人行900应答报文,进行同步处理");
        byte[] b900 = SerializeUtil.serialize(ccms900);
        String channel = "900_" + orgnlGrpHdr.getOrgnlMsgId();
        myLog.info(logger, "900报文同步通道编号=[" + channel + "]");

        //插入900数据库处理
        MivsCmonConfModel mivsCmonConfModel = new MivsCmonConfModel();
        mivsCmonConfModel.setPlat_date(ccms900.getSysDate());
        mivsCmonConfModel.setPlat_trace(ccms900.getSysTraceno());
        mivsCmonConfModel.setPlat_time(ccms900.getSysTime());
        mivsCmonConfModel.setMsg_id(grpHdr.getMsgId());
        mivsCmonConfModel.setCre_dt_tm(grpHdr.getCreDtTm());
        mivsCmonConfModel.setInstg_drct_pty(grpHdr.getInstgPty().getInstgDrctPty());
        mivsCmonConfModel.setInstg_pty(grpHdr.getInstgPty().getInstgPty());
        mivsCmonConfModel.setInstd_drct_pty(grpHdr.getInstdPty().getInstdDrctPty());
        mivsCmonConfModel.setInstd_pty(grpHdr.getInstdPty().getInstdPty());
        mivsCmonConfModel.setSystem_code(grpHdr.getSysCd());
        mivsCmonConfModel.setRemark(grpHdr.getRmk());
        mivsCmonConfModel.setOrgnl_msg_id(orgnlGrpHdr.getOrgnlMsgId());
        mivsCmonConfModel.setOrgnl_instg_pty(orgnlGrpHdr.getOrgnlInstgPty());
        mivsCmonConfModel.setOrgnl_mt(orgnlGrpHdr.getOrgnlMT());
        mivsCmonConfModel.setProc_sts(cmonConfInf.getPrcSts());
        mivsCmonConfModel.setProc_cd(cmonConfInf.getPrcCd());
        mivsCmonConfModel.setPty_id(cmonConfInf.getPtyId());
        mivsCmonConfModel.setPty_prc_cd(cmonConfInf.getPtyPrcCd());
        mivsCmonConfModel.setRjct_inf(cmonConfInf.getRjctInf());;
        mivsCmonConfModel.setPrc_dt(cmonConfInf.getPrcDt());
        mivsCmonConfModel.setNetg_rnd(cmonConfInf.getNetgRnd());
        mivsCmonConfService.insertStart(mivsCmonConfModel);

        super.jedisPublish(myLog,channel.getBytes(), b900);
        myLog.info(logger, "发布至redis成功");
        return ccms900;
    }

}