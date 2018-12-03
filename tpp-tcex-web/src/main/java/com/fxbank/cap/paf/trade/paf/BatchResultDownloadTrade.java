package com.fxbank.cap.paf.trade.paf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchCrdtDetailModel;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchLoanDetailModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.tienon.util.FileFieldConv;

@Service("BDC208")
public class BatchResultDownloadTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(BatchResultDownloadTrade.class);

	@Reference(version = "1.0.0")
	private IBatchCrdtService batchCrdtService;
	
	@Reference(version = "1.0.0")
	private IBatchLoanService batchLoanService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService IForwardToESBService;
	
	@Resource
	private MyJedis myJedis;
	
	@Resource
	private LogPool logPool;
	
	private static final String BRTEL_PREFIX = "paf_branch.";
	private final static String COMMON_PREFIX = "paf_common.";
	
	//发送方日期
	private static final String SENDDATE = "SendDate";
	//发送方时间
	private static final String SENDTIME = "SendTime";
	//发送方流水
	private static final String SENDSEQNO = "SendSeqNo";
	//公积金机构号
	private static final String TXUNITNO = "TxUnitNo";
	//发送方节点号
	private static final String SENDNODE = "SendNode";
	//接收方节点号
	private static final String RECEIVENODE = "ReceiveNode";
	//结算系统日期
	private static final String BDCDATE = "BDCDate";
	//结算系统时间
	private static final String BDCTIME = "BDCTime";
	//结算系统流水
	private static final String BDCSEQNO = "BDCSeqNo";
	//银行客户编号
	private static final String CUSTNO = "CustNo";
	//操作员编号
	private static final String OPERNO = "OperNo";
	//类型
	private static final String TXCODESUB = "TxCodeSub";
	//批量编号
	private static final String BATCHNO = "BatchNo";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();
		String batchNo = reqDto.getBody().get(BATCHNO).toString();
		//类型1：成功清单下载2：失败清单下载3：结果数据下载
		String txCodeSub = reqDto.getBody().get(TXCODESUB).toString();
		String txStatus = "";
		if("1".equals(txCodeSub)) {
			txStatus = "2";
		}else if("2".equals(txCodeSub)) {
			txStatus = "3";
		}else {
			txStatus = "2,3";
		}
		String fileName = null,bcdString = null;
		StringBuffer acDataBuf = new StringBuffer();
		/**
		 * 类型 1:文件2:逐笔3:都支持
		 */
		String type = "1";
		/**
		 * 主表状态
		 * 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
		 * 交易状态：0、接收；1、登记；2、处理中；3、处理完成；4、转入公积金账户；9、处理失败
		 */
		String masterStatus = "";
		if(batchNo.startsWith("CRDT")) {
			BatchCrdtMasterModel record = new BatchCrdtMasterModel(myLog,Integer.parseInt(batchNo.substring(4, 12)),0,Integer.parseInt(batchNo.substring(12)));
			record = batchCrdtService.queryMasterByPk(myLog,record);
			if(null == record) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10021);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			masterStatus = record.getTxStatus();
			if(!"5".equals(masterStatus)&&!"9".equals(masterStatus)) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10023);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			List<String> detailSeqNos = batchCrdtService.querySeqNosByParams(batchNo,txStatus);
			    /**
			     * 类别 a：行内代扣b：行内代发c：跨行代扣d: 跨行代发e: 混合代扣f: 混合代发g: 其他
			    */
			    String category = "",fileType = record.getFileType();
			    if("1".equals(fileType)) {
			    	category = "b";
			    }else if("2".equals(fileType)) {
			    	category = "d";
			    }
			    //汇总行
				acDataBuf.append("$").append(record.getSndDate()).append("|");
				acDataBuf.append(record.getSndUnitNo()).append("|");
				acDataBuf.append(batchNo).append("|");
				acDataBuf.append(category).append("|");
				acDataBuf.append(type).append("|");
				acDataBuf.append(record.getCurrNo()).append("|");
				acDataBuf.append(record.getCurrIden()).append("|");
				acDataBuf.append(record.getTotalNum()).append("|");
				acDataBuf.append(record.getTotalAmt()).append("|");
				acDataBuf.append(record.getSuccNum()).append("|"); 
				acDataBuf.append(record.getSuccAmt()).append("|"); 
				acDataBuf.append(record.getFailNum()).append("|"); 
				acDataBuf.append(record.getFailAmt()).append("|"); 
				acDataBuf.append(record.getTxMsg()).append("|"); 
				acDataBuf.append("\n");
				//明细行
				String hostSeqNo = "",detailStatus = "";
				for(String seqNo:detailSeqNos) {
					BatchCrdtDetailModel detailRecord = new BatchCrdtDetailModel();
					detailRecord.setBatchNo(batchNo);
					detailRecord.setSeqNo(seqNo);
					detailRecord = batchCrdtService.queryDetailByPk(detailRecord);
					acDataBuf.append(seqNo).append("|");
					acDataBuf.append(record.getCurrNo()).append("|");
					acDataBuf.append(record.getCurrIden()).append("|");
					acDataBuf.append(detailRecord.getTxAmt()).append("|");
					acDataBuf.append(record.getDeAcctno()).append("|");
					acDataBuf.append(record.getDeAcctname()).append("|");
					//付方余额
					acDataBuf.append("").append("|");
					acDataBuf.append(detailRecord.getCrAcctNo()).append("|");
					acDataBuf.append(detailRecord.getCrAcctName()).append("|");
					//收方余额
					acDataBuf.append("").append("|");
				    /**
				     * 银行反馈的结算处理响应码
				     * 00000-表示该笔明细交易成功
				     * 其他表示该笔明细交易失败，交易失败原因显示在“响应信息”中
				     */
					acDataBuf.append(detailRecord.getHostRspCode().
							equals("000000")?"00000":detailRecord.getHostRspCode()).
					append("|");
					acDataBuf.append(detailRecord.getHostRspMsg()).append("|");
					//主机日期
					acDataBuf.append(batchNo.substring(4,12)).append("|");
					//主机流水号
					detailStatus = detailRecord.getTxStatus();
					if("2".equals(detailStatus)) {
						hostSeqNo = record.getSuccHostSeqNo();
					}else if("3".equals(detailStatus)) {
						hostSeqNo = record.getRollHostSeqNo();
					}
					acDataBuf.append(hostSeqNo).append("|");
					//回执编号
					acDataBuf.append("").append("|");
					//备用扩展
					acDataBuf.append("");
					acDataBuf.append("\n");
				}
				acDataBuf.append("#END");
			
		}else if(batchNo.startsWith("LOAN")) {
			BatchLoanMasterModel record = new BatchLoanMasterModel(myLog,Integer.parseInt(batchNo.substring(4, 12)),0,Integer.parseInt(batchNo.substring(12)));
			record = batchLoanService.queryMasterByPk(myLog,record);
			if(null == record) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10021);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			masterStatus = record.getTxStatus();
			if(!"4".equals(masterStatus)&&!"9".equals(masterStatus)) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10023);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			List<String> detailSeqNos = batchLoanService.querySeqNosByParams(batchNo,txStatus);
			    /**
			     * 类别 a：行内代扣b：行内代发c：跨行代扣d: 跨行代发e: 混合代扣f: 混合代发g: 其他
			    */
			    String category = "",fileType = record.getFileType();
			    if("1".equals(fileType)) {
			    	category = "a";
			    }else if("2".equals(fileType)) {
			    	category = "c";
			    }
			    //汇总行
				acDataBuf.append("$").append(record.getSndDate()).append("|");
				acDataBuf.append(record.getSndUnitNo()).append("|");
				acDataBuf.append(batchNo).append("|");
				acDataBuf.append(category).append("|");
				acDataBuf.append(type).append("|");
				acDataBuf.append(record.getCurrNo()).append("|");
				acDataBuf.append(record.getCurrIden()).append("|");
				acDataBuf.append(record.getTotalNum()).append("|");
				acDataBuf.append(record.getTotalAmt()).append("|");
				acDataBuf.append(record.getSuccNum()).append("|"); 
				acDataBuf.append(record.getSuccAmt()).append("|"); 
				acDataBuf.append(record.getFailNum()).append("|"); 
				acDataBuf.append(record.getFailAmt()).append("|"); 
				acDataBuf.append(record.getTxMsg()).append("|"); 
				acDataBuf.append("\n");
				//明细行
				for(String seqNo:detailSeqNos) {
					BatchLoanDetailModel detailRecord = new BatchLoanDetailModel();
					detailRecord.setBatchNo(batchNo);
					detailRecord.setSeqNo(seqNo);
					detailRecord = batchLoanService.queryDetailByPk(detailRecord);
					acDataBuf.append(seqNo).append("|");
					acDataBuf.append(record.getCurrNo()).append("|");
					acDataBuf.append(record.getCurrIden()).append("|");
					acDataBuf.append(detailRecord.getSuAmt()).append("|");
					acDataBuf.append(detailRecord.getDeAcctNo()).append("|");
					acDataBuf.append(detailRecord.getDeAcctName()).append("|");
					//付方余额
					acDataBuf.append("").append("|");
					acDataBuf.append(record.getCrAcctno()).append("|");
					acDataBuf.append(record.getCrAcctname()).append("|");
					//收方余额
					acDataBuf.append("").append("|");
				    /**
				     * 银行反馈的结算处理响应码
				     * 00000-表示该笔明细交易成功
				     * 其他表示该笔明细交易失败，交易失败原因显示在“响应信息”中
				     */
					acDataBuf.append(detailRecord.getHostRspCode().
							equals("000000")?"00000":detailRecord.getHostRspCode()).
					append("|");
					acDataBuf.append(detailRecord.getHostRspMsg()).append("|");
					//主机日期
					acDataBuf.append(batchNo.substring(4,12)).append("|");
					//主机流水号
					acDataBuf.append(record.getSuccHostSeqNo()).append("|");
					//回执编号
					acDataBuf.append("").append("|");
					//备用扩展
					acDataBuf.append("");
					acDataBuf.append("\n");
				}
				acDataBuf.append("#END");
			
		}else {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10022);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		 //循环报文体节点名称
		FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("FILE_LIST");
		List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
		FIELDS_LIST_INNER inner = new FIELDS_LIST_INNER("0");
		fileName = "BANK_BAT_"+batchNo+"_RST.DAT";
	    try {
			bcdString = FileFieldConv.fieldASCtoBCD(acDataBuf.toString(), PAF.ENCODING);
		} catch (IOException e) {
			throw new RuntimeException("文件加密失败");
		}
		inner.getField().add(new FIELD("Name",fileName));
		inner.getField().add(new FIELD("DATA",bcdString));
		innerList.add(inner);
		myLog.info(logger,"发送文件名称["+fileName+"]");
		myLog.info(logger,"发送文件内容["+acDataBuf.toString()+"]");
		outer.setField_list(innerList);
		repDto.getBody().setField_list(outer);
		return repDto;
		
	}
}
