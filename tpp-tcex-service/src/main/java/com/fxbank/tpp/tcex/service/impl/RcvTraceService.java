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
import com.fxbank.tpp.tcex.mapper.TcexRcvLogMapper;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

@Service(validation = "true", version = "1.0.0")
public class RcvTraceService implements IRcvTraceService{
	
	@Resource
	private TcexRcvLogMapper tcexRcvLogMapper;

	@Override
	public void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(record.getSysDate());
		tcexRcvLog.setPlatTrace(record.getSysTraceno());
		tcexRcvLog.setPlatTime(record.getSysTime());
		tcexRcvLog.setSourceType(record.getSourceType());
		tcexRcvLog.setTxBranch(record.getTxBranch());
		tcexRcvLog.setTxInd(record.getTxInd());
		tcexRcvLog.setDcFlag(record.getDcFlag());
		tcexRcvLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		tcexRcvLog.setPayerAcno(record.getPayerAcno());
		tcexRcvLog.setPayerName(record.getPayerName());
		tcexRcvLog.setPayeeAcno(record.getPayeeAcno());
		tcexRcvLog.setPayeeName(record.getPayeeName());
		tcexRcvLog.setHostState(record.getHostState());
		tcexRcvLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tcexRcvLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tcexRcvLog.setTownTraceno(record.getTownTraceNo());
		}
		if(null != record.getTownBranch()) {
			tcexRcvLog.setTownBranch(record.getTownBranch());
		}
		tcexRcvLog.setTxTel(record.getTxTel());
		tcexRcvLog.setChkTel(record.getChkTel());
		tcexRcvLog.setAuthTel(record.getAuthTel());
		tcexRcvLog.setInfo(record.getInfo());
		tcexRcvLog.setTownFlag(record.getTownFlag());
		tcexRcvLog.setPrint(record.getPrint());
		tcexRcvLog.setCheckFlag(record.getCheckFlag());
		
		tcexRcvLogMapper.insertSelective(tcexRcvLog);
	}
	
	@Override
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException{
		List<TcexRcvLog> tppRcvTraceList = tcexRcvLogMapper.selectRcvTrace(begDate, endDate, minAmt, maxAmt, brnoFlag);
		List<RcvTraceQueryModel> rcvTraceInitModelList = new ArrayList<>();
		for(TcexRcvLog tpp : tppRcvTraceList) {
			RcvTraceQueryModel model = new RcvTraceQueryModel(myLog,tpp.getPlatDate(),tpp.getPlatTime(),tpp.getPlatTrace());
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
			
			rcvTraceInitModelList.add(model);
		}
		return rcvTraceInitModelList;
	}

	/** 
	* @Title: rcvTraceUpd 
	* @Description:更新核心记账状态 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void rcvTraceUpd(@Valid RcvTraceUpdModel record) throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(record.getSysDate());
		tcexRcvLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
		    tcexRcvLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			tcexRcvLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			tcexRcvLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getTownDate()) {
			tcexRcvLog.setTownDate(record.getTownDate());
		}
		if(null != record.getTownBranch()) {
			tcexRcvLog.setTownBranch(record.getTownBranch());
		}
		if(null != record.getTownState()) {
			tcexRcvLog.setTownState(record.getTownState());
		}
		if(null != record.getTownTraceno()) {
			tcexRcvLog.setTownTraceno(record.getTownTraceno());
		}
		if(null != record.getRetCode()) {
			tcexRcvLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			tcexRcvLog.setRetMsg(record.getRetMsg());
		}
		if(null != record.getHostBranch()) {
			tcexRcvLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getCheckFlag()) {
			tcexRcvLog.setCheckFlag(record.getCheckFlag());
		}
		tcexRcvLogMapper.updateByPrimaryKeySelective(tcexRcvLog);
		
	}
	
	@Override
	public RcvTraceQueryModel getConfirmTrace(MyLog myLog,String townDate,String townTraceno)throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setTownDate(Integer.parseInt(townDate));
		tcexRcvLog.setTownTraceno(townTraceno);
		TcexRcvLog data = tcexRcvLogMapper.selectOne(tcexRcvLog);
		RcvTraceQueryModel model = new RcvTraceQueryModel(myLog, tcexRcvLog.getPlatDate(), tcexRcvLog.getPlatTime(), tcexRcvLog.getPlatTrace());
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
		model.setTxAmt(data.getTxAmt());
		model.setTownFlag(data.getTownFlag());
		return model;
	}

	@Override
	public List<RcvTraceQueryModel> getCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(Integer.parseInt(date));
		tcexRcvLog.setCheckFlag("1");
		List<TcexRcvLog> dataList = tcexRcvLogMapper.select(tcexRcvLog);
		List<RcvTraceQueryModel> modelList = new ArrayList<>();
		for(TcexRcvLog data : dataList) {
			RcvTraceQueryModel model = new RcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
	public void replenishRcvTrace(RcvTraceRepModel record) throws SysTradeExecuteException{
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(record.getSysDate());
		tcexRcvLog.setPlatTrace(record.getSysTraceno());
		tcexRcvLog.setPlatTime(record.getSysTime());
		tcexRcvLog.setSourceType(record.getSourceType());
		tcexRcvLog.setTxBranch(record.getTxBranch());
		tcexRcvLog.setTxInd(record.getTxInd());
		tcexRcvLog.setDcFlag(record.getDcFlag());
		tcexRcvLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		if("1".equals(record.getTxInd())) {
		tcexRcvLog.setPayerAcno(record.getPayerAcno());
		tcexRcvLog.setPayerName(record.getPayerName());
		}
		tcexRcvLog.setPayeeAcno(record.getPayeeAcno());
		tcexRcvLog.setPayeeName(record.getPayeeName());
		tcexRcvLog.setHostState(record.getHostState());
		tcexRcvLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tcexRcvLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tcexRcvLog.setTownTraceno(record.getTownTraceNo());
		}
		tcexRcvLog.setTxTel(record.getTxTel());
		tcexRcvLog.setChkTel(record.getChkTel());
		tcexRcvLog.setAuthTel(record.getAuthTel());
		tcexRcvLog.setInfo(record.getInfo());
		tcexRcvLog.setTownFlag(record.getTownFlag());
		tcexRcvLog.setCheckFlag(record.getCheckFlag());
		
		tcexRcvLogMapper.insertSelective(tcexRcvLog);
	}

	@Override
	public RcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(settleDate);
		tcexRcvLog.setPlatTrace(platTrace);
		TcexRcvLog data = tcexRcvLogMapper.selectOne(tcexRcvLog);
		RcvTraceQueryModel model = new RcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
	public List<RcvTraceQueryModel> getUploadCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		TcexRcvLog tcexRcvLog = new TcexRcvLog();
		tcexRcvLog.setPlatDate(Integer.parseInt(date));
		tcexRcvLog.setHostState("1");
		
		List<TcexRcvLog> dataList = tcexRcvLogMapper.select(tcexRcvLog);
		List<RcvTraceQueryModel> modelList = new ArrayList<>();
		for(TcexRcvLog data : dataList) {
			RcvTraceQueryModel model = new RcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			model.setTxAmt(data.getTxAmt());
			model.setTxInd(data.getTxInd());

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
	public String getRcvTotalNum(MyLog myLog, String date, String dcFlag) throws SysTradeExecuteException {
		String num = tcexRcvLogMapper.selectDtRcvTotalNum(date, dcFlag);
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
	public String getRcvTotalSum(MyLog myLog, String date, String dcFlag) throws SysTradeExecuteException {
		String sum = tcexRcvLogMapper.selectDtRcvTotalSum(date, dcFlag);
		return sum;
	}

	

}
