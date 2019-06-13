package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_regvrfctn_lic_info")
public class MivsRegvrfctnLicInfoEntity {
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
    @Column(name = "LIC_INFO_NB")
    private Integer licInfoNb;

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
    @Column(name = "ORGNL_OR_CP")
    private String orgnlOrCp;

    /**
     * null
     */
    @Column(name = "LIC_NULL_STM_DT")
    private String licNullStmDt;

    /**
     * null
     */
    @Column(name = "RPL_STS")
    private String rplSts;

    /**
     * null
     */
    @Column(name = "RPL_DT")
    private String rplDt;

    /**
     * null
     */
    @Column(name = "LIC_CP_NB")
    private String licCpNb;

    /**
     * null
     */
    @Column(name = "LIC_NULL_STM_CNTT")
    private String licNullStmCntt;

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
     * @return LIC_INFO_NB null
     */
    public Integer getLicInfoNb() {
        return licInfoNb;
    }

    /**
     * null
     * @param licInfoNb null
     */
    public void setLicInfoNb(Integer licInfoNb) {
        this.licInfoNb = licInfoNb;
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
     * @return ORGNL_OR_CP null
     */
    public String getOrgnlOrCp() {
        return orgnlOrCp;
    }

    /**
     * null
     * @param orgnlOrCp null
     */
    public void setOrgnlOrCp(String orgnlOrCp) {
        this.orgnlOrCp = orgnlOrCp;
    }

    /**
     * null
     * @return LIC_NULL_STM_DT null
     */
    public String getLicNullStmDt() {
        return licNullStmDt;
    }

    /**
     * null
     * @param licNullStmDt null
     */
    public void setLicNullStmDt(String licNullStmDt) {
        this.licNullStmDt = licNullStmDt;
    }

    /**
     * null
     * @return RPL_STS null
     */
    public String getRplSts() {
        return rplSts;
    }

    /**
     * null
     * @param rplSts null
     */
    public void setRplSts(String rplSts) {
        this.rplSts = rplSts;
    }

    /**
     * null
     * @return RPL_DT null
     */
    public String getRplDt() {
        return rplDt;
    }

    /**
     * null
     * @param rplDt null
     */
    public void setRplDt(String rplDt) {
        this.rplDt = rplDt;
    }

    /**
     * null
     * @return LIC_CP_NB null
     */
    public String getLicCpNb() {
        return licCpNb;
    }

    /**
     * null
     * @param licCpNb null
     */
    public void setLicCpNb(String licCpNb) {
        this.licCpNb = licCpNb;
    }

    /**
     * null
     * @return LIC_NULL_STM_CNTT null
     */
    public String getLicNullStmCntt() {
        return licNullStmCntt;
    }

    /**
     * null
     * @param licNullStmCntt null
     */
    public void setLicNullStmCntt(String licNullStmCntt) {
        this.licNullStmCntt = licNullStmCntt;
    }
}