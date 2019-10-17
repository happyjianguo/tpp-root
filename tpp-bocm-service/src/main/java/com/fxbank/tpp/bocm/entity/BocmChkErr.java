package com.fxbank.tpp.bocm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkErr {
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
    @Column(name = "TX_SOURCE")
    private String txSource;
    
    /**
     * null
     */
    @Column(name = "TX_CODE")
    private String txCode;
    
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
    @Column(name = "TX_DATE")
    private Integer txDate;
    
    /**
     * null
     */
    @Column(name = "SND_BANKNO")
    private String sndBankno;
    
    /**
     * null
     */
    @Column(name = "TX_BRANCH")
    private String txBranch;
    
    /**
     * null
     */
    @Column(name = "TX_TEL")
    private String txTel;

    /**
     * null
     */
    @Column(name = "TX_IND")
    private String txInd;
      
    /**
     * null
     */
    @Column(name = "PROXY_FLAG")
    private String proxyFlag;
    
    /**
     * null
     */
    @Column(name = "PROXY_FEE")
    private BigDecimal proxyFee;
    
    /**
     * null
     */
    @Column(name = "PAYER_BANK")
    private String payerBank;
    
    /**
     * null
     */
    @Column(name = "PAYEE_BANK")
    private String payeeBank;
    
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
    @Column(name = "HOST_FLAG")
    private String hostFlag;
    
    /**
     * null
     */
    @Column(name = "BOCM_FLAG")
    private String bocmFlag;

    /**
     * null
     */
    @Column(name = "DIRECTION")
    private String direction;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;
    
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
    @Column(name = "MSG")
    private String msg;


    public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
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

    public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
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

	public String getTxSource() {
		return txSource;
	}

	public void setTxSource(String txSource) {
		this.txSource = txSource;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getTxBranch() {
		return txBranch;
	}

	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}

	public String getTxTel() {
		return txTel;
	}

	public void setTxTel(String txTel) {
		this.txTel = txTel;
	}

	public String getTxInd() {
		return txInd;
	}

	public void setTxInd(String txInd) {
		this.txInd = txInd;
	}

	public String getProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(String proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public BigDecimal getProxyFee() {
		return proxyFee;
	}

	public void setProxyFee(BigDecimal proxyFee) {
		this.proxyFee = proxyFee;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public String getHostFlag() {
		return hostFlag;
	}

	public void setHostFlag(String hostFlag) {
		this.hostFlag = hostFlag;
	}

	public String getBocmFlag() {
		return bocmFlag;
	}

	public void setBocmFlag(String bocmFlag) {
		this.bocmFlag = bocmFlag;
	}
    
    
}