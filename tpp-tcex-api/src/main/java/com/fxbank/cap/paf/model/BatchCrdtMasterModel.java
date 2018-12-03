package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;


public class BatchCrdtMasterModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = -3103730354919980252L;
	public BatchCrdtMasterModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	private String sndUnitNo;
	private String deAcctno;
	private String deAcctname;
	private String txStatus;
	private String batchNo;//批量编号CRDT开头，贷款是LOAN开头
	private String batchFileName;
	private String localFilePath;
	private String remoteFilePath;
	private String fileType;
	private BigDecimal capAmt;
	private Integer totalNum;
	private BigDecimal totalAmt;
	private Integer succNum;
	private BigDecimal succAmt;
	private Integer failNum;
	private BigDecimal failAmt;
	private String sndDate;
	private Integer batchPrjNo;
	private String busType;
	private String currNo;
	private String currIden;
	private String operNo;
	private String sndSeqNo;
	private String txMsg;
	private String remark;
	private String succHostSeqNo;
	private String rollHostSeqNo;
	
	public String getSuccHostSeqNo() {
		return succHostSeqNo;
	}
	public void setSuccHostSeqNo(String succHostSeqNo) {
		this.succHostSeqNo = succHostSeqNo;
	}
	public String getRollHostSeqNo() {
		return rollHostSeqNo;
	}
	public void setRollHostSeqNo(String rollHostSeqNo) {
		this.rollHostSeqNo = rollHostSeqNo;
	}
	public String getSndUnitNo() {
		return sndUnitNo;
	}
	public void setSndUnitNo(String sndUnitNo) {
		this.sndUnitNo = sndUnitNo;
	}
	public String getDeAcctno() {
		return deAcctno;
	}
	public void setDeAcctno(String deAcctno) {
		this.deAcctno = deAcctno;
	}
	public String getDeAcctname() {
		return deAcctname;
	}
	public void setDeAcctname(String deAcctname) {
		this.deAcctname = deAcctname;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchFileName() {
		return batchFileName;
	}
	public void setBatchFileName(String batchFileName) {
		this.batchFileName = batchFileName;
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	public String getRemoteFilePath() {
		return remoteFilePath;
	}
	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public BigDecimal getCapAmt() {
		return capAmt;
	}
	public void setCapAmt(BigDecimal capAmt) {
		this.capAmt = capAmt;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public Integer getSuccNum() {
		return succNum;
	}
	public void setSuccNum(Integer succNum) {
		this.succNum = succNum;
	}
	public BigDecimal getSuccAmt() {
		return succAmt;
	}
	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public BigDecimal getFailAmt() {
		return failAmt;
	}
	public void setFailAmt(BigDecimal failAmt) {
		this.failAmt = failAmt;
	}
	public String getSndDate() {
		return sndDate;
	}
	public void setSndDate(String sndDate) {
		this.sndDate = sndDate;
	}
	public Integer getBatchPrjNo() {
		return batchPrjNo;
	}
	public void setBatchPrjNo(Integer batchPrjNo) {
		this.batchPrjNo = batchPrjNo;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getCurrNo() {
		return currNo;
	}
	public void setCurrNo(String currNo) {
		this.currNo = currNo;
	}
	public String getCurrIden() {
		return currIden;
	}
	public void setCurrIden(String currIden) {
		this.currIden = currIden;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getSndSeqNo() {
		return sndSeqNo;
	}
	public void setSndSeqNo(String sndSeqNo) {
		this.sndSeqNo = sndSeqNo;
	}
	public String getTxMsg() {
		return txMsg;
	}
	public void setTxMsg(String txMsg) {
		this.txMsg = txMsg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
