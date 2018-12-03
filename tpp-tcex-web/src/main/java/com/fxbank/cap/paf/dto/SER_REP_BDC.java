package com.fxbank.cap.paf.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fxbank.cap.paf.model.BODY;
import com.fxbank.cap.paf.model.FIELDS;
import com.fxbank.cip.base.dto.DataTransObject;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class SER_REP_BDC extends DataTransObject {

	
	@XmlTransient
	private Integer bkTotNum;
	
	@XmlTransient
	private Integer bkRecNum;
	
	private FIELDS head = new FIELDS();
	
	private BODY body = new BODY();

	public FIELDS getHead() {
		return head;
	}

	public void setHead(FIELDS head) {
		this.head = head;
	}


	public BODY getBody() {
		return body;
	}

	public void setBody(BODY body) {
		this.body = body;
	}

	public Integer getBkTotNum() {
		return bkTotNum;
	}

	public void setBkTotNum(Integer bkTotNum) {
		this.bkTotNum = bkTotNum;
	}

	public Integer getBkRecNum() {
		return bkRecNum;
	}

	public void setBkRecNum(Integer bkRecNum) {
		this.bkRecNum = bkRecNum;
	}	
	
}
