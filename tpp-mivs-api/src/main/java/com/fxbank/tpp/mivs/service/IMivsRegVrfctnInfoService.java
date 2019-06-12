package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;

/**
 * @Description: 登记信息核查业务表操作接口
 * @Author: 王鹏
 * @Date: 2019/6/3 9:27
 */
public interface IMivsRegVrfctnInfoService {
    /**
     * 新增登记信息核查表数据
     *
     * @param mivsRegvrfctnInfoModel
     */
    void insertMaster(MivsRegVrfctnInfoModel mivsRegvrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsRegvrfctnInfoModel
     */
    void updateSts(MivsRegVrfctnInfoModel mivsRegvrfctnInfoModel);

    /**
     * 更新主表信息及插入附表信息
     *
     * @param mivsRegvrfctnInfoModel
     */
    void uMasterAndiAttached(MivsRegVrfctnInfoModel mivsRegvrfctnInfoModel);

    /**
     * 查询数据库主表信息
     *
     * @param origMsgid
     */
    MivsRegVrfctnInfoModel selectMaster(String origMsgid);

    /**
     * 查询数据库附表信息
     *
     * @param Msgid
     */
    MivsRegVrfctnInfoModel selectAttachedCnt(String Msgid, String Instgpty);

}
