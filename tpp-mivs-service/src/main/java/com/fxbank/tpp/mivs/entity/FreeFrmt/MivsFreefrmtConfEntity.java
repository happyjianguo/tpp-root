package com.fxbank.tpp.mivs.entity.FreeFrmt;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_freefrmt_conf")
public class MivsFreefrmtConfEntity {
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
    @Column(name = "ORIG_DLV_MSGID")
    private String origDlvMsgid;

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
    @Column(name = "MSG_CNTT")
    private String msgCntt;

    /**
     * null
     */
    @Column(name = "RCV_MSG_ID")
    private String rcvMsgId;

    /**
     * null
     */
    @Column(name = "MSG_PRC_CD")
    private String msgPrcCd;

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

    /**
     * null
     * @return ORIG_DLV_MSGID null
     */
    public String getOrigDlvMsgid() {
        return origDlvMsgid;
    }

    /**
     * null
     * @param origDlvMsgid null
     */
    public void setOrigDlvMsgid(String origDlvMsgid) {
        this.origDlvMsgid = origDlvMsgid;
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
     * @return MSG_CNTT null
     */
    public String getMsgCntt() {
        return msgCntt;
    }

    /**
     * null
     * @param msgCntt null
     */
    public void setMsgCntt(String msgCntt) {
        this.msgCntt = msgCntt;
    }

    /**
     * null
     * @return RCV_MSG_ID null
     */
    public String getRcvMsgId() {
        return rcvMsgId;
    }

    /**
     * null
     * @param rcvMsgId null
     */
    public void setRcvMsgId(String rcvMsgId) {
        this.rcvMsgId = rcvMsgId;
    }

    /**
     * null
     * @return MSG_PRC_CD null
     */
    public String getMsgPrcCd() {
        return msgPrcCd;
    }

    /**
     * null
     * @param msgPrcCd null
     */
    public void setMsgPrcCd(String msgPrcCd) {
        this.msgPrcCd = msgPrcCd;
    }
}