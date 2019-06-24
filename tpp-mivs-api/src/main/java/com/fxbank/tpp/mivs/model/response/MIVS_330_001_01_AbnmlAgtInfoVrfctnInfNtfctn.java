package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/6/6 15:56
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "AbnmlVrfctnInf"})
public class MIVS_330_001_01_AbnmlAgtInfoVrfctnInfNtfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 2156357114349059231L;
    private MsgHdr MsgHdr = new MsgHdr();
    private AbnmlVrfctnInf AbnmlVrfctnInf = new AbnmlVrfctnInf();

    @Override
    public String toString() {
        return "纳税信息联网核查应答报文[MIVS_323_001_01]";
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
        private static final long serialVersionUID = -3755667721306861443L;
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

        public InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(InstgPty instgPty) {
            InstgPty = instgPty;
        }

        public InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(InstdPty instdPty) {
            InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -3242208246904042876L;
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
            private static final long serialVersionUID = 4665034521714781470L;
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
    @XmlType(propOrder = { "OrgnlInstgPty" })
    public static class AbnmlVrfctnInf implements Serializable{
        private static final long serialVersionUID = 3207207101563741789L;
        private String  OrgnlInstgPty = null;
        private String AbnmlType = null;
        private String DESC = null;

        public String getOrgnlInstgPty() {
            return OrgnlInstgPty;
        }

        public void setOrgnlInstgPty(String orgnlInstgPty) {
            OrgnlInstgPty = orgnlInstgPty;
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
    }

    @Override
    public String signData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(this.getAbnmlVrfctnInf().getOrgnlInstgPty().getAbnmlType() + "|");
//        sb.append(this.getAbnmlVrfctnInf().getOrgnlInstgPty().getDESC() + "|");
//
//        return sb.toString();
        return null;
    }

}