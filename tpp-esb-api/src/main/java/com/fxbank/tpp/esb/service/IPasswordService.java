package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.model.ses.PasswordModel;

public interface IPasswordService {

	public PasswordModel transPin(PasswordModel model) throws SysTradeExecuteException;
	
	public PasswordModel encryptPwd(PasswordModel model) throws SysTradeExecuteException;
	
	public PasswordModel genKey(PasswordModel model) throws SysTradeExecuteException;
	
	public void verifyTownMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;

	public void verifyCityMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;
	
	public String calcTOWN(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
	
	public String calcCITY(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
}
