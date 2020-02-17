package com.fxbank.tpp.beps.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: CCMS_911_001_02 
* @Description: 报文丢弃通知报文
* @author Duzhenduo
* @date 2019年4月25日 上午10:25:07 
*  
*/
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class CCMS_911_001_02 extends MODEL_BASE {

	private static final long serialVersionUID = -657349610366272597L;
	private static final String MESGTYPE = "ccms.911.001.02";
    private static final String XMLNS= "urn:cnaps:std:ccms:2010:tech:xsd:ccms.911.001.02";
    private static final String XMLNS_XSI= "http://www.w3.org/2001/XMLSchema-instance";

    private CCMS_911_001_02_DscrdMsgNtfctn DscrdMsgNtfctn = new CCMS_911_001_02_DscrdMsgNtfctn();

    public CCMS_911_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_911_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CCMS_911_001_02.MESGTYPE;
        super.XMLNS= CCMS_911_001_02.XMLNS;
        super.XMLNS_XSI= CCMS_911_001_02.XMLNS_XSI;
    }

    public CCMS_911_001_02_DscrdMsgNtfctn getDscrdMsgNtfctn() {
		return DscrdMsgNtfctn;
	}

	public void setDscrdMsgNtfctn(CCMS_911_001_02_DscrdMsgNtfctn dscrdMsgNtfctn) {
		DscrdMsgNtfctn = dscrdMsgNtfctn;
	}

	@Override
    public String signData() {
        return DscrdMsgNtfctn.signData();
    }
}