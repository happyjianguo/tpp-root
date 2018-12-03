package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BatchCrdtDetailModel implements Serializable{
	
	private static final long serialVersionUID = 9049732990158364419L;
	@NotNull(message = "batchNo批量编号不能为空")
	@Size(max = 20,message = "batchNo批量编号长度不能大于20") 
	private String batchNo;
	@NotNull(message = "seqNo序号不能为空")
	@Size(max = 20,message = "seqNo序号长度不能大于20") 
	private String seqNo;
	private BigDecimal txAmt;
	private String crAcctNo;
	private String crAcctName;
	private String crChgNo;
	private String crOpnChgNo;
	private String crAddr;
	private String summary;
	private String refAcctNo;
	private String refSeqNo1;
	private String refSeqNo2;
	private BigDecimal capAmt;
	private BigDecimal intAmt;
	private String txStatus;
	private String hostSeqNo;
	private String hostRspCode;
	private String hostRspMsg;
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public BigDecimal getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}
	public String getCrAcctNo() {
		return crAcctNo;
	}
	public void setCrAcctNo(String crAcctNo) {
		this.crAcctNo = crAcctNo;
	}
	public String getCrAcctName() {
		return crAcctName;
	}
	public void setCrAcctName(String crAcctName) {
		this.crAcctName = crAcctName;
	}
	public String getCrChgNo() {
		return crChgNo;
	}
	public void setCrChgNo(String crChgNo) {
		this.crChgNo = crChgNo;
	}
	public String getCrOpnChgNo() {
		return crOpnChgNo;
	}
	public void setCrOpnChgNo(String crOpnChgNo) {
		this.crOpnChgNo = crOpnChgNo;
	}
	public String getCrAddr() {
		return crAddr;
	}
	public void setCrAddr(String crAddr) {
		this.crAddr = crAddr;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRefAcctNo() {
		return refAcctNo;
	}
	public void setRefAcctNo(String refAcctNo) {
		this.refAcctNo = refAcctNo;
	}
	public String getRefSeqNo1() {
		return refSeqNo1;
	}
	public void setRefSeqNo1(String refSeqNo1) {
		this.refSeqNo1 = refSeqNo1;
	}
	public String getRefSeqNo2() {
		return refSeqNo2;
	}
	public void setRefSeqNo2(String refSeqNo2) {
		this.refSeqNo2 = refSeqNo2;
	}
	public BigDecimal getCapAmt() {
		return capAmt;
	}
	public void setCapAmt(BigDecimal capAmt) {
		this.capAmt = capAmt;
	}
	public BigDecimal getIntAmt() {
		return intAmt;
	}
	public void setIntAmt(BigDecimal intAmt) {
		this.intAmt = intAmt;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getHostSeqNo() {
		return hostSeqNo;
	}
	public void setHostSeqNo(String hostSeqNo) {
		this.hostSeqNo = hostSeqNo;
	}
	public String getHostRspCode() {
		return hostRspCode;
	}
	public void setHostRspCode(String hostRspCode) {
		this.hostRspCode = hostRspCode;
	}
	public String getHostRspMsg() {
		return hostRspMsg;
	}
	public void setHostRspMsg(String hostRspMsg) {
		this.hostRspMsg = hostRspMsg;
	}
	
}
