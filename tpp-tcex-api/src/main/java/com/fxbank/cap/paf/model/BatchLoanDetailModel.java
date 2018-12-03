package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BatchLoanDetailModel implements Serializable{
	
	private static final long serialVersionUID = 9049732990158364419L;
	@NotNull(message = "batchNo批量编号不能为空")
	@Size(max = 20,message = "batchNo批量编号长度不能大于20") 
	private String batchNo;
	@NotNull(message = "seqNo序号不能为空")
	@Size(max = 20,message = "seqNo序号长度不能大于20") 
	private String seqNo;
	private BigDecimal txAmt;
	private BigDecimal suAmt;
	private String deAcctNo;
	private String deAcctName;
	private String deChgNo;
	private String deOpnChgNo;
	private String deAddr;
	private String proNo;
	private String enghFlag;
	private String summary;
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
	public String getDeAcctNo() {
		return deAcctNo;
	}
	public void setDeAcctNo(String deAcctNo) {
		this.deAcctNo = deAcctNo;
	}
	public String getDeAcctName() {
		return deAcctName;
	}
	public void setDeAcctName(String deAcctName) {
		this.deAcctName = deAcctName;
	}
	public String getDeChgNo() {
		return deChgNo;
	}
	public void setDeChgNo(String deChgNo) {
		this.deChgNo = deChgNo;
	}
	public String getDeOpnChgNo() {
		return deOpnChgNo;
	}
	public void setDeOpnChgNo(String deOpnChgNo) {
		this.deOpnChgNo = deOpnChgNo;
	}
	public String getDeAddr() {
		return deAddr;
	}
	public void setDeAddr(String deAddr) {
		this.deAddr = deAddr;
	}
	public String getProNo() {
		return proNo;
	}
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	public String getEnghFlag() {
		return enghFlag;
	}
	public void setEnghFlag(String enghFlag) {
		this.enghFlag = enghFlag;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getSuAmt() {
		return suAmt;
	}
	public void setSuAmt(BigDecimal suAmt) {
		this.suAmt = suAmt;
	}
	
}
