package com.fxbank.tpp.tcex.model;

import com.alibaba.fastjson.annotation.JSONField;

/** 
* @ClassName: TownInfo 
* @Description: 查询来往账村镇信息
* @author Duzhenduo
* @date 2019年1月31日 上午10:07:52 
*  
*/
public class TownInfo {
	
	@JSONField(name = "TOWN_FLAG")
	private String townFlag;
	
	@JSONField(name = "TOWN_NAME")
	private String townName;

	@JSONField(name = "TOWN_BRANCH")
	private String townBranch;
	
	@JSONField(name = "CASH_BRANCH")
	private String cashBranch;
	
	

	public String getCashBranch() {
		return cashBranch;
	}

	public void setCashBranch(String cashBranch) {
		this.cashBranch = cashBranch;
	}

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
