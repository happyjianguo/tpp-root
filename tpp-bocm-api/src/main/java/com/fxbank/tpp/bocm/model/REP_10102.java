/**   
* @Title: REP_20102.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:47:29 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_10102 
* @Description:账户信息应答
* @author YePuLiang
* @date 2019年5月9日 上午10:47:29 
*  
*/
public class REP_10102 extends REP_BASE {
	
	private static final long serialVersionUID = -1624248808632530084L;

	@Deprecated
	public REP_10102() {
		super(null, 0, 0, 0);
	}

    public REP_10102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }
    
	@FixedField(order = 5, len = 3, desc = "币种")
	private String ccyCod;
	
	@FixedField(order = 6, len = 12, desc = "开户行行号")
	private String actBnk;
	
	@FixedField(order = 7, len = 1, desc = "账户类型")	
	private String actTyp;
	
	@FixedField(order = 8, len = 30, desc = "账户户名")
	private String actNam;
	
	@FixedField(order = 9, len = 32, desc = "账号")
	private String actNo;
	
	@FixedField(order = 10, len = 15, desc = "手续费")
	private Double fee;

	@FixedField(order = 11, len = 60, desc = "客户地址")
	private String actAdr;
	
	@FixedField(order = 12, len = 1, desc = "金额超限标志")
	private String amtLmt;

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getActBnk() {
		return actBnk;
	}

	public void setActBnk(String actBnk) {
		this.actBnk = actBnk;
	}

	public String getActTyp() {
		return actTyp;
	}

	public void setActTyp(String actTyp) {
		this.actTyp = actTyp;
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getActAdr() {
		return actAdr;
	}

	public void setActAdr(String actAdr) {
		this.actAdr = actAdr;
	}

	public String getAmtLmt() {
		return amtLmt;
	}

	public void setAmtLmt(String amtLmt) {
		this.amtLmt = amtLmt;
	}


	
	
	
}
