package com.fxbank.tpp.mivs.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.mivs.model.MODEL_BASE;

/**
 * @Description: 发送请求报文至PMTS，没有异常抛出，代表发送成功，且收到成功990报文。返回发送报文。
 * @Author: 周勇沩
 * @Date: 2019-04-20 13:10:28
 */
public interface IForwardToPmtsService {
	
	public MODEL_BASE sendToPmts(MODEL_BASE modelBase) throws SysTradeExecuteException;
	
	public MODEL_BASE sendToPmtsNoWait(MODEL_BASE modelBase) throws SysTradeExecuteException;

}
