package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author : 周勇沩
 * @description: 原报文主键组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT"})
public class OrgnlGrpHdr implements Serializable {

	private static final long serialVersionUID = 8418712474124260362L;
	
	private String OrgnlMsgId;
    private String OrgnlInstgPty;
    private String OrgnlMT;

    public String getOrgnlMsgId() {
        return OrgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.OrgnlMsgId = orgnlMsgId;
    }

    public String getOrgnlInstgPty() {
        return OrgnlInstgPty;
    }

    public void setOrgnlInstgPty(String orgnlInstgPty) {
        this.OrgnlInstgPty = orgnlInstgPty;
    }

    public String getOrgnlMT() {
        return OrgnlMT;
    }

    public void setOrgnlMT(String orgnlMT) {
        this.OrgnlMT = orgnlMT;
    }

}
