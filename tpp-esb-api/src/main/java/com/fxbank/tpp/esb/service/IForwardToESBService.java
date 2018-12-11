package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;

public interface IForwardToESBService {
	
	public <T> T sendToESB(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException;
    
	public <T> T sendToTown(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException;
}
