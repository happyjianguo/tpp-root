package com.fxbank.tpp.bocm.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkErr {
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
    @Column(name = "PRE_HOST_STATE")
    private String preHostState;

    /**
     * null
     */
    @Column(name = "RE_HOST_STATE")
    private String reHostState;

    /**
     * null
     */
    @Column(name = "DC_FLAG")
    private String dcFlag;

    /**
     * null
     */
    @Column(name = "CHECK_FLAG")
    private String checkFlag;

    /**
     * null
     */
    @Column(name = "DIRECTION")
    private String direction;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private Short txAmt;

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
    @Column(name = "MSG")
    private String msg;

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
     * @return PRE_HOST_STATE null
     */
    public String getPreHostState() {
        return preHostState;
    }

    /**
     * null
     * @param preHostState null
     */
    public void setPreHostState(String preHostState) {
        this.preHostState = preHostState;
    }

    /**
     * null
     * @return RE_HOST_STATE null
     */
    public String getReHostState() {
        return reHostState;
    }

    /**
     * null
     * @param reHostState null
     */
    public void setReHostState(String reHostState) {
        this.reHostState = reHostState;
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
     * @return DIRECTION null
     */
    public String getDirection() {
        return direction;
    }

    /**
     * null
     * @param direction null
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     * @return MSG null
     */
    public String getMsg() {
        return msg;
    }

    /**
     * null
     * @param msg null
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}