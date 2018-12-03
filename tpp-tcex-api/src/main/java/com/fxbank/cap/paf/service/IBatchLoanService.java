package com.fxbank.cap.paf.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import com.fxbank.cap.paf.model.BatchLoanDetailModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.BatchLoanRcvFileModel;
import com.fxbank.cap.paf.model.BatchLoanUpdDetailModel;
import com.fxbank.cap.paf.model.BatchLoanUpdMasterModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

public interface IBatchLoanService {

	void rcvBatchLoanFile(@Valid BatchLoanRcvFileModel record) throws SysTradeExecuteException;
	
	void updateMaster(@Valid BatchLoanUpdMasterModel record) throws SysTradeExecuteException;
	
	void updateDetail(@Valid BatchLoanUpdDetailModel record) throws SysTradeExecuteException;
	
	List<String> querySeqNosByBatchNo(String batchNo) throws SysTradeExecuteException;
	
	List<String> querySeqNosByParams(String batchNo,String txStatus) throws SysTradeExecuteException;
	
	List<BatchLoanMasterModel> queryMasterByTxStatus(MyLog myLog,String txStatus) throws SysTradeExecuteException;
    
	void addBatchDetail(MyLog myLog,Logger logger,@Valid BatchLoanMasterModel record) throws SysTradeExecuteException ;
	
	BatchLoanDetailModel queryDetailByPk(@Valid BatchLoanDetailModel record)throws SysTradeExecuteException ;
	
	BatchLoanMasterModel queryMasterByPk(MyLog myLog,@Valid BatchLoanMasterModel record)throws SysTradeExecuteException ;
    
	BatchLoanMasterModel queryDetailsAmtSum(MyLog myLog,String batchNo)throws SysTradeExecuteException ;
}
