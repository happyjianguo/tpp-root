package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: NPC处理信息组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"PrcSts", "PrcCd", "RjctInf", "NetgDt", "NetgRnd", "SttlmDt", "RcvTm", "TrnsmtTm"})
public class NPCPrcInf implements Serializable {

    private static final long serialVersionUID = 8596137135886958221L;

    private String PrcSts;
    private String PrcCd;
    private String RjctInf;
    private String NetgDt;
    private String NetgRnd;
    private String SttlmDt;
    private String RcvTm;
    private String TrnsmtTm;

    public String getPrcSts() {
        return PrcSts;
    }

    public void setPrcSts(String prcSts) {
        PrcSts = prcSts;
    }

    public String getPrcCd() {
        return PrcCd;
    }

    public void setPrcCd(String prcCd) {
        PrcCd = prcCd;
    }

    public String getRjctInf() {
        return RjctInf;
    }

    public void setRjctInf(String rjctInf) {
        RjctInf = rjctInf;
    }

    public String getNetgDt() {
        return NetgDt;
    }

    public void setNetgDt(String netgDt) {
        NetgDt = netgDt;
    }

    public String getNetgRnd() {
        return NetgRnd;
    }

    public void setNetgRnd(String netgRnd) {
        NetgRnd = netgRnd;
    }

    public String getSttlmDt() {
        return SttlmDt;
    }

    public void setSttlmDt(String sttlmDt) {
        SttlmDt = sttlmDt;
    }

    public String getRcvTm() {
        return RcvTm;
    }

    public void setRcvTm(String rcvTm) {
        RcvTm = rcvTm;
    }

    public String getTrnsmtTm() {
        return TrnsmtTm;
    }

    public void setTrnsmtTm(String trnsmtTm) {
        TrnsmtTm = trnsmtTm;
    }

}
