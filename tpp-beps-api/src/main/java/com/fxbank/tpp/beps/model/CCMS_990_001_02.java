package com.fxbank.tpp.beps.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 通讯级确认应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-23 20:41:27
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends MODEL_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private static final String MESGTYPE = "ccms.990.001.02";
    private static final String XMLNS= "urn:cnaps:std:ccms:2010:tech:xsd:ccms.990.001.02";
    private static final String XMLNS_XSI= "http://www.w3.org/2001/XMLSchema-instance";

    private CCMS_990_001_02_ComConf ComConf = new CCMS_990_001_02_ComConf();

    public CCMS_990_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_990_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CCMS_990_001_02.MESGTYPE;
        super.XMLNS= CCMS_990_001_02.XMLNS;
        super.XMLNS_XSI= CCMS_990_001_02.XMLNS_XSI;
    }

    /**
     * @return the comConf
     */
    public CCMS_990_001_02_ComConf getComConf() {
        return ComConf;
    }

    /**
     * @param comConf the comConf to set
     */
    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.ComConf = comConf;
    }

    @Override
    public String signData() {
        return ComConf.signData();
    }
}