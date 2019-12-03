package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;

import java.util.List;

/**
 * @Description: 手机号核查表操作接口
 * @Author: 王鹏
 * @Date: 2019/5/21 16:20
 */
public interface IMivsIdVrfctnInfoService {
    /**
     * 新增手机号核查表数据
     *
     * @param mivsIdVrfctnInfoModel
     */
    void insertStart(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    void updateSts(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 查询数据库表信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    List<MivsIdVrfctnInfoModel> selectResult(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 查询数据库主表信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    MivsIdVrfctnInfoModel selectFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 新增手机号核查反馈表数据
     *
     * @param mivsIdVrfctnInfoModel
     */
    void insertFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsIdVrfctnInfoModel
     */
    void updateFdbk(MivsIdVrfctnInfoModel mivsIdVrfctnInfoModel);
}
