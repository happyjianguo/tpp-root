package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @ClassName: BatchLoanUpdMasterModel 
* @Description: 批量贷款扣款主表更新模型
* @author DuZhenduo
* @date 2018年10月26日 下午2:50:00 
*  
*/
public class BatchLoanUpdMasterModel implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	private String txStatus;
	private String oldStatus;
	private String txMsg;
	private String succHostSeqNo;
	private String succHostRspCode;
	private String succHostRspMsg;
	private Integer platDate;
	private Integer platTrace;
	private BigDecimal succAmt;
	private Integer succNum;
	private BigDecimal failAmt;
	private Integer failNum;
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public BigDecimal getSuccAmt() {
		return succAmt;
	}
	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
	}
	public Integer getSuccNum() {
		return succNum;
	}
	public void setSuccNum(Integer succNum) {
		this.succNum = succNum;
	}
	public BigDecimal getFailAmt() {
		return failAmt;
	}
	public void setFailAmt(BigDecimal failAmt) {
		this.failAmt = failAmt;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getTxMsg() {
		return txMsg;
	}
	public void setTxMsg(String txMsg) {
		this.txMsg = txMsg;
	}
	public String getSuccHostSeqNo() {
		return succHostSeqNo;
	}
	public void setSuccHostSeqNo(String succHostSeqNo) {
		this.succHostSeqNo = succHostSeqNo;
	}
	public String getSuccHostRspCode() {
		return succHostRspCode;
	}
	public void setSuccHostRspCode(String succHostRspCode) {
		this.succHostRspCode = succHostRspCode;
	}
	public String getSuccHostRspMsg() {
		return succHostRspMsg;
	}
	public void setSuccHostRspMsg(String succHostRspMsg) {
		this.succHostRspMsg = succHostRspMsg;
	}
	public Integer getPlatDate() {
		return platDate;
	}
	public void setPlatDate(Integer integer) {
		this.platDate = integer;
	}
	public Integer getPlatTrace() {
		return platTrace;
	}
	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

}
