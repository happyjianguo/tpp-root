package com.fxbank.tpp.tcex.service;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;

public interface IRcvTraceService {

	void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException;
	
	
}
