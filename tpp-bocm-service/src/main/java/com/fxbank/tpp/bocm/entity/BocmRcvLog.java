package com.fxbank.tpp.bocm.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmRcvLog {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Long platDate;

    /**
     * null
     */
    @Id
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
    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    /**
     * null
     */
    @Column(name = "TX_BRANCH")
    private String txBranch;

    /**
     * null
     */
    @Column(name = "TX_IND")
    private String txInd;

    /**
     * null
     */
    @Column(name = "DC_FLAG")
    private String dcFlag;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private Short txAmt;

    /**
     * null
     */
    @Column(name = "HOST_DATE")
    private Long hostDate;

    /**
     * null
     */
    @Column(name = "HOST_TRACENO")
    private String hostTraceno;

    /**
     * null
     */
    @Column(name = "PAYER_ACNO")
    private String payerAcno;

    /**
     * null
     */
    @Column(name = "PAYER_NAME")
    private String payerName;

    /**
     * null
     */
    @Column(name = "PAYEE_ACNO")
    private String payeeAcno;

    /**
     * null
     */
    @Column(name = "PAYEE_NAME")
    private String payeeName;

    /**
     * null
     */
    @Column(name = "BOCM_BRANCH")
    private String bocmBranch;

    /**
     * null
     */
    @Column(name = "BOCM_DATE")
    private Long bocmDate;

    /**
     * null
     */
    @Column(name = "BOCM_TRACENO")
    private String bocmTraceno;

    /**
     * null
     */
    @Column(name = "CHECK_FLAG")
    private String checkFlag;

    /**
     * null
     */
    @Column(name = "HOST_STATE")
    private String hostState;

    /**
     * null
     */
    @Column(name = "BOCM_STATE")
    private String bocmState;

    /**
     * null
     */
    @Column(name = "TX_TEL")
    private String txTel;

    /**
     * null
     */
    @Column(name = "CHK_TEL")
    private String chkTel;

    /**
     * null
     */
    @Column(name = "AUTH_TEL")
    private String authTel;

    /**
     * null
     */
    @Column(name = "PRINT")
    private String print;

    /**
     * null
     */
    @Column(name = "INFO")
    private String info;

    /**
     * null
     */
    @Column(name = "RET_CODE")
    private String retCode;

    /**
     * null
     */
    @Column(name = "RET_MSG")
    private String retMsg;

    /**
     * null
     */
    @Column(name = "BOCM_FLAG")
    private String bocmFlag;

    /**
     * null
     */
    @Column(name = "HOST_BRANCH")
    private String hostBranch;

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
     * @return SOURCE_TYPE null
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * null
     * @param sourceType null
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * null
     * @return TX_BRANCH null
     */
    public String getTxBranch() {
        return txBranch;
    }

    /**
     * null
     * @param txBranch null
     */
    public void setTxBranch(String txBranch) {
        this.txBranch = txBranch;
    }

    /**
     * null
     * @return TX_IND null
     */
    public String getTxInd() {
        return txInd;
    }

    /**
     * null
     * @param txInd null
     */
    public void setTxInd(String txInd) {
        this.txInd = txInd;
    }

    /**
     * null
     * @return DC_FLAG null
     */
    public String getDcFlag() {
        return dcFlag;
    }

    /**
     * null
     * @param dcFlag null
     */
    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    /**
     * null
     * @return TX_AMT null
     */
    public Short getTxAmt() {
        return txAmt;
    }

    /**
     * null
     * @param txAmt null
     */
    public void setTxAmt(Short txAmt) {
        this.txAmt = txAmt;
    }

    /**
     * null
     * @return HOST_DATE null
     */
    public Long getHostDate() {
        return hostDate;
    }

    /**
     * null
     * @param hostDate null
     */
    public void setHostDate(Long hostDate) {
        this.hostDate = hostDate;
    }

    /**
     * null
     * @return HOST_TRACENO null
     */
    public String getHostTraceno() {
        return hostTraceno;
    }

    /**
     * null
     * @param hostTraceno null
     */
    public void setHostTraceno(String hostTraceno) {
        this.hostTraceno = hostTraceno;
    }

    /**
     * null
     * @return PAYER_ACNO null
     */
    public String getPayerAcno() {
        return payerAcno;
    }

    /**
     * null
     * @param payerAcno null
     */
    public void setPayerAcno(String payerAcno) {
        this.payerAcno = payerAcno;
    }

    /**
     * null
     * @return PAYER_NAME null
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * null
     * @param payerName null
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    /**
     * null
     * @return PAYEE_ACNO null
     */
    public String getPayeeAcno() {
        return payeeAcno;
    }

    /**
     * null
     * @param payeeAcno null
     */
    public void setPayeeAcno(String payeeAcno) {
        this.payeeAcno = payeeAcno;
    }

    /**
     * null
     * @return PAYEE_NAME null
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * null
     * @param payeeName null
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * null
     * @return BOCM_BRANCH null
     */
    public String getBocmBranch() {
        return bocmBranch;
    }

    /**
     * null
     * @param bocmBranch null
     */
    public void setBocmBranch(String bocmBranch) {
        this.bocmBranch = bocmBranch;
    }

    /**
     * null
     * @return BOCM_DATE null
     */
    public Long getBocmDate() {
        return bocmDate;
    }

    /**
     * null
     * @param bocmDate null
     */
    public void setBocmDate(Long bocmDate) {
        this.bocmDate = bocmDate;
    }

    /**
     * null
     * @return BOCM_TRACENO null
     */
    public String getBocmTraceno() {
        return bocmTraceno;
    }

    /**
     * null
     * @param bocmTraceno null
     */
    public void setBocmTraceno(String bocmTraceno) {
        this.bocmTraceno = bocmTraceno;
    }

    /**
     * null
     * @return CHECK_FLAG null
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * null
     * @param checkFlag null
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * null
     * @return HOST_STATE null
     */
    public String getHostState() {
        return hostState;
    }

    /**
     * null
     * @param hostState null
     */
    public void setHostState(String hostState) {
        this.hostState = hostState;
    }

    /**
     * null
     * @return BOCM_STATE null
     */
    public String getBocmState() {
        return bocmState;
    }

    /**
     * null
     * @param bocmState null
     */
    public void setBocmState(String bocmState) {
        this.bocmState = bocmState;
    }

    /**
     * null
     * @return TX_TEL null
     */
    public String getTxTel() {
        return txTel;
    }

    /**
     * null
     * @param txTel null
     */
    public void setTxTel(String txTel) {
        this.txTel = txTel;
    }

    /**
     * null
     * @return CHK_TEL null
     */
    public String getChkTel() {
        return chkTel;
    }

    /**
     * null
     * @param chkTel null
     */
    public void setChkTel(String chkTel) {
        this.chkTel = chkTel;
    }

    /**
     * null
     * @return AUTH_TEL null
     */
    public String getAuthTel() {
        return authTel;
    }

    /**
     * null
     * @param authTel null
     */
    public void setAuthTel(String authTel) {
        this.authTel = authTel;
    }

    /**
     * null
     * @return PRINT null
     */
    public String getPrint() {
        return print;
    }

    /**
     * null
     * @param print null
     */
    public void setPrint(String print) {
        this.print = print;
    }

    /**
     * null
     * @return INFO null
     */
    public String getInfo() {
        return info;
    }

    /**
     * null
     * @param info null
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * null
     * @return RET_CODE null
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * null
     * @param retCode null
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * null
     * @return RET_MSG null
     */
    public String getRetMsg() {
        return retMsg;
    }

    /**
     * null
     * @param retMsg null
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * null
     * @return BOCM_FLAG null
     */
    public String getBocmFlag() {
        return bocmFlag;
    }

    /**
     * null
     * @param bocmFlag null
     */
    public void setBocmFlag(String bocmFlag) {
        this.bocmFlag = bocmFlag;
    }

    /**
     * null
     * @return HOST_BRANCH null
     */
    public String getHostBranch() {
        return hostBranch;
    }

    /**
     * null
     * @param hostBranch null
     */
    public void setHostBranch(String hostBranch) {
        this.hostBranch = hostBranch;
    }
}