package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 公告信息确认报文
 * @Author: 王鹏
 * @Date: 2019/7/4 15:05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "OrgnlMsg", "FreeFrmtInf" })
public class MIVS_333_001_01_FreeFrmtConf implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 831482237335365435L;
    private MsgHdr MsgHdr = new MsgHdr();
    private OrgnlMsg OrgnlMsg = new OrgnlMsg();
    private FreeFrmtInf FreeFrmtInf = new FreeFrmtInf();

    @Override
    public String toString() {
        return "公告信息确认[MIVS_333_001_01]";
    }

    public MIVS_333_001_01_FreeFrmtConf.MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MIVS_333_001_01_FreeFrmtConf.MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public MIVS_333_001_01_FreeFrmtConf.OrgnlMsg getOrgnlMsg() {
        return OrgnlMsg;
    }

    public void setOrgnlMsg(MIVS_333_001_01_FreeFrmtConf.OrgnlMsg orgnlMsg) {
        OrgnlMsg = orgnlMsg;
    }

    public MIVS_333_001_01_FreeFrmtConf.FreeFrmtInf getFreeFrmtInf() {
        return FreeFrmtInf;
    }

    public void setFreeFrmtInf(MIVS_333_001_01_FreeFrmtConf.FreeFrmtInf freeFrmtInf) {
        FreeFrmtInf = freeFrmtInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = 2619914400385918290L;
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

        public MIVS_333_001_01_FreeFrmtConf.MsgHdr.InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(MIVS_333_001_01_FreeFrmtConf.MsgHdr.InstgPty instgPty) {
            InstgPty = instgPty;
        }

        public MIVS_333_001_01_FreeFrmtConf.MsgHdr.InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(MIVS_333_001_01_FreeFrmtConf.MsgHdr.InstdPty instdPty) {
            InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 4391407684083798793L;
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
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -8910636535776056648L;
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
    @XmlType(propOrder = { "MsgId", "InstgPty"})
    public static class OrgnlMsg implements Serializable{
        private static final long serialVersionUID = -7690569786043589615L;
        private String MsgId = null;
        private InstgPty InstgPty = new InstgPty();

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        public InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(InstgPty instgPty) {
            InstgPty = instgPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="OrgnlMsg.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 4889667858591444433L;
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
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgCntt" })
    public static class FreeFrmtInf implements Serializable{
        private static final long serialVersionUID = 2809184497321898804L;
        private String MsgCntt = null;

        public String getMsgCntt() {
            return MsgCntt;
        }

        public void setMsgCntt(String msgCntt) {
            MsgCntt = msgCntt;
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
        sb.append(this.getOrgnlMsg().getMsgId() + "|");
        sb.append(this.getOrgnlMsg().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getOrgnlMsg().getInstgPty().getInstgPty() + "|");
        sb.append(this.getFreeFrmtInf().getMsgCntt() + "|");

        return sb.toString();
    }

}
