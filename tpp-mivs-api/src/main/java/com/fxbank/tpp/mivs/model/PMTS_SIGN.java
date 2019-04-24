package com.fxbank.tpp.mivs.model;

import java.io.Serializable;

/**
 * @Description: 二代支付XML报文签名域
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:09:01
 */
public class PMTS_SIGN implements Serializable{

	private static final long serialVersionUID = -7624287062337443798L;

	private String beginFlag = "{S:";
	private String digitalSignature = null;
	private String endFlag = "}\r\n";

	public String creaFixPack() {
		if (this.digitalSignature == null) { // 无签名
			return "";
		} else { // 有签名
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s", this.beginFlag == null ? "" : this.beginFlag));
			sb.append(String.format("%s", this.digitalSignature == null ? "" : this.digitalSignature));
			sb.append(String.format("%s", this.endFlag == null ? "" : this.endFlag));
			return sb.toString();
		}
	}

	public void chanFixPack(String sign) {
		StringBuffer sb = new StringBuffer(sign);
		int i = 0;
		this.beginFlag = sb.substring(0, i = i + 3);
		this.digitalSignature = sb.substring(0, sb.length() - 1);
		this.endFlag = sb.substring(sb.length() - 1, sb.length());
	}

	/**
	 * @param digitalSignature the digitalSignature to set
	 */
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

}