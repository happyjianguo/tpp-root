package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000206;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000206;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业联网核查结果查询请求
 * @Author: 王鹏
 * @Date: 2019/5/15 14:34
 */
@Service("REQ_50023000206")
public class SelRslt extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

//    @Reference(version = "1.0.0")
//    private IForwardToPmtsService pmtsService;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000206 req = (REQ_50023000206) dto;//接收ESB请求报文
        REQ_50023000206.REQ_BODY reqBody = req.getReqBody();

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

        REP_50023000206 rep = new REP_50023000206();
//        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
//            CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
//            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
//            throw e;
//        }else if(dtoBase.getHead().getMesgType().equals("mivs.323.001.01")){
//            MIVS_323_001_01 mivs323 = (MIVS_323_001_01)dtoBase;
//            REP_50023000204.REP_BODY repBody = rep.getRepBody();
//            if(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcSts()!=null) {
//                MivsTradeExecuteException e = new MivsTradeExecuteException(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcCd(),mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getRjctinf());
//                throw e;
//            }
//
//            repBody.setTxAuthCd(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxAuthCd());
//            repBody.setTxAuthNm(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxAuthNm());
//            repBody.setTxpyrSts(mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf().getTxpmtInf().getTxpySts());
//            repBody.setProcSts(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcSts());
//            repBody.setProcCd(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getProcCd());
//            repBody.setRjctinf(mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr().getRjctinf());
//        }

        return rep;
    }
}
