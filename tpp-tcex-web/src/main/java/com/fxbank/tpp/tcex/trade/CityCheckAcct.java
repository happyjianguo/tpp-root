package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_30042001701;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042001701;
/**
 * 商行村镇通存通兑业务对账
 * @author liye
 *
 */
@Service("REQ_TCHK01")
public class CityCheckAcct extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityCheckAcct.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30042001701 reqDto = (REQ_30042001701)dto;
		
		//请求核心接口，获取对账文件
		
		//将对账文件上传至村镇ftp服务器
		
		//调用村镇接口，通知村镇对账
		
		REP_30042001701 repDto = new REP_30042001701();
		
		
		
		return repDto;
	}
//	/**
//	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
//	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
//	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
//	 * 返回类型 @throws
//	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
//				PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009);
//				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
//			PafTradeExecuteException e1 = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10009,
//					e.getMessage());
//			throw e1;
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

}
