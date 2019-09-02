package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000215;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000215;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_346_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.request.MIVS_345_001_01;
import com.fxbank.tpp.mivs.model.request.MIVS_345_001_01_GetSysSts;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 企业信息联网核查查业务受理时间查询
 * @Author: 王鹏
 * @Date: 2019/4/30 10:09
 */
@Service("REQ_50023000215")
public class GetSysSts extends TradeBase implements TradeExecutionStrategy {

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

        REQ_50023000215 req = (REQ_50023000215) dto;//接收ESB请求报文
        REQ_50023000215.REQ_BODY reqBody = req.getReqBody();

        MIVS_345_001_01 mivs345 = new MIVS_345_001_01(new MyLog(),dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        MIVS_345_001_01_GetSysSts.MsgHdr msgHdr = mivs345.getSysSts().getMsgHdr();

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
            myLog.error(logger, "企业信息联网核查查业务受理时间查询，机构号：" + branchId,e);
            throw e;
        }
        myLog.info(logger, "企业信息联网核查查业务受理时间查询，机构号：" + branchId + "，人行行号：" + bankNumber);

        //拼人行345报文
        mivs345.getHeader().setOrigSender(settlementBankNo);
        mivs345.getHeader().setOrigReceiver("0000");
        msgHdr.getInstgPty().setInstgDrctPty(settlementBankNo);
        msgHdr.getInstgPty().setInstgPty(bankNumber);
        msgHdr.getInstdPty().setInstdDrctPty("0000");
        msgHdr.getInstdPty().setInstdPty("0000");
        mivs345.getSysSts().getQueInf().setSysInd(isOrNotNull(reqBody.getSysInd(),"核查系统标识"));
        mivs345.getSysSts().getQueInf().setQueDt(dateToIsoDate(reqBody.getQueDt(),"查询日期","Y"));
        mivs345 = (MIVS_345_001_01) pmtsService.sendToPmts(mivs345); // 发送请求，实时等待990

        String msgid= mivs345.getHeader().getMesgID();    //为同步等待345，组合三要素
        String channel = "346_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        REP_50023000215 rep = new REP_50023000215();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.346.001.01")){
            MIVS_346_001_01 mivs346 = (MIVS_346_001_01)dtoBase;
            REP_50023000215.REP_BODY repBody = rep.getRepBody();
//            if(mivs346.getRtrSysSts().getRspsn().getOprlErr().getProcSts()!=null) {
//                MivsTradeExecuteException e = new MivsTradeExecuteException(mivs346.getRtrSysSts().getRspsn().getOprlErr().getProcCd(),mivs346.getRtrSysSts().getRspsn().getOprlErr().getRjctinf());
//                throw e;
//            }
            repBody.setOrgnlQueDt(mivs346.getRtrSysSts().getRplyInf().getOrgnlQueDt());
            repBody.setProcSts(mivs346.getRtrSysSts().getRplyInf().getProcSts());
            repBody.setProcCd(mivs346.getRtrSysSts().getRplyInf().getProcCd());
            repBody.setRjctInf(mivs346.getRtrSysSts().getRplyInf().getRjctInf());
            repBody.setSysInd(mivs346.getRtrSysSts().getRplyInf().getSvcInf().getSysInd());
            repBody.setSvcInd(mivs346.getRtrSysSts().getRplyInf().getSvcInf().getSvcInd());
            repBody.setSysOpTm(mivs346.getRtrSysSts().getRplyInf().getSvcInf().getSysOpTm());
            repBody.setSysClTm(mivs346.getRtrSysSts().getRplyInf().getSvcInf().getSysClTm());
        }

        return rep;
    }
}
