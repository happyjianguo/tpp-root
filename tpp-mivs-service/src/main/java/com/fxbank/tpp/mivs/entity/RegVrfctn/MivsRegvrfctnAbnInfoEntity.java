package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MIVS_REGVRFCTN_ABN_INFO")
public class MivsRegvrfctnAbnInfoEntity {
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
    @Column(name = "ABN_INFO_NB")
    private Integer abnInfoNb;

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
    @Column(name = "ABNML_CAUSE")
    private String abnmlCause;

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
     * @return ABN_INFO_NB null
     */
    public Integer getAbnInfoNb() {
        return abnInfoNb;
    }

    /**
     * null
     * @param abnInfoNb null
     */
    public void setAbnInfoNb(Integer abnInfoNb) {
        this.abnInfoNb = abnInfoNb;
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
     * @return ABNML_CAUSE null
     */
    public String getAbnmlCause() {
        return abnmlCause;
    }

    /**
     * null
     * @param abnmlCause null
     */
    public void setAbnmlCause(String abnmlCause) {
        this.abnmlCause = abnmlCause;
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