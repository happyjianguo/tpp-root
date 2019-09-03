package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_332_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsFreeFrmtModel;
import com.fxbank.tpp.mivs.model.response.MIVS_332_001_01_FreeFrmt;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsFreeFrmtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 公告信息人行来账交易
 * @Author: 王鹏
 * @Date: 2019/7/3 15:54
 */
@Service("MIVS_332_001_01")
public class FreeFrmt  extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference
    IMivsFreeFrmtService freeFrmtService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_332_001_01 mivs332 = (MIVS_332_001_01) dto;
        MIVS_332_001_01_FreeFrmt.MsgHdr msgHdr = mivs332.getFreeFrmt().getMsgHdr();
        MIVS_332_001_01_FreeFrmt.FreeFrmtInf freeFrmtInf = mivs332.getFreeFrmt().getFreeFrmtInf();
        myLog.info(logger, "收到人行公告信息交易");
        MivsFreeFrmtModel freeFrmtModel = new MivsFreeFrmtModel();
        freeFrmtModel.setPlat_date(mivs332.getSysDate());
        freeFrmtModel.setPlat_trace(mivs332.getSysTraceno());
        freeFrmtModel.setPlat_time(mivs332.getSysTime());
        freeFrmtModel.setMivs_sts("06");
        freeFrmtModel.setMsg_id(msgHdr.getMsgId());
        freeFrmtModel.setCre_dt_tm(msgHdr.getCreDtTm());
        freeFrmtModel.setInstg_drct_pty(msgHdr.getInstgPty().getInstgDrctPty());
        freeFrmtModel.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
        freeFrmtModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        freeFrmtModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        freeFrmtModel.setRply_flag(freeFrmtInf.getRplyFlag());
        freeFrmtModel.setMsg_cntt(freeFrmtInf.getMsgCntt());
        myLog.info(logger, "freeFrmtModel 的值为： " + freeFrmtModel.toString());

        //信息落地
        freeFrmtService.insertInfo(freeFrmtModel);

        //返回990报文
        CCMS_990_001_02 ccms990 = new CCMS_990_001_02();
        ccms990.getComConf().getConfInf().setOrigSndr(mivs332.getHead().getOrigSender());
        ccms990.getComConf().getConfInf().setOrigSndDt(mivs332.getHead().getOrigSendDate().toString());
        ccms990.getComConf().getConfInf().setMT(mivs332.getHead().getMesgType());
        ccms990.getComConf().getConfInf().setMsgId(mivs332.getHead().getMesgID());
        ccms990.getComConf().getConfInf().setMsgRefId(mivs332.getHead().getMesgRefID());
        ccms990.getComConf().getConfInf().setMsgPrcCd("PR01");
        ccms990 = (CCMS_990_001_02) pmtsService.sendToPmtsNoWait(ccms990);

        return mivs332;
    }

}