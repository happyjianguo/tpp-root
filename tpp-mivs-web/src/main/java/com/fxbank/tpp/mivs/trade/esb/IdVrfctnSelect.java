package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000202;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000202;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsIdVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 手机号联网核查结果查询
 * @Author: 王鹏
 * @Date: 2019/6/24 11:01
 */
@Service("REQ_50023000202")
public class IdVrfctnSelect extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsIdVrfctnInfoService mivsIdVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000202 req = (REQ_50023000202) dto;//接收ESB请求报文
        REQ_50023000202.REQ_BODY reqBody = req.getReqBody();

        //查询数据库数据
        MivsIdVrfctnInfoModel idVrfctnTableSelect =  new MivsIdVrfctnInfoModel();
        if(reqBody.getStartDt() != null && !reqBody.getStartDt().equals("")) {
            idVrfctnTableSelect.setStart_dt(Integer.parseInt(reqBody.getStartDt()));
        }
        if(reqBody.getEndDt() != null && !reqBody.getEndDt().equals("")) {
            idVrfctnTableSelect.setEnd_dt(Integer.parseInt(reqBody.getEndDt()));
        }
        if(reqBody.getOrigBranchId() != null && !reqBody.getOrigBranchId().equals("")) {
            idVrfctnTableSelect.setBranch_id(reqBody.getOrigBranchId());
        }
        if(reqBody.getOrigUserId() != null && !reqBody.getOrigUserId().equals("")) {
            idVrfctnTableSelect.setUser_id(reqBody.getOrigUserId());
        }
        if(reqBody.getOrgnlDlvrgMsgId() != null && !reqBody.getOrgnlDlvrgMsgId().equals("")) {
            idVrfctnTableSelect.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        }
        if(reqBody.getOrgnlRcvgMsgId() != null && !reqBody.getOrgnlRcvgMsgId().equals("")) {
            idVrfctnTableSelect.setOrig_rcv_msgid(reqBody.getOrgnlRcvgMsgId());
        }
        if(reqBody.getMobNb() != null && !reqBody.getMobNb().equals("")) {
            idVrfctnTableSelect.setMob_nb(reqBody.getMobNb());
        }
        if(reqBody.getNm() != null && !reqBody.getNm().equals("")) {
            idVrfctnTableSelect.setNm(reqBody.getNm());
        }
        if(reqBody.getIdTp() != null && !reqBody.getIdTp().equals("")) {
            idVrfctnTableSelect.setId_tp(reqBody.getIdTp());
        }
        if(reqBody.getId() != null && !reqBody.getId().equals("")) {
            idVrfctnTableSelect.setId(reqBody.getId());
        }
        if(reqBody.getUniSocCdtCd() != null && !reqBody.getUniSocCdtCd().equals("")) {
            idVrfctnTableSelect.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        }
        if(reqBody.getBizRegNb() != null && !reqBody.getBizRegNb().equals("")) {
            idVrfctnTableSelect.setBiz_reg_nb(reqBody.getBizRegNb());
        }

        List<MivsIdVrfctnInfoModel> idVrfctnInfoModels = mivsIdVrfctnInfoService.selectResult(idVrfctnTableSelect); //查询数据库业务数据
        if(idVrfctnInfoModels != null && !idVrfctnInfoModels.isEmpty()) {
            myLog.info(logger, "查询结果为：" + idVrfctnInfoModels.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000202 rep = new REP_50023000202();
        if(idVrfctnInfoModels != null && !idVrfctnInfoModels.isEmpty()) {
            List<REP_50023000202.resultList> resultArrayList = new ArrayList<REP_50023000202.resultList>();
            int i = 0;
            for (MivsIdVrfctnInfoModel Info : idVrfctnInfoModels) {
                REP_50023000202.resultList resultList = new REP_50023000202.resultList();
                resultList.setOrigTranDate(Info.getTran_date());
                resultList.setOrigSeqNo(Info.getSeq_no());
                resultList.setOrigTranTime(Info.getTran_time());
                resultList.setOrgnlDlvrgMsgId(Info.getOrig_dlv_msgid());
                resultList.setOrgnlRcvgMsgId(Info.getOrig_rcv_msgid());
                resultList.setOrigBranchId(Info.getBranch_id());
                resultList.setOrigUserId(Info.getUser_id());
                resultList.setMobNb(Info.getMob_nb());
                resultList.setNm(Info.getNm());
                resultList.setIdTp(Info.getId_tp());
                resultList.setId(Info.getId());
                resultList.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                resultList.setBizRegNb(Info.getBiz_reg_nb());
                resultList.setRslt(Info.getRslt());
                resultList.setMobCrr(Info.getMob_crr());
                resultList.setLocMobNb(Info.getLoc_mob_nb());
                resultList.setLocNmMobNb(Info.getLoc_nm_mob_nb());
                resultList.setCdTp(Info.getCd_tp());
                resultList.setSts(Info.getSts());
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
