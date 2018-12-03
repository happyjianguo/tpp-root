package com.fxbank.cap.paf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class BODY implements Serializable{
	
	private static final long serialVersionUID = -3855675728909845409L;

	private List<FIELD> field = new ArrayList<FIELD>();
	
	@XmlElement(name="field-list")
	private FIELDS_LIST_OUTER field_list ;

	public List<FIELD> getField() {
		return field;
	}

	public void setField(List<FIELD> field) {
		this.field = field;
	}

	@XmlTransient
	public FIELDS_LIST_OUTER getField_list() {
		return field_list;
	}

	public void setField_list(FIELDS_LIST_OUTER field_list) {
		this.field_list = field_list;
	}

}
