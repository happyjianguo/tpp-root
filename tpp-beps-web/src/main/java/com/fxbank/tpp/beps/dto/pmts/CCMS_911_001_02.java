package com.fxbank.tpp.beps.dto.pmts;

import com.fxbank.tpp.beps.pmts.CCMS_911_001_02_DscrdMsgNtfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : 周勇沩
 * @description: 报文丢弃通知911
 * @Date : 2020/2/20 22:55
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_911_001_02 extends DTO_BASE {

    private static final long serialVersionUID = -7337313752075414901L;

    public CCMS_911_001_02() {
        super.txDesc = "报文丢弃通知911";
    }

    private CCMS_911_001_02_DscrdMsgNtfctn DscrdMsgNtfctn = new CCMS_911_001_02_DscrdMsgNtfctn();

    public CCMS_911_001_02_DscrdMsgNtfctn getDscrdMsgNtfctn() {
        return DscrdMsgNtfctn;
    }

    public void setDscrdMsgNtfctn(CCMS_911_001_02_DscrdMsgNtfctn dscrdMsgNtfctn) {
        DscrdMsgNtfctn = dscrdMsgNtfctn;
    }

    @Override
    public String signData() {
        return this.DscrdMsgNtfctn.signData();
    }

}