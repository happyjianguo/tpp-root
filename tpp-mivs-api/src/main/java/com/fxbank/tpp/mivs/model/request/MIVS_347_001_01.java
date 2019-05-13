package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 请求人行“手机号码核查结果疑义反馈报文”
 * @Author: 王鹏
 * @Date: 2019/5/5 9:14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_347_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = 7372090657419787388L;
    private static final String MESGTYPE = "mivs.347.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.322.001.01";

    private MIVS_347_001_01_IdVrfctnFdbk IdVrfctnFdbk = new MIVS_347_001_01_IdVrfctnFdbk();

    public MIVS_347_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_347_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_347_001_01.MESGTYPE;
        super.XMLNS= MIVS_347_001_01.XMLNS;
        this.IdVrfctnFdbk.getMsgHdr().setMsgId(super.msgId());
        this.IdVrfctnFdbk.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /**
     * @return the IdVrfctnFdbk
     */
    public MIVS_347_001_01_IdVrfctnFdbk getIdVrfctnFdbk() {
        return IdVrfctnFdbk;
    }

    /**
     * @param idVrfctnFdbk the getSysSts to set
     */
    public void setRtrIdVrfctn(MIVS_347_001_01_IdVrfctnFdbk idVrfctnFdbk) {
        IdVrfctnFdbk = idVrfctnFdbk;
    }

    @Override
    public String signData() {
        return this.IdVrfctnFdbk.signData();
    }
}
