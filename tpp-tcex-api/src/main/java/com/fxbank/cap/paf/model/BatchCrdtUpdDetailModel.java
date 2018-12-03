package com.fxbank.cap.paf.model;

import java.io.Serializable;

/** 
* @ClassName: BatchCrdtUpdMasterModel 
* @Description: 批量付款附表更新模型
* @author DuZhenduo
* @date 2018年10月26日 下午2:50:00 
*  
*/
public class BatchCrdtUpdDetailModel implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	private String txStatus;
	private String hostSeqNo;
	private String hostRspCode;
	private String hostRspMsg;
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	private String batchNo;
	private String seqNo;
	private String oldStatus;
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

}
