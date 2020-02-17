package com.fxbank.tpp.beps.model.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.model.MODEL_BASE;

/**
 * 
 * @类描述：   CCMS_303_001_02报文头
 * @项目名称：tpp-beps-api
 * @包名： com.fxbank.tpp.beps.model.headreq
 * @类名称：CCMS_303_001_02
 * @创建人：lit
 * @创建时间：2020年1月17日下午4:38:41
 * @修改人：lit
 * @修改时间：2020年1月17日下午4:38:41
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail *@qq.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class CCMS_303_001_02 extends MODEL_BASE {
	
	private static final long serialVersionUID = 2807609504637074085L;
	private static final String MESGTYPE = "ccms.303.001.02";
    private static final String XMLNS = "urn:cnaps:std:ccms:2010:tech:xsd:ccms.303.001.02";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    private CCMS_303_001_02_FreeFrmt FreeFrmt = new CCMS_303_001_02_FreeFrmt();
    
    

    public CCMS_303_001_02_FreeFrmt getFreeFrmt() {
		return FreeFrmt;
	}

	public void setFreeFrmt(CCMS_303_001_02_FreeFrmt freeFrmt) {
		FreeFrmt = freeFrmt;
	}

	public CCMS_303_001_02() {
        super(null, 0, 0, 0);
    }

    public CCMS_303_001_02(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = CCMS_303_001_02.MESGTYPE;
        super.XMLNS= CCMS_303_001_02.XMLNS;
        super.XMLNS_XSI=CCMS_303_001_02.XMLNS_XSI;
        this.FreeFrmt.getGrpHdr().setMsgId(super.msgId());
        this.FreeFrmt.getGrpHdr().setCreDtTm(super.creDtTm());
    }



    @Override
    public String signData() {
        return this.FreeFrmt.signData();
    }

    @Override
    public String toString() {
        return "CCMS_303_001_02{" +
                "FreeFrmt=" + FreeFrmt +
                ", mesgType='" + MESGTYPE + '\'' +
                ", XMLNS='" + XMLNS + '\'' +
                ", XMLNS_XSI='" + XMLNS_XSI + '\'' +
                '}';
    }
}