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
     * 新增纳税信息核查表数据
     * flag = "all" ，更新主表、增加附表数据
     * flag = "master" ，仅更新主表数据
     *
     * @param mivsTxpmtVrfctnInfoModel
     */
    void uMasterAndiAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel, String flag);

    /**
     * 查询数据库主附表信息
     * flag = "all" ，查询主表+附表数据
     * flag = "master" ，仅查询主表数据
     *
     * @param origMsgid
     */
    MivsTxpmtVrfctnInfoModel selectMasterAndAttached(String origMsgid, String origInstgPty, String flag);
}
