package com.fxbank.tpp.mivs.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvfctnInfoAttEntity;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvfctnInfoEntity;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvfctnInfoAttMapper;
import com.fxbank.tpp.mivs.mapper.TxPmtVrfctn.MivsTxpmtvfctnInfoMapper;
import com.fxbank.tpp.mivs.model.mivstables.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsTxPmtVrfctnInfoService;

import javax.annotation.Resource;

/**
 * @Description: 纳税信息核查数据库操作
 * @Author: 王鹏
 * @Date: 2019/5/23 10:14
 */
@Service(version = "1.0.0")
public class MivsTxpmtVrfctnInfoService implements IMivsTxPmtVrfctnInfoService {

    @Resource
    private MivsTxpmtvfctnInfoMapper mapper;

    @Resource
    private MivsTxpmtvfctnInfoAttMapper attMapper;

    @Override
    public void insertMaster(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel) {
        MivsTxpmtvfctnInfoEntity info = new MivsTxpmtvfctnInfoEntity();
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
    public void insertAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel){
        MivsTxpmtvfctnInfoAttEntity attInfo = new MivsTxpmtvfctnInfoAttEntity();
        attInfo.setPlatDate(mivsTxpmtVrfctnInfoModel.getPlat_date());
        attInfo.setPlatTrace(mivsTxpmtVrfctnInfoModel.getPlat_trace());
        attInfo.setPlatTime(mivsTxpmtVrfctnInfoModel.getPlat_time());
        attInfo.setMsgId(mivsTxpmtVrfctnInfoModel.getMsg_id());
        attInfo.setCreDtTm(mivsTxpmtVrfctnInfoModel.getCre_dt_tm());
        attInfo.setTxpmtInfNb(mivsTxpmtVrfctnInfoModel.getTxpmt_inf_nb());
        attInfo.setTxAuthCd(mivsTxpmtVrfctnInfoModel.getTx_auth_cd());
        attInfo.setTxAuthNm(mivsTxpmtVrfctnInfoModel.getTx_auth_nm());
        attInfo.setTxpyrSts(mivsTxpmtVrfctnInfoModel.getTxpyr_sts());

        attMapper.insertSelective(attInfo);
    }

    @Override
    public void updateSts(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel) {
        MivsTxpmtvfctnInfoEntity info = new MivsTxpmtvfctnInfoEntity();
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
    }
}
