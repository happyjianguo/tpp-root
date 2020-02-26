package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author YePuLiang
 * @ClassName: BEPS_351_001_01_PtcSnReq
 * @Description: 通用处理确认报文
 * @date 2020年2月21日 下午3:55:57
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"GrpHdr", "CtrctChngInf"})
public class BEPS_351_001_01_PtcSnReq implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = -2107007006054449808L;

    private GrpHdr GrpHdr = new GrpHdr();

    private CtrctChngInf CtrctChngInf = new CtrctChngInf();

    @Override
    public String toString() {
        return "通用处理确认报文[BEPS_351_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngInf.getCstmrId() + "][" + this.CtrctChngInf.getCstmrNm() + "]";
    }

    public BEPS_351_001_01_PtcSnReq.GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(BEPS_351_001_01_PtcSnReq.GrpHdr grpHdr) {
        GrpHdr = grpHdr;
    }

    public BEPS_351_001_01_PtcSnReq.CtrctChngInf getCtrctChngInf() {
        return CtrctChngInf;
    }

    public void setCtrctChngInf(BEPS_351_001_01_PtcSnReq.CtrctChngInf ctrctChngInf) {
        CtrctChngInf = ctrctChngInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk"})
    public static class GrpHdr implements Serializable {

        private static final long serialVersionUID = -7164625084979709481L;

        private String MsgId;
        private String CreDtTm;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();
        private String SysCd;
        private String Rmk;

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        public String getCreDtTm() {
            return CreDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        public String getSysCd() {
            return SysCd;
        }

        public void setSysCd(String sysCd) {
            this.SysCd = sysCd;
        }

        public String getRmk() {
            return Rmk;
        }

        public void setRmk(String rmk) {
            this.Rmk = rmk;
        }

        public InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        public InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(InstdPty instdPty) {
            this.InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstgPty")
        public static class InstgPty implements Serializable {

            private static final long serialVersionUID = -6877011574990490256L;

            private String InstgDrctPty;
            private String InstgPty;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstdPty")
        public static class InstdPty implements Serializable {

            private static final long serialVersionUID = 8084891028404642070L;

            private String InstdDrctPty;
            private String InstdPty;

            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            public void setInstdDrctPty(String instdDrctPty) {
                this.InstdDrctPty = instdDrctPty;
            }

            public String getInstdPty() {
                return InstdPty;
            }

            public void setInstdPty(String instdPty) {
                this.InstdPty = instdPty;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"ChngTp", "CtrctTp", "CstmrId", "RegId", "CstmrNm",
            "UniSocCdtCd", "NbOfPmtItms", "PmtItms", "CtrctNb", "ReqId",
            "Issr", "CstmrAcctType", "AcctId", "OncDdctnLmt", "CycDdctnNumLmt",
            "CtrctDueDt", "CtrctSgnDt", "EctDt", "PyrInf", "TelNb",
            "AdrLine", "Rmk", "AuthMd", "TmUt", "TmSp", "TmDc", "CycDdctnLmt", "CtrctAddtlInf"})
    public static class CtrctChngInf implements Serializable {

        private static final long serialVersionUID = -6681513355015101594L;

        private String ChngTp;
        private String CtrctTp;
        private String CstmrId;
        private String RegId;
        private String CstmrNm;
        private String UniSocCdtCd;
        private String NbOfPmtItms;
        private PmtItms PmtItms = new PmtItms();
        private String CtrctNb;
        private String ReqId;
        private String Issr;
        private String CstmrAcctType;
        private String AcctId;
        private String OncDdctnLmt;
        private String CycDdctnNumLmt;
        private String CtrctDueDt;
        private String CtrctSgnDt;
        private String EctDt;
        private PyrInf PyrInf = new PyrInf();
        private String TelNb;
        private String AdrLine;
        private String Rmk;
        private String AuthMd;
        private String TmUt;
        private String TmSp;
        private String TmDc;
        private String CycDdctnLmt;
        private String CtrctAddtlInf;

        public String getChngTp() {
            return ChngTp;
        }

        public void setChngTp(String chngTp) {
            ChngTp = chngTp;
        }

        public String getCtrctTp() {
            return CtrctTp;
        }

        public void setCtrctTp(String ctrctTp) {
            CtrctTp = ctrctTp;
        }

        public String getCstmrId() {
            return CstmrId;
        }

        public void setCstmrId(String cstmrId) {
            CstmrId = cstmrId;
        }

        public String getRegId() {
            return RegId;
        }

        public void setRegId(String regId) {
            RegId = regId;
        }

        public String getCstmrNm() {
            return CstmrNm;
        }

        public void setCstmrNm(String cstmrNm) {
            CstmrNm = cstmrNm;
        }

        public String getUniSocCdtCd() {
            return UniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            UniSocCdtCd = uniSocCdtCd;
        }

        public String getNbOfPmtItms() {
            return NbOfPmtItms;
        }

        public void setNbOfPmtItms(String nbOfPmtItms) {
            NbOfPmtItms = nbOfPmtItms;
        }

        public PmtItms getPmtItms() {
			return PmtItms;
		}

		public void setPmtItms(PmtItms pmtItms) {
			PmtItms = pmtItms;
		}

		public String getCtrctNb() {
            return CtrctNb;
        }

        public void setCtrctNb(String ctrctNb) {
            CtrctNb = ctrctNb;
        }

        public String getReqId() {
            return ReqId;
        }

        public void setReqId(String reqId) {
            ReqId = reqId;
        }

        public String getIssr() {
            return Issr;
        }

        public void setIssr(String issr) {
            Issr = issr;
        }

        public String getCstmrAcctType() {
            return CstmrAcctType;
        }

        public void setCstmrAcctType(String cstmrAcctType) {
            CstmrAcctType = cstmrAcctType;
        }

        public String getAcctId() {
            return AcctId;
        }

        public void setAcctId(String acctId) {
            AcctId = acctId;
        }

        public String getOncDdctnLmt() {
            return OncDdctnLmt;
        }

        public void setOncDdctnLmt(String oncDdctnLmt) {
            OncDdctnLmt = oncDdctnLmt;
        }

        public String getCycDdctnNumLmt() {
            return CycDdctnNumLmt;
        }

        public void setCycDdctnNumLmt(String cycDdctnNumLmt) {
            CycDdctnNumLmt = cycDdctnNumLmt;
        }

        public String getCtrctDueDt() {
            return CtrctDueDt;
        }

        public void setCtrctDueDt(String ctrctDueDt) {
            CtrctDueDt = ctrctDueDt;
        }

        public String getCtrctSgnDt() {
            return CtrctSgnDt;
        }

        public void setCtrctSgnDt(String ctrctSgnDt) {
            CtrctSgnDt = ctrctSgnDt;
        }

        public String getEctDt() {
            return EctDt;
        }

        public void setEctDt(String ectDt) {
            EctDt = ectDt;
        }

        public PyrInf getPyrInf() {
            return PyrInf;
        }

        public void setPyrInf(PyrInf pyrInf) {
            PyrInf = pyrInf;
        }

        public String getTelNb() {
            return TelNb;
        }

        public void setTelNb(String telNb) {
            TelNb = telNb;
        }

        public String getAdrLine() {
            return AdrLine;
        }

        public void setAdrLine(String adrLine) {
            AdrLine = adrLine;
        }

        public String getRmk() {
            return Rmk;
        }

        public void setRmk(String rmk) {
            Rmk = rmk;
        }

        public String getAuthMd() {
            return AuthMd;
        }

        public void setAuthMd(String authMd) {
            AuthMd = authMd;
        }

        public String getTmUt() {
            return TmUt;
        }

        public void setTmUt(String tmUt) {
            TmUt = tmUt;
        }

        public String getTmSp() {
            return TmSp;
        }

        public void setTmSp(String tmSp) {
            TmSp = tmSp;
        }

        public String getTmDc() {
            return TmDc;
        }

        public void setTmDc(String tmDc) {
            TmDc = tmDc;
        }

        public String getCycDdctnLmt() {
            return CycDdctnLmt;
        }

        public void setCycDdctnLmt(String cycDdctnLmt) {
            CycDdctnLmt = cycDdctnLmt;
        }

        public String getCtrctAddtlInf() {
            return CtrctAddtlInf;
        }

        public void setCtrctAddtlInf(String ctrctAddtlInf) {
            CtrctAddtlInf = ctrctAddtlInf;
        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "CtrctChngInf.PmtItms")
        public static class PmtItms implements Serializable {

			private static final long serialVersionUID = -6773638064390328019L;
			
			private List<String> PmtItmCd;

			public List<String> getPmtItmCd() {
				return PmtItmCd;
			}

			public void setPmtItmCd(List<String> pmtItmCd) {
				PmtItmCd = pmtItmCd;
			}

        }
        
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "CtrctChngInf.PyrInf")
        public static class PyrInf implements Serializable {

			private static final long serialVersionUID = -7559196732322207266L;
			
	        private String Nm;
	        
	        private String IdTp;
	        
	        private String Id;
	        
	        public String getNm() {
	            return Nm;
	        }

	        public void setNm(String nm) {
	            Nm = nm;
	        }

	        public String getIdTp() {
	            return IdTp;
	        }

	        public void setIdTp(String idTp) {
	            IdTp = idTp;
	        }

	        public String getId() {
	            return Id;
	        }

	        public void setId(String id) {
	            Id = id;
	        }

        }
        
        

    }

    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        //GrpHdr组包
        sb.append(this.getGrpHdr().getMsgId() + "|");
        sb.append(this.getGrpHdr().getCreDtTm() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGrpHdr().getSysCd() + "|");
        sb.append(this.getGrpHdr().getRmk() + "|");
        //CtrctChngInf组包
        sb.append(this.CtrctChngInf.getChngTp() == null ? "" : this.CtrctChngInf.getChngTp() + "|");
        sb.append(this.CtrctChngInf.getCtrctTp() == null ? "" : this.CtrctChngInf.getCtrctTp() + "|");
        sb.append(this.CtrctChngInf.getCstmrId() == null ? "" : this.CtrctChngInf.getCstmrId() + "|");
        sb.append(this.CtrctChngInf.getRegId() == null ? "" : this.CtrctChngInf.getRegId() + "|");
        sb.append(this.CtrctChngInf.getCstmrNm() == null ? "" : this.CtrctChngInf.getCstmrNm() + "|");
        sb.append(this.CtrctChngInf.getUniSocCdtCd() == null ? "" : this.CtrctChngInf.getUniSocCdtCd() + "|");
        sb.append(this.CtrctChngInf.getNbOfPmtItms() == null ? "" : this.CtrctChngInf.getNbOfPmtItms() + "|");
        sb.append(this.CtrctChngInf.getPmtItms().getPmtItmCd() == null ? "" : this.CtrctChngInf.getPmtItms().getPmtItmCd() + "|");
        sb.append(this.CtrctChngInf.getCtrctNb() == null ? "" : this.CtrctChngInf.getCtrctNb() + "|");
        sb.append(this.CtrctChngInf.getReqId() == null ? "" : this.CtrctChngInf.getReqId() + "|");
        sb.append(this.CtrctChngInf.getIssr() == null ? "" : this.CtrctChngInf.getIssr() + "|");
        sb.append(this.CtrctChngInf.getCstmrAcctType() == null ? "" : this.CtrctChngInf.getCstmrAcctType() + "|");
        sb.append(this.CtrctChngInf.getAcctId() == null ? "" : this.CtrctChngInf.getAcctId() + "|");
        sb.append(this.CtrctChngInf.getOncDdctnLmt() == null ? "" : this.CtrctChngInf.getOncDdctnLmt() + "|");
        sb.append(this.CtrctChngInf.getCycDdctnNumLmt() == null ? "" : this.CtrctChngInf.getCycDdctnNumLmt() + "|");
        sb.append(this.CtrctChngInf.getCtrctDueDt() == null ? "" : this.CtrctChngInf.getCtrctDueDt() + "|");
        sb.append(this.CtrctChngInf.getCtrctSgnDt() == null ? "" : this.CtrctChngInf.getCtrctSgnDt() + "|");
        sb.append(this.CtrctChngInf.getEctDt() == null ? "" : this.CtrctChngInf.getEctDt() + "|");
        sb.append(this.CtrctChngInf.getPyrInf().getNm() == null ? "" : this.CtrctChngInf.getPyrInf().getNm() + "|");
        sb.append(this.CtrctChngInf.getPyrInf().getIdTp() == null ? "" : this.CtrctChngInf.getPyrInf().getIdTp() + "|");
        sb.append(this.CtrctChngInf.getPyrInf().getId() == null ? "" : this.CtrctChngInf.getPyrInf().getId() + "|");
        sb.append(this.CtrctChngInf.getTelNb() == null ? "" : this.CtrctChngInf.getTelNb() + "|");
        sb.append(this.CtrctChngInf.getAdrLine() == null ? "" : this.CtrctChngInf.getAdrLine() + "|");
        sb.append(this.CtrctChngInf.getRmk() == null ? "" : this.CtrctChngInf.getRmk() + "|");
        sb.append(this.CtrctChngInf.getAuthMd() == null ? "" : this.CtrctChngInf.getAuthMd() + "|");
        sb.append(this.CtrctChngInf.getTmUt() == null ? "" : this.CtrctChngInf.getTmUt() + "|");
        sb.append(this.CtrctChngInf.getTmSp() == null ? "" : this.CtrctChngInf.getTmSp() + "|");
        sb.append(this.CtrctChngInf.getTmDc() == null ? "" : this.CtrctChngInf.getTmDc() + "|");
        sb.append(this.CtrctChngInf.getCycDdctnLmt() == null ? "" : this.CtrctChngInf.getCycDdctnLmt() + "|");
        sb.append(this.CtrctChngInf.getCtrctAddtlInf() == null ? "" : this.CtrctChngInf.getCtrctAddtlInf() + "|");
        return sb.toString();
    }
}