package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QrySingleCapTradeModel implements Serializable{
	
	private static final long serialVersionUID = 3722823871112888140L;

	@NotNull(message = "platDate平台日期不能为空")
	private Integer platDate;//平台日期
	
	@NotNull(message = "platTrace平台流水号不能为空")
	private Integer platTrace;//平台流水号
	
	@Size(max = 32,message = "capSeqNo本金核心系统流水号长度不能大于32")    
	private String capSeqNo;//本金核心系统流水号

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

	public String getCapSeqNo() {
		return capSeqNo;
	}

	public void setCapSeqNo(String capSeqNo) {
		this.capSeqNo = capSeqNo;
	}

}
