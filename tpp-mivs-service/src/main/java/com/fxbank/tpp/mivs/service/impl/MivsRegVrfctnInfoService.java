package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.RegVrfctn.*;
import com.fxbank.tpp.mivs.mapper.RegVrfctn.*;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登记信息核查业务表操作
 * @Author: 王鹏
 * @Date: 2019/6/3 10:43
 */
@Service(version = "1.0.0")
public class MivsRegVrfctnInfoService implements IMivsRegVrfctnInfoService {

    @Resource
    private MivsRegvrfctnInfoEntityMapper infoMapper;
    @Resource
    private MivsRegvrfctnBasInfoEntityMapper basInfoEntityMapper;
    @Resource
    private MivsRegvrfctnCosInfoEntityMapper cosInfoEntityMapper;
    @Resource
    private MivsRegvrfctnDirInfoEntityMapper dirInfoEntityMapper;
    @Resource
    private MivsRegvrfctnChngInfoEntityMapper chngInfoEntityMapper;
    @Resource
    private MivsRegvrfctnAbnInfoEntityMapper abnInfoEntityMapper;
    @Resource
    private MivsRegvrfctnIllInfoEntityMapper illInfoEntityMapper;
    @Resource
    private MivsRegvrfctnLicInfoEntityMapper licInfoEntityMapper;

    @Override
    public void insertMaster(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel) {
        MivsRegvrfctnInfoEntity info = new MivsRegvrfctnInfoEntity();
        info.setPlatDate(mivsRegVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsRegVrfctnInfoModel.getPlat_trace());
        info.setPlatTime(mivsRegVrfctnInfoModel.getPlat_time());
        info.setSystemId(mivsRegVrfctnInfoModel.getSystem_id());
        info.setTranDate(mivsRegVrfctnInfoModel.getTran_date());
        info.setSeqNo(mivsRegVrfctnInfoModel.getSeq_no());
        info.setTranTime(mivsRegVrfctnInfoModel.getTran_time());
        info.setUserId(mivsRegVrfctnInfoModel.getUser_id());
        info.setBranchId(mivsRegVrfctnInfoModel.getBranch_id());
        info.setMivsSts(mivsRegVrfctnInfoModel.getMivs_sts());
        info.setMsgId(mivsRegVrfctnInfoModel.getMsg_id());
        info.setCreDtTm(mivsRegVrfctnInfoModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsRegVrfctnInfoModel.getInstg_drct_pty());
        info.setDrctPtyNm(mivsRegVrfctnInfoModel.getDrct_pty_nm());
        info.setInstgPty(mivsRegVrfctnInfoModel.getInstg_pty());
        info.setPtyNm(mivsRegVrfctnInfoModel.getPty_nm());
        info.setInstdDrctPty(mivsRegVrfctnInfoModel.getInstd_drct_pty());
        info.setInstdPty(mivsRegVrfctnInfoModel.getInstd_pty());
        info.setMarketType(mivsRegVrfctnInfoModel.getMarket_type());
        info.setEntNm(mivsRegVrfctnInfoModel.getEnt_nm());
        info.setUniSocCdtCd(mivsRegVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setNmOfLglPrsn(mivsRegVrfctnInfoModel.getNm_of_lgl_prsn());
        info.setIdOfLglPrsn(mivsRegVrfctnInfoModel.getId_of_lgl_prsn());
        info.setTraNm(mivsRegVrfctnInfoModel.getTra_nm());
        info.setNm(mivsRegVrfctnInfoModel.getNm());
        info.setId(mivsRegVrfctnInfoModel.getId());
        info.setAgtNm(mivsRegVrfctnInfoModel.getAgt_nm());
        info.setAgtId(mivsRegVrfctnInfoModel.getAgt_id());
        info.setOpNm(mivsRegVrfctnInfoModel.getOp_nm());

        infoMapper.insertSelective(info);
    }

    @Override
    public void uMasterAndiAttached(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel, String flag){
        MivsRegvrfctnInfoEntity info = new MivsRegvrfctnInfoEntity();
        info.setPlatDate(mivsRegVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsRegVrfctnInfoModel.getPlat_trace());
        info.setMivsSts(mivsRegVrfctnInfoModel.getMivs_sts());
        info.setProcCd(mivsRegVrfctnInfoModel.getProc_cd());
        info.setProcSts(mivsRegVrfctnInfoModel.getProc_sts());
        info.setRjctInf(mivsRegVrfctnInfoModel.getRjct_inf());
        info.setPgNb(mivsRegVrfctnInfoModel.getPg_nb());
        info.setLastPgInd(mivsRegVrfctnInfoModel.getLast_pg_ind());
        info.setRslt(mivsRegVrfctnInfoModel.getRslt());
        info.setDataResrcDt(mivsRegVrfctnInfoModel.getData_resrc_dt());
        info.setBasInfoCnt(mivsRegVrfctnInfoModel.getBas_info_cnt());
        info.setCoShrhdrfndInfoCnt(mivsRegVrfctnInfoModel.getCo_shrhdrfnd_info_cnt());
        info.setDirSupsrsgrInfoCnt(mivsRegVrfctnInfoModel.getDir_supsrsgr_info_cnt());
        info.setChngInfoCnt(mivsRegVrfctnInfoModel.getChng_info_cnt());
        info.setAbnmlBizInfoCnt(mivsRegVrfctnInfoModel.getAbnml_biz_info_cnt());
        info.setIllDscrtInfoCnt(mivsRegVrfctnInfoModel.getIll_dscrt_info_cnt());
        info.setLicNullCnt(mivsRegVrfctnInfoModel.getLic_null_cnt());
        info.setProcCd(mivsRegVrfctnInfoModel.getProc_cd());
        info.setProcSts(mivsRegVrfctnInfoModel.getProc_sts());
        info.setRjctInf(mivsRegVrfctnInfoModel.getRjct_inf());
        infoMapper.updateByPrimaryKeySelective(info);

        if(flag.equals("all")) {
            //插入BasInfo附表
            if (mivsRegVrfctnInfoModel.getBasInfo() != null && !mivsRegVrfctnInfoModel.getBasInfo().isEmpty()) {
                MivsRegvrfctnBasInfoEntity basInfoEntity = new MivsRegvrfctnBasInfoEntity();
                for (MivsRegVrfctnInfoModel.BasInfo Info : mivsRegVrfctnInfoModel.getBasInfo()) {
                    basInfoEntity.setPlatDate(Info.getPlat_date());
                    basInfoEntity.setPlatTrace(Info.getPlat_trace());
                    basInfoEntity.setPlatTime(Info.getPlat_time());
                    basInfoEntity.setInstgPty(Info.getInstg_pty());
                    basInfoEntity.setMsgId(Info.getMsg_id());
                    basInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    basInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    basInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    basInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    basInfoEntity.setPgNb(Info.getPg_nb());
                    basInfoEntity.setBasInfoNb(Info.getBas_info_nb());
                    basInfoEntity.setMarketType(Info.getMarket_type());
//                    if(Info.getMarket_type().equals("ENT")){
                        basInfoEntity.setEntNm(Info.getEnt_nm());
                        basInfoEntity.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
                        basInfoEntity.setCoTp(Info.getCo_tp());
                        basInfoEntity.setDom(Info.getDom());
                        basInfoEntity.setRegCptl(Info.getReg_cptl());
                        basInfoEntity.setDtEst(Info.getDt_est());
                        basInfoEntity.setOpPrdFrom(Info.getOp_prd_from());
                        basInfoEntity.setOpPrdTo(Info.getOp_prd_to());
                        basInfoEntity.setRegSts(Info.getReg_sts());
                        basInfoEntity.setNmOfLglPrsn(Info.getNm_of_lgl_prsn());
                        basInfoEntity.setRegAuth(Info.getReg_auth());
                        basInfoEntity.setBizScp(Info.getBiz_scp());
                        basInfoEntity.setDtAppr(Info.getDt_appr());
//                    }else if(Info.getMarket_type().equals("TRA")){
                        basInfoEntity.setTraNm(Info.getTra_nm());
//                        basInfoEntity.setUniSocCdtCd(Info.getUni_soc_cdt_cd());
//                        basInfoEntity.setCoTp(Info.getCo_tp());
                        basInfoEntity.setOpLoc(Info.getOp_loc());
                        basInfoEntity.setFdAmt(Info.getFd_amt());
                        basInfoEntity.setDtReg(Info.getDt_reg());
//                        basInfoEntity.setRegSts(Info.getReg_sts());
                        basInfoEntity.setNm(Info.getNm());
//                        basInfoEntity.setRegAuth(Info.getReg_auth());
//                        basInfoEntity.setBizScp(Info.getBiz_scp());
//                        basInfoEntity.setDtAppr(Info.getDt_appr());
//                    }
                    basInfoEntityMapper.insertSelective(basInfoEntity);
                }
            }

            //插入CosInfo附表
            if (mivsRegVrfctnInfoModel.getCoShrhdrFndInfo() != null && !mivsRegVrfctnInfoModel.getCoShrhdrFndInfo().isEmpty()) {
                MivsRegvrfctnCosInfoEntity cosInfoEntity = new MivsRegvrfctnCosInfoEntity();
                for (MivsRegVrfctnInfoModel.CoShrhdrFndInfo Info : mivsRegVrfctnInfoModel.getCoShrhdrFndInfo()) {
                    cosInfoEntity.setPlatDate(Info.getPlat_date());
                    cosInfoEntity.setPlatTrace(Info.getPlat_trace());
                    cosInfoEntity.setPlatTime(Info.getPlat_time());
                    cosInfoEntity.setInstgPty(Info.getInstg_pty());
                    cosInfoEntity.setMsgId(Info.getMsg_id());
                    cosInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    cosInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    cosInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    cosInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    cosInfoEntity.setPgNb(Info.getPg_nb());
                    cosInfoEntity.setCoShrhdrfndInfoNb(Info.getCo_shrhdrfnd_info_nb());
                    cosInfoEntity.setNatlPrsnFlag(Info.getNatl_prsn_flag());
                    cosInfoEntity.setInvtrNm(Info.getInvtr_nm());
                    cosInfoEntity.setSubscrCptlConAmt(Info.getSubscr_cptl_con_amt());
                    cosInfoEntity.setActlCptlConAmt(Info.getActl_cptl_con_amt());
                    cosInfoEntity.setSubscrCptlConFm(Info.getSubscr_cptl_con_fm());
                    cosInfoEntity.setSubscrCptlConDt(Info.getSubscr_cptl_con_dt());
                    cosInfoEntityMapper.insertSelective(cosInfoEntity);
                }
            }

            //插入DirInfo附表
            if (mivsRegVrfctnInfoModel.getDirSupSrMgrInfo() != null && !mivsRegVrfctnInfoModel.getDirSupSrMgrInfo().isEmpty()) {
                MivsRegvrfctnDirInfoEntity dirInfoEntity = new MivsRegvrfctnDirInfoEntity();
                for (MivsRegVrfctnInfoModel.DirSupSrMgrInfo Info : mivsRegVrfctnInfoModel.getDirSupSrMgrInfo()) {
                    dirInfoEntity.setPlatDate(Info.getPlat_date());
                    dirInfoEntity.setPlatTrace(Info.getPlat_trace());
                    dirInfoEntity.setPlatTime(Info.getPlat_time());
                    dirInfoEntity.setInstgPty(Info.getInstg_pty());
                    dirInfoEntity.setMsgId(Info.getMsg_id());
                    dirInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    dirInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    dirInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    dirInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    dirInfoEntity.setPgNb(Info.getPg_nb());
                    dirInfoEntity.setDirSupsrsgrInfoNb(Info.getDir_supsrsgr_info_nb());
                    dirInfoEntity.setNm(Info.getNm());
                    dirInfoEntity.setPosn(Info.getPosn());
                    dirInfoEntityMapper.insertSelective(dirInfoEntity);
                }
            }

            //插入ChngInfo附表
            if (mivsRegVrfctnInfoModel.getChngInfo() != null && !mivsRegVrfctnInfoModel.getChngInfo().isEmpty()) {
                MivsRegvrfctnChngInfoEntity chngInfoEntity = new MivsRegvrfctnChngInfoEntity();
                for (MivsRegVrfctnInfoModel.ChngInfo Info : mivsRegVrfctnInfoModel.getChngInfo()) {
                    chngInfoEntity.setPlatDate(Info.getPlat_date());
                    chngInfoEntity.setPlatTrace(Info.getPlat_trace());
                    chngInfoEntity.setPlatTime(Info.getPlat_time());
                    chngInfoEntity.setInstgPty(Info.getInstg_pty());
                    chngInfoEntity.setMsgId(Info.getMsg_id());
                    chngInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    chngInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    chngInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    chngInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    chngInfoEntity.setPgNb(Info.getPg_nb());
                    chngInfoEntity.setChngInfoNb(Info.getChng_info_nb());
                    chngInfoEntity.setChngItm(Info.getChng_itm());
                    chngInfoEntity.setBfChng(Info.getBf_chng());
                    chngInfoEntity.setAftChng(Info.getAft_chng());
                    chngInfoEntity.setDtOfChng(Info.getDt_of_chng());
                    chngInfoEntityMapper.insertSelective(chngInfoEntity);
                }
            }

            //插入AbnInfo附表
            if (mivsRegVrfctnInfoModel.getAbnmlBizInfo() != null && !mivsRegVrfctnInfoModel.getAbnmlBizInfo().isEmpty()) {
                MivsRegvrfctnAbnInfoEntity abnInfoEntity = new MivsRegvrfctnAbnInfoEntity();
                for (MivsRegVrfctnInfoModel.AbnmlBizInfo Info : mivsRegVrfctnInfoModel.getAbnmlBizInfo()) {
                    abnInfoEntity.setPlatDate(Info.getPlat_date());
                    abnInfoEntity.setPlatTrace(Info.getPlat_trace());
                    abnInfoEntity.setPlatTime(Info.getPlat_time());
                    abnInfoEntity.setInstgPty(Info.getInstg_pty());
                    abnInfoEntity.setMsgId(Info.getMsg_id());
                    abnInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    abnInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    abnInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    abnInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    abnInfoEntity.setPgNb(Info.getPg_nb());
                    abnInfoEntity.setAbnInfoNb(Info.getAbn_info_nb());
                    abnInfoEntity.setAbnmlCause(Info.getAbnml_cause());
                    abnInfoEntity.setAbnmlDate(Info.getAbnml_date());
                    abnInfoEntity.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                    abnInfoEntity.setRmvCause(Info.getRmv_cause());
                    abnInfoEntity.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                    abnInfoEntity.setRmvDate(Info.getRmv_date());
                    abnInfoEntityMapper.insertSelective(abnInfoEntity);
                }
            }

            //插入IllInfo附表
            if (mivsRegVrfctnInfoModel.getIllDscrtInfo() != null && !mivsRegVrfctnInfoModel.getIllDscrtInfo().isEmpty()) {
                MivsRegvrfctnIllInfoEntity illInfoEntity = new MivsRegvrfctnIllInfoEntity();
                for (MivsRegVrfctnInfoModel.IllDscrtInfo Info : mivsRegVrfctnInfoModel.getIllDscrtInfo()) {
                    illInfoEntity.setPlatDate(Info.getPlat_date());
                    illInfoEntity.setPlatTrace(Info.getPlat_trace());
                    illInfoEntity.setPlatTime(Info.getPlat_time());
                    illInfoEntity.setInstgPty(Info.getInstg_pty());
                    illInfoEntity.setMsgId(Info.getMsg_id());
                    illInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    illInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    illInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    illInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    illInfoEntity.setPgNb(Info.getPg_nb());
                    illInfoEntity.setIllInfoNb(Info.getIll_info_nb());
                    illInfoEntity.setIllDscrtCause(Info.getIll_dscrt_cause());
                    illInfoEntity.setAbnmlDate(Info.getAbnml_date());
                    illInfoEntity.setAbnmlCauseDcsnAuth(Info.getAbnml_cause_dcsn_auth());
                    illInfoEntity.setRmvCause(Info.getRmv_cause());
                    illInfoEntity.setRmvCauseDcsnAuth(Info.getRmv_cause_dcsn_auth());
                    illInfoEntity.setRmvDate(Info.getRmv_date());
                    illInfoEntityMapper.insertSelective(illInfoEntity);
                }
            }

            //插入LicInfo附表
            if (mivsRegVrfctnInfoModel.getLicInfo() != null && !mivsRegVrfctnInfoModel.getLicInfo().isEmpty()) {
                MivsRegvrfctnLicInfoEntity licInfoEntity = new MivsRegvrfctnLicInfoEntity();
                for (MivsRegVrfctnInfoModel.LicInfo Info : mivsRegVrfctnInfoModel.getLicInfo()) {
                    licInfoEntity.setPlatDate(Info.getPlat_date());
                    licInfoEntity.setPlatTrace(Info.getPlat_trace());
                    licInfoEntity.setPlatTime(Info.getPlat_time());
                    licInfoEntity.setInstgPty(Info.getInstg_pty());
                    licInfoEntity.setMsgId(Info.getMsg_id());
                    licInfoEntity.setCreDtTm(Info.getCre_dt_tm());
                    licInfoEntity.setOrigMsgId(Info.getOrig_msg_id());
                    licInfoEntity.setOrigInstgDrctPty(Info.getOrig_instg_drct_pty());
                    licInfoEntity.setOrigInstgPty(Info.getOrig_instg_pty());
                    licInfoEntity.setPgNb(Info.getPg_nb());
                    licInfoEntity.setLicInfoNb(Info.getLic_info_nb());
                    licInfoEntity.setOrgnlOrCp(Info.getOrgnl_or_cp());
                    licInfoEntity.setLicNullStmCntt(Info.getLic_null_stm_cntt());
                    licInfoEntity.setLicNullStmDt(Info.getLic_null_stm_dt());
                    licInfoEntity.setRplSts(Info.getRpl_sts());
                    licInfoEntity.setRplDt(Info.getRpl_dt());
                    licInfoEntity.setLicCpNb(Info.getLic_cp_nb());
                    licInfoEntityMapper.insertSelective(licInfoEntity);
                }
            }
        }
    }

    @Override
    public MivsRegVrfctnInfoModel selectMasterAndAttached(String Msgid, String Instgpty, String flag){
        MivsRegVrfctnInfoModel infoModel = new MivsRegVrfctnInfoModel();

        MivsRegvrfctnInfoEntity infoEntity = new MivsRegvrfctnInfoEntity();
        infoEntity.setMsgId(Msgid);
        infoEntity.setInstgPty(Instgpty);
        MivsRegvrfctnInfoEntity mivsRegvrfctnInfoEntity = infoMapper.selectOne(infoEntity);
        infoModel.setPlat_date(mivsRegvrfctnInfoEntity.getPlatDate());
        infoModel.setPlat_trace(mivsRegvrfctnInfoEntity.getPlatTrace());
        infoModel.setPlat_time(mivsRegvrfctnInfoEntity.getPlatTime());
        infoModel.setTran_date(mivsRegvrfctnInfoEntity.getTranDate());
        infoModel.setSeq_no(mivsRegvrfctnInfoEntity.getSeqNo());
        infoModel.setMivs_sts(mivsRegvrfctnInfoEntity.getMivsSts());
        infoModel.setMsg_id(mivsRegvrfctnInfoEntity.getMsgId());
        infoModel.setCre_dt_tm(mivsRegvrfctnInfoEntity.getCreDtTm());
        infoModel.setInstg_drct_pty(mivsRegvrfctnInfoEntity.getInstgDrctPty());
        infoModel.setDrct_pty_nm(mivsRegvrfctnInfoEntity.getDrctPtyNm());
        infoModel.setInstg_pty(mivsRegvrfctnInfoEntity.getInstgPty());
        infoModel.setPty_nm(mivsRegvrfctnInfoEntity.getPtyNm());
        infoModel.setInstg_drct_pty(mivsRegvrfctnInfoEntity.getInstgDrctPty());
        infoModel.setInstd_pty(mivsRegvrfctnInfoEntity.getInstdPty());
        infoModel.setPg_nb(mivsRegvrfctnInfoEntity.getPgNb());
        infoModel.setLast_pg_ind(mivsRegvrfctnInfoEntity.getLastPgInd());
        infoModel.setRslt(mivsRegvrfctnInfoEntity.getRslt());
        infoModel.setData_resrc_dt(mivsRegvrfctnInfoEntity.getDataResrcDt());
        infoModel.setBas_info_cnt(mivsRegvrfctnInfoEntity.getBasInfoCnt());
        infoModel.setCo_shrhdrfnd_info_cnt(mivsRegvrfctnInfoEntity.getCoShrhdrfndInfoCnt());
        infoModel.setDir_supsrsgr_info_cnt(mivsRegvrfctnInfoEntity.getDirSupsrsgrInfoCnt());
        infoModel.setChng_info_cnt(mivsRegvrfctnInfoEntity.getChngInfoCnt());
        infoModel.setAbnml_biz_info_cnt(mivsRegvrfctnInfoEntity.getAbnmlBizInfoCnt());
        infoModel.setIll_dscrt_info_cnt(mivsRegvrfctnInfoEntity.getIllDscrtInfoCnt());
        infoModel.setIll_dscrt_info_cnt(mivsRegvrfctnInfoEntity.getIllDscrtInfoCnt());

        if(flag.equals("all")) {
            //查询BasInfo附表
            MivsRegvrfctnBasInfoEntity basInfo = new MivsRegvrfctnBasInfoEntity();
            basInfo.setOrigMsgId(Msgid);
            basInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnBasInfoEntity> mivsRegvrfctnBasInfoEntities = basInfoEntityMapper.select(basInfo);
            if (mivsRegvrfctnBasInfoEntities != null && !mivsRegvrfctnBasInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.BasInfo> basInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.BasInfo>();
                for (MivsRegvrfctnBasInfoEntity Info : mivsRegvrfctnBasInfoEntities) {
                    MivsRegVrfctnInfoModel.BasInfo basInfoMsg = new MivsRegVrfctnInfoModel.BasInfo();
                    basInfoMsg.setPg_nb(Info.getPgNb());
                    basInfoMsg.setBas_info_nb(Info.getBasInfoNb());
                    basInfoMsg.setEnt_nm(Info.getEntNm());
                    basInfoMsg.setUni_soc_cdt_cd(Info.getUniSocCdtCd());
                    basInfoMsg.setCo_tp(Info.getCoTp());
                    basInfoMsg.setDom(Info.getDom());
                    basInfoMsg.setReg_cptl(Info.getRegCptl());
                    basInfoMsg.setDt_est(Info.getDtEst());
                    basInfoMsg.setOp_prd_from(Info.getOpPrdFrom());
                    basInfoMsg.setOp_prd_to(Info.getOpPrdTo());
                    basInfoMsg.setReg_sts(Info.getRegSts());
                    basInfoMsg.setNm_of_lgl_prsn(Info.getNmOfLglPrsn());
                    basInfoMsg.setReg_auth(Info.getRegAuth());
                    basInfoMsg.setBiz_scp(Info.getBizScp());
                    basInfoMsg.setDt_appr(Info.getDtAppr());
                    basInfoMsg.setTra_nm(Info.getTraNm());
                    basInfoMsg.setMarket_type(Info.getMarketType());
                    basInfoMsg.setOp_loc(Info.getOpLoc());
                    basInfoMsg.setFd_amt(Info.getFdAmt());
                    basInfoMsg.setDt_reg(Info.getDtReg());
                    basInfoMsg.setNm(Info.getNm());
                    basInfoArrayMsg.add(basInfoMsg);
                }
                infoModel.setBasInfo(basInfoArrayMsg);
            }

            //查询CosInfo附表
            MivsRegvrfctnCosInfoEntity cosInfo = new MivsRegvrfctnCosInfoEntity();
            cosInfo.setOrigMsgId(Msgid);
            cosInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnCosInfoEntity> mivsRegvrfctnCosInfoEntities = cosInfoEntityMapper.select(cosInfo);
            if (mivsRegvrfctnCosInfoEntities != null && !mivsRegvrfctnCosInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.CoShrhdrFndInfo> coShrhdrFndInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.CoShrhdrFndInfo>();
                for (MivsRegvrfctnCosInfoEntity Info : mivsRegvrfctnCosInfoEntities) {
                    MivsRegVrfctnInfoModel.CoShrhdrFndInfo coShrhdrFndInfoMsg = new MivsRegVrfctnInfoModel.CoShrhdrFndInfo();
                    coShrhdrFndInfoMsg.setPg_nb(Info.getPgNb());
                    coShrhdrFndInfoMsg.setCo_shrhdrfnd_info_nb(Info.getCoShrhdrfndInfoNb());
                    coShrhdrFndInfoMsg.setNatl_prsn_flag(Info.getNatlPrsnFlag());
                    coShrhdrFndInfoMsg.setInvtr_nm(Info.getInvtrNm());
                    coShrhdrFndInfoMsg.setInvtr_id(Info.getInvtrId());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_amt(Info.getSubscrCptlConAmt());
                    coShrhdrFndInfoMsg.setActl_cptl_con_amt(Info.getActlCptlConAmt());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_fm(Info.getSubscrCptlConFm());
                    coShrhdrFndInfoMsg.setSubscr_cptl_con_dt(Info.getSubscrCptlConDt());
                    coShrhdrFndInfoArrayMsg.add(coShrhdrFndInfoMsg);
                }
                infoModel.setCoShrhdrFndInfo(coShrhdrFndInfoArrayMsg);
            }

            //查询DirInfo附表
            MivsRegvrfctnDirInfoEntity dirInfo = new MivsRegvrfctnDirInfoEntity();
            dirInfo.setOrigMsgId(Msgid);
            dirInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnDirInfoEntity> mivsRegvrfctnDirInfoEntities = dirInfoEntityMapper.select(dirInfo);
            if (mivsRegvrfctnDirInfoEntities != null && !mivsRegvrfctnDirInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.DirSupSrMgrInfo> dirSupSrMgrInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.DirSupSrMgrInfo>();
                for (MivsRegvrfctnDirInfoEntity Info : mivsRegvrfctnDirInfoEntities) {
                    MivsRegVrfctnInfoModel.DirSupSrMgrInfo dirSupSrMgrInfoMsg = new MivsRegVrfctnInfoModel.DirSupSrMgrInfo();
                    dirSupSrMgrInfoMsg.setPg_nb(Info.getPgNb());
                    dirSupSrMgrInfoMsg.setDir_supsrsgr_info_nb(Info.getDirSupsrsgrInfoNb());
                    dirSupSrMgrInfoMsg.setNm(Info.getNm());
                    dirSupSrMgrInfoMsg.setPosn(Info.getPosn());
                    dirSupSrMgrInfoArrayMsg.add(dirSupSrMgrInfoMsg);
                }
                infoModel.setDirSupSrMgrInfo(dirSupSrMgrInfoArrayMsg);
            }

            //查询ChngInfo附表
            MivsRegvrfctnChngInfoEntity chngInfo = new MivsRegvrfctnChngInfoEntity();
            chngInfo.setOrigMsgId(Msgid);
            chngInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnChngInfoEntity> mivsRegvrfctnChngInfoEntities = chngInfoEntityMapper.select(chngInfo);
            if (mivsRegvrfctnChngInfoEntities != null && !mivsRegvrfctnChngInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.ChngInfo> chngInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.ChngInfo>();
                for (MivsRegvrfctnChngInfoEntity Info : mivsRegvrfctnChngInfoEntities) {
                    MivsRegVrfctnInfoModel.ChngInfo chngInfoMsg = new MivsRegVrfctnInfoModel.ChngInfo();
                    chngInfoMsg.setPg_nb(Info.getPgNb());
                    chngInfoMsg.setChng_info_nb(Info.getChngInfoNb());
                    chngInfoMsg.setChng_itm(Info.getChngItm());
                    chngInfoMsg.setAft_chng(Info.getAftChng());
                    chngInfoMsg.setBf_chng(Info.getBfChng());
                    chngInfoMsg.setDt_of_chng(Info.getDtOfChng());
                    chngInfoArrayMsg.add(chngInfoMsg);
                }
                infoModel.setChngInfo(chngInfoArrayMsg);
            }

            //查询AbnInfo附表
            MivsRegvrfctnAbnInfoEntity abnInfo = new MivsRegvrfctnAbnInfoEntity();
            abnInfo.setOrigMsgId(Msgid);
            abnInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnAbnInfoEntity> mivsRegvrfctnAbnInfoEntities = abnInfoEntityMapper.select(abnInfo);
            if (mivsRegvrfctnAbnInfoEntities != null && !mivsRegvrfctnAbnInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.AbnmlBizInfo> abnInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.AbnmlBizInfo>();
                for (MivsRegvrfctnAbnInfoEntity Info : mivsRegvrfctnAbnInfoEntities) {
                    MivsRegVrfctnInfoModel.AbnmlBizInfo abnInfoMsg = new MivsRegVrfctnInfoModel.AbnmlBizInfo();
                    abnInfoMsg.setPg_nb(Info.getPgNb());
                    abnInfoMsg.setAbn_info_nb(Info.getAbnInfoNb());
                    abnInfoMsg.setAbnml_cause(Info.getAbnmlCause());
                    abnInfoMsg.setAbnml_cause_dcsn_auth(Info.getAbnmlCauseDcsnAuth());
                    abnInfoMsg.setAbnml_date(Info.getAbnmlDate());
                    abnInfoMsg.setRmv_cause(Info.getRmvCause());
                    abnInfoMsg.setRmv_cause_dcsn_auth(Info.getRmvCauseDcsnAuth());
                    abnInfoMsg.setRmv_date(Info.getRmvDate());
                    abnInfoArrayMsg.add(abnInfoMsg);
                }
                infoModel.setAbnmlBizInfo(abnInfoArrayMsg);
            }

            //查询IllInfo附表
            MivsRegvrfctnIllInfoEntity illInfo = new MivsRegvrfctnIllInfoEntity();
            illInfo.setOrigMsgId(Msgid);
            illInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnIllInfoEntity> mivsRegvrfctnIllInfoEntities = illInfoEntityMapper.select(illInfo);
            if (mivsRegvrfctnIllInfoEntities != null && !mivsRegvrfctnIllInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.IllDscrtInfo> illInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.IllDscrtInfo>();
                for (MivsRegvrfctnIllInfoEntity Info : mivsRegvrfctnIllInfoEntities) {
                    MivsRegVrfctnInfoModel.IllDscrtInfo illInfoMsg = new MivsRegVrfctnInfoModel.IllDscrtInfo();
                    illInfoMsg.setPg_nb(Info.getPgNb());
                    illInfoMsg.setIll_info_nb(Info.getIllInfoNb());
                    illInfoMsg.setIll_dscrt_cause(Info.getIllDscrtCause());
                    illInfoMsg.setAbnml_cause_dcsn_auth(Info.getAbnmlCauseDcsnAuth());
                    illInfoMsg.setAbnml_date(Info.getAbnmlDate());
                    illInfoMsg.setRmv_cause(Info.getRmvCause());
                    illInfoMsg.setRmv_cause_dcsn_auth(Info.getRmvCauseDcsnAuth());
                    illInfoMsg.setRmv_date(Info.getRmvDate());
                    illInfoArrayMsg.add(illInfoMsg);
                }
                infoModel.setIllDscrtInfo(illInfoArrayMsg);
            }

            //查询LicInfo附表
            MivsRegvrfctnLicInfoEntity licInfo = new MivsRegvrfctnLicInfoEntity();
            licInfo.setOrigMsgId(Msgid);
            licInfo.setOrigInstgPty(Instgpty);
            List<MivsRegvrfctnLicInfoEntity> mivsRegvrfctnLicInfoEntities = licInfoEntityMapper.select(licInfo);
            if (mivsRegvrfctnLicInfoEntities != null && !mivsRegvrfctnLicInfoEntities.isEmpty()) {
                List<MivsRegVrfctnInfoModel.LicInfo> licInfoArrayMsg = new ArrayList<MivsRegVrfctnInfoModel.LicInfo>();
                for (MivsRegvrfctnLicInfoEntity Info : mivsRegvrfctnLicInfoEntities) {
                    MivsRegVrfctnInfoModel.LicInfo licInfoMsg = new MivsRegVrfctnInfoModel.LicInfo();
                    licInfoMsg.setPg_nb(Info.getPgNb());
                    licInfoMsg.setLic_info_nb(Info.getLicInfoNb());
                    licInfoMsg.setOrgnl_or_cp(Info.getOrgnlOrCp());
                    licInfoMsg.setLic_null_stm_cntt(Info.getLicNullStmCntt());
                    licInfoMsg.setLic_null_stm_dt(Info.getLicNullStmDt());
                    licInfoMsg.setRpl_sts(Info.getRplSts());
                    licInfoMsg.setRpl_dt(Info.getRplDt());
                    licInfoMsg.setLic_cp_nb(Info.getLicCpNb());
                    licInfoArrayMsg.add(licInfoMsg);
                }
                infoModel.setLicInfo(licInfoArrayMsg);
            }
        }
        return infoModel;
    }


}
