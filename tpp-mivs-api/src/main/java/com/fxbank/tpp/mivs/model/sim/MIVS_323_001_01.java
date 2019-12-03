package com.fxbank.tpp.mivs.model.sim;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;
import com.fxbank.tpp.mivs.model.response.MIVS_323_001_01_RtrTxPmtVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息联网核查应答报文（测试用）
 * @Author: 王鹏
 * @Date: 2019/4/29 9:58
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_323_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = -9149252817699094575L;
    private static final String MESGTYPE = "mivs.323.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.323.001.01";

    private MIVS_323_001_01_RtrTxPmtVrfctn RtrTxPmtVrfctn = new MIVS_323_001_01_RtrTxPmtVrfctn();

    public MIVS_323_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_323_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_323_001_01.MESGTYPE;
        super.XMLNS= MIVS_323_001_01.XMLNS;
        this.RtrTxPmtVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.RtrTxPmtVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_323_001_01_RtrTxPmtVrfctn getRtrTxPmtVrfctn() {
        return RtrTxPmtVrfctn;
    }

    public void setRtrTxPmtVrfctn(MIVS_323_001_01_RtrTxPmtVrfctn rtrTxPmtVrfctn) {
        RtrTxPmtVrfctn = rtrTxPmtVrfctn;
    }

    @Override
    public String signData() {
        return this.RtrTxPmtVrfctn.signData();
    }

}