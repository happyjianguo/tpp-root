package com.fxbank.tpp.tcex.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/** 
* @ClassName: TownList 
* @Description: 查询来往账村镇信息列表 
* @author Duzhenduo
* @date 2019年1月31日 上午10:09:01 
*  
*/
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
