package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @ClassName: BocmChkStatusModel 
* @Description: 对账状态Model
* @author YePuLiang
* @date 2019年7月2日 上午9:19:10 
*  
*/
public class BocmChkStatusModel implements Serializable{

	private static final long serialVersionUID = -1804273320664907651L;
	
	//账务日期
    private Integer txDate;
    //对账日期
    private Integer chkDate;
    //对账时间
    private Integer chkTime;
    //与核心对账状态
    private Integer hostStatus;
    //与交行对账状态
    private Integer bocmStatus;
    //我行对账状态
    private Integer platStatus;
    //我行对账状态
    private Integer platTxCnt;
    //我行对账状态
    private BigDecimal platTxAmt;
    //交行为主交易笔数
    private Integer bocmTxCnt;
    //交行为主交易金额
    private BigDecimal bocmTxAmt;
    //我行为主交易笔数
    private Integer hostTxCnt;
    //我行为主交易金额
    private BigDecimal hostTxAmt;
    //交易机构
    private String txBranch;
    //交易柜员
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