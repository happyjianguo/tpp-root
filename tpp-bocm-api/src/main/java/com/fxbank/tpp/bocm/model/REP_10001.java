package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_10001 
* @Description: 磁条卡通兑
* @author Duzhenduo
* @date 2019年4月16日 上午9:26:08 
*  
*/
public class REP_10001 extends REP_BASE {

	private static final long serialVersionUID = 927936689160685939L;



    @Deprecated
	public REP_10001() {
		super(null, 0, 0, 0);
	}

    public REP_10001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

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
		this.otxnAmt = otxnAmt;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getActBal() {
		return actBal;
	}

	public void setActBal(Double actBal) {
		this.actBal = actBal;
	}

	
}