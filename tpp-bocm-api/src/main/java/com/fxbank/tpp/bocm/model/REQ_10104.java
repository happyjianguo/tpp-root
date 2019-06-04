/**   
* @Title: REQ_10104.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:43:17 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_10104 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月23日 下午3:43:17 
*  
*/
public class REQ_10104 extends REQ_BASE {
	
	private static final long serialVersionUID = 8664446376630924208L;

	@Deprecated
	public REQ_10104() {
		super(null, 0, 0, 0);
	}

    public REQ_10104(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.setTtxnCd("10104");
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
    private Integer keyLen;

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

	public Integer getKeyLen() {
		return keyLen;
	}

	public void setKeyLen(Integer keyLen) {
		this.keyLen = keyLen;
	}





}
