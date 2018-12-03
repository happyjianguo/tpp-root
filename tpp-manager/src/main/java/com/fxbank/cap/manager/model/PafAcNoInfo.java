package com.fxbank.cap.manager.model;

import com.alibaba.fastjson.annotation.JSONField;

public class PafAcNoInfo {
	
	@JSONField(name = "CLI_NO")
	private String cliNo;
	
	@JSONField(name = "AC_NO")
	private String acNo;
	
	@JSONField(name = "AC_NAME")
	private String acName;
	
	@JSONField(name = "BR_NO")
	private String brNo;
	
	@JSONField(name = "BR_NAME")
	private String brName;
	
	@JSONField(name = "AC_TYPE")
	private String acType;
	
	@JSONField(name = "AC_KIND")
	private String acKind;
	
	@JSONField(name = "AC_BITYPE")
	private String acBitype;
	
	@JSONField(name = "AC_STS")
	private String acSts;

	public String getCliNo() {
		return cliNo;
	}

	public void setCliNo(String cliNo) {
		this.cliNo = cliNo;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAcKind() {
		return acKind;
	}

	public void setAcKind(String acKind) {
		this.acKind = acKind;
	}

	public String getAcBitype() {
		return acBitype;
	}

	public void setAcBitype(String acBitype) {
		this.acBitype = acBitype;
	}

	public String getAcSts() {
		return acSts;
	}

	public void setAcSts(String acSts) {
		this.acSts = acSts;
	}
	
}
