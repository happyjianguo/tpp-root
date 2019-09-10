package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;

import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/5 14:47
 */
public interface IMivsSysStsNtfctnService {
    /**
     * 新增企业信息联网核查业务受理时间查询
     *
     * @param mivsSysStsNtfctnModel
     */
    void insertInfo(MivsSysStsNtfctnModel mivsSysStsNtfctnModel);
    /**
     * 新增企业信息联网核查业务受理时间通知
     *
     * @param mivsSysStsNtfctnModel
     */
    String insertMsg(List<MivsSysStsNtfctnModel> mivsSysStsNtfctnModel);
    /**
     * 查询企业信息联网核查业务受理时间通知
     *
     * @param mivsSysStsNtfctnModel
     */
    List<MivsSysStsNtfctnModel> selectMsg(MivsSysStsNtfctnModel mivsSysStsNtfctnModel);
}
