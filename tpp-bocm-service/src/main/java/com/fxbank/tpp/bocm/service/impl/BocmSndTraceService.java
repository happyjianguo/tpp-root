package com.fxbank.tpp.bocm.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmSndLog;
import com.fxbank.tpp.bocm.mapper.BocmSndLogMapper;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceRepModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;

/** 
* @ClassName: SndTraceService.java
* @Description: 交行往账实现
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
		entity.setTranType(record.getTranType());
		entity.setTxInd(record.getTxInd());
		entity.setTxCode(record.getTxCode());
		entity.setDcFlag(record.getDcFlag());
		
		BigDecimal txAmt = new BigDecimal(record.getTxAmt());
		entity.setTxAmt(txAmt);
		entity.setTxDate(record.getTxDate());
		
		entity.setActBal(record.getActBal());
		
		entity.setPayerAcno(record.getPayerAcno());
		entity.setPayerName(record.getPayerName());
		entity.setPayeeAcno(record.getPayeeAcno());
		entity.setPayeeName(record.getPayeeName());
		
		
		entity.setHostState(record.getHostState());		
		entity.setHostDate(record.getHostDate());	
		entity.setHostTraceno(record.getHostTraceno());
		entity.setRetCode(record.getRetCode());
		entity.setRetMsg(record.getRetMsg());
		
		entity.setBocmBranch(record.getBocmBranch());
		entity.setBocmState(record.getBocmState());
		entity.setBocmDate(record.getBocmDate());
		entity.setBocmTime(record.getBocmTime());
		entity.setBocmTraceno(record.getBocmTraceno());
		entity.setBocmRepcd(record.getBocmRepcd());
		entity.setBocmRepmsg(record.getBocmRepmsg());
		entity.setRetCode(record.getRetCode());
		entity.setRetMsg(record.getRetMsg());
		
		entity.setTxTel(record.getTxTel());
		entity.setChkTel(record.getChkTel());
		entity.setAuthTel(record.getAuthTel());
		entity.setPrint(record.getPrint());
		entity.setInfo(record.getInfo());
		entity.setCheckFlag("1");
		
		entity.setFeeFlag(record.getFeeFlag());
		entity.setFee(record.getFee());
		entity.setSndBankno(record.getSndBankno());
		entity.setRcvBankno(record.getRcvBankno());
		entity.setPayerBank(record.getPayerBank());
		entity.setPayerActtp(record.getPayerActtp());
		entity.setPayeeBank(record.getPayeeBank());
		entity.setPayeeActtp(record.getPayeeActtp());
		
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
		if(null != record.getBocmTime()) {
			bocmSndLog.setBocmTime(record.getBocmTime());
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
		if(null != record.getBocmRepcd()) {
			bocmSndLog.setBocmRepcd(record.getBocmRepcd());
		}
		if(null != record.getBocmRepmsg()) {
			bocmSndLog.setBocmRepmsg(record.getBocmRepmsg());
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
		if(null != record.getSndBankno()) {
			bocmSndLog.setSndBankno(record.getSndBankno());
		}	
		if(null != record.getRcvBankno()) {
			bocmSndLog.setRcvBankno(record.getRcvBankno());
		}	
		if(null != record.getActBal()) {
			bocmSndLog.setActBal(record.getActBal());
		}
		if(null != record.getProxyFlag()) {
			bocmSndLog.setProxyFlag(record.getProxyFlag());
		}
		if(null != record.getProxyFee()) {
			bocmSndLog.setProxyFee(record.getProxyFee());
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
			model.setBocmTraceno(data.getBocmTraceno());

			model.setSndBankno(data.getSndBankno());
			model.setRcvBankno(data.getRcvBankno());
			
			model.setPayerActtp(data.getPayerActtp());
			model.setPayeeActtp(data.getPayeeActtp());
			
			model.setTxAmt(data.getTxAmt());
			model.setTxDate(data.getTxDate());
			model.setTranType(data.getTranType());
			model.setTxCode(data.getTxCode());
			model.setTxBranch(data.getTxBranch());
			model.setTxTel(data.getTxTel());
			model.setTxInd(data.getTxInd());
			
		}
		return model;
	}
	
	public BocmSndTraceQueryModel getBocmSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			 String bocmTraceno) throws SysTradeExecuteException{
		BocmSndLog bocmSndLog = new BocmSndLog();
		bocmSndLog.setBocmTraceno(bocmTraceno);
		BocmSndLog data = bocmSndLogMapper.selectOne(bocmSndLog);
		BocmSndTraceQueryModel model = new BocmSndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
		if(data == null )model = null;
		else {
			model.setAuthTel(data.getAuthTel());
			model.setCheckFlag(data.getCheckFlag());
			model.setChkTel(data.getChkTel());
			model.setTxCode(data.getTxCode());
			model.setTranType(data.getTranType());
			model.setTxInd(data.getTxInd());
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
			model.setBocmTraceno(data.getBocmTraceno());
			
			model.setSndBankno(data.getSndBankno());
			model.setRcvBankno(data.getRcvBankno());
			
			model.setPayerActtp(data.getPayerActtp());
			model.setPayeeActtp(data.getPayeeActtp());
			model.setTxAmt(data.getTxAmt());
			model.setTxDate(data.getTxDate());
		}
		return model;
	}
	
	@Override
	public List<BocmSndTraceQueryModel> getSndTrace(MyLog myLog,String begDate,String endDate,String begTrace,String endTrace,
			String txAmt,String hostStatus,String txBranch)  throws SysTradeExecuteException {
		List<BocmSndLog> tppSndTraceList = bocmSndLogMapper.selectSndTrace(begDate, endDate, begTrace, endTrace, txAmt,hostStatus,txBranch);
		List<BocmSndTraceQueryModel> sndTraceQueryModelList = new ArrayList<>();
		for(BocmSndLog tpp : tppSndTraceList) {
			BocmSndTraceQueryModel model = new BocmSndTraceQueryModel(myLog,tpp.getPlatDate(),tpp.getPlatTime(),tpp.getPlatTrace());
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
			model.setBocmBranch(tpp.getBocmBranch());
			model.setTxAmt(tpp.getTxAmt());
			model.setTxDate(tpp.getTxDate());
			model.setTxBranch(tpp.getTxBranch());
			model.setTxInd(tpp.getTxInd());
			model.setTxTel(tpp.getTxTel());
			model.setCheckFlag(tpp.getCheckFlag());
			model.setHostDate(tpp.getHostDate());
			model.setHostTraceno(tpp.getHostTraceno());
			model.setPlatDate(tpp.getPlatDate());
			model.setPlatTime(tpp.getPlatTime());
			model.setPlatTrace(tpp.getPlatTrace());
			model.setBocmDate(tpp.getBocmDate());
			model.setBocmState(tpp.getBocmState());
			model.setBocmTraceno(tpp.getBocmTraceno());

			model.setSndBankno(tpp.getSndBankno());
			model.setRcvBankno(tpp.getRcvBankno());
			
			model.setTranType(tpp.getTranType());
			
			model.setProxy_flag(tpp.getProxyFlag());
			model.setProxy_fee(tpp.getProxyFee());
								
			sndTraceQueryModelList.add(model);
		}
		return sndTraceQueryModelList;
	}

	@Override
	public void replenishSndTrace(BocmSndTraceRepModel record) {
		BocmSndLog tcexSndLog = new BocmSndLog();
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
		tcexSndLog.setBocmState(record.getBocmState());
		if(null != record.getBocmDate()) {
			tcexSndLog.setBocmDate(Integer.parseInt(record.getBocmDate()));
		}
		if(null != record.getBocmTraceNo()) {
			tcexSndLog.setBocmTraceno(record.getBocmTraceNo());
		}
		tcexSndLog.setTxTel(record.getTxTel());
		tcexSndLog.setChkTel(record.getChkTel());
		tcexSndLog.setAuthTel(record.getAuthTel());
		tcexSndLog.setInfo(record.getInfo());
		tcexSndLog.setCheckFlag(record.getCheckFlag());

		bocmSndLogMapper.insertSelective(tcexSndLog);
	}

	@Override
	public List<BocmSndTraceQueryModel> getCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		BocmSndLog tcexSndLog = new BocmSndLog();
		tcexSndLog.setTxDate(Integer.parseInt(date));
		tcexSndLog.setCheckFlag("1");
		List<BocmSndLog> dataList = bocmSndLogMapper.select(tcexSndLog);
		List<BocmSndTraceQueryModel> modelList = new ArrayList<>();
		for(BocmSndLog data : dataList) {
			BocmSndTraceQueryModel model = new BocmSndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			model.setBocmTraceno(data.getBocmTraceno());
			
			model.setSndBankno(data.getSndBankno());
			model.setRcvBankno(data.getRcvBankno());
			
			model.setPayerActtp(data.getPayerActtp());
			model.setPayeeActtp(data.getPayeeActtp());
			
			model.setTxAmt(data.getTxAmt());
			model.setTxDate(data.getTxDate());
			model.setTranType(data.getTranType());
			
			model.setProxy_flag(data.getProxyFlag());
			model.setProxy_fee(data.getProxyFee());
			
			model.setTxCode(data.getTxCode());
			model.setSourceType(data.getSourceType());
			model.setTxBranch(data.getTxBranch());
			model.setTxTel(data.getTxTel());
			model.setTxInd(data.getTxInd());
			model.setCheckFlag(data.getCheckFlag());
			model.setPayerBank(data.getPayerBank());
			model.setPayerName(data.getPayerName());
			model.setPayeeBank(data.getPayeeBank());
			model.setPayeeName(data.getPayeeName());
			
			
			modelList.add(model);
		}
		
		return modelList;
	}

	@Override
	public List<BocmSndTraceQueryModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		List<BocmSndLog> dataList = bocmSndLogMapper.selectCheckedTrace(date);
		List<BocmSndTraceQueryModel> modelList = new ArrayList<>();
		for(BocmSndLog data : dataList) {
			BocmSndTraceQueryModel model = new BocmSndTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			model.setBocmTraceno(data.getBocmTraceno());
			model.setTxAmt(data.getTxAmt());
			model.setTxDate(data.getTxDate());
            model.setTxInd(data.getTxInd());
			model.setTxCode(data.getTxCode());

			model.setSndBankno(data.getSndBankno());
			model.setRcvBankno(data.getRcvBankno());
			
			model.setPayerActtp(data.getPayerActtp());
			model.setPayeeActtp(data.getPayeeActtp());
			model.setTranType(data.getTranType());
			
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
		String num = bocmSndLogMapper.selectDtSndTotalNum(date, dcFlag);
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
		String sum = bocmSndLogMapper.selectDtSndTotalSum(date, dcFlag);
		return sum;
	}

	@Override
	public String getTraceNum(String date, String checkFlag) throws SysTradeExecuteException {
		String sum = bocmSndLogMapper.selectTraceNum(date, checkFlag);
		return sum;
	}
	
	@Override
	public String getSndTotalChkSum(MyLog myLog, String date) throws SysTradeExecuteException{
		String sum = bocmSndLogMapper.selectChkSndTotalSum(date);
		return sum;
	}
}
