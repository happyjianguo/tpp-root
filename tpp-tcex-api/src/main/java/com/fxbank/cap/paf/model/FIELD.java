package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class FIELD implements Serializable{
	
	private static final long serialVersionUID = 2595938244458583911L;

	@XmlAttribute
	private String name;
	
	@XmlValue
	private String value;
	
	public FIELD(){}
	
	public FIELD(String name,String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
