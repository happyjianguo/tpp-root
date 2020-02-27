package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author : 周勇沩
 * @description: 业务应答信息报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"Sts", "RjctCd", "RjctInf","PrcPty"})
public class RspnInf implements Serializable {
 
	private static final long serialVersionUID = -6977892675725560801L;
	
	private String Sts;
    private String RjctCd;
    private String RjctInf;
    private String PrcPty;
    
	public String getSts() {
		return Sts;
	}
	public void setSts(String sts) {
		Sts = sts;
	}
	public String getRjctCd() {
		return RjctCd;
	}
	public void setRjctCd(String rjctCd) {
		RjctCd = rjctCd;
	}
	public String getRjctInf() {
		return RjctInf;
	}
	public void setRjctInf(String rjctInf) {
		RjctInf = rjctInf;
	}
	public String getPrcPty() {
		return PrcPty;
	}
	public void setPrcPty(String prcPty) {
		PrcPty = prcPty;
	}

    
}
