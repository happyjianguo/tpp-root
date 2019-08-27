package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 企业异常核查通知报文
 * @Author: 王鹏
 * @Date: 2019/6/6 16:25
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "AbnmlVrfctnInf"})
public class MIVS_331_001_01_AbnmlCoInfoVrfctnInfNtfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -2863031334391611648L;
    private MsgHdr MsgHdr = new MsgHdr();
    private AbnmlVrfctnInf AbnmlVrfctnInf = new AbnmlVrfctnInf();

    @Override
    public String toString() {
        return "企业异常核查通知报文[MIVS_331_001_01]";
    }

    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public AbnmlVrfctnInf getAbnmlVrfctnInf() {
        return AbnmlVrfctnInf;
    }

    public void setAbnmlVrfctnInf(AbnmlVrfctnInf abnmlVrfctnInf) {
        AbnmlVrfctnInf = abnmlVrfctnInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = -2785694973139152062L;
        private String MsgId = null;
        private String CreDtTm = null;
        private MsgHdr.InstgPty InstgPty = new MsgHdr.InstgPty();
        private MsgHdr.InstdPty InstdPty = new MsgHdr.InstdPty();

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

        public MsgHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(MsgHdr.InstgPty instgPty) {
            InstgPty = instgPty;
        }

        public MsgHdr.InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(MsgHdr.InstdPty instdPty) {
            InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 9140123940993810355L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                InstgDrctPty = instgDrctPty;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                InstgPty = instgPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="InstdPty")
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -6570654560312734212L;
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
    @XmlType(propOrder = { "AbnmlCo", "AbnmlPhNb", "AbnmlType", "DESC" })
    public static class AbnmlVrfctnInf implements Serializable{
        private static final long serialVersionUID = 7112339269854798464L;
        private AbnmlCo AbnmlCo = new AbnmlCo();
        private AbnmlPhNb AbnmlPhNb = new AbnmlPhNb();
        private String AbnmlType = null;
        private String DESC = null;

        public AbnmlCo getAbnmlCo() {
            return AbnmlCo;
        }

        public void setAbnmlCo(AbnmlCo abnmlCo) {
            AbnmlCo = abnmlCo;
        }

        public AbnmlPhNb getAbnmlPhNb() {
            return AbnmlPhNb;
        }

        public void setAbnmlPhNb(AbnmlPhNb abnmlPhNb) {
            AbnmlPhNb = abnmlPhNb;
        }

        public String getAbnmlType() {
            return AbnmlType;
        }

        public void setAbnmlType(String abnmlType) {
            AbnmlType = abnmlType;
        }

        public String getDESC() {
            return DESC;
        }

        public void setDESC(String DESC) {
            this.DESC = DESC;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "CoNm","UniSocCdtCd" })
        public static class AbnmlCo implements Serializable{
            private static final long serialVersionUID = -7108357698060831798L;
            private String CoNm = null;
            private String UniSocCdtCd = null;

            public String getCoNm() {
                return CoNm;
            }

            public void setCoNm(String coNm) {
                CoNm = coNm;
            }

            public String getUniSocCdtCd() {
                return UniSocCdtCd;
            }

            public void setUniSocCdtCd(String uniSocCdtCd) {
                UniSocCdtCd = uniSocCdtCd;
            }
        }
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "PhNb","Nm" })
        public static class AbnmlPhNb implements Serializable{
            private static final long serialVersionUID = -2229809197478941207L;
            private String PhNb = null;
            private String Nm = null;

            public String getPhNb() {
                return PhNb;
            }

            public void setPhNb(String phNb) {
                PhNb = phNb;
            }

            public String getNm() {
                return Nm;
            }

            public void setNm(String nm) {
                Nm = nm;
            }
        }
    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMsgHdr().getMsgId() + "|");
        sb.append(this.getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstdPty().getInstdPty() + "|");
        if(this.getAbnmlVrfctnInf().getAbnmlCo() != null) {
            sb.append(this.getAbnmlVrfctnInf().getAbnmlCo().getCoNm() + "|");
            sb.append(this.getAbnmlVrfctnInf().getAbnmlCo().getUniSocCdtCd() + "|");
        }
        if(this.getAbnmlVrfctnInf().getAbnmlPhNb() != null) {
            sb.append(this.getAbnmlVrfctnInf().getAbnmlPhNb().getPhNb() + "|");
            sb.append(this.getAbnmlVrfctnInf().getAbnmlPhNb().getNm() + "|");
        }
        sb.append(this.getAbnmlVrfctnInf().getAbnmlType() + "|");
        sb.append(this.getAbnmlVrfctnInf().getDESC() + "|");

        return sb.toString();
    }
}
