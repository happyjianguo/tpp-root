package com.fxbank.cap.paf.trade.paf;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpPut;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.BatchLoanRcvFileModel;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.service.IBatchLoanService;
import com.fxbank.cap.paf.utils.FilesUtils;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.tienon.util.FileFieldConv;

import redis.clients.jedis.Jedis;

@Service("BDC205")
public class BatchLoanTrade implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(BatchLoanTrade.class);

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
	//币种，156
	private static final String CURRNO = "CurrNo";
	//钞汇鉴别：1、钞；2、汇
	private static final String CURRIDEN = "CurrIden";
	//文件类型 1：同行2：跨行3：混合
	private static final String FILETYPE = "FileType";
	//批量项目编号
	private static final String BATCHPRJNO = "BatchPrjNo";
	//收方账号
	private static final String CRACCTNO = "CrAcctNo";
	//收方户名 
	private static final String CRACCTNAME = "CrAcctName";
	//收方账户类别1：对私2：对公
	private static final String CRACCTCLASS = "CrAcctClass";
	//银行内部过渡户
	private static final String BANKACCTNO = "BankAcctNo";
	//总笔数
	private static final String BATCHTOTALNUM = "BatchTotalNum";
	//总金额
	private static final String BATCHTOTALAMT = "BatchTotalAmt";
	//备注
	private static final String REMARK = "Remark";
	//文件列表 
	private static final String FILE_LIST = "FILE_LIST";
	//文件数据
	private static final String DATA = "DATA";
	//文件名
	private static final String NAME = "NAME";
	
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		SER_REP_BDC repDto = new SER_REP_BDC();
		//文件类型1：同行2：跨行3：混合,目前不支持跨行
		String fileType = reqDto.getBody().get(FILETYPE).toString();
		if(!"1".equals(fileType)) {
			PafTradeExecuteException e = new PafTradeExecuteException(
					PafTradeExecuteException.PAF_E_10017);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		//接收文件
		String batchNo = rcvBatchLoanFile(reqDto);
		repDto.getBody().getField().add(new FIELD("BatchNo",batchNo));
		return repDto;
		
	}
	private String rcvBatchLoanFile(SER_REQ_DATA reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		Map<String,Object> headMap = reqDto.getHead();
		Map<String,Object> bodyMap = reqDto.getBody();
		//公积金节点编号
		String txUnitNo = headMap.get(TXUNITNO).toString();	
		//交易机构
        String txBrno =null ;
        //开户机构
    	String upBrno = null;
    	String txtPath = null;
        try(Jedis jedis = myJedis.connect()){
        	txBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_TXBRNO");
        	upBrno = jedis.get(BRTEL_PREFIX+txUnitNo+"_UPBRNO");
        	txtPath = jedis.get(COMMON_PREFIX + "txt_path");
        }
        
        String fileName = "";
        String ascString = "";
        for (Entry<String, Object> e : reqDto.getBodyList().entrySet()) {
			logger.info("BODYLIST:" + e.getKey() + " || " + e.getValue());
			if (e.getKey().equals("DATA0")) {
				String bcdString = (String) e.getValue();
				try {
					 ascString = FileFieldConv.fieldBCDtoASC(bcdString,PAF.ENCODING);
					logger.info("收到的文件内容:"+ascString);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getKey().equals("NAME0")) {
				fileName = (String) e.getValue();
			}
		}
        
        String remoteFile = "/cip/"+fileName;
        try {
			FilesUtils.createFile(txtPath + File.separator, fileName, ascString);
		} catch (Exception e1) {
			myLog.error(logger, "文件创建失败", e1);
		}
		String localFile = txtPath + File.separator + fileName;
		uploadTraceLogFile(myLog, remoteFile, localFile);
        
		BatchLoanRcvFileModel record = new BatchLoanRcvFileModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),reqDto.getSysTraceno());
		record.setTx_brno(txBrno);		
		record.setUp_brno(upBrno);	
		record.setSnd_date(headMap.get(SENDDATE).toString()); //发送方日期
		record.setSnd_time(headMap.get(SENDTIME).toString());//'发送方时间' ,
		record.setSnd_seqno(headMap.get(SENDSEQNO).toString());// '发送方流水' ,
		record.setSnd_unitno(txUnitNo);// '公积金机构号' ,
		record.setSnd_node(headMap.get(SENDNODE).toString());//'发送方节点号' ,
		record.setRcv_node(headMap.get(RECEIVENODE).toString());//'接收方节点号' ,
		record.setBdc_date(headMap.get(BDCDATE).toString());// '结算系统日期' ,
		record.setBdc_time(headMap.get(BDCTIME).toString());// '结算系统时间' ,
		record.setBdc_seqno(headMap.get(BDCSEQNO).toString());// '结算系统流水' ,
		record.setCus_no(headMap.get(CUSTNO).toString());// '银行客户编号' ,
		record.setOpr_no(headMap.get(OPERNO).toString());//'操作员编号' ,
		record.setTx_status("0");
		record.setTx_msg("上传批量贷款扣款明细文件成功");
		record.setFile_type(bodyMap.get(FILETYPE).toString());
		record.setBatch_filename(fileName);
		record.setCurr_no(bodyMap.get(CURRNO).toString());
		record.setCurr_iden(bodyMap.get(CURRIDEN).toString());
		record.setBatch_prjno(bodyMap.get(BATCHPRJNO).toString());
		record.setCr_acctno(bodyMap.get(CRACCTNO).toString());
		record.setCr_acctname(bodyMap.get(CRACCTNAME).toString());
		record.setCr_acctclass(bodyMap.get(CRACCTCLASS).toString());
		record.setBank_accno(bodyMap.get(BANKACCTNO).toString());
		record.setTotal_num(bodyMap.get(BATCHTOTALNUM).toString());
		record.setTotal_amt(bodyMap.get(BATCHTOTALAMT).toString());
		record.setRemark(bodyMap.get(REMARK).toString());
		record.setBatch_no("LOAN" + reqDto.getSysDate() + reqDto.getSysTraceno());
		
		batchLoanService.rcvBatchLoanFile(record);
		return record.getBatch_no();
	}
	 
	private void uploadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws PafTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpPut ftpPut = null;
		try {
			ftpPut = new FtpPut(localFile, remoteFile, configSet);
			String result = ftpPut.doPutFile();
			if ("".equals(result)) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "上传失败！");
				PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10015);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "上传成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "上传失败！", e);
			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10015,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpPut != null) {
				ftpPut.close();
			}
		}
	}
}
