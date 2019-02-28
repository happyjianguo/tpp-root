package com.fxbank.tpp.tcex.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.entity.TcexChkErr;
import com.fxbank.tpp.tcex.mapper.TcexChkErrMapper;
import com.fxbank.tpp.tcex.model.AcctCheckErrModel;
import com.fxbank.tpp.tcex.service.IAcctCheckErrService;

@Service(validation = "true", version = "1.0.0")
public class AcctCheckErrService implements IAcctCheckErrService {
	
	@Resource
	private TcexChkErrMapper mapper;

	@Override
	public List<AcctCheckErrModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno,String date) throws SysTradeExecuteException {
		List<TcexChkErr> list = mapper.selectByDate(date);
		List<AcctCheckErrModel> modelList = new ArrayList<>();
		for(TcexChkErr ace : list) {
			AcctCheckErrModel model = new AcctCheckErrModel(myLog, sysDate, sysTime, sysTraceno);
			model.setPlatDate(ace.getPlatDate());
			model.setPlatTrace(ace.getPlatTrace());
			model.setPreHostState(ace.getPreHostState());
			model.setReHostState(ace.getReHostState());
			model.setDcFlag(ace.getDcFlag());
			model.setCheckFlag(ace.getCheckFlag());
			model.setDirection(ace.getDirection());
			model.setTxAmt(ace.getTxAmt());
			model.setPayeeAcno(ace.getPayeeAcno());
			model.setPayeeName(ace.getPayeeName());
			model.setPayerAcno(ace.getPayerAcno());
			model.setPayerName(ace.getPayerName());
			model.setMsg(ace.getMsg());
			modelList.add(model);
		}
		return modelList;
	}

	@Override
	public void insert(AcctCheckErrModel model) throws SysTradeExecuteException {
		TcexChkErr ace = new TcexChkErr();
		ace.setPlatDate(model.getPlatDate());
		ace.setPlatTrace(model.getPlatTrace());
		ace.setPreHostState(model.getPreHostState());
		ace.setReHostState(model.getReHostState());
		ace.setDcFlag(model.getDcFlag());
		ace.setCheckFlag(model.getCheckFlag());
		ace.setDirection(model.getDirection());
		ace.setTxAmt(model.getTxAmt());
		ace.setPayeeAcno(model.getPayeeAcno());
		ace.setPayeeName(model.getPayeeName());
		ace.setPayerAcno(model.getPayerAcno());
		ace.setPayerName(model.getPayerName());
		ace.setMsg(model.getMsg());
		
		mapper.insertSelective(ace);
	}

	@Override
	public void delete(String date) throws SysTradeExecuteException {
		mapper.deleteByDate(date);

	}

}
