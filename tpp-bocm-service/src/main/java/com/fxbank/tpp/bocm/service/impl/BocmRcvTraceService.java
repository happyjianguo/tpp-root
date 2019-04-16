/**   
* @Title: BocmRcvTraceService.java 
* @Package com.fxbank.tpp.bocm.service.impl 
* @Description: 交行来账处理
* @author YePuLiang
* @date 2019年4月16日 上午8:59:46 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;
import com.fxbank.tpp.bocm.mapper.BocmRcvLogMapper;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;

/** 
* @ClassName: BocmRcvTraceService 
* @Description: 交行来账处理
* @author YePuLiang
* @date 2019年4月16日 上午8:59:46 
*  
*/
@Service(version = "1.0.0")
public class BocmRcvTraceService implements IBocmRcvTraceService {
	
	@Resource
	private BocmRcvLogMapper bocmRcvLogMapper;

	/** 
	* @Title: rcvTraceInit 
	* @Description: 来账登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void rcvTraceInit(@Valid BocmRcvTraceInitModel record) throws SysTradeExecuteException {
		BocmRcvLog entity = new BocmRcvLog();
		entity.setPlatDate(record.getSysDate());
		entity.setPlatTime(record.getSysTime());
		entity.setPlatTrace(record.getSysTraceno());
		entity.setCurTime(System.currentTimeMillis());
		entity.setSourceType(record.getSourceType());
		entity.setTxBranch(record.getTxBranch());
		entity.setTxInd(record.getTxInd());
		entity.setDcFlag(record.getDcFlag());
		BigDecimal txAmt = new BigDecimal(record.getTxAmt());
		entity.setTxAmt(txAmt);
		
		entity.setPayerAcno(record.getPayerAcno());
		entity.setPayerName(record.getPayerName());
		entity.setPayeeAcno(record.getPayeeAcno());
		entity.setPayeeName(record.getPayeeName());
		entity.setBocmBranch(record.getBocmBranch());
		entity.setHostState(record.getHostState());	
		entity.setBocmState(record.getBocmState());
		
		entity.setTxTel(record.getTxTel());
		entity.setChkTel(record.getChkTel());
		entity.setAuthTel(record.getAuthTel());
		entity.setPrint(record.getPrint());
		entity.setInfo(record.getInfo());
		
		bocmRcvLogMapper.insertSelective(entity);

	}

	/** 
	* @Title: rcvTraceUpd 
	* @Description: 来账更新 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void rcvTraceUpd(@Valid BocmRcvTraceUpdModel record) throws SysTradeExecuteException {
		// TODO Auto-generated method stub
		BocmRcvLog bocmRcvLog = new BocmRcvLog();
		bocmRcvLog.setPlatDate(record.getSysDate());
		bocmRcvLog.setPlatTrace(record.getSysTraceno());
		if(null != record.getHostState()) {
			bocmRcvLog.setHostState(record.getHostState());
		}
		if(null != record.getHostDate()) {
			bocmRcvLog.setHostDate(record.getHostDate());
		}
		if(null != record.getHostTraceno()) {
			bocmRcvLog.setHostTraceno(record.getHostTraceno());
		}
		if(null != record.getHostBranch()) {
			bocmRcvLog.setHostBranch(record.getHostBranch());
		}
		if(null != record.getBocmDate()) {
			bocmRcvLog.setBocmDate(record.getBocmDate());
		}
		if(null != record.getBocmTime()) {
			bocmRcvLog.setBocmTime(record.getBocmTime());
		}
		if(null != record.getBocmBranch()) {
			bocmRcvLog.setBocmBranch(record.getBocmBranch());
		}
		if(null != record.getBocmState()) {
			bocmRcvLog.setBocmState(record.getBocmState());
		}
		if(null != record.getBocmTraceno()) {
			bocmRcvLog.setBocmTraceno(record.getBocmTraceno());
		}
		if(null != record.getRetCode()) {
			bocmRcvLog.setRetCode(record.getRetCode());
		}
		if(null != record.getRetMsg()) {
			bocmRcvLog.setRetMsg(record.getRetMsg());
		}
		if(null != record.getCheckFlag()) {
			bocmRcvLog.setCheckFlag(record.getCheckFlag());
		}
		
		bocmRcvLogMapper.updateByPrimaryKeySelective(bocmRcvLog);
	}

}
