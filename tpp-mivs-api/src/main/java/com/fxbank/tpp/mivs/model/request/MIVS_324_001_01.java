package com.fxbank.tpp.mivs.model.request;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Description: 登记信息联网核查申请
 * @Author: 王鹏
 * @Date: 2019/5/20 16:29
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class MIVS_324_001_01 extends MODEL_BASE {
    private static final long serialVersionUID = 1798194998571302185L;
    private static final String MESGTYPE = "mivs.324.001.01";
    private static final String XMLNS = "urn:cnaps:std:mivs:2010:tech:xsd:mivs.324.001.01";

    private GetRegVrfctn GetRegVrfctn = new GetRegVrfctn();

    public MIVS_324_001_01() {
        super(null, 0, 0, 0);
    }

    public MIVS_324_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MIVS_324_001_01.MESGTYPE;
        super.XMLNS= MIVS_324_001_01.XMLNS;
        this.GetRegVrfctn.getMsgHdr().setMsgId(super.msgId());
        this.GetRegVrfctn.getMsgHdr().setCreDtTm(super.creDtTm());
    }

    public GetRegVrfctn getGetRegVrfctn() {
        return GetRegVrfctn;
    }

    public void setGetRegVrfctn(GetRegVrfctn getRegVrfctn) {
        GetRegVrfctn = getRegVrfctn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgHdr", "VryDef" })
    public static class GetRegVrfctn implements Serializable{
        private static final long serialVersionUID = 1548635919175327334L;
        private MsgHdr MsgHdr = new MsgHdr();
        private VryDef VryDef = new VryDef();

        @Override
        public String toString() {
            return "登记信息联网核查申请请求报文[MIVS_324_001_01]";
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
            private static final long serialVersionUID = 2726041441828975299L;
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
                private static final long serialVersionUID = 3753754078491826745L;
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
                private static final long serialVersionUID = -3611381264901901751L;
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
//    @XmlType(propOrder = { "Ent", "SlfEplydPpl", "AgtNm", "AgtId", "OpNm" })
        public static class VryDef implements Serializable{
            private static final long serialVersionUID = 7036312448035278706L;
            private Ent Ent = new Ent();
            private SlfEplydPpl SlfEplydPpl = new SlfEplydPpl();
            private String AgtNm = null; //代理人姓名
            private String AgtId = null; //代理人身份证件号码
            private String OpNm = null; //操作员姓名

            public Ent getEnt() {
                return Ent;
            }

            public void setEnt(Ent ent) {
                Ent = ent;
            }

            public SlfEplydPpl getSlfEplydPpl() {
                return SlfEplydPpl;
            }

            public void setSlfEplydPpl(SlfEplydPpl slfEplydPpl) {
                SlfEplydPpl = slfEplydPpl;
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

            @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(propOrder = { "EntNm", "UniSocCdtCd", "NmOfLglPrsn", "IdOfLglPrsn" })
            public static class Ent implements Serializable{
                private static final long serialVersionUID = -637900312057806102L;
                private String EntNm = null; //企业名称
                private String UniSocCdtCd = null; //统一社会信用代码
                private String NmOfLglPrsn = null; //法定代表人或单位负责人姓名
                private String IdOfLglPrsn = null; //法定代表人或单位负责人身份证件号

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
            }

            @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(propOrder = { "TraNm", "UniSocCdtCd", "Nm", "Id" })
            public static class SlfEplydPpl implements Serializable{
                private static final long serialVersionUID = 5514694792803947807L;
                private String TraNm = null; //字号名称
                private String UniSocCdtCd = null; //统一社会信用代码
                private String Nm = null; //经营者姓名
                private String Id = null; //经营者证件号

                public String getTraNm() {
                    return TraNm;
                }

                public void setTraNm(String traNm) {
                    TraNm = traNm;
                }

                public String getUniSocCdtCd() {
                    return UniSocCdtCd;
                }

                public void setUniSocCdtCd(String uniSocCdtCd) {
                    UniSocCdtCd = uniSocCdtCd;
                }

                public String getNm() {
                    return Nm;
                }

                public void setNm(String nm) {
                    Nm = nm;
                }

                public String getId() {
                    return Id;
                }

                public void setId(String id) {
                    Id = id;
                }
            }

        }
    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getGetRegVrfctn().getMsgHdr().getMsgId() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstgPty().getDrctPtyNm() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstgPty().getPtyNm() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGetRegVrfctn().getMsgHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getSlfEplydPpl().getTraNm() == null ? ""
                : this.getGetRegVrfctn().getVryDef().getSlfEplydPpl().getTraNm() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getSlfEplydPpl().getUniSocCdtCd()+ "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getSlfEplydPpl().getNm() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getSlfEplydPpl().getId() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getAgtNm() == null ? ""
                : this.getGetRegVrfctn().getVryDef().getAgtNm() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getAgtId() == null ? ""
                : this.getGetRegVrfctn().getVryDef().getAgtId() + "|");
        sb.append(this.getGetRegVrfctn().getVryDef().getOpNm() == null ? ""
                : this.getGetRegVrfctn().getVryDef().getOpNm() + "|");
        return sb.toString();
    }

}
