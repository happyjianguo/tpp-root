package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 登记信息联网核查申请
 * @Author: 王鹏
 * @Date: 2019/5/20 16:29
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_324_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = -8735362521978419657L;
    private static final String MESGTYPE = "mivs.324.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.324.001.01";

    private MIVS_324_001_01_GetRegVrfctn GetRegVrfctn = new MIVS_324_001_01_GetRegVrfctn();

    public MIVS_324_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_324_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_324_001_01.MESGTYPE;
        super.XMLNS= MIVS_324_001_01.XMLNS;
        this.GetRegVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.GetRegVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_324_001_01_GetRegVrfctn getGetRegVrfctn() {
        return GetRegVrfctn;
    }

    public void setGetRegVrfctn(MIVS_324_001_01_GetRegVrfctn getRegVrfctn) {
        GetRegVrfctn = getRegVrfctn;
    }

    @Override
    public String signData() {
        return this.GetRegVrfctn.signData();
    }
}
