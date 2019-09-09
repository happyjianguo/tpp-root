package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000209;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000209;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登记信息核查结果汇总查询
 * @Author: 王鹏
 * @Date: 2019/7/2 15:38
 */
@Service("REQ_50023000209")
public class RegVrfctnSelect extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsRegVrfctnInfoService mivsRegVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000209 req = (REQ_50023000209) dto;//接收ESB请求报文
        REQ_50023000209.REQ_BODY reqBody = req.getReqBody();

        //查询数据
        MivsRegVrfctnInfoModel regVrfctnInfoModel =  new MivsRegVrfctnInfoModel();
        if(reqBody.getStartDt() != null && !reqBody.getStartDt().equals("")) {
            regVrfctnInfoModel.setStart_dt(Integer.parseInt(reqBody.getStartDt()));
        }
        if(reqBody.getEndDt() != null && !reqBody.getEndDt().equals("")) {
            regVrfctnInfoModel.setEnd_dt(Integer.parseInt(reqBody.getEndDt()));
        }
        regVrfctnInfoModel.setBranch_id(isOrNotNull(reqBody.getOrigBranchId(),"原核查人行机构号", "N"));
        regVrfctnInfoModel.setUser_id(isOrNotNull(reqBody.getOrigUserId(),"原核查柜员号", "N"));
        regVrfctnInfoModel.setOrig_dlv_msgid(isOrNotNull(reqBody.getOrgnlDlvrgMsgId(),"原申请报文标识号", "N"));
        regVrfctnInfoModel.setEnt_nm(isOrNotNull(reqBody.getEntNm(),"单位名称", "N"));
        regVrfctnInfoModel.setUni_soc_cdt_cd(isOrNotNull(reqBody.getUniSocCdtCd(),"统一社会信用代码", "N"));
        regVrfctnInfoModel.setNm_of_lgl_prsn(isOrNotNull(reqBody.getNmOfLglPrsn(),"法定代表人或单位负责人姓名", "N"));
        regVrfctnInfoModel.setId_of_lgl_prsn(isOrNotNull(reqBody.getIdOfLglPrsn(), "法定代表人或单位负责人身份证件号", "N"));
        regVrfctnInfoModel.setTra_nm(isOrNotNull(reqBody.getTraNm(), "字号名称", "N"));
        regVrfctnInfoModel.setNm(isOrNotNull(reqBody.getNm(), "经营者姓名", "N"));
        regVrfctnInfoModel.setId(isOrNotNull(reqBody.getId(), "经营者证件号", "N"));


        List<MivsRegVrfctnInfoModel> regVrfctnInfoModels = mivsRegVrfctnInfoService.selectResult(regVrfctnInfoModel); //查询数据库业务数据
        if(regVrfctnInfoModels != null && !regVrfctnInfoModels.isEmpty()) {
            myLog.info(logger, "查询结果为：" + regVrfctnInfoModels.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "查询无记录");
            throw e;
        }

        if(regVrfctnInfoModels == null || regVrfctnInfoModels.isEmpty()) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "查询无记录");
            throw e;
        }

        REP_50023000209 rep = new REP_50023000209();
        if(regVrfctnInfoModels != null && !regVrfctnInfoModels.isEmpty()) {
            List<REP_50023000209.resultList> resultArrayList = new ArrayList<REP_50023000209.resultList>();
            int i = 0;
            for (MivsRegVrfctnInfoModel infoModel : regVrfctnInfoModels) {
                REP_50023000209.resultList resultList = new REP_50023000209.resultList();
                resultList.setOrigTranDate(infoModel.getTran_date());
                resultList.setOrigSeqNo(infoModel.getSeq_no());
                resultList.setOrigTranTime(infoModel.getTran_time());
                resultList.setOrgnlDlvrgMsgId(infoModel.getOrig_dlv_msgid());
                resultList.setOrigInstgPty(infoModel.getOrig_instg_pty());
                resultList.setOrigBranchId(infoModel.getBranch_id());
                resultList.setOrigUserId(infoModel.getUser_id());
                resultList.setMarketType(infoModel.getMarket_type());
                resultList.setEntNm(infoModel.getEnt_nm());
                resultList.setNmOfLglPrsn(infoModel.getNm_of_lgl_prsn());
                resultList.setIdOfLglPrsn(infoModel.getId_of_lgl_prsn());
                resultList.setTraNm(infoModel.getTra_nm());
                resultList.setNm(infoModel.getNm());
                resultList.setId(infoModel.getId());
                resultList.setUniSocDdtCd(infoModel.getUni_soc_cdt_cd());
                resultList.setAgtNm(infoModel.getAgt_nm());
                resultList.setAgtId(infoModel.getAgt_id());
                resultList.setRslt(infoModel.getRslt());
                resultList.setDataResrcDt(infoModel.getData_resrc_dt());
                resultList.setBasInfoCnt(String.valueOf(isOrNotNull(String.valueOf(infoModel.getBas_info_cnt()),"","N")));
                resultList.setCoShrhdrfndInfoCnt(isOrNotNull(String.valueOf(infoModel.getCo_shrhdrfnd_info_cnt()),"","N"));
                resultList.setDirSupsrsgrInfoCnt(isOrNotNull(String.valueOf(infoModel.getDir_supsrsgr_info_cnt()),"","N"));
                resultList.setChngInfoCnt(isOrNotNull(String.valueOf(infoModel.getChng_info_cnt()),"","N"));
                resultList.setAbnmlBizInfoCnt(isOrNotNull(String.valueOf(infoModel.getAbnml_biz_info_cnt()),"","N"));
                resultList.setIllDscrtInfoCnt(isOrNotNull(String.valueOf(infoModel.getIll_dscrt_info_cnt()),"","N"));
                resultList.setLicNullCnt(isOrNotNull(String.valueOf(infoModel.getLic_null_cnt()),"","N"));
                resultList.setProcSts(infoModel.getProc_sts());
                resultList.setProcCd(infoModel.getProc_cd());
                resultList.setRjctinf(infoModel.getRjct_inf());
                resultList.setRemarks1(infoModel.getRemark1());
                resultList.setRemarks2(infoModel.getRemark2());
                resultList.setRemarks3(infoModel.getRemark3());
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}
