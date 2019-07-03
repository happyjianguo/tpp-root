package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnFdbkEntity;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoAttEntity;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvrfctnFdbkEntityMapper;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvrfctnInfoAttEntityMapper;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvrfctnInfoEntityMapper;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsTxPmtVrfctnInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 纳税信息核查数据库操作
 * @Author: 王鹏
 * @Date: 2019/5/23 10:14
 */
@Service(version = "1.0.0")
public class MivsTxpmtVrfctnInfoService implements IMivsTxPmtVrfctnInfoService {

    @Resource
    private MivsTxpmtvrfctnInfoEntityMapper mapper;

    @Resource
    private MivsTxpmtvrfctnInfoAttEntityMapper attMapper;

    @Resource
    private MivsTxpmtvrfctnFdbkEntityMapper fdbkEntityMapper;

    @Override
    public void insertMaster(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel) {
        MivsTxpmtvrfctnInfoEntity info = new MivsTxpmtvrfctnInfoEntity();
        info.setPlatDate(mivsTxpmtVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsTxpmtVrfctnInfoModel.getPlat_trace());
        info.setPlatTime(mivsTxpmtVrfctnInfoModel.getPlat_time());
        info.setSystemId(mivsTxpmtVrfctnInfoModel.getSystem_id());
        info.setTranDate(mivsTxpmtVrfctnInfoModel.getTran_date());
        info.setSeqNo(mivsTxpmtVrfctnInfoModel.getSeq_no());
        info.setTranTime(mivsTxpmtVrfctnInfoModel.getTran_time());
        info.setUserId(mivsTxpmtVrfctnInfoModel.getUser_id());
        info.setBranchId(mivsTxpmtVrfctnInfoModel.getBranch_id());
        info.setMivsSts(mivsTxpmtVrfctnInfoModel.getMivs_sts());
        info.setMsgId(mivsTxpmtVrfctnInfoModel.getMsg_id());
        info.setCreDtTm(mivsTxpmtVrfctnInfoModel.getCre_dt_tm());
        info.setInstgDrctPty(mivsTxpmtVrfctnInfoModel.getInstg_drct_pty());
        info.setDrctPtyNm(mivsTxpmtVrfctnInfoModel.getDrct_pty_nm());
        info.setInstgPty(mivsTxpmtVrfctnInfoModel.getInstg_pty());
        info.setPtyNm(mivsTxpmtVrfctnInfoModel.getPty_nm());
        info.setInstdDrctPty(mivsTxpmtVrfctnInfoModel.getInstd_drct_pty());
        info.setInstdPty(mivsTxpmtVrfctnInfoModel.getInstd_pty());
        info.setCoNm(mivsTxpmtVrfctnInfoModel.getCo_nm());
        info.setUniSocCdtCd(mivsTxpmtVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setTxpyrIdNb(mivsTxpmtVrfctnInfoModel.getTxpyr_id_nb());
        info.setOpNm(mivsTxpmtVrfctnInfoModel.getOp_nm());

        mapper.insertSelective(info);
    }

    @Override
    public MivsTxpmtVrfctnInfoModel selectMasterAndAttached(String origMsgid, String origInstgPty, String flag){
        MivsTxpmtvrfctnInfoEntity info = new MivsTxpmtvrfctnInfoEntity();
        info.setMsgId(origMsgid);
        info.setInstgPty(origInstgPty);
        MivsTxpmtvrfctnInfoEntity mivsTxpmtvrfctnInfoEntity = mapper.selectOne(info);
        if(mivsTxpmtvrfctnInfoEntity != null) {
            MivsTxpmtVrfctnInfoModel infoModel = new MivsTxpmtVrfctnInfoModel();
            infoModel.setPlat_date(mivsTxpmtvrfctnInfoEntity.getPlatDate());
            infoModel.setPlat_trace(mivsTxpmtvrfctnInfoEntity.getPlatTrace());
            infoModel.setPlat_time(mivsTxpmtvrfctnInfoEntity.getPlatTime());
            infoModel.setTran_date(mivsTxpmtvrfctnInfoEntity.getTranDate());
            infoModel.setSeq_no(mivsTxpmtvrfctnInfoEntity.getSeqNo());
            infoModel.setMivs_sts(mivsTxpmtvrfctnInfoEntity.getMivsSts());
            infoModel.setMsg_id(mivsTxpmtvrfctnInfoEntity.getMsgId());
            infoModel.setCre_dt_tm(mivsTxpmtvrfctnInfoEntity.getCreDtTm());
            infoModel.setInstg_drct_pty(mivsTxpmtvrfctnInfoEntity.getInstgDrctPty());
            infoModel.setDrct_pty_nm(mivsTxpmtvrfctnInfoEntity.getDrctPtyNm());
            infoModel.setInstg_pty(mivsTxpmtvrfctnInfoEntity.getInstgPty());
            infoModel.setPty_nm(mivsTxpmtvrfctnInfoEntity.getPtyNm());
            infoModel.setInstg_drct_pty(mivsTxpmtvrfctnInfoEntity.getInstgDrctPty());
            infoModel.setInstd_pty(mivsTxpmtvrfctnInfoEntity.getInstdPty());
            infoModel.setRcv_msg_id(mivsTxpmtvrfctnInfoEntity.getRcvMsgId());
            infoModel.setRcv_cre_dt_tm(mivsTxpmtvrfctnInfoEntity.getRcvCreDtTm());
            infoModel.setCo_nm(mivsTxpmtvrfctnInfoEntity.getCoNm());
            infoModel.setUni_soc_cdt_cd(mivsTxpmtvrfctnInfoEntity.getUniSocCdtCd());
            infoModel.setTxpyr_id_nb(mivsTxpmtvrfctnInfoEntity.getTxpyrIdNb());
            infoModel.setOp_nm(mivsTxpmtvrfctnInfoEntity.getOpNm());
            infoModel.setRslt(mivsTxpmtvrfctnInfoEntity.getRslt());
            infoModel.setData_resrc_dt(mivsTxpmtvrfctnInfoEntity.getDataResrcDt());
            infoModel.setTxpmt_inf_cnt(mivsTxpmtvrfctnInfoEntity.getTxpmtInfCnt());
            infoModel.setProc_sts(mivsTxpmtvrfctnInfoEntity.getProcSts());
            infoModel.setProc_cd(mivsTxpmtvrfctnInfoEntity.getProcCd());
            infoModel.setRjct_inf(mivsTxpmtvrfctnInfoEntity.getRjctInf());

            if (flag.equals("all")) {
                //查询附表
                MivsTxpmtvrfctnInfoAttEntity txpmtvrfctnInfoAttEntity = new MivsTxpmtvrfctnInfoAttEntity();
                txpmtvrfctnInfoAttEntity.setOrigMsgId(origMsgid);
                txpmtvrfctnInfoAttEntity.setOrigInstgPty(origInstgPty);
                List<MivsTxpmtvrfctnInfoAttEntity> mivsTxpmtvrfctnInfoAttEntities = attMapper.select(txpmtvrfctnInfoAttEntity);
                if (mivsTxpmtvrfctnInfoAttEntities != null && !mivsTxpmtvrfctnInfoAttEntities.isEmpty()) {
                    List<MivsTxpmtVrfctnInfoModel.TxpmtInf> txpmtInfListArrayMsg = new ArrayList<MivsTxpmtVrfctnInfoModel.TxpmtInf>();
                    for (MivsTxpmtvrfctnInfoAttEntity Info : mivsTxpmtvrfctnInfoAttEntities) {
                        MivsTxpmtVrfctnInfoModel.TxpmtInf txpmtInfMsg = new MivsTxpmtVrfctnInfoModel.TxpmtInf();
                        txpmtInfMsg.setTxpmt_inf_nb(Info.getTxpmtInfNb());
                        txpmtInfMsg.setTx_auth_cd(Info.getTxAuthCd());
                        txpmtInfMsg.setTx_auth_nm(Info.getTxAuthNm());
                        txpmtInfMsg.setTxpyr_sts(Info.getTxpyrSts());
                        txpmtInfListArrayMsg.add(txpmtInfMsg);
                    }
                    infoModel.setTxpmtInfList(txpmtInfListArrayMsg);
                }
            }
            return infoModel;
        }else{
            return null;
        }
    }

    @Override
    public void uMasterAndiAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel, String flag){
        MivsTxpmtvrfctnInfoEntity info = new MivsTxpmtvrfctnInfoEntity();
        info.setPlatDate(mivsTxpmtVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsTxpmtVrfctnInfoModel.getPlat_trace());
        info.setMivsSts(mivsTxpmtVrfctnInfoModel.getMivs_sts());
        info.setRcvMsgId(mivsTxpmtVrfctnInfoModel.getRcv_msg_id());
        info.setRcvCreDtTm(mivsTxpmtVrfctnInfoModel.getCre_dt_tm());
        info.setProcCd(mivsTxpmtVrfctnInfoModel.getProc_cd());
        info.setProcSts(mivsTxpmtVrfctnInfoModel.getProc_sts());
        info.setRjctInf(mivsTxpmtVrfctnInfoModel.getRjct_inf());
        info.setRslt(mivsTxpmtVrfctnInfoModel.getRslt());
        info.setDataResrcDt(mivsTxpmtVrfctnInfoModel.getData_resrc_dt());
        info.setTxpmtInfCnt(mivsTxpmtVrfctnInfoModel.getTxpmt_inf_cnt());
        mapper.updateByPrimaryKeySelective(info);

        if(flag.equals("all")) {
            //插入TxpmtInf附表
            if (mivsTxpmtVrfctnInfoModel.getTxpmtInfList() != null && !mivsTxpmtVrfctnInfoModel.getTxpmtInfList().isEmpty()) {
                MivsTxpmtvrfctnInfoAttEntity txpmtvrfctnInfoAttEntity = new MivsTxpmtvrfctnInfoAttEntity();
                for (MivsTxpmtVrfctnInfoModel.TxpmtInf attInfo : mivsTxpmtVrfctnInfoModel.getTxpmtInfList()) {
                    txpmtvrfctnInfoAttEntity.setPlatDate(attInfo.getPlat_date());
                    txpmtvrfctnInfoAttEntity.setPlatTrace(attInfo.getPlat_trace());
                    txpmtvrfctnInfoAttEntity.setPlatTime(attInfo.getPlat_time());
                    txpmtvrfctnInfoAttEntity.setInstgPty(attInfo.getInstg_pty());
                    txpmtvrfctnInfoAttEntity.setMsgId(attInfo.getMsg_id());
                    txpmtvrfctnInfoAttEntity.setCreDtTm(attInfo.getCre_dt_tm());
                    txpmtvrfctnInfoAttEntity.setOrigMsgId(attInfo.getOrig_msg_id());
                    txpmtvrfctnInfoAttEntity.setOrigInstgDrctPty(attInfo.getOrig_instg_drct_pty());
                    txpmtvrfctnInfoAttEntity.setOrigInstgPty(attInfo.getOrig_instg_pty());
                    txpmtvrfctnInfoAttEntity.setTxpmtInfNb(attInfo.getTxpmt_inf_nb());
                    txpmtvrfctnInfoAttEntity.setTxAuthCd(attInfo.getTx_auth_cd());
                    txpmtvrfctnInfoAttEntity.setTxAuthNm(attInfo.getTx_auth_nm());
                    txpmtvrfctnInfoAttEntity.setTxpyrSts(attInfo.getTxpyr_sts());

                    attMapper.insertSelective(txpmtvrfctnInfoAttEntity);
                }
            }
        }
    }

    @Override
    public List<MivsTxpmtVrfctnInfoModel> selectResult(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel){
        List<MivsTxpmtvrfctnInfoEntity> infoEntityList = new ArrayList<MivsTxpmtvrfctnInfoEntity>();
        MivsTxpmtvrfctnInfoEntity infoEntity = new MivsTxpmtvrfctnInfoEntity();
        infoEntity.setPlatDate(mivsIdVrfctnInfoModel.getStart_dt());
        infoEntity.setBranchId(mivsIdVrfctnInfoModel.getBranch_id());
        infoEntity.setUserId(mivsIdVrfctnInfoModel.getUser_id());
        infoEntity.setMsgId(mivsIdVrfctnInfoModel.getOrig_dlv_msgid());
        infoEntity.setRcvMsgId(mivsIdVrfctnInfoModel.getOrig_rcv_msgid());
        infoEntity.setCoNm(mivsIdVrfctnInfoModel.getCo_nm());
        infoEntity.setUniSocCdtCd(mivsIdVrfctnInfoModel.getUni_soc_cdt_cd());
        infoEntity.setTxpyrIdNb(mivsIdVrfctnInfoModel.getTxpyr_id_nb());

        infoEntityList = mapper.select(infoEntity);
        if(!infoEntityList.isEmpty() && infoEntityList != null) {
            List<MivsTxpmtVrfctnInfoModel> txpmtVrfctnInfoModels = new ArrayList<MivsTxpmtVrfctnInfoModel>();
            for (MivsTxpmtvrfctnInfoEntity Info : infoEntityList) {
                MivsTxpmtVrfctnInfoModel infoResult = new MivsTxpmtVrfctnInfoModel();
                infoResult.setTran_date(Info.getTranDate());
                infoResult.setSeq_no(Info.getSeqNo());
                infoResult.setTran_time(Info.getTranTime());
                infoResult.setOrig_dlv_msgid(Info.getMsgId());
                infoResult.setOrig_rcv_msgid(Info.getRcvMsgId());
                infoResult.setCo_nm(Info.getCoNm());
                infoResult.setUni_soc_cdt_cd(Info.getUniSocCdtCd());
                infoResult.setTxpyr_id_nb(Info.getTxpyrIdNb());
                infoResult.setRslt(Info.getRslt());
                infoResult.setData_resrc_dt(Info.getDataResrcDt());
                infoResult.setProc_sts(Info.getProcSts());
                infoResult.setProc_cd(Info.getProcCd());
                infoResult.setRjct_inf(Info.getRjctInf());
                infoResult.setRemark1(Info.getRemark1());
                infoResult.setRemark2(Info.getRemark2());
                infoResult.setRemark3(Info.getRemark3());
                //查询附表
                MivsTxpmtvrfctnInfoAttEntity txpmtvrfctnInfoAttEntity = new MivsTxpmtvrfctnInfoAttEntity();
                txpmtvrfctnInfoAttEntity.setMsgId(infoResult.getOrig_rcv_msgid());
                txpmtvrfctnInfoAttEntity.setOrigMsgId(infoResult.getOrig_dlv_msgid());
                List<MivsTxpmtvrfctnInfoAttEntity> mivsTxpmtvrfctnInfoAttEntities = attMapper.select(txpmtvrfctnInfoAttEntity);
                if (mivsTxpmtvrfctnInfoAttEntities != null && !mivsTxpmtvrfctnInfoAttEntities.isEmpty()) {
                    List<MivsTxpmtVrfctnInfoModel.TxpmtInf> txpmtInfListArrayMsg = new ArrayList<MivsTxpmtVrfctnInfoModel.TxpmtInf>();
                    for (MivsTxpmtvrfctnInfoAttEntity InfoAtt : mivsTxpmtvrfctnInfoAttEntities) {
                        MivsTxpmtVrfctnInfoModel.TxpmtInf txpmtInfMsg = new MivsTxpmtVrfctnInfoModel.TxpmtInf();
                        txpmtInfMsg.setTxpmt_inf_nb(InfoAtt.getTxpmtInfNb());
                        txpmtInfMsg.setTx_auth_cd(InfoAtt.getTxAuthCd());
                        txpmtInfMsg.setTx_auth_nm(InfoAtt.getTxAuthNm());
                        txpmtInfMsg.setTxpyr_sts(InfoAtt.getTxpyrSts());
                        txpmtInfListArrayMsg.add(txpmtInfMsg);
                    }
                    infoResult.setTxpmtInfList(txpmtInfListArrayMsg);
                }
                txpmtVrfctnInfoModels.add(infoResult);
            }
            return txpmtVrfctnInfoModels;
        }else{
            return null;
        }
    }

    @Override
    public MivsTxpmtVrfctnInfoModel selectFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsTxpmtvrfctnInfoEntity info = new MivsTxpmtvrfctnInfoEntity();
        info.setMsgId(mivsIdVrfctnInfoModel.getOrig_dlv_msgid());
        info.setRcvMsgId(mivsIdVrfctnInfoModel.getOrig_rcv_msgid());

        info = mapper.selectOne(info);
        if(info == null){
            return null;
        }else{
            MivsTxpmtVrfctnInfoModel idVrfctnInfoModel = new MivsTxpmtVrfctnInfoModel();
            idVrfctnInfoModel.setPlat_date(info.getPlatDate());
            return idVrfctnInfoModel;
        }
    }

    @Override
    public void insertFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsTxpmtvrfctnFdbkEntity info = new MivsTxpmtvrfctnFdbkEntity();
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
        info.setCoNm(mivsIdVrfctnInfoModel.getCo_nm());
        info.setUniSocCdtCd(mivsIdVrfctnInfoModel.getUni_soc_cdt_cd());
        info.setRslt(mivsIdVrfctnInfoModel.getRslt());
        info.setDataResrcDt(mivsIdVrfctnInfoModel.getData_resrc_dt());
        info.setTxpmtInf(mivsIdVrfctnInfoModel.getTxpmt_inf());
        info.setCntt(mivsIdVrfctnInfoModel.getCntt());
        info.setContactNm(mivsIdVrfctnInfoModel.getContact_nm());
        info.setContactNb(mivsIdVrfctnInfoModel.getContact_nb());
        info.setRemark1(mivsIdVrfctnInfoModel.getRemark1());
        info.setRemark2(mivsIdVrfctnInfoModel.getRemark2());
        info.setRemark3(mivsIdVrfctnInfoModel.getRemark3());

        fdbkEntityMapper.insertSelective(info);
    }

    @Override
    public void updateFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel){
        MivsTxpmtvrfctnFdbkEntity info = new MivsTxpmtvrfctnFdbkEntity();
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
