package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
public interface IRcvTraceService {

	void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException;
	
	void rcvTraceUpd(@Valid RcvTraceUpdModel record) throws SysTradeExecuteException;
	
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String txBrno,String depDraInd) throws SysTradeExecuteException;
}
