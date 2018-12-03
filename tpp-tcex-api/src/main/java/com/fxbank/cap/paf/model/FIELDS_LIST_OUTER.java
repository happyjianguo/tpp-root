package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class FIELDS_LIST_OUTER implements Serializable{

	private static final long serialVersionUID = -6321077967105623296L;
	
	@XmlAttribute
	private String name;
	
	@XmlElement(name="field-list")
	private List<FIELDS_LIST_INNER> field_list ;
	
	public FIELDS_LIST_OUTER(String name){
		this.name = name;
	}
	
	public FIELDS_LIST_OUTER(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<FIELDS_LIST_INNER> getField_list() {
		return field_list;
	}

	public void setField_list(List<FIELDS_LIST_INNER> field_list) {
		this.field_list = field_list;
	}

}
