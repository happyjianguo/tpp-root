package com.fxbank.cap.paf.trade.paf;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchCrdtMasterModel;
import com.fxbank.cap.paf.model.BatchLoanMasterModel;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.service.IBatchCrdtService;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

@Service("BDC207")
public class BatchResultQryTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(BatchResultQryTrade.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService IForwardToESBService;
	
	@Reference(version = "1.0.0")
	private IBatchCrdtService batchCrdtService;
	
	@Reference(version = "1.0.0")
	private IBatchLoanService batchLoanService;
	
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
	//批量编号
	private static final String BATCHNO = "BatchNo";
    //批量日期
	private static final String BATCHDATE = "BatchDate";
	//批量项目编号
	private static final String BATCHPRJNO = "BatchPrjNo";
	//业务类别 
	private static final String BUSTYPE = "BusType";
	//币种
	private static final String CURRNO = "CurrNo";
	//钞汇别
	private static final String CURRIDEN = "CurrIden";
	//发送机构
	private static final String BATCHSENDBRANCHNO = "BatchSendBranchNo";
	//发送操作员
	private static final String BKOPERNO = "BkOperNo";
	//总笔数
	private static final String BATCHTOTALNUM = "BatchTotalNum";
	//总金额
	private static final String BATCHTOTALAMT = "BatchTotalAmt";
	//成功笔数
	private static final String BATCHTOTALSUCCNUM = "BatchTotalSuccNum";
	//成功金额
	private static final String BATCHTOTALSUCCAMT = "BatchTotalSuccAmt";
	//批量备注 
	private static final String REMARK = "Remark";
	//处理/失败信息
	private static final String SUMMARY = "Summary";
	//当前状态
	private static final String BATCHTXSTATUS = "BatchTxStatus";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();
		
		String batchNo = reqDto.getBody().get(BATCHNO).toString();
		String batchDate = null,batchPrjNo = null,busType = null,txStatus = null,
				currNo = null,currIden = null,batchSendBranchNo = null,bkOperNo = null,
				sendSeqNo = null,batchTotalNum = null,batchTotalAmt = null,batchTotalSuccNum = null,
				batchTotalSuccAmt = null,remark = null,summary = null,batchTxStatus = null;
		if(batchNo.startsWith("CRDT")) {
			BatchCrdtMasterModel record = new BatchCrdtMasterModel(myLog,Integer.parseInt(batchNo.substring(4, 12)),0,Integer.parseInt(batchNo.substring(12)));
			record = batchCrdtService.queryMasterByPk(myLog,record);
			if(null == record) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10021);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			batchDate = record.getSndDate();
			batchPrjNo = record.getBatchPrjNo()!=null?record.getBatchPrjNo().toString():"";
			busType = record.getBusType();
			currNo = record.getCurrNo();
			currIden = record.getCurrIden();
			batchSendBranchNo = record.getSndUnitNo();
			bkOperNo = record.getOperNo();
			sendSeqNo = record.getSndSeqNo();
			batchTotalNum = record.getTotalNum().toString();
			batchTotalAmt = record.getTotalAmt().toString();
			batchTotalSuccNum = record.getSuccNum().toString();
			batchTotalSuccAmt = record.getSuccAmt().toString();
			remark = record.getRemark();
			summary = record.getTxMsg();
			txStatus = record.getTxStatus();
			switch(txStatus) {
			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
				batchTxStatus = "0";
				break;
			case "5":
				batchTxStatus = "1";
				break;
			case "9":
				batchTxStatus = "3";
				break;
			}
			
		}else if(batchNo.startsWith("LOAN")) {
			BatchLoanMasterModel record = new BatchLoanMasterModel(myLog,Integer.parseInt(batchNo.substring(4, 12)),0,Integer.parseInt(batchNo.substring(12)));
			record = batchLoanService.queryMasterByPk(myLog,record);
			if(null == record) {
				PafTradeExecuteException e = new PafTradeExecuteException(
						PafTradeExecuteException.PAF_E_10021);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			batchDate = record.getSndDate();
			batchPrjNo = record.getBatchPrjNo()!=null?record.getBatchPrjNo().toString():"";
			busType = "05";
			currNo = record.getCurrNo();
			currIden = record.getCurrIden();
			batchSendBranchNo = record.getSndUnitNo();
			bkOperNo = record.getOperNo();
			sendSeqNo = record.getSndSeqNo();
			batchTotalNum = record.getTotalNum().toString();
			batchTotalAmt = record.getTotalAmt().toString();
			batchTotalSuccNum = record.getSuccNum().toString();
			batchTotalSuccAmt = record.getSuccAmt().toString();
			remark = record.getRemark();
			summary = record.getTxMsg();
			txStatus = record.getTxStatus();
			switch(txStatus) {
			case "0":
			case "1":
			case "2":
			case "3":
				batchTxStatus = "0";
				break;
			case "4":
				batchTxStatus = "1";
				break;
			case "9":
				batchTxStatus = "3";
				break;
			}
		}else {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10022);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		
		repDto.getBody().getField().add(new FIELD(BATCHDATE,batchDate));
		repDto.getBody().getField().add(new FIELD(BATCHPRJNO,batchPrjNo));
		repDto.getBody().getField().add(new FIELD(BATCHNO,batchNo));
		repDto.getBody().getField().add(new FIELD(BUSTYPE,busType));
		repDto.getBody().getField().add(new FIELD(CURRNO,currNo));
		repDto.getBody().getField().add(new FIELD(CURRIDEN,currIden));
		repDto.getBody().getField().add(new FIELD(BATCHSENDBRANCHNO,batchSendBranchNo));
		repDto.getBody().getField().add(new FIELD(BKOPERNO,bkOperNo));
		repDto.getBody().getField().add(new FIELD(SENDSEQNO,sendSeqNo));
		repDto.getBody().getField().add(new FIELD(BATCHTOTALNUM,batchTotalNum));
		repDto.getBody().getField().add(new FIELD(BATCHTOTALAMT,batchTotalAmt));
		repDto.getBody().getField().add(new FIELD(BATCHTOTALSUCCNUM,batchTotalSuccNum));
		repDto.getBody().getField().add(new FIELD(BATCHTOTALSUCCAMT,batchTotalSuccAmt));
		repDto.getBody().getField().add(new FIELD(REMARK,remark));
		repDto.getBody().getField().add(new FIELD(SUMMARY,summary));
		repDto.getBody().getField().add(new FIELD(BATCHTXSTATUS,batchTxStatus));
		return repDto;
		
	}
}
