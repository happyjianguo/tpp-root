package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsIdVrfctnInfoModel;

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
}
