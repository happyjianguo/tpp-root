package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author : 周勇沩
 * @description: 报文分片组件报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"Sts", "RjctCd", "RjctInf","PrcPty"})
public class Prttn implements Serializable {

	private static final long serialVersionUID = 2794349176112940263L;
	
	private String TtlNb;
    private String StartNb;
    private String EndNb;
    
	public String getTtlNb() {
		return TtlNb;
	}
	public void setTtlNb(String ttlNb) {
		TtlNb = ttlNb;
	}
	public String getStartNb() {
		return StartNb;
	}
	public void setStartNb(String startNb) {
		StartNb = startNb;
	}
	public String getEndNb() {
		return EndNb;
	}
	public void setEndNb(String endNb) {
		EndNb = endNb;
	}
    
    

}
