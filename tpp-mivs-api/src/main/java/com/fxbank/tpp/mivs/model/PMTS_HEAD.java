package com.fxbank.tpp.mivs.model;

/**
 * @Description: 二代支付XML报文固定格式头
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:09:58
 */
public class PMTS_HEAD {

	private String beginFlag = "{H:";
	private String versionID = "02";
	private String origSender;			//报文发起人
	private String origSenderSID= "MIVS";
	private String origReceiver; 		//报文接收人
	private String origReceiverSID= "MIVS";
	private Integer origSendDate;		//报文发起日期
	private Integer origSendTime; 		//报文发起时间
	private String structType= "XML";
	private String mesgType;			//报文类型代码
	private String mesgID;				//通信级标识号
	private String mesgRefID;			//通信级参考号
	private String mesgPriority= "3";
	private String mesgDirection= "U";
	private String reserve;
	private String endFlag = "}\r\n";

	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s", this.beginFlag == null ? "" : this.beginFlag));
		sb.append(String.format("%s", this.versionID == null ? "" : this.versionID));
		sb.append(String.format("%-14s", this.origSender== null ? "" : this.origSender));
		sb.append(String.format("%s", this.origSenderSID== null ? "" : this.origSenderSID));
		sb.append(String.format("%-14s", this.origReceiver== null ? "" : this.origReceiver));
		sb.append(String.format("%s", this.origReceiverSID== null ? "" : this.origReceiverSID));
		sb.append(String.format("%08d", this.origSendDate== null ? "" : this.origSendDate));
		sb.append(String.format("%06d", this.origSendTime== null ? "" : this.origSendTime));
		sb.append(String.format("%s", this.structType== null ? "" : this.structType));
		sb.append(String.format("%-20s", this.mesgType== null ? "" : this.mesgType));
		sb.append(String.format("%-20s", this.mesgID== null ? "" : this.mesgID));
		sb.append(String.format("%-20s", this.mesgRefID== null ? "" : this.mesgRefID));
		sb.append(String.format("%s", this.mesgPriority== null ? "" : this.mesgPriority));
		sb.append(String.format("%s", this.mesgDirection== null ? "" : this.mesgDirection));
		sb.append(String.format("%-9s", this.reserve== null ? "" : this.reserve));
		sb.append(String.format("%s", this.endFlag == null ? "" : this.endFlag));
		return sb.toString();
	}

	/**
	 * @return the mesgRefID
	 */
	public String getMesgRefID() {
		return mesgRefID;
	}

	/**
	 * @param mesgRefID the mesgRefID to set
	 */
	public void setMesgRefID(String mesgRefID) {
		this.mesgRefID = mesgRefID;
	}

	/**
	 * @return the mesgID
	 */
	public String getMesgID() {
		return mesgID;
	}

	/**
	 * @param mesgID the mesgID to set
	 */
	public void setMesgID(String mesgID) {
		this.mesgID = mesgID;
	}

	/**
	 * @return the mesgType
	 */
	public String getMesgType() {
		return mesgType;
	}

	/**
	 * @param mesgType the mesgType to set
	 */
	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}

	/**
	 * @return the origSendTime
	 */
	public Integer getOrigSendTime() {
		return origSendTime;
	}

	/**
	 * @param origSendTime the origSendTime to set
	 */
	public void setOrigSendTime(Integer origSendTime) {
		this.origSendTime = origSendTime;
	}

	/**
	 * @return the origSendDate
	 */
	public Integer getOrigSendDate() {
		return origSendDate;
	}

	/**
	 * @param origSendDate the origSendDate to set
	 */
	public void setOrigSendDate(Integer origSendDate) {
		this.origSendDate = origSendDate;
	}

	/**
	 * @return the origReceiver
	 */
	public String getOrigReceiver() {
		return origReceiver;
	}

	/**
	 * @param origReceiver the origReceiver to set
	 */
	public void setOrigReceiver(String origReceiver) {
		this.origReceiver = origReceiver;
	}

	/**
	 * @return the origSender
	 */
	public String getOrigSender() {
		return origSender;
	}

	/**
	 * @param origSender the origSender to set
	 */
	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}

	public void chanFixPack(String head) {

	}

}