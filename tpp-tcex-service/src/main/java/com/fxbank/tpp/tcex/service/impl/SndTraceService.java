package com.fxbank.tpp.tcex.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.entity.TcexRcvLog;
import com.fxbank.tpp.tcex.entity.TcexSndLog;
import com.fxbank.tpp.tcex.mapper.TcexSndLogMapper;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;
import com.fxbank.tpp.tcex.service.ISndTraceService;

@Service(validation = "true", version = "1.0.0")
public class SndTraceService implements ISndTraceService{
	
	@Resource
	private TcexSndLogMapper tcexSndLogMapper;

	@Override
	public void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException {
		TcexSndLog tcexSndLog = new TcexSndLog();
		tcexSndLog.setPlatDate(record.getSysDate());
		tcexSndLog.setPlatTrace(record.getSysTraceno());
		tcexSndLog.setPlatTime(record.getSysTime());
		tcexSndLog.setSourceType(record.getSourceType());
		tcexSndLog.setTxBranch(record.getTxBranch());
		tcexSndLog.setTxInd(record.getTxInd());
		tcexSndLog.setDcFlag(record.getDcFlag());
		tcexSndLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		tcexSndLog.setPayerAcno(record.getPayerAcno());
		tcexSndLog.setPayerName(record.getPayerName());
		tcexSndLog.setPayeeAcno(record.getPayeeAcno());
		tcexSndLog.setPayeeName(record.getPayeeName());
		tcexSndLog.setHostState(record.getHostState());
		tcexSndLog.setTownState(record.getTownState());
		tcexSndLog.setTxTel(record.getTxTel());
		tcexSndLog.setChkTel(record.getChkTel());
		tcexSndLog.setAuthTel(record.getAuthTel());
		tcexSndLog.setInfo(record.getInfo());
		tcexSndLog.setTownFlag(record.getTownFlag());
		tcexSndLog.setPrint(record.getPrint());
		tcexSndLog.setCheckFlag(record.getCheckFlag());
		
		tcexSndLogMapper.insertSelective(tcexSndLog);
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
		TcexSndLog tcexSndLog = new TcexSndLog();
		tcexSndLog.setPlatDate(record.getSysDate());
		tcexSndLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
		    tcexSndLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			tcexSndLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			tcexSndLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getHostBranch()) {
			tcexSndLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getTownDate()) {
			tcexSndLog.setTownDate(record.getTownDate());
		}
		if(null != record.getTownBranch()) {
			tcexSndLog.setTownBranch(record.getTownBranch());
		}
		if(null != record.getTownState()) {
			tcexSndLog.setTownState(record.getTownState());
		}
		if(null != record.getTownTraceno()) {
			tcexSndLog.setTownTraceno(record.getTownTraceno());
		}
		if(null != record.getRetCode()) {
			tcexSndLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			tcexSndLog.setRetMsg(record.getRetMsg());
		}
		if(null != record.getCheckFlag()) {
			tcexSndLog.setCheckFlag(record.getCheckFlag());
		}
		
		tcexSndLogMapper.updateByPrimaryKeySelective(tcexSndLog);
	}
		
	@Override
	public List<SndTraceQueryModel> getSndTrace(MyLog myLog, String begDate, String endDate, String minAmt,
			String maxAmt, String brnoFlag) throws SysTradeExecuteException {
		List<TcexSndLog> tppSndTraceList = tcexSndLogMapper.selectSndTrace(begDate, endDate, minAmt, maxAmt, brnoFlag);
		List<SndTraceQueryModel> sndTraceQueryModelList = new ArrayList<>();
		for(TcexSndLog tpp : tppSndTraceList) {
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
		TcexSndLog tcexSndLog = new TcexSndLog();
		tcexSndLog.setPlatDate(settleDate);
		tcexSndLog.setPlatTrace(platTrace);
		TcexSndLog data = tcexSndLogMapper.selectOne(tcexSndLog);
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
		TcexSndLog tcexSndLog = new TcexSndLog();
		tcexSndLog.setPlatDate(record.getSysDate());
		tcexSndLog.setPlatTrace(record.getSysTraceno());
		tcexSndLog.setPlatTime(record.getSysTime());
		tcexSndLog.setSourceType(record.getSourceType());
		tcexSndLog.setTxBranch(record.getTxBranch());
		tcexSndLog.setTxInd(record.getTxInd());
		tcexSndLog.setDcFlag(record.getDcFlag());
		tcexSndLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		if("1".equals(record.getTxInd())) {
		tcexSndLog.setPayerAcno(record.getPayerAcno());
		tcexSndLog.setPayerName(record.getPayerName());
		}
		tcexSndLog.setPayeeAcno(record.getPayeeAcno());
		tcexSndLog.setPayeeName(record.getPayeeName());
		tcexSndLog.setHostState(record.getHostState());
		tcexSndLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tcexSndLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tcexSndLog.setTownTraceno(record.getTownTraceNo());
		}
		tcexSndLog.setTxTel(record.getTxTel());
		tcexSndLog.setChkTel(record.getChkTel());
		tcexSndLog.setAuthTel(record.getAuthTel());
		tcexSndLog.setInfo(record.getInfo());
		tcexSndLog.setTownFlag(record.getTownFlag());
		tcexSndLog.setCheckFlag(record.getCheckFlag());
		
		tcexSndLogMapper.insertSelective(tcexSndLog);
	}

	@Override
	public List<SndTraceQueryModel> getCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		TcexSndLog tcexSndLog = new TcexSndLog();
		tcexSndLog.setPlatDate(Integer.parseInt(date));
		tcexSndLog.setCheckFlag("1");
		List<TcexSndLog> dataList = tcexSndLogMapper.select(tcexSndLog);
		List<SndTraceQueryModel> modelList = new ArrayList<>();
		for(TcexSndLog data : dataList) {
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
		List<TcexSndLog> dataList = tcexSndLogMapper.selectCheckedTrace(date);
		List<SndTraceQueryModel> modelList = new ArrayList<>();
		for(TcexSndLog data : dataList) {
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
	/** 
	* @Title: getRcvTotalNum 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getSndTotalNum(MyLog myLog, String date, String dcFlag) throws SysTradeExecuteException {
		String num = tcexSndLogMapper.selectDtSndTotalNum(date, dcFlag);
		return num;
	}

	/** 
	* @Title: getRcvTotalSum 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getSndTotalSum(MyLog myLog, String date, String dcFlag) throws SysTradeExecuteException {
		String sum = tcexSndLogMapper.selectDtSndTotalSum(date, dcFlag);
		return sum;
	}

	@Override
	public String getTraceNum(String date, String checkFlag) throws SysTradeExecuteException {
		String sum = tcexSndLogMapper.selectTraceNum(date, checkFlag);
		return sum;
	}
}
