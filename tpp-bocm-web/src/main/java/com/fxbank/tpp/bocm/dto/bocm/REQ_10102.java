package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_10102 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月6日 上午10:10:10 
*  
*/
public class REQ_10102 extends REQ_BASE {
	
    public REQ_10102() {
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
	
	@FixedField(order = 12, len = 1, desc = "手续费收取方式")
	private String feeFlg;
	
	@FixedField(order = 13, len = 15, desc = "交易金额")
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
