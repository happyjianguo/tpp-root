package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : 周勇沩
 * @description: 通讯级确认报文
 * @Date : 2019/4/23 20:41
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_990_001_02 extends REQ_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private static final String XMLNS = "urn:cnaps:std:ccms:2010:tech:xsd:ccms.990.001.02";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    @EsbSimuAnno.EsbField(type = "Object")
    private CCMS_990_001_02_ComConf ComConf = new CCMS_990_001_02_ComConf();

    public CCMS_990_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_990_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = ComConf.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
    }

    public CCMS_990_001_02_ComConf getComConf() {
        return ComConf;
    }

    public void setComConf(CCMS_990_001_02_ComConf comConf) {
        this.ComConf = comConf;
    }

    @Override
    public String signData() {
        return ComConf.signData();
    }
}