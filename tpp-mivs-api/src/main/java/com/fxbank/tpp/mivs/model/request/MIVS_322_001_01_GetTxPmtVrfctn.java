package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 纳税信息联网核查申请报文主节点
 * @Author: 王鹏
 * @Date: 2019/4/29 9:48
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "VryDef" })
public class MIVS_322_001_01_GetTxPmtVrfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = -7866286955362967083L;
    private MsgHdr MsgHdr = new MsgHdr();
    private VryDef VryDef = new VryDef();

    @Override
    public String toString() {
        return "纳税信息联网核查申请报文[MIVS_322_001_01]";
    }

    /**
     * @return the msgHdr
     */
    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    /**
     * @return the vryDef
     */
    public VryDef getVryDef() {
        return VryDef;
    }

    /**
     * @param vryDef the vryDef to set
     */
    public void setVryDef(VryDef vryDef) {
        this.VryDef = vryDef;
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
        private static final long serialVersionUID = -7832110571163013845L;
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
//        @XmlType(propOrder = { "InstgDrctPty", "DrctPtyNm", "InstgPty", "PtyNm" })
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 3725799073736097150L;
            private String InstgDrctPty = null;
            private String DrctPtyNm = null;
            private String InstgPty = null;
            private String PtyNm = null;

            /**
             * @return the instgDrctPty
             */
            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            /**
             * @return the ptyNm
             */
            public String getPtyNm() {
                return PtyNm;
            }

            /**
             * @param ptyNm the ptyNm to set
             */
            public void setPtyNm(String ptyNm) {
                this.PtyNm = ptyNm;
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
             * @return the drctPtyNm
             */
            public String getDrctPtyNm() {
                return DrctPtyNm;
            }

            /**
             * @param drctPtyNm the drctPtyNm to set
             */
            public void setDrctPtyNm(String drctPtyNm) {
                this.DrctPtyNm = drctPtyNm;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(propOrder = { "InstdDrctPty", "InstdPty" })
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = 4474632357265632738L;
            private String InstdDrctPty = null;
            private String InstdPty = null;

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
    @XmlType(propOrder = { "CoNm", "UniSocCdtCd", "TaxPayerId", "OpNm" })
    public static class VryDef implements Serializable{
        private static final long serialVersionUID = 5043496881254050491L;
        private String CoNm = null;
        private String UniSocCdtCd = null;
        private String TaxPayerId = null;
        private String OpNm = null;

        /**
         * @return the CoNm
         */
        public String getCoNm() {
            return CoNm;
        }

        /**
         * @param companyName the CoNm to set
         */
        public void setCoNm(String companyName) {
            this.CoNm = companyName;
        }

        /**
         * @return the uniSocCdtCd
         */
        public String getUniSocCdtCd() {
            return UniSocCdtCd;
        }

        /**
         * @param uniSocCdtCd the uniSocCdtCd to set
         */
        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.UniSocCdtCd = uniSocCdtCd;
        }

        /**
         * @return the taxPayerId
         */
        public String getTaxPayerId() {
            return TaxPayerId;
        }

        /**
         * @param taxPayerId the id to set
         */
        public void setTaxPayerId(String taxPayerId) {
            this.TaxPayerId = taxPayerId;
        }

        /**
         * @return the opNm
         */
        public String getOpNm() {
            return OpNm;
        }

        /**
         * @param opNm the nm to set
         */
        public void setOpNm(String opNm) {
            this.OpNm = opNm;
        }

    }

    @Override
    public String signData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(this.getMsgHdr().getMsgId() + "|");
//        sb.append(this.getMsgHdr().getCreDtTm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getDrctPtyNm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getPtyNm() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstdPty().getInstdPty() + "|");
//        sb.append(this.getVryDef().getCoNm() + "|");
//        sb.append(this.getVryDef().getUniSocCdtCd() == null ? ""
//                : this.getVryDef().getUniSocCdtCd() + "|");
//        sb.append(this.getVryDef().getTaxPayerId() == null ? ""
//                : this.getVryDef().getTaxPayerId() + "|");
//        sb.append(this.getVryDef().getOpNm() + "|");
//        return sb.toString();
        return null;
    }

}
