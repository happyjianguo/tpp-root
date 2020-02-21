package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.tpp.beps.pmts.CCMS_900_001_02_CmonConf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author : 周勇沩
 * @description: 通用处理确认报文900
 * @Date : 2020/2/20 22:56
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_900_001_02 extends DTO_BASE {

    private static final long serialVersionUID = -7488880209959372053L;

    public CCMS_900_001_02() {
        super.txDesc = "通用处理确认报文900";
    }

    private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

    public CCMS_900_001_02_CmonConf getCmonConf() {
        return CmonConf;
    }

    public void setCmonConf(CCMS_900_001_02_CmonConf comConf) {
        CmonConf = comConf;
    }

    @Override
    public String signData() {
        return this.CmonConf.signData();
    }
}