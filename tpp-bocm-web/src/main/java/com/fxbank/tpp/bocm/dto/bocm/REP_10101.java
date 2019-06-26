package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.tpp.bocm.model.AmtBase;

/**
 * @Descripttion: 交行来账余额查询
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:19:04
 */
public class REP_10101 extends REP_BASE {

	@FixedField(order = 5, len = 3, desc = "币种")
    private String ccyCod="CNY";

	@FixedField(order = 6, len = 30, desc = "账户名称")
    private String actNam;

	@FixedField(order = 7, len = 32, desc = "账号")
    private String actNo;

    @FixedField(order = 8, len = 15, scale = 0, desc = "账户余额")
    private Double actBal;

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getActNam() {
		return actNam;
	}

	public void setActNam(String actNam) {
		this.actNam = actNam;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public Double getActBal() {
		return actBal;
	}

	public void setActBal(Double actBal) {
		this.actBal = actBal;
	}


    
    


 

}