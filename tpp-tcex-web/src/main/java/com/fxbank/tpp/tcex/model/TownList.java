package com.fxbank.tpp.tcex.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class TownList {
	
	@JSONField(name = "DATA")
	private List<TownInfo>  data = new ArrayList<TownInfo>();

	public List<TownInfo> getData() {
		return data;
	}

	public void setData(List<TownInfo> data) {
		this.data = data;
	}

	
	
}
