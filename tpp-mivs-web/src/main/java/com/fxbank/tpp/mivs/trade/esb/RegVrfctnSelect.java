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

        //查询数据落库
        MivsRegVrfctnInfoModel regVrfctnInfoModel =  new MivsRegVrfctnInfoModel();
        regVrfctnInfoModel.setStart_dt(reqBody.getStartDt());
        regVrfctnInfoModel.setEnd_dt(reqBody.getEndDt());
        regVrfctnInfoModel.setBranch_id(reqBody.getOrigBranchId());
        regVrfctnInfoModel.setUser_id(reqBody.getOrigUserId());
        regVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        regVrfctnInfoModel.setEnt_nm(reqBody.getEntNm());
        regVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        regVrfctnInfoModel.setNm_of_lgl_prsn(reqBody.getNmOfLglPrsn());
        regVrfctnInfoModel.setId_of_lgl_prsn(reqBody.getIdOfLglPrsn());
        regVrfctnInfoModel.setTra_nm(reqBody.getTraNm());
        regVrfctnInfoModel.setNm(reqBody.getNm());
        regVrfctnInfoModel.setId(reqBody.getId());

        List<MivsRegVrfctnInfoModel> regVrfctnInfoModels = mivsRegVrfctnInfoService.selectResult(regVrfctnInfoModel); //查询数据库业务数据
        myLog.info(logger,"查询结果为：" + regVrfctnInfoModels.toString());
        if(regVrfctnInfoModels == null || regVrfctnInfoModels.isEmpty()) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
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
                resultList.setRslt(infoModel.getRslt());
                resultList.setProcSts(infoModel.getProc_sts());
                resultList.setProcCd(infoModel.getProc_cd());
                resultList.setRjctinf(infoModel.getRjct_inf());
                resultList.setRemarks1(infoModel.getRemark1());
                resultList.setRemarks2(infoModel.getRemark2());
                resultList.setRemarks3(infoModel.getRemark3());
                //赋BasInfo数据
                List<REP_50023000209.BasInfoEnt> basInfoEntArrayMsg = new ArrayList<REP_50023000209.BasInfoEnt>();
                List<REP_50023000209.BasInfOfSlfEplydPpl> basInfOfSlfEplydPplArrayMsg = new ArrayList<REP_50023000209.BasInfOfSlfEplydPpl>();
                if(infoModel.getBasInfo() != null && !infoModel.getBasInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.BasInfo Info:infoModel.getBasInfo()) {
                        if(Info.getMarket_type().equals("ENT")) {
                            REP_50023000209.BasInfoEnt basInfoArMsg = new REP_50023000209.BasInfoEnt();
                            basInfoArMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                            basInfoArMsg.setPgNb(Info.getPg_nb());
                            basInfoArMsg.setEntNm(Info.getEnt_nm());
                            basInfoArMsg.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                            basInfoArMsg.setCoTp(Info.getCo_tp());
                            basInfoArMsg.setDom(Info.getDom());
                            basInfoArMsg.setRegCptl(Info.getReg_cptl());
                            basInfoArMsg.setDtEst(Info.getDt_est());
                            basInfoArMsg.setOpPrdFrom(Info.getOp_prd_from());
                            basInfoArMsg.setOpPrdTo(Info.getOp_prd_to());
                            basInfoArMsg.setRegSts(Info.getReg_sts());
                            basInfoArMsg.setNmOfLglPrsn(Info.getNm_of_lgl_prsn());
                            basInfoArMsg.setRegAuth(Info.getReg_auth());
                            basInfoArMsg.setBizScp(Info.getBiz_scp());
                            basInfoArMsg.setDtAppr(Info.getDt_appr());
                            basInfoEntArrayMsg.add(basInfoArMsg);
                        }else if(Info.getMarket_type().equals("TRA")){
                            REP_50023000209.BasInfOfSlfEplydPpl basInfoArMsg = new REP_50023000209.BasInfOfSlfEplydPpl();
                            basInfoArMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                            basInfoArMsg.setPgNb(Info.getPg_nb());
                            basInfoArMsg.setTraNm(Info.getTra_nm());
                            basInfoArMsg.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                            basInfoArMsg.setCoTp(Info.getCo_tp());
                            basInfoArMsg.setOpLoc(Info.getOp_loc());
                            basInfoArMsg.setFdAmt(Info.getFd_amt());
                            basInfoArMsg.setDtReg(Info.getDt_reg());
                            basInfoArMsg.setRegSts(Info.getReg_sts());
                            basInfoArMsg.setNm(Info.getNm());
                            basInfoArMsg.setRegAuth(Info.getReg_auth());
                            basInfoArMsg.setBizScp(Info.getBiz_scp());
                            basInfoArMsg.setDtAppr(Info.getDt_appr());
                            basInfOfSlfEplydPplArrayMsg.add(basInfoArMsg);
                        }
                    }
                    resultList.setBasInfoEntList(basInfoEntArrayMsg);
                    myLog.debug(logger, "repBody.basInfoEntArrayMsg = " + basInfoEntArrayMsg.toString());
                    resultList.setBasInfOfSlfEplydPplList(basInfOfSlfEplydPplArrayMsg);
                    myLog.debug(logger, "repBody.basInfOfSlfEplydPplArrayMsg = " + basInfOfSlfEplydPplArrayMsg.toString());
                }
                //赋CoShrhdrFndInfo数据
                List<REP_50023000209.CoShrhdrFndInfo> coShrhdrFndInfoArrayMsg = new ArrayList<REP_50023000209.CoShrhdrFndInfo>();
                if(infoModel.getCoShrhdrFndInfo() != null && !infoModel.getCoShrhdrFndInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.CoShrhdrFndInfo Info:infoModel.getCoShrhdrFndInfo()) {
                        REP_50023000209.CoShrhdrFndInfo coShrhdrFndInfoMsg = new REP_50023000209.CoShrhdrFndInfo();
                        coShrhdrFndInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        coShrhdrFndInfoMsg.setPgNb(Info.getPg_nb());
                        coShrhdrFndInfoMsg.setCoShrhdrfndInfoNb(Info.getCo_shrhdrfnd_info_nb());
                        coShrhdrFndInfoMsg.setInvtrNm(Info.getInvtr_nm());
                        coShrhdrFndInfoMsg.setInvtrId(Info.getInvtr_id());
                        coShrhdrFndInfoMsg.setSubscrCptlConAmt(Info.getSubscr_cptl_con_amt());
                        coShrhdrFndInfoMsg.setActlCptlConAmt(Info.getActl_cptl_con_amt());
                        coShrhdrFndInfoMsg.setSubscrCptlConFm(Info.getSubscr_cptl_con_fm());
                        coShrhdrFndInfoMsg.setSubscrCptlConDt(Info.getSubscr_cptl_con_dt());
                        coShrhdrFndInfoArrayMsg.add(coShrhdrFndInfoMsg);
                    }
                    //插入ESB应答报文CoShrhdrFndInfo
                    myLog.debug(logger, "repBody.coShrhdrFndInfoArrayMsg = " + coShrhdrFndInfoArrayMsg.toString());
                    resultList.setCoShrhdrFndInfoList(coShrhdrFndInfoArrayMsg);
                }
                //赋DirSupSrMgrInfo数据
                List<REP_50023000209.DirSupSrMgrInfo> dirSupSrMgrInfoArrayMsg = new ArrayList<REP_50023000209.DirSupSrMgrInfo>();
                if(infoModel.getDirSupSrMgrInfo() != null && !infoModel.getDirSupSrMgrInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.DirSupSrMgrInfo Info:infoModel.getDirSupSrMgrInfo()) {
                        REP_50023000209.DirSupSrMgrInfo dirSupSrMgrInfoMsg = new REP_50023000209.DirSupSrMgrInfo();
                        dirSupSrMgrInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        dirSupSrMgrInfoMsg.setPgNb(Info.getPg_nb());
                        dirSupSrMgrInfoMsg.setDirSupSrMgrInfoNb(Info.getDir_supsrsgr_info_nb());
                        dirSupSrMgrInfoMsg.setNm(Info.getNm());
                        dirSupSrMgrInfoMsg.setPosn(Info.getPosn());
                        dirSupSrMgrInfoArrayMsg.add(dirSupSrMgrInfoMsg);
                    }
                    //插入ESB应答报文DirSupSrMgrInfoList
                    myLog.debug(logger, "repBody.dirSupSrMgrInfoArrayMsg = " + dirSupSrMgrInfoArrayMsg.toString());
                    resultList.setDirSupSrMgrInfoList(dirSupSrMgrInfoArrayMsg);
                }
                //赋ChngInfo数据
                List<REP_50023000209.ChngInfo> chngInfoArrayMsg = new ArrayList<REP_50023000209.ChngInfo>();
                if(infoModel.getChngInfo() != null && !infoModel.getChngInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.ChngInfo Info:infoModel.getChngInfo()) {
                        REP_50023000209.ChngInfo chngInfoMsg = new REP_50023000209.ChngInfo();
                        chngInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        chngInfoMsg.setPgNb(Info.getPg_nb());
                        chngInfoMsg.setChngInfoNb(Info.getChng_info_nb());
                        chngInfoMsg.setChngItm(Info.getChng_itm());
                        chngInfoMsg.setBfChng(Info.getBf_chng());
                        chngInfoMsg.setAftChng(Info.getAft_chng());
                        chngInfoMsg.setDtOfChng(Info.getDt_of_chng());
                        chngInfoArrayMsg.add(chngInfoMsg);
                    }
                    //插入ESB应答报文chngInfoArrayMsg
                    myLog.debug(logger, "repBody.chngInfoArrayMsg = " + chngInfoArrayMsg.toString());
                    resultList.setChngInfoList(chngInfoArrayMsg);
                }
                //赋AbnmlBizInfo数据
                List<REP_50023000209.AbnmlBizInfo> abnInfoArrayMsg = new ArrayList<REP_50023000209.AbnmlBizInfo>();
                if(infoModel.getAbnmlBizInfo() != null && !infoModel.getAbnmlBizInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.AbnmlBizInfo Info:infoModel.getAbnmlBizInfo()) {
                        REP_50023000209.AbnmlBizInfo abnInfoMsg = new REP_50023000209.AbnmlBizInfo();
                        abnInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        abnInfoMsg.setPgNb(Info.getPg_nb());
                        abnInfoMsg.setAbnInfoNb(Info.getAbn_info_nb());
                        abnInfoMsg.setAbnmlCause(Info.getAbnml_cause());
                        abnInfoMsg.setAbnmlDate(Info.getAbnml_date());
                        abnInfoMsg.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                        abnInfoMsg.setRmvCause(Info.getRmv_cause());
                        abnInfoMsg.setRmvDate(Info.getRmv_date());
                        abnInfoMsg.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                        abnInfoArrayMsg.add(abnInfoMsg);
                    }
                    //插入ESB应答报文abnInfoArrayMsg
                    myLog.debug(logger, "repBody.abnInfoArrayMsg = " + abnInfoArrayMsg.toString());
                    resultList.setAbnmlBizInfoList(abnInfoArrayMsg);
                }
                //赋IllDscrtInfo数据
                List<REP_50023000209.IllDscrtInfo> illInfoArrayMsg = new ArrayList<REP_50023000209.IllDscrtInfo>();
                if(infoModel.getIllDscrtInfo() != null && !infoModel.getIllDscrtInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.IllDscrtInfo Info:infoModel.getIllDscrtInfo()) {
                        REP_50023000209.IllDscrtInfo illInfoMsg = new REP_50023000209.IllDscrtInfo();
                        illInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        illInfoMsg.setPgNb(Info.getPg_nb());
                        illInfoMsg.setIllInfoNb(Info.getIll_info_nb());
                        illInfoMsg.setIllDscrtCause(Info.getIll_dscrt_cause());
                        illInfoMsg.setAbnmlDate(Info.getAbnml_date());
                        illInfoMsg.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                        illInfoMsg.setRmvCause(Info.getRmv_cause());
                        illInfoMsg.setRmvDate(Info.getRmv_date());
                        illInfoMsg.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                        illInfoArrayMsg.add(illInfoMsg);
                    }
                    //插入ESB应答报文illInfoArrayMsg
                    myLog.debug(logger, "repBody.illInfoArrayMsg = " + illInfoArrayMsg.toString());
                    resultList.setIllDscrtInfoList(illInfoArrayMsg);
                }
                //赋LicNull数据
                List<REP_50023000209.LicNull> licNullArrayMsg = new ArrayList<REP_50023000209.LicNull>();
                if(infoModel.getLicInfo() != null && !infoModel.getLicInfo().isEmpty()) {
                    for (MivsRegVrfctnInfoModel.LicInfo Info:infoModel.getLicInfo()) {
                        REP_50023000209.LicNull licNullMsg = new REP_50023000209.LicNull();
                        licNullMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        licNullMsg.setPgNb(Info.getPg_nb());
                        licNullMsg.setLicInfoNb(Info.getPlat_time());
                        licNullMsg.setOrgnlOrCp(Info.getOrgnl_or_cp());
                        licNullMsg.setLicNullStmCntt(Info.getLic_null_stm_cntt());
                        licNullMsg.setLicNullStmDt(Info.getLic_null_stm_dt());
                        licNullMsg.setRplSts(Info.getRpl_sts());
                        licNullMsg.setRplDt(Info.getRpl_dt());
                        licNullMsg.setLicCpNb(Info.getLic_cp_nb());
                        licNullArrayMsg.add(licNullMsg);
                    }
                    //插入ESB应答报文licNullArrayMsg
                    myLog.debug(logger, "repBody.licNullArrayMsg = " + licNullArrayMsg.toString());
                    resultList.setLicNullList(licNullArrayMsg);
                }
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}
