package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 通用处理确认报文
 * @Date : 2020/2/20 20:07
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"GrpHdr", "OrgnlGrpHdr", "CmonConfInf"})
public class CCMS_900_001_02_CmonConf extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 7763154310187493781L;

    private static final String MESGTYPE = "ccms.900.001.02";

    private GrpHdr GrpHdr = new GrpHdr();
    private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();
    private CmonConfInf CmonConfInf = new CmonConfInf();

    @Override
    public String toString() {
        return "通用处理确认报文[CCMS_900_001_02][" + this.GrpHdr.getMsgId() + "][" + this.CmonConfInf.getPrcCd() + "][" + this.CmonConfInf.getRjctInf() + "]";
    }

    public CCMS_900_001_02_CmonConf(){
        super.mesgType = MESGTYPE;
    }

    public CCMS_900_001_02_CmonConf.GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(CCMS_900_001_02_CmonConf.GrpHdr grpHdr) {
        GrpHdr = grpHdr;
    }

    public CCMS_900_001_02_CmonConf.OrgnlGrpHdr getOrgnlGrpHdr() {
        return OrgnlGrpHdr;
    }

    public void setOrgnlGrpHdr(CCMS_900_001_02_CmonConf.OrgnlGrpHdr orgnlGrpHdr) {
        OrgnlGrpHdr = orgnlGrpHdr;
    }

    public CCMS_900_001_02_CmonConf.CmonConfInf getCmonConfInf() {
        return CmonConfInf;
    }

    public void setCmonConfInf(CCMS_900_001_02_CmonConf.CmonConfInf cmonConfInf) {
        CmonConfInf = cmonConfInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk"})
    public static class GrpHdr implements Serializable {

        private static final long serialVersionUID = 695741942804747894L;

        private String MsgId;
        private String CreDtTm;
        private String SysCd;
        private String Rmk;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        public String getCreDtTm() {
            return CreDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        public String getSysCd() {
            return SysCd;
        }

        public void setSysCd(String sysCd) {
            this.SysCd = sysCd;
        }

        public String getRmk() {
            return Rmk;
        }

        public void setRmk(String rmk) {
            this.Rmk = rmk;
        }

        public InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        public InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(InstdPty instdPty) {
            this.InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstgPty")
        public static class InstgPty implements Serializable {

            private static final long serialVersionUID = -814496645868794191L;

            private String InstgDrctPty;
            private String InstgPty;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstdPty")
        public static class InstdPty implements Serializable {

            private static final long serialVersionUID = -9137860962783022375L;

            private String InstdDrctPty;
            private String InstdPty;

            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            public void setInstdDrctPty(String instdDrctPty) {
                this.InstdDrctPty = instdDrctPty;
            }

            public String getInstdPty() {
                return InstdPty;
            }

            public void setInstdPty(String instdPty) {
                this.InstdPty = instdPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT"})
    public static class OrgnlGrpHdr implements Serializable {

        private static final long serialVersionUID = -4212364067523875173L;

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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"PrcSts", "PrcCd", "PtyId", "PtyPrcCd", "RjctInf", "PrcDt", "NetgRnd"})
    public static class CmonConfInf implements Serializable {

        private static final long serialVersionUID = 5485526951875655276L;

        private String PrcSts;
        private String PtyId;
        private String PtyPrcCd;
        private String PrcCd;
        private String RjctInf;
        private String PrcDt;
        private String NetgRnd;

        public String getPrcSts() {
            return PrcSts;
        }

        public void setPrcSts(String prcSts) {
            PrcSts = prcSts;
        }

        public String getPtyId() {
            return PtyId;
        }

        public void setPtyId(String ptyId) {
            PtyId = ptyId;
        }

        public String getPtyPrcCd() {
            return PtyPrcCd;
        }

        public void setPtyPrcCd(String ptyPrcCd) {
            PtyPrcCd = ptyPrcCd;
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

        public String getPrcDt() {
            return PrcDt;
        }

        public void setPrcDt(String prcDt) {
            PrcDt = prcDt;
        }

        public String getNetgRnd() {
            return NetgRnd;
        }

        public void setNetgRnd(String netgRnd) {
            NetgRnd = netgRnd;
        }
    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getGrpHdr().getMsgId() + "|");
        sb.append(this.getGrpHdr().getCreDtTm() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGrpHdr().getSysCd() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        sb.append(this.getCmonConfInf().getPrcSts() + "|");
        sb.append(this.getCmonConfInf().getPrcCd() == null ? "" : this.getCmonConfInf().getPrcCd() + "|");
        sb.append(this.getCmonConfInf().getPtyId() == null ? "" : this.getCmonConfInf().getPtyId() + "|");
        sb.append(this.getCmonConfInf().getPtyPrcCd() == null ? "" : this.getCmonConfInf().getPtyPrcCd() + "|");
        sb.append(this.getCmonConfInf().getPrcDt() == null ? "" : this.getCmonConfInf().getPrcDt() + "|");
        sb.append(this.getCmonConfInf().getNetgRnd() == null ? "" : this.getCmonConfInf().getNetgRnd() + "|");
        return sb.toString();
    }
}