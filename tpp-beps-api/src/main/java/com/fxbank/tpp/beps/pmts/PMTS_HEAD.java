/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-07-10 08:16:39
 * @LastEditTime: 2019-07-10 08:16:39
 * @LastEditors: your name
 */
package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

import java.io.Serializable;

/**
 * @description: 二代支付XML报文固定格式头
 * @author     : 周勇沩
 * @Date       : 2019/4/20 9:9
*/
public class PMTS_HEAD implements Serializable{

	private static final long serialVersionUID = -2989705168381006729L;

	@FixedField(order = 1, len = 3, desc = "起始标识")
	private String beginFlag = "{H:";

	@FixedField(order = 2, len = 2, desc = "版本号")
	private String versionID = "02";

	@FixedField(order = 3, len = 14, desc = "报文发起人")
	private String origSender;

	@FixedField(order = 4, len = 4, desc = "发送系统号")
	private String origSenderSID = "HVPS";

	@FixedField(order = 5, len = 14, desc = "报文接收人")
	private String origReceiver;

	@FixedField(order = 6, len = 4, desc = "接收系统号")
	private String origReceiverSID = "HVPS";

	@FixedField(order = 7, len = 8, desc = "报文发起日期")
	private Integer origSendDate;

	@FixedField(order = 8, len = 6, desc = "报文发起时间")
	private Integer origSendTime;

	@FixedField(order = 9, len = 3, desc = "格式类型")
	private String structType = "XML";

	@FixedField(order = 10, len = 20, desc = "报文类型代码")
	private String mesgType;

	@FixedField(order = 11, len = 20, desc = "通信级标识号")
	private String mesgID;

	@FixedField(order = 12, len = 20, desc = "通信级参考号")
	private String mesgRefID;

	@FixedField(order = 13, len = 1, desc = "报文优先级")
	private String mesgPriority = "2";

	@FixedField(order = 14, len = 1, desc = "报文传输方向")
	private String mesgDirection = "U";

	@FixedField(order = 15, len = 9, desc = "保留域")
	private String reserve;

	@FixedField(order = 16, len = 3, desc = "结束标识")
	private String endFlag = "}\r\n";

	public String getBeginFlag() {
		return beginFlag;
	}

	public void setBeginFlag(String beginFlag) {
		this.beginFlag = beginFlag;
	}

	public String getVersionID() {
		return versionID;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}

	public String getOrigSender() {
		return origSender;
	}

	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}

	public String getOrigSenderSID() {
		return origSenderSID;
	}

	public void setOrigSenderSID(String origSenderSID) {
		this.origSenderSID = origSenderSID;
	}

	public String getOrigReceiver() {
		return origReceiver;
	}

	public void setOrigReceiver(String origReceiver) {
		this.origReceiver = origReceiver;
	}

	public String getOrigReceiverSID() {
		return origReceiverSID;
	}

	public void setOrigReceiverSID(String origReceiverSID) {
		this.origReceiverSID = origReceiverSID;
	}

	public Integer getOrigSendDate() {
		return origSendDate;
	}

	public void setOrigSendDate(Integer origSendDate) {
		this.origSendDate = origSendDate;
	}

	public Integer getOrigSendTime() {
		return origSendTime;
	}

	public void setOrigSendTime(Integer origSendTime) {
		this.origSendTime = origSendTime;
	}

	public String getStructType() {
		return structType;
	}

	public void setStructType(String structType) {
		this.structType = structType;
	}

	public String getMesgType() {
		return mesgType;
	}

	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}

	public String getMesgID() {
		return mesgID;
	}

	public void setMesgID(String mesgID) {
		this.mesgID = mesgID;
	}

	public String getMesgRefID() {
		return mesgRefID;
	}

	public void setMesgRefID(String mesgRefID) {
		this.mesgRefID = mesgRefID;
	}

	public String getMesgPriority() {
		return mesgPriority;
	}

	public void setMesgPriority(String mesgPriority) {
		this.mesgPriority = mesgPriority;
	}

	public String getMesgDirection() {
		return mesgDirection;
	}

	public void setMesgDirection(String mesgDirection) {
		this.mesgDirection = mesgDirection;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
}