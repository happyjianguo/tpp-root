package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000206;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000206;
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
 * @Description: 纳税信息核查结果明细查询
 * @Author: 王鹏
 * @Date: 2019/7/11 7:17
 */
@Service("REQ_50023000206")
public class TxPmtVrfctnSelectDetail extends TradeBase implements TradeExecutionStrategy {

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

        REQ_50023000206 req = (REQ_50023000206) dto;//接收ESB请求报文
        REQ_50023000206.REQ_BODY reqBody = req.getReqBody();

        //查询数据落库
        MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModel =  new MivsTxpmtVrfctnInfoModel();
        txpmtVrfctnInfoModel.setTran_date(isOrNotNull(reqBody.getOrigTranDate(),"原核查日期","Y"));
        txpmtVrfctnInfoModel.setSeq_no(isOrNotNull(reqBody.getOrigSeqNo(),"原核查流水号","N"));
        txpmtVrfctnInfoModel.setOrig_dlv_msgid(isOrNotNull(reqBody.getOrgnlDlvrgMsgId(),"原申请报文标识号","N"));
        txpmtVrfctnInfoModel.setOrig_rcv_msgid(isOrNotNull(reqBody.getOrgnlRcvgMsgId(),"原应答报文标识号","N"));
        txpmtVrfctnInfoModel.setOrig_instg_pty(isOrNotNull(reqBody.getOrigInstgPty(),"原发起参与机构（人行行号）","N"));
        txpmtVrfctnInfoModel.setDetail_flag("YES");

        //查询明细数据
        txpmtVrfctnInfoModel = mivsTxPmtVrfctnInfoService.selectMasterAndAttached(txpmtVrfctnInfoModel); //查询数据库业务数据
        if(txpmtVrfctnInfoModel != null) {
            myLog.info(logger, "查询结果为：" + txpmtVrfctnInfoModel.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000206 rep = new REP_50023000206();
        REP_50023000206.REP_BODY repBody = rep.getRepBody();
        if(txpmtVrfctnInfoModel != null) {
            repBody.setCoNm(txpmtVrfctnInfoModel.getCo_nm());
            repBody.setUniSocCdtCd(txpmtVrfctnInfoModel.getUni_soc_cdt_cd());
            repBody.setTxpyrIdNb(txpmtVrfctnInfoModel.getTxpyr_id_nb());
            repBody.setRslt(txpmtVrfctnInfoModel.getRslt());
            repBody.setDataResrcDt(txpmtVrfctnInfoModel.getData_resrc_dt());
            if(txpmtVrfctnInfoModel.getTxpmtInfList() != null && !txpmtVrfctnInfoModel.getTxpmtInfList().isEmpty()) {
                List<REP_50023000206.REP_BODY.TXPYR_INFO_ARRAY> txpyrInfoArrayList = new ArrayList<REP_50023000206.REP_BODY.TXPYR_INFO_ARRAY>();
                for (MivsTxpmtVrfctnInfoModel.TxpmtInf txpInfo : txpmtVrfctnInfoModel.getTxpmtInfList()) {
                    REP_50023000206.REP_BODY.TXPYR_INFO_ARRAY txpyrInfoArray = new REP_50023000206.REP_BODY.TXPYR_INFO_ARRAY();
                    txpyrInfoArray.setTxpmtInfNb(txpInfo.getTxpmt_inf_nb());
                    txpyrInfoArray.setTxAuthCd(txpInfo.getTx_auth_cd());
                    txpyrInfoArray.setTxAuthNm(txpInfo.getTx_auth_nm());
                    txpyrInfoArray.setTxpyrSts(txpInfo.getTxpyr_sts());
                    txpyrInfoArrayList.add(txpyrInfoArray);
                }
                repBody.setTxpmtInf(txpyrInfoArrayList);
            }
        }
        return rep;
    }
}