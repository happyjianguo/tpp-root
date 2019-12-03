package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_regvrfctn_bas_info")
public class MivsRegvrfctnBasInfoEntity {
    /**
     * null
     */
    @Id
    @Column(name = "INSTG_PTY")
    private String instgPty;

    /**
     * null
     */
    @Id
    @Column(name = "MSG_ID")
    private String msgId;

    /**
     * null
     */
    @Id
    @Column(name = "BAS_INFO_NB")
    private Integer basInfoNb;

    /**
     * null
     */
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Column(name = "PLAT_TRACE")
    private Integer platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Integer platTime;

    /**
     * null
     */
    @Column(name = "CRE_DT_TM")
    private String creDtTm;

    /**
     * null
     */
    @Column(name = "ORIG_MSG_ID")
    private String origMsgId;

    /**
     * null
     */
    @Column(name = "ORIG_INSTG_DRCT_PTY")
    private String origInstgDrctPty;

    /**
     * null
     */
    @Column(name = "ORIG_INSTG_PTY")
    private String origInstgPty;

    /**
     * null
     */
    @Column(name = "PG_NB")
    private Integer pgNb;

    /**
     * null
     */
    @Column(name = "MARKET_TYPE")
    private String marketType;

    /**
     * null
     */
    @Column(name = "UNI_SOC_CDT_CD")
    private String uniSocCdtCd;

    /**
     * null
     */
    @Column(name = "CO_TP")
    private String coTp;

    /**
     * null
     */
    @Column(name = "REG_STS")
    private String regSts;

    /**
     * null
     */
    @Column(name = "REG_AUTH")
    private String regAuth;

    /**
     * null
     */
    @Column(name = "DT_APPR")
    private String dtAppr;

    /**
     * null
     */
    @Column(name = "ENT_NM")
    private String entNm;

    /**
     * null
     */
    @Column(name = "DOM")
    private String dom;

    /**
     * null
     */
    @Column(name = "REG_CPTL")
    private String regCptl;

    /**
     * null
     */
    @Column(name = "DT_EST")
    private String dtEst;

    /**
     * null
     */
    @Column(name = "OP_PRD_FROM")
    private String opPrdFrom;

    /**
     * null
     */
    @Column(name = "OP_PRD_TO")
    private String opPrdTo;

    /**
     * null
     */
    @Column(name = "NM_OF_LGL_PRSN")
    private String nmOfLglPrsn;

    /**
     * null
     */
    @Column(name = "TRA_NM")
    private String traNm;

    /**
     * null
     */
    @Column(name = "OP_LOC")
    private String opLoc;

    /**
     * null
     */
    @Column(name = "FD_AMT")
    private String fdAmt;

    /**
     * null
     */
    @Column(name = "DT_REG")
    private String dtReg;

    /**
     * null
     */
    @Column(name = "NM")
    private String nm;

    /**
     * null
     */
    @Column(name = "BIZ_SCP")
    private String bizScp;

    /**
     * null
     * @return INSTG_PTY null
     */
    public String getInstgPty() {
        return instgPty;
    }

    /**
     * null
     * @param instgPty null
     */
    public void setInstgPty(String instgPty) {
        this.instgPty = instgPty;
    }

    /**
     * null
     * @return MSG_ID null
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * null
     * @param msgId null
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * null
     * @return BAS_INFO_NB null
     */
    public Integer getBasInfoNb() {
        return basInfoNb;
    }

    /**
     * null
     * @param basInfoNb null
     */
    public void setBasInfoNb(Integer basInfoNb) {
        this.basInfoNb = basInfoNb;
    }

    /**
     * null
     * @return PLAT_DATE null
     */
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Integer getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Integer platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Integer getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Integer platTime) {
        this.platTime = platTime;
    }

    /**
     * null
     * @return CRE_DT_TM null
     */
    public String getCreDtTm() {
        return creDtTm;
    }

    /**
     * null
     * @param creDtTm null
     */
    public void setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
    }

    /**
     * null
     * @return ORIG_MSG_ID null
     */
    public String getOrigMsgId() {
        return origMsgId;
    }

    /**
     * null
     * @param origMsgId null
     */
    public void setOrigMsgId(String origMsgId) {
        this.origMsgId = origMsgId;
    }

    /**
     * null
     * @return ORIG_INSTG_DRCT_PTY null
     */
    public String getOrigInstgDrctPty() {
        return origInstgDrctPty;
    }

    /**
     * null
     * @param origInstgDrctPty null
     */
    public void setOrigInstgDrctPty(String origInstgDrctPty) {
        this.origInstgDrctPty = origInstgDrctPty;
    }

    /**
     * null
     * @return ORIG_INSTG_PTY null
     */
    public String getOrigInstgPty() {
        return origInstgPty;
    }

    /**
     * null
     * @param origInstgPty null
     */
    public void setOrigInstgPty(String origInstgPty) {
        this.origInstgPty = origInstgPty;
    }

    /**
     * null
     * @return PG_NB null
     */
    public Integer getPgNb() {
        return pgNb;
    }

    /**
     * null
     * @param pgNb null
     */
    public void setPgNb(Integer pgNb) {
        this.pgNb = pgNb;
    }

    /**
     * null
     * @return MARKET_TYPE null
     */
    public String getMarketType() {
        return marketType;
    }

    /**
     * null
     * @param marketType null
     */
    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    /**
     * null
     * @return UNI_SOC_CDT_CD null
     */
    public String getUniSocCdtCd() {
        return uniSocCdtCd;
    }

    /**
     * null
     * @param uniSocCdtCd null
     */
    public void setUniSocCdtCd(String uniSocCdtCd) {
        this.uniSocCdtCd = uniSocCdtCd;
    }

    /**
     * null
     * @return CO_TP null
     */
    public String getCoTp() {
        return coTp;
    }

    /**
     * null
     * @param coTp null
     */
    public void setCoTp(String coTp) {
        this.coTp = coTp;
    }

    /**
     * null
     * @return REG_STS null
     */
    public String getRegSts() {
        return regSts;
    }

    /**
     * null
     * @param regSts null
     */
    public void setRegSts(String regSts) {
        this.regSts = regSts;
    }

    /**
     * null
     * @return REG_AUTH null
     */
    public String getRegAuth() {
        return regAuth;
    }

    /**
     * null
     * @param regAuth null
     */
    public void setRegAuth(String regAuth) {
        this.regAuth = regAuth;
    }

    /**
     * null
     * @return DT_APPR null
     */
    public String getDtAppr() {
        return dtAppr;
    }

    /**
     * null
     * @param dtAppr null
     */
    public void setDtAppr(String dtAppr) {
        this.dtAppr = dtAppr;
    }

    /**
     * null
     * @return ENT_NM null
     */
    public String getEntNm() {
        return entNm;
    }

    /**
     * null
     * @param entNm null
     */
    public void setEntNm(String entNm) {
        this.entNm = entNm;
    }

    /**
     * null
     * @return DOM null
     */
    public String getDom() {
        return dom;
    }

    /**
     * null
     * @param dom null
     */
    public void setDom(String dom) {
        this.dom = dom;
    }

    /**
     * null
     * @return REG_CPTL null
     */
    public String getRegCptl() {
        return regCptl;
    }

    /**
     * null
     * @param regCptl null
     */
    public void setRegCptl(String regCptl) {
        this.regCptl = regCptl;
    }

    /**
     * null
     * @return DT_EST null
     */
    public String getDtEst() {
        return dtEst;
    }

    /**
     * null
     * @param dtEst null
     */
    public void setDtEst(String dtEst) {
        this.dtEst = dtEst;
    }

    /**
     * null
     * @return OP_PRD_FROM null
     */
    public String getOpPrdFrom() {
        return opPrdFrom;
    }

    /**
     * null
     * @param opPrdFrom null
     */
    public void setOpPrdFrom(String opPrdFrom) {
        this.opPrdFrom = opPrdFrom;
    }

    /**
     * null
     * @return OP_PRD_TO null
     */
    public String getOpPrdTo() {
        return opPrdTo;
    }

    /**
     * null
     * @param opPrdTo null
     */
    public void setOpPrdTo(String opPrdTo) {
        this.opPrdTo = opPrdTo;
    }

    /**
     * null
     * @return NM_OF_LGL_PRSN null
     */
    public String getNmOfLglPrsn() {
        return nmOfLglPrsn;
    }

    /**
     * null
     * @param nmOfLglPrsn null
     */
    public void setNmOfLglPrsn(String nmOfLglPrsn) {
        this.nmOfLglPrsn = nmOfLglPrsn;
    }

    /**
     * null
     * @return TRA_NM null
     */
    public String getTraNm() {
        return traNm;
    }

    /**
     * null
     * @param traNm null
     */
    public void setTraNm(String traNm) {
        this.traNm = traNm;
    }

    /**
     * null
     * @return OP_LOC null
     */
    public String getOpLoc() {
        return opLoc;
    }

    /**
     * null
     * @param opLoc null
     */
    public void setOpLoc(String opLoc) {
        this.opLoc = opLoc;
    }

    /**
     * null
     * @return FD_AMT null
     */
    public String getFdAmt() {
        return fdAmt;
    }

    /**
     * null
     * @param fdAmt null
     */
    public void setFdAmt(String fdAmt) {
        this.fdAmt = fdAmt;
    }

    /**
     * null
     * @return DT_REG null
     */
    public String getDtReg() {
        return dtReg;
    }

    /**
     * null
     * @param dtReg null
     */
    public void setDtReg(String dtReg) {
        this.dtReg = dtReg;
    }

    /**
     * null
     * @return NM null
     */
    public String getNm() {
        return nm;
    }

    /**
     * null
     * @param nm null
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * null
     * @return BIZ_SCP null
     */
    public String getBizScp() {
        return bizScp;
    }

    /**
     * null
     * @param bizScp null
     */
    public void setBizScp(String bizScp) {
        this.bizScp = bizScp;
    }
}