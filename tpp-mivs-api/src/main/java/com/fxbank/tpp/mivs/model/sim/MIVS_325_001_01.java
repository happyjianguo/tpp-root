package com.fxbank.tpp.mivs.model.sim;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;
import com.fxbank.tpp.mivs.model.response.MIVS_325_001_01_RtrRegVrfctn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/20 16:30
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_325_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = -9149252817699094575L;
    private static final String MESGTYPE = "mivs.323.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.323.001.01";

    private MIVS_325_001_01_RtrRegVrfctn RtrRegVrfctn = new MIVS_325_001_01_RtrRegVrfctn();

    public MIVS_325_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_325_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_325_001_01.MESGTYPE;
        super.XMLNS= MIVS_325_001_01.XMLNS;
        this.RtrRegVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.RtrRegVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_325_001_01_RtrRegVrfctn getRtrRegVrfctn() {
        return RtrRegVrfctn;
    }

    public void setRtrRegVrfctn(MIVS_325_001_01_RtrRegVrfctn rtrRegVrfctn) {
        RtrRegVrfctn = rtrRegVrfctn;
    }

    @Override
    public String signData() {
        return this.RtrRegVrfctn.signData();
    }

}