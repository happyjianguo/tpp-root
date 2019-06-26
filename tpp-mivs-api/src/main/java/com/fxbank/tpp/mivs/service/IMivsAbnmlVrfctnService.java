package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsAbnmlVrfctnModel;

/**
 * @Description: 机构异常核查通知表操作服务
 * @Author: 王鹏
 * @Date: 2019/6/20 7:28
 */
public interface IMivsAbnmlVrfctnService {
    /**
     * 新增机构异常核查通知表数据
     *
     * @param mivsAbnmlVrfctnModel
     */
    void insertStart(MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel);

    /**
     * 更新机构异常核查通知表信息
     *
     * @param mivsAbnmlVrfctnModel
     */
    void updateSts(MivsAbnmlVrfctnModel mivsAbnmlVrfctnModel);
}
