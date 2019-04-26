package com.fxbank.tpp.bocm.service.impl;


import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmSndLog;
import com.fxbank.tpp.bocm.mapper.BocmSndLogMapper;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;

/** 
* @ClassName: SndTraceService.java
* @Description: 
* @author YePuLiang
* @date  2019年3月19日 上午10:58:27   
*/
@Service(version = "1.0.0")
public class BocmSndTraceService implements IBocmSndTraceService{
	
	@Resource
	private BocmSndLogMapper bocmSndLogMapper;

	@Override
	public void sndTraceInit(@Valid BocmSndTraceInitModel record) throws SysTradeExecuteException {
		// TODO Auto-generated method stub
		BocmSndLog entity = new BocmSndLog();
		entity.setPlatDate(record.getSysDate());
		entity.setPlatTime(record.getSysTime());
		entity.setPlatTrace(record.getSysTraceno());
		entity.setCurTime(System.currentTimeMillis());
		entity.setSourceType(record.getSourceType());
		entity.setTxBranch(record.getTxBranch());
		entity.setTxInd(record.getTxInd());
		entity.setTxCode(record.getTxCode());
		entity.setDcFlag(record.getDcFlag());
		BigDecimal txAmt = new BigDecimal(record.getTxAmt());
		entity.setTxAmt(txAmt);
		
		entity.setPayerAcno(record.getPayerAcno());
		entity.setPayerName(record.getPayerName());
		entity.setPayeeAcno(record.getPayeeAcno());
		entity.setPayeeName(record.getPayeeName());
		entity.setBocmBranch(record.getBocmBranch());
		entity.setHostState(record.getHostState());		
		entity.setHostDate(record.getHostDate());
		entity.setHostTraceno(record.getHostTraceno());
		entity.setBocmState(record.getBocmState());
		entity.setRetCode(record.getRetCode());
		entity.setRetMsg(record.getRetMsg());
		
		entity.setTxTel(record.getTxTel());
		entity.setChkTel(record.getChkTel());
		entity.setAuthTel(record.getAuthTel());
		entity.setPrint(record.getPrint());
		entity.setInfo(record.getInfo());
		
		bocmSndLogMapper.insertSelective(entity);
	}

	@Override
	public void sndTraceUpd(@Valid BocmSndTraceUpdModel record) throws SysTradeExecuteException {
		// TODO Auto-generated method stub
		BocmSndLog bocmSndLog = new BocmSndLog();
		bocmSndLog.setPlatDate(record.getSysDate());
		bocmSndLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
			bocmSndLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			bocmSndLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			bocmSndLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getHostBranch()) {
			bocmSndLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getBocmDate()) {
			bocmSndLog.setBocmDate(record.getBocmDate());
		}
		if(null != record.getBocmBranch()) {
			bocmSndLog.setBocmBranch(record.getBocmBranch());
		}
		if(null != record.getBocmState()) {
			bocmSndLog.setBocmState(record.getBocmState());
		}
		if(null != record.getBocmTraceno()) {
			bocmSndLog.setBocmTraceno(record.getBocmTraceno());
		}
		if(null != record.getRetCode()) {
			bocmSndLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			bocmSndLog.setRetMsg(record.getRetMsg());
		}
		if(null != record.getCheckFlag()) {
			bocmSndLog.setCheckFlag(record.getCheckFlag());
		}		
		bocmSndLogMapper.updateByPrimaryKeySelective(bocmSndLog);
	}
	
	@Override
	public BocmSndTraceQueryModel getSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) {
		BocmSndLog bocmSndLog = new BocmSndLog();
		bocmSndLog.setPlatDate(settleDate);
		bocmSndLog.setPlatTrace(platTrace);
		BocmSndLog data = bocmSndLogMapper.selectOne(bocmSndLog);
		BocmSndTraceQueryModel model = new BocmSndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			model.setBocmBranch(data.getBocmBranch());
			model.setBocmDate(data.getBocmDate());
			model.setBocmState(data.getBocmState());
			model.setBocmTraceno(model.getBocmTraceno());
		}
		return model;
	}
}
