package com.fxbank.tpp.tcex.service;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;

public interface ISndTraceService {

	void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException;
	
	
}
