package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000203;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000203;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_347_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_347_001_01_IdVrfctnFdbk;
import com.fxbank.tpp.mivs.model.request.MIVS_348_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsIdVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:  手机号码核查结果疑义反馈报文 mivs.347.001.01
 * @Author: 王鹏
 * @Date: 2019/5/5 8:09
 */
@Service("REQ_50023000203")
public class IdVrfctnFdbk extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsIdVrfctnInfoService mivsIdVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000203 req = (REQ_50023000203) dto;//接收ESB请求报文
        REQ_50023000203.REQ_BODY reqBody = req.getReqBody();

        MivsIdVrfctnInfoModel idVrfctnInfoModel = new MivsIdVrfctnInfoModel();
        idVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        idVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        MivsIdVrfctnInfoModel infoModel = mivsIdVrfctnInfoService.selectFdbk(idVrfctnInfoModel);
        if(infoModel == null) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无反馈记录");
            throw e;
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
        MIVS_347_001_01 mivs347 = new MIVS_347_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_347_001_01_IdVrfctnFdbk.MsgHdr msgHdr = mivs347.getIdVrfctnFdbk().getMsgHdr();
        MIVS_347_001_01_IdVrfctnFdbk.Fdbk fdbk = mivs347.getIdVrfctnFdbk().getFdbk();
        myLog.info(logger, "手机号码核查结果疑义反馈");
        //拼人行报文
        mivs347.getHeader().setOrigSender(bankNumber);
        mivs347.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        fdbk.setSysInd("MIIT");
        fdbk.getOrgnlVrfctn().setOrgnlDlvrgMsgId(reqBody.getOrgnlDlvrgMsgId());
        fdbk.getOrgnlVrfctn().setOrgnlRcvgMsgId(reqBody.getOrgnlRcvgMsgId());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setMobNb(reqBody.getMobNb());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setNm(reqBody.getNm());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setIdTp(reqBody.getIdTp());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setId(reqBody.getId());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setUniSocCdtCd(reqBody.getUniSocCdtCd());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setBizRegNb(reqBody.getBizRegNb());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setRslt(reqBody.getRslt());
        fdbk.setCntt(reqBody.getCntt());
        fdbk.setContactNb(reqBody.getContactNb());
        fdbk.setContactNm(reqBody.getContactNm());

        //信息落地入库
//        MivsIdVrfctnInfoModel idVrfctnInfoModel = new MivsIdVrfctnInfoModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        idVrfctnInfoModel.setPlat_date(req.getSysDate());
        idVrfctnInfoModel.setPlat_trace(req.getSysTraceno());
        idVrfctnInfoModel.setPlat_time(req.getSysTime());
        idVrfctnInfoModel.setSystem_id(req.getReqSysHead().getSystemId());
        idVrfctnInfoModel.setTran_date(req.getReqSysHead().getTranDate());
        idVrfctnInfoModel.setSeq_no(req.getReqSysHead().getSeqNo());
        idVrfctnInfoModel.setTran_time(req.getReqSysHead().getTranTimestamp());
        idVrfctnInfoModel.setUser_id(req.getReqSysHead().getUserId());
        idVrfctnInfoModel.setBranch_id(req.getReqSysHead().getBranchId());
        idVrfctnInfoModel.setMivs_sts("00");
        idVrfctnInfoModel.setMsg_id(msgHdr.getMsgId());
        idVrfctnInfoModel.setCre_dt_tm(msgHdr.getCreDtTm());
        idVrfctnInfoModel.setInstg_drct_pty(settlementBankNo);
        idVrfctnInfoModel.setDrct_pty_nm(lqtnBnkNmT1);
        idVrfctnInfoModel.setInstg_pty(bankNumber);
        idVrfctnInfoModel.setPty_nm(bnkNmT);
        idVrfctnInfoModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        idVrfctnInfoModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        idVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        idVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        idVrfctnInfoModel.setSys_ind(fdbk.getSysInd());
        idVrfctnInfoModel.setMob_nb(reqBody.getMobNb());
        idVrfctnInfoModel.setNm(reqBody.getNm());
        idVrfctnInfoModel.setId_tp(reqBody.getIdTp());
        idVrfctnInfoModel.setId(reqBody.getId());
        idVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        idVrfctnInfoModel.setBiz_reg_nb(reqBody.getBizRegNb());
        idVrfctnInfoModel.setRslt(reqBody.getRslt());
        idVrfctnInfoModel.setCntt(reqBody.getCntt());
        idVrfctnInfoModel.setContact_nm(reqBody.getContactNm());
        idVrfctnInfoModel.setContact_nb(reqBody.getContactNb());
        idVrfctnInfoModel.setRemark1(reqBody.getRemark1());
        idVrfctnInfoModel.setRemark2(reqBody.getRemark2());
        idVrfctnInfoModel.setRemark3(reqBody.getRemark3());

        mivsIdVrfctnInfoService.insertFdbk(idVrfctnInfoModel); //插入数据库业务数据
        myLog.info(logger,"idVrfctnInfoModel插入的表数据为：" + idVrfctnInfoModel.toString());

        mivs347 = (MIVS_347_001_01) pmtsService.sendToPmts(mivs347); // 发送请求，实时等待990

        String channel = "900_"+ mivs347.getIdVrfctnFdbk().getMsgHdr().getMsgId();//为同步等待900，组合三要素
        myLog.info(logger,"347报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);
        myLog.info(logger,"收到的报文类型" + dtoBase.getHead().getMesgType());

        //收到人行通讯回执，更新数据库状态
        MivsIdVrfctnInfoModel idVrfctnInfoModelUpdate = new MivsIdVrfctnInfoModel();
        //更新数据的主键赋值
        idVrfctnInfoModelUpdate.setPlat_date(req.getSysDate());
        idVrfctnInfoModelUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000203 rep = new REP_50023000203();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            idVrfctnInfoModelUpdate.setMivs_sts("02");
            idVrfctnInfoModelUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            idVrfctnInfoModelUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("ccms.900.001.02")){ //通用处理确认报文

            CCMS_900_001_02 ccms900 = (CCMS_900_001_02)dtoBase;
            CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf = ccms900.getCmonConf().getCmonConfInf();

            REP_50023000203.REP_BODY repBody = rep.getRepBody();

            //返回ESB报文
            repBody.setProcSts(cmonConfInf.getPrcSts());
            repBody.setProcCd(cmonConfInf.getPrcCd());
            repBody.setPtyId(cmonConfInf.getPtyId());
            repBody.setPtyPrcCd(cmonConfInf.getPtyPrcCd());
            repBody.setRjctInf(cmonConfInf.getRjctInf());
            repBody.setPrcDt(cmonConfInf.getPrcDt());
            repBody.setNetgRnd(cmonConfInf.getNetgRnd());

            //待更新数据库数据
            idVrfctnInfoModelUpdate.setMivs_sts("04");
            idVrfctnInfoModelUpdate.setRcv_msg_id(ccms900.getCmonConf().getGrpHdr().getMsgId());
            idVrfctnInfoModelUpdate.setRcv_cre_dt_tm(ccms900.getCmonConf().getGrpHdr().getCreDtTm());
            idVrfctnInfoModelUpdate.setProc_sts(cmonConfInf.getPrcSts());
            idVrfctnInfoModelUpdate.setProc_cd(cmonConfInf.getPrcCd());
            idVrfctnInfoModelUpdate.setPty_id(cmonConfInf.getPtyId());
            idVrfctnInfoModelUpdate.setPty_prc_cd(cmonConfInf.getPtyPrcCd());
            idVrfctnInfoModelUpdate.setRjct_inf(cmonConfInf.getRjctInf());
            idVrfctnInfoModelUpdate.setPrc_dt(cmonConfInf.getPrcDt());
            idVrfctnInfoModelUpdate.setNetg_rnd(cmonConfInf.getNetgRnd());
        }
        //更新业务数据表
        myLog.info(logger,"idVrfctnInfoModelUpdate更新的表数据为：" + idVrfctnInfoModelUpdate.toString());
        mivsIdVrfctnInfoService.updateFdbk(idVrfctnInfoModelUpdate);

        return rep;
    }
}
