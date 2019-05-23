package com.fxbank.tpp.mivs.service;

import com.fxbank.tpp.mivs.model.mivstables.MivsTxpmtvfctnInfoTable;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/23 10:17
 */
public interface IMivsTxpmtvfctnInfoService {
    /**
     * 新增手机号核查表数据
     *
     * @param mivsTxpmtvfctnInfoTable
     */
    void insertStart(MivsTxpmtvfctnInfoTable mivsTxpmtvfctnInfoTable);

    /**
     * 更新数据库表状态信息
     *
     * @param mivsTxpmtvfctnInfoTable
     */
    void updateSts(MivsTxpmtvfctnInfoTable mivsTxpmtvfctnInfoTable);
}
