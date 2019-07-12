package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;

import java.util.List;

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
     * 更新主表信息及插入附表信息
     * detail_flag = "YES" ，更新主表、增加附表数据
     * flag = "NO" ，仅更新主表数据
     *
     * @param mivsRegvrfctnInfoModel
     */
    void uMasterAndiAttached(MivsRegVrfctnInfoModel mivsRegvrfctnInfoModel);

    /**
     * 查询数据库表信息
     * detail_flag = "YES" ，查询主表+附表数据
     * detail_flag = "NO" ，仅查询主表数据
     *
     * @param mivsRegVrfctnInfoModel
     */
    MivsRegVrfctnInfoModel selectMasterAndAttached(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel);

    /**
     * 查询数据库表信息
     *
     * @param mivsRegVrfctnInfoModel
     */
    List<MivsRegVrfctnInfoModel> selectResult(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel);

    /**
     * 查询数据库主表信息
     *
     * @param mivsRegVrfctnInfoModel
     */
    MivsRegVrfctnInfoModel selectFdbk(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel);

    /**
     * 新增登记信息核查反馈表数据
     *
     * @param mivsRegVrfctnInfoModel
     */
    void insertFdbk(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsRegVrfctnInfoModel
     */
    void updateFdbk(MivsRegVrfctnInfoModel mivsRegVrfctnInfoModel);
}
