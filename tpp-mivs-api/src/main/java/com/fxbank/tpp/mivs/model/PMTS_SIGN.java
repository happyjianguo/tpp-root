package com.fxbank.tpp.mivs.model;

/**
 * @Description: 二代支付XML报文签名域
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:09:01
 */
public class PMTS_SIGN {

	private String BeginFlag = "{S:";
	private String DigitalSignature = null;
	private String EndFlag = "}\r\n";

	public String creaFixPack() {
		if (DigitalSignature== null) {	//无签名
			return "";
		} else {						//有签名
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s", this.BeginFlag == null ? "" : this.BeginFlag));
			sb.append(String.format("%s", this.DigitalSignature == null ? "" : this.DigitalSignature));
			sb.append(String.format("%s", this.EndFlag == null ? "" : this.EndFlag));
			return sb.toString();
		}
	}

	public void chanFixPack(String sign) {

	}

	/**
	 * @param digitalSignature the digitalSignature to set
	 */
	public void setDigitalSignature(String digitalSignature) {
		DigitalSignature = digitalSignature;
	}

}