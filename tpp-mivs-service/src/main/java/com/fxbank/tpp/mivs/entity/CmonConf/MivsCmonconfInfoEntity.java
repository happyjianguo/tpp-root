package com.fxbank.tpp.mivs.entity.CmonConf;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="MIVS_CMONCONF_INFO")
public class MivsCmonconfInfoEntity {
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
    @Column(name = "SYSTEM_CODE")
    private String systemCode;

    /**
     * null
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * null
     */
    @Column(name = "ORGNL_MSG_ID")
    private String orgnlMsgId;

    /**
     * null
     */
    @Column(name = "ORGNL_INSTG_PTY")
    private String orgnlInstgPty;

    /**
     * null
     */
    @Column(name = "ORGNL_MT")
    private String orgnlMt;

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
    @Column(name = "PTY_ID")
    private String ptyId;

    /**
     * null
     */
    @Column(name = "PTY_PRC_CD")
    private String ptyPrcCd;

    /**
     * null
     */
    @Column(name = "RJCT_INF")
    private String rjctInf;

    /**
     * null
     */
    @Column(name = "PRC_DT")
    private String prcDt;

    /**
     * null
     */
    @Column(name = "NETG_RND")
    private String netgRnd;

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
     * @return SYSTEM_CODE null
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * null
     * @param systemCode null
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * null
     * @return REMARK null
     */
    public String getRemark() {
        return remark;
    }

    /**
     * null
     * @param remark null
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * null
     * @return ORGNL_MSG_ID null
     */
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    /**
     * null
     * @param orgnlMsgId null
     */
    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
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
     * @return ORGNL_MT null
     */
    public String getOrgnlMt() {
        return orgnlMt;
    }

    /**
     * null
     * @param orgnlMt null
     */
    public void setOrgnlMt(String orgnlMt) {
        this.orgnlMt = orgnlMt;
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
     * @return PTY_ID null
     */
    public String getPtyId() {
        return ptyId;
    }

    /**
     * null
     * @param ptyId null
     */
    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
    }

    /**
     * null
     * @return PTY_PRC_CD null
     */
    public String getPtyPrcCd() {
        return ptyPrcCd;
    }

    /**
     * null
     * @param ptyPrcCd null
     */
    public void setPtyPrcCd(String ptyPrcCd) {
        this.ptyPrcCd = ptyPrcCd;
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
     * @return PRC_DT null
     */
    public String getPrcDt() {
        return prcDt;
    }

    /**
     * null
     * @param prcDt null
     */
    public void setPrcDt(String prcDt) {
        this.prcDt = prcDt;
    }

    /**
     * null
     * @return NETG_RND null
     */
    public String getNetgRnd() {
        return netgRnd;
    }

    /**
     * null
     * @param netgRnd null
     */
    public void setNetgRnd(String netgRnd) {
        this.netgRnd = netgRnd;
    }
}