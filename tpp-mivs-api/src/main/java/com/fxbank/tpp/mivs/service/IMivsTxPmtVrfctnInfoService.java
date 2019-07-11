package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;

import java.util.List;

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
     * detail_flag = "YES" ，查询主表+附表数据
     * detail_flag = "NO" ，仅查询主表数据
     *
     * @param mivsTxpmtVrfctnInfoModel
     */
    MivsTxpmtVrfctnInfoModel selectMasterAndAttached(MivsTxpmtVrfctnInfoModel mivsTxpmtVrfctnInfoModel);

    /**
     * 查询数据库表信息查询数据库主附表信息
     * detail_flag = "YES" ，查询主表+附表数据
     * detail_flag = "NO" ，仅查询主表数据
     *
     * @param mivsIdVrfctnInfoModel
     */
    List<MivsTxpmtVrfctnInfoModel> selectResult(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 查询数据库主表信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    MivsTxpmtVrfctnInfoModel selectFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 新增手机号核查反馈表数据
     *
     * @param mivsIdVrfctnInfoModel
     */
    void insertFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    void updateFdbk(MivsTxpmtVrfctnInfoModel mivsIdVrfctnInfoModel);
}
