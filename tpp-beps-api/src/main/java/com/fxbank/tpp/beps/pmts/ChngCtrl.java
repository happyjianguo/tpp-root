package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author : 周勇沩
 * @description: 数据变更组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"ChngTp", "FctvTp", "FctvDt", "IfctvDt"})
public class ChngCtrl implements Serializable {

	private static final long serialVersionUID = -1464250703054134241L;
	
	private String ChngTp;
    private String FctvTp;
    private String FctvDt;
	private String IfctvDt;
	
	public String getChngTp() {
		return ChngTp;
	}
	public void setChngTp(String chngTp) {
		ChngTp = chngTp;
	}
	public String getFctvTp() {
		return FctvTp;
	}
	public void setFctvTp(String fctvTp) {
		FctvTp = fctvTp;
	}
	public String getFctvDt() {
		return FctvDt;
	}
	public void setFctvDt(String fctvDt) {
		FctvDt = fctvDt;
	}
	public String getIfctvDt() {
		return IfctvDt;
	}
	public void setIfctvDt(String ifctvDt) {
		IfctvDt = ifctvDt;
	}

	

}
