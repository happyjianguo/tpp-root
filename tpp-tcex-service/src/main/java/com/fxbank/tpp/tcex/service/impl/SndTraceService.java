package com.fxbank.tpp.tcex.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.tcex.entity.TppSndTraceLog;
import com.fxbank.tpp.tcex.mapper.TppSndTraceLogMapper;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
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
		tppSndTraceLog.setTxTel(record.getTxTel());
		tppSndTraceLog.setChkTel(record.getChkTel());
		tppSndTraceLog.setAuthTel(record.getAuthTel());
		tppSndTraceLog.setInfo(record.getInfo());
		
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
		tppSndTraceLog.setHostState(record.getHostState());
		
		tppSndTraceLogMapper.updateByPrimaryKeySelective(tppSndTraceLog);
	}

}
