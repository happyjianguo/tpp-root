package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.CmonConf.MivsCmonconfInfoEntity;
import com.fxbank.tpp.mivs.mapper.CmonConf.MivsCmonconfInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsCmonConfModel;
import com.fxbank.tpp.mivs.service.IMivsCmonConfService;

import javax.annotation.Resource;

/**
 * @Description: 900表操作服务
 * @Author: 王鹏
 * @Date: 2019/6/17 9:18
 */
@Service(version = "1.0.0")
public class MivsCmonConfService implements IMivsCmonConfService {

    @Resource
    private MivsCmonconfInfoEntityMapper mapper;

    @Override
    public void insertStart(MivsCmonConfModel mivsCmonConfModel){
        MivsCmonconfInfoEntity info = new MivsCmonconfInfoEntity();
        info.setPlatDate(mivsCmonConfModel.getPlat_date());
        info.setPlatTrace(mivsCmonConfModel.getPlat_trace());
        info.setPlatTime(mivsCmonConfModel.getPlat_time());
        info.setTranDate(mivsCmonConfModel.getTran_date());
        info.setSeqNo(mivsCmonConfModel.getSeq_no());
        info.setTranTime(mivsCmonConfModel.getTran_time());
        info.setMsgId(mivsCmonConfModel.getMsg_id());
        info.setCreDtTm(mivsCmonConfModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsCmonConfModel.getInstg_drct_pty());
        info.setInstgPty(mivsCmonConfModel.getInstg_pty());
        info.setInstdDrctPty(mivsCmonConfModel.getInstd_drct_pty());
        info.setInstdPty(mivsCmonConfModel.getInstd_pty());
        info.setSystemCode(mivsCmonConfModel.getSystem_code());
        info.setOrgnlMsgId(mivsCmonConfModel.getOrgnl_msg_id());
        info.setOrgnlInstgPty(mivsCmonConfModel.getOrgnl_instg_pty());
        info.setOrgnlMt(mivsCmonConfModel.getOrgnl_mt());
        info.setProcSts(mivsCmonConfModel.getProc_sts());
        info.setProcCd(mivsCmonConfModel.getProc_cd());
        info.setPtyId(mivsCmonConfModel.getPty_id());
        info.setPtyPrcCd(mivsCmonConfModel.getPty_prc_cd());
        info.setRjctInf(mivsCmonConfModel.getRjct_inf());
        info.setPrcDt(mivsCmonConfModel.getPrc_dt());
        info.setNetgRnd(mivsCmonConfModel.getNetg_rnd());
        mapper.insertSelective(info);
    }
}
