package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_347_001_01_IdVrfctnFdbk;

/**
 * @Description: 手机号码核查结果疑义反馈报文 （测试用）
 * @Author: 王鹏
 * @Date: 2019/5/14 10:31
 */
public class MIVS_347_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_347_001_01() {
        super.txDesc = "纳税信息网核查请求320";
    }

    private MIVS_347_001_01_IdVrfctnFdbk IdVrfctnFdbk = new MIVS_347_001_01_IdVrfctnFdbk();

    /**
     * @return the comConf
     */
    public CCMS_990_001_02_ComConf getComConf() {
        return this.comConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.comConf = comConf;
    }

    @Override
    public String signData() {
        return this.comConf.signData();
    }

    /**
     * @return the getSysSts
     */
    public MIVS_347_001_01_IdVrfctnFdbk getIdVrfctnFdbk() {
        return IdVrfctnFdbk;
    }

    /**
     * @param idVrfctnFdbk the idVrfctnFdbk to set
     */
    public void setSysSts(MIVS_347_001_01_IdVrfctnFdbk idVrfctnFdbk) {
        IdVrfctnFdbk = idVrfctnFdbk;
    }


}