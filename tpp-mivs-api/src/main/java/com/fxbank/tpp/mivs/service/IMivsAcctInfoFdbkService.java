package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsAcctInfoFdbkModel;

/**
 * @Description: 企业开销户状态反馈表操作接口
 * @Author: 王鹏
 * @Date: 2019/6/14 8:32
 */
public interface IMivsAcctInfoFdbkService {
    /**
     * 新增企业开销户状态反馈报表数据
     *
     * @param mivsAcctInfoFdbkModel
     */
    void insertStart(MivsAcctInfoFdbkModel mivsAcctInfoFdbkModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsAcctInfoFdbkModel
     */
    void updateSts(MivsAcctInfoFdbkModel mivsAcctInfoFdbkModel);
}
