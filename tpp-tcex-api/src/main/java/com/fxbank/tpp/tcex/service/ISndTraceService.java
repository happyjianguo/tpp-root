package com.fxbank.tpp.tcex.service;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;

public interface ISndTraceService {

	void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException;
	
	void sndTraceUpd(@Valid SndTraceUpdModel record) throws SysTradeExecuteException;
	
}
