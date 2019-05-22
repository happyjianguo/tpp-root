package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @Descripttion: 交行来账余额查询
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:18:51
 */
public class REQ_10101 extends REQ_BASE {

	@FixedField(order = 8, len = 3, desc = "币种")
    private String ccyCod;

	@FixedField(order = 9, len = 1, desc = "账号类型")
    private String actTyp;

	@FixedField(order = 10, len = 32, desc = "账号")
    private String actNo;

	@FixedField(order = 11, len = 20, desc = "查询密码")
    private String pin;

    public REQ_10101() {
        super.txDesc = "账户余额查询";
        super.log = false;
	}

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