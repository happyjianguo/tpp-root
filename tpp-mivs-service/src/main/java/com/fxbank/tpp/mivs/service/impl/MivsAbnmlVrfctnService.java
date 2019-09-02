package com.fxbank.tpp.mivs.service.impl;

import com.fxbank.tpp.mivs.entity.AbnmlVrfctn.MivsAbnmlvrfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.AbnmlVrfctn.MivsAbnmlvrfctnInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsAbnmlVrfctnModel;
import com.fxbank.tpp.mivs.service.IMivsAbnmlVrfctnService;

import javax.annotation.Resource;

/**
 * @Description: 企业异常核查信息通知服务
 * @Author: 王鹏
 * @Date: 2019/6/20 8:25
 */
public class MivsAbnmlVrfctnService implements IMivsAbnmlVrfctnService {

    @Resource
    private MivsAbnmlvrfctnInfoEntityMapper mapper;

    @Override
    public void insertStart(MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel){
        MivsAbnmlvrfctnInfoEntity infoEntity = new MivsAbnmlvrfctnInfoEntity();
        infoEntity.setPlatDate(mivsAbnmlVrfctnModel.getPlat_date());
        infoEntity.setPlatTrace(mivsAbnmlVrfctnModel.getPlat_trace());
        infoEntity.setPlatTime(mivsAbnmlVrfctnModel.getPlat_time());
        infoEntity.setCheckType(mivsAbnmlVrfctnModel.getCheck_type());
        infoEntity.setMivsSts(mivsAbnmlVrfctnModel.getMivs_sts());
        infoEntity.setMsgId(mivsAbnmlVrfctnModel.getMsg_id());
        infoEntity.setInstgDrctPty(mivsAbnmlVrfctnModel.getInstg_drct_pty());
        infoEntity.setInstgPty(mivsAbnmlVrfctnModel.getInstg_pty());
        infoEntity.setInstdDrctPty(mivsAbnmlVrfctnModel.getInstd_drct_pty());
        infoEntity.setInstdPty(mivsAbnmlVrfctnModel.getInstd_pty());
        infoEntity.setBranchId(mivsAbnmlVrfctnModel.getBranch_id());
        infoEntity.setOrgnlInstgPty(mivsAbnmlVrfctnModel.getOrgnl_instg_pty());
        infoEntity.setDescrip(mivsAbnmlVrfctnModel.getDescrip());
        infoEntity.setAbnmlType(mivsAbnmlVrfctnModel.getAbnml_type());
        infoEntity.setCoNm(mivsAbnmlVrfctnModel.getCo_nm());
        infoEntity.setUniSocCdtCd(mivsAbnmlVrfctnModel.getUni_soc_cdt_cd());
        infoEntity.setMobNb(mivsAbnmlVrfctnModel.getMob_nb());
        infoEntity.setNm(mivsAbnmlVrfctnModel.getNm());
        mapper.insert(infoEntity);
    }

    @Override
    public void updateSts(MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel){

    }
}
