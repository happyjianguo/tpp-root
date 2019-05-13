package com.fxbank.tpp.mivs.model.sim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;
import com.fxbank.tpp.mivs.model.response.MIVS_321_001_01_RtrIdVrfctn;


/** 
* @ClassName: MIVS_321_001_01 
* @Description: 手机号码联网核查应答报文
* @author Duzhenduo
* @date 2019年4月25日 上午10:34:23 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_321_001_01 extends MODEL_BASE {

	private static final long serialVersionUID = 450987434163934038L;
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