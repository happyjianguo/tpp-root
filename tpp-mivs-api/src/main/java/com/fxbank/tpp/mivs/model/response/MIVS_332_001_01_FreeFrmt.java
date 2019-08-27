package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 企业信息通知报文
 * @Author: 王鹏
 * @Date: 2019/7/3 16:39
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "FreeFrmtInf"})
public class MIVS_332_001_01_FreeFrmt implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -9132894158613287468L;
    private MsgHdr MsgHdr = new MsgHdr();
    private FreeFrmtInf FreeFrmtInf = new FreeFrmtInf();

    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public FreeFrmtInf getFreeFrmtInf() {
        return FreeFrmtInf;
    }

    public void setFreeFrmtInf(FreeFrmtInf freeFrmtInf) {
        FreeFrmtInf = freeFrmtInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = -6498677314926143554L;
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
            private static final long serialVersionUID = -8493690807578795040L;
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
            private static final long serialVersionUID = 5552654868462596192L;
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
    @XmlType(propOrder = { "RplyFlag", "MsgCntt" })
    public static class FreeFrmtInf implements Serializable{
        private static final long serialVersionUID = 2228809806599744753L;
        private String RplyFlag = null;
        private String MsgCntt = null;

        public String getRplyFlag() {
            return RplyFlag;
        }

        public void setRplyFlag(String rplyFlag) {
            RplyFlag = rplyFlag;
        }

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
        sb.append(this.getFreeFrmtInf().getRplyFlag() + "|");
        sb.append(this.getFreeFrmtInf().getMsgCntt() + "|");

        return sb.toString();
    }
}
