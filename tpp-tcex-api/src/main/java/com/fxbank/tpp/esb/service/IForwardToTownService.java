package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;

public interface IForwardToTownService {
	
	public <T> T sendToTown(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException;

}
