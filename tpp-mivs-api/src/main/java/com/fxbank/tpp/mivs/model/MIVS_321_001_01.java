package com.fxbank.tpp.mivs.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 手机号码联网核查申请报文
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:51:24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_321_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private static final String MESGTYPE = "mivs.321.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.321.001.01";

    private MIVS_321_001_01_RtrIdVrfctn RtrIdVrfctn = new MIVS_321_001_01_RtrIdVrfctn();

    public MIVS_321_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_321_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_321_001_01.MESGTYPE;
        super.XMLNS= MIVS_321_001_01.XMLNS;
        this.RtrIdVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.RtrIdVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_321_001_01_RtrIdVrfctn getRtrIdVrfctn() {
		return RtrIdVrfctn;
	}

	public void setRtrIdVrfctn(MIVS_321_001_01_RtrIdVrfctn rtrIdVrfctn) {
		RtrIdVrfctn = rtrIdVrfctn;
	}

	@Override
    public String signData() {
        return this.RtrIdVrfctn.signData();
    }

}