package com.fxbank.cap.paf.service;

import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.*;
import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface IAccountService {
	
	/** 
	* @Title: notify 
	* @Description: 账户变动通知 
	* @param @param reqData
	* @param @throws PafTradeExecuteException
	* @param @throws JAXBException    设定文件 
	* @return CLI_REP_DATA    返回类型
	* @throws 
	*/
	CLI_REP_DATA notify(CLI_REQ_BDC reqData,String txCode) throws PafTradeExecuteException, JAXBException;

}
