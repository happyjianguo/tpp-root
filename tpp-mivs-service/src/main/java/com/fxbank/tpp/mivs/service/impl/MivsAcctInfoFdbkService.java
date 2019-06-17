package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.AcctInfoFdbk.MivsAcctinfofdbkInfoAttEntity;
import com.fxbank.tpp.mivs.mapper.AcctInfoFdbk.MivsAcctinfofdbkInfoAttEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsAcctInfoFdbkModel;
import com.fxbank.tpp.mivs.service.IMivsAcctInfoFdbkService;

import javax.annotation.Resource;

/**
 * @Description: 企业开销户状态反馈表操作服务
 * @Author: 王鹏
 * @Date: 2019/6/14 15:45
 */
@Service(version = "1.0.0")
public class MivsAcctInfoFdbkService implements IMivsAcctInfoFdbkService {

    @Resource
    private MivsAcctinfofdbkInfoAttEntityMapper mapper;

    @Override
    public void insertStart(MivsAcctInfoFdbkModel mivsAcctInfoFdbkModel){
        MivsAcctinfofdbkInfoAttEntity info = new MivsAcctinfofdbkInfoAttEntity();
        info.setPlatDate(mivsAcctInfoFdbkModel.getPlat_date());
        info.setPlatTrace(mivsAcctInfoFdbkModel.getPlat_trace());
        info.setPlatTime(mivsAcctInfoFdbkModel.getPlat_time());
        info.setSystemId(mivsAcctInfoFdbkModel.getSystem_id());
        info.setTranDate(mivsAcctInfoFdbkModel.getTran_date());
        info.setSeqNo(mivsAcctInfoFdbkModel.getSeq_no());
        info.setTranTime(mivsAcctInfoFdbkModel.getTran_time());
        info.setUserId(mivsAcctInfoFdbkModel.getUser_id());
        info.setBranchId(mivsAcctInfoFdbkModel.getBranch_id());
        info.setMivsSts(mivsAcctInfoFdbkModel.getMivs_sts());
        info.setMsgId(mivsAcctInfoFdbkModel.getMsg_id());
        info.setCreDtTm(mivsAcctInfoFdbkModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsAcctInfoFdbkModel.getInstg_drct_pty());
        info.setDrctPtyNm(mivsAcctInfoFdbkModel.getDrct_pty_nm());
        info.setInstgPty(mivsAcctInfoFdbkModel.getInstg_pty());
        info.setPtyNm(mivsAcctInfoFdbkModel.getPty_nm());
        info.setInstdDrctPty(mivsAcctInfoFdbkModel.getInstd_drct_pty());
        info.setInstdPty(mivsAcctInfoFdbkModel.getInstd_pty());
        info.setEntNm(mivsAcctInfoFdbkModel.getEnt_nm());
        info.setUniSocCdtCd(mivsAcctInfoFdbkModel.getUni_soc_cdt_cd());
        info.setAcctSts(mivsAcctInfoFdbkModel.getMivs_sts());
        info.setChngDt(mivsAcctInfoFdbkModel.getChng_dt());
        info.setRemark1(mivsAcctInfoFdbkModel.getRemark1());
        info.setRemark2(mivsAcctInfoFdbkModel.getRemark2());
        info.setRemark3(mivsAcctInfoFdbkModel.getRemark3());

        mapper.insertSelective(info);
    }

    @Override
    public void updateSts(MivsAcctInfoFdbkModel mivsAcctInfoFdbkModel){
        MivsAcctinfofdbkInfoAttEntity info = new MivsAcctinfofdbkInfoAttEntity();
        info.setPlatDate(mivsAcctInfoFdbkModel.getPlat_date());
        info.setPlatTrace(mivsAcctInfoFdbkModel.getPlat_trace());
        info.setMivsSts(mivsAcctInfoFdbkModel.getMivs_sts());
        info.setProcSts(mivsAcctInfoFdbkModel.getProc_sts());
        info.setProcCd(mivsAcctInfoFdbkModel.getProc_cd());
        info.setPtyId(mivsAcctInfoFdbkModel.getPty_id());
        info.setPtyPrcCd(mivsAcctInfoFdbkModel.getPty_prc_cd());
        info.setRjctInf(mivsAcctInfoFdbkModel.getRjct_inf());
        info.setPrcDt(mivsAcctInfoFdbkModel.getPrc_dt());
        info.setNetgRnd(mivsAcctInfoFdbkModel.getNetg_rnd());

        mapper.updateByPrimaryKeySelective(info);

    }
}
