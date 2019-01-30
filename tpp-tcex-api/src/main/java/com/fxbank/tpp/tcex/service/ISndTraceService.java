package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;

public interface ISndTraceService {

	void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException;
	
	void sndTraceUpd(@Valid SndTraceUpdModel record) throws SysTradeExecuteException;
	
	public List<SndTraceQueryModel> getSndTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException;

	SndTraceQueryModel getSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException;

	void replenishSndTrace(SndTraceRepModel record) throws SysTradeExecuteException;

	List<SndTraceQueryModel> getCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;

	List<SndTraceQueryModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;
	String getSndTotalNum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;
	String getSndTotalSum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;

}
