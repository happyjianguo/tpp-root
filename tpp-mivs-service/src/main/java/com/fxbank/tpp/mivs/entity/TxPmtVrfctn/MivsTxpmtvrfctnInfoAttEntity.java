package com.fxbank.tpp.mivs.entity.TxPmtVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_txpmtvrfctn_info_att")
public class MivsTxpmtvrfctnInfoAttEntity {
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
    @Column(name = "TXPMT_INF_NB")
    private Integer txpmtInfNb;

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
    @Column(name = "INSTG_PTY")
    private String instgPty;

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
    @Column(name = "TX_AUTH_CD")
    private String txAuthCd;

    /**
     * null
     */
    @Column(name = "TX_AUTH_NM")
    private String txAuthNm;

    /**
     * null
     */
    @Column(name = "TXPYR_STS")
    private String txpyrSts;

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
     * @return TXPMT_INF_NB null
     */
    public Integer getTxpmtInfNb() {
        return txpmtInfNb;
    }

    /**
     * null
     * @param txpmtInfNb null
     */
    public void setTxpmtInfNb(Integer txpmtInfNb) {
        this.txpmtInfNb = txpmtInfNb;
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
     * @return TX_AUTH_CD null
     */
    public String getTxAuthCd() {
        return txAuthCd;
    }

    /**
     * null
     * @param txAuthCd null
     */
    public void setTxAuthCd(String txAuthCd) {
        this.txAuthCd = txAuthCd;
    }

    /**
     * null
     * @return TX_AUTH_NM null
     */
    public String getTxAuthNm() {
        return txAuthNm;
    }

    /**
     * null
     * @param txAuthNm null
     */
    public void setTxAuthNm(String txAuthNm) {
        this.txAuthNm = txAuthNm;
    }

    /**
     * null
     * @return TXPYR_STS null
     */
    public String getTxpyrSts() {
        return txpyrSts;
    }

    /**
     * null
     * @param txpyrSts null
     */
    public void setTxpyrSts(String txpyrSts) {
        this.txpyrSts = txpyrSts;
    }
}