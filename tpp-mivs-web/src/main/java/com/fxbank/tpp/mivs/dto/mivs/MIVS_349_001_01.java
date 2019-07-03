package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.request.MIVS_349_001_01_RegVrfctnFdbk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/2 20:37
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_349_001_01 extends DTO_BASE {

    private static final long serialVersionUID = 7043577496218974114L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_349_001_01() {
        super.txDesc = "登记信息核查结果疑义反馈报文349";
    }

    private MIVS_349_001_01_RegVrfctnFdbk regVrfctnFdbk = new MIVS_349_001_01_RegVrfctnFdbk();

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

    public MIVS_349_001_01_RegVrfctnFdbk getRegVrfctnFdbk() {
        return regVrfctnFdbk;
    }

    public void setRegVrfctnFdbk(MIVS_349_001_01_RegVrfctnFdbk regVrfctnFdbk) {
        this.regVrfctnFdbk = regVrfctnFdbk;
    }
}