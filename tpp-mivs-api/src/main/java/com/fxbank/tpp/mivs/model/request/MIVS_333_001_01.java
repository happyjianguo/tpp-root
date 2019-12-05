package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description: 公告信息确认报文
 * @Author: 王鹏
 * @Date: 2019/7/4 15:04
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_333_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = 7329083920138703048L;

    private static final String MESGTYPE = "mivs.333.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.333.001.01";

    private MIVS_333_001_01_FreeFrmtConf FreeFrmtConf = new MIVS_333_001_01_FreeFrmtConf();

    public MIVS_333_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_333_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_333_001_01.MESGTYPE;
        super.XMLNS= MIVS_333_001_01.XMLNS;
        this.FreeFrmtConf.getMsgHdr().setMsgId(super.msgId());
        this.FreeFrmtConf.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public MIVS_333_001_01_FreeFrmtConf getFreeFrmtConf() {
        return FreeFrmtConf;
    }

    public void setFreeFrmtConf(MIVS_333_001_01_FreeFrmtConf freeFrmtConf) {
        this.FreeFrmtConf = freeFrmtConf;
    }

    @Override
    public String signData() {
        return this.FreeFrmtConf.signData();
    }
}
