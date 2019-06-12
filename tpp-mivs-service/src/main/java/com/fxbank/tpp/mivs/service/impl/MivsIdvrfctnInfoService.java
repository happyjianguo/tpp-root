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
    public void insertStart(MivsIdVrfctnInfoModel mivsIdVrfctnTable) {
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnTable.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnTable.getPlat_trace());
        info.setPlatTime(mivsIdVrfctnTable.getPlat_time());
        info.setSystemId(mivsIdVrfctnTable.getSystem_id());
        info.setTranDate(mivsIdVrfctnTable.getTran_date());
        info.setSeqNo(mivsIdVrfctnTable.getSeq_no());
        info.setTranTime(mivsIdVrfctnTable.getTran_time());
        info.setUserId(mivsIdVrfctnTable.getUser_id());
        info.setBranchId(mivsIdVrfctnTable.getBranch_id());
        info.setMivsSts(mivsIdVrfctnTable.getMivs_sts());
        info.setMsgId(mivsIdVrfctnTable.getMsg_id());
        info.setCreDtTm(mivsIdVrfctnTable.getCre_dt_tm());
        info.setInstgDrctPty(mivsIdVrfctnTable.getInstg_drct_pty());
        info.setDrctPtyNm(mivsIdVrfctnTable.getDrct_pty_nm());
        info.setInstgPty(mivsIdVrfctnTable.getInstg_pty());
        info.setPtyNm(mivsIdVrfctnTable.getPty_nm());
        info.setInstdDrctPty(mivsIdVrfctnTable.getInstd_drct_pty());
        info.setInstdPty(mivsIdVrfctnTable.getInstd_pty());
        info.setMobNb(mivsIdVrfctnTable.getMob_nb());
        info.setNm(mivsIdVrfctnTable.getNm());
        info.setIdTp(mivsIdVrfctnTable.getId_tp());
        info.setId(mivsIdVrfctnTable.getId());
        info.setUniSocCdtCd(mivsIdVrfctnTable.getUni_soc_cdt_cd());
        info.setBizRegNb(mivsIdVrfctnTable.getBiz_reg_nb());
        info.setOpNm(mivsIdVrfctnTable.getOp_nm());

        mapper.insertSelective(info);
    }

    @Override
    public void updateSts(MivsIdVrfctnInfoModel mivsIdVrfctnTable) {
        MivsIdvrfctnInfoEntity info = new MivsIdvrfctnInfoEntity();
        info.setPlatDate(mivsIdVrfctnTable.getPlat_date());
        info.setPlatTrace(mivsIdVrfctnTable.getPlat_trace());
        info.setMivsSts(mivsIdVrfctnTable.getMivs_sts());

        info.setProcCd(mivsIdVrfctnTable.getProc_cd());
        info.setProcSts(mivsIdVrfctnTable.getProc_sts());
        info.setRjctInf(mivsIdVrfctnTable.getRjct_inf());
        info.setMobNb(mivsIdVrfctnTable.getMob_nb());
        info.setRslt(mivsIdVrfctnTable.getRslt());
        info.setMobCrr(mivsIdVrfctnTable.getMob_crr());
        info.setLocMobNb(mivsIdVrfctnTable.getLoc_mob_nb());
        info.setLocNmMobNb(mivsIdVrfctnTable.getLoc_nm_mob_nb());
        info.setCdTp(mivsIdVrfctnTable.getCd_tp());
        info.setSts(mivsIdVrfctnTable.getSts());

        mapper.updateByPrimaryKeySelective(info);
    }

}
