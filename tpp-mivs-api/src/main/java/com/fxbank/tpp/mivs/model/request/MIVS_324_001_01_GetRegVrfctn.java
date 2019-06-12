package com.fxbank.tpp.mivs.model.request;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 登记信息联网核查申请请求报文
 * @Author: 王鹏
 * @Date: 2019/5/20 16:33
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "VryDef" })
public class MIVS_324_001_01_GetRegVrfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 2671959651920079085L;
    private MsgHdr MsgHdr = new MsgHdr();
    private VryDef VryDef = new VryDef();

    @Override
    public String toString() {
        return "登记信息联网核查申请请求报文[MIVS_320_001_01]";
    }

    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public VryDef getVryDef() {
        return VryDef;
    }

    public void setVryDef(VryDef vryDef) {
        VryDef = vryDef;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty" })
    public static class MsgHdr implements Serializable {
        private static final long serialVersionUID = -1562462308783251754L;
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
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = -3831881346899516465L;
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
            private static final long serialVersionUID = 8628377200680399121L;
            private String InstdDrctPty = "0000";
            private String InstdPty = "0000";

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
    @XmlType(propOrder = { "EntNm", "UniSocCdtCd", "NmOfLglPrsn", "IdOfLglPrsn", "AgtNm", "AgtId", "OpNm" })
    public static class VryDef implements Serializable{
        private static final long serialVersionUID = 453675020908748650L;
        private String EntNm = null; //企业名称
        private String UniSocCdtCd = null; //统一社会信用代码
        private String NmOfLglPrsn = null; //法定代表人或单位负责人姓名
        private String IdOfLglPrsn = null; //法定代表人或单位负责人身份证件号
        private String AgtNm = null; //代理人姓名
        private String AgtId = null; //代理人身份证件号码
        private String OpNm = null; //操作员姓名

        public String getEntNm() {
            return EntNm;
        }

        public void setEntNm(String entNm) {
            EntNm = entNm;
        }

        public String getUniSocCdtCd() {
            return UniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            UniSocCdtCd = uniSocCdtCd;
        }

        public String getNmOfLglPrsn() {
            return NmOfLglPrsn;
        }

        public void setNmOfLglPrsn(String nmOfLglPrsn) {
            NmOfLglPrsn = nmOfLglPrsn;
        }

        public String getIdOfLglPrsn() {
            return IdOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            IdOfLglPrsn = idOfLglPrsn;
        }

        public String getAgtNm() {
            return AgtNm;
        }

        public void setAgtNm(String agtNm) {
            AgtNm = agtNm;
        }

        public String getAgtId() {
            return AgtId;
        }

        public void setAgtId(String agtId) {
            AgtId = agtId;
        }

        public String getOpNm() {
            return OpNm;
        }

        public void setOpNm(String opNm) {
            OpNm = opNm;
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
//        sb.append(this.getVryDef().getEntNm() + "|");
//        sb.append(this.getVryDef().getUniSocCdtCd() == null ? ""
//                : this.getVryDef().getUniSocCdtCd() + "|");
//        sb.append(this.getVryDef().getNmOfLglPrsn() == null ? ""
//                : this.getVryDef().getNmOfLglPrsn() + "|");
//        sb.append(this.getVryDef().getIdOfLglPrsn());
//        sb.append(this.getVryDef().getAgtNm());
//        sb.append(this.getVryDef().getAgtId());
//        sb.append(this.getVryDef().getOpNm() + "|");
//        return sb.toString();
        return null;
    }

}
