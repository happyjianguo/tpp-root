package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000210;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000210;
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
 * @Description: 登记信息核查结果明细查询
 * @Author: 王鹏
 * @Date: 2019/7/11 7:19
 */
@Service("REQ_50023000210")
public class RegVrfctnSelectDetail extends TradeBase implements TradeExecutionStrategy {

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

        REQ_50023000210 req = (REQ_50023000210) dto;//接收ESB请求报文
        REQ_50023000210.REQ_BODY reqBody = req.getReqBody();

        //查询数据落库
        MivsRegVrfctnInfoModel regVrfctnInfoModel =  new MivsRegVrfctnInfoModel();
        if(reqBody.getOrigTranDate() != null && !reqBody.getOrigTranDate().equals("")) {
            regVrfctnInfoModel.setTran_date(reqBody.getOrigTranDate());
        }
        if(reqBody.getOrigSeqNo() != null && !reqBody.getOrigSeqNo().equals("")) {
            regVrfctnInfoModel.setSeq_no(reqBody.getOrigSeqNo());
        }
        if(reqBody.getOrgnlDlvrgMsgId() != null && !reqBody.getOrgnlDlvrgMsgId().equals("")) {
            regVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        }
        if(reqBody.getOrigInstgPty() != null && !reqBody.getOrigInstgPty().equals("")) {
            regVrfctnInfoModel.setOrig_instg_pty(reqBody.getOrigInstgPty());
        }
        regVrfctnInfoModel.setDetail_flag("YES");
        //查询明细数据
        regVrfctnInfoModel = mivsRegVrfctnInfoService.selectMasterAndAttached(regVrfctnInfoModel); //查询数据库业务数据
        if(regVrfctnInfoModel != null) {
            myLog.info(logger, "查询结果为：" + regVrfctnInfoModel.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000210 rep = new REP_50023000210();
        if(regVrfctnInfoModel != null) {
            //赋BasInfo数据
            List<REP_50023000210.BasInfoEnt> basInfoEntArrayMsg = new ArrayList<REP_50023000210.BasInfoEnt>();
            List<REP_50023000210.BasInfOfSlfEplydPpl> basInfOfSlfEplydPplArrayMsg = new ArrayList<REP_50023000210.BasInfOfSlfEplydPpl>();
            if(regVrfctnInfoModel.getBasInfo() != null && !regVrfctnInfoModel.getBasInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.BasInfo Info:regVrfctnInfoModel.getBasInfo()) {
                    if(Info.getMarket_type().equals("ENT")) {
                        REP_50023000210.BasInfoEnt basInfoArMsg = new REP_50023000210.BasInfoEnt();
                        basInfoArMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        basInfoArMsg.setMarketType("ENT");
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
                        REP_50023000210.BasInfOfSlfEplydPpl basInfoArMsg = new REP_50023000210.BasInfOfSlfEplydPpl();
                        basInfoArMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                        basInfoArMsg.setMarketType("TRA");
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
                rep.getRepBody().setBasInfoEntList(basInfoEntArrayMsg);
                myLog.debug(logger, "repBody.basInfoEntArrayMsg = " + basInfoEntArrayMsg.toString());
                rep.getRepBody().setBasInfOfSlfEplydPplList(basInfOfSlfEplydPplArrayMsg);
                myLog.debug(logger, "repBody.basInfOfSlfEplydPplArrayMsg = " + basInfOfSlfEplydPplArrayMsg.toString());
            }
            //赋CoShrhdrFndInfo数据
            List<REP_50023000210.CoShrhdrFndInfo> coShrhdrFndInfoArrayMsg = new ArrayList<REP_50023000210.CoShrhdrFndInfo>();
            if(regVrfctnInfoModel.getCoShrhdrFndInfo() != null && !regVrfctnInfoModel.getCoShrhdrFndInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.CoShrhdrFndInfo Info:regVrfctnInfoModel.getCoShrhdrFndInfo()) {
                    REP_50023000210.CoShrhdrFndInfo coShrhdrFndInfoMsg = new REP_50023000210.CoShrhdrFndInfo();
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
                rep.getRepBody().setCoShrhdrFndInfoList(coShrhdrFndInfoArrayMsg);
            }
            //赋DirSupSrMgrInfo数据
            List<REP_50023000210.DirSupSrMgrInfo> dirSupSrMgrInfoArrayMsg = new ArrayList<REP_50023000210.DirSupSrMgrInfo>();
            if(regVrfctnInfoModel.getDirSupSrMgrInfo() != null && !regVrfctnInfoModel.getDirSupSrMgrInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.DirSupSrMgrInfo Info:regVrfctnInfoModel.getDirSupSrMgrInfo()) {
                    REP_50023000210.DirSupSrMgrInfo dirSupSrMgrInfoMsg = new REP_50023000210.DirSupSrMgrInfo();
                    dirSupSrMgrInfoMsg.setOrgnlRcvgMsgId(Info.getMsg_id());
                    dirSupSrMgrInfoMsg.setPgNb(Info.getPg_nb());
                    dirSupSrMgrInfoMsg.setDirSupSrMgrInfoNb(Info.getDir_supsrsgr_info_nb());
                    dirSupSrMgrInfoMsg.setNm(Info.getNm());
                    dirSupSrMgrInfoMsg.setPosn(Info.getPosn());
                    dirSupSrMgrInfoArrayMsg.add(dirSupSrMgrInfoMsg);
                }
                //插入ESB应答报文DirSupSrMgrInfoList
                myLog.debug(logger, "repBody.dirSupSrMgrInfoArrayMsg = " + dirSupSrMgrInfoArrayMsg.toString());
                rep.getRepBody().setDirSupSrMgrInfoList(dirSupSrMgrInfoArrayMsg);
            }
            //赋ChngInfo数据
            List<REP_50023000210.ChngInfo> chngInfoArrayMsg = new ArrayList<REP_50023000210.ChngInfo>();
            if(regVrfctnInfoModel.getChngInfo() != null && !regVrfctnInfoModel.getChngInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.ChngInfo Info:regVrfctnInfoModel.getChngInfo()) {
                    REP_50023000210.ChngInfo chngInfoMsg = new REP_50023000210.ChngInfo();
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
                rep.getRepBody().setChngInfoList(chngInfoArrayMsg);
            }
            //赋AbnmlBizInfo数据
            List<REP_50023000210.AbnmlBizInfo> abnInfoArrayMsg = new ArrayList<REP_50023000210.AbnmlBizInfo>();
            if(regVrfctnInfoModel.getAbnmlBizInfo() != null && !regVrfctnInfoModel.getAbnmlBizInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.AbnmlBizInfo Info:regVrfctnInfoModel.getAbnmlBizInfo()) {
                    REP_50023000210.AbnmlBizInfo abnInfoMsg = new REP_50023000210.AbnmlBizInfo();
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
                rep.getRepBody().setAbnmlBizInfoList(abnInfoArrayMsg);
            }
            //赋IllDscrtInfo数据
            List<REP_50023000210.IllDscrtInfo> illInfoArrayMsg = new ArrayList<REP_50023000210.IllDscrtInfo>();
            if(regVrfctnInfoModel.getIllDscrtInfo() != null && !regVrfctnInfoModel.getIllDscrtInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.IllDscrtInfo Info:regVrfctnInfoModel.getIllDscrtInfo()) {
                    REP_50023000210.IllDscrtInfo illInfoMsg = new REP_50023000210.IllDscrtInfo();
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
                rep.getRepBody().setIllDscrtInfoList(illInfoArrayMsg);
            }
            //赋LicNull数据
            List<REP_50023000210.LicNull> licNullArrayMsg = new ArrayList<REP_50023000210.LicNull>();
            if(regVrfctnInfoModel.getLicInfo() != null && !regVrfctnInfoModel.getLicInfo().isEmpty()) {
                for (MivsRegVrfctnInfoModel.LicInfo Info:regVrfctnInfoModel.getLicInfo()) {
                    REP_50023000210.LicNull licNullMsg = new REP_50023000210.LicNull();
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
                rep.getRepBody().setLicNullList(licNullArrayMsg);
            }
        }
        return rep;
    }
}
