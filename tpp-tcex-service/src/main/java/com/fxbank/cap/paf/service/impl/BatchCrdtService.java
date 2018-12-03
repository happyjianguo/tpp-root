package com.fxbank.cap.paf.service.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;

import com.alibaba.dubbo.config.annotation.Service;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cap.paf.entity.PafBatchCrdtDetail;
import com.fxbank.cap.paf.entity.PafBatchCrdtMaster;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.mapper.PafBatchCrdtDetailMapper;
import com.fxbank.cap.paf.mapper.PafBatchCrdtMasterMapper;
import com.fxbank.cap.paf.model.BatchCrdtDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchCrdtRcvFileModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtUpdMasterModel;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class BatchCrdtService implements IBatchCrdtService{
	
	@Resource
	private PafBatchCrdtMasterMapper pbcmMapper;
	@Resource
	private PafBatchCrdtDetailMapper pbcdMapper;
	//最多十四位整数，2位小数，不为0
	private final static String REGEX_DECIMAL = "^(?!0$|0\\.00|0\\.0|0\\d+$)(?:0?|[1-9]\\d{0,13})(?:\\.\\d{1,2})?$";

	/** 
	* @Title: rcvBatchCrdtFile 
	* @Description: 接收批量付款文件 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void rcvBatchCrdtFile(@Valid BatchCrdtRcvFileModel record) throws SysTradeExecuteException {
		PafBatchCrdtMaster pbcm = new PafBatchCrdtMaster();
		pbcm.setTxBrno(record.getTx_brno());
		pbcm.setPlatDate(record.getSysDate());
		pbcm.setPlatTime(record.getSysTime());
		pbcm.setPlatTrace(record.getSysTraceno());
		pbcm.setUpBrno(record.getUp_brno());
		pbcm.setLogId(record.getMylog().getLogId());
		pbcm.setLogDev(record.getMylog().getLogDev());
		pbcm.setSndDate(record.getSnd_date()); //发送方日期
		pbcm.setSndTime(record.getSnd_time());//'发送方时间' ,
		pbcm.setSndSeqno(record.getSnd_seqno());// '发送方流水' ,
		pbcm.setSndUnitno(record.getSnd_unitno());// '公积金机构号' ,
		pbcm.setSndNode(record.getSnd_node());//'发送方节点号' ,
		pbcm.setRcvNode(record.getRcv_node());//'接收方节点号' ,
		pbcm.setBdcDate(record.getBdc_date());// '结算系统日期' ,
		pbcm.setBdcTime(record.getBdc_time());// '结算系统时间' ,
		pbcm.setBdcSeqno(record.getBdc_seqno());// '结算系统流水' ,
		pbcm.setCusNo(record.getCus_no());// '银行客户编号' ,
		pbcm.setOprNo(record.getOpr_no());//'操作员编号' ,
		pbcm.setTxStatus(record.getTx_status());// 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
		pbcm.setTxMsg(record.getTx_msg());
		pbcm.setBatchFilename(record.getBatch_filename());
		pbcm.setFileType(record.getFile_type());
		pbcm.setSuccHostseqno("");
		pbcm.setSuccHostrspcode("");
		pbcm.setSuccHostrspcode("");
		pbcm.setRollHostseqno("");
		pbcm.setRollHostrspcode("");
		pbcm.setRollHostrspmsg("");
		pbcm.setBatchNo("");
		pbcm.setCurrNo(record.getCurr_no());
		pbcm.setCurrIden(record.getCurr_iden());
		pbcm.setBusType(record.getBus_type());
		if(!"".equals(record.getBatch_prjno())) {
		pbcm.setBatchPrjno(Integer.parseInt(record.getBatch_prjno()));
		}
		pbcm.setDeAcctno(record.getDe_acctno());
		pbcm.setDeAcctname(record.getDe_acctname());
		pbcm.setDeAcctclass(record.getDe_acctclass());
		pbcm.setCapAmt(new BigDecimal(record.getCap_amt()));
		pbcm.setDeIntacctno(record.getDe_intacctno());
		pbcm.setDeIntacctname(record.getDe_intacctname());
		pbcm.setDeIntacctclass(record.getDe_intacctclass());
		pbcm.setDeIntcracct(record.getDe_intcracct());
		if(!"".equals(record.getInt_amt())) {
		pbcm.setIntAmt(new BigDecimal(record.getInt_amt()));
		}
		pbcm.setBankAccno(record.getBank_accno());
		pbcm.setTotalNum(Integer.parseInt(record.getTotal_num()));
		pbcm.setTotalAmt(new BigDecimal(record.getTotal_amt()));
		pbcm.setBatchNo(record.getBatch_no());
		pbcm.setRemark(record.getRemark());
		
		pbcmMapper.insertSelective(pbcm);
	}


	/** 
	* @Title: querySeqNosByBatchNo 
	* @Description: 根据批量编号查询明细表序号 
	* @param @param batchNo
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<String> querySeqNosByBatchNo(String batchNo) throws SysTradeExecuteException {
		List<String> list = pbcdMapper.selectSeqNosByBatchNo(batchNo);
		return list;
	}

	/** 
	* @Title: queryMasterByTxStatus 
	* @Description: 根据状态查询主表
	* @param @param txStatus
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<BatchCrdtMasterModel> queryMasterByTxStatus(MyLog myLog,String txStatus) throws SysTradeExecuteException {
		List<PafBatchCrdtMaster> list = pbcmMapper.selectBatchByTxStatus(txStatus);
		List<BatchCrdtMasterModel> resultList = new ArrayList<BatchCrdtMasterModel>();
		for(int i = 0,size = list.size();i<size;i++) {
			PafBatchCrdtMaster entity = list.get(i);
			BatchCrdtMasterModel model = new BatchCrdtMasterModel(myLog,entity.getPlatDate(),
					entity.getPlatTime(),entity.getPlatTrace());
			model.setSndUnitNo(entity.getSndUnitno());
			model.setDeAcctno(entity.getDeAcctno());
			model.setDeAcctname(entity.getDeAcctname());
			model.setBatchNo(entity.getBatchNo());
			model.setTxStatus(entity.getTxStatus());
			model.setBatchFileName(entity.getBatchFilename());
			model.setFileType(entity.getFileType());
			model.setCapAmt(entity.getCapAmt());
			model.setTotalAmt(entity.getTotalAmt());
			model.setTotalNum(entity.getTotalNum());
			model.setTxMsg(entity.getTxMsg());
			resultList.add(model);
		}
		return resultList;
	}

	/**
	 * @throws SysTradeExecuteException  
	* @Title: addBatchDetail 
	* @Description: 明细表登记
	* @param @param record    设定文件 
	* @throws 
	*/
	@Override
	public void addBatchDetail(MyLog myLog,Logger logger,@Valid BatchCrdtMasterModel record) {
		String fileName = record.getBatchFileName();
		String batchNo = record.getBatchNo();
		String fileType = record.getFileType();
		// 4、从文件传输平台获取核心文件
		String remoteFile = record.getRemoteFilePath()+fileName;
		String localFile =  record.getLocalFilePath()+ File.separator + fileName;
		BufferedReader br = null;
		try {
			loadTraceLogFile(myLog,logger, remoteFile, localFile);
			String lineTxt = null;
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(localFile)),
			        "UTF-8"));
			while ((lineTxt = br.readLine()) != null) {
				 lineTxt += "0@|$";
				 String[] array = lineTxt.split("@\\|\\$"); 
				 PafBatchCrdtDetail detailRecord = new PafBatchCrdtDetail();
	             String summary = "",refAcctNo = "",refSeqNo1 = "",
	            		 refSeqNo2 = "",crChgNo = "",
	            		 crOpnchgno = "",crAddr = "",txStatus = "0";
	             BigDecimal txAmt = new BigDecimal(0),capAmt = new BigDecimal(0),
	            		 intAmt = new BigDecimal(0);
	             if(!array[1].matches(REGEX_DECIMAL)) {
	             myLog.error(logger,"批量付款单笔交易金额格式不正确，批量编号："+batchNo+"，序号："+array[0]);
	             throw new RuntimeException("批量交易登记失败，单笔交易金额格式不正确，批量编号：" + batchNo+"，序号："+array[0]);
	             }else {
	            	 txAmt = new BigDecimal(array[1]);
	             }
				if("1".equals(fileType)) {
				     summary = array[4];
				     refAcctNo = array[5];
				     refSeqNo1 = array[6];
				     refSeqNo2 = array[7];
				     if(!array[8].matches(REGEX_DECIMAL)) {
				    	 myLog.error(logger,"批量付款单笔本金交易金额格式不正确，批量编号："+batchNo+"，序号："+array[0]);
				    	 throw new RuntimeException("批量交易登记失败，单笔本金交易金额格式不正确，批量编号：" + batchNo+"，序号："+array[0]);	     
				     }else {
				    	 capAmt = new BigDecimal(array[8]);
				     }
				     if(!"".equals(array[9])) {
				    	 myLog.error(logger,"批量付款单笔利息金额不为空，批量编号："+batchNo+"，序号："+array[0]);
				    	 throw new RuntimeException("批量交易登记失败，单笔利息金额不为空，批量编号：" + batchNo+"，序号："+array[0]);	
				     }
				}else if("2".equals(fileType)) {
					crChgNo = array[4];
					crOpnchgno = array[5];
					crAddr = array[6];
					summary = array[7];
				    refAcctNo = array[8];
				    refSeqNo1 = array[9];
				    refSeqNo2 = array[10];
				    if(!array[11].matches(REGEX_DECIMAL)) {
				    	myLog.error(logger,"批量付款单笔本金交易金额格式不正确，批量编号："+batchNo+"，序号："+array[0]);
				    	throw new RuntimeException("批量交易登记失败，单笔本金交易金额格式不正确，批量编号：" + batchNo+"，序号："+array[0]); 
				    }else {
				    	 capAmt = new BigDecimal(array[11]);
				     }
				     if(!"".equals(array[12])) {
				    	 myLog.error(logger,"批量付款单笔利息金额不为空，批量编号："+batchNo+"，序号："+array[0]);
				    	 throw new RuntimeException("批量交易登记失败，单笔利息金额不为空，批量编号：" + batchNo+"，序号："+array[0]); 
				     }
				 }
				 detailRecord.setBatchNo(batchNo);
	             detailRecord.setSeqNo(array[0]);
	             detailRecord.setTxAmt(txAmt);
				 detailRecord.setCrAcctno(array[2]);
	             detailRecord.setCrAcctname(array[3]);
				 detailRecord.setCrOpnchgno(crOpnchgno);
				 detailRecord.setCrAddr(crAddr);
				 detailRecord.setCrChgno(crChgNo);
				 detailRecord.setSummary(summary);
	             detailRecord.setRefAcctno(refAcctNo);
	             detailRecord.setRefSeqno1(refSeqNo1);
	             detailRecord.setRefSeqno2(refSeqNo2);
	             detailRecord.setCapAmt(capAmt);
	             detailRecord.setIntAmt(intAmt);
                 detailRecord.setTxStatus(txStatus);
                 detailRecord.setHostSeqno("");
                 detailRecord.setHostRspcode("");
                 detailRecord.setHostRspmsg("");
                 pbcdMapper.insert(detailRecord);
			}
		}catch(Exception e) {
			myLog.error(logger, "批量付款明细登记失败，批量编号："+batchNo,e);
			throw new RuntimeException("批量交易登记失败，单笔利息金额不为空，批量编号：" + batchNo); 
		}finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
		}
		
	}
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog,Logger logger, String remoteFile, String localFile) throws PafTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}

	/** 
	* @Title: updateMaster 
	* @Description: 更新主表
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateMaster(@Valid BatchCrdtUpdMasterModel record) throws SysTradeExecuteException {
		PafBatchCrdtMaster master = new PafBatchCrdtMaster();
		master.setPlatDate(record.getPlatDate());
		master.setPlatTrace(record.getPlatTrace());
		master.setTxStatus(record.getTxStatus());
		master.setTxMsg(record.getTxMsg());
		if(null!=record.getSuccAmt()) {
		    master.setSuccAmt(record.getSuccAmt());
		}
		if(null!=record.getSuccNum()) {
		    master.setSuccNum(record.getSuccNum());	
		}
		if(null!=record.getFailAmt()) {
		    master.setFailAmt(record.getFailAmt());	
		}
		if(null!=record.getFailNum()) {
			master.setFailNum(record.getFailNum());
		}
		if(null!=record.getSuccHostSeqNo()) {
		master.setSuccHostseqno(record.getSuccHostSeqNo());
		}
		if(null!=record.getSuccHostRspCode()) {
		master.setSuccHostrspcode(record.getSuccHostRspCode());
		}
		if(null!=record.getSuccHostRspMsg()) {
		master.setSuccHostrspmsg(record.getSuccHostRspMsg());
		}
		if(null!=record.getRollHostSeqNo()) {
		master.setRollHostseqno(record.getRollHostSeqNo());
		}
		if(null!=record.getRollHostRspCode()) {
		master.setRollHostrspcode(record.getRollHostRspCode());
		}
		if(null!=record.getRollHostRspMsg()) {
		master.setRollHostrspmsg(record.getRollHostRspMsg());
		}
		pbcmMapper.updateMaster(master,record.getOldStatus());
		
	}



	/** 
	* @Title: updateDetail 
	* @Description: 更新明细表
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateDetail(@Valid BatchCrdtUpdDetailModel record) throws SysTradeExecuteException {
		PafBatchCrdtDetail detail = new PafBatchCrdtDetail();
		detail.setBatchNo(record.getBatchNo());
		detail.setSeqNo(record.getSeqNo());
		detail.setTxStatus(record.getTxStatus());
		detail.setHostSeqno(record.getHostSeqNo());
		detail.setHostRspcode(record.getHostRspCode());
		detail.setHostRspmsg(record.getHostRspMsg());
		pbcdMapper.updateDetail(detail,record.getOldStatus());
		
	}


	/** 
	* @Title: queryDetailByPk 
	* @Description: 根据主键查询明细信息
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public BatchCrdtDetailModel queryDetailByPk(@Valid BatchCrdtDetailModel record) throws SysTradeExecuteException {
		PafBatchCrdtDetail selections = new PafBatchCrdtDetail();
		selections.setBatchNo(record.getBatchNo());
		selections.setSeqNo(record.getSeqNo());
		PafBatchCrdtDetail entity = pbcdMapper.selectByPrimaryKey(selections);
		if(null == entity) {
			return null;
		}
		BatchCrdtDetailModel result = new BatchCrdtDetailModel();
		result.setBatchNo(entity.getBatchNo());
		result.setSeqNo(entity.getSeqNo());
		result.setTxAmt((entity.getTxAmt()));
		result.setCrAcctNo(entity.getCrAcctno());
		result.setCrAcctName(entity.getCrAcctname());
		result.setCrChgNo(entity.getCrChgno());
		result.setCrOpnChgNo(entity.getCrOpnchgno());
		result.setCrAddr(entity.getCrAddr());
		result.setSummary(entity.getSummary());
		result.setRefAcctNo(entity.getRefAcctno());
		result.setRefSeqNo1(entity.getRefSeqno1());
		result.setRefSeqNo2(entity.getRefSeqno2());
		result.setCapAmt(entity.getCapAmt());
		result.setIntAmt(entity.getIntAmt());
		result.setTxStatus(entity.getTxStatus());
		result.setHostRspCode(null==entity.getHostRspcode()?"":entity.getHostRspcode());
		result.setHostRspMsg(null==entity.getHostRspmsg()?"":entity.getHostRspmsg());
		result.setHostSeqNo(null==entity.getHostSeqno()?"":entity.getHostSeqno());
		return result;
	}


	/** 
	* @Title: queryMasterByPk 
	* @Description: 根据主键查询主表
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public BatchCrdtMasterModel queryMasterByPk(MyLog myLog,@Valid BatchCrdtMasterModel record) throws SysTradeExecuteException {
		PafBatchCrdtMaster selections = new PafBatchCrdtMaster();
		selections.setPlatDate(record.getSysDate());
		selections.setPlatTrace(record.getSysTraceno());
		PafBatchCrdtMaster entity =	pbcmMapper.selectByPrimaryKey(selections);
		if(null == entity) {
			return null;
		}
		record.setSndUnitNo(entity.getSndUnitno());
		record.setDeAcctno(entity.getDeAcctno());
		record.setDeAcctname(entity.getDeAcctname());
		record.setBatchNo(entity.getBatchNo());
		record.setTxStatus(entity.getTxStatus());
		record.setBatchFileName(entity.getBatchFilename());
		record.setFileType(entity.getFileType());
		record.setDeAcctno(entity.getDeAcctno());
		record.setDeAcctname(entity.getDeAcctname());
		record.setCapAmt(entity.getCapAmt());
		record.setTotalAmt(entity.getTotalAmt());
		record.setTotalNum(entity.getTotalNum());
		record.setSndDate(entity.getSndDate());
		record.setBatchPrjNo(entity.getBatchPrjno());
		record.setBusType(entity.getBusType());
		record.setCurrNo(entity.getCurrNo());
		record.setCurrIden(entity.getCurrIden());
		record.setOperNo(entity.getOprNo());
		record.setSuccNum(entity.getSuccNum());
		record.setSuccAmt(entity.getSuccAmt());
		record.setTxMsg(entity.getTxMsg());
		record.setRemark(entity.getRemark());
		record.setSndSeqNo(entity.getSndSeqno());
		record.setFailAmt(entity.getFailAmt());
		record.setFailNum(entity.getFailNum());
		record.setSuccHostSeqNo(entity.getSuccHostseqno());
		record.setRollHostSeqNo(entity.getRollHostseqno());
		return record;
	}


	/** 
	* @Title: queryDetailsAmtSum 
	* @Description: 根据批量编号查询明细信息总成功金额、总成功笔数、总失败金额、总失败笔数
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public BatchCrdtMasterModel queryDetailsAmtSum(MyLog myLog,String batchNo) throws SysTradeExecuteException {
		PafBatchCrdtMaster entity = pbcmMapper.selectDetailsAmtSum(batchNo);
		if(null == entity) {
			return null;
		}
		BatchCrdtMasterModel record = new BatchCrdtMasterModel(myLog,entity.getPlatDate(),
				entity.getPlatTime(),entity.getPlatTrace());
		record.setSuccNum(entity.getSuccNum());
		record.setSuccAmt(entity.getSuccAmt());
		record.setFailAmt(entity.getFailAmt());
		record.setFailNum(entity.getFailNum());
		record.setTotalAmt(entity.getTotalAmt());
		record.setTotalNum(entity.getTotalNum());
		return record;
	}


	/** 
	* @Title: querySeqNosByParams 
	* @Description: 根据状态、批量编号查询明细信息序号
	* @param @param batchNo
	* @param @param txStatus
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<String> querySeqNosByParams(String batchNo, String txStatus) throws SysTradeExecuteException {
		List<String> list = pbcdMapper.selectSeqNosByParams(batchNo, txStatus);
		return list;
	}


	
}
