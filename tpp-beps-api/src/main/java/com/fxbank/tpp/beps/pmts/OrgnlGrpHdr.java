package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 原报文主键组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT"})
public class OrgnlGrpHdr implements Serializable {

    private static final long serialVersionUID = 8418712474124260362L;
    @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
    private String OrgnlMsgId;
    @EsbSimuAnno.EsbField(type = "String", value = "313229000008")
    private String OrgnlInstgPty;
    @EsbSimuAnno.EsbField(type = "String", value = "BEPS")
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
