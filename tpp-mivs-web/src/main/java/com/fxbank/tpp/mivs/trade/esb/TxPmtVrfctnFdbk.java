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
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000207;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_348_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_348_001_01_TxPmtVrfctnFdbk;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsTxPmtVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 纳税信息核查结果疑义反馈报文 mivs.348.001.01
 * @Author: 王鹏
 * @Date: 2019/6/24 10:15
 */
@Service("REQ_50023000207")
public class TxPmtVrfctnFdbk extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsTxPmtVrfctnInfoService mivsTxPmtVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000207 req = (REQ_50023000207) dto;//接收ESB请求报文
        REQ_50023000207.REQ_BODY reqBody = req.getReqBody();
        if(reqBody.getTxpyrIdNb() != null && !reqBody.getTxpyrIdNb().equals("") &&
                reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")){
            MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_00001","统一社会信用代码和纳税人识别号只能填写其一");
            throw e;
        }

        MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModel = new MivsTxpmtVrfctnInfoModel();
        txpmtVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        txpmtVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        MivsTxpmtVrfctnInfoModel infoModel = mivsTxPmtVrfctnInfoService.selectFdbk(txpmtVrfctnInfoModel);
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
        MIVS_348_001_01 mivs348 = new MIVS_348_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_348_001_01_TxPmtVrfctnFdbk.MsgHdr msgHdr = mivs348.getTxPmtVrfctnFdbk().getMsgHdr();
        MIVS_348_001_01_TxPmtVrfctnFdbk.Fdbk fdbk = mivs348.getTxPmtVrfctnFdbk().getFdbk();
        myLog.info(logger, "纳税信息核查结果疑义反馈");
        //拼人行报文
        mivs348.getHeader().setOrigSender(settlementBankNo);
        mivs348.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        fdbk.setSysInd("CSAT");
        fdbk.getOrgnlVrfctn().setOrgnlDlvrgMsgId(reqBody.getOrgnlDlvrgMsgId());
        fdbk.getOrgnlVrfctn().setOrgnlRcvgMsgId(reqBody.getOrgnlRcvgMsgId());
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setCoNm(reqBody.getCompanyName());
        if(reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")) {
            fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setUniSocCdtCd(reqBody.getUniSocCdtCd());
        }else if(reqBody.getTxpyrIdNb() != null && !reqBody.getTxpyrIdNb().equals("")) {
            fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setTxpyrIdNb(reqBody.getTxpyrIdNb());
        }
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setRslt(isOrNotNull(reqBody.getRslt(),"核查结果"));
        fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setDataResrcDt(dateToIsoDate(reqBody.getDataResrcDt(),"数据源日期","Y"));
        fdbk.setCntt(isOrNotNull(reqBody.getCntt(),"疑义反馈内容"));
        fdbk.setContactNb(isOrNotNull(reqBody.getContactNb(),"联系人电话"));
        fdbk.setContactNm(isOrNotNull(reqBody.getContactNm(),"联系人姓名"));
        if(reqBody.getTxpyrInfoArrayMsg() != null && !reqBody.getTxpyrInfoArrayMsg().isEmpty()){
            List<MIVS_348_001_01_TxPmtVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.TxpmtInf> txpmtInfList = new ArrayList<MIVS_348_001_01_TxPmtVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.TxpmtInf>();
            for(REQ_50023000207.txpyrInfoArray info:reqBody.getTxpyrInfoArrayMsg()){
                MIVS_348_001_01_TxPmtVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.TxpmtInf txpmtInf = new MIVS_348_001_01_TxPmtVrfctnFdbk.Fdbk.OrgnlVrfctn.OrgnlVrfctnInfo.TxpmtInf();
                txpmtInf.setTxAuthCd(info.getTxAuthCd());
                txpmtInf.setTxAuthNm(info.getTxAuthNm());
                txpmtInf.setTxpyrSts(info.getTxpyrSts());
                txpmtInfList.add(txpmtInf);
            }
            fdbk.getOrgnlVrfctn().getOrgnlVrfctnInfo().setTxpmtInf(txpmtInfList);
            txpmtVrfctnInfoModel.setTxpmt_inf(txpmtInfList.toString());
        }

        //信息落地入库
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        txpmtVrfctnInfoModel.setPlat_date(req.getSysDate());
        txpmtVrfctnInfoModel.setPlat_trace(req.getSysTraceno());
        txpmtVrfctnInfoModel.setPlat_time(req.getSysTime());
        txpmtVrfctnInfoModel.setSystem_id(req.getReqSysHead().getSystemId());
        txpmtVrfctnInfoModel.setTran_date(req.getReqSysHead().getTranDate());
        txpmtVrfctnInfoModel.setSeq_no(req.getReqSysHead().getSeqNo());
        txpmtVrfctnInfoModel.setTran_time(req.getReqSysHead().getTranTimestamp());
        txpmtVrfctnInfoModel.setUser_id(req.getReqSysHead().getUserId());
        txpmtVrfctnInfoModel.setBranch_id(req.getReqSysHead().getBranchId());
        txpmtVrfctnInfoModel.setMivs_sts("00");
        txpmtVrfctnInfoModel.setMsg_id(msgHdr.getMsgId());
        txpmtVrfctnInfoModel.setCre_dt_tm(msgHdr.getCreDtTm());
        txpmtVrfctnInfoModel.setInstg_drct_pty(settlementBankNo);
        txpmtVrfctnInfoModel.setDrct_pty_nm(lqtnBnkNmT1);
        txpmtVrfctnInfoModel.setInstg_pty(bankNumber);
        txpmtVrfctnInfoModel.setPty_nm(bnkNmT);
        txpmtVrfctnInfoModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        txpmtVrfctnInfoModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        txpmtVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        txpmtVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        txpmtVrfctnInfoModel.setSys_ind(fdbk.getSysInd());
        txpmtVrfctnInfoModel.setCo_nm(reqBody.getCompanyName());
        txpmtVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        txpmtVrfctnInfoModel.setTxpyr_id_nb(reqBody.getTxpyrIdNb());
        txpmtVrfctnInfoModel.setRslt(reqBody.getRslt());
        txpmtVrfctnInfoModel.setData_resrc_dt(reqBody.getDataResrcDt());
        txpmtVrfctnInfoModel.setCntt(reqBody.getCntt());
        txpmtVrfctnInfoModel.setContact_nm(reqBody.getContactNm());
        txpmtVrfctnInfoModel.setContact_nb(reqBody.getContactNb());

        mivsTxPmtVrfctnInfoService.insertFdbk(txpmtVrfctnInfoModel); //插入数据库业务数据
        myLog.info(logger,"txpmtVrfctnInfoModel插入的表数据为：" + txpmtVrfctnInfoModel.toString());

        mivs348 = (MIVS_348_001_01) pmtsService.sendToPmts(mivs348); // 发送请求，实时等待990

        String channel = "900_"+ mivs348.getTxPmtVrfctnFdbk().getMsgHdr().getMsgId();//为同步等待900，组合三要素
        myLog.info(logger,"348报文发送通道编号=[" + channel);
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);
        myLog.info(logger,"收到的报文类型" + dtoBase.getHead().getMesgType());

        //收到人行通讯回执，更新数据库状态
        MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModelUpdate = new MivsTxpmtVrfctnInfoModel();
        //更新数据的主键赋值
        txpmtVrfctnInfoModelUpdate.setPlat_date(req.getSysDate());
        txpmtVrfctnInfoModelUpdate.setPlat_trace(req.getSysTraceno());

        REP_50023000203 rep = new REP_50023000203();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            txpmtVrfctnInfoModelUpdate.setMivs_sts("02");
            txpmtVrfctnInfoModelUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            txpmtVrfctnInfoModelUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
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

            //待更新数据库数据
            txpmtVrfctnInfoModelUpdate.setMivs_sts("04");
            txpmtVrfctnInfoModelUpdate.setRcv_msg_id(ccms900.getCmonConf().getGrpHdr().getMsgId());
            txpmtVrfctnInfoModelUpdate.setRcv_cre_dt_tm(ccms900.getCmonConf().getGrpHdr().getCreDtTm());
            txpmtVrfctnInfoModelUpdate.setProc_sts(cmonConfInf.getPrcSts());
            txpmtVrfctnInfoModelUpdate.setProc_cd(cmonConfInf.getPrcCd());
            txpmtVrfctnInfoModelUpdate.setPty_id(cmonConfInf.getPtyId());
            txpmtVrfctnInfoModelUpdate.setPty_prc_cd(cmonConfInf.getPtyPrcCd());
            txpmtVrfctnInfoModelUpdate.setRjct_inf(cmonConfInf.getRjctInf());
            txpmtVrfctnInfoModelUpdate.setPrc_dt(cmonConfInf.getPrcDt());
            txpmtVrfctnInfoModelUpdate.setNetg_rnd(cmonConfInf.getNetgRnd());
        }
        //更新业务数据表
        myLog.info(logger,"txpmtVrfctnInfoModelUpdate更新的表数据为：" + txpmtVrfctnInfoModelUpdate.toString());
        mivsTxPmtVrfctnInfoService.updateFdbk(txpmtVrfctnInfoModelUpdate);

        return rep;
    }
}
