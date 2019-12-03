package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息核查反馈报文
 * @Author: 王鹏
 * @Date: 2019/5/13 17:45
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_348_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = 1481529906446874267L;
    private static final String MESGTYPE = "mivs.348.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.348.001.01";

    private MIVS_348_001_01_TxPmtVrfctnFdbk TxPmtVrfctnFdbk = new MIVS_348_001_01_TxPmtVrfctnFdbk();

    public MIVS_348_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_348_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_348_001_01.MESGTYPE;
        super.XMLNS= MIVS_348_001_01.XMLNS;
        this.TxPmtVrfctnFdbk.getMsgHdr().setMsgId(super.msgId());
        this.TxPmtVrfctnFdbk.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /**
     * @return the TxPmtVrfctnFdbk
     */
    public MIVS_348_001_01_TxPmtVrfctnFdbk getTxPmtVrfctnFdbk() {
        return TxPmtVrfctnFdbk;
    }

    /**
     * @param txPmtVrfctnFdbk the getSysSts to set
     */
    public void setRtrIdVrfctn(MIVS_348_001_01_TxPmtVrfctnFdbk txPmtVrfctnFdbk) {
        TxPmtVrfctnFdbk = txPmtVrfctnFdbk;
    }

    @Override
    public String signData() {
        return this.TxPmtVrfctnFdbk.signData();
    }
}
