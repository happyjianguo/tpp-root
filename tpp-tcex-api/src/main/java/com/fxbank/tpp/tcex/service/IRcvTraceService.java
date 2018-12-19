package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
public interface IRcvTraceService {

	void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException;
	
	void rcvTraceUpd(@Valid RcvTraceUpdModel record) throws SysTradeExecuteException;
	
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException;
	
	public RcvTraceQueryModel getConfirmTrace(MyLog myLog,String brno,String townDate,String townTraceno,String dcflag)throws SysTradeExecuteException;

	public List<RcvTraceQueryModel> getCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno, String date)throws SysTradeExecuteException;

	void replenishRcvTrace(RcvTraceRepModel record)throws SysTradeExecuteException;

	RcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace)throws SysTradeExecuteException;

	List<RcvTraceQueryModel> getUploadCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date)throws SysTradeExecuteException;

}
