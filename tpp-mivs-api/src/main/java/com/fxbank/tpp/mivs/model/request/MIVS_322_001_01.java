package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 纳税信息联网核查申请报文
 * @Author: 王鹏
 * @Date: 2019/4/29 9:43
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_322_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = -3586125898011104640L;
    private static final String MESGTYPE = "mivs.322.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.322.001.01";

    private MIVS_322_001_01_GetTxPmtVrfctn GetTxPmtVrfctn = new MIVS_322_001_01_GetTxPmtVrfctn();

    public MIVS_322_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_322_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_322_001_01.MESGTYPE;
        super.XMLNS= MIVS_322_001_01.XMLNS;
        this.GetTxPmtVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.GetTxPmtVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /**
     * @return the getTxPmtVrfctn
     */
    public MIVS_322_001_01_GetTxPmtVrfctn getTxPmtVrfctn() {
        return GetTxPmtVrfctn;
    }

    /**
     * @param getTxPmtVrfctn the getTxPmtVrfctn to set
     */
    public void setRtrIdVrfctn(MIVS_322_001_01_GetTxPmtVrfctn getTxPmtVrfctn) {
        GetTxPmtVrfctn = getTxPmtVrfctn;
    }

    @Override
    public String signData() {
        return this.GetTxPmtVrfctn.signData();
    }
}
