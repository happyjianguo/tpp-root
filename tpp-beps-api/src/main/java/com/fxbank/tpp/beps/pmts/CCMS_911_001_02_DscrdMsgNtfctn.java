package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 报文丢弃通知报文
 * @Date : 2019/4/25 10:26
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"DscrdInf"})
public class CCMS_911_001_02_DscrdMsgNtfctn extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 6344404911792166373L;

    private static final String MESGTYPE = "ccms.911.001.02";

    @EsbSimuAnno.EsbField(type = "Object")
    private DscrdInf DscrdInf = new DscrdInf();

    public CCMS_911_001_02_DscrdMsgNtfctn() {
        super.mesgType = MESGTYPE;
    }

    @Override
    public String toString() {
        return "报文丢弃通知报文[CCMS_911_001_02][" + this.DscrdInf.getMsgId() + "][" + this.DscrdInf.getPrcCd() + "][" + this.DscrdInf.getRjctInf() + "]";
    }

    public DscrdInf getDscrdInf() {
        return DscrdInf;
    }

    public void setDscrdInf(DscrdInf dscrdInf) {
        DscrdInf = dscrdInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"OrigSndr", "OrigSndDt", "MT", "MsgId", "MsgRefId", "PrcCd", "RjctInf"})
    public static class DscrdInf implements Serializable {

        private static final long serialVersionUID = 1884306537559651697L;
        @EsbSimuAnno.EsbField(type = "String", len = 10)
        private String OrigSndr;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd HH:mm:ss")
        private String OrigSndDt;
        @EsbSimuAnno.EsbField(type = "String", value = "hvps.111.001.01")
        private String MT;
        @EsbSimuAnno.EsbField(type = "String", len = 12)
        private String MsgId;
        @EsbSimuAnno.EsbField(type = "String", len = 20)
        private String MsgRefId;
        @EsbSimuAnno.EsbField(type = "String", value = "CB1O1001")
        private String PrcCd;
        @EsbSimuAnno.EsbField(type = "String", value = "报文无法解析")
        private String RjctInf;

        public String getOrigSndr() {
            return OrigSndr;
        }

        public void setOrigSndr(String origSndr) {
            OrigSndr = origSndr;
        }

        public String getOrigSndDt() {
            return OrigSndDt;
        }

        public void setOrigSndDt(String origSndDt) {
            OrigSndDt = origSndDt;
        }

        public String getMT() {
            return MT;
        }

        public void setMT(String mT) {
            MT = mT;
        }

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        public String getMsgRefId() {
            return MsgRefId;
        }

        public void setMsgRefId(String msgRefId) {
            MsgRefId = msgRefId;
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

    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getDscrdInf().getOrigSndr() == null ? "" : this.getDscrdInf().getOrigSndr() + "|");
        sb.append(this.getDscrdInf().getOrigSndDt() == null ? "" : this.getDscrdInf().getOrigSndDt() + "|");
        sb.append(this.getDscrdInf().getMT() == null ? "" : this.getDscrdInf().getMT() + "|");
        sb.append(this.getDscrdInf().getMsgId() == null ? "" : this.getDscrdInf().getMsgId() + "|");
        sb.append(this.getDscrdInf().getMsgRefId() == null ? "" : this.getDscrdInf().getMsgRefId() + "|");
        sb.append(this.getDscrdInf().getPrcCd() == null ? "" : this.getDscrdInf().getPrcCd() + "|");
        sb.append(this.getDscrdInf().getRjctInf() == null ? "" : this.getDscrdInf().getRjctInf() + "|");
        return sb.toString();
    }

}