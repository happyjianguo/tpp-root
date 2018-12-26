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
	private String keyValue;//工作密钥密文
	private String checkValue;// 工作密钥校验值
	private String keyModel;//申请密钥类型

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

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getKeyModel() {
		return keyModel;
	}

	public void setKeyModel(String keyModel) {
		this.keyModel = keyModel;
	}


}
