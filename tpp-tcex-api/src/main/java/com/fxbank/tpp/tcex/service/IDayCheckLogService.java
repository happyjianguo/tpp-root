package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;

public interface IDayCheckLogService {

	void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException; 
	
	List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate,String direction) throws SysTradeExecuteException;

}
