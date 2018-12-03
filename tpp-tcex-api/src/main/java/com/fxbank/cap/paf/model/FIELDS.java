package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class FIELDS implements Serializable{

	private static final long serialVersionUID = -6321077967105623296L;
	
	private List<FIELD> field = new ArrayList<FIELD>();

	public List<FIELD> getField() {
		return field;
	}

	public void setField(List<FIELD> field) {
		this.field = field;
	}	

}
