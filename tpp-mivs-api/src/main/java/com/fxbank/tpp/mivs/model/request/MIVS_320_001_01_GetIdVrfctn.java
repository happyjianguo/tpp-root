package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description: 手机号码联网核查申请报文主节点
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:51:24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "VryDef" })
public class MIVS_320_001_01_GetIdVrfctn implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = 2906655856390529936L;
    private MsgHdr MsgHdr = new MsgHdr();
    private VryDef VryDef = new VryDef();

    @Override
    public String toString() {
        return "手机号码联网核查申请报文[MIVS_320_001_01]";
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
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = -581103924009687799L;
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
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 7162761168675476519L;
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
        public static class InstdPty implements Serializable{
            private static final long serialVersionUID = -3321102053955156293L;
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
    @XmlType(propOrder = { "MobNb", "Nm", "IdTp", "Id", "UniSocCdtCd", "BizRegNb", "OpNm" })
    public static class VryDef implements Serializable{
        private static final long serialVersionUID = 3773039275927181664L;
        private String MobNb = null;
        private String Nm = null;
        private String IdTp = null;
        private String Id = null;
        private String UniSocCdtCd = null;
        private String BizRegNb = null;
        private String OpNm = null;

        /**
         * @return the mobNb
         */
        public String getMobNb() {
            return MobNb;
        }

        /**
         * @return the opNm
         */
        public String getOpNm() {
            return OpNm;
        }

        /**
         * @param opNm the opNm to set
         */
        public void setOpNm(String opNm) {
            this.OpNm = opNm;
        }

        /**
         * @return the bizRegNb
         */
        public String getBizRegNb() {
            return BizRegNb;
        }

        /**
         * @param bizRegNb the bizRegNb to set
         */
        public void setBizRegNb(String bizRegNb) {
            this.BizRegNb = bizRegNb;
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
         * @return the id
         */
        public String getId() {
            return Id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.Id = id;
        }

        /**
         * @return the idTp
         */
        public String getIdTp() {
            return IdTp;
        }

        /**
         * @param idTp the idTp to set
         */
        public void setIdTp(String idTp) {
            this.IdTp = idTp;
        }

        /**
         * @return the nm
         */
        public String getNm() {
            return Nm;
        }

        /**
         * @param nm the nm to set
         */
        public void setNm(String nm) {
            this.Nm = nm;
        }

        /**
         * @param mobNb the mobNb to set
         */
        public void setMobNb(String mobNb) {
            this.MobNb = mobNb;
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
        sb.append(this.getVryDef().getMobNb() + "|");
        sb.append(this.getVryDef().getNm() + "|");
        sb.append(this.getVryDef().getIdTp() + "|");
        sb.append(this.getVryDef().getId() + "|");
        sb.append(this.getVryDef().getUniSocCdtCd() == null ? ""
                : this.getVryDef().getUniSocCdtCd() + "|");
        sb.append(this.getVryDef().getBizRegNb() == null ? ""
                : this.getVryDef().getBizRegNb() + "|");
        sb.append(this.getVryDef().getOpNm() + "|");
        return sb.toString();
    }

}