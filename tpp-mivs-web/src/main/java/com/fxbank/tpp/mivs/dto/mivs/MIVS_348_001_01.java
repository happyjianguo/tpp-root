package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_348_001_01_TxPmtVrfctnFdbk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息核查结果疑义反馈报文 （测试用）
 * @Author: 王鹏
 * @Date: 2019/5/14 16:00
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_348_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_348_001_01() {
        super.txDesc = "纳税信息核查结果疑义反馈报文348";
    }

    private MIVS_348_001_01_TxPmtVrfctnFdbk TxPmtVrfctnFdbk = new MIVS_348_001_01_TxPmtVrfctnFdbk();

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
    public MIVS_348_001_01_TxPmtVrfctnFdbk getTxPmtVrfctnFdbk() {
        return TxPmtVrfctnFdbk;
    }

    /**
     * @param txPmtVrfctnFdbk the txPmtVrfctnFdbk to set
     */
    public void setSysSts(MIVS_348_001_01_TxPmtVrfctnFdbk txPmtVrfctnFdbk) {
        TxPmtVrfctnFdbk = txPmtVrfctnFdbk;
    }


}