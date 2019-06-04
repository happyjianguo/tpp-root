package com.fxbank.tpp.bocm.dto.bocm;

import java.math.BigDecimal;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.tpp.bocm.model.AmtBase;


/** 
* @ClassName: REP_20000 
* @Description: IC卡通存
* @author Duzhenduo
* @date 2019年4月18日 上午10:37:21 
*  
*/
public class REP_20000 extends REP_BASE {

	@FixedField(order = 5, len = 15, scale = 0, desc = "原交易金额")
	private Double otxnAmt;

	@FixedField(order = 6, len = 15, scale = 0, desc = "开户行手续费")
    private Double fee;

	@FixedField(order = 7, len = 15, scale = 0, desc = "账户余额")
    private Double actBal;



	public Double getOtxnAmt() {
		return otxnAmt;
	}

	public void setOtxnAmt(Double otxnAmt) {
		this.otxnAmt = AmtBase.toPack(otxnAmt);
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = AmtBase.toPack(fee);
	}

	public Double getActBal() {
		return actBal;
	}

	public void setActBal(Double actBal) {
		this.actBal = AmtBase.toPack(actBal);
	}


}