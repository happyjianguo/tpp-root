package com.fxbank.cap.paf.dto;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.dto.DataTransObject;

public class SER_REQ_DATA extends DataTransObject {
	
	private String packHeader;

	private Map<String,Object> head = new HashMap<String,Object>();
	
	private Map<String,Object> body = new HashMap<String,Object>();
	
	private Map<String,Object> bodyList = new HashMap<String,Object>();

	public String getPackHeader() {
		return packHeader;
	}

	public void setPackHeader(String packHeader) {
		this.packHeader = packHeader;
	}

	public Map<String, Object> getHead() {
		return head;
	}

	public void setHead(Map<String, Object> head) {
		this.head = head;
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	public Map<String, Object> getBodyList() {
		return bodyList;
	}

	public void setBodyList(Map<String, Object> bodyList) {
		this.bodyList = bodyList;
	}
	
}
