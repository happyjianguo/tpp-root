package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @author : 周勇沩
 * @description: 实时客户支付协议管理报文
 * @Date : 2020/2/28 08:07
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"GrpHdr", "CtrctChngInf"})
public class BEPS_351_001_01_PtcSnReq extends REP_BASE implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = -2107007006054449808L;

    private static final String MESGTYPE = "beps.351.001.01";

    @EsbSimuAnno.EsbField(type = "Object")
    private GrpHdr GrpHdr = new GrpHdr();

    @EsbSimuAnno.EsbField(type = "Object")
    private CtrctChngInf CtrctChngInf = new CtrctChngInf();

    @Override
    public String toString() {
        return "实时客户支付协议管理报文[BEPS_351_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngInf.getCstmrId() + "][" + this.CtrctChngInf.getCstmrNm() + "]";
    }

    public BEPS_351_001_01_PtcSnReq() {
        super.mesgType = MESGTYPE;
    }

    public GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        GrpHdr = grpHdr;
    }

    public BEPS_351_001_01_PtcSnReq.CtrctChngInf getCtrctChngInf() {
        return CtrctChngInf;
    }

    public void setCtrctChngInf(BEPS_351_001_01_PtcSnReq.CtrctChngInf ctrctChngInf) {
        CtrctChngInf = ctrctChngInf;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"ChngTp", "CtrctTp", "CstmrId", "RegId", "CstmrNm",
            "UniSocCdtCd", "NbOfPmtItms", "PmtItms", "CtrctNb", "ReqId",
            "Issr", "CstmrAcctType", "AcctId", "OncDdctnLmt", "CycDdctnNumLmt",
            "CtrctDueDt", "CtrctSgnDt", "EctDt", "PyrInf", "TelNb",
            "AdrLine", "Rmk", "AuthMd", "TmUt", "TmSp", "TmDc", "CycDdctnLmt", "CtrctAddtlInf"})
    public static class CtrctChngInf implements Serializable {

        private static final long serialVersionUID = -6681513355015101594L;

        @EsbSimuAnno.EsbField(type = "String", value = "CC00")
        private String ChngTp;
        @EsbSimuAnno.EsbField(type = "String", value = "CO00")
        private String CtrctTp;
        @EsbSimuAnno.EsbField(type = "String", len = 10)
        private String CstmrId;
        @EsbSimuAnno.EsbField(type = "Enum", value = "110000,310000,330100")
        private String RegId;
        @EsbSimuAnno.EsbField(type = "String", value = "张三")
        private String CstmrNm;
        @EsbSimuAnno.EsbField(type = "String", len = 10)
        private String UniSocCdtCd;
        @EsbSimuAnno.EsbField(type = "String", value = "3")
        private String NbOfPmtItms;
        @EsbSimuAnno.EsbField(type = "Object")
        private PmtItms PmtItms = new PmtItms();
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
        private String CtrctNb;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
        private String ReqId;
        @EsbSimuAnno.EsbField(type = "String", value = "313229000008")
        private String Issr;
        @EsbSimuAnno.EsbField(type = "String", value = "AO00")
        private String CstmrAcctType;
        @EsbSimuAnno.EsbField(type = "Enum", value = "6228480049085072475,621226041000408000,6217000730028446116")
        private String AcctId;
        @EsbSimuAnno.EsbField(type = "String", value = "CNY650000.00")
        private String OncDdctnLmt;
        @EsbSimuAnno.EsbField(type = "String", value = "5")
        private String CycDdctnNumLmt;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd")
        private String CtrctDueDt;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd")
        private String CtrctSgnDt;
        @EsbSimuAnno.EsbField(type = "Date", value = "yyyy-MM-dd")
        private String EctDt;
        @EsbSimuAnno.EsbField(type = "Object")
        private PyrInf PyrInf = new PyrInf();
        @EsbSimuAnno.EsbField(type = "Enum", value = "13133334444,13255556666,13377778888")
        private String TelNb;
        @EsbSimuAnno.EsbField(type = "String", value = "地址地址地址")
        private String AdrLine;
        @EsbSimuAnno.EsbField(type = "String", value = "备注备注备注")
        private String Rmk;
        @EsbSimuAnno.EsbField(type = "String", value = "AM00")
        private String AuthMd;
        @EsbSimuAnno.EsbField(type = "String", value = "TU00")
        private String TmUt;
        @EsbSimuAnno.EsbField(type = "String", value = "1")
        private String TmSp;
        @EsbSimuAnno.EsbField(type = "String", value = "扣款时间描述")
        private String TmDc;
        @EsbSimuAnno.EsbField(type = "String", value = "CNY650000.00")
        private String CycDdctnLmt;
        @EsbSimuAnno.EsbField(type = "String", value = "协议附加数据")
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

        public BEPS_351_001_01_PtcSnReq.CtrctChngInf.PmtItms getPmtItms() {
            return PmtItms;
        }

        public void setPmtItms(BEPS_351_001_01_PtcSnReq.CtrctChngInf.PmtItms pmtItms) {
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
        public static class PmtItms implements Serializable {

            private static final long serialVersionUID = 7885733285704845412L;

            @EsbSimuAnno.EsbField(type = "List", len = 3)
            private List<String> PmtItmCd;

            public List<String> getPmtItmCd() {
                return PmtItmCd;
            }

            public void setPmtItmCd(List<String> pmtItmCd) {
                PmtItmCd = pmtItmCd;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class PyrInf implements Serializable {

            private static final long serialVersionUID = -7559196732322207266L;
            @EsbSimuAnno.EsbField(type = "String", value = "张三")
            private String Nm;
            @EsbSimuAnno.EsbField(type = "String", value = "IC00")
            private String IdTp;
            @EsbSimuAnno.EsbField(type = "String", value = "110000201912151234")
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
        //sb.append(this.CtrctChngInf.getPmtItms().getPmtItmCd() == null ? "" : this.CtrctChngInf.getPmtItms().getPmtItmCd() + "|");
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
