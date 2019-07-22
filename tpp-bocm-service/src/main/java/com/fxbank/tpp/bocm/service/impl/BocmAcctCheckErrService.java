package com.fxbank.tpp.bocm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmChkErr;
import com.fxbank.tpp.bocm.mapper.BocmChkErrMapper;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;

/** 
* @ClassName: BocmAcctCheckErrService 
* @Description: 对账错误日志处理实现
* @author YePuLiang
* @date 2019年5月7日 上午9:34:40 
*  
*/
@Service(version = "1.0.0")
public class BocmAcctCheckErrService implements IBocmAcctCheckErrService{
	
	
	@Resource
	private BocmChkErrMapper mapper;

	@Override
	public List<BocmAcctCheckErrModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno,String begDate,String endDate) throws SysTradeExecuteException {
		List<BocmChkErr> list = mapper.selectByDate(begDate,endDate);
		List<BocmAcctCheckErrModel> modelList = new ArrayList<>();
		for(BocmChkErr ace : list) {
			BocmAcctCheckErrModel model = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, sysTraceno);
			model.setPlatDate(ace.getPlatDate());
			model.setPlatTrace(ace.getPlatTrace());
			model.setPreHostState(ace.getPreHostState());
			model.setReHostState(ace.getReHostState());
			model.setDcFlag(ace.getDcFlag());
			model.setDirection(ace.getDirection());
			model.setTxAmt(ace.getTxAmt());
			model.setPayeeAcno(ace.getPayeeAcno());
			model.setPayeeName(ace.getPayeeName());
			model.setPayerAcno(ace.getPayerAcno());
			model.setPayerName(ace.getPayerName());
			model.setTxCode(ace.getTxCode());
			model.setTxSource(ace.getTxSource());
			model.setHostDate(ace.getHostDate());
			model.setHostTraceno(ace.getHostTraceno());
			model.setTxDate(ace.getTxDate());
			model.setSndBankno(ace.getSndBankno());
			model.setTxBranch(ace.getTxBranch());
			model.setTxTel(ace.getTxTel());
			model.setTxInd(ace.getTxInd());
			model.setProxyFee(ace.getProxyFee());
			model.setProxyFlag(ace.getProxyFlag());
			model.setPayerBank(ace.getPayerBank());
			model.setPayeeBank(ace.getPayeeBank());
			model.setHostState(ace.getHostState());
			model.setBocmState(ace.getBocmState());
			model.setCheckFlag(ace.getCheckFlag());
			model.setMsg(ace.getMsg());
			model.setBocmFlag(ace.getBocmFlag());
			model.setHostFlag(ace.getHostFlag());
			modelList.add(model);
		}
		return modelList;
	}

	@Override
	public void insert(BocmAcctCheckErrModel model) throws SysTradeExecuteException {
		BocmChkErr ace = new BocmChkErr();
		ace.setPlatDate(model.getPlatDate());
		ace.setPlatTrace(model.getPlatTrace());
		ace.setTxSource(model.getTxSource());
		ace.setTxCode(model.getTxCode());
		ace.setHostDate(model.getHostDate());
		ace.setHostTraceno(model.getHostTraceno());
		ace.setTxDate(model.getTxDate());
		ace.setSndBankno(model.getSndBankno());
		ace.setTxBranch(model.getTxBranch());
		ace.setTxTel(model.getTxTel());
		ace.setTxInd(model.getTxInd());
		ace.setProxyFee(model.getProxyFee());
		ace.setProxyFlag(model.getProxyFlag());
		ace.setPayerBank(model.getPayerBank());
		ace.setPayeeBank(model.getPayeeBank());
		ace.setHostState(model.getHostState());
		ace.setBocmState(model.getBocmState());
		ace.setPreHostState(model.getPreHostState());
		ace.setReHostState(model.getReHostState());
		ace.setDcFlag(model.getDcFlag());
		ace.setCheckFlag(model.getCheckFlag());
		ace.setDirection(model.getDirection());
		ace.setTxAmt(model.getTxAmt());
		ace.setPayeeAcno(model.getPayeeAcno());
		ace.setPayeeName(model.getPayeeName());
		ace.setPayerAcno(model.getPayerAcno());
		ace.setPayerName(model.getPayerName());
		ace.setMsg(model.getMsg());
		ace.setBocmFlag(model.getBocmFlag());
		ace.setHostFlag(model.getHostFlag());
		mapper.insertSelective(ace);
	}

	@Override
	public void delete(String date) throws SysTradeExecuteException {
		mapper.deleteByDate(date);

	}

}
