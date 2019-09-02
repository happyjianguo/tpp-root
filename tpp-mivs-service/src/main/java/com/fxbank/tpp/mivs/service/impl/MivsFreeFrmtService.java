package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.FreeFrmt.MivsFreefrmtConfEntity;
import com.fxbank.tpp.mivs.entity.FreeFrmt.MivsFreefrmtInfoEntity;
import com.fxbank.tpp.mivs.mapper.FreeFrmt.MivsFreefrmtConfEntityMapper;
import com.fxbank.tpp.mivs.mapper.FreeFrmt.MivsFreefrmtInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsFreeFrmtModel;
import com.fxbank.tpp.mivs.service.IMivsFreeFrmtService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 企业通知
 * @Author: 王鹏
 * @Date: 2019/7/3 17:01
 */
@Service(version = "1.0.0")
public class MivsFreeFrmtService implements IMivsFreeFrmtService {

    @Resource
    MivsFreefrmtInfoEntityMapper infoEntityMapper;

    @Resource
    MivsFreefrmtConfEntityMapper confEntityMapper;

    @Override
    public void insertInfo(MivsFreeFrmtModel mivsFreeFrmtModel){
        MivsFreefrmtInfoEntity infoEntity = new MivsFreefrmtInfoEntity();
        infoEntity.setPlatDate(mivsFreeFrmtModel.getPlat_date());
        infoEntity.setPlatTrace(mivsFreeFrmtModel.getPlat_trace());
        infoEntity.setPlatTime(mivsFreeFrmtModel.getPlat_time());
        infoEntity.setMivsSts(mivsFreeFrmtModel.getMivs_sts());
        infoEntity.setMsgId(mivsFreeFrmtModel.getMsg_id());
        infoEntity.setInstgDrctPty(mivsFreeFrmtModel.getInstg_drct_pty());
        infoEntity.setInstgPty(mivsFreeFrmtModel.getInstg_pty());
        infoEntity.setInstdDrctPty(mivsFreeFrmtModel.getInstd_drct_pty());
        infoEntity.setInstdPty(mivsFreeFrmtModel.getInstd_pty());
        infoEntity.setRplyFlag(mivsFreeFrmtModel.getRply_flag());
        infoEntity.setMsgCntt(mivsFreeFrmtModel.getMsg_cntt());
        infoEntity.setIsornotRsp(mivsFreeFrmtModel.getIsornot_rsp());
        infoEntityMapper.insert(infoEntity);
    }

    @Override
    public void updateInfo(MivsFreeFrmtModel mivsFreeFrmtModel){

    }

    @Override
    public List<MivsFreeFrmtModel> selectResult(MivsFreeFrmtModel mivsFreeFrmtModel){
        List<MivsFreefrmtInfoEntity> infoEntityList = new ArrayList<MivsFreefrmtInfoEntity>();
        MivsFreefrmtInfoEntity info = new MivsFreefrmtInfoEntity();
        info.setPlatDate(mivsFreeFrmtModel.getStart_dt());
        info.setRplyFlag(mivsFreeFrmtModel.getRply_flag());

        infoEntityList = infoEntityMapper.select(info);
        if(!infoEntityList.isEmpty() && infoEntityList != null) {
            List<MivsFreeFrmtModel> freeFrmtModels = new ArrayList<MivsFreeFrmtModel>();
            for (MivsFreefrmtInfoEntity Info : infoEntityList) {
                MivsFreeFrmtModel infoResult = new MivsFreeFrmtModel();
                infoResult.setPlat_date(Info.getPlatDate());
                infoResult.setPlat_trace(Info.getPlatTrace());
                infoResult.setPlat_time(Info.getPlatTime());
                infoResult.setMsg_id(Info.getMsgId());
                infoResult.setCre_dt_tm(Info.getCreDtTm());
                infoResult.setInstd_drct_pty(Info.getInstdDrctPty());
                infoResult.setInstd_pty(Info.getInstdPty());
                infoResult.setInstg_drct_pty(Info.getInstgDrctPty());
                infoResult.setInstg_pty(Info.getInstgPty());
                infoResult.setRply_flag(Info.getRplyFlag());
                infoResult.setMsg_cntt(Info.getMsgCntt());
                infoResult.setIsornot_rsp(Info.getIsornotRsp());
                freeFrmtModels.add(infoResult);
            }

            return freeFrmtModels;
        }else{
            return null;
        }
    }

    @Override
    public void insertConf(MivsFreeFrmtModel mivsFreeFrmtModel){
        MivsFreefrmtConfEntity confEntity = new MivsFreefrmtConfEntity();
        confEntity.setPlatDate(mivsFreeFrmtModel.getPlat_date());
        confEntity.setPlatTrace(mivsFreeFrmtModel.getPlat_trace());
        confEntity.setPlatTime(mivsFreeFrmtModel.getPlat_time());
        confEntity.setSystemId(mivsFreeFrmtModel.getSystem_id());
        confEntity.setTranDate(mivsFreeFrmtModel.getTran_date());
        confEntity.setSeqNo(mivsFreeFrmtModel.getSeq_no());
        confEntity.setTranTime(mivsFreeFrmtModel.getTran_time());
        confEntity.setUserId(mivsFreeFrmtModel.getUser_id());
        confEntity.setBranchId(mivsFreeFrmtModel.getBranch_id());
        confEntity.setMivsSts(mivsFreeFrmtModel.getMivs_sts());
        confEntity.setMsgId(mivsFreeFrmtModel.getMsg_id());
        confEntity.setCreDtTm(mivsFreeFrmtModel.getCre_dt_tm());
        confEntity.setInstdDrctPty(mivsFreeFrmtModel.getInstd_drct_pty());
        confEntity.setInstdPty(mivsFreeFrmtModel.getInstd_pty());
        confEntity.setInstgDrctPty(mivsFreeFrmtModel.getInstg_drct_pty());
        confEntity.setDrctPtyNm(mivsFreeFrmtModel.getDrct_pty_nm());
        confEntity.setInstgPty(mivsFreeFrmtModel.getInstg_pty());
        confEntity.setPtyNm(mivsFreeFrmtModel.getPty_nm());
        confEntity.setOrigDlvMsgid(mivsFreeFrmtModel.getOrig_dlv_msgid());
        confEntity.setOrigInstgDrctPty(mivsFreeFrmtModel.getOrig_instg_drct_pty());
        confEntity.setOrigInstgPty(mivsFreeFrmtModel.getOrig_instg_pty());
        confEntity.setMsgCntt(mivsFreeFrmtModel.getMsg_cntt());

        confEntityMapper.insertSelective(confEntity);
    }

    @Override
    public void upInfoAndConf(MivsFreeFrmtModel mivsFreeFrmtModel){
        MivsFreefrmtConfEntity confEntity = new MivsFreefrmtConfEntity();
        confEntity.setPlatDate(mivsFreeFrmtModel.getPlat_date());
        confEntity.setPlatTrace(mivsFreeFrmtModel.getPlat_trace());
        confEntity.setMivsSts(mivsFreeFrmtModel.getMivs_sts());
        confEntity.setMsgPrcCd(mivsFreeFrmtModel.getProc_cd());
        confEntityMapper.updateByPrimaryKey(confEntity);

        MivsFreefrmtInfoEntity infoEntity = new MivsFreefrmtInfoEntity();
        infoEntity.setMsgId(mivsFreeFrmtModel.getMsg_id());
        infoEntity.setInstgPty(mivsFreeFrmtModel.getInstg_pty());
        infoEntity.setInstgDrctPty(mivsFreeFrmtModel.getInstg_drct_pty());
        infoEntity.setIsornotRsp(mivsFreeFrmtModel.getIsornot_rsp());
        infoEntityMapper.updateByPrimaryKey(infoEntity);
    }
}
