package com.fxbank.tpp.bocm.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkLog {
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
    @Column(name = "SETTLE_DATE")
    private Long settleDate;

    /**
     * null
     */
    @Column(name = "SETTLE_BRANCH")
    private String settleBranch;

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
    @Column(name = "CCY")
    private String ccy;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private Short txAmt;

    /**
     * null
     */
    @Column(name = "ACCOUNTNO")
    private String accountno;

    /**
     * null
     */
    @Column(name = "REVERSAL")
    private String reversal;

    /**
     * null
     */
    @Column(name = "TX_STATUS")
    private String txStatus;

    /**
     * null
     */
    @Column(name = "DIRECTION")
    private String direction;

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
     * @return SETTLE_DATE null
     */
    public Long getSettleDate() {
        return settleDate;
    }

    /**
     * null
     * @param settleDate null
     */
    public void setSettleDate(Long settleDate) {
        this.settleDate = settleDate;
    }

    /**
     * null
     * @return SETTLE_BRANCH null
     */
    public String getSettleBranch() {
        return settleBranch;
    }

    /**
     * null
     * @param settleBranch null
     */
    public void setSettleBranch(String settleBranch) {
        this.settleBranch = settleBranch;
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
     * @return CCY null
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * null
     * @param ccy null
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
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
     * @return ACCOUNTNO null
     */
    public String getAccountno() {
        return accountno;
    }

    /**
     * null
     * @param accountno null
     */
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    /**
     * null
     * @return REVERSAL null
     */
    public String getReversal() {
        return reversal;
    }

    /**
     * null
     * @param reversal null
     */
    public void setReversal(String reversal) {
        this.reversal = reversal;
    }

    /**
     * null
     * @return TX_STATUS null
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * null
     * @param txStatus null
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
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
}