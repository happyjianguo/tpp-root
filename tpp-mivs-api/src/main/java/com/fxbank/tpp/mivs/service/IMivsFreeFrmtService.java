package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsFreeFrmtModel;

import java.util.List;

/**
 * @Description: 企业通知报文
 * @Author: 王鹏
 * @Date: 2019/7/3 16:46
 */
public interface IMivsFreeFrmtService {
    /**
     * 新增企业通知表数据
     *
     * @param mivsFreeFrmtModel
     */
    void insertInfo(MivsFreeFrmtModel mivsFreeFrmtModel);

    /**
     * 更新企业通知表信息
     *
     * @param mivsFreeFrmtModel
     */
    void updateInfo(MivsFreeFrmtModel mivsFreeFrmtModel);

    /**
     * 查询数据库表信息
     *
     * @param mivsFreeFrmtModel
     */
    List<MivsFreeFrmtModel> selectResult(MivsFreeFrmtModel mivsFreeFrmtModel);

    /**
     * 新增企业通知确认表数据
     *
     * @param mivsFreeFrmtModel
     */
    void insertConf(MivsFreeFrmtModel mivsFreeFrmtModel);

    /**
     * 更新公告信息确认表信息，更新公告确认表“是否已确认字段”
     *
     * @param mivsFreeFrmtModel
     */
    void upInfoAndConf(MivsFreeFrmtModel mivsFreeFrmtModel);
}
