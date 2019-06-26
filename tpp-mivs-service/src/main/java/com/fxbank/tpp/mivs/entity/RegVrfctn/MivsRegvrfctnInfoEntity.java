package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_regvrfctn_info")
public class MivsRegvrfctnInfoEntity {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Id
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
    @Column(name = "SYSTEM_ID")
    private String systemId;

    /**
     * null
     */
    @Column(name = "TRAN_DATE")
    private String tranDate;

    /**
     * null
     */
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     * null
     */
    @Column(name = "TRAN_TIME")
    private String tranTime;

    /**
     * null
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * null
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * null
     */
    @Column(name = "MIVS_STS")
    private String mivsSts;

    /**
     * null
     */
    @Column(name = "MSG_ID")
    private String msgId;

    /**
     * null
     */
    @Column(name = "CRE_DT_TM")
    private String creDtTm;

    /**
     * null
     */
    @Column(name = "INSTG_DRCT_PTY")
    private String instgDrctPty;

    /**
     * null
     */
    @Column(name = "DRCT_PTY_NM")
    private String drctPtyNm;

    /**
     * null
     */
    @Column(name = "INSTG_PTY")
    private String instgPty;

    /**
     * null
     */
    @Column(name = "PTY_NM")
    private String ptyNm;

    /**
     * null
     */
    @Column(name = "INSTD_DRCT_PTY")
    private String instdDrctPty;

    /**
     * null
     */
    @Column(name = "INSTD_PTY")
    private String instdPty;

    /**
     * null
     */
    @Column(name = "RCV_MSG_ID")
    private String rcvMsgId;

    /**
     * null
     */
    @Column(name = "RCV_CRE_DT_TM")
    private String rcvCreDtTm;

    /**
     * null
     */
    @Column(name = "AGT_NM")
    private String agtNm;

    /**
     * null
     */
    @Column(name = "AGT_ID")
    private String agtId;

    /**
     * null
     */
    @Column(name = "OP_NM")
    private String opNm;

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
    @Column(name = "ENT_NM")
    private String entNm;

    /**
     * null
     */
    @Column(name = "NM_OF_LGL_PRSN")
    private String nmOfLglPrsn;

    /**
     * null
     */
    @Column(name = "ID_OF_LGL_PRSN")
    private String idOfLglPrsn;

    /**
     * null
     */
    @Column(name = "TRA_NM")
    private String traNm;

    /**
     * null
     */
    @Column(name = "NM")
    private String nm;

    /**
     * null
     */
    @Column(name = "ID")
    private String id;

    /**
     * null
     */
    @Column(name = "PG_NB")
    private Integer pgNb;

    /**
     * null
     */
    @Column(name = "LAST_PG_IND")
    private String lastPgInd;

    /**
     * null
     */
    @Column(name = "RSLT")
    private String rslt;

    /**
     * null
     */
    @Column(name = "DATA_RESRC_DT")
    private String dataResrcDt;

    /**
     * null
     */
    @Column(name = "BAS_INFO_CNT")
    private Integer basInfoCnt;

    /**
     * null
     */
    @Column(name = "CO_SHRHDRFND_INFO_CNT")
    private Integer coShrhdrfndInfoCnt;

    /**
     * null
     */
    @Column(name = "DIR_SUPSRSGR_INFO_CNT")
    private Integer dirSupsrsgrInfoCnt;

    /**
     * null
     */
    @Column(name = "CHNG_INFO_CNT")
    private Integer chngInfoCnt;

    /**
     * null
     */
    @Column(name = "ABNML_BIZ_INFO_CNT")
    private Integer abnmlBizInfoCnt;

    /**
     * null
     */
    @Column(name = "ILL_DSCRT_INFO_CNT")
    private Integer illDscrtInfoCnt;

    /**
     * null
     */
    @Column(name = "LIC_NULL_CNT")
    private Integer licNullCnt;

    /**
     * null
     */
    @Column(name = "PROC_STS")
    private String procSts;

    /**
     * null
     */
    @Column(name = "PROC_CD")
    private String procCd;

    /**
     * null
     */
    @Column(name = "RJCT_INF")
    private String rjctInf;

    /**
     * null
     */
    @Column(name = "REMARK1")
    private String remark1;

    /**
     * null
     */
    @Column(name = "REMARK2")
    private String remark2;

    /**
     * null
     */
    @Column(name = "REMARK3")
    private String remark3;

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
     * @return SYSTEM_ID null
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * null
     * @param systemId null
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * null
     * @return TRAN_DATE null
     */
    public String getTranDate() {
        return tranDate;
    }

    /**
     * null
     * @param tranDate null
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    /**
     * null
     * @return SEQ_NO null
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * null
     * @param seqNo null
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * null
     * @return TRAN_TIME null
     */
    public String getTranTime() {
        return tranTime;
    }

    /**
     * null
     * @param tranTime null
     */
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    /**
     * null
     * @return USER_ID null
     */
    public String getUserId() {
        return userId;
    }

    /**
     * null
     * @param userId null
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * null
     * @return BRANCH_ID null
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * null
     * @param branchId null
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * null
     * @return MIVS_STS null
     */
    public String getMivsSts() {
        return mivsSts;
    }

    /**
     * null
     * @param mivsSts null
     */
    public void setMivsSts(String mivsSts) {
        this.mivsSts = mivsSts;
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
     * @return INSTG_DRCT_PTY null
     */
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    /**
     * null
     * @param instgDrctPty null
     */
    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    /**
     * null
     * @return DRCT_PTY_NM null
     */
    public String getDrctPtyNm() {
        return drctPtyNm;
    }

    /**
     * null
     * @param drctPtyNm null
     */
    public void setDrctPtyNm(String drctPtyNm) {
        this.drctPtyNm = drctPtyNm;
    }

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
     * @return PTY_NM null
     */
    public String getPtyNm() {
        return ptyNm;
    }

    /**
     * null
     * @param ptyNm null
     */
    public void setPtyNm(String ptyNm) {
        this.ptyNm = ptyNm;
    }

    /**
     * null
     * @return INSTD_DRCT_PTY null
     */
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    /**
     * null
     * @param instdDrctPty null
     */
    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    /**
     * null
     * @return INSTD_PTY null
     */
    public String getInstdPty() {
        return instdPty;
    }

    /**
     * null
     * @param instdPty null
     */
    public void setInstdPty(String instdPty) {
        this.instdPty = instdPty;
    }

    public String getRcvMsgId() {
        return rcvMsgId;
    }

    public void setRcvMsgId(String rcvMsgId) {
        this.rcvMsgId = rcvMsgId;
    }

    public String getRcvCreDtTm() {
        return rcvCreDtTm;
    }

    public void setRcvCreDtTm(String rcvCreDtTm) {
        this.rcvCreDtTm = rcvCreDtTm;
    }

    /**
     * null
     * @return AGT_NM null
     */
    public String getAgtNm() {
        return agtNm;
    }

    /**
     * null
     * @param agtNm null
     */
    public void setAgtNm(String agtNm) {
        this.agtNm = agtNm;
    }

    /**
     * null
     * @return AGT_ID null
     */
    public String getAgtId() {
        return agtId;
    }

    /**
     * null
     * @param agtId null
     */
    public void setAgtId(String agtId) {
        this.agtId = agtId;
    }

    /**
     * null
     * @return OP_NM null
     */
    public String getOpNm() {
        return opNm;
    }

    /**
     * null
     * @param opNm null
     */
    public void setOpNm(String opNm) {
        this.opNm = opNm;
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
     * @return ID_OF_LGL_PRSN null
     */
    public String getIdOfLglPrsn() {
        return idOfLglPrsn;
    }

    /**
     * null
     * @param idOfLglPrsn null
     */
    public void setIdOfLglPrsn(String idOfLglPrsn) {
        this.idOfLglPrsn = idOfLglPrsn;
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
     * @return ID null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id;
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
     * @return LAST_PG_IND null
     */
    public String getLastPgInd() {
        return lastPgInd;
    }

    /**
     * null
     * @param lastPgInd null
     */
    public void setLastPgInd(String lastPgInd) {
        this.lastPgInd = lastPgInd;
    }

    /**
     * null
     * @return RSLT null
     */
    public String getRslt() {
        return rslt;
    }

    /**
     * null
     * @param rslt null
     */
    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    /**
     * null
     * @return DATA_RESRC_DT null
     */
    public String getDataResrcDt() {
        return dataResrcDt;
    }

    /**
     * null
     * @param dataResrcDt null
     */
    public void setDataResrcDt(String dataResrcDt) {
        this.dataResrcDt = dataResrcDt;
    }

    /**
     * null
     * @return BAS_INFO_CNT null
     */
    public Integer getBasInfoCnt() {
        return basInfoCnt;
    }

    /**
     * null
     * @param basInfoCnt null
     */
    public void setBasInfoCnt(Integer basInfoCnt) {
        this.basInfoCnt = basInfoCnt;
    }

    /**
     * null
     * @return CO_SHRHDRFND_INFO_CNT null
     */
    public Integer getCoShrhdrfndInfoCnt() {
        return coShrhdrfndInfoCnt;
    }

    /**
     * null
     * @param coShrhdrfndInfoCnt null
     */
    public void setCoShrhdrfndInfoCnt(Integer coShrhdrfndInfoCnt) {
        this.coShrhdrfndInfoCnt = coShrhdrfndInfoCnt;
    }

    /**
     * null
     * @return DIR_SUPSRSGR_INFO_CNT null
     */
    public Integer getDirSupsrsgrInfoCnt() {
        return dirSupsrsgrInfoCnt;
    }

    /**
     * null
     * @param dirSupsrsgrInfoCnt null
     */
    public void setDirSupsrsgrInfoCnt(Integer dirSupsrsgrInfoCnt) {
        this.dirSupsrsgrInfoCnt = dirSupsrsgrInfoCnt;
    }

    /**
     * null
     * @return CHNG_INFO_CNT null
     */
    public Integer getChngInfoCnt() {
        return chngInfoCnt;
    }

    /**
     * null
     * @param chngInfoCnt null
     */
    public void setChngInfoCnt(Integer chngInfoCnt) {
        this.chngInfoCnt = chngInfoCnt;
    }

    /**
     * null
     * @return ABNML_BIZ_INFO_CNT null
     */
    public Integer getAbnmlBizInfoCnt() {
        return abnmlBizInfoCnt;
    }

    /**
     * null
     * @param abnmlBizInfoCnt null
     */
    public void setAbnmlBizInfoCnt(Integer abnmlBizInfoCnt) {
        this.abnmlBizInfoCnt = abnmlBizInfoCnt;
    }

    /**
     * null
     * @return ILL_DSCRT_INFO_CNT null
     */
    public Integer getIllDscrtInfoCnt() {
        return illDscrtInfoCnt;
    }

    /**
     * null
     * @param illDscrtInfoCnt null
     */
    public void setIllDscrtInfoCnt(Integer illDscrtInfoCnt) {
        this.illDscrtInfoCnt = illDscrtInfoCnt;
    }

    /**
     * null
     * @return LIC_NULL_CNT null
     */
    public Integer getLicNullCnt() {
        return licNullCnt;
    }

    /**
     * null
     * @param licNullCnt null
     */
    public void setLicNullCnt(Integer licNullCnt) {
        this.licNullCnt = licNullCnt;
    }

    /**
     * null
     * @return PROC_STS null
     */
    public String getProcSts() {
        return procSts;
    }

    /**
     * null
     * @param procSts null
     */
    public void setProcSts(String procSts) {
        this.procSts = procSts;
    }

    /**
     * null
     * @return PROC_CD null
     */
    public String getProcCd() {
        return procCd;
    }

    /**
     * null
     * @param procCd null
     */
    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }

    /**
     * null
     * @return RJCT_INF null
     */
    public String getRjctInf() {
        return rjctInf;
    }

    /**
     * null
     * @param rjctInf null
     */
    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    /**
     * null
     * @return REMARK1 null
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * null
     * @param remark1 null
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    /**
     * null
     * @return REMARK2 null
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * null
     * @param remark2 null
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    /**
     * null
     * @return REMARK3 null
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * null
     * @param remark3 null
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}