package com.fxbank.tpp.beps.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.beps.pmts.REP_BASE;
import com.fxbank.tpp.beps.pmts.REQ_BASE;

/**
 * @Description: 发送请求报文至PMTS
 * @Author: 周勇沩
 * @Date: 2019-04-20 13:10:28
 */
public interface IForwardToPmtsService {


	/**
	 * @description: 发送往账后实时等待990
	 * @Date       : 2020/2/21 13:47
	 */
	public REP_BASE sendToPmts990Wait(REQ_BASE modelBase) throws SysTradeExecuteException;


	/**
	 * @description: 发送往账后实时等待990和对应回执报文
	 * @Date       : 2020/2/21 13:47
	 */
	public REP_BASE sendToPmtsRtnWait(REQ_BASE modelBase) throws SysTradeExecuteException;


	/**
	 * @description: 发送往账后不等待应答报文
	 * @Date       : 2020/2/21 13:48
	 */
	public void sendToPmtsNoWait(REQ_BASE modelBase) throws SysTradeExecuteException;

}
