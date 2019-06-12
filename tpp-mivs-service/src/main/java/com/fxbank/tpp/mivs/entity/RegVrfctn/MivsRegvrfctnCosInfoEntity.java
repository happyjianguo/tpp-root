package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "MIVS_REGVRFCTN_COS_INFO")
public class MivsRegvrfctnCosInfoEntity {
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
    @Column(name = "CO_SHRHDRFND_INFO_NB")
    private Integer coShrhdrfndInfoNb;

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
    @Column(name = "INVTR_NM")
    private String invtrNm;

    /**
     * null
     */
    @Column(name = "INVTR_ID")
    private String invtrId;

    /**
     * null
     */
    @Column(name = "SUBSCR_CPTL_CON_AMT")
    private String subscrCptlConAmt;

    /**
     * null
     */
    @Column(name = "ACTL_CPTL_CON_AMT")
    private String actlCptlConAmt;

    /**
     * null
     */
    @Column(name = "SUBSCR_CPTL_CON_FM")
    private String subscrCptlConFm;

    /**
     * null
     */
    @Column(name = "SUBSCR_CPTL_CON_DT")
    private String subscrCptlConDt;

    public String getInstgPty() {
        return instgPty;
    }

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
     * @return CO_SHRHDRFND_INFO_NB null
     */
    public Integer getCoShrhdrfndInfoNb() {
        return coShrhdrfndInfoNb;
    }

    /**
     * null
     * @param coShrhdrfndInfoNb null
     */
    public void setCoShrhdrfndInfoNb(Integer coShrhdrfndInfoNb) {
        this.coShrhdrfndInfoNb = coShrhdrfndInfoNb;
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
     * @return INVTR_NM null
     */
    public String getInvtrNm() {
        return invtrNm;
    }

    /**
     * null
     * @param invtrNm null
     */
    public void setInvtrNm(String invtrNm) {
        this.invtrNm = invtrNm;
    }

    /**
     * null
     * @return INVTR_ID null
     */
    public String getInvtrId() {
        return invtrId;
    }

    /**
     * null
     * @param invtrId null
     */
    public void setInvtrId(String invtrId) {
        this.invtrId = invtrId;
    }

    /**
     * null
     * @return SUBSCR_CPTL_CON_AMT null
     */
    public String getSubscrCptlConAmt() {
        return subscrCptlConAmt;
    }

    /**
     * null
     * @param subscrCptlConAmt null
     */
    public void setSubscrCptlConAmt(String subscrCptlConAmt) {
        this.subscrCptlConAmt = subscrCptlConAmt;
    }

    /**
     * null
     * @return ACTL_CPTL_CON_AMT null
     */
    public String getActlCptlConAmt() {
        return actlCptlConAmt;
    }

    /**
     * null
     * @param actlCptlConAmt null
     */
    public void setActlCptlConAmt(String actlCptlConAmt) {
        this.actlCptlConAmt = actlCptlConAmt;
    }

    /**
     * null
     * @return SUBSCR_CPTL_CON_FM null
     */
    public String getSubscrCptlConFm() {
        return subscrCptlConFm;
    }

    /**
     * null
     * @param subscrCptlConFm null
     */
    public void setSubscrCptlConFm(String subscrCptlConFm) {
        this.subscrCptlConFm = subscrCptlConFm;
    }

    /**
     * null
     * @return SUBSCR_CPTL_CON_DT null
     */
    public String getSubscrCptlConDt() {
        return subscrCptlConDt;
    }

    /**
     * null
     * @param subscrCptlConDt null
     */
    public void setSubscrCptlConDt(String subscrCptlConDt) {
        this.subscrCptlConDt = subscrCptlConDt;
    }

}