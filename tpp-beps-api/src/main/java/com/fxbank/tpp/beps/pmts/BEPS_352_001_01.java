package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 周勇沩
 * @Description: 实时客户支付协议管理报文
 * @date 2020/2/21 10:12:54
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_352_001_01 extends REQ_BASE {

    private static final long serialVersionUID = -7323215792195194660L;

    private static final String XMLNS = "urn:cnaps:std:beps:2010:tech:xsd:beps.352.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    @EsbSimuAnno.EsbField(type = "Object")
    private BEPS_352_001_01_ResFrPtcSn ResFrPtcSn = new BEPS_352_001_01_ResFrPtcSn();

    public BEPS_352_001_01() {
        super(null, 0, 0, 0);
    }

    public BEPS_352_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = ResFrPtcSn.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
        ResFrPtcSn.getGrpHdr().setMsgId(super.msgId());
        ResFrPtcSn.getGrpHdr().setCreDtTm(super.creDtTm());
    }

    public BEPS_352_001_01_ResFrPtcSn getResFrPtcSn() {
        return ResFrPtcSn;
    }

    public void setResFrPtcSn(BEPS_352_001_01_ResFrPtcSn ResFrPtcSn) {
        this.ResFrPtcSn = ResFrPtcSn;
    }

    @Override
    public String signData() {
        return ResFrPtcSn.signData();
    }
}

