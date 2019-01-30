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
import com.fxbank.tpp.tcex.mapper.TppRcvTraceLogMapper;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;

@Service(validation = "true", version = "1.0.0")
public class RcvTraceService implements IRcvTraceService{
	
	@Resource
	private TppRcvTraceLogMapper tppRcvTraceLogMapper;

	@Override
	public void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException {
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(record.getSysDate());
		tppRcvTraceLog.setPlatTrace(record.getSysTraceno());
		tppRcvTraceLog.setPlatTime(record.getSysTime());
		tppRcvTraceLog.setSourceType(record.getSourceType());
		tppRcvTraceLog.setTxBranch(record.getTxBranch());
		tppRcvTraceLog.setTxInd(record.getTxInd());
		tppRcvTraceLog.setDcFlag(record.getDcFlag());
		tppRcvTraceLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		tppRcvTraceLog.setPayerAcno(record.getPayerAcno());
		tppRcvTraceLog.setPayerName(record.getPayerName());
		tppRcvTraceLog.setPayeeAcno(record.getPayeeAcno());
		tppRcvTraceLog.setPayeeName(record.getPayeeName());
		tppRcvTraceLog.setHostState(record.getHostState());
		tppRcvTraceLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tppRcvTraceLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tppRcvTraceLog.setTownTraceno(record.getTownTraceNo());
		}
		if(null != record.getTownBranch()) {
			tppRcvTraceLog.setTownBranch(record.getTownBranch());
		}
		tppRcvTraceLog.setTxTel(record.getTxTel());
		tppRcvTraceLog.setChkTel(record.getChkTel());
		tppRcvTraceLog.setAuthTel(record.getAuthTel());
		tppRcvTraceLog.setInfo(record.getInfo());
		tppRcvTraceLog.setTownFlag(record.getTownFlag());
		tppRcvTraceLog.setPrint(record.getPrint());
		tppRcvTraceLog.setCheckFlag(record.getCheckFlag());
		
		tppRcvTraceLogMapper.insertSelective(tppRcvTraceLog);
	}
	
	@Override
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException{
		List<TppRcvTraceLog> tppRcvTraceList = tppRcvTraceLogMapper.selectRcvTrace(begDate, endDate, minAmt, maxAmt, brnoFlag);
		List<RcvTraceQueryModel> rcvTraceInitModelList = new ArrayList<>();
		for(TppRcvTraceLog tpp : tppRcvTraceList) {
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
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(record.getSysDate());
		tppRcvTraceLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
		    tppRcvTraceLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			tppRcvTraceLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			tppRcvTraceLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getTownDate()) {
			tppRcvTraceLog.setTownDate(record.getTownDate());
		}
		if(null != record.getTownBranch()) {
			tppRcvTraceLog.setTownBranch(record.getTownBranch());
		}
		if(null != record.getTownState()) {
			tppRcvTraceLog.setTownState(record.getTownState());
		}
		if(null != record.getTownTraceno()) {
			tppRcvTraceLog.setTownTraceno(record.getTownTraceno());
		}
		if(null != record.getRetCode()) {
			tppRcvTraceLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			tppRcvTraceLog.setRetMsg(record.getRetMsg());
		}
		if(null != record.getHostBranch()) {
			tppRcvTraceLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getCheckFlag()) {
			tppRcvTraceLog.setCheckFlag(record.getCheckFlag());
		}
		tppRcvTraceLogMapper.updateByPrimaryKeySelective(tppRcvTraceLog);
		
	}
	
	@Override
	public RcvTraceQueryModel getConfirmTrace(MyLog myLog,String townDate,String townTraceno)throws SysTradeExecuteException {
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setTownDate(Integer.parseInt(townDate));
		tppRcvTraceLog.setTownTraceno(townTraceno);
		TppRcvTraceLog data = tppRcvTraceLogMapper.selectOne(tppRcvTraceLog);
		RcvTraceQueryModel model = new RcvTraceQueryModel(myLog, tppRcvTraceLog.getPlatDate(), tppRcvTraceLog.getPlatTime(), tppRcvTraceLog.getPlatTrace());
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
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(Integer.parseInt(date));
		tppRcvTraceLog.setCheckFlag("1");
		List<TppRcvTraceLog> dataList = tppRcvTraceLogMapper.select(tppRcvTraceLog);
		List<RcvTraceQueryModel> modelList = new ArrayList<>();
		for(TppRcvTraceLog data : dataList) {
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
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(record.getSysDate());
		tppRcvTraceLog.setPlatTrace(record.getSysTraceno());
		tppRcvTraceLog.setPlatTime(record.getSysTime());
		tppRcvTraceLog.setSourceType(record.getSourceType());
		tppRcvTraceLog.setTxBranch(record.getTxBranch());
		tppRcvTraceLog.setTxInd(record.getTxInd());
		tppRcvTraceLog.setDcFlag(record.getDcFlag());
		tppRcvTraceLog.setTxAmt(new BigDecimal("".equals(record.getTxAmt())?"0":record.getTxAmt()));
		if("1".equals(record.getTxInd())) {
		tppRcvTraceLog.setPayerAcno(record.getPayerAcno());
		tppRcvTraceLog.setPayerName(record.getPayerName());
		}
		tppRcvTraceLog.setPayeeAcno(record.getPayeeAcno());
		tppRcvTraceLog.setPayeeName(record.getPayeeName());
		tppRcvTraceLog.setHostState(record.getHostState());
		tppRcvTraceLog.setTownState(record.getTownState());
		if(null != record.getTownDate()) {
			tppRcvTraceLog.setTownDate(Integer.parseInt(record.getTownDate()));
		}
		if(null != record.getTownTraceNo()) {
			tppRcvTraceLog.setTownTraceno(record.getTownTraceNo());
		}
		tppRcvTraceLog.setTxTel(record.getTxTel());
		tppRcvTraceLog.setChkTel(record.getChkTel());
		tppRcvTraceLog.setAuthTel(record.getAuthTel());
		tppRcvTraceLog.setInfo(record.getInfo());
		tppRcvTraceLog.setTownFlag(record.getTownFlag());
		tppRcvTraceLog.setCheckFlag(record.getCheckFlag());
		
		tppRcvTraceLogMapper.insertSelective(tppRcvTraceLog);
	}

	@Override
	public RcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException {
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(settleDate);
		tppRcvTraceLog.setPlatTrace(platTrace);
		TppRcvTraceLog data = tppRcvTraceLogMapper.selectOne(tppRcvTraceLog);
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
		TppRcvTraceLog tppRcvTraceLog = new TppRcvTraceLog();
		tppRcvTraceLog.setPlatDate(Integer.parseInt(date));
		tppRcvTraceLog.setHostState("1");
		
		List<TppRcvTraceLog> dataList = tppRcvTraceLogMapper.select(tppRcvTraceLog);
		List<RcvTraceQueryModel> modelList = new ArrayList<>();
		for(TppRcvTraceLog data : dataList) {
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
		String num = tppRcvTraceLogMapper.selectDtRcvTotalNum(date, dcFlag);
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
		String sum = tppRcvTraceLogMapper.selectDtRcvTotalSum(date, dcFlag);
		return sum;
	}

	

}
