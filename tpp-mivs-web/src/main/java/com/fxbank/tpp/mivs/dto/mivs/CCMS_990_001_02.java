package com.fxbank.tpp.mivs.dto.mivs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.tpp.mivs.model.CCMS_990_001_02_COMCONF;

@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends DTO_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private CCMS_990_001_02_COMCONF comConf = new CCMS_990_001_02_COMCONF();

    public CCMS_990_001_02() {
        super.txDesc = "通信级确认";
    } 

    /**
     * @return the comConf
     */
    public CCMS_990_001_02_COMCONF getComConf() {
        return this.comConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_COMCONF comConf) {
        this.comConf = comConf;
    }

    @Override
    public String signData() {
        return this.comConf.signData();
    }

}