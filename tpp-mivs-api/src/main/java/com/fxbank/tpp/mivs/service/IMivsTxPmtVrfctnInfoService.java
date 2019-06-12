package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;

/**
 * @Description: 纳税信息核查数据库操作接口
 * @Author: 王鹏
 * @Date: 2019/5/23 10:17
 */
public interface IMivsTxPmtVrfctnInfoService {
    /**
     * 新增纳税信息核查表数据
     *
     * @param mivsTxpmtVrfctnInfoModel
     */
    void insertMaster(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel);

    /**
     * 新增纳税信息核查附表数据
     *
     * @param mivsTxpmtVrfctnInfoModel
     */
    void insertAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsTxpmtVrfctnInfoModel
     */
    void updateSts(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel);
}
