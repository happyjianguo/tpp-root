package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.MivsIdvrfctnInfo;
import com.fxbank.tpp.mivs.mapper.MivsIdvrfctnInfoMapper;
import com.fxbank.tpp.mivs.model.mivstables.MivsIdVrfctnInfoTable;
import com.fxbank.tpp.mivs.service.IMivsIdvrfctnInfoService;

import javax.annotation.Resource;

/**
 * @Description: 手机号核查业务数据库操作服务
 * @Author: 王鹏
 * @Date: 2019/5/21 16:23
 */
@Service(version = "1.0.0")
public class MivsIdvrfctnInfoService implements IMivsIdvrfctnInfoService {

    @Resource
    private MivsIdvrfctnInfoMapper mapper;

    @Override
    public void insertStart(MivsIdVrfctnInfoTable mivsIdVrfctnTable) {
        MivsIdvrfctnInfo log = new MivsIdvrfctnInfo();
        log.setPlatDate(mivsIdVrfctnTable.getPlat_date());
        log.setPlatTrace(mivsIdVrfctnTable.getPlat_trace());
        log.setPlatTime(mivsIdVrfctnTable.getPlat_time());
        log.setSystemId(mivsIdVrfctnTable.getSystem_id());
        log.setTranDate(mivsIdVrfctnTable.getTran_date());
        log.setSeqNo(mivsIdVrfctnTable.getSeq_no());
        log.setTranTime(mivsIdVrfctnTable.getTran_time());
        log.setUserId(mivsIdVrfctnTable.getUser_id());
        log.setBranchId(mivsIdVrfctnTable.getBranch_id());
        log.setMivsSts(mivsIdVrfctnTable.getMivs_sts());
        log.setMsgId(mivsIdVrfctnTable.getMsg_id());
        log.setCreDtTm(mivsIdVrfctnTable.getCre_dt_tm());
        log.setInstgDrctPty(mivsIdVrfctnTable.getInstg_drct_pty());
        log.setDrctPtyNm(mivsIdVrfctnTable.getDrct_pty_nm());
        log.setInstgPty(mivsIdVrfctnTable.getInstg_pty());
        log.setPtyNm(mivsIdVrfctnTable.getPty_nm());
        log.setInstdDrctPty(mivsIdVrfctnTable.getInstd_drct_pty());
        log.setInstdPty(mivsIdVrfctnTable.getInstd_pty());
        log.setMobNb(mivsIdVrfctnTable.getMob_nb());
        log.setNm(mivsIdVrfctnTable.getNm());
        log.setIdTp(mivsIdVrfctnTable.getId_tp());
        log.setId(mivsIdVrfctnTable.getId());
        log.setUniSocCdtCd(mivsIdVrfctnTable.getUni_soc_cdt_cd());
        log.setBizRegNb(mivsIdVrfctnTable.getBiz_reg_nb());
        log.setOpNm(mivsIdVrfctnTable.getOp_nm());

        mapper.insertSelective(log);
    }

    @Override
    public void updateSts(MivsIdVrfctnInfoTable mivsIdVrfctnTable) {
        MivsIdvrfctnInfo log = new MivsIdvrfctnInfo();
        log.setPlatDate(mivsIdVrfctnTable.getPlat_date());
        log.setPlatTrace(mivsIdVrfctnTable.getPlat_trace());
        log.setMivsSts(mivsIdVrfctnTable.getMivs_sts());

        log.setProcCd(mivsIdVrfctnTable.getProc_cd());
        log.setProcSts(mivsIdVrfctnTable.getProc_sts());
        log.setRjctInf(mivsIdVrfctnTable.getRjct_inf());
        log.setMobNb(mivsIdVrfctnTable.getMob_nb());
        log.setRslt(mivsIdVrfctnTable.getRslt());
        log.setMobCrr(mivsIdVrfctnTable.getMob_crr());
        log.setLocMobNb(mivsIdVrfctnTable.getLoc_mob_nb());
        log.setLocNmMobNb(mivsIdVrfctnTable.getLoc_nm_mob_nb());
        log.setCdTp(mivsIdVrfctnTable.getCd_tp());
        log.setSts(mivsIdVrfctnTable.getSts());

        mapper.updateByPrimaryKeySelective(log);
    }

}
