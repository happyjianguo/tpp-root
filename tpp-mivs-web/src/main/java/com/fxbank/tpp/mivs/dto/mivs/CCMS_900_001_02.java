package com.fxbank.tpp.mivs.dto.mivs;

import com.fxbank.tpp.mivs.model.response.CCMS_900_001_02_CmonConf;

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
    private static final long serialVersionUID = -4125716324225975090L;
    private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

    public CCMS_900_001_02() {
        super.txDesc = "通用处理确认报文900";
    }

    public CCMS_900_001_02_CmonConf getCmonConf() {
        return CmonConf;
    }

    public void setCmonConf(CCMS_900_001_02_CmonConf cmonConf) {
        CmonConf = cmonConf;
    }

    @Override
    public String signData() {
        return this.CmonConf.signData();
    }

}