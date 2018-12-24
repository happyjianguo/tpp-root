package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class PasswordModel extends ModelBase implements Serializable {

	private static final long serialVersionUID = -6232087490769944074L;

	public PasswordModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	private String password;//密码
	private String acctNo;// 账号
	private String sourceType;//渠道类型
	private String sPINKey;// 转PIN的源加密因子

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getsPINKey() {
		return sPINKey;
	}

	public void setsPINKey(String sPINKey) {
		this.sPINKey = sPINKey;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

}
