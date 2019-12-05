package com.fxbank.tpp.mivs.entity.AbnmlVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_abnmlvrfctn_info")
public class MivsAbnmlvrfctnInfoEntity {
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
    @Id
    @Column(name = "MSG_ID")
    private String msgId;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Integer platTime;

    /**
     * null
     */
    @Column(name = "CHECK_TYPE")
    private String checkType;

    /**
     * null
     */
    @Column(name = "MIVS_STS")
    private String mivsSts;

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
    @Column(name = "INSTG_PTY")
    private String instgPty;

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
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * null
     */
    @Column(name = "ORGNL_INSTG_PTY")
    private String orgnlInstgPty;

    /**
     * null
     */
    @Column(name = "CO_NM")
    private String coNm;

    /**
     * null
     */
    @Column(name = "UNI_SOC_CDT_CD")
    private String uniSocCdtCd;

    /**
     * null
     */
    @Column(name = "MOB_NB")
    private String mobNb;

    /**
     * null
     */
    @Column(name = "NM")
    private String nm;

    /**
     * null
     */
    @Column(name = "ABNML_TYPE")
    private String abnmlType;

    /**
     * null
     */
    @Column(name = "DESCRIP")
    private String descrip;

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
     * @return CHECK_TYPE null
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * null
     * @param checkType null
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
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
     * @return ORGNL_INSTG_PTY null
     */
    public String getOrgnlInstgPty() {
        return orgnlInstgPty;
    }

    /**
     * null
     * @param orgnlInstgPty null
     */
    public void setOrgnlInstgPty(String orgnlInstgPty) {
        this.orgnlInstgPty = orgnlInstgPty;
    }

    /**
     * null
     * @return CO_NM null
     */
    public String getCoNm() {
        return coNm;
    }

    /**
     * null
     * @param coNm null
     */
    public void setCoNm(String coNm) {
        this.coNm = coNm;
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
     * @return MOB_NB null
     */
    public String getMobNb() {
        return mobNb;
    }

    /**
     * null
     * @param mobNb null
     */
    public void setMobNb(String mobNb) {
        this.mobNb = mobNb;
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
     * @return ABNML_TYPE null
     */
    public String getAbnmlType() {
        return abnmlType;
    }

    /**
     * null
     * @param abnmlType null
     */
    public void setAbnmlType(String abnmlType) {
        this.abnmlType = abnmlType;
    }

    /**
     * null
     * @return DESCRIP null
     */
    public String getDescrip() {
        return descrip;
    }

    /**
     * null
     * @param descrip null
     */
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}