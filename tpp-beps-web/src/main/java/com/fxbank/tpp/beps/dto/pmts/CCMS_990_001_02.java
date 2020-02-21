package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.tpp.beps.pmts.CCMS_990_001_02_ComConf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author : 周勇沩
 * @description: 通信级确认报文990
 * @Date : 2020/2/20 22:54
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends DTO_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    public CCMS_990_001_02() {
        super.txDesc = "通信级确认990";
    }

    private CCMS_990_001_02_ComConf ComConf = new CCMS_990_001_02_ComConf();

    public CCMS_990_001_02_ComConf getComConf() {
        return this.ComConf;
    }

    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.ComConf = comConf;
    }

    @Override
    public String signData() {
        return this.ComConf.signData();
    }

}