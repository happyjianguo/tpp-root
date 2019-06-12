package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MIVS_REGVRFCTN_CHNG_INFO")
public class MivsRegvrfctnChngInfoEntity {
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
    @Column(name = "CHNG_INFO_NB")
    private Short chngInfoNb;

    /**
     * null
     */
    @Column(name = "PLAT_DATE")
    private Long platDate;

    /**
     * null
     */
    @Column(name = "PLAT_TRACE")
    private Long platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Long platTime;

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
    @Column(name = "CHNG_ITM")
    private String chngItm;

    /**
     * null
     */
    @Column(name = "BF_CHNG")
    private String bfChng;

    /**
     * null
     */
    @Column(name = "AFT_CHNG")
    private String aftChng;

    /**
     * null
     */
    @Column(name = "DT_OF_CHNG")
    private String dtOfChng;

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
     * @return CHNG_INFO_NB null
     */
    public Short getChngInfoNb() {
        return chngInfoNb;
    }

    /**
     * null
     * @param chngInfoNb null
     */
    public void setChngInfoNb(Short chngInfoNb) {
        this.chngInfoNb = chngInfoNb;
    }

    /**
     * null
     * @return PLAT_DATE null
     */
    public Long getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Long platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Long getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Long platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Long getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Long platTime) {
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
     * @return CHNG_ITM null
     */
    public String getChngItm() {
        return chngItm;
    }

    /**
     * null
     * @param chngItm null
     */
    public void setChngItm(String chngItm) {
        this.chngItm = chngItm;
    }

    /**
     * null
     * @return BF_CHNG null
     */
    public String getBfChng() {
        return bfChng;
    }

    /**
     * null
     * @param bfChng null
     */
    public void setBfChng(String bfChng) {
        this.bfChng = bfChng;
    }

    /**
     * null
     * @return AFT_CHNG null
     */
    public String getAftChng() {
        return aftChng;
    }

    /**
     * null
     * @param aftChng null
     */
    public void setAftChng(String aftChng) {
        this.aftChng = aftChng;
    }

    /**
     * null
     * @return DT_OF_CHNG null
     */
    public String getDtOfChng() {
        return dtOfChng;
    }

    /**
     * null
     * @param dtOfChng null
     */
    public void setDtOfChng(String dtOfChng) {
        this.dtOfChng = dtOfChng;
    }
}