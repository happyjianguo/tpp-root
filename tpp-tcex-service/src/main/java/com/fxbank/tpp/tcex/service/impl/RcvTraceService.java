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
		if("1".equals(record.getTxInd())) {
		tppRcvTraceLog.setPayerAcno(record.getPayerAcno());
		tppRcvTraceLog.setPayerName(record.getPayerName());
		}
		tppRcvTraceLog.setPayeeAcno(record.getPayeeAcno());
		tppRcvTraceLog.setPayeeName(record.getPayeeName());
		tppRcvTraceLog.setHostState(record.getHostState());
		tppRcvTraceLog.setTxTel(record.getTxTel());
		tppRcvTraceLog.setChkTel(record.getChkTel());
		tppRcvTraceLog.setAuthTel(record.getAuthTel());
		tppRcvTraceLog.setInfo(record.getInfo());
		
		tppRcvTraceLogMapper.insertSelective(tppRcvTraceLog);
	}
	
	@Override
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String txBrno,String depDraInd) throws SysTradeExecuteException{
		List<TppRcvTraceLog> tppRcvTraceList = tppRcvTraceLogMapper.selectRcvTrace(begDate, endDate, minAmt, maxAmt, txBrno,depDraInd);
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
		tppRcvTraceLog.setHostState(record.getHostState());
		tppRcvTraceLogMapper.updateByPrimaryKeySelective(tppRcvTraceLog);
		
	}

}
