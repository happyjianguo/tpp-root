package com.fxbank.tpp.bocm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.bocm.entity.BocmChkStatus;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;
import com.fxbank.tpp.bocm.mapper.BocmChkStatusMapper;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;

/** 
* @ClassName: BocmChkStatusService 
* @Description: 对账状态处理实现
* @author YePuLiang
* @date 2019年7月2日 上午9:22:27 
*  
*/
@Service(version = "1.0.0")
public class BocmChkStatusService implements IBocmChkStatusService{

	@Resource
	private BocmChkStatusMapper mapper;
	
	@Override
	public void chkStatusInit(BocmChkStatusModel record) throws SysTradeExecuteException {
		BocmChkStatus entity = new BocmChkStatus();
		entity.setChkDate(record.getChkDate());
		entity.setHostStatus(0);
		entity.setBocmStatus(0);
		entity.setPlatStatus(0);
		entity.setBocmTxCnt(0);
		entity.setBocmTxAmt(new BigDecimal("0"));
		entity.setHostTxCnt(0);
		entity.setHostTxAmt(new BigDecimal("0"));
		mapper.insertSelective(entity);
	}	
	
	@Override
	public void chkStatusUpd(BocmChkStatusModel record) throws SysTradeExecuteException {
		BocmChkStatus entity = new BocmChkStatus();
		entity.setChkDate(record.getChkDate());
		if(null != record.getHostStatus()) {
			entity.setHostStatus(record.getHostStatus());
		}
		if(null != record.getBocmStatus()) {
			entity.setBocmStatus(record.getBocmStatus());
		}
		if(null != record.getPlatStatus()) {
			entity.setPlatStatus(record.getPlatStatus());
		}
		if(null != record.getBocmTxCnt()) {
			entity.setBocmTxCnt(record.getBocmTxCnt());
		}
		if(null != record.getBocmTxAmt()) {
			entity.setBocmTxAmt(record.getBocmTxAmt());
		}
		if(null != record.getHostTxCnt()) {
			entity.setHostTxCnt(record.getHostTxCnt());
		}
		if(null != record.getHostTxAmt()) {
			entity.setHostTxAmt(record.getHostTxAmt());
		}
		mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void chkHostStatusUpd(BocmChkStatusModel record) throws SysTradeExecuteException {
		BocmChkStatus entity = new BocmChkStatus();
		entity.setChkDate(record.getChkDate());
		entity.setHostStatus(record.getHostStatus());
		entity.setBocmStatus(record.getBocmStatus());
		entity.setPlatStatus(record.getPlatStatus());
		mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void chkBocmStatusUpd(BocmChkStatusModel record) throws SysTradeExecuteException {
		BocmChkStatus entity = new BocmChkStatus();
		entity.setChkDate(record.getChkDate());
		entity.setHostStatus(record.getHostStatus());
		entity.setBocmStatus(record.getBocmStatus());
		entity.setPlatStatus(record.getPlatStatus());
		mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public BocmChkStatusModel selectByDate(String date) throws SysTradeExecuteException {
		BocmChkStatus chk = new BocmChkStatus();
		chk.setChkDate(Integer.parseInt(date));
		BocmChkStatus entity = mapper.selectOne(chk);
		BocmChkStatusModel model = new BocmChkStatusModel();
		if(entity!=null){
			model.setChkDate(entity.getChkDate());
			model.setHostStatus(entity.getHostStatus());
			model.setBocmStatus(entity.getBocmStatus());
			model.setPlatStatus(entity.getPlatStatus());
			model.setBocmTxCnt(entity.getBocmTxCnt());
			model.setBocmTxAmt(entity.getBocmTxAmt());
			model.setHostTxCnt(entity.getHostTxCnt());
			model.setHostTxAmt(entity.getHostTxAmt());
			return model;
		}else{
			return null;
		}

	}

}
