package com.fxbank.tpp.tcex.model;

import com.alibaba.fastjson.annotation.JSONField;

public class TownInfo {
	
	@JSONField(name = "TOWN_FLAG")
	private String townFlag;
	
	@JSONField(name = "TOWN_NAME")
	private String townName;

	@JSONField(name = "TOWN_BRANCH")
	private String townBranch;

	public String getTownFlag() {
		return townFlag;
	}

	public void setTownFlag(String townFlag) {
		this.townFlag = townFlag;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getTownBranch() {
		return townBranch;
	}

	public void setTownBranch(String townBranch) {
		this.townBranch = townBranch;
	}

	
}
