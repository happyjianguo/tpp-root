package com.fxbank.tpp.bocm.trade.esb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpPut;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.QR_TraceDto;
import com.fxbank.tpp.bocm.dto.esb.REP_QR_Trace;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;

import redis.clients.jedis.Jedis;


/** 
* @ClassName: QR_Trace 
* @Description: 交易流水查询
* @author YePuLiang
* @date 2019年7月10日 下午3:00:41 
*  
*/
@Service("REQ_QR_Trace")
public class QR_Trace extends TradeBase implements TradeExecutionStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(QR_Trace.class);
	
	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "bocm.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		
		MyLog myLog = logPool.get();
		String begDate = "20190710";
		String endDate = "20190710";
		String begTrace="";
		String endTrace="";
		String txAmt="";
		String hostStatus="";
		List<BocmSndTraceQueryModel> sndlist = sndTraceService.getSndTrace(myLog, begDate, endDate, begTrace, 
				endTrace, txAmt, hostStatus);
		logger.info("往账记录:  "+sndlist.size());
		
		//返回柜面文件报文
		StringBuffer sb = new StringBuffer();
		//总金额
		BigDecimal total = new BigDecimal("0.00");
		for(BocmSndTraceQueryModel model : sndlist){
			logger.info("渠道流水 号："+model.getPlatTrace());
			total = total.add(model.getTxAmt());
			String trace = transRepTrace(model);
			sb.append(trace).append("\n"); 
		}
		sb.append(sndlist.size()).append("|"); 	
		sb.append(total.toString()).append("|"); 	
		
		logger.info("返回查询文件：\n"+sb.toString());
		
		String fileName = "QR_Trace_"+dto.getSysDate()+dto.getSysTime()+".txt";
		writeFile(myLog,fileName,sb.toString());
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		uploadTraceFile(myLog, fileName, localPath);
		REP_QR_Trace rep = new REP_QR_Trace();
		return rep;
	}
	
	private void writeFile(MyLog myLog,String fileName,String str) throws BocmTradeExecuteException{
		String localPath="";
		File file = null;
		BufferedWriter bw = null;
		try {
			try (Jedis jedis = myJedis.connect()) {
				localPath = jedis.get(COMMON_PREFIX+"txt_path");
			}
			file = new File(localPath+File.separator+fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream writerStream = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			bw.write(str);
			
		}catch (Exception e) {
			myLog.error(logger,"生成柜面流水查询文件失败: "+e);
			BocmTradeExecuteException e1 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10018);
			throw e1;
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				logger.error("关闭临时文件异常", e);
			}
		}
	}
	
	private void uploadTraceFile(MyLog myLog, String remoteFile, String localFile) throws BocmTradeExecuteException{
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpPut ftpPut = null;
		try {
			localFile = localFile +"/"+remoteFile;
			logger.info(localFile);
			ftpPut = new FtpPut(localFile,remoteFile, configSet);
			String result = ftpPut.doPutFile();
//			if (!result) {
//				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
//				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012);
//				throw e;
//			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "上传成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "上传失败！", e);
			BocmTradeExecuteException e1 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10012,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpPut != null) {
				ftpPut.close();
			}
		}
	}
	
	private String transRepTrace(BocmSndTraceQueryModel model){
		QR_TraceDto trace = new QR_TraceDto();
		trace.setSource(model.getSourceType());
		trace.setPlatDate(model.getPlatDate()+"");
		trace.setPlatTrace(model.getPlatTrace()+"");
		trace.setHostDate(model.getHostDate()+"");
		trace.setHostTrace(model.getHostTraceno()+"");
		trace.setTxDate(model.getTxDate()+"");
		trace.setSndBank(model.getSndBankno());
		trace.setTxBranch(model.getTxBranch());
		trace.setTxTel(model.getTxTel());
//		trace.setTxMod(model.gett);
		trace.setTxAmt(model.getTxAmt()+"");
//		trace.setProxyFee(model.getpro);
//		trace.setProxyFlag(model.getPrint());
		trace.setFee(model.getFee()+"");
		trace.setPayerBank(model.getPayerBank());
		trace.setPayerAcno(model.getPayerAcno());
		trace.setPayerName(model.getPayerName());
		trace.setPayeeBank(model.getPayeeBank());
		trace.setPayeeAcno(model.getPayeeAcno());
		trace.setPayeeName(model.getPayeeName());
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(trace,"|",ServerInitializer.CODING));
		return fixPack.toString();
	}
}
