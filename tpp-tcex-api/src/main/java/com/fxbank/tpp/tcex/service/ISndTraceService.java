package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;

public interface ISndTraceService {

	void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException;
	
	void sndTraceUpd(@Valid SndTraceUpdModel record) throws SysTradeExecuteException;
	
	public List<SndTraceQueryModel> getSndTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String txBrno) throws SysTradeExecuteException;

}
