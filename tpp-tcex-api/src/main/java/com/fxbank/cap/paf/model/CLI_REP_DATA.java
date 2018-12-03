package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CLI_REP_DATA  implements Serializable{
	
	private static final long serialVersionUID = -8732489987920570346L;

	private Map<String,Object> head = new HashMap<String,Object>();
	
	private Map<String,Object> body = new HashMap<String,Object>();
	
	private Map<String,Object> bodyList = new HashMap<String,Object>();

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
