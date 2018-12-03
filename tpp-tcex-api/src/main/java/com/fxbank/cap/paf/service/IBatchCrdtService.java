package com.fxbank.cap.paf.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;

import com.fxbank.cap.paf.model.BatchCrdtDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchCrdtRcvFileModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdMasterModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

public interface IBatchCrdtService {

	/** 
	* @Title: CrdtInfoInit 
	* @Description: 公积金批量付款接收文件
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void rcvBatchCrdtFile(@Valid BatchCrdtRcvFileModel record) throws SysTradeExecuteException;
	
	void updateMaster(@Valid BatchCrdtUpdMasterModel record) throws SysTradeExecuteException;
	
	void updateDetail(@Valid BatchCrdtUpdDetailModel record) throws SysTradeExecuteException;
	
	List<String> querySeqNosByBatchNo(String batchNo) throws SysTradeExecuteException;
	
	List<String> querySeqNosByParams(String batchNo,String txStatus) throws SysTradeExecuteException;
	
	List<BatchCrdtMasterModel> queryMasterByTxStatus(MyLog myLog,String txStatus) throws SysTradeExecuteException;
    
	void addBatchDetail(MyLog myLog,Logger logger,@Valid BatchCrdtMasterModel record) throws SysTradeExecuteException ;
	
	BatchCrdtDetailModel queryDetailByPk(@Valid BatchCrdtDetailModel record)throws SysTradeExecuteException ;
	
	BatchCrdtMasterModel queryMasterByPk(MyLog myLog,@Valid BatchCrdtMasterModel record)throws SysTradeExecuteException ;
    
	BatchCrdtMasterModel queryDetailsAmtSum(MyLog myLog,String batchNo)throws SysTradeExecuteException ;
}
