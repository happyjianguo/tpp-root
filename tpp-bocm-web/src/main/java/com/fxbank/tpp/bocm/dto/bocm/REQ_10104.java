/**   
* @Title: REQ_10104.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:30:24 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_10104 
* @Description  工作密钥请求 
* @author YePuLiang
* @date 2019年5月23日 下午3:30:24 
*  
*/
public class REQ_10104 extends REQ_BASE {
	
    public REQ_10104() {
        super.txDesc = "工作密钥请求";
        super.log = false;
	}
    
    @FixedField(order = 8, len = 2, desc = "应用代码")
	private Integer appCod = 40;
    
	@FixedField(order = 9, len = 1, desc = "请求标示")
	private String reqFlg = "1";

	@FixedField(order = 10, len = 2, desc = "渠道标识")
    private String cnlCod = "36";
	
	@FixedField(order = 11, len = 3, desc = "分行号")
    private String brNo = "999";
	
	@FixedField(order = 12, len = 7, desc = "密钥ID")
    private String keyId;
	
	@FixedField(order = 13, len = 1, desc = "密钥类型")
    private Integer keyTyp;
	
	@FixedField(order = 14, len = 2, desc = "密钥类型")
    private String keyLen;

	public Integer getAppCod() {
		return appCod;
	}

	public void setAppCod(Integer appCod) {
		this.appCod = appCod;
	}

	public String getReqFlg() {
		return reqFlg;
	}

	public void setReqFlg(String reqFlg) {
		this.reqFlg = reqFlg;
	}

	public String getCnlCod() {
		return cnlCod;
	}

	public void setCnlCod(String cnlCod) {
		this.cnlCod = cnlCod;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Integer getKeyTyp() {
		return keyTyp;
	}

	public void setKeyTyp(Integer keyTyp) {
		this.keyTyp = keyTyp;
	}

	public String getKeyLen() {
		return keyLen;
	}

	public void setKeyLen(String keyLen) {
		this.keyLen = keyLen;
	}
	
	


}
