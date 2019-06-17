package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.IdVrfctn.MivsIdvrfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.IdVrfctn.MivsIdvrfctnInfoMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsIdVrfctnInfoService;

import javax.annotation.Resource;

/**
 * @Description: 手机号核查业务数据库操作服务
 * @Author: 王鹏
 * @Date: 2019/5/21 16:23
 */
@Service(version = "1.0.0")
public class MivsIdVrfctnInfoService implements IMivsIdVrfctnInfoService {

    @Resource
    private MivsIdvrfctnInfoMapper mapper;

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

        mapper.insertSelective(info);
    }

    @Override
    public void updateSts(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel) {
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnInfoModel.getPlat_trace());
        info.setMivsSts(mivsIdVrfctnInfoModel.getMivs_sts());

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

        mapper.updateByPrimaryKeySelective(info);
    }

}
