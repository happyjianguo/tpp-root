package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000211;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000211;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_349_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_349_001_01_RegVrfctnFdbk;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 登记信息核查反馈交易
 * @Author: 王鹏
 * @Date: 2019/6/24 10:15
 */
@Service("REQ_50023000211")
public class RegVrfctnFdbk extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsRegVrfctnInfoService mivsRegVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000211 req = (REQ_50023000211) dto;//接收ESB请求报文
        REQ_50023000211.REQ_BODY reqBody = req.getReqBody();

        MivsRegVrfctnInfoModel regVrfctnInfoModel = new MivsRegVrfctnInfoModel();
        regVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        regVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        MivsRegVrfctnInfoModel infoModel = mivsRegVrfctnInfoService.selectFdbk(regVrfctnInfoModel);
        if(infoModel.getRemark1() != null) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, infoModel.getRemark1());
            throw e;
        }else{
            myLog.info(logger, "infoModel.getOrig_dlv_msgid = " + infoModel.getOrig_dlv_msgid() + ", infoModel.getOrig_rcv_msgid" + infoModel.getOrig_rcv_msgid());
        }

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
        MIVS_349_001_01 mivs349 = new MIVS_349_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_349_001_01_RegVrfctnFdbk.MsgHdr msgHdr = mivs349.getRegVrfctnFdbk().getMsgHdr();
        MIVS_349_001_01_RegVrfctnFdbk.Fdbk fdbk = mivs349.getRegVrfctnFdbk().getFdbk();
        MIVS_349_001_01_RegVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.OrgnlVrfctnInfOfEnt vrfctnInfOfEnt = mivs349.getRegVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().getOrgnlVrfctnInfOfEnt();
        MIVS_349_001_01_RegVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.OrgnlVrfctnInfOfSlfEplydPpl vrfctnInfOfSlfEplydPpl = mivs349.getRegVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().getOrgnlVrfctnInfOfSlfEplydPpl();
        myLog.info(logger, "登记信息核查结果疑义反馈");
        //拼人行报文
        mivs349.getHeader().setOrigSender(bankNumber);
        mivs349.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        fdbk.setSysInd("SAMR");
        fdbk.getOrgnlVrfctn().setOrgnlDlvrgMsgId(reqBody.getOrgnlDlvrgMsgId());
        fdbk.getOrgnlVrfctn().setOrgnlRcvgMsgId(reqBody.getOrgnlRcvgMsgId());
        if(reqBody.getEntNm() != null) {
            vrfctnInfOfEnt.setEntNm(reqBody.getEntNm());
            vrfctnInfOfEnt.setUniSocCdtCd(reqBody.getUniSocCdtCd());
            vrfctnInfOfEnt.setNmOfLglPrsn(reqBody.getNmOfLglPrsn());
            vrfctnInfOfEnt.setIdOfLglPrsn(reqBody.getIdOfLglPrsn());
        }else if(reqBody.getNm() != null){
            vrfctnInfOfSlfEplydPpl.setTraNm(reqBody.getTraNm());
            vrfctnInfOfSlfEplydPpl.setUniSocCdtCd(reqBody.getUniSocCdtCd());
            vrfctnInfOfSlfEplydPpl.setNm(reqBody.getNm());
            vrfctnInfOfSlfEplydPpl.setId(reqBody.getId());
        }
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setRslt(reqBody.getRslt());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setDataResrcDt(reqBody.getDataResrcDt());
        fdbk.setCntt(reqBody.getCntt());
        fdbk.setContactNb(reqBody.getContactNb());
        fdbk.setContactNm(reqBody.getContactNm());

        //信息落地入库
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        regVrfctnInfoModel.setPlat_date(req.getSysDate());
        regVrfctnInfoModel.setPlat_trace(req.getSysTraceno());
        regVrfctnInfoModel.setPlat_time(req.getSysTime());
        regVrfctnInfoModel.setSystem_id(req.getReqSysHead().getSystemId());
        regVrfctnInfoModel.setTran_date(req.getReqSysHead().getTranDate());
        regVrfctnInfoModel.setSeq_no(req.getReqSysHead().getSeqNo());
        regVrfctnInfoModel.setTran_time(req.getReqSysHead().getTranTimestamp());
        regVrfctnInfoModel.setUser_id(req.getReqSysHead().getUserId());
        regVrfctnInfoModel.setBranch_id(req.getReqSysHead().getBranchId());
        regVrfctnInfoModel.setMivs_sts("00");
        regVrfctnInfoModel.setMsg_id(msgHdr.getMsgId());
        regVrfctnInfoModel.setCre_dt_tm(msgHdr.getCreDtTm());
        regVrfctnInfoModel.setInstg_drct_pty(settlementBankNo);
        regVrfctnInfoModel.setDrct_pty_nm(lqtnBnkNmT1);
        regVrfctnInfoModel.setInstg_pty(bankNumber);
        regVrfctnInfoModel.setPty_nm(bnkNmT);
        regVrfctnInfoModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        regVrfctnInfoModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        regVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        regVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        regVrfctnInfoModel.setSys_ind(fdbk.getSysInd());
        regVrfctnInfoModel.setEnt_nm(reqBody.getEntNm());
        regVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        regVrfctnInfoModel.setNm_of_lgl_prsn(reqBody.getNmOfLglPrsn());
        regVrfctnInfoModel.setId_of_lgl_prsn(reqBody.getIdOfLglPrsn());
        regVrfctnInfoModel.setTra_nm(reqBody.getTraNm());
        regVrfctnInfoModel.setNm(reqBody.getNm());
        regVrfctnInfoModel.setId(reqBody.getId());
        regVrfctnInfoModel.setRslt(reqBody.getRslt());
        regVrfctnInfoModel.setData_resrc_dt(reqBody.getDataResrcDt());
        regVrfctnInfoModel.setCntt(reqBody.getCntt());
        regVrfctnInfoModel.setContact_nm(reqBody.getContactNm());
        regVrfctnInfoModel.setContact_nb(reqBody.getContactNb());

        mivsRegVrfctnInfoService.insertFdbk(regVrfctnInfoModel); //插入数据库业务数据
        myLog.info(logger,"regVrfctnInfoModel插入的表数据为：" + regVrfctnInfoModel.toString());

        mivs349 = (MIVS_349_001_01) pmtsService.sendToPmts(mivs349); // 发送请求，实时等待990

        String channel = "900_"+ mivs349.getRegVrfctnFdbk().getMsgHdr().getMsgId();//为同步等待900，组合三要素
        myLog.info(logger,"349报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);
        myLog.info(logger,"收到的报文类型" + dtoBase.getHead().getMesgType());

        //收到人行通讯回执，更新数据库状态
        MivsRegVrfctnInfoModel regVrfctnInfoModelUpdate = new MivsRegVrfctnInfoModel();
        //更新数据的主键赋值
        regVrfctnInfoModelUpdate.setPlat_date(req.getSysDate());
        regVrfctnInfoModelUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000211 rep = new REP_50023000211();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            regVrfctnInfoModelUpdate.setMivs_sts("02");
            regVrfctnInfoModelUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            regVrfctnInfoModelUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("ccms.900.001.02")){ //通用处理确认报文

            CCMS_900_001_02 ccms900 = (CCMS_900_001_02)dtoBase;
            CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();

            REP_50023000211.REP_BODY repBody = rep.getRepBody();

            //返回ESB报文
            repBody.setProcSts(cmonConfInf.getPrcSts());
            repBody.setProcCd(cmonConfInf.getPrcCd());
            repBody.setPtyId(cmonConfInf.getPtyId());
            repBody.setPtyPrcCd(cmonConfInf.getPtyPrcCd());
            repBody.setRjctInf(cmonConfInf.getRjctInf());
            repBody.setPrcDt(cmonConfInf.getPrcDt());
            repBody.setRemark1(cmonConfInf.getNetgRnd());

            //待更新数据库数据
            regVrfctnInfoModelUpdate.setMivs_sts("04");
            regVrfctnInfoModelUpdate.setRcv_msg_id(ccms900.getCmonConf().getGrpHdr().getMsgId());
            regVrfctnInfoModelUpdate.setRcv_cre_dt_tm(ccms900.getCmonConf().getGrpHdr().getCreDtTm());
            regVrfctnInfoModelUpdate.setProc_sts(cmonConfInf.getPrcSts());
            regVrfctnInfoModelUpdate.setProc_cd(cmonConfInf.getPrcCd());
            regVrfctnInfoModelUpdate.setPty_id(cmonConfInf.getPtyId());
            regVrfctnInfoModelUpdate.setPty_prc_cd(cmonConfInf.getPtyPrcCd());
            regVrfctnInfoModelUpdate.setRjct_inf(cmonConfInf.getRjctInf());
            regVrfctnInfoModelUpdate.setPrc_dt(cmonConfInf.getPrcDt());
            regVrfctnInfoModelUpdate.setNetg_rnd(cmonConfInf.getNetgRnd());
        }
        //更新业务数据表
        myLog.info(logger,"regVrfctnInfoModelUpdate更新的表数据为：" + regVrfctnInfoModelUpdate.toString());
        mivsRegVrfctnInfoService.updateFdbk(regVrfctnInfoModelUpdate);

        return rep;
    }
}
