package com.fxbank.tpp.beps.dto.beps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.tpp.beps.model.CCMS_990_001_02_ComConf;

/**
 * @Description: 人行请求990
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:27:48
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends DTO_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private CCMS_990_001_02_ComConf ComConf = new CCMS_990_001_02_ComConf();

    public CCMS_990_001_02() {
        super.txDesc = "通信级确认990";
    }

    /**
     * @return the comConf
     */
    public CCMS_990_001_02_ComConf getComConf() {
        return this.ComConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.ComConf = comConf;
    }

    @Override
    public String signData() {
        return this.ComConf.signData();
    }

}