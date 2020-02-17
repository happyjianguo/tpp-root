package com.fxbank.tpp.beps.model.bepsmodel;

import java.io.Serializable;

public class CcmsFreeinfoModel implements  Serializable{


	private static final long serialVersionUID = -8693332601218843964L;
	
	/**
	 * 系统代码
	 */
	private String  sysCode;
	
	/**
	 * 交易机构号
	 */
	private String branchNo;
	/**
	 * 平台日期
	 */
	private Integer  platDate;
	
	/**
	 * 平台流水
	 */
	private Integer  platTraceno;
	
	/**
	 * 报文标识号
	 */
	private String msgid;
	
	/**
	 * 发起参与者
	 */
	private String dtlSndBkno;
	
	/**
	 * 报文类型
	 */
	private String cmtNo;
	
	/**
	 * 信息序号
	 */
	private Integer tssno;
	
	/**
	 * 发报日期
	 */
	private Integer sndDate;
	
	/**
	 * 发报中心
	 */
	private String sndCcpc;
	
	/**
	 * 发起清算行行号
	 */
	private String sndSaBkno;
	
	/**
	 * 发起行行号
	 */	
	private String sndBankNo;
	
	/**
	 * 收报中心
	 */
	private String rcvCcpc;
	
	/**
	 * 接收清算行行号
	 */
	private String rcvSaBkno;
	
	/**
	 * 接收行行号
	 */
	private String rcvBankNo;
	
	/**
	 * 业务类型
	 */
	private String busitype;
	
	/**
	 * 业务种类
	 */
	private String busikind;
	
	/**
	 * 接收方类型   1-参与者 2-CCPC
	 */
	private String infoType;
	
	/**
	 * 处理状态   0-发送成功  1-已收妥
	 */
	private String procState;
	
	/**
	 * 业务来源   00-往 01-来
	 */
	private String infoSource;
	
	/**
	 * 信息标题
	 */
	private String infoTitle;
	
	/**
	 * 信息内容
	 */
	private String information;
	
	/**
	 * 附件长度
	 */
	private Integer attachLen;
	
	/**
	 * 附件名称
	 */
	private String attachName;
	
	/**
	 * 代理标志  
	 */
	private String agency;
	
	/**
	 * 应答信息
	 */
	private String replyInfo;
	
	/**
	 * 业务状态
	 */
	private String npcSts;
	
	/**
	 * 业务处理码
	 */
	private String npcCode;
	
	/**
	 * 业务处理信息
	 */
	private String npcMsg;
	
	/**
	 * 终态日期
	 */
	private Integer npcDate;
	
	/**
	 * 录入员
	 */
	private String putUser;
	
	/**
	 * 来源渠道
	 */
	private String frChnl;
	
	/**
	 * 代理他行 0-否 1-是 2-发送至村镇成功
	 */
	private String ifTown;
	
	/**
	 * 打印次数
	 */
	private Integer printNo;

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTraceno() {
		return platTraceno;
	}

	public void setPlatTraceno(Integer platTraceno) {
		this.platTraceno = platTraceno;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getDtlSndBkno() {
		return dtlSndBkno;
	}

	public void setDtlSndBkno(String dtlSndBkno) {
		this.dtlSndBkno = dtlSndBkno;
	}

	public String getCmtNo() {
		return cmtNo;
	}

	public void setCmtNo(String cmtNo) {
		this.cmtNo = cmtNo;
	}

	public Integer getTssno() {
		return tssno;
	}

	public void setTssno(Integer tssno) {
		this.tssno = tssno;
	}

	public Integer getSndDate() {
		return sndDate;
	}

	public void setSndDate(Integer sndDate) {
		this.sndDate = sndDate;
	}

	public String getSndCcpc() {
		return sndCcpc;
	}

	public void setSndCcpc(String sndCcpc) {
		this.sndCcpc = sndCcpc;
	}

	public String getSndSaBkno() {
		return sndSaBkno;
	}

	public void setSndSaBkno(String sndSaBkno) {
		this.sndSaBkno = sndSaBkno;
	}

	public String getSndBankNo() {
		return sndBankNo;
	}

	public void setSndBankNo(String sndBankNo) {
		this.sndBankNo = sndBankNo;
	}

	public String getRcvCcpc() {
		return rcvCcpc;
	}

	public void setRcvCcpc(String rcvCcpc) {
		this.rcvCcpc = rcvCcpc;
	}

	public String getRcvSaBkno() {
		return rcvSaBkno;
	}

	public void setRcvSaBkno(String rcvSaBkno) {
		this.rcvSaBkno = rcvSaBkno;
	}

	public String getRcvBankNo() {
		return rcvBankNo;
	}

	public void setRcvBankNo(String rcvBankNo) {
		this.rcvBankNo = rcvBankNo;
	}

	public String getBusitype() {
		return busitype;
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	public String getBusikind() {
		return busikind;
	}

	public void setBusikind(String busikind) {
		this.busikind = busikind;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getProcState() {
		return procState;
	}

	public void setProcState(String procState) {
		this.procState = procState;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Integer getAttachLen() {
		return attachLen;
	}

	public void setAttachLen(Integer attachLen) {
		this.attachLen = attachLen;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getReplyInfo() {
		return replyInfo;
	}

	public void setReplyInfo(String replyInfo) {
		this.replyInfo = replyInfo;
	}

	public String getNpcSts() {
		return npcSts;
	}

	public void setNpcSts(String npcSts) {
		this.npcSts = npcSts;
	}

	public String getNpcCode() {
		return npcCode;
	}

	public void setNpcCode(String npcCode) {
		this.npcCode = npcCode;
	}

	public String getNpcMsg() {
		return npcMsg;
	}

	public void setNpcMsg(String npcMsg) {
		this.npcMsg = npcMsg;
	}

	public Integer getNpcDate() {
		return npcDate;
	}

	public void setNpcDate(Integer npcDate) {
		this.npcDate = npcDate;
	}

	public String getPutUser() {
		return putUser;
	}

	public void setPutUser(String putUser) {
		this.putUser = putUser;
	}

	public String getFrChnl() {
		return frChnl;
	}

	public void setFrChnl(String frChnl) {
		this.frChnl = frChnl;
	}

	public String getIfTown() {
		return ifTown;
	}

	public void setIfTown(String ifTown) {
		this.ifTown = ifTown;
	}

	public Integer getPrintNo() {
		return printNo;
	}

	public void setPrintNo(Integer printNo) {
		this.printNo = printNo;
	}


}
