package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 人行企业信息联网核查查业务受理时间查询请求报文
 * @Author: 王鹏
 * @Date: 2019/4/30 10:25
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_345_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = -8645602514515434625L;
    private static final String MESGTYPE = "mivs.345.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.345.001.01";

    private MIVS_345_001_01_GetSysSts GetSysSts = new MIVS_345_001_01_GetSysSts();

    public MIVS_345_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_345_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_345_001_01.MESGTYPE;
        super.XMLNS= MIVS_345_001_01.XMLNS;
        this.GetSysSts.getMsgHdr().setMsgId(super.msgId());
        this.GetSysSts.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /**
     * @return the getSysSts
     */
    public MIVS_345_001_01_GetSysSts getSysSts() {
        return GetSysSts;
    }

    /**
     * @param getSysSts the getSysSts to set
     */
    public void setRtrIdVrfctn(MIVS_345_001_01_GetSysSts getSysSts) {
        GetSysSts = getSysSts;
    }

    @Override
    public String signData() {
        return this.GetSysSts.signData();
    }
}
