package com.fxbank.tpp.tcex.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.tcex.entity.TppRcvTraceLog;
import com.fxbank.tpp.tcex.mapper.TppRcvTraceLogMapper;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
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

}
