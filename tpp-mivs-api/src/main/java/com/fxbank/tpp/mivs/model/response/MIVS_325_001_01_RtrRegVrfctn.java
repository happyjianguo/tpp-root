package com.fxbank.tpp.mivs.model.response;

import com.fxbank.tpp.mivs.model.SIGN_DATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 登记信息核查应答报文
 * @Author: 王鹏
 * @Date: 2019/5/20 16:38
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "MsgPgntn","OrgnlBizQry", "Rspsn" })
public class MIVS_325_001_01_RtrRegVrfctn implements Serializable, SIGN_DATA {
    private static final long serialVersionUID = 6410912962315563294L;
    private MsgHdr MsgHdr = new MsgHdr();
    private MsgPgntn MsgPgntn = new MsgPgntn();
    private OrgnlBizQry OrgnlBizQry = new OrgnlBizQry();
    private Rspsn Rspsn = new Rspsn();

    @Override
    public String toString() {
        return "纳税信息联网核查应答报文[MIVS_325_001_01]";
    }

    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    public void setMsgHdr(MsgHdr msgHdr) {
        MsgHdr = msgHdr;
    }

    public MsgPgntn getMsgPgntn() {
        return MsgPgntn;
    }

    public void setMsgPgntn(MsgPgntn msgPgntn) {
        MsgPgntn = msgPgntn;
    }

    public OrgnlBizQry getOrgnlBizQry() {
        return OrgnlBizQry;
    }

    public void setOrgnlBizQry(OrgnlBizQry orgnlBizQry) {
        OrgnlBizQry = orgnlBizQry;
    }

    public Rspsn getRspsn() {
        return Rspsn;
    }

    public void setRspsn(Rspsn rspsn) {
        Rspsn = rspsn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty" })
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = 8127207925264274988L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();

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

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name="MsgHdr.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 1097873633071623456L;
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
    @XmlType(propOrder = { "PgNb", "LastPgInd" })
    public static class MsgPgntn implements Serializable{
        private static final long serialVersionUID = -8844072768050566887L;
        private Integer PgNb = null;
        private String LastPgInd = null;

        public Integer getPgNb() {
            return PgNb;
        }

        public void setPgNb(Integer pgNb) {
            PgNb = pgNb;
        }

        public String getLastPgInd() {
            return LastPgInd;
        }

        public void setLastPgInd(String lastPgInd) {
            LastPgInd = lastPgInd;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "InstgPty" })
    public static class OrgnlBizQry implements Serializable{
        private static final long serialVersionUID = -1561794738551219893L;
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
        @XmlType(name="OrgnlBizQry.InstgPty")
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 5587646689678165319L;
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
    @XmlType(propOrder = { "VrfctnInf","OprlErr" })
    public static class Rspsn implements Serializable{
        private static final long serialVersionUID = 8222417825425712922L;
        private VrfctnInf VrfctnInf = new VrfctnInf();
        private OprlErr OprlErr = new OprlErr();

        public VrfctnInf getVrfctnInf() {
            return VrfctnInf;
        }

        public void setVrfctnInf(VrfctnInf vrfctnInf) {
            VrfctnInf = vrfctnInf;
        }

        public OprlErr getOprlErr() {
            return OprlErr;
        }


        public void setOprlErr(OprlErr oprlErr) {
            OprlErr = oprlErr;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "Rslt","DataResrcDt","RegInf"})
        public static class VrfctnInf implements Serializable{
            private static final long serialVersionUID = 293223418691401711L;
            private String Rslt = null;
            private String DataResrcDt = null;
            private RegInf RegInf = new RegInf();

            public String getRslt() {
                return Rslt;
            }

            public void setRslt(String rslt) {
                Rslt = rslt;
            }

            public String getDataResrcDt() {
                return DataResrcDt;
            }

            public void setDataResrcDt(String dataResrcDt) {
                DataResrcDt = dataResrcDt;
            }

            public RegInf getRegInf() {
                return RegInf;
            }

            public void setRegInf(RegInf regInf) {
                RegInf = regInf;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(propOrder = { "BasInfo","CoShrhdrFndInfo","DirSupSrMgrInfo","ChngInfo","AbnmlBizInfo","IllDscrtInfo","LicNull"})
            public static class RegInf implements Serializable{
                private static final long serialVersionUID = -5365622990443907518L;
                private List<BasInfo> BasInfo;
                private List<CoShrhdrFndInfo> CoShrhdrFndInfo;
                private List<DirSupSrMgrInfo> DirSupSrMgrInfo;
                private List<ChngInfo> ChngInfo;
                private List<AbnmlBizInfo> AbnmlBizInfo;
                private List<IllDscrtInfo> IllDscrtInfo;
                private List<LicNull> LicNull;

                public List<BasInfo> getBasInfo() {
                    return BasInfo;
                }

                public void setBasInfo(List<BasInfo> basInfo) {
                    BasInfo = basInfo;
                }

                public List<CoShrhdrFndInfo> getCoShrhdrFndInfo() {
                    return CoShrhdrFndInfo;
                }

                public void setCoShrhdrFndInfo(List<CoShrhdrFndInfo> coShrhdrFndInfo) {
                    CoShrhdrFndInfo = coShrhdrFndInfo;
                }

                public List<DirSupSrMgrInfo> getDirSupSrMgrInfo() {
                    return DirSupSrMgrInfo;
                }

                public void setDirSupSrMgrInfo(List<DirSupSrMgrInfo> dirSupSrMgrInfo) {
                    DirSupSrMgrInfo = dirSupSrMgrInfo;
                }

                public List<ChngInfo> getChngInfo() {
                    return ChngInfo;
                }

                public void setChngInfo(List<ChngInfo> chngInfo) {
                    ChngInfo = chngInfo;
                }

                public List<AbnmlBizInfo> getAbnmlBizInfo() {
                    return AbnmlBizInfo;
                }

                public void setAbnmlBizInfo(List<AbnmlBizInfo> abnmlBizInfo) {
                    AbnmlBizInfo = abnmlBizInfo;
                }

                public List<IllDscrtInfo> getIllDscrtInfo() {
                    return IllDscrtInfo;
                }

                public void setIllDscrtInfo(List<IllDscrtInfo> illDscrtInfo) {
                    IllDscrtInfo = illDscrtInfo;
                }

                public List<LicNull> getLicNull() {
                    return LicNull;
                }

                public void setLicNull(List<LicNull> licNull) {
                    LicNull = licNull;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.BasInfo")
                public static class BasInfo implements Serializable{
                    private static final long serialVersionUID = -2967462237131397416L;
                    private String EntNm = null;
                    private String UniSocCdtCd = null;
                    private String CoTp = null;
                    private String Dom = null;
                    private String RegCptl = null;
                    private String DtEst = null;
                    private String OpPrdFrom = null;
                    private String OpPrdTo = null;
                    private String RegSts = null;
                    private String NmOfLglPrsn = null;
                    private String RegAuth = null;
                    private String BizScp = null;
                    private String DtAppr = null;

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

                    public String getCoTp() {
                        return CoTp;
                    }

                    public void setCoTp(String coTp) {
                        CoTp = coTp;
                    }

                    public String getDom() {
                        return Dom;
                    }

                    public void setDom(String dom) {
                        Dom = dom;
                    }

                    public String getRegCptl() {
                        return RegCptl;
                    }

                    public void setRegCptl(String regCptl) {
                        RegCptl = regCptl;
                    }

                    public String getDtEst() {
                        return DtEst;
                    }

                    public void setDtEst(String dtEst) {
                        DtEst = dtEst;
                    }

                    public String getOpPrdFrom() {
                        return OpPrdFrom;
                    }

                    public void setOpPrdFrom(String opPrdFrom) {
                        OpPrdFrom = opPrdFrom;
                    }

                    public String getOpPrdTo() {
                        return OpPrdTo;
                    }

                    public void setOpPrdTo(String opPrdTo) {
                        OpPrdTo = opPrdTo;
                    }

                    public String getRegSts() {
                        return RegSts;
                    }

                    public void setRegSts(String regSts) {
                        RegSts = regSts;
                    }

                    public String getNmOfLglPrsn() {
                        return NmOfLglPrsn;
                    }

                    public void setNmOfLglPrsn(String nmOfLglPrsn) {
                        NmOfLglPrsn = nmOfLglPrsn;
                    }

                    public String getRegAuth() {
                        return RegAuth;
                    }

                    public void setRegAuth(String regAuth) {
                        RegAuth = regAuth;
                    }

                    public String getBizScp() {
                        return BizScp;
                    }

                    public void setBizScp(String bizScp) {
                        BizScp = bizScp;
                    }

                    public String getDtAppr() {
                        return DtAppr;
                    }

                    public void setDtAppr(String dtAppr) {
                        DtAppr = dtAppr;
                    }

                    @Override
                    public String toString() {
                        return "BasInfo{" +
                                "EntNm='" + EntNm + '\'' +
                                ", UniSocCdtCd='" + UniSocCdtCd + '\'' +
                                ", CoTp='" + CoTp + '\'' +
                                ", Dom='" + Dom + '\'' +
                                ", RegCptl='" + RegCptl + '\'' +
                                ", DtEst='" + DtEst + '\'' +
                                ", OpPrdFrom='" + OpPrdFrom + '\'' +
                                ", OpPrdTo='" + OpPrdTo + '\'' +
                                ", RegSts='" + RegSts + '\'' +
                                ", NmOfLglPrsn='" + NmOfLglPrsn + '\'' +
                                ", RegAuth='" + RegAuth + '\'' +
                                ", BizScp='" + BizScp + '\'' +
                                ", DtAppr='" + DtAppr + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.CoShrhdrFndInfo")
                public static class CoShrhdrFndInfo implements Serializable{
                    private static final long serialVersionUID = 6976244244556182886L;
                    private String InvtrNm = null;
                    private String InvtrId = null;
                    private String SubscrCptlConAmt = null;
                    private String ActlCptlConAmt = null;
                    private String SubscrCptlConFm = null;
                    private String SubscrCptlConDt = null;

                    public String getInvtrNm() {
                        return InvtrNm;
                    }

                    public void setInvtrNm(String invtrNm) {
                        InvtrNm = invtrNm;
                    }

                    public String getInvtrId() {
                        return InvtrId;
                    }

                    public void setInvtrId(String invtrId) {
                        InvtrId = invtrId;
                    }

                    public String getSubscrCptlConAmt() {
                        return SubscrCptlConAmt;
                    }

                    public void setSubscrCptlConAmt(String subscrCptlConAmt) {
                        SubscrCptlConAmt = subscrCptlConAmt;
                    }

                    public String getActlCptlConAmt() {
                        return ActlCptlConAmt;
                    }

                    public void setActlCptlConAmt(String actlCptlConAmt) {
                        ActlCptlConAmt = actlCptlConAmt;
                    }

                    public String getSubscrCptlConFm() {
                        return SubscrCptlConFm;
                    }

                    public void setSubscrCptlConFm(String subscrCptlConFm) {
                        SubscrCptlConFm = subscrCptlConFm;
                    }

                    public String getSubscrCptlConDt() {
                        return SubscrCptlConDt;
                    }

                    public void setSubscrCptlConDt(String subscrCptlConDt) {
                        SubscrCptlConDt = subscrCptlConDt;
                    }

                    @Override
                    public String toString() {
                        return "CoShrhdrFndInfo{" +
                                "InvtrNm='" + InvtrNm + '\'' +
                                ", InvtrId='" + InvtrId + '\'' +
                                ", SubscrCptlConAmt='" + SubscrCptlConAmt + '\'' +
                                ", ActlCptlConAmt='" + ActlCptlConAmt + '\'' +
                                ", SubscrCptlConFm='" + SubscrCptlConFm + '\'' +
                                ", SubscrCptlConDt='" + SubscrCptlConDt + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.DirSupSrMgrInfo")
                public static class DirSupSrMgrInfo implements Serializable{
                    private static final long serialVersionUID = 6034573705703640062L;
                    private String Nm = null;
                    private String Posn = null;

                    public String getNm() {
                        return Nm;
                    }

                    public void setNm(String nm) {
                        Nm = nm;
                    }

                    public String getPosn() {
                        return Posn;
                    }

                    public void setPosn(String posn) {
                        Posn = posn;
                    }

                    @Override
                    public String toString() {
                        return "DirSupSrMgrInfo{" +
                                "Nm='" + Nm + '\'' +
                                ", Posn='" + Posn + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.ChngInfo")
                public static class ChngInfo implements Serializable{
                    private static final long serialVersionUID = -8953675143549078170L;
                    private String ChngItm = null;
                    private String BfChng = null;
                    private String AftChng = null;
                    private String DtOfChng = null;

                    public String getChngItm() {
                        return ChngItm;
                    }

                    public void setChngItm(String chngItm) {
                        ChngItm = chngItm;
                    }

                    public String getBfChng() {
                        return BfChng;
                    }

                    public void setBfChng(String bfChng) {
                        BfChng = bfChng;
                    }

                    public String getAftChng() {
                        return AftChng;
                    }

                    public void setAftChng(String aftChng) {
                        AftChng = aftChng;
                    }

                    public String getDtOfChng() {
                        return DtOfChng;
                    }

                    public void setDtOfChng(String dtOfChng) {
                        DtOfChng = dtOfChng;
                    }

                    @Override
                    public String toString() {
                        return "ChngInfo{" +
                                "ChngItm='" + ChngItm + '\'' +
                                ", BfChng='" + BfChng + '\'' +
                                ", AftChng='" + AftChng + '\'' +
                                ", DtOfChng='" + DtOfChng + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.AbnmlBizInfo")
                public static class AbnmlBizInfo implements Serializable{
                    private static final long serialVersionUID = 8786145087804013943L;
                    private String AbnmlCause = null;
                    private String AbnmlDate = null;
                    private String AbnmlCauseDcsnAuth = null;
                    private String RmvCause = null;
                    private String RmvDate = null;
                    private String RmvCauseDcsnAuth = null;

                    public String getAbnmlCause() {
                        return AbnmlCause;
                    }

                    public void setAbnmlCause(String abnmlCause) {
                        AbnmlCause = abnmlCause;
                    }

                    public String getAbnmlDate() {
                        return AbnmlDate;
                    }

                    public void setAbnmlDate(String abnmlDate) {
                        AbnmlDate = abnmlDate;
                    }

                    public String getAbnmlCauseDcsnAuth() {
                        return AbnmlCauseDcsnAuth;
                    }

                    public void setAbnmlCauseDcsnAuth(String abnmlCauseDcsnAuth) {
                        AbnmlCauseDcsnAuth = abnmlCauseDcsnAuth;
                    }

                    public String getRmvCause() {
                        return RmvCause;
                    }

                    public void setRmvCause(String rmvCause) {
                        RmvCause = rmvCause;
                    }

                    public String getRmvDate() {
                        return RmvDate;
                    }

                    public void setRmvDate(String rmvDate) {
                        RmvDate = rmvDate;
                    }

                    public String getRmvCauseDcsnAuth() {
                        return RmvCauseDcsnAuth;
                    }

                    public void setRmvCauseDcsnAuth(String rmvCauseDcsnAuth) {
                        RmvCauseDcsnAuth = rmvCauseDcsnAuth;
                    }

                    @Override
                    public String toString() {
                        return "AbnmlBizInfo{" +
                                "AbnmlCause='" + AbnmlCause + '\'' +
                                ", AbnmlDate='" + AbnmlDate + '\'' +
                                ", AbnmlCauseDcsnAuth='" + AbnmlCauseDcsnAuth + '\'' +
                                ", RmvCause='" + RmvCause + '\'' +
                                ", RmvDate='" + RmvDate + '\'' +
                                ", RmvCauseDcsnAuth='" + RmvCauseDcsnAuth + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.IllDscrtInfo")
                public static class IllDscrtInfo implements Serializable{
                    private static final long serialVersionUID = -2848189638058499708L;
                    private String IllDscrtCause = null;
                    private String AbnmlDate = null;
                    private String AbnmlCauseDcsnAuth = null;
                    private String RmvCause = null;
                    private String RmvCauseDcsnAuth = null;
                    private String RmvDate = null;

                    public String getIllDscrtCause() {
                        return IllDscrtCause;
                    }

                    public void setIllDscrtCause(String illDscrtCause) {
                        IllDscrtCause = illDscrtCause;
                    }

                    public String getAbnmlDate() {
                        return AbnmlDate;
                    }

                    public void setAbnmlDate(String abnmlDate) {
                        AbnmlDate = abnmlDate;
                    }

                    public String getAbnmlCauseDcsnAuth() {
                        return AbnmlCauseDcsnAuth;
                    }

                    public void setAbnmlCauseDcsnAuth(String abnmlCauseDcsnAuth) {
                        AbnmlCauseDcsnAuth = abnmlCauseDcsnAuth;
                    }

                    public String getRmvCause() {
                        return RmvCause;
                    }

                    public void setRmvCause(String rmvCause) {
                        RmvCause = rmvCause;
                    }

                    public String getRmvCauseDcsnAuth() {
                        return RmvCauseDcsnAuth;
                    }

                    public void setRmvCauseDcsnAuth(String rmvCauseDcsnAuth) {
                        RmvCauseDcsnAuth = rmvCauseDcsnAuth;
                    }

                    public String getRmvDate() {
                        return RmvDate;
                    }

                    public void setRmvDate(String rmvDate) {
                        RmvDate = rmvDate;
                    }

                    @Override
                    public String toString() {
                        return "IllDscrtInfo{" +
                                "IllDscrtCause='" + IllDscrtCause + '\'' +
                                ", AbnmlDate='" + AbnmlDate + '\'' +
                                ", AbnmlCauseDcsnAuth='" + AbnmlCauseDcsnAuth + '\'' +
                                ", RmvCause='" + RmvCause + '\'' +
                                ", RmvCauseDcsnAuth='" + RmvCauseDcsnAuth + '\'' +
                                ", RmvDate='" + RmvDate + '\'' +
                                '}';
                    }
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "RegInf.LicNull")
                public static class LicNull implements Serializable{
                    private static final long serialVersionUID = 1470655276191139554L;
                    private String OrgnlOrCp = null;
                    private String LicNullStmCntt = null;
                    private String LicNullStmDt = null;
                    private String RplSts = null;
                    private String RplDt = null;
                    private String LicCpNb = null;

                    public String getOrgnlOrCp() {
                        return OrgnlOrCp;
                    }

                    public void setOrgnlOrCp(String orgnlOrCp) {
                        OrgnlOrCp = orgnlOrCp;
                    }

                    public String getLicNullStmCntt() {
                        return LicNullStmCntt;
                    }

                    public void setLicNullStmCntt(String licNullStmCntt) {
                        LicNullStmCntt = licNullStmCntt;
                    }

                    public String getLicNullStmDt() {
                        return LicNullStmDt;
                    }

                    public void setLicNullStmDt(String licNullStmDt) {
                        LicNullStmDt = licNullStmDt;
                    }

                    public String getRplSts() {
                        return RplSts;
                    }

                    public void setRplSts(String rplSts) {
                        RplSts = rplSts;
                    }

                    public String getRplDt() {
                        return RplDt;
                    }

                    public void setRplDt(String rplDt) {
                        RplDt = rplDt;
                    }

                    public String getLicCpNb() {
                        return LicCpNb;
                    }

                    public void setLicCpNb(String licCpNb) {
                        LicCpNb = licCpNb;
                    }

                    @Override
                    public String toString() {
                        return "LicNull{" +
                                "OrgnlOrCp='" + OrgnlOrCp + '\'' +
                                ", LicNullStmCntt='" + LicNullStmCntt + '\'' +
                                ", LicNullStmDt='" + LicNullStmDt + '\'' +
                                ", RplSts='" + RplSts + '\'' +
                                ", RplDt='" + RplDt + '\'' +
                                ", LicCpNb='" + LicCpNb + '\'' +
                                '}';
                    }
                }
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"ProcSts","ProcCd","Rjctinf"})
        public static class OprlErr implements Serializable{
            private static final long serialVersionUID = -4352854015236225510L;
            private String ProcSts;
            private String ProcCd;
            private String Rjctinf;

            public String getProcSts() {
                return ProcSts;
            }
            public void setProcSts(String procSts) {
                ProcSts = procSts;
            }

            public String getProcCd() {
                return ProcCd;
            }
            public void setProcCd(String procCd) {
                ProcCd = procCd;
            }

            public String getRjctinf() {
                return Rjctinf;
            }
            public void setRjctinf(String rjctinf) {
                Rjctinf = rjctinf;
            }


        }
    }

    @Override
    public String signData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(this.getMsgHdr().getMsgId() + "|");
//        sb.append(this.getMsgHdr().getCreDtTm() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
//        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
//        sb.append(this.getOrgnlBizQry().getMsgId() + "|");
//        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgDrctPty() + "|");
//        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgPty() + "|");
//        sb.append(this.getRspsn().getVrfctnInf().getRslt() + "|");
//        sb.append(this.getRspsn().getVrfctnInf().getDataResrcDt() + "|");
//        if(this.getRspsn().getVrfctnInf().getRslt().equals("MCHD")) {
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getEntNm() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getUniSocCdtCd() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getCoTp() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getDom() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getRegCptl() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getDtEst() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getOpPrdFrom() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getOpPrdTo() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getRegSts() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getNmOfLglPrsn() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getRegAuth() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getBizScp() + "|");
//            sb.append(this.getRspsn().getVrfctnInf().getRegInf().getBasInfo().getDtAppr() + "|");
//        }else if(this.getRspsn().getOprlErr().getProcCd()!=null){
//            sb.append(this.getRspsn().getOprlErr().getProcSts() + "|");
//            sb.append(this.getRspsn().getOprlErr().getProcCd() + "|");
//        }
//        return sb.toString();
        return null;
    }

}