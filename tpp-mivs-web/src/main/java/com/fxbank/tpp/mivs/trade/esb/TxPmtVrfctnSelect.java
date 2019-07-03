package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000205;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000205;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsTxPmtVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 纳税信息联网核查结果查询
 * @Author: 王鹏
 * @Date: 2019/7/1 10:28
 */
@Service("REQ_50023000205")
public class TxPmtVrfctnSelect extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsTxPmtVrfctnInfoService mivsTxPmtVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000205 req = (REQ_50023000205) dto;//接收ESB请求报文
        REQ_50023000205.REQ_BODY reqBody = req.getReqBody();

        //查询数据落库
        MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModel =  new MivsTxpmtVrfctnInfoModel();
        txpmtVrfctnInfoModel.setStart_dt(reqBody.getStartDt());
        txpmtVrfctnInfoModel.setEnd_dt(reqBody.getEndDt());
        txpmtVrfctnInfoModel.setBranch_id(reqBody.getOrigBranchId());
        txpmtVrfctnInfoModel.setUser_id(reqBody.getOrigUserId());
        txpmtVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        txpmtVrfctnInfoModel.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        txpmtVrfctnInfoModel.setCo_nm(reqBody.getCompanyName());
        txpmtVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        txpmtVrfctnInfoModel.setTxpyr_id_nb(reqBody.getTaxPayerId());

        List<MivsTxpmtVrfctnInfoModel> txpmtVrfctnInfoModels = mivsTxPmtVrfctnInfoService.selectResult(txpmtVrfctnInfoModel); //查询数据库业务数据
        myLog.info(logger,"查询结果为：" + txpmtVrfctnInfoModels.toString());
        if(txpmtVrfctnInfoModels == null || txpmtVrfctnInfoModels.isEmpty()) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000205 rep = new REP_50023000205();
        if(txpmtVrfctnInfoModels != null && !txpmtVrfctnInfoModels.isEmpty()) {
            List<REP_50023000205.resultList> resultArrayList = new ArrayList<REP_50023000205.resultList>();
            int i = 0;
            for (MivsTxpmtVrfctnInfoModel Info : txpmtVrfctnInfoModels) {
                REP_50023000205.resultList resultList = new REP_50023000205.resultList();
                resultList.setOrigTranDate(Info.getTran_date());
                resultList.setOrigSeqNo(Info.getSeq_no());
                resultList.setOrigTranTime(Info.getTran_time());
                resultList.setOrgnlDlvrgMsgId(Info.getOrig_dlv_msgid());
                resultList.setOrgnlRcvgMsgId(Info.getOrig_rcv_msgid());
                resultList.setCoNm(Info.getCo_nm());
                resultList.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                resultList.setTxpyrIdNb(Info.getTxpyr_id_nb());
                resultList.setRslt(Info.getRslt());
                resultList.setDataResrcDt(Info.getData_resrc_dt());
                resultList.setProcSts(Info.getProc_sts());
                resultList.setProcCd(Info.getProc_cd());
                resultList.setRjctinf(Info.getRjct_inf());
                resultList.setRemarks1(Info.getRemark1());
                resultList.setRemarks2(Info.getRemark2());
                resultList.setRemarks3(Info.getRemark3());
                if(Info.getTxpmtInfList() != null && !Info.getTxpmtInfList().isEmpty()) {
                    List<REP_50023000205.resultList.TXPYR_INFO_ARRAY> txpyrInfoArrayList = new ArrayList<REP_50023000205.resultList.TXPYR_INFO_ARRAY>();
                    for (MivsTxpmtVrfctnInfoModel.TxpmtInf txpInfo : Info.getTxpmtInfList()) {
                        REP_50023000205.resultList.TXPYR_INFO_ARRAY txpyrInfoArray = new REP_50023000205.resultList.TXPYR_INFO_ARRAY();
                        txpyrInfoArray.setTxAuthCd(txpInfo.getTx_auth_cd());
                        txpyrInfoArray.setTxAuthNm(txpInfo.getTx_auth_nm());
                        txpyrInfoArray.setTxpyrSts(txpInfo.getTxpyr_sts());
                        txpyrInfoArrayList.add(txpyrInfoArray);
                    }
                    resultList.setTxpmtInf(txpyrInfoArrayList);
                }
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}