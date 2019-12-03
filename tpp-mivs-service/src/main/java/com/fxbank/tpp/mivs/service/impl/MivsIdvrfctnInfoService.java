package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.IdVrfctn.MivsIdvrfctnFdbkEntity;
import com.fxbank.tpp.mivs.entity.IdVrfctn.MivsIdvrfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.IdVrfctn.MivsIdvrfctnFdbkEntityMapper;
import com.fxbank.tpp.mivs.mapper.IdVrfctn.MivsIdvrfctnInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsIdVrfctnInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 手机号核查业务数据库操作服务
 * @Author: 王鹏
 * @Date: 2019/5/21 16:23
 */
@Service(version = "1.0.0")
public class MivsIdvrfctnInfoService implements IMivsIdVrfctnInfoService {

    @Resource
    private MivsIdvrfctnInfoEntityMapper infoEntityMapper;

    @Resource
    private MivsIdvrfctnFdbkEntityMapper fdbkEntityMapper;

    @Override
    public void insertStart(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel) {
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnInfoModel.getPlat_trace());
        info.setPlatTime(mivsIdVrfctnInfoModel.getPlat_time());
        info.setSystemId(mivsIdVrfctnInfoModel.getSystem_id());
        info.setTranDate(mivsIdVrfctnInfoModel.getTran_date());
        info.setSeqNo(mivsIdVrfctnInfoModel.getSeq_no());
        info.setTranTime(mivsIdVrfctnInfoModel.getTran_time());
        info.setUserId(mivsIdVrfctnInfoModel.getUser_id());
        info.setBranchId(mivsIdVrfctnInfoModel.getBranch_id());
        info.setMivsSts(mivsIdVrfctnInfoModel.getMivs_sts());
        info.setMsgId(mivsIdVrfctnInfoModel.getMsg_id());
        info.setCreDtTm(mivsIdVrfctnInfoModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsIdVrfctnInfoModel.getInstg_drct_pty());
        info.setDrctPtyNm(mivsIdVrfctnInfoModel.getDrct_pty_nm());
        info.setInstgPty(mivsIdVrfctnInfoModel.getInstg_pty());
        info.setPtyNm(mivsIdVrfctnInfoModel.getPty_nm());
        info.setInstdDrctPty(mivsIdVrfctnInfoModel.getInstd_drct_pty());
        info.setInstdPty(mivsIdVrfctnInfoModel.getInstd_pty());
        info.setMobNb(mivsIdVrfctnInfoModel.getMob_nb());
        info.setNm(mivsIdVrfctnInfoModel.getNm());
        info.setIdTp(mivsIdVrfctnInfoModel.getId_tp());
        info.setId(mivsIdVrfctnInfoModel.getId());
        info.setUniSocCdtCd(mivsIdVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setBizRegNb(mivsIdVrfctnInfoModel.getBiz_reg_nb());
        info.setOpNm(mivsIdVrfctnInfoModel.getOp_nm());

        infoEntityMapper.insertSelective(info);
    }

    @Override
    public void updateSts(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel) {
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnInfoModel.getPlat_trace());

        info.setMivsSts(mivsIdVrfctnInfoModel.getMivs_sts());
        info.setRcvMsgId(mivsIdVrfctnInfoModel.getRcv_msg_id());
        info.setRcvCreDtTm(mivsIdVrfctnInfoModel.getCre_dt_tm());
        info.setProcCd(mivsIdVrfctnInfoModel.getProc_cd());
        info.setProcSts(mivsIdVrfctnInfoModel.getProc_sts());
        info.setRjctInf(mivsIdVrfctnInfoModel.getRjct_inf());
        info.setMobNb(mivsIdVrfctnInfoModel.getMob_nb());
        info.setRslt(mivsIdVrfctnInfoModel.getRslt());
        info.setMobCrr(mivsIdVrfctnInfoModel.getMob_crr());
        info.setLocMobNb(mivsIdVrfctnInfoModel.getLoc_mob_nb());
        info.setLocNmMobNb(mivsIdVrfctnInfoModel.getLoc_nm_mob_nb());
        info.setCdTp(mivsIdVrfctnInfoModel.getCd_tp());
        info.setSts(mivsIdVrfctnInfoModel.getSts());

        infoEntityMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public List<MivsIdVrfctnInfoModel> selectResult(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel){
        List<MivsIdvrfctnInfoEntity> infoEntityList = new ArrayList<MivsIdvrfctnInfoEntity>();
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getStart_dt());
        info.setBranchId(mivsIdVrfctnInfoModel.getBranch_id());
        info.setUserId(mivsIdVrfctnInfoModel.getUser_id());
        info.setMsgId(mivsIdVrfctnInfoModel.getOrig_dlv_msgid());
        info.setRcvMsgId(mivsIdVrfctnInfoModel.getOrig_rcv_msgid());
        info.setMobNb(mivsIdVrfctnInfoModel.getMob_nb());
        info.setNm(mivsIdVrfctnInfoModel.getNm());
        info.setIdTp(mivsIdVrfctnInfoModel.getId_tp());
        info.setId(mivsIdVrfctnInfoModel.getId());
        info.setUniSocCdtCd(mivsIdVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setBizRegNb(mivsIdVrfctnInfoModel.getBiz_reg_nb());

        infoEntityList = infoEntityMapper.select(info);
        if(!infoEntityList.isEmpty() && infoEntityList != null) {
            List<MivsIdVrfctnInfoModel> idVrfctnInfoModel = new ArrayList<MivsIdVrfctnInfoModel>();
            for (MivsIdvrfctnInfoEntity Info : infoEntityList) {
                MivsIdVrfctnInfoModel infoResult = new MivsIdVrfctnInfoModel();
                infoResult.setTran_date(Info.getTranDate());
                infoResult.setSeq_no(Info.getSeqNo());
                infoResult.setTran_time(Info.getTranTime());
                infoResult.setOrig_dlv_msgid(Info.getMsgId());
                infoResult.setOrig_rcv_msgid(Info.getRcvMsgId());
                infoResult.setBranch_id(Info.getBranchId());
                infoResult.setUser_id(Info.getUserId());
                infoResult.setMob_nb(Info.getMobNb());
                infoResult.setNm(Info.getNm());
                infoResult.setId_tp(Info.getIdTp());
                infoResult.setId(Info.getId());
                infoResult.setUni_soc_cdt_cd(Info.getUniSocCdtCd());
                infoResult.setBiz_reg_nb(Info.getBizRegNb());
                infoResult.setRslt(Info.getRslt());
                infoResult.setMob_crr(Info.getMobCrr());
                infoResult.setLoc_mob_nb(Info.getLocMobNb());
                infoResult.setLoc_nm_mob_nb(Info.getLocNmMobNb());
                infoResult.setCd_tp(Info.getCdTp());
                infoResult.setSts(Info.getSts());
                infoResult.setProc_sts(Info.getProcSts());
                infoResult.setProc_cd(Info.getProcCd());
                infoResult.setRjct_inf(Info.getRjctInf());
                infoResult.setRemark1(Info.getRemark1());
                infoResult.setRemark2(Info.getRemark2());
                infoResult.setRemark3(Info.getRemark3());
                idVrfctnInfoModel.add(infoResult);
            }

            return idVrfctnInfoModel;
        }else{
            return null;
        }
    }

    @Override
    public MivsIdVrfctnInfoModel selectFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setMsgId(mivsIdVrfctnInfoModel.getOrig_dlv_msgid());
        info.setRcvMsgId(mivsIdVrfctnInfoModel.getOrig_rcv_msgid());

        info = infoEntityMapper.selectOne(info);
        if(info == null){
            return null;
        }else{
            MivsIdVrfctnInfoModel idVrfctnInfoModel = new MivsIdVrfctnInfoModel();
            idVrfctnInfoModel.setPlat_date(info.getPlatDate());
            return idVrfctnInfoModel;
        }
    }

    @Override
    public void insertFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsIdvrfctnFdbkEntity info = new MivsIdvrfctnFdbkEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnInfoModel.getPlat_trace());
        info.setPlatTime(mivsIdVrfctnInfoModel.getPlat_time());
        info.setSystemId(mivsIdVrfctnInfoModel.getSystem_id());
        info.setTranDate(mivsIdVrfctnInfoModel.getTran_date());
        info.setSeqNo(mivsIdVrfctnInfoModel.getSeq_no());
        info.setTranTime(mivsIdVrfctnInfoModel.getTran_time());
        info.setUserId(mivsIdVrfctnInfoModel.getUser_id());
        info.setBranchId(mivsIdVrfctnInfoModel.getBranch_id());
        info.setMivsSts(mivsIdVrfctnInfoModel.getMivs_sts());
        info.setMsgId(mivsIdVrfctnInfoModel.getMsg_id());
        info.setCreDtTm(mivsIdVrfctnInfoModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsIdVrfctnInfoModel.getInstg_drct_pty());
        info.setDrctPtyNm(mivsIdVrfctnInfoModel.getDrct_pty_nm());
        info.setInstgPty(mivsIdVrfctnInfoModel.getInstg_pty());
        info.setPtyNm(mivsIdVrfctnInfoModel.getPty_nm());
        info.setInstdDrctPty(mivsIdVrfctnInfoModel.getInstd_drct_pty());
        info.setInstdPty(mivsIdVrfctnInfoModel.getInstd_pty());

        info.setSysInd(mivsIdVrfctnInfoModel.getSys_ind());
        info.setOrigDlvMsgid(mivsIdVrfctnInfoModel.getOrig_dlv_msgid());
        info.setOrigRcvMsgid(mivsIdVrfctnInfoModel.getOrig_rcv_msgid());
        info.setMobNb(mivsIdVrfctnInfoModel.getMob_nb());
        info.setNm(mivsIdVrfctnInfoModel.getNm());
        info.setIdTp(mivsIdVrfctnInfoModel.getId_tp());
        info.setId(mivsIdVrfctnInfoModel.getId());
        info.setUniSocCdtCd(mivsIdVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setBizRegNb(mivsIdVrfctnInfoModel.getBiz_reg_nb());
        info.setRslt(mivsIdVrfctnInfoModel.getRslt());
        info.setCntt(mivsIdVrfctnInfoModel.getCntt());
        info.setContactNm(mivsIdVrfctnInfoModel.getContact_nm());
        info.setContactNb(mivsIdVrfctnInfoModel.getContact_nb());
        info.setRemark1(mivsIdVrfctnInfoModel.getRemark1());
        info.setRemark2(mivsIdVrfctnInfoModel.getRemark2());
        info.setRemark3(mivsIdVrfctnInfoModel.getRemark3());

        fdbkEntityMapper.insertSelective(info);
    }

    @Override
    public void updateFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsIdvrfctnFdbkEntity info = new MivsIdvrfctnFdbkEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnInfoModel.getPlat_trace());

        info.setMivsSts(mivsIdVrfctnInfoModel.getMivs_sts());
        info.setRcvMsgId(mivsIdVrfctnInfoModel.getRcv_msg_id());
        info.setRcvCreDtTm(mivsIdVrfctnInfoModel.getCre_dt_tm());
        info.setProcSts(mivsIdVrfctnInfoModel.getProc_sts());
        info.setProcCd(mivsIdVrfctnInfoModel.getProc_cd());
        info.setPtyId(mivsIdVrfctnInfoModel.getPty_id());
        info.setPtyPrcCd(mivsIdVrfctnInfoModel.getPty_prc_cd());
        info.setRjctInf(mivsIdVrfctnInfoModel.getRjct_inf());
        info.setPrcDt(mivsIdVrfctnInfoModel.getPrc_dt());
        info.setNetgRnd(mivsIdVrfctnInfoModel.getNetg_rnd());
        info.setRemark1(mivsIdVrfctnInfoModel.getRemark1());
        info.setRemark2(mivsIdVrfctnInfoModel.getRemark2());
        info.setRemark3(mivsIdVrfctnInfoModel.getRemark3());

        fdbkEntityMapper.updateByPrimaryKeySelective(info);
    }
}
