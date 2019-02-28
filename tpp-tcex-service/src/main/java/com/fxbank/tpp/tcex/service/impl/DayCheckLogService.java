package com.fxbank.tpp.tcex.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.entity.TcexChkLog;
import com.fxbank.tpp.tcex.mapper.TcexChkLogMapper;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;
import com.fxbank.tpp.tcex.service.IDayCheckLogService;

@Service(validation = "true", version = "1.0.0")
public class DayCheckLogService implements IDayCheckLogService {
	
	@Resource
	private TcexChkLogMapper mapper;

	@Override
	public void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException {
		TcexChkLog tcexChkLog = new TcexChkLog();
		
		tcexChkLog.setAccountno(model.getAccountno());
		tcexChkLog.setCcy(model.getCcy());
		tcexChkLog.setHostDate(model.getHostDate());
		tcexChkLog.setHostTraceno(model.getHostTraceno());
		tcexChkLog.setPlatDate(model.getPlatDate());
		tcexChkLog.setPlatTrace(model.getPlatTrace());
		tcexChkLog.setReversal(model.getReversal());
		tcexChkLog.setSettleBranch(model.getSettleBranch());
		tcexChkLog.setTxAmt(model.getTxAmt());
		tcexChkLog.setTxStatus(model.getTxStatus());
		tcexChkLog.setSettleDate(model.getSettleDate());
		tcexChkLog.setDirection(model.getDirection());
		mapper.insertSelective(tcexChkLog);
	}

	@Override
	public List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate,String direction) throws SysTradeExecuteException {
		TcexChkLog tcexChkLog = new TcexChkLog();
		tcexChkLog.setPlatDate(platDate);
		tcexChkLog.setDirection(direction);
		
		List<TcexChkLog> tcexChkLogList = mapper.select(tcexChkLog);
		List<DayCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<DayCheckLogInitModel>();
		for(TcexChkLog tpp : tcexChkLogList) {
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

	@Override
	public void delete(String direction) throws SysTradeExecuteException {
		mapper.deleteAll(direction);
	}

}
