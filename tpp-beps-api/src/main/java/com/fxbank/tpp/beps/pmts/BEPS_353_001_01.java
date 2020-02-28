package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 周勇沩
 * @Description: 客户身份认证回执报文
 * @date 2020/2/21 10:12:54
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_353_001_01 extends REQ_BASE {

    private static final long serialVersionUID = -763446990530918663L;
    private static final String XMLNS = "urn:cnaps:std:beps:2010:tech:xsd:beps.353.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    @EsbSimuAnno.EsbField(type = "Object")
    private BEPS_353_001_01_TxAuthReq txAuthReq = new BEPS_353_001_01_TxAuthReq();

    public BEPS_353_001_01() {
        super(null, 0, 0, 0);
    }

    public BEPS_353_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = txAuthReq.getMesgType();
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
        txAuthReq.getGrpHdr().setMsgId(super.msgId());
        txAuthReq.getGrpHdr().setCreDtTm(super.creDtTm());
    }

    public BEPS_353_001_01_TxAuthReq getTxAuthReq() {
        return txAuthReq;
    }

    public void setTxAuthReq(BEPS_353_001_01_TxAuthReq txAuthReq) {
        this.txAuthReq = txAuthReq;
    }

    @Override
    public String signData() {
        return txAuthReq.signData();
    }
}
