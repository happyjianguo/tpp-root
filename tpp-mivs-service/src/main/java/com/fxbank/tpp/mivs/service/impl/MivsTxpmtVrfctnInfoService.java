package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoAttEntity;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvrfctnInfoAttEntityMapper;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvrfctnInfoMapper;
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
    private MivsTxpmtvrfctnInfoMapper mapper;

    @Resource
    private MivsTxpmtvrfctnInfoAttEntityMapper attMapper;

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
        MivsTxpmtvrfctnInfoEntity mivsRegvrfctnInfoEntity = mapper.selectOne(info);
        MivsTxpmtVrfctnInfoModel infoModel = new MivsTxpmtVrfctnInfoModel();

        infoModel.setPlat_date(mivsRegvrfctnInfoEntity.getPlatDate());
        infoModel.setPlat_trace(mivsRegvrfctnInfoEntity.getPlatTrace());
        infoModel.setPlat_time(mivsRegvrfctnInfoEntity.getPlatTime());
        infoModel.setTran_date(mivsRegvrfctnInfoEntity.getTranDate());
        infoModel.setSeq_no(mivsRegvrfctnInfoEntity.getSeqNo());
        infoModel.setMivs_sts(mivsRegvrfctnInfoEntity.getMivsSts());
        infoModel.setMsg_id(mivsRegvrfctnInfoEntity.getMsgId());
        infoModel.setCre_dt_tm(mivsRegvrfctnInfoEntity.getCreDtTm());
        infoModel.setInstg_drct_pty(mivsRegvrfctnInfoEntity.getInstgDrctPty());
        infoModel.setDrct_pty_nm(mivsRegvrfctnInfoEntity.getDrctPtyNm());
        infoModel.setInstg_pty(mivsRegvrfctnInfoEntity.getInstgPty());
        infoModel.setPty_nm(mivsRegvrfctnInfoEntity.getPtyNm());
        infoModel.setInstg_drct_pty(mivsRegvrfctnInfoEntity.getInstgDrctPty());
        infoModel.setInstd_pty(mivsRegvrfctnInfoEntity.getInstdPty());
        infoModel.setCo_nm(mivsRegvrfctnInfoEntity.getCoNm());
        infoModel.setUni_soc_cdt_cd(mivsRegvrfctnInfoEntity.getUniSocCdtCd());
        infoModel.setTxpyr_id_nb(mivsRegvrfctnInfoEntity.getTxpyrIdNb());
        infoModel.setOp_nm(mivsRegvrfctnInfoEntity.getOpNm());
        infoModel.setRslt(mivsRegvrfctnInfoEntity.getRslt());
        infoModel.setData_resrc_dt(mivsRegvrfctnInfoEntity.getDataResrcDt());
        infoModel.setTxpmt_inf_cnt(mivsRegvrfctnInfoEntity.getTxpmtInfCnt());
        infoModel.setProc_sts(mivsRegvrfctnInfoEntity.getProcSts());
        infoModel.setProc_cd(mivsRegvrfctnInfoEntity.getProcCd());
        infoModel.setRjct_inf(mivsRegvrfctnInfoEntity.getRjctInf());

        if(flag.equals("all")) {
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
    }

    @Override
    public void uMasterAndiAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel, String flag){
        MivsTxpmtvrfctnInfoEntity info = new MivsTxpmtvrfctnInfoEntity();
        info.setPlatDate(mivsTxpmtVrfctnInfoModel.getPlat_date());
        info.setPlatTrace(mivsTxpmtVrfctnInfoModel.getPlat_trace());
        info.setMivsSts(mivsTxpmtVrfctnInfoModel.getMivs_sts());
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
}
