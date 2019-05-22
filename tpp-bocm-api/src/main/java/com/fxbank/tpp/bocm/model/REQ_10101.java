package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @Description: 交行余额查询请求
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:20:40
 */
public class REQ_10101 extends REQ_BASE {
	
    @Deprecated
	public REQ_10101() {
		super(null, 0, 0, 0);
	}

    public REQ_10101(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.setTtxnCd("10101");
    }

    private static final long serialVersionUID = 4718668418571598941L;

	@FixedField(order = 8, len = 3, desc = "币种")
    private String ccyCod;

	@FixedField(order = 9, len = 1, desc = "账号类型")
    private String actTyp;

	@FixedField(order = 10, len = 32, desc = "账号")
    private String actNo;

	@FixedField(order = 11, len = 20, desc = "查询密码")
    private String pin;

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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}