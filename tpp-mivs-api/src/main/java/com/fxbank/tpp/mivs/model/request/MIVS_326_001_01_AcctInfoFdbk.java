package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 企业开销户状态反馈请求报文
 * @Author: 王鹏
 * @Date: 2019/6/6 14:56
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "Fdbk" })
public class MIVS_326_001_01_AcctInfoFdbk implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 549446073281010017L;
    private MsgHdr MsgHdr = new MsgHdr();
    private Fdbk Fdbk = new Fdbk();

    @Override
    public String toString() {
        return "企业开销户状态反馈请求报文[MIVS_326_001_01]";
    }

    public MIVS_326_001_01_AcctInfoFdbk.MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MIVS_326_001_01_AcctInfoFdbk.MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public Fdbk getFdbk() {
        return Fdbk;
    }

    public void setFdbk(Fdbk fdbk) {
        Fdbk = fdbk;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = -581103924009687799L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        public String getCreDtTm() {
            return CreDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            CreDtTm = creDtTm;
        }

        public MIVS_326_001_01_AcctInfoFdbk.MsgHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(MIVS_326_001_01_AcctInfoFdbk.MsgHdr.InstgPty instgPty) {
            InstgPty = instgPty;
        }

        public MIVS_326_001_01_AcctInfoFdbk.MsgHdr.InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(MIVS_326_001_01_AcctInfoFdbk.MsgHdr.InstdPty instdPty) {
            InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 7162761168675476519L;
            private String InstgDrctPty = null;
            private String DrctPtyNm = null;
            private String InstgPty = null;
            private String PtyNm = null;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                InstgDrctPty = instgDrctPty;
            }

            public String getDrctPtyNm() {
                return DrctPtyNm;
            }

            public void setDrctPtyNm(String drctPtyNm) {
                DrctPtyNm = drctPtyNm;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                InstgPty = instgPty;
            }

            public String getPtyNm() {
                return PtyNm;
            }

            public void setPtyNm(String ptyNm) {
                PtyNm = ptyNm;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -3747427290097139117L;
            private String InstdDrctPty = null;
            private String InstdPty = null;

            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            public void setInstdDrctPty(String instdDrctPty) {
                InstdDrctPty = instdDrctPty;
            }

            public String getInstdPty() {
                return InstdPty;
            }

            public void setInstdPty(String instdPty) {
                InstdPty = instdPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "EntNm", "TraNm", "UniSocCdtCd", "AcctSts", "ChngDt" })
    public static class Fdbk implements Serializable{
        private static final long serialVersionUID = -646497094763301349L;
        private String EntNm = null;
        private String TraNm = null;
        private String UniSocCdtCd = null;
        private String AcctSts = null;
        private String ChngDt = null;

        public String getEntNm() {
            return EntNm;
        }

        public void setEntNm(String entNm) {
            EntNm = entNm;
        }

        public String getTraNm() {
            return TraNm;
        }

        public void setTraNm(String traNm) {
            TraNm = traNm;
        }

        public String getUniSocCdtCd() {
            return UniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            UniSocCdtCd = uniSocCdtCd;
        }

        public String getAcctSts() {
            return AcctSts;
        }

        public void setAcctSts(String acctSts) {
            AcctSts = acctSts;
        }

        public String getChngDt() {
            return ChngDt;
        }

        public void setChngDt(String chngDt) {
            ChngDt = chngDt;
        }
    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMsgHdr().getMsgId() + "|");
        sb.append(this.getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getDrctPtyNm() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getPtyNm() + "|");
        sb.append(this.getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getFdbk().getEntNm() == null ? ""
                : this.getFdbk().getEntNm() + "|");
        sb.append(this.getFdbk().getTraNm() == null ? ""
                : this.getFdbk().getTraNm() + "|");
        sb.append(this.getFdbk().getUniSocCdtCd() + "|");
        sb.append(this.getFdbk().getAcctSts() + "|");
        sb.append(this.getFdbk().getChngDt() + "|");
        return sb.toString();
    }

}