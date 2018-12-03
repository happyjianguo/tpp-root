package com.fxbank.cap.manager.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class PafCenterList {
	
	@JSONField(name = "DATA")
	private List<PafCenterInfo>  data = new ArrayList<PafCenterInfo>();

	public List<PafCenterInfo> getData() {
		return data;
	}

	public void setData(List<PafCenterInfo> data) {
		this.data = data;
	}
	
}
