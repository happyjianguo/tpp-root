package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业开销户状态反馈请求报文
 * @Author: 王鹏
 * @Date: 2019/6/6 14:55
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_326_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = -8735362521978419657L;
    private static final String MESGTYPE = "mivs.326.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.326.001.01";

    private MIVS_326_001_01_AcctInfoFdbk AcctInfoFdbk = new MIVS_326_001_01_AcctInfoFdbk();

    public MIVS_326_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_326_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_326_001_01.MESGTYPE;
        super.XMLNS= MIVS_326_001_01.XMLNS;
        this.AcctInfoFdbk.getMsgHdr().setMsgId(super.msgId());
        this.AcctInfoFdbk.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_326_001_01_AcctInfoFdbk getAcctInfoFdbk() {
        return AcctInfoFdbk;
    }

    public void setAcctInfoFdbk(MIVS_326_001_01_AcctInfoFdbk acctInfoFdbk) {
        AcctInfoFdbk = acctInfoFdbk;
    }

    @Override
    public String signData() {
        return this.AcctInfoFdbk.signData();
    }

}
