package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description: 支付协议管理应答报文
 * @author     : 周勇沩
 * @Date       : 2020/2/21 18:21
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_352_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = 4367448796395422253L;

    private static final String MESGTYPE = "beps.352.001.01";
    private static final String XMLNS = "urn:cnaps:std:ccms:2010:tech:xsd:beps.352.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    //private CCMS_900_001_02_CmonConf CmonConf = new CCMS_900_001_02_CmonConf();

    public BEPS_352_001_01() {
        super(null, 0, 0, 0);
    }

    public BEPS_352_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MESGTYPE;
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
        //this.GetIdVrfctn.getMsgHdr().setMsgId(super.msgId());
        //this.GetIdVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /*
    public CCMS_900_001_02_CmonConf getCmonConf() {
        return CmonConf;
    }

    public void setCmonConf(CCMS_900_001_02_CmonConf dscrdMsgNtfctn) {
        CmonConf = dscrdMsgNtfctn;
    }
     */

    @Override
    public String signData() {
        //return CmonConf.signData();
        return null;
    }
}