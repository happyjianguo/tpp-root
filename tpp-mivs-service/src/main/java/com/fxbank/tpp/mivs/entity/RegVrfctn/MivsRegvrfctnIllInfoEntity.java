package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_regvrfctn_ill_info")
public class MivsRegvrfctnIllInfoEntity {
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
    @Column(name = "ILL_INFO_NB")
    private Integer illInfoNb;

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
    @Column(name = "ILL_DSCRT_CAUSE")
    private String illDscrtCause;

    /**
     * null
     */
    @Column(name = "ABNML_DATE")
    private String abnmlDate;

    /**
     * null
     */
    @Column(name = "ABNML_CAUSE_DCSN_AUTH")
    private String abnmlCauseDcsnAuth;

    /**
     * null
     */
    @Column(name = "RMV_CAUSE")
    private String rmvCause;

    /**
     * null
     */
    @Column(name = "RMV_DATE")
    private String rmvDate;

    /**
     * null
     */
    @Column(name = "RMV_CAUSE_DCSN_AUTH")
    private String rmvCauseDcsnAuth;

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
     * @return ILL_INFO_NB null
     */
    public Integer getIllInfoNb() {
        return illInfoNb;
    }

    /**
     * null
     * @param illInfoNb null
     */
    public void setIllInfoNb(Integer illInfoNb) {
        this.illInfoNb = illInfoNb;
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
     * @return ILL_DSCRT_CAUSE null
     */
    public String getIllDscrtCause() {
        return illDscrtCause;
    }

    /**
     * null
     * @param illDscrtCause null
     */
    public void setIllDscrtCause(String illDscrtCause) {
        this.illDscrtCause = illDscrtCause;
    }

    /**
     * null
     * @return ABNML_DATE null
     */
    public String getAbnmlDate() {
        return abnmlDate;
    }

    /**
     * null
     * @param abnmlDate null
     */
    public void setAbnmlDate(String abnmlDate) {
        this.abnmlDate = abnmlDate;
    }

    /**
     * null
     * @return ABNML_CAUSE_DCSN_AUTH null
     */
    public String getAbnmlCauseDcsnAuth() {
        return abnmlCauseDcsnAuth;
    }

    /**
     * null
     * @param abnmlCauseDcsnAuth null
     */
    public void setAbnmlCauseDcsnAuth(String abnmlCauseDcsnAuth) {
        this.abnmlCauseDcsnAuth = abnmlCauseDcsnAuth;
    }

    /**
     * null
     * @return RMV_CAUSE null
     */
    public String getRmvCause() {
        return rmvCause;
    }

    /**
     * null
     * @param rmvCause null
     */
    public void setRmvCause(String rmvCause) {
        this.rmvCause = rmvCause;
    }

    /**
     * null
     * @return RMV_DATE null
     */
    public String getRmvDate() {
        return rmvDate;
    }

    /**
     * null
     * @param rmvDate null
     */
    public void setRmvDate(String rmvDate) {
        this.rmvDate = rmvDate;
    }

    /**
     * null
     * @return RMV_CAUSE_DCSN_AUTH null
     */
    public String getRmvCauseDcsnAuth() {
        return rmvCauseDcsnAuth;
    }

    /**
     * null
     * @param rmvCauseDcsnAuth null
     */
    public void setRmvCauseDcsnAuth(String rmvCauseDcsnAuth) {
        this.rmvCauseDcsnAuth = rmvCauseDcsnAuth;
    }
}