package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TCHK01;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TSK01;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TCHK01;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TSK01;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30042000307;
import com.fxbank.tpp.tcex.dto.esb.REP_30042001701;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042001701;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
/**
 * 商行村镇通存通兑业务对账
 * @author liye
 *
 */
@Service("REQ_30042001701")
public class CityCheckAcct extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityCheckAcct.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30042001701 reqDto = (REQ_30042001701)dto;
		String date = reqDto.getReqBody().getCollateDt();
		REP_30042001701 repDto = new REP_30042001701();
		
		//请求核心接口，获取对账文件
		
		//将对账文件上传至村镇ftp服务器
		
		//调用村镇接口，通知村镇对账
		ESB_REQ_TCHK01 esbReq_tchk01 = new ESB_REQ_TCHK01(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_tchk01.getReqSysHead(),reqDto).setBranchId("22").setUserId("33").build();
		esbReq_tchk01.setReqSysHead(reqSysHead);
		ESB_REQ_TCHK01.REQ_BODY esbReqBody_tchk01 = esbReq_tchk01.getReqBody();
		esbReqBody_tchk01.setFileName("/a/a.txt");
		ESB_REP_TCHK01 esbRep_tchk01 = forwardToTownService.sendToTown(esbReq_tchk01, esbReqBody_tchk01, ESB_REP_TCHK01.class);
		if("000000".equals(esbRep_tchk01.getRepSysHead().getRet().get(0).getRetCode())) {
			System.out.println("柜面通【"+date+"】对账成功:");
		}else {
			System.out.println("柜面通【\"+date+\"】对账失败: "+esbRep_tchk01.getRepSysHead().getRet().get(0).getRetMsg());
			myLog.error(logger, "柜面通【\"+date+\"】对账失败: "+esbRep_tchk01.getRepSysHead().getRet().get(0).getRetMsg());
			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
			throw e;
		}
		
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
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10009);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			TcexTradeExecuteException e1 = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10009,
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

}
