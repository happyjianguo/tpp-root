package com.fxbank.tpp.tcex.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.entity.TppRcvTraceLog;
import com.fxbank.tpp.tcex.entity.TppSndTraceLog;
import com.fxbank.tpp.tcex.mapper.TppSndTraceLogMapper;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;

@Service(validation = "true", version = "1.0.0")
public class SndTraceService implements ISndTraceService{
	
	@Resource
	private TppSndTraceLogMapper tppSndTraceLogMapper;

	@Override
	public void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(record.getSysDate());
		tppSndTraceLog.setPlatTrace(record.getSysTraceno());
		tppSndTraceLog.setPlatTime(record.getSysTime());
		tppSndTraceLog.setSourceType(record.getSourceType());
		tppSndTraceLog.setTxBranch(record.getTxBranch());
		tppSndTraceLog.setTxInd(record.getTxInd());
		tppSndTraceLog.setDcFlag(record.getDcFlag());
		tppSndTraceLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		if("1".equals(record.getTxInd())) {
		tppSndTraceLog.setPayerAcno(record.getPayerAcno());
		tppSndTraceLog.setPayerName(record.getPayerName());
		}
		tppSndTraceLog.setPayeeAcno(record.getPayeeAcno());
		tppSndTraceLog.setPayeeName(record.getPayeeName());
		tppSndTraceLog.setHostState(record.getHostState());
		tppSndTraceLog.setTownState(record.getTownState());
		tppSndTraceLog.setTxTel(record.getTxTel());
		tppSndTraceLog.setChkTel(record.getChkTel());
		tppSndTraceLog.setAuthTel(record.getAuthTel());
		tppSndTraceLog.setInfo(record.getInfo());
		tppSndTraceLog.setTownFlag(record.getTownFlag());
		
		tppSndTraceLogMapper.insertSelective(tppSndTraceLog);
	}

	/** 
	* @Title: sndTraceUpd 
	* @Description: 更新核心记账状态
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void sndTraceUpd(@Valid SndTraceUpdModel record) throws SysTradeExecuteException {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(record.getSysDate());
		tppSndTraceLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
		    tppSndTraceLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			tppSndTraceLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			tppSndTraceLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getHostBranch()) {
			tppSndTraceLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getTownDate()) {
			tppSndTraceLog.setTownDate(record.getTownDate());
		}
		if(null != record.getTownBranch()) {
			tppSndTraceLog.setTownBranch(record.getTownBranch());
		}
		if(null != record.getTownState()) {
			tppSndTraceLog.setTownState(record.getTownState());
		}
		if(null != record.getTownTraceno()) {
			tppSndTraceLog.setTownTraceno(record.getTownTraceno());
		}
		if(null != record.getRetCode()) {
			tppSndTraceLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			tppSndTraceLog.setRetMsg(record.getRetMsg());
		}
		
		tppSndTraceLogMapper.updateByPrimaryKeySelective(tppSndTraceLog);
	}
		
	@Override
	public List<SndTraceQueryModel> getSndTrace(MyLog myLog, String begDate, String endDate, String minAmt,
			String maxAmt, String brnoFlag) throws SysTradeExecuteException {
		List<TppSndTraceLog> tppSndTraceList = tppSndTraceLogMapper.selectSndTrace(begDate, endDate, minAmt, maxAmt, brnoFlag);
		List<SndTraceQueryModel> sndTraceQueryModelList = new ArrayList<>();
		for(TppSndTraceLog tpp : tppSndTraceList) {
			SndTraceQueryModel model = new SndTraceQueryModel(myLog,tpp.getPlatDate(),tpp.getPlatTime(),tpp.getPlatTrace());
			model.setAuthTel(tpp.getAuthTel());
			model.setChkTel(tpp.getCheckFlag());
			model.setDcFlag(tpp.getDcFlag());
			model.setHostState(tpp.getHostState());
			model.setInfo(tpp.getInfo());
			model.setPayeeAcno(tpp.getPayeeAcno());
			model.setPayeeName(tpp.getPayeeName());
			model.setPayerAcno(tpp.getPayerAcno());
			model.setPayerName(tpp.getPayerName());
			model.setPrint(tpp.getPrint());
			model.setSourceType(tpp.getSourceType());
			model.setTownBranch(tpp.getTownBranch());
			model.setTxAmt(tpp.getTxAmt());
			model.setTxBranch(tpp.getTxBranch());
			model.setTxInd(tpp.getTxInd());
			model.setTxTel(tpp.getTxTel());
			model.setCheckFlag(tpp.getCheckFlag());
			model.setHostDate(tpp.getHostDate());
			model.setHostTraceno(tpp.getHostTraceno());
			model.setPlatDate(tpp.getPlatDate());
			model.setPlatTime(tpp.getPlatTime());
			model.setPlatTrace(tpp.getPlatTrace());
			model.setTownDate(tpp.getTownDate());
			model.setTownState(tpp.getTownState());
			model.setTownTraceno(tpp.getTownTraceno());
			
			sndTraceQueryModelList.add(model);
		}
		return sndTraceQueryModelList;
	}

	@Override
	public SndTraceQueryModel getSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(settleDate);
		tppSndTraceLog.setPlatTrace(platTrace);
		TppSndTraceLog data = tppSndTraceLogMapper.selectOne(tppSndTraceLog);
		SndTraceQueryModel model = new SndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
		if(data == null )model = null;
		else {
			model.setAuthTel(data.getAuthTel());
			model.setCheckFlag(data.getCheckFlag());
			model.setChkTel(data.getChkTel());
			model.setDcFlag(data.getDcFlag());
			model.setHostDate(data.getHostDate());
			model.setHostState(data.getHostState());
			model.setHostTraceno(data.getHostTraceno());
			model.setInfo(data.getInfo());
			model.setPayeeAcno(data.getPayeeAcno());
			model.setPayeeName(data.getPayeeName());
			model.setPayerAcno(data.getPayerAcno());
			model.setPayerName(data.getPayerName());
			model.setPlatDate(data.getPlatDate());
			model.setPlatTime(data.getPlatTime());
			model.setPlatTrace(data.getPlatTrace());
			model.setSourceType(data.getSourceType());
			model.setTownBranch(data.getTownBranch());
			model.setTownDate(data.getTownDate());
			model.setTownState(data.getTownState());
			model.setTownTraceno(model.getTownTraceno());
		}
		return model;
	}

	@Override
	public void replenishSndTrace(SndTraceRepModel record) {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(record.getSysDate());
		tppSndTraceLog.setPlatTrace(record.getSysTraceno());
		tppSndTraceLog.setPlatTime(record.getSysTime());
		tppSndTraceLog.setSourceType(record.getSourceType());
		tppSndTraceLog.setTxBranch(record.getTxBranch());
		tppSndTraceLog.setTxInd(record.getTxInd());
		tppSndTraceLog.setDcFlag(record.getDcFlag());
		tppSndTraceLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		if("1".equals(record.getTxInd())) {
		tppSndTraceLog.setPayerAcno(record.getPayerAcno());
		tppSndTraceLog.setPayerName(record.getPayerName());
		}
		tppSndTraceLog.setPayeeAcno(record.getPayeeAcno());
		tppSndTraceLog.setPayeeName(record.getPayeeName());
		tppSndTraceLog.setHostState(record.getHostState());
		tppSndTraceLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tppSndTraceLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tppSndTraceLog.setTownTraceno(record.getTownTraceNo());
		}
		tppSndTraceLog.setTxTel(record.getTxTel());
		tppSndTraceLog.setChkTel(record.getChkTel());
		tppSndTraceLog.setAuthTel(record.getAuthTel());
		tppSndTraceLog.setInfo(record.getInfo());
		tppSndTraceLog.setTownFlag(record.getTownFlag());
		
		tppSndTraceLogMapper.insertSelective(tppSndTraceLog);
	}

	@Override
	public List<SndTraceQueryModel> getCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(Integer.parseInt(date));
		tppSndTraceLog.setCheckFlag("1");
		List<TppSndTraceLog> dataList = tppSndTraceLogMapper.select(tppSndTraceLog);
		List<SndTraceQueryModel> modelList = new ArrayList<>();
		for(TppSndTraceLog data : dataList) {
			SndTraceQueryModel model = new SndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
			model.setAuthTel(data.getAuthTel());
			model.setCheckFlag(data.getCheckFlag());
			model.setChkTel(data.getChkTel());
			model.setDcFlag(data.getDcFlag());
			model.setHostDate(data.getHostDate());
			model.setHostState(data.getHostState());
			model.setHostTraceno(data.getHostTraceno());
			model.setInfo(data.getInfo());
			model.setPayeeAcno(data.getPayeeAcno());
			model.setPayeeName(data.getPayeeName());
			model.setPayerAcno(data.getPayerAcno());
			model.setPayerName(data.getPayerName());
			model.setPlatDate(data.getPlatDate());
			model.setPlatTime(data.getPlatTime());
			model.setPlatTrace(data.getPlatTrace());
			model.setSourceType(data.getSourceType());
			model.setTownBranch(data.getTownBranch());
			model.setTownDate(data.getTownDate());
			model.setTownState(data.getTownState());
			model.setTownTraceno(model.getTownTraceno());
			modelList.add(model);
		}
		
		return modelList;
	}

	@Override
	public List<SndTraceQueryModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		TppSndTraceLog tppSndTraceLog = new TppSndTraceLog();
		tppSndTraceLog.setPlatDate(Integer.parseInt(date));
		tppSndTraceLog.setHostState("1");
		
		List<TppSndTraceLog> dataList = tppSndTraceLogMapper.select(tppSndTraceLog);
		List<SndTraceQueryModel> modelList = new ArrayList<>();
		for(TppSndTraceLog data : dataList) {
			SndTraceQueryModel model = new SndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
			model.setAuthTel(data.getAuthTel());
			model.setCheckFlag(data.getCheckFlag());
			model.setChkTel(data.getChkTel());
			model.setDcFlag(data.getDcFlag());
			model.setHostDate(data.getHostDate());
			model.setHostState(data.getHostState());
			model.setHostTraceno(data.getHostTraceno());
			model.setInfo(data.getInfo());
			model.setPayeeAcno(data.getPayeeAcno());
			model.setPayeeName(data.getPayeeName());
			model.setPayerAcno(data.getPayerAcno());
			model.setPayerName(data.getPayerName());
			model.setPlatDate(data.getPlatDate());
			model.setPlatTime(data.getPlatTime());
			model.setPlatTrace(data.getPlatTrace());
			model.setSourceType(data.getSourceType());
			model.setTownBranch(data.getTownBranch());
			model.setTownDate(data.getTownDate());
			model.setTownState(data.getTownState());
			model.setTownTraceno(data.getTownTraceno());
			model.setTownFlag(data.getTownFlag());

			modelList.add(model);
		}
		
		return modelList;
	}

}
