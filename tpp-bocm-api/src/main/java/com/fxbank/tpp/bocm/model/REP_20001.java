package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;




/** 
* @ClassName: REP_20001 
* @Description: IC卡通兑
* @author Duzhenduo
* @date 2019年4月16日 下午4:06:09 
*  
*/
public class REP_20001 extends REP_BASE {

	private static final long serialVersionUID = 7762922925444902496L;

    @Deprecated
	public REP_20001() {
		super(null, 0, 0, 0);
	}

    public REP_20001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

	@FixedField(order = 5, len = 15, scale = 0, desc = "原交易金额")
	private Double otxnAmt;

	@FixedField(order = 6, len = 15, scale = 0, desc = "开户行手续费")
    private Double fee;

	@FixedField(order = 7, len = 15, scale = 0, desc = "账户余额")
    private Double actBal;

	public Double getOtxnAmt() {
		otxnAmt = otxnAmt/100;
		return otxnAmt;
	}

	public void setOtxnAmt(Double otxnAmt) {
		otxnAmt = otxnAmt*100;
		this.otxnAmt = otxnAmt;
	}

	public Double getFee() {
		fee = fee/100;
		return fee;
	}

	public void setFee(Double fee) {
		fee = fee*100;
		this.fee = fee;
	}

	public Double getActBal() {
		actBal = actBal/100;
		return actBal;
	}

	public void setActBal(Double actBal) {
		actBal = actBal*100;
		this.actBal = actBal;
	}

	

}