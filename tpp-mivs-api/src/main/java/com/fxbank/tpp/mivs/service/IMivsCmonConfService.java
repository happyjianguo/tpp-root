package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsCmonConfModel;

/**
 * @Description: 900表操作服务
 * @Author: 王鹏
 * @Date: 2019/6/17 9:08
 */
public interface IMivsCmonConfService {
    /**
     * 新增900表数据
     *
     * @param mivsCmonConfModel
     */
    void insertStart(MivsCmonConfModel mivsCmonConfModel);
}
