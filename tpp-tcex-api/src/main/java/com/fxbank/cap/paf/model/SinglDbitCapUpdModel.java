package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class SinglDbitCapUpdModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public SinglDbitCapUpdModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@NotNull(message = "txStatus交易状态不能为空")
	private String txStatus;//交易状态
	
	@NotNull(message = "capSeqno本金核心系统流水号不能为空")
	@Size(max = 32,message = "capSeqno本金核心系统流水号长度不能大于32") 
	private String capSeqno;//本金核心系统流水号
	
	@NotNull(message = "capHostcode本金核心系统响应代码不能为空")
	@Size(max = 20,message = "capHostcode本金核心系统响应代码长度不能大于20")    
	private String capHostcode;//本金核心系统响应代码

	@NotNull(message = "capHostmsg本金核心系统响应信息不能为空")
	@Size(max = 120,message = "capHostmsg本金核心系统响应信息长度不能大于120")  
	private String capHostmsg;//本金核心系统响应信息
	
	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getCapSeqno() {
		return capSeqno;
	}

	public void setCapSeqno(String capSeqno) {
		this.capSeqno = capSeqno;
	}

	public String getCapHostcode() {
		return capHostcode;
	}

	public void setCapHostcode(String capHostcode) {
		this.capHostcode = capHostcode;
	}

	public String getCapHostmsg() {
		return capHostmsg;
	}

	public void setCapHostmsg(String capHostmsg) {
		this.capHostmsg = capHostmsg;
	}

}
