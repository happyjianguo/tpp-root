package com.fxbank.tpp.bocm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmChkLog;
import com.fxbank.tpp.bocm.mapper.BocmChkLogMapper;
import com.fxbank.tpp.bocm.model.BocmDayCheckLogInitModel;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;

/** 
* @ClassName: BocmDayCheckLogService 
* @Description: 对账数据接口实现
* @author YePuLiang
* @date 2019年5月7日 上午9:34:17 
*  
*/
@Service(version = "1.0.0")
public class BocmDayCheckLogService implements IBocmDayCheckLogService {
	
	@Resource
	private BocmChkLogMapper mapper;

	@Override
	public void dayCheckLogInit(@Valid BocmDayCheckLogInitModel model) throws SysTradeExecuteException {
		BocmChkLog tcexChkLog = new BocmChkLog();
		
		tcexChkLog.setAccountno(model.getAccountno());
		tcexChkLog.setCcy(model.getCcy());
		tcexChkLog.setHostDate(model.getHostDate());
		tcexChkLog.setHostTraceno(model.getHostTraceno());
		tcexChkLog.setTranType(model.getTranType());
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
	public List<BocmDayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate) throws SysTradeExecuteException {
		BocmChkLog tcexChkLog = new BocmChkLog();
		tcexChkLog.setPlatDate(platDate);
		
		List<BocmChkLog> tcexChkLogList = mapper.select(tcexChkLog);
		List<BocmDayCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<BocmDayCheckLogInitModel>();
		for(BocmChkLog tpp : tcexChkLogList) {
			BocmDayCheckLogInitModel model = new BocmDayCheckLogInitModel(myLog,platDate,sysTime,sysTraceno);
			model.setAccountno(tpp.getAccountno());
			model.setCcy(tpp.getCcy());
			model.setHostDate(tpp.getHostDate());
			model.setHostTraceno(tpp.getHostTraceno());
			model.setTranType(tpp.getTranType());
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
	public void delete() throws SysTradeExecuteException {
		mapper.deleteAll();
	}


}
