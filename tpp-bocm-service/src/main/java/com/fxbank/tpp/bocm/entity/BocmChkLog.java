package com.fxbank.tpp.bocm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkLog {
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
    @Column(name = "SETTLE_DATE")
    private Integer settleDate;

    /**
     * null
     */
    @Column(name = "SETTLE_BRANCH")
    private String settleBranch;

    /**
     * null
     */
    @Column(name = "HOST_DATE")
    private Integer hostDate;

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
    private BigDecimal txAmt;

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



    public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
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
    
    public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
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



    public Integer getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Integer settleDate) {
		this.settleDate = settleDate;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
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