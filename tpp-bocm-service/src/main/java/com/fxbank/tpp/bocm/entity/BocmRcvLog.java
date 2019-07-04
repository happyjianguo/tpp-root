package com.fxbank.tpp.bocm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmRcvLog {
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
    @Column(name = "CUR_TIME")
    private Long curTime;

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

    @Column(name = "TRAN_TYPE")
    private String tranType;
    
    /**
     * null
     */
    @Column(name = "TX_IND")
    private String txInd;
    
    /**
     * null
     */
    @Column(name = "TX_CODE")
    private String txCode;

    /**
     * null
     */
    @Column(name = "DC_FLAG")
    private String dcFlag;

    /**
     * null
     */
    @Column(name = "ACT_BAL")
    private BigDecimal actBal;
    
    /**
     * null
     */
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;
    
    /**
     * null
     */
    @Column(name = "TX_DATE")
    private Integer txDate;
    
    /**
     * null
     */
    @Column(name = "FEE_FLAG")
    private String feeFlag;
    
    /**
     * null
     */
    @Column(name = "FEE")
    private BigDecimal fee;
    
    /**
     * null
     */
    @Column(name = "SND_BANKNO")
    private String sndBankno;
    
    /**
     * null
     */
    @Column(name = "RCV_BANKNO")
    private String rcvBankno;
    
    /**
     * null
     */
    @Column(name = "PAYER_BANK")
    private String payerBank;
    
    /**
     * null
     */
    @Column(name = "PAYER_ACTTP")
    private String payerActtp;
    
    /**
     * null
     */
    @Column(name = "PAYEE_BANK")
    private String payeeBank;
    
    /**
     * null
     */
    @Column(name = "PAYEE_ACTTP")
    private String payeeActtp;

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
    private Integer bocmDate;
    
    
    /**
     * null
     */
    @Column(name = "BOCM_TIME")
    private Integer bocmTime;

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
    @Column(name = "HOST_BRANCH")
    private String hostBranch;


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

	public Integer getPlatTime() {
		return platTime;
	}

	public void setPlatTime(Integer platTime) {
		this.platTime = platTime;
	}

	public Long getCurTime() {
		return curTime;
	}

	public void setCurTime(Long curTime) {
		this.curTime = curTime;
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
    
    public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
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

    public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

    public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
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

    public Integer getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(Integer bocmDate) {
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

	public Integer getBocmTime() {
		return bocmTime;
	}

	public void setBocmTime(Integer bocmTime) {
		this.bocmTime = bocmTime;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getRcvBankno() {
		return rcvBankno;
	}

	public void setRcvBankno(String rcvBankno) {
		this.rcvBankno = rcvBankno;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayerActtp() {
		return payerActtp;
	}

	public void setPayerActtp(String payerActtp) {
		this.payerActtp = payerActtp;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getPayeeActtp() {
		return payeeActtp;
	}

	public void setPayeeActtp(String payeeActtp) {
		this.payeeActtp = payeeActtp;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}
	
	
    
}