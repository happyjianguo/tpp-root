package com.fxbank.tpp.bocm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;
import com.fxbank.tpp.bocm.mapper.BocmRcvLogMapper;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceRepModel;
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
		entity.setTranType(record.getTranType());
		entity.setTxInd(record.getTxInd());
		entity.setTxCode(record.getTxCode());
		entity.setDcFlag(record.getDcFlag());
		
		
		if(record.getTxAmt()!=null){
			entity.setTxAmt(record.getTxAmt());
		}
		if(record.getActBal()!=null){
			entity.setActBal(record.getActBal());
		}
		entity.setTxDate(record.getTxDate());
		
		entity.setPayerAcno(record.getPayerAcno());
		entity.setPayerName(record.getPayerName());
		entity.setPayeeAcno(record.getPayeeAcno());
		entity.setPayeeName(record.getPayeeName());
		entity.setBocmBranch(record.getBocmBranch());
		
		entity.setHostState(record.getHostState());	
		entity.setHostDate(record.getHostDate());	
		entity.setHostTraceno(record.getHostTraceno());
		entity.setRetCode(record.getRetCode());
		entity.setRetMsg(record.getRetMsg());
		
		entity.setBocmState(record.getBocmState());
		entity.setBocmDate(record.getBocmDate());
		entity.setBocmTime(record.getBocmTime());
		entity.setBocmTraceno(record.getBocmTraceNo());
		
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
		if(null != record.getActBal()){
			bocmRcvLog.setActBal(record.getActBal());
		}	
		if(null != record.getSndBankno()) {
			bocmRcvLog.setSndBankno(record.getSndBankno());
		}	
		if(null != record.getRcvBankno()) {
			bocmRcvLog.setRcvBankno(record.getRcvBankno());
		}	
		if(null != record.getProxyFlag()) {
			bocmRcvLog.setProxyFlag(record.getProxyFlag());
		}
		if(null != record.getProxyFee()) {
			bocmRcvLog.setProxyFee(record.getProxyFee());
		}
		bocmRcvLogMapper.updateByPrimaryKeySelective(bocmRcvLog);
	}
	
	@Override
	public BocmRcvTraceQueryModel getConfirmTrace(MyLog myLog,int townDate, String townTraceno)throws SysTradeExecuteException {
		BocmRcvLog bocmRcvLog = new BocmRcvLog();
		bocmRcvLog.setBocmTraceno(townTraceno);
		BocmRcvLog data = bocmRcvLogMapper.selectOne(bocmRcvLog);
		if(data==null){
			return null;
		}
		BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog, bocmRcvLog.getPlatDate(), bocmRcvLog.getPlatTime(), bocmRcvLog.getPlatTrace());
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
		model.setTranType(data.getTranType());
		model.setActBal(data.getActBal());
		model.setFeeFlag(data.getFeeFlag());
		model.setFee(data.getFee());
		model.setRetCode(data.getRetCode());
		model.setRetMsg(data.getRetMsg());
		return model;
	}
	
	@Override
	public List<BocmRcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String payeeAcno,String hostTraceno,String platTrace)  throws SysTradeExecuteException {
		List<BocmRcvLog> tppRcvTraceList = bocmRcvLogMapper.selectRcvTrace(begDate, endDate, payeeAcno, hostTraceno, platTrace);
		List<BocmRcvTraceQueryModel> rcvTraceInitModelList = new ArrayList<>();
		for(BocmRcvLog tpp : tppRcvTraceList) {
			BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog,tpp.getPlatDate(),tpp.getPlatTime(),tpp.getPlatTrace());
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
			model.setTranType(tpp.getTranType());
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
			
			rcvTraceInitModelList.add(model);
		}
		return rcvTraceInitModelList;
	}

	@Override
	public List<BocmRcvTraceQueryModel> getCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		BocmRcvLog tcexRcvLog = new BocmRcvLog();
		tcexRcvLog.setTxDate(Integer.parseInt(date));
		tcexRcvLog.setCheckFlag("1");
		List<BocmRcvLog> dataList = bocmRcvLogMapper.select(tcexRcvLog);
		List<BocmRcvTraceQueryModel> modelList = new ArrayList<>();
		for(BocmRcvLog data : dataList) {
			BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			
			modelList.add(model);
		}
		
		return modelList;
	}

	@Override
	public void replenishRcvTrace(BocmRcvTraceRepModel record) throws SysTradeExecuteException{
		BocmRcvLog tcexRcvLog = new BocmRcvLog();
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
		tcexRcvLog.setBocmState(record.getBocmState());
		if(null != record.getBocmDate()) {
			tcexRcvLog.setBocmDate(Integer.parseInt(record.getBocmDate()));
		}
		if(null != record.getBocmTraceNo()) {
			tcexRcvLog.setBocmTraceno(record.getBocmTraceNo());
		}
		tcexRcvLog.setTxTel(record.getTxTel());
		tcexRcvLog.setChkTel(record.getChkTel());
		tcexRcvLog.setAuthTel(record.getAuthTel());
		tcexRcvLog.setInfo(record.getInfo());
		tcexRcvLog.setCheckFlag(record.getCheckFlag());
		
		bocmRcvLogMapper.insertSelective(tcexRcvLog);
	}

	@Override
	public BocmRcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException {
		BocmRcvLog tcexRcvLog = new BocmRcvLog();
		tcexRcvLog.setPlatDate(settleDate);
		tcexRcvLog.setPlatTrace(platTrace);
		BocmRcvLog data = bocmRcvLogMapper.selectOne(tcexRcvLog);
		BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
		}
		
		return model;
	}
	
	public BocmRcvTraceQueryModel getBocmRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			String bocmTrace)throws SysTradeExecuteException{
		BocmRcvLog tcexRcvLog = new BocmRcvLog();
		tcexRcvLog.setBocmTraceno(bocmTrace);
		BocmRcvLog data = bocmRcvLogMapper.selectOne(tcexRcvLog);
		BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
		if(data == null )model = null;
		else {
			model.setAuthTel(data.getAuthTel());
			model.setCheckFlag(data.getCheckFlag());
			model.setChkTel(data.getChkTel());
			model.setDcFlag(data.getDcFlag());
			model.setTxCode(data.getTxCode());
			model.setTxInd(data.getTxInd());
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
		}
		
		return model;
	}

	@Override
	public List<BocmRcvTraceQueryModel> getUploadCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		List<BocmRcvLog> dataList = bocmRcvLogMapper.selectCheckedTrace(date);
		List<BocmRcvTraceQueryModel> modelList = new ArrayList<>();
		for(BocmRcvLog data : dataList) {
			BocmRcvTraceQueryModel model = new BocmRcvTraceQueryModel(myLog, sysDate, sysTime, sysTraceno);
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
			model.setTranType(data.getTranType());
			model.setTxInd(data.getTxInd());
			model.setTxCode(data.getTxCode());
			
			model.setSndBankno(data.getSndBankno());
			model.setRcvBankno(data.getRcvBankno());
			
			model.setPayerActtp(data.getPayerActtp());
			model.setPayeeActtp(data.getPayeeActtp());
			
			modelList.add(model);
		}
		
		return modelList;
	}

	
	@Override
	public String getTraceNum(String date, String checkFlag) throws SysTradeExecuteException {
		System.out.println(checkFlag);
		String sum = bocmRcvLogMapper.selectTraceNum(date, checkFlag);
		return sum;
	}
	@Override
	public String getRcvTotalChkSum(MyLog myLog, String date) throws SysTradeExecuteException{
		String sum = bocmRcvLogMapper.selectChkRcvTotalSum(date);
		return sum;
	}

	


}
