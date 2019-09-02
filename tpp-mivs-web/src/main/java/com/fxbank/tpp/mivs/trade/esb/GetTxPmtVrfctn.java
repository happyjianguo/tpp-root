package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000204;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000204;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_323_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.request.MIVS_322_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_322_001_01_GetTxPmtVrfctn;
import com.fxbank.tpp.mivs.model.response.MIVS_323_001_01_RtrTxPmtVrfctn;
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
 * @Description: 纳税信息联网核查
 * @Author: 王鹏
 * @Date: 2019/4/29 10:53
 */
@Service("REQ_50023000204")
public class GetTxPmtVrfctn extends TradeBase implements TradeExecutionStrategy {

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
    private IMivsTxPmtVrfctnInfoService mivsTxpmtvfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000204 req = (REQ_50023000204) dto;//接收ESB请求报文
        REQ_50023000204.REQ_BODY reqBody = req.getReqBody();
        if(reqBody.getTxPayerId() != null && !reqBody.getTxPayerId().equals("") &&
                reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")){
            MivsTradeExecuteException e = new MivsTradeExecuteException("MIVS_E_00001","统一社会信用代码和纳税人识别号只能填写其一");
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

        //拼发送人行322报文
        MIVS_322_001_01 mivs322 = new MIVS_322_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_322_001_01_GetTxPmtVrfctn.MsgHdr msgHdr = mivs322.getGetTxPmtVrfctn().getMsgHdr();
        MIVS_322_001_01_GetTxPmtVrfctn.VryDef vryDef = mivs322.getGetTxPmtVrfctn().getVryDef();
        mivs322.getHeader().setOrigSender(settlementBankNo);
        mivs322.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstgPty().setPtyNm(bnkNmT);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        vryDef.setCoNm(isOrNotNull(reqBody.getCompanyName(),"单位名称"));
        if(reqBody.getTxPayerId() != null && !reqBody.getTxPayerId().equals("")) {
            vryDef.setTxpyrIdNb(reqBody.getTxPayerId());
        }else if(reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")) {
            vryDef.setUniSocCdtCd(reqBody.getUniSocCdtCd());
        }
        vryDef.setOpNm(isOrNotNull(reqBody.getOpNm(),"操作员姓名"));

        //发送人行请求报文落地
        MivsTxpmtVrfctnInfoModel txpmtvfctnInfoTableInsert = new MivsTxpmtVrfctnInfoModel();
        myLog.info(logger, "Date = " + req.getSysDate());
        myLog.info(logger, "trace = " + req.getSysTraceno());
        myLog.info(logger, "SystemId = " + req.getReqSysHead().getSystemId());
        myLog.info(logger, "TranDate = " + req.getReqSysHead().getTranDate());
        myLog.info(logger, "SeqNo = " + req.getReqSysHead().getSeqNo());
        myLog.info(logger, "TranTimestamp = " + req.getReqSysHead().getTranTimestamp());
        txpmtvfctnInfoTableInsert.setPlat_date(req.getSysDate());
        txpmtvfctnInfoTableInsert.setPlat_trace(req.getSysTraceno());
        txpmtvfctnInfoTableInsert.setPlat_time(req.getSysTime());
        txpmtvfctnInfoTableInsert.setSystem_id(req.getReqSysHead().getSystemId());
        txpmtvfctnInfoTableInsert.setTran_date(req.getReqSysHead().getTranDate());
        txpmtvfctnInfoTableInsert.setSeq_no(req.getReqSysHead().getSeqNo());
        txpmtvfctnInfoTableInsert.setTran_time(req.getReqSysHead().getTranTimestamp());
        txpmtvfctnInfoTableInsert.setUser_id(req.getReqSysHead().getUserId());
        txpmtvfctnInfoTableInsert.setBranch_id(req.getReqSysHead().getBranchId());
        txpmtvfctnInfoTableInsert.setMivs_sts("00");
        txpmtvfctnInfoTableInsert.setMsg_id(msgHdr.getMsgId());
        txpmtvfctnInfoTableInsert.setCre_dt_tm(msgHdr.getCreDtTm());
        txpmtvfctnInfoTableInsert.setInstg_drct_pty(settlementBankNo);
        txpmtvfctnInfoTableInsert.setDrct_pty_nm(lqtnBnkNmT1);
        txpmtvfctnInfoTableInsert.setInstg_pty(bankNumber);
        txpmtvfctnInfoTableInsert.setPty_nm(bnkNmT);
        txpmtvfctnInfoTableInsert.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        txpmtvfctnInfoTableInsert.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        txpmtvfctnInfoTableInsert.setCo_nm(reqBody.getCompanyName());
        txpmtvfctnInfoTableInsert.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        txpmtvfctnInfoTableInsert.setTxpyr_id_nb(reqBody.getTxPayerId());
        txpmtvfctnInfoTableInsert.setOp_nm(reqBody.getOpNm());

        mivsTxpmtvfctnInfoService.insertMaster(txpmtvfctnInfoTableInsert); //插入数据库业务数据

        mivs322 = (MIVS_322_001_01) pmtsService.sendToPmts(mivs322); // 发送请求，实时等待990

        String msgid= mivs322.getHeader().getMesgID();    //为同步等待323，组合三要素
        String channel = "323_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        //收到人行通讯回执，准备更新数据库状态
        REP_50023000204 rep = new REP_50023000204();
        REP_50023000204.REP_BODY repBody = rep.getRepBody();
        MivsTxpmtVrfctnInfoModel txpmtvfctnInfoTableUpdate = new MivsTxpmtVrfctnInfoModel();
        //更新数据的主键赋值
        txpmtvfctnInfoTableUpdate.setPlat_date(req.getSysDate());
        txpmtvfctnInfoTableUpdate.setPlat_trace(req.getSysTraceno());

        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccms911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            //根据人行返回报文更新数据库状态
            txpmtvfctnInfoTableUpdate.setMivs_sts("02");
            txpmtvfctnInfoTableUpdate.setProc_cd(ccms911.getDscrdMsgNtfctn().getDscrdInf().getPrcCd());
            txpmtvfctnInfoTableUpdate.setRjct_inf(ccms911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            //更新业务数据表
            mivsTxpmtvfctnInfoService.uMasterAndiAttached(txpmtvfctnInfoTableUpdate);
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.323.001.01")){
            MIVS_323_001_01 mivs323 = (MIVS_323_001_01)dtoBase;
            MIVS_323_001_01_RtrTxPmtVrfctn.OrgnlBizQry orgnlBizQry = mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry();
            MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf();
            MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.OprlErr oprlErr = mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr();
            if(oprlErr.getProcSts()!=null) {
                MivsTradeExecuteException e = new MivsTradeExecuteException(oprlErr.getProcCd(),oprlErr.getRjctinf());
                txpmtvfctnInfoTableUpdate.setMivs_sts("03");
                txpmtvfctnInfoTableUpdate.setProc_cd(oprlErr.getProcCd());
                txpmtvfctnInfoTableUpdate.setProc_sts(oprlErr.getProcSts());
                txpmtvfctnInfoTableUpdate.setRjct_inf(oprlErr.getRjctinf());
                //更新业务数据表
                mivsTxpmtvfctnInfoService.uMasterAndiAttached(txpmtvfctnInfoTableUpdate);
                throw e;
            }
            //查询数据库主表条数数据+附表内容数据，以323应答报文的“原报文标识号，原发起机构”为查询条件
            MivsTxpmtVrfctnInfoModel infoModel = new MivsTxpmtVrfctnInfoModel();
            infoModel.setOrig_dlv_msgid(orgnlBizQry.getMsgId());
            infoModel.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
            infoModel.setDetail_flag("YES");//查询明细数据
            infoModel = mivsTxpmtvfctnInfoService.selectMasterAndAttached(infoModel);
            myLog.debug(logger, "###infoModel :" + infoModel.toString());
            //赋循环数据
            List<REP_50023000204.TXPYR_INFO_ARRAY> arrayMsg = new ArrayList<REP_50023000204.TXPYR_INFO_ARRAY>();
            if(infoModel.getTxpmtInfList() != null && !infoModel.getTxpmtInfList().isEmpty()) {
                for (MivsTxpmtVrfctnInfoModel.TxpmtInf Info:infoModel.getTxpmtInfList()) {
                    REP_50023000204.TXPYR_INFO_ARRAY txpyrInfoArray = new REP_50023000204.TXPYR_INFO_ARRAY();
                    //赋值纳税核查信息附表数据
                    txpyrInfoArray.setTxpmtInfNb(Info.getTxpmt_inf_nb());
                    txpyrInfoArray.setTxAuthCd(Info.getTx_auth_cd());
                    txpyrInfoArray.setTxAuthNm(Info.getTx_auth_nm());
                    txpyrInfoArray.setTxpyrSts(Info.getTxpyr_sts());
                    arrayMsg.add(txpyrInfoArray);
                }
                repBody.setArrayMsg(arrayMsg);
            }
            repBody.setRslt(vrfctnInf.getRslt());
            repBody.setDataResrcD(vrfctnInf.getDataResrcDt());
            //待更新数据库数据
            txpmtvfctnInfoTableUpdate.setMivs_sts("04");
            txpmtvfctnInfoTableUpdate.setDetail_flag("NO");
        }

        //更新业务数据表
        mivsTxpmtvfctnInfoService.uMasterAndiAttached(txpmtvfctnInfoTableUpdate);

        myLog.info(logger,"Json  " + JsonUtil.toJson(rep));
        return rep;
    }
}
