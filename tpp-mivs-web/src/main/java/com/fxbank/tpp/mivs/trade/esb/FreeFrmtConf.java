package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000212;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000212;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsFreeFrmtModel;
import com.fxbank.tpp.mivs.model.request.MIVS_333_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_333_001_01_FreeFrmtConf;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsFreeFrmtService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 公告信息确认
 * @Author: 王鹏
 * @Date: 2019/7/3 15:56
 */
@Service("REQ_50023000212")
public class FreeFrmtConf extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsFreeFrmtService mivsFreeFrmtService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000212 req = (REQ_50023000212) dto;
        REQ_50023000212.REQ_BODY reqBody = req.getReqBody();

        // 通过机构号查询渠道接口获取（机构号查行号）
        String branchId = req.getReqSysHead().getBranchId();
        String bankNumber = null, bnkNmT = null, settlementBankNo = null, lqtnBnkNmT1 = null;
        try {
            //调用二代接口查询二代行号
            ESB_REP_30043003001 esbRep_30043003001 = queryBankno(myLog, dto, branchId);
            // 发起行人行行号
            bankNumber = esbRep_30043003001.getRepBody().getBankNumber();
            // 发起行人行行名
            bnkNmT = esbRep_30043003001.getRepBody().getBnkNmT();
            // 发起行人行清算行号
            settlementBankNo = esbRep_30043003001.getRepBody().getSettlementBankNo();
            // 发起行人行清算行名
            lqtnBnkNmT1 = esbRep_30043003001.getRepBody().getLqtnBnkNmT1();
        } catch (SysTradeExecuteException e) {
            myLog.error(logger, "通过本行机构号查询人行行号失败，机构号：" + branchId,e);
            throw e;
        }
        myLog.info(logger, "通过本行机构号查询人行行号成功，机构号：" + branchId + "，人行行号：" + bankNumber);

        //拼发送人行320报文
        MIVS_333_001_01 mivs333 = new MIVS_333_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_333_001_01_FreeFrmtConf.MsgHdr msgHdr = mivs333.getFreeFrmtConf().getMsgHdr();
        MIVS_333_001_01_FreeFrmtConf.OrgnlMsg orgnlMsg = mivs333.getFreeFrmtConf().getOrgnlMsg();
        mivs333.getHeader().setOrigSender(bankNumber);
        mivs333.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        orgnlMsg.setMsgId(reqBody.getOrigMsgId());
        orgnlMsg.getInstgPty().setInstgDrctPty(reqBody.getOrigInstgDrctPty());
        orgnlMsg.getInstgPty().setInstgPty(reqBody.getOrigInstgPty());
        mivs333.getFreeFrmtConf().getFreeFrmtInf().setMsgCntt(reqBody.getMsgCntt());

        //发送人行请求数据落库
        MivsFreeFrmtModel freeFrmtModelInsert =  new MivsFreeFrmtModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        freeFrmtModelInsert.setPlat_date(req.getSysDate());
        freeFrmtModelInsert.setPlat_trace(req.getSysTraceno());
        freeFrmtModelInsert.setPlat_time(req.getSysTime());
        freeFrmtModelInsert.setSystem_id(req.getReqSysHead().getSystemId());
        freeFrmtModelInsert.setTran_date(req.getReqSysHead().getTranDate());
        freeFrmtModelInsert.setSeq_no(req.getReqSysHead().getSeqNo());
        freeFrmtModelInsert.setTran_time(req.getReqSysHead().getTranTimestamp());
        freeFrmtModelInsert.setUser_id(req.getReqSysHead().getUserId());
        freeFrmtModelInsert.setBranch_id(req.getReqSysHead().getBranchId());
        freeFrmtModelInsert.setMivs_sts("00");
        freeFrmtModelInsert.setMsg_id(msgHdr.getMsgId());
        freeFrmtModelInsert.setCre_dt_tm(msgHdr.getCreDtTm());
        freeFrmtModelInsert.setInstg_drct_pty(settlementBankNo);
        freeFrmtModelInsert.setDrct_pty_nm(lqtnBnkNmT1);
        freeFrmtModelInsert.setInstg_pty(bankNumber);
        freeFrmtModelInsert.setPty_nm(bnkNmT);
        freeFrmtModelInsert.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        freeFrmtModelInsert.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        freeFrmtModelInsert.setOrig_dlv_msgid(reqBody.getOrigMsgId());
        freeFrmtModelInsert.setOrig_instg_drct_pty(reqBody.getOrigInstgDrctPty());
        freeFrmtModelInsert.setOrig_instg_pty(reqBody.getOrigInstgPty());
        freeFrmtModelInsert.setMsg_cntt(reqBody.getMsgCntt());

        mivsFreeFrmtService.insertConf(freeFrmtModelInsert); //插入确认信息表
        myLog.info(logger, freeFrmtModelInsert.toString());
        myLog.info(logger,"断点1");
        mivs333 = (MIVS_333_001_01) pmtsService.sendToPmts(mivs333); // 发送请求，实时等待990
        myLog.info(logger,"断点2");
        String channel = "990_"+ mivs333.getHeader().getMesgID();  //为同步等待，组合报文{H:的三要素
        myLog.info(logger,"333报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);
        myLog.info(logger,"断点3");

        //收到人行通讯回执，更新数据库状态
        MivsFreeFrmtModel freeFrmtModeltUpdate = new MivsFreeFrmtModel();
        //更新数据的主键赋值
        freeFrmtModeltUpdate.setPlat_date(req.getSysDate());
        freeFrmtModeltUpdate.setPlat_trace(req.getSysTraceno());
        freeFrmtModeltUpdate.setMsg_id(reqBody.getOrigMsgId());
        freeFrmtModeltUpdate.setInstg_pty(reqBody.getOrigInstgPty());
        freeFrmtModeltUpdate.setInstg_drct_pty(reqBody.getOrigInstgDrctPty());

        REP_50023000212 rep = new REP_50023000212();
        REP_50023000212.REP_BODY repBody = rep.getRepBody();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            freeFrmtModeltUpdate.setMivs_sts("02");
            freeFrmtModeltUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            freeFrmtModeltUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("ccms.990.001.02")){

            CCMS_990_001_02 ccms990 = (CCMS_990_001_02)dtoBase;
            CCMS_990_001_02_ComConf.ConfInf confInf = ccms990.getComConf().getConfInf();

            //返回ESB报文
            repBody.setProcCd(ccms990.getComConf().getConfInf().getMsgPrcCd());

            //待更新数据库数据
            freeFrmtModeltUpdate.setMivs_sts("04");
            freeFrmtModeltUpdate.setProc_cd(confInf.getMsgPrcCd());
            freeFrmtModeltUpdate.setIsornot_rsp("YES");
        }

        //更新业务数据表
        mivsFreeFrmtService.upInfoAndConf(freeFrmtModeltUpdate);

        return rep;
    }
}