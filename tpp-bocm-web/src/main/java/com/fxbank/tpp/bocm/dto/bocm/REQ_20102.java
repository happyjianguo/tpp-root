/**   
* @Title: REQ_20102.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 上午10:10:10 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import java.math.BigDecimal;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_20102 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月6日 上午10:10:10 
*  
*/
public class REQ_20102 extends REQ_BASE {
	
    public REQ_20102() {
        super.txDesc = "账户信息查询";
        super.log = false;
	}
	
	@FixedField(order = 8, len = 3, desc = "币种")
	private String ccyCod = "CNY";
	
	@FixedField(order = 9, len = 1, desc = "账号类型")
	private String actTyp;
	
	@FixedField(order = 10, len = 32, desc = "账号") 
	private String actNo;
	
	@FixedField(order = 11, len = 2, desc = "交易类型")
	private String txnTyp;
	
	@FixedField(order = 12, len = 12, desc = "开户行行号")
	private String actBnk;
	
	@FixedField(order = 13, len = 1, desc = "手续费收取方式")
	private String feeFlg;
	
	@FixedField(order = 14, len = 15, desc = "交易金额")
	private Double txnAmt;
	

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getActTyp() {
		return actTyp;
	}

	public void setActTyp(String actTyp) {
		this.actTyp = actTyp;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getTxnTyp() {
		return txnTyp;
	}

	public void setTxnTyp(String txnTyp) {
		this.txnTyp = txnTyp;
	}

	public String getActBnk() {
		return actBnk;
	}

	public void setActBnk(String actBnk) {
		this.actBnk = actBnk;
	}

	public String getFeeFlg() {
		return feeFlg;
	}

	public void setFeeFlg(String feeFlg) {
		this.feeFlg = feeFlg;
	}

	public Double getTxnAmt() {
		txnAmt = txnAmt/100;
		return txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		txnAmt = txnAmt*100;
		this.txnAmt = txnAmt;
	}



    
    
}
