package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 通讯级确认报文
 * @Date : 2019/4/23 20:41
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"ConfInf"})
public class CCMS_990_001_02_ComConf extends REP_BASE implements SIGN_DATA {

    private static final long serialVersionUID = 2612342428152631207L;

    private static final String MESGTYPE = "ccms.990.001.02";

    private ConfInf ConfInf = new ConfInf();

    public CCMS_990_001_02_ComConf() {
        super.mesgType = MESGTYPE;
    }

    @Override
    public String toString() {
        return "通讯级确认报文[CCMS_990_001_02][" + this.ConfInf.getMsgId() + "][" + this.ConfInf.getMsgPrcCd() + "]";
    }

    public ConfInf getConfInf() {
        return ConfInf;
    }

    public void setConfInf(ConfInf confInf) {
        this.ConfInf = confInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"OrigSndr", "OrigSndDt", "MT", "MsgId", "MsgRefId", "MsgPrcCd"})
    public static class ConfInf implements Serializable {

        private static final long serialVersionUID = -901278219681758441L;

        private String OrigSndr;
        private String OrigSndDt;
        private String MT;
        private String MsgId;
        private String MsgRefId;
        private String MsgPrcCd;

        public String getOrigSndr() {
            return OrigSndr;
        }

        public String getMsgPrcCd() {
            return MsgPrcCd;
        }

        public void setMsgPrcCd(String msgPrcCd) {
            this.MsgPrcCd = msgPrcCd;
        }

        public String getMsgRefId() {
            return MsgRefId;
        }

        public void setMsgRefId(String msgRefId) {
            this.MsgRefId = msgRefId;
        }

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        public String getMT() {
            return MT;
        }

        public void setMT(String mT) {
            this.MT = mT;
        }

        public String getOrigSndDt() {
            return OrigSndDt;
        }

        public void setOrigSndDt(String origSndDt) {
            this.OrigSndDt = origSndDt;
        }

        public void setOrigSndr(String origSndr) {
            this.OrigSndr = origSndr;
        }

    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getConfInf().getOrigSndr() == null ? "" : this.getConfInf().getOrigSndr() + "|");
        sb.append(this.getConfInf().getOrigSndDt() == null ? "" : this.getConfInf().getOrigSndDt() + "|");
        sb.append(this.getConfInf().getMT() == null ? "" : this.getConfInf().getMT() + "|");
        sb.append(this.getConfInf().getMsgId() == null ? "" : this.getConfInf().getMsgId() + "|");
        sb.append(this.getConfInf().getMsgRefId() == null ? "" : this.getConfInf().getMsgRefId() + "|");
        sb.append(this.getConfInf().getMsgPrcCd() == null ? "" : this.getConfInf().getMsgPrcCd() + "|");
        return sb.toString();
    }

}