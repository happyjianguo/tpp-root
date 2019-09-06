package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.SysStsNtfctn.MivsSysstsntfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.SysStsNtfctn.MivsSysstsntfctnInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;
import com.fxbank.tpp.mivs.service.IMivsSysStsNtfctnService;

import javax.annotation.Resource;

/**
 * @Description: 企业信息联网核查业务受理时间通知服务
 * @Author: 王鹏
 * @Date: 2019/7/5 16:36
 */
@Service(version = "1.0.0")
public class MivsSysStsNtfctnService implements IMivsSysStsNtfctnService {
    @Resource
    MivsSysstsntfctnInfoEntityMapper infoEntityMapper;

    @Override
    public void insertInfo(MivsSysStsNtfctnModel mivsSysStsNtfctnModel){
        MivsSysstsntfctnInfoEntity infoEntity = new MivsSysstsntfctnInfoEntity();
        infoEntity.setPlatDate(mivsSysStsNtfctnModel.getPlat_date());
        infoEntity.setPlatTrace(mivsSysStsNtfctnModel.getPlat_trace());
        infoEntity.setPlatTime(mivsSysStsNtfctnModel.getPlat_time());
        infoEntity.setMivsSts(mivsSysStsNtfctnModel.getMivs_sts());
        infoEntity.setMsgId(mivsSysStsNtfctnModel.getMsg_id());
        infoEntity.setInstgDrctPty(mivsSysStsNtfctnModel.getInstg_drct_pty());
        infoEntity.setInstgPty(mivsSysStsNtfctnModel.getInstg_pty());
        infoEntity.setInstdDrctPty(mivsSysStsNtfctnModel.getInstd_drct_pty());
        infoEntity.setInstdPty(mivsSysStsNtfctnModel.getInstd_pty());
        infoEntity.setCurSysDt(mivsSysStsNtfctnModel.getCur_sys_dt());
        infoEntity.setNxtSysDt(mivsSysStsNtfctnModel.getNxt_sys_dt());
        infoEntity.setSysInd(mivsSysStsNtfctnModel.getSys_ind());
        infoEntity.setSvcInd(mivsSysStsNtfctnModel.getSvc_ind());
        infoEntity.setNxtSysClTm(mivsSysStsNtfctnModel.getNxt_sys_cl_tm());
        infoEntity.setNxtSysOpTm(mivsSysStsNtfctnModel.getNxt_sys_op_tm());
        infoEntityMapper.insertSelective(infoEntity);
    }
}
