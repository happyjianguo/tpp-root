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
import com.fxbank.cap.paf.entity.PafBatchLoanDetail;
import com.fxbank.cap.paf.entity.PafBatchLoanMaster;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.mapper.PafBatchLoanDetailMapper;
import com.fxbank.cap.paf.mapper.PafBatchLoanMasterMapper;
import com.fxbank.cap.paf.model.BatchLoanDetailModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.BatchLoanRcvFileModel;
import com.fxbank.cap.paf.model.BatchLoanUpdDetailModel;
import com.fxbank.cap.paf.model.BatchLoanUpdMasterModel;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class BatchLoanService implements IBatchLoanService{
	
	@Resource
	private PafBatchLoanMasterMapper pbcmMapper;
	@Resource
	private PafBatchLoanDetailMapper pbcdMapper;
	//最多十四位整数，2位小数，不为0
	private final static String REGEX_DECIMAL = "^(?!0$|0\\.00|0\\.0|0\\d+$)(?:0?|[1-9]\\d{0,13})(?:\\.\\d{1,2})?$";

	/** 
	* @Title: rcvBatchLoanFile 
	* @Description: 接收批量贷款扣款文件 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void rcvBatchLoanFile(@Valid BatchLoanRcvFileModel record) throws SysTradeExecuteException {
		PafBatchLoanMaster pbcm = new PafBatchLoanMaster();
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
		pbcm.setBatchNo("");
		pbcm.setCurrNo(record.getCurr_no());
		pbcm.setCurrIden(record.getCurr_iden());
		if(!"".equals(record.getBatch_prjno())) {
		pbcm.setBatchPrjno(Integer.parseInt(record.getBatch_prjno()));
		}
		pbcm.setCrAcctno(record.getCr_acctno());
		pbcm.setCrAcctname(record.getCr_acctname());
		pbcm.setCrAcctclass(record.getCr_acctclass());
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
	public List<BatchLoanMasterModel> queryMasterByTxStatus(MyLog myLog,String txStatus) throws SysTradeExecuteException {
		List<PafBatchLoanMaster> list = pbcmMapper.selectBatchByTxStatus(txStatus);
		List<BatchLoanMasterModel> resultList = new ArrayList<BatchLoanMasterModel>();
		for(int i = 0,size = list.size();i<size;i++) {
			PafBatchLoanMaster entity = list.get(i);
			BatchLoanMasterModel model = new BatchLoanMasterModel(myLog,entity.getPlatDate(),
					entity.getPlatTime(),entity.getPlatTrace());
			model.setSndUnitNo(entity.getSndUnitno());
			model.setCrAcctno(entity.getCrAcctno());
			model.setCrAcctname(entity.getCrAcctname());
			model.setBatchNo(entity.getBatchNo());
			model.setTxStatus(entity.getTxStatus());
			model.setBatchFileName(entity.getBatchFilename());
			model.setFileType(entity.getFileType());
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
	public void addBatchDetail(MyLog myLog,Logger logger,@Valid BatchLoanMasterModel record) {
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
				 PafBatchLoanDetail detailRecord = new PafBatchLoanDetail();
	             String summary = "",deChgNo = "",deOpnchgno = "",
	            		 deAddr = "",enghFlag = "",proNo = "",txStatus = "0";
	             BigDecimal txAmt = new BigDecimal(0);
	             if(!array[1].matches(REGEX_DECIMAL)) {
	             myLog.error(logger,"批量贷款扣款单笔交易金额格式不正确，批量编号："+batchNo+"，序号："+array[0]);
	             throw new RuntimeException("批量交易登记失败，单笔交易金额格式不正确，批量编号：" + batchNo+"，序号："+array[0]);
	             }else {
	            	 txAmt = new BigDecimal(array[1]);
	             }
				if("1".equals(fileType)) {
					 enghFlag = array[4];
				     summary = array[5];
				}else if("2".equals(fileType)) {
					deChgNo = array[4];
					deOpnchgno = array[5];
					deAddr = array[6];
					proNo = array[7];
					enghFlag = array[8];
					summary = array[9];
				 }
				 detailRecord.setBatchNo(batchNo);
	             detailRecord.setSeqNo(array[0]);
	             detailRecord.setTxAmt(txAmt);
	             detailRecord.setSuAmt(new BigDecimal(0));
				 detailRecord.setDeAcctno(array[2]);
	             detailRecord.setDeAcctname(array[3]);
				 detailRecord.setDeOpnchgno(deOpnchgno);
				 detailRecord.setDeAddr(deAddr);
				 detailRecord.setDeChgno(deChgNo);
				 detailRecord.setProNo(proNo);
				 detailRecord.setEnghFlag(enghFlag);
				 detailRecord.setSummary(summary);
                 detailRecord.setTxStatus(txStatus);
                 detailRecord.setHostSeqno("");
                 detailRecord.setHostRspcode("");
                 detailRecord.setHostRspmsg("");
                 
                 pbcdMapper.insert(detailRecord);
			}
		}catch(Exception e) {
			myLog.error(logger, "批量贷款扣款明细登记失败，批量编号："+batchNo,e);
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
	public void updateMaster(@Valid BatchLoanUpdMasterModel record) throws SysTradeExecuteException {
		PafBatchLoanMaster master = new PafBatchLoanMaster();
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
	public void updateDetail(@Valid BatchLoanUpdDetailModel record) throws SysTradeExecuteException {
		PafBatchLoanDetail detail = new PafBatchLoanDetail();
		detail.setBatchNo(record.getBatchNo());
		detail.setSeqNo(record.getSeqNo());
		detail.setTxStatus(record.getTxStatus());
		detail.setHostSeqno(record.getHostSeqNo());
		detail.setHostRspcode(record.getHostRspCode());
		detail.setHostRspmsg(record.getHostRspMsg());
		detail.setSuAmt(record.getSuAmt());
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
	public BatchLoanDetailModel queryDetailByPk(@Valid BatchLoanDetailModel record) throws SysTradeExecuteException {
		PafBatchLoanDetail selections = new PafBatchLoanDetail();
		selections.setBatchNo(record.getBatchNo());
		selections.setSeqNo(record.getSeqNo());
		PafBatchLoanDetail entity = pbcdMapper.selectByPrimaryKey(selections);
		if(null == entity) {
			return null;
		}
		BatchLoanDetailModel result = new BatchLoanDetailModel();
		result.setBatchNo(entity.getBatchNo());
		result.setSeqNo(entity.getSeqNo());
		result.setTxAmt((entity.getTxAmt()));
		result.setDeAcctNo(entity.getDeAcctno());
		result.setDeAcctName(entity.getDeAcctname());
		result.setDeChgNo(entity.getDeChgno());
		result.setDeOpnChgNo(entity.getDeOpnchgno());
		result.setDeAddr(entity.getDeAddr());
		result.setSummary(entity.getSummary());
		result.setTxStatus(entity.getTxStatus());
		result.setEnghFlag(entity.getEnghFlag());
		result.setSuAmt(entity.getSuAmt());
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
	public BatchLoanMasterModel queryMasterByPk(MyLog myLog,@Valid BatchLoanMasterModel record) throws SysTradeExecuteException {
		PafBatchLoanMaster selections = new PafBatchLoanMaster();
		selections.setPlatDate(record.getSysDate());
		selections.setPlatTrace(record.getSysTraceno());
		PafBatchLoanMaster entity =	pbcmMapper.selectByPrimaryKey(selections);
		if(null == entity) {
			return null;
		}
		record.setSndUnitNo(entity.getSndUnitno());
		record.setCrAcctno(entity.getCrAcctno());
		record.setCrAcctname(entity.getCrAcctname());
		record.setBatchNo(entity.getBatchNo());
		record.setTxStatus(entity.getTxStatus());
		record.setBatchFileName(entity.getBatchFilename());
		record.setFileType(entity.getFileType());
		record.setCrAcctno(entity.getCrAcctno());
		record.setCrAcctname(entity.getCrAcctname());
		record.setTotalAmt(entity.getTotalAmt());
		record.setTotalNum(entity.getTotalNum());
		record.setSndDate(entity.getSndDate());
		record.setBatchPrjNo(entity.getBatchPrjno());
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
	public BatchLoanMasterModel queryDetailsAmtSum(MyLog myLog,String batchNo) throws SysTradeExecuteException {
		PafBatchLoanMaster entity = pbcmMapper.selectDetailsAmtSum(batchNo);
		if(null == entity) {
			return null;
		}
		BatchLoanMasterModel record = new BatchLoanMasterModel(myLog,entity.getPlatDate(),
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
