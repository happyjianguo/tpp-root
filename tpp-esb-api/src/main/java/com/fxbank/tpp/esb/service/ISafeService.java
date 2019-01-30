package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.model.tcex.SafeModel;

public interface ISafeService {

	public SafeModel transPin(SafeModel model) throws SysTradeExecuteException;
	
	public SafeModel encryptPwd(SafeModel model) throws SysTradeExecuteException;
	
	public SafeModel genKey(SafeModel model) throws SysTradeExecuteException;
	
	public void verifyTownMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;

	public void verifyCityMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;
	
	public String calcTOWN(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
	
	public String calcCITY(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
}
