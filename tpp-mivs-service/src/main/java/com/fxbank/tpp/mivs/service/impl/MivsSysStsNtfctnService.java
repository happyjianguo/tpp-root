package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.SysStsNtfctn.MivsSysstsntfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.SysStsNtfctn.MivsSysstsntfctnInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;
import com.fxbank.tpp.mivs.service.IMivsSysStsNtfctnService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String insertMsg(List<MivsSysStsNtfctnModel> mivsSysStsNtfctnModelList){
        if(mivsSysStsNtfctnModelList !=null && !mivsSysStsNtfctnModelList.isEmpty()){
            for(MivsSysStsNtfctnModel modelinfo: mivsSysStsNtfctnModelList) {
                MivsSysstsntfctnInfoEntity infoEntity = new MivsSysstsntfctnInfoEntity();
                infoEntity.setPlatDate(modelinfo.getPlat_date());
                infoEntity.setPlatTrace(modelinfo.getPlat_trace());
                infoEntity.setPlatTime(modelinfo.getPlat_time());
                infoEntity.setMivsSts(modelinfo.getMivs_sts());
                infoEntity.setMsgId(modelinfo.getMsg_id());
                infoEntity.setCreDtTm(modelinfo.getCre_dt_tm());
                infoEntity.setInstgDrctPty(modelinfo.getInstg_drct_pty());
                infoEntity.setInstgPty(modelinfo.getInstg_pty());
                infoEntity.setInstdDrctPty(modelinfo.getInstd_drct_pty());
                infoEntity.setInstdPty(modelinfo.getInstd_pty());
                infoEntity.setCurSysDt(modelinfo.getCur_sys_dt());
                infoEntity.setNxtSysDt(modelinfo.getNxt_sys_dt());
                infoEntity.setSysInd(modelinfo.getSys_ind());
                infoEntity.setSvcInd(modelinfo.getSvc_ind());
                infoEntity.setNxtSysOpTm(modelinfo.getNxt_sys_op_tm());
                infoEntity.setNxtSysClTm(modelinfo.getNxt_sys_cl_tm());

                infoEntityMapper.insertSelective(infoEntity);
            }
            return "YES";
        }else{
            return "NO";
        }
    }

    @Override
    public List<MivsSysStsNtfctnModel> selectMsg(MivsSysStsNtfctnModel mivsSysStsNtfctnModel){
        List<MivsSysstsntfctnInfoEntity> infoEntityList = new ArrayList<MivsSysstsntfctnInfoEntity>();
        MivsSysstsntfctnInfoEntity infoEntity = new MivsSysstsntfctnInfoEntity();
        infoEntity.setPlatDate(mivsSysStsNtfctnModel.getPlat_date());
        infoEntity.setMsgId(mivsSysStsNtfctnModel.getMsg_id());
        infoEntityList = infoEntityMapper.select(infoEntity);

        if(!infoEntityList.isEmpty() && infoEntityList != null) {
            List<MivsSysStsNtfctnModel> regVrfctnInfoModelList = new ArrayList<MivsSysStsNtfctnModel>();
            for (MivsSysstsntfctnInfoEntity sysstsntfctnInfoEntity : infoEntityList) {
                MivsSysStsNtfctnModel infoResult = new MivsSysStsNtfctnModel();
                infoResult.setPlat_date(sysstsntfctnInfoEntity.getPlatDate());
                infoResult.setMsg_id(sysstsntfctnInfoEntity.getMsgId());
                infoResult.setCre_dt_tm(sysstsntfctnInfoEntity.getCreDtTm());
                infoResult.setInstg_drct_pty(sysstsntfctnInfoEntity.getInstgDrctPty());
                infoResult.setInstg_pty(sysstsntfctnInfoEntity.getInstgPty());
                infoResult.setInstd_drct_pty(sysstsntfctnInfoEntity.getInstdDrctPty());
                infoResult.setInstd_pty(sysstsntfctnInfoEntity.getInstdPty());
                infoResult.setCur_sys_dt(sysstsntfctnInfoEntity.getCurSysDt());
                infoResult.setNxt_sys_dt(sysstsntfctnInfoEntity.getNxtSysDt());
                infoResult.setSys_ind(sysstsntfctnInfoEntity.getSysInd());
                infoResult.setSvc_ind(sysstsntfctnInfoEntity.getSvcInd());
                infoResult.setNxt_sys_op_tm(sysstsntfctnInfoEntity.getNxtSysOpTm());
                infoResult.setNxt_sys_cl_tm(sysstsntfctnInfoEntity.getNxtSysClTm());

                regVrfctnInfoModelList.add(infoResult);
            }
            return regVrfctnInfoModelList;
        }else{
            return null;
        }
    }
}
