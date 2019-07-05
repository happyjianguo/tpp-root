package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/5 14:47
 */
public interface IMivsSysStsNtfctnService {
    /**
     * 新增企业信息联网核查业务受理时间通知
     *
     * @param mivsSysStsNtfctnModel
     */
    void insertInfo(MivsSysStsNtfctnModel mivsSysStsNtfctnModel);
}
