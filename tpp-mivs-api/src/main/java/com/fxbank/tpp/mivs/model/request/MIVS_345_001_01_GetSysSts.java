package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 人行企业信息联网核查查业务受理时间查询请求报文 mivs.345.001.01
 * @Author: 王鹏
 * @Date: 2019/4/30 10:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "QueInf" })
public class MIVS_345_001_01_GetSysSts implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -5795231853706204889L;
    private MsgHdr MsgHdr = new MsgHdr();
    private QueInf QueInf = new QueInf();

    @Override
    public String toString() {
        return "纳税信息联网核查申请报文[MIVS_345_001_01]";
    }

    /**
     * @return the msgHdr
     */
    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    /**
     * @return the queInf
     */
    public QueInf getQueInf() {
        return QueInf;
    }

    /**
     * @param queInf the queInf to set
     */
    public void setQueInf(QueInf queInf) {
        this.QueInf = queInf;
    }

    /**
     * @param msgHdr the msgHdr to set
     */
    public void setMsgHdr(MsgHdr msgHdr) {
        this.MsgHdr = msgHdr;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = 2786761560470579792L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();

        /**
         * @return the msgId
         */
        public String getMsgId() {
            return MsgId;
        }

        /**
         * @return the instdPty
         */
        public InstdPty getInstdPty() {
            return InstdPty;
        }

        /**
         * @param instdPty the instdPty to set
         */
        public void setInstdPty(InstdPty instdPty) {
            this.InstdPty = instdPty;
        }

        /**
         * @return the instgPty
         */
        public InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        /**
         * @return the creDtTm
         */
        public String getCreDtTm() {
            return CreDtTm;
        }

        /**
         * @param creDtTm the creDtTm to set
         */
        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        /**
         * @param msgId the msgId to set
         */
        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "InstgDrctPty", "InstgPty" })
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 2267139539513933332L;
            private String InstgDrctPty = null;
            private String InstgPty = null;

            /**
             * @return the instgDrctPty
             */
            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            /**
             * @return the instgPty
             */
            public String getInstgPty() {
                return InstgPty;
            }

            /**
             * @param instgPty the instgPty to set
             */
            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "InstdDrctPty", "InstdPty" })
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -7963369455171749739L;
            private String InstdDrctPty = "0000";
            private String InstdPty = "0000";

            /**
             * @return the instdDrctPty
             */
            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            /**
             * @return the instdPty
             */
            public String getInstdPty() {
                return InstdPty;
            }

            /**
             * @param instdPty the instdPty to set
             */
            public void setInstdPty(String instdPty) {
                this.InstdPty = instdPty;
            }

            /**
             * @param instdDrctPty the instdDrctPty to set
             */
            public void setInstdDrctPty(String instdDrctPty) {
                this.InstdDrctPty = instdDrctPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "SysInd", "QueDt"})
    public static class QueInf implements Serializable{
        private static final long serialVersionUID = 7500158135784137006L;
        private String SysInd = null;
        private String QueDt = null;

        /**
         * @return the SysInd
         */
        public String getSysInd() {
            return SysInd;
        }

        /**
         * @param sysInd the sysInd to set
         */
        public void setSysInd(String sysInd) {
            this.SysInd = sysInd;
        }

        /**
         * @return the queDt
         */
        public String getQueDt() {
            return QueDt;
        }

        /**
         * @param queDt the queDt to set
         */
        public void setQueDt(String queDt) {
            this.QueDt = queDt;
        }
    }

    @Override
    public String signData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(this.getMsgHdr().getMsgId() + "|");
//        sb.append(this.getMsgHdr().getCreDtTm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdPty() + "|");
//        sb.append(this.getQueInf().getSysInd() + "|");
//        sb.append(this.getQueInf().getQueDt() + "|");
//        return sb.toString();
        return null;
    }

}