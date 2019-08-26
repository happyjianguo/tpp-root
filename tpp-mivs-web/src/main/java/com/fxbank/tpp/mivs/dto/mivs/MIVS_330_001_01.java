package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 机构异常核查通知报文 测试用
 * @Author: 王鹏
 * @Date: 2019/6/6 15:53
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_330_001_01 extends DTO_BASE {
    private static final long serialVersionUID = -852433851834186525L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_330_001_01() {
        super.txDesc = "机构异常核查通知报文330";
    }

    private MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn AbnmlAgtInfoVrfctnInfNtfctn = new MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn();

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
        return this.AbnmlAgtInfoVrfctnInfNtfctn.signData();
    }

    public MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn getAbnmlAgtInfoVrfctnInfNtfctn() {
        return AbnmlAgtInfoVrfctnInfNtfctn;
    }

    public void setAbnmlAgtInfoVrfctnInfNtfctn(MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn abnmlAgtInfoVrfctnInfNtfctn) {
        AbnmlAgtInfoVrfctnInfNtfctn = abnmlAgtInfoVrfctnInfNtfctn;
    }


}