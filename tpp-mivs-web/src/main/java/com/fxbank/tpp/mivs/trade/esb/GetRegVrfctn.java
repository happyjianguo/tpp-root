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
import com.fxbank.tpp.mivs.dto.esb.REP_50023000203;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000203;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_325_001_01;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.request.MIVS_324_001_01;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
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
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/20 15:28
 */
@Service("REQ_50023000203")
public class GetRegVrfctn extends TradeBase implements TradeExecutionStrategy {

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

        REQ_50023000203 req = (REQ_50023000203) dto;//接收ESB请求报文
        REQ_50023000203.REQ_BODY reqBody = req.getReqBody();

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

        MIVS_324_001_01 mivs324 = new MIVS_324_001_01(new MyLog(), dto.getSysDate(),dto.getSysTime(), dto.getSysTraceno());
        //发起行行号
        mivs324.getHeader().setOrigSender(bankNumber);
        mivs324.getHeader().setOrigReceiver("0000");
        mivs324.getRegVrfctn().getMsgHdr().getInstgPty().setInstgDrctPty(settlementBankNo);
        mivs324.getRegVrfctn().getMsgHdr().getInstgPty().setDrctPtyNm(lqtnBnkNmT1);
        mivs324.getRegVrfctn().getMsgHdr().getInstgPty().setInstgPty(bankNumber);
        mivs324.getRegVrfctn().getMsgHdr().getInstgPty().setPtyNm(bnkNmT);

        mivs324.getRegVrfctn().getVryDef().setCompanyName(reqBody.getCompanyName());
        mivs324.getRegVrfctn().getVryDef().setUniSocCdtCd(reqBody.getUniSocCdtCd());
        mivs324.getRegVrfctn().getVryDef().setTaxPayerId(reqBody.getTaxPayerId());
        mivs324.getRegVrfctn().getVryDef().setOpNm(reqBody.getOpNm());

        //发送人行请求报文落地


        mivs324 = (MIVS_324_001_01) pmtsService.sendToPmts(mivs324); // 发送请求，实时等待990

        String msgid= mivs324.getRegVrfctn().getMsgHdr().getMsgId();    //为同步等待323，组合三要素
        String channel = "323_"+msgid;
        DTO_BASE dtoBase = syncCom.get(myLog, channel, super.queryTimeout911(myLog), TimeUnit.SECONDS);

        //收到人行通讯回执，更新数据库状态

        REP_50023000203 rep = new REP_50023000203();
        if(dtoBase.getHead().getMesgType().equals("ccms.911.001.02")){  //根据911组织应答报文
            CCMS_911_001_02 ccmc911 = (CCMS_911_001_02)dtoBase;
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10002,ccmc911.getDscrdMsgNtfctn().getDscrdInf().getRjctInf());

            //根据人行返回报文更新数据库状态
            throw e;
        }else if(dtoBase.getHead().getMesgType().equals("mivs.325.001.01")){
            MIVS_325_001_01 mivs325 = (MIVS_325_001_01)dtoBase;
            REP_50023000203.REP_BODY repBody = rep.getRepBody();
            if(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getProcSts()!=null) {
                MivsTradeExecuteException e = new MivsTradeExecuteException(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getProcCd(),mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getRjctinf());
                throw e;
            }
            //取循环数据
            List<MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.TxpmtInf> TxpmtInf = mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getTxpmtInf();
            //赋循环数据
            List<REP_50023000203.TXPYR_INFO_ARRAY> arrayMsg = new ArrayList<REP_50023000203.TXPYR_INFO_ARRAY>();
            if(TxpmtInf != null && !TxpmtInf.isEmpty()) {
                for (MIVS_325_001_01_RtrRegVrfctn.Rspsn.VrfctnInf.TxpmtInf Info:TxpmtInf) {
                    REP_50023000203.TXPYR_INFO_ARRAY arMsg = new REP_50023000203.TXPYR_INFO_ARRAY();
                    arMsg.setTxAuthCd(Info.getTxAuthCd());
                    arMsg.setTxAuthNm(Info.getTxAuthNm());
                    arMsg.setTxpyrSts(Info.getTxpySts());
                    arrayMsg.add(arMsg);
                }
            }
            repBody.setRslt(mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getRslt());
            repBody.setDataResrcD(mivs325.getRtrRegVrfctn().getRspsn().getVrfctnInf().getDataResrcDt());
            if(arrayMsg !=null && !arrayMsg.isEmpty()){
                repBody.setArrayMsg(arrayMsg);
            }
            repBody.setProcSts(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getProcSts());
            repBody.setProcCd(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getProcCd());
            repBody.setRjctinf(mivs325.getRtrRegVrfctn().getRspsn().getOprlErr().getRjctinf());

            //根据人行返回报文更新数据库状态

        }
        myLog.info(logger,"Json  " + JsonUtil.toJson(rep));
        return rep;
    }
}
