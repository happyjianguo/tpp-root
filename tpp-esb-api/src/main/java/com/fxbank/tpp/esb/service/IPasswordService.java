package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.esb.model.ses.PasswordModel;

public interface IPasswordService {

	public PasswordModel transPin(PasswordModel model) throws SysTradeExecuteException;
	
	public PasswordModel encryptPwd(PasswordModel model) throws SysTradeExecuteException;
	
//	public MACModel calcMAC(MACModel model) throws SysTradeExecuteException;
}
