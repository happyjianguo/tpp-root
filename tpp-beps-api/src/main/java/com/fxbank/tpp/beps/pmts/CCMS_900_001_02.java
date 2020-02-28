package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : 周勇沩
 * @description: 通用处理确认报文
 * @Date : 2019/5/7 8:55
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_900_001_02 extends REQ_BASE {

    private static final long serialVersionUID = 4367448796395422253L;

    private static final String XMLNS = "urn:cnaps:std:ccms:2010:tech:xsd:ccms.900.001.02";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    @EsbSimuAnno.EsbField(type = "Object")
    private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

    public CCMS_900_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_900_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CmonConf.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
        CmonConf.getGrpHdr().setMsgId(super.msgId());
        CmonConf.getGrpHdr().setCreDtTm(super.creDtTm());
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