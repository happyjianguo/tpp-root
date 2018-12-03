package com.fxbank.cap.manager.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class PafAcNoList {
	
	@JSONField(name = "DATA")
	private List<PafAcNoInfo>  data = new ArrayList<PafAcNoInfo>();

	public List<PafAcNoInfo> getData() {
		return data;
	}

	public void setData(List<PafAcNoInfo> data) {
		this.data = data;
	}
	
}
