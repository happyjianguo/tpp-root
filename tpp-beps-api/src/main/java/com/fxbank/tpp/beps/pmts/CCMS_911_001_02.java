package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : 周勇沩
 * @description: 报文丢弃通知报文
 * @Date : 2019/4/25 10:25
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_911_001_02 extends REQ_BASE {

    private static final long serialVersionUID = -1687385296687443808L;

    private static final String XMLNS = "urn:cnaps:std:ccms:2010:tech:xsd:ccms.911.001.02";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    private CCMS_911_001_02_DscrdMsgNtfctn DscrdMsgNtfctn = new CCMS_911_001_02_DscrdMsgNtfctn();

    public CCMS_911_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_911_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = DscrdMsgNtfctn.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
    }

    public CCMS_911_001_02_DscrdMsgNtfctn getDscrdMsgNtfctn() {
        return DscrdMsgNtfctn;
    }

    public void setDscrdMsgNtfctn(CCMS_911_001_02_DscrdMsgNtfctn dscrdMsgNtfctn) {
        DscrdMsgNtfctn = dscrdMsgNtfctn;
    }

    @Override
    public String signData() {
        return DscrdMsgNtfctn.signData();
    }
}