package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : 周勇沩
 * @description: 实时客户支付协议管理报文
 * @Date : 2020/2/28 08:07
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_351_001_01 extends REQ_BASE {

    private static final long serialVersionUID = -8355675423783779001L;

    private static final String XMLNS = "urn:cnaps:std:beps:2010:tech:xsd:beps.351.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    @EsbSimuAnno.EsbField(type="Object")
    private BEPS_351_001_01_PtcSnReq PtcSnReq = new BEPS_351_001_01_PtcSnReq();

    public BEPS_351_001_01() {
        super(null, 0, 0, 0);
    }

    public BEPS_351_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = PtcSnReq.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
        PtcSnReq.getGrpHdr().setMsgId(super.msgId());
        PtcSnReq.getGrpHdr().setCreDtTm(super.creDtTm());
    }

    public BEPS_351_001_01_PtcSnReq getPtcSnReq() {
        return PtcSnReq;
    }

    public void setPtcSnReq(BEPS_351_001_01_PtcSnReq ptcSnReq) {
        this.PtcSnReq = ptcSnReq;
    }

    @Override
    public String signData() {
        return PtcSnReq.signData();
    }
}
