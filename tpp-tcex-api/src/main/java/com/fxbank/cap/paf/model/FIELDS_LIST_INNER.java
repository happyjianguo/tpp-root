package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class FIELDS_LIST_INNER implements Serializable{

	private static final long serialVersionUID = -6321077967105623296L;
	
	@XmlAttribute
	private String name;
	
	private List<FIELD> field = new ArrayList<FIELD>();
	
	public FIELDS_LIST_INNER(){
	}
	
	public FIELDS_LIST_INNER(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FIELD> getField() {
		return field;
	}

	public void setField(List<FIELD> field) {
		this.field = field;
	}

}
