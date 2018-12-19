package com.fxbank.tpp.tcex.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.entity.TppDayCheckLog;
import com.fxbank.tpp.tcex.mapper.TppDayCheckLogMapper;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;
import com.fxbank.tpp.tcex.service.IDayCheckLogService;

@Service(validation = "true", version = "1.0.0")
public class DayCheckLogService implements IDayCheckLogService {
	
	@Resource
	private TppDayCheckLogMapper mapper;

	@Override
	public void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException {
		TppDayCheckLog tppDayCheckLog = new TppDayCheckLog();
		
		tppDayCheckLog.setAccountno(model.getAccountno());
		tppDayCheckLog.setCcy(model.getCcy());
		tppDayCheckLog.setHostDate(model.getHostDate());
		tppDayCheckLog.setHostTraceno(model.getHostTraceno());
		tppDayCheckLog.setPlatDate(model.getPlatDate());
		tppDayCheckLog.setPlatTrace(model.getPlatTrace());
		tppDayCheckLog.setReversal(model.getReversal());
		tppDayCheckLog.setSettleBranch(model.getSettleBranch());
		tppDayCheckLog.setTxAmt(model.getTxAmt());
		tppDayCheckLog.setTxStatus(model.getTxStatus());
		tppDayCheckLog.setSettleDate(model.getSettleDate());
		tppDayCheckLog.setDirection(model.getDirection());
		mapper.insertSelective(tppDayCheckLog);
	}

	@Override
	public List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate,String direction) throws SysTradeExecuteException {
		TppDayCheckLog tppDayCheckLog = new TppDayCheckLog();
		tppDayCheckLog.setPlatDate(platDate);
		tppDayCheckLog.setDirection(direction);
		
		List<TppDayCheckLog> tppDayCheckLogList = mapper.select(tppDayCheckLog);
		List<DayCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<DayCheckLogInitModel>();
		for(TppDayCheckLog tpp : tppDayCheckLogList) {
			DayCheckLogInitModel model = new DayCheckLogInitModel(myLog,platDate,sysTime,sysTraceno);
			model.setAccountno(tpp.getAccountno());
			model.setCcy(tpp.getCcy());
			model.setHostDate(tpp.getHostDate());
			model.setHostTraceno(tpp.getHostTraceno());
			model.setPlatDate(tpp.getPlatDate());
			model.setPlatTrace(tpp.getPlatTrace());
			model.setReversal(tpp.getReversal());
			model.setSettleBranch(tpp.getSettleBranch());
			model.setSettleDate(tpp.getSettleDate());
			model.setTxAmt(tpp.getTxAmt());
			model.setTxStatus(tpp.getTxStatus());
			dayCheckLogInitModelList.add(model);
		}
		
		return dayCheckLogInitModelList;
	}

}
