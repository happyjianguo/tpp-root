package com.fxbank.tpp.bocm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkStatus {
    /**
     * null
     */
    @Id
    @Column(name = "TX_DATE")
    private Integer txDate;
    
    /**
     * null
     */
    @Column(name = "CHK_DATE")
    private Integer chkDate;
    
    /**
     * null
     */
    @Column(name = "CHK_TIME")
    private Integer chkTime;

    /**
     * null
     */
    @Column(name = "HOST_STATUS")
    private Integer hostStatus;

       /**
     * null
     */
    @Column(name = "BOCM_STATUS")
    private Integer bocmStatus;
	
	/**
     * null
     */
    @Column(name = "PLAT_STATUS")
    private Integer platStatus;
    
	/**
     * null
     */
    @Column(name = "PLAT_TX_CNT")
    private Integer platTxCnt;
    
	/**
     * null
     */
    @Column(name = "PLAT_TX_AMT")
    private BigDecimal platTxAmt;
    
	/**
     * null
     */
    @Column(name = "BOCM_TX_CNT")
    private Integer bocmTxCnt;
    
	/**
     * null
     */
    @Column(name = "BOCM_TX_AMT")
    private BigDecimal bocmTxAmt;
    
	/**
     * null
     */
    @Column(name = "HOST_TX_CNT")
    private Integer hostTxCnt;
    
	/**
     * null
     */
    @Column(name = "HOST_TX_AMT")
    private BigDecimal hostTxAmt;

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
    
	public Integer getChkDate() {
		return chkDate;
	}

	public void setChkDate(Integer chkDate) {
		this.chkDate = chkDate;
	}

	public Integer getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(Integer hostStatus) {
		this.hostStatus = hostStatus;
	}

	public Integer getBocmStatus() {
		return bocmStatus;
	}

	public void setBocmStatus(Integer bocmStatus) {
		this.bocmStatus = bocmStatus;
	}

	public Integer getPlatStatus() {
		return platStatus;
	}

	public void setPlatStatus(Integer platStatus) {
		this.platStatus = platStatus;
	}

	public Integer getBocmTxCnt() {
		return bocmTxCnt;
	}

	public void setBocmTxCnt(Integer bocmTxCnt) {
		this.bocmTxCnt = bocmTxCnt;
	}

	public BigDecimal getBocmTxAmt() {
		return bocmTxAmt;
	}

	public void setBocmTxAmt(BigDecimal bocmTxAmt) {
		this.bocmTxAmt = bocmTxAmt;
	}

	public Integer getHostTxCnt() {
		return hostTxCnt;
	}

	public void setHostTxCnt(Integer hostTxCnt) {
		this.hostTxCnt = hostTxCnt;
	}

	public BigDecimal getHostTxAmt() {
		return hostTxAmt;
	}

	public void setHostTxAmt(BigDecimal hostTxAmt) {
		this.hostTxAmt = hostTxAmt;
	}

	public Integer getPlatTxCnt() {
		return platTxCnt;
	}

	public void setPlatTxCnt(Integer platTxCnt) {
		this.platTxCnt = platTxCnt;
	}

	public BigDecimal getPlatTxAmt() {
		return platTxAmt;
	}

	public void setPlatTxAmt(BigDecimal platTxAmt) {
		this.platTxAmt = platTxAmt;
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

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public Integer getChkTime() {
		return chkTime;
	}

	public void setChkTime(Integer chkTime) {
		this.chkTime = chkTime;
	}





}