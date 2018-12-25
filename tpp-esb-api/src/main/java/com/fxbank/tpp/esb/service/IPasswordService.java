package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.model.ses.PasswordModel;

public interface IPasswordService {

	public PasswordModel transPin(PasswordModel model) throws SysTradeExecuteException;
	
	public String encryptPwd(MyLog myLog,String pwd) throws SysTradeExecuteException;
	
//	public MACModel calcMAC(MACModel model) throws SysTradeExecuteException;
}
