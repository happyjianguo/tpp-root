package com.fxbank.tpp.mivs.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.mivs.model.REP_BASE;
import com.fxbank.tpp.mivs.model.REQ_BASE;

/**
 * @Description: 交行客户端通讯服务
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:56:27
 */
public interface IForwardToMivsService {
	
	public <T extends REP_BASE> T sendToBocm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException; 

}
