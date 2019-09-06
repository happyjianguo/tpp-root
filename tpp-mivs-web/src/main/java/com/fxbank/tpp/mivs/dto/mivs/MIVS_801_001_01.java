package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;
import com.fxbank.tpp.mivs.model.response.MIVS_801_001_01_SysStsNtfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业信息联网核查业务受理时间通知报文
 * @Author: 王鹏
 * @Date: 2019/7/5 14:34
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class MIVS_801_001_01 extends DTO_BASE {
    private static final long serialVersionUID = -3684747255834937414L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public MIVS_801_001_01() {
        super.txDesc = "企业信息联网核查业务受理时间通知报文801";
    }

    private MIVS_801_001_01_SysStsNtfctn sysStsNtfctn = new MIVS_801_001_01_SysStsNtfctn();

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
        return this.sysStsNtfctn.signData();
    }

    public MIVS_801_001_01_SysStsNtfctn getSysStsNtfctn() {
        return sysStsNtfctn;
    }

    public void setSysStsNtfctn(MIVS_801_001_01_SysStsNtfctn sysStsNtfctn) {
        this.sysStsNtfctn = sysStsNtfctn;
    }
}