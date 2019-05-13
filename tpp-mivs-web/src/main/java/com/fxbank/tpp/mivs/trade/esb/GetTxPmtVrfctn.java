package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_30041000902;
import com.fxbank.tpp.mivs.dto.esb.REQ_30041000902;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_323_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.request.MIVS_322_001_01;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 纳税信息联网核查
 * @Author: 王鹏
 * @Date: 2019/4/29 10:53
 */
@Service("REQ_30041000902")
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

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_30041000902 req = (REQ_30041000902) dto;//接收ESB请求报文
        REQ_30041000902.REQ_BODY reqBody = req.getReqBody();

        MIVS_322_001_01 mivs322 = new MIVS_322_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());

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

        //发起行行号
        mivs322.getHeader().setOrigSender(bankNumber);
        mivs322.getHeader().setOrigReceiver("0000");
        mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
        mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().setInstgPty(bankNumber);
        mivs322.getTxPmtVrfctn().getMsgHdr().getInstgPty().setPtyNm(bnkNmT);

        mivs322.getTxPmtVrfctn().getVryDef().setCompanyName(reqBody.getCompanyName());
        mivs322.getTxPmtVrfctn().getVryDef().setUniSocCdtCd(reqBody.getUniSocCdtCd());
        mivs322.getTxPmtVrfctn().getVryDef().setTaxPayerId(reqBody.getTaxPayerId());
        mivs322.getTxPmtVrfctn().getVryDef().setOpNm(reqBody.getOpNm());

        mivs322 = (MIVS_322_001_01) pmtsService.sendToPmts(mivs322); // 发送请求，实时等待990

        String msgid= mivs322.getTxPmtVrfctn().getMsgHdr().getMsgId();    //为同步等待323，组合三要素
        String channel = "323_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        REP_30041000902 rep = new REP_30041000902();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.323.001.01")){
            MIVS_323_001_01 mivs323 = (MIVS_323_001_01)dtoBase;
            REP_30041000902.REP_BODY repBody = rep.getRepBody();
            if(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcSts()!=null) {
                MivsTradeExecuteException e = new MivsTradeExecuteException(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcCd(),mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getRjctinf());
                throw e;
            }
            repBody.setRslt(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getRslt());
            repBody.setDataResrcD(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getDataResrcDt());
            repBody.setTxAuthCd(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxAuthCd());
            repBody.setTxAuthNm(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxAuthNm());
            repBody.setTxpyrSts(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxpySts());
            repBody.setProcSts(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcSts());
            repBody.setProcCd(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcCd());
            repBody.setRjctinf(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getRjctinf());
        }

        return rep;
    }
}
