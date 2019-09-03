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

        //查询数据
        MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModel =  new MivsTxpmtVrfctnInfoModel();
        if(reqBody.getStartDt() != null && !reqBody.getStartDt().equals("")) {
            txpmtVrfctnInfoModel.setStart_dt(Integer.parseInt(reqBody.getStartDt()));
        }
        if(reqBody.getEndDt() != null && !reqBody.getEndDt().equals("")) {
            txpmtVrfctnInfoModel.setEnd_dt(Integer.parseInt(reqBody.getEndDt()));
        }
        txpmtVrfctnInfoModel.setBranch_id(isOrNotNull(reqBody.getOrigBranchId(),"原核查人行机构号", "N"));
        txpmtVrfctnInfoModel.setUser_id(isOrNotNull(reqBody.getOrigUserId(),"原核查柜员号", "N"));
        txpmtVrfctnInfoModel.setOrig_dlv_msgid(isOrNotNull(reqBody.getOrgnlDlvrgMsgId(),"原请求报文编号","N"));
        txpmtVrfctnInfoModel.setOrig_rcv_msgid(isOrNotNull(reqBody.getOrgnlRcvgMsgId(),"原应答报文编号","N"));
        txpmtVrfctnInfoModel.setCo_nm(isOrNotNull(reqBody.getCompanyName(),"单位名称","N"));
        txpmtVrfctnInfoModel.setUni_soc_cdt_cd(isOrNotNull(reqBody.getUniSocCdtCd(),"统一社会信用代码","N"));
        txpmtVrfctnInfoModel.setTxpyr_id_nb(isOrNotNull(reqBody.getTaxPayerId(),"纳税人识别号 ","N"));
        txpmtVrfctnInfoModel.setDetail_flag("NO");//不查询明细数据

        List<MivsTxpmtVrfctnInfoModel> txpmtVrfctnInfoModels = mivsTxPmtVrfctnInfoService.selectResult(txpmtVrfctnInfoModel); //查询数据库业务数据
        if(txpmtVrfctnInfoModels != null && !txpmtVrfctnInfoModels.isEmpty()) {
            myLog.info(logger, "查询结果为：" + txpmtVrfctnInfoModels.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }
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
                resultList.setOrigInstgPty(Info.getOrig_instg_pty());
                resultList.setOrigBranchId(Info.getBranch_id());
                resultList.setOrigUserId(Info.getUser_id());
                resultList.setCoNm(Info.getCo_nm());
                resultList.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                resultList.setTxpyrIdNb(Info.getTxpyr_id_nb());
                resultList.setRslt(Info.getRslt());
                resultList.setDataResrcDt(Info.getData_resrc_dt());
                resultList.setTxpmtInfCnt(Info.getTxpmt_inf_cnt());
                resultList.setProcSts(Info.getProc_sts());
                resultList.setProcCd(Info.getProc_cd());
                resultList.setRjctinf(Info.getRjct_inf());
                resultList.setRemarks1(Info.getRemark1());
                resultList.setRemarks2(Info.getRemark2());
                resultList.setRemarks3(Info.getRemark3());
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}