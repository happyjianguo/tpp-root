package com.fxbank.tpp.mivs.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 手机号码联网核查申请报文
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:51:24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "GetIdVrfctn" })
@XmlRootElement(name = "Document")
public class MIVS_320_001_01 extends MODEL_BASE {

    private static final long serialVersionUID = -7575351098905821390L;

    private static final String MESGTYPE = "mivs.320.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.320.001.01";
    private static final String XMLNS_XSI= null;

    private GetIdVrfctn GetIdVrfctn = new GetIdVrfctn();

    public MIVS_320_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_320_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_320_001_01.MESGTYPE;
        super.XMLNS= MIVS_320_001_01.XMLNS;
        super.XMLNS_XSI= MIVS_320_001_01.XMLNS_XSI;
        this.getGetIdVrfctn().getMsgHdr().setMsgId(super.msgId());
        this.getGetIdVrfctn().getMsgHdr().setCreDtTm(super.creDtTm());
    }

    /**
     * @return the getIdVrfctn
     */
    public GetIdVrfctn getGetIdVrfctn() {
        return GetIdVrfctn;
    }

    /**
     * @param getIdVrfctn the getIdVrfctn to set
     */
    public void setGetIdVrfctn(GetIdVrfctn getIdVrfctn) {
        GetIdVrfctn = getIdVrfctn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgHdr", "VryDef" })
    public static class GetIdVrfctn {
        private MsgHdr MsgHdr = new MsgHdr();
        private VryDef VryDef = new VryDef();

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
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr {

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
        public static class InstgPty {
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
        public static class InstdPty {
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
    public static class VryDef {
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

    /**
     * @Description: 需要签名则返回实际签名数据，不需要签名则返回null
     * @Author: 周勇沩
     * @Date: 2019-04-20 13:06:02
     */
    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getGetIdVrfctn().getMsgHdr().getMsgId() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstgPty().getDrctPtyNm() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstgPty().getPtyNm() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGetIdVrfctn().getMsgHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getMobNb() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getNm() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getIdTp() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getId() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getUniSocCdtCd() == null ? ""
                : this.getGetIdVrfctn().getVryDef().getUniSocCdtCd() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getBizRegNb() == null ? ""
                : this.getGetIdVrfctn().getVryDef().getBizRegNb() + "|");
        sb.append(this.getGetIdVrfctn().getVryDef().getOpNm() + "|");
        return sb.toString();
    }

}