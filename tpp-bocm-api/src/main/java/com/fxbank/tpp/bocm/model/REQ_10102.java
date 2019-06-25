/**   
* @Title: REQ_20102.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:43:14 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_20102 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月9日 上午10:43:14 
*  
*/
public class REQ_10102 extends REQ_BASE {
	
	private static final long serialVersionUID = -83548845382386681L;
	
    @Deprecated
	public REQ_10102() {
		super(null, 0, 0, 0);
	}

    public REQ_10102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.setTtxnCd("10102");
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
		return txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = AmtBase.toPack(txnAmt);
	}


}
