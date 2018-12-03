package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class CLI_REQ_BDC extends ModelBase implements Serializable{

	private static final long serialVersionUID = 4862382497852873604L;

	private FIELDS head = new FIELDS();
	
	private BODY body = new BODY();

	public CLI_REQ_BDC(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	public CLI_REQ_BDC(){
		super(null, 0, 0, 0);
	}
	
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
