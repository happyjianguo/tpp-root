package com.fxbank.tpp.beps.model.sim;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.model.CCMS_900_001_02_CmonConf;
import com.fxbank.tpp.beps.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 通用处理确认报文 CCMS.900.001.02
 * @Author: 王鹏
 * @Date: 2019/5/7 8:55
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_900_001_02 extends MODEL_BASE {
    private static final long serialVersionUID = -5636290168567829116L;
    private static final String MESGTYPE = "ccms.900.001.02";
    private static final String XMLNS= "urn:cnaps:std:ccms:2010:tech:xsd:ccms.900.001.02";
    private static final String XMLNS_XSI= "http://www.w3.org/2001/XMLSchema-instance";

    private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

    public CCMS_900_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_900_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CCMS_900_001_02.MESGTYPE;
        super.XMLNS= CCMS_900_001_02.XMLNS;
        super.XMLNS_XSI= CCMS_900_001_02.XMLNS_XSI;
    }

    public CCMS_900_001_02_CmonConf getCmonConf() {
        return CmonConf;
    }

    public void setCmonConf(CCMS_900_001_02_CmonConf dscrdMsgNtfctn) {
        CmonConf = dscrdMsgNtfctn;
    }

    @Override
    public String signData() {
        return CmonConf.signData();
    }
}