package com.fxbank.tpp.mivs.trade.esb;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
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
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsAcctInfoFdbkModel;
import com.fxbank.tpp.mivs.model.request.MIVS_326_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_326_001_01_AcctInfoFdbk;
import com.fxbank.tpp.mivs.service.IMivsAcctInfoFdbkService;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 企业开销户状态反馈ESB请求；
 * 企业首次开立基本存款账户或企业因注销等原因撤销基本存款账户的情况，银行机构需要反馈企业开销户信息时，
 * 发起参与机构组此报文发送到MIVS，MIVS转发该反馈信息至市场监管总局相关平台的接口。
 * @Author: 王鹏
 * @Date: 2019/6/6 14:35
 */

@Service("REQ_50023000210")
public class AcctInfoFdbk extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsAcctInfoFdbkService acctInfoFdbkService;

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

        //拼发送人行326报文
        MIVS_326_001_01 mivs326 = new MIVS_326_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_326_001_01_AcctInfoFdbk.MsgHdr msgHdr = mivs326.getAcctInfoFdbk().getMsgHdr();
        MIVS_326_001_01_AcctInfoFdbk.Fdbk fdbk = mivs326.getAcctInfoFdbk().getFdbk();

        mivs326.getHeader().setOrigSender(bankNumber);
        mivs326.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstgPty().setPtyNm(bnkNmT);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        fdbk.setEntNm(reqBody.getEntNm());
        fdbk.setTraNm(reqBody.getTraNm());
        fdbk.setUniSocCdtCd(reqBody.getUniSocCdtCd());
        fdbk.setAcctSts(reqBody.getAcctSts());
        fdbk.setChngDt(dateToIsoDate(reqBody.getChngDt()));

        //发送人行请求数据落库
        MivsAcctInfoFdbkModel mivsAcctInfoFdbkModelInsert =  new MivsAcctInfoFdbkModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        mivsAcctInfoFdbkModelInsert.setPlat_date(req.getSysDate());
        mivsAcctInfoFdbkModelInsert.setPlat_trace(req.getSysTraceno());
        mivsAcctInfoFdbkModelInsert.setPlat_time(req.getSysTime());
        mivsAcctInfoFdbkModelInsert.setSystem_id(req.getReqSysHead().getSystemId());
        mivsAcctInfoFdbkModelInsert.setTran_date(req.getReqSysHead().getTranDate());
        mivsAcctInfoFdbkModelInsert.setSeq_no(req.getReqSysHead().getSeqNo());
        mivsAcctInfoFdbkModelInsert.setTran_time(req.getReqSysHead().getTranTimestamp());
        mivsAcctInfoFdbkModelInsert.setUser_id(req.getReqSysHead().getUserId());
        mivsAcctInfoFdbkModelInsert.setBranch_id(req.getReqSysHead().getBranchId());
        mivsAcctInfoFdbkModelInsert.setMivs_sts("00");
        mivsAcctInfoFdbkModelInsert.setMsg_id(msgHdr.getMsgId());
        mivsAcctInfoFdbkModelInsert.setCre_dt_tm(msgHdr.getCreDtTm());
        mivsAcctInfoFdbkModelInsert.setInstg_drct_pty(settlementBankNo);
        mivsAcctInfoFdbkModelInsert.setDrct_pty_nm(lqtnBnkNmT1);
        mivsAcctInfoFdbkModelInsert.setInstg_pty(bankNumber);
        mivsAcctInfoFdbkModelInsert.setPty_nm(bnkNmT);
        mivsAcctInfoFdbkModelInsert.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        mivsAcctInfoFdbkModelInsert.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        mivsAcctInfoFdbkModelInsert.setEnt_nm(fdbk.getEntNm());
        mivsAcctInfoFdbkModelInsert.setTra_nm(fdbk.getTraNm());
        mivsAcctInfoFdbkModelInsert.setUni_soc_cdt_cd(fdbk.getUniSocCdtCd());
        mivsAcctInfoFdbkModelInsert.setAcct_sts(fdbk.getAcctSts());
        mivsAcctInfoFdbkModelInsert.setChng_dt(fdbk.getChngDt());
        mivsAcctInfoFdbkModelInsert.setRemark1(reqBody.getRemarks1());
        mivsAcctInfoFdbkModelInsert.setRemark2(reqBody.getRemarks2());
        mivsAcctInfoFdbkModelInsert.setRemark3(reqBody.getRemarks3());
        acctInfoFdbkService.insertStart(mivsAcctInfoFdbkModelInsert); //插入数据库业务数据

        mivs326 = (MIVS_326_001_01) pmtsService.sendToPmts(mivs326); // 发送请求，实时等待990

        String channel = "900_"+ mivs326.getAcctInfoFdbk().getMsgHdr().getMsgId();  //为同步等待321，组合报文{H:的三要素
        myLog.info(logger,"326报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        //收到人行通讯回执，更新数据库状态
        MivsAcctInfoFdbkModel mivsAcctInfoFdbkModelUpdate = new MivsAcctInfoFdbkModel();
        //更新数据的主键赋值
        mivsAcctInfoFdbkModelUpdate.setPlat_date(req.getSysDate());
        mivsAcctInfoFdbkModelUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000212 rep = new REP_50023000212();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            mivsAcctInfoFdbkModelUpdate.setMivs_sts("02");
            mivsAcctInfoFdbkModelUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            mivsAcctInfoFdbkModelUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("ccms.900.001.02")){//通用处理确认报文

            CCMS_900_001_02 ccms900 = (CCMS_900_001_02)dtoBase;
            CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();

            REP_50023000212.REP_BODY repBody = rep.getRepBody();

            //返回ESB报文
            repBody.setProcSts(cmonConfInf.getPrcSts());
            repBody.setProcCd(cmonConfInf.getPrcCd());
            repBody.setPtyId(cmonConfInf.getPtyId());
            repBody.setPtyPrcCd(cmonConfInf.getPtyPrcCd());
            repBody.setRjctInf(cmonConfInf.getRjctInf());
            repBody.setPrcDt(cmonConfInf.getPrcDt());
            repBody.setNetgRnd(cmonConfInf.getNetgRnd());

            //待更新数据库数据
            mivsAcctInfoFdbkModelUpdate.setMivs_sts("04");
            mivsAcctInfoFdbkModelUpdate.setProc_sts(cmonConfInf.getPrcSts());
            mivsAcctInfoFdbkModelUpdate.setProc_cd(cmonConfInf.getPrcCd());
            mivsAcctInfoFdbkModelUpdate.setPty_id(cmonConfInf.getPtyId());
            mivsAcctInfoFdbkModelUpdate.setPty_prc_cd(cmonConfInf.getPtyPrcCd());
            mivsAcctInfoFdbkModelUpdate.setRjct_inf(cmonConfInf.getRjctInf());
            mivsAcctInfoFdbkModelUpdate.setPrc_dt(cmonConfInf.getPrcDt());
            mivsAcctInfoFdbkModelUpdate.setNetg_rnd(cmonConfInf.getNetgRnd());
        }

        //更新业务数据表
        acctInfoFdbkService.updateSts(mivsAcctInfoFdbkModelUpdate);

        return rep;
    }
}