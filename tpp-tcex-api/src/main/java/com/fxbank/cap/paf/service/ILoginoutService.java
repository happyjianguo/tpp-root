package com.fxbank.cap.paf.service;

import javax.xml.bind.JAXBException;

import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;

public interface ILoginoutService {
	
	/** 
	* @Title: login 
	* @Description: 登录公积金结算系统
	* @param @param reqData
	* @param @throws PafTradeExecuteException
	* @param @throws JAXBException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	CLI_REP_DATA login(CLI_REQ_BDC reqData) throws PafTradeExecuteException, JAXBException;
	
	/** 
	* @Title: logout 
	* @Description: 退出公积金结算系统
	* @param @param reqData
	* @param @throws PafTradeExecuteException
	* @param @throws JAXBException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	CLI_REP_DATA logout(CLI_REQ_BDC reqData) throws PafTradeExecuteException, JAXBException;
}
