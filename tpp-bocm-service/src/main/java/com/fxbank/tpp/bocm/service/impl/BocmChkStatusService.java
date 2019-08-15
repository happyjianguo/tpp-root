package com.fxbank.tpp.bocm.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.entity.BocmChkStatus;
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
		entity.setTxDate(record.getTxDate());
		String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		entity.setChkDate(Integer.valueOf(sDate.substring(0, 8)));
		entity.setChkTime(Integer.valueOf(sDate.substring(8))); 
		entity.setHostStatus(0);
		entity.setBocmStatus(0);
		entity.setPlatStatus(0);
		entity.setBocmTxCnt(0);
		entity.setBocmTxAmt(new BigDecimal("0"));
		entity.setHostTxCnt(0);
		entity.setHostTxAmt(new BigDecimal("0"));
		entity.setPlatTxCnt(0);
		entity.setPlatTxAmt(new BigDecimal("0"));
		entity.setTxBranch(record.getTxBranch());
		entity.setTxTel(record.getTxTel());
		mapper.insertSelective(entity);
	}	
	
	@Override
	public void chkStatusUpd(BocmChkStatusModel record) throws SysTradeExecuteException {
		BocmChkStatus entity = new BocmChkStatus();
		entity.setTxDate(record.getTxDate());
		if(null != record.getTxBranch()) {
			entity.setTxBranch(record.getTxBranch());
		}
		if(null != record.getTxTel()) {
			entity.setTxTel(record.getTxTel());
		}
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
		if(null != record.getPlatTxAmt()) {
			entity.setPlatTxAmt(record.getPlatTxAmt());
		}
		if(null != record.getPlatTxCnt()) {
			entity.setPlatTxCnt(record.getPlatTxCnt());
		}
		String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		entity.setChkDate(Integer.valueOf(sDate.substring(0, 8)));
		entity.setChkTime(Integer.valueOf(sDate.substring(8))); 
		mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public BocmChkStatusModel selectByDate(String date) throws SysTradeExecuteException {
		BocmChkStatus chk = new BocmChkStatus();
		chk.setTxDate(Integer.parseInt(date));
		BocmChkStatus entity = mapper.selectOne(chk);
		BocmChkStatusModel model = new BocmChkStatusModel();
		if(entity!=null){
			model.setTxDate(entity.getTxDate());
			model.setHostStatus(entity.getHostStatus());
			model.setHostTxCnt(entity.getHostTxCnt());
			model.setHostTxAmt(entity.getHostTxAmt());
			model.setBocmStatus(entity.getBocmStatus());
			model.setBocmTxCnt(entity.getBocmTxCnt());
			model.setBocmTxAmt(entity.getBocmTxAmt());
			model.setPlatStatus(entity.getPlatStatus());
			model.setPlatTxCnt(entity.getPlatTxCnt());
			model.setPlatTxAmt(entity.getPlatTxAmt());
			model.setTxBranch(entity.getTxBranch());
			model.setTxTel(entity.getTxTel());
			model.setChkDate(entity.getChkDate());
			model.setChkTime(entity.getChkTime());
			return model;
		}else{
			return null;
		}

	}
	
	@Override
	public List<BocmChkStatusModel> selectByDate(MyLog myLog,String begDate,String endDate,String state) throws SysTradeExecuteException{
		List<BocmChkStatus> list = new ArrayList<BocmChkStatus>();
		
		if(state.equals("1")){
			//state=1 成功
			list = mapper.selectByDateSuccess(begDate, endDate, state);
		}else if(state.equals("")){
			//state=""  全部查询
			list = mapper.selectByDateSuccess(begDate, endDate, "");
		}else{
			//state=2  失败
			list = mapper.selectByDateError(begDate, endDate, state);
		}
		List<BocmChkStatusModel> modelList = new ArrayList<BocmChkStatusModel>();
		for(BocmChkStatus entity:list){
			BocmChkStatusModel model = new BocmChkStatusModel();
			model.setTxDate(entity.getTxDate());
			model.setHostStatus(entity.getHostStatus());
			model.setHostTxCnt(entity.getHostTxCnt());
			model.setHostTxAmt(entity.getHostTxAmt());
			model.setBocmStatus(entity.getBocmStatus());
			model.setBocmTxCnt(entity.getBocmTxCnt());
			model.setBocmTxAmt(entity.getBocmTxAmt());
			model.setPlatStatus(entity.getPlatStatus());
			model.setPlatTxCnt(entity.getPlatTxCnt());
			model.setPlatTxAmt(entity.getPlatTxAmt());
			model.setTxBranch(entity.getTxBranch());
			model.setTxTel(entity.getTxTel());
			model.setChkDate(entity.getChkDate());
			model.setChkTime(entity.getChkTime());
			modelList.add(model);
		}
		return modelList;
	}

}
