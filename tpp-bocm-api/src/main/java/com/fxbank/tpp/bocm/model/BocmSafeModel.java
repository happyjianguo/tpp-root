package com.fxbank.tpp.bocm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmSafeModel 
* @Description: 加密model
* @author YePuLiang
* @date 2019年5月23日 下午3:55:26 
*  
*/
public class BocmSafeModel extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = 908176548039925045L;

	public BocmSafeModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	//密码
	private String password;
	// 账号
	private String acctNo;
	//工作密钥密文
	private String keyValue;
	// 工作密钥校验值
	private String checkValue;
	//申请密钥类型
	private String keyModel;

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
