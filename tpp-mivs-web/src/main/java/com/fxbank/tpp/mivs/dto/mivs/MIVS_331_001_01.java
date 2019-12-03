package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业异常核查通知报文
 * @Author: 王鹏
 * @Date: 2019/6/6 16:24
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_331_001_01 extends DTO_BASE {
    private static final long serialVersionUID = -852433851834186525L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_331_001_01() {
        super.txDesc = "企业异常核查通知报文331";
    }

    private MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn AbnmlCoInfoVrfctnInfNtfctn = new MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn();

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
        return this.AbnmlCoInfoVrfctnInfNtfctn.signData();
    }

    public MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn getAbnmlCoInfoVrfctnInfNtfctn() {
        return AbnmlCoInfoVrfctnInfNtfctn;
    }

    public void setAbnmlCoInfoVrfctnInfNtfctn(MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn abnmlAgtInfoVrfctnInfNtfctn) {
        AbnmlCoInfoVrfctnInfNtfctn = abnmlAgtInfoVrfctnInfNtfctn;
    }


}