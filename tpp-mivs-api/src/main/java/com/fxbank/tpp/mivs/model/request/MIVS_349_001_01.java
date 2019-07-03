package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 登记信息核查反馈报文
 * @Author: 王鹏
 * @Date: 2019/7/2 19:54
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_349_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = 3458176062327523387L;
    private static final String MESGTYPE = "mivs.349.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.349.001.01";

    private MIVS_349_001_01_RegVrfctnFdbk RegVrfctnFdbk = new MIVS_349_001_01_RegVrfctnFdbk();

    public MIVS_349_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_349_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_349_001_01.MESGTYPE;
        super.XMLNS= MIVS_349_001_01.XMLNS;
        this.RegVrfctnFdbk.getMsgHdr().setMsgId(super.msgId());
        this.RegVrfctnFdbk.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_349_001_01_RegVrfctnFdbk getRegVrfctnFdbk() {
        return RegVrfctnFdbk;
    }

    public void setRegVrfctnFdbk(MIVS_349_001_01_RegVrfctnFdbk regVrfctnFdbk) {
        RegVrfctnFdbk = regVrfctnFdbk;
    }

    @Override
    public String signData() {
        return this.RegVrfctnFdbk.signData();
    }
}
