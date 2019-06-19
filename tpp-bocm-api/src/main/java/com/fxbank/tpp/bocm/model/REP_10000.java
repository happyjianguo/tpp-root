package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;


/** 
* @ClassName: REP_10000 
* @Description: 磁条卡通存
* @author Duzhenduo
* @date 2019年4月16日 上午8:27:41 
*  
*/
public class REP_10000 extends REP_BASE {

	private static final long serialVersionUID = -5008748819109644373L;

    @Deprecated
	public REP_10000() {
		super(null, 0, 0, 0);
	}

    public REP_10000(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
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
		return fee/100;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getActBal() {
		return actBal/100;
	}

	public void setActBal(Double actBal) {
		this.actBal = actBal;
	}

}