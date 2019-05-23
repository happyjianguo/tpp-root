package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02_ComConf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 通用处理确认报文 CCMS.900.001.02
 * @Author: 王鹏
 * @Date: 2019/5/7 8:52
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_900_001_02 extends DTO_BASE {
    private static final long serialVersionUID = 3833828532687872260L;
    private CCMS_990_001_02_ComConf comConf = new CCMS_990_001_02_ComConf();

    public CCMS_900_001_02() {
        super.txDesc = "通用处理确认报文CCMS.900.001.02";
    }

    private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

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

    public CCMS_900_001_02_CmonConf getCmonConf() {
        return CmonConf;
    }

    public void setCmonConf(CCMS_900_001_02_CmonConf comConf) {
        CmonConf = comConf;
    }


}