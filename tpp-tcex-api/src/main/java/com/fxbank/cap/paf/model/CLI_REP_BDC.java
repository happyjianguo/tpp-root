package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class CLI_REP_BDC implements Serializable{

	private static final long serialVersionUID = 453856687555272220L;

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
	
}
