package com.fxbank.cap.manager.model;

import com.alibaba.fastjson.annotation.JSONField;

public class PafCenterInfo {
	
	@JSONField(name = "PAFC_NO")
	private String pafcNo;
	
	@JSONField(name = "PAFC_NAME")
	private String pafcName;

	@JSONField(name = "DEPART_CODE")
	private String departCode;

	public String getPafcNo() {
		return pafcNo;
	}

	public void setPafcNo(String pafcNo) {
		this.pafcNo = pafcNo;
	}

	public String getPafcName() {
		return pafcName;
	}

	public void setPafcName(String pafcName) {
		this.pafcName = pafcName;
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
}
