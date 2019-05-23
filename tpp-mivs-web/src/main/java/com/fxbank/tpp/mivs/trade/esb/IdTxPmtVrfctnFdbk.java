package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000207;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000207;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.request.MIVS_347_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_348_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:  手机号码/纳税信息核查结果疑义反馈报文 mivs.347.001.01
 * @Author: 王鹏
 * @Date: 2019/5/5 8:09
 */
@Service("REQ_50023000207")
public class IdTxPmtVrfctnFdbk extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000207 req = (REQ_50023000207) dto;//接收ESB请求报文
        REQ_50023000207.REQ_BODY reqBody = req.getReqBody();

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
        //判断手机号查询或纳税信息查询
        String channel = null;
        String msgid = null;
        String sysind = reqBody.getSysInd();
        if(sysind.equals("MIIT")){
            MIVS_347_001_01 mivsIdTxPmt = new MIVS_347_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
            myLog.info(logger, "SysInd是这个：" + sysind + "，手机号码核查结果疑义反馈");
            //发起行行号
            mivsIdTxPmt.getHeader().setOrigSender(bankNumber);
            mivsIdTxPmt.getHeader().setOrigReceiver("0000");
            mivsIdTxPmt.getIdVrfctnFdbk().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
            mivsIdTxPmt.getIdVrfctnFdbk().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().setSysInd(reqBody.getSysInd());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().setOrgnlDlvrgMsgId(reqBody.getOrgnlDlvrgMsgId());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().setOrgnlRcvgMsgId(reqBody.getOrgnlRcvgMsgId());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setMobNb(reqBody.getMobNb());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setNm(reqBody.getNm());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setIdTp(reqBody.getIdTp());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setId(reqBody.getId());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setUniSocCdtCd(reqBody.getUniSocCdtCd());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setBizRegNb(reqBody.getBizRegNb());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().getOrgnlVrfctn().getOrgnlVrfctnInfo().setRslt(reqBody.getRslt());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().setCntt(reqBody.getCntt());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().setContactNb(reqBody.getContactNb());
            mivsIdTxPmt.getIdVrfctnFdbk().getFdbk().setContactNm(reqBody.getContactNm());

            mivsIdTxPmt = (MIVS_347_001_01) pmtsService.sendToPmts(mivsIdTxPmt); // 发送请求，实时等待990
            msgid= mivsIdTxPmt.getIdVrfctnFdbk().getMsgHdr().getMsgId();    //为同步等待900，组合三要素

        }else if(sysind.equals("CSAT")){
            MIVS_348_001_01 mivsIdTxPmt = new MIVS_348_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
            myLog.info(logger, "SysInd是这个：" + sysind + "，纳税信息核查结果疑义反馈");
            //发起行行号
            mivsIdTxPmt.getHeader().setOrigSender(bankNumber);
            mivsIdTxPmt.getHeader().setOrigReceiver("0000");
            mivsIdTxPmt.getTxPmtVrfctnFdbk().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
            mivsIdTxPmt.getTxPmtVrfctnFdbk().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
            mivsIdTxPmt.getTxPmtVrfctnFdbk().getMsgHdr().getInstgPty().setInstgPty(bankNumber);
            mivsIdTxPmt.getTxPmtVrfctnFdbk().getMsgHdr().getInstgPty().setPtyNm(bnkNmT);

            mivsIdTxPmt.getTxPmtVrfctnFdbk().getFdbk().setSysInd(reqBody.getSysInd());

            mivsIdTxPmt = (MIVS_348_001_01) pmtsService.sendToPmts(mivsIdTxPmt); // 发送请求，实时等待990
            msgid= mivsIdTxPmt.getTxPmtVrfctnFdbk().getMsgHdr().getMsgId();    //为同步等待900，组合三要素
        }

        channel = "900_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        REP_50023000207 rep = new REP_50023000207();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.900.001.01")){ //通用处理确认报文
            CCMS_900_001_02 ccms900 = (CCMS_900_001_02)dtoBase;
            REP_50023000207.REP_BODY repBody = rep.getRepBody();
//            if(ccms900.getCmonConf().getCmonConf().getPrcSts()!=null) {
//                MivsTradeExecuteException e = new MivsTradeExecuteException(ccms900.getCmonConf().getRspsn().getOprlErr().getProcCd(),mivsIdTxPmt.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getRjctinf());
//                throw e;
//            }
            repBody.setProcSts(ccms900.getCmonConf().getCmonConf().getPrcSts());
            repBody.setProcCd(ccms900.getCmonConf().getCmonConf().getRjctInf());
        }

        return rep;
    }
}
