package com.fxbank.tpp.mivs.model.sim;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;
import com.fxbank.tpp.mivs.model.response.MIVS_346_001_01_RtrSysSts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 企业信息联网核查查业务受理时间查询（测试）
 * @Author: 王鹏
 * @Date: 2019/5/10 9:02
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_346_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = -9149252817699094575L;
    private static final String MESGTYPE = "mivs.346.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.346.001.01";

    private MIVS_346_001_01_RtrSysSts RtrSysSts = new MIVS_346_001_01_RtrSysSts();

    public MIVS_346_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_346_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_346_001_01.MESGTYPE;
        super.XMLNS= MIVS_346_001_01.XMLNS;
        this.RtrSysSts.getMsgHdr().setMsgId(super.msgId());
        this.RtrSysSts.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_346_001_01_RtrSysSts getRtrSysSts() {
        return RtrSysSts;
    }

    public void setRtrSysSts(MIVS_346_001_01_RtrSysSts rtrSysSts) {
        RtrSysSts = rtrSysSts;
    }

    @Override
    public String signData() {
        return this.RtrSysSts.signData();
    }

}