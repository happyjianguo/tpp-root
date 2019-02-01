package com.fxbank.tpp.tcex.trade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;

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
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TCHK01;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TCHK01;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_30042001701;
import com.fxbank.tpp.tcex.dto.esb.REQ_30042001701;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.service.IDayCheckLogService;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
import com.fxbank.tpp.tcex.service.ISndTraceService;
import com.fxbank.tpp.tcex.utils.FtpUtil;

import redis.clients.jedis.Jedis;
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
	
	@Reference(version = "1.0.0")
	private IDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private ISndTraceService sndTraceService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "tcex_common.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_30042001701 reqDto = (REQ_30042001701)dto;
		//对账日期
		String date = reqDto.getReqBody().getCollateDt();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();
				
		REP_30042001701 repDto = new REP_30042001701();
		
		//核对来账
		List<DayCheckLogInitModel> rcvDayCheckLogList = getCheckLogList(myLog, date, txBrno, txTel, dto, "I");
		checkRcvLog(myLog, dto, rcvDayCheckLogList, date);
		
		//核对往帐
		List<DayCheckLogInitModel> SndDayCheckLogList = getCheckLogList(myLog, date, txBrno, txTel, dto, "O");
		checkSndLog(myLog, dto, SndDayCheckLogList, date);
		
		//获取未对账的来账信息
		//TODO 对账逻辑需要调整，渠道多记录时，应调整chestate状态     加上状态3-核心比渠道多  4-渠道比核心多
		List<RcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		//失败、超时、存款确认、冲正成功？
		for(RcvTraceQueryModel model : rcvTraceList) {
			if(!model.getHostState().equals("2")&&!model.getHostState().equals("3")&&!model.getHostState().equals("4")&&!model.getHostState().equals("5")) {
				System.out.println("柜面通【"+date+"】对账失败: 多出来账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
				myLog.error(logger,"柜面通【"+date+"】对账失败: 多出来账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
			}
		}
		
		//获取未对账的往帐信息
		List<SndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(SndTraceQueryModel model:sndTraceList) {
			if(model.getDcFlag().equals("0")) {
				//通存
				//失败、超时、存款确认、冲正成功？
				if(!model.getHostState().equals("2")&&!model.getHostState().equals("3")&&!model.getHostState().equals("4")&&!model.getHostState().equals("5")) {
					System.out.println("柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
					myLog.error(logger,"柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}else if(model.getDcFlag().equals("1")) {
				//通兑
				//失败、超时
				if(!model.getHostState().equals("0")&&!model.getHostState().equals("2")&&!model.getHostState().equals("3")) {
					System.out.println("柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
					myLog.error(logger,"柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】核心状态【"+model.getHostState()+"】异常");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}else {
				System.out.println("柜面通【"+date+"】往帐对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】通存通兑标志状态异常:【"+model.getDcFlag()+"】");
				myLog.error(logger,"柜面通【"+date+"】往帐对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】通存通兑标志状态异常:【"+model.getDcFlag()+"】");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		//来账通存I0、来账通兑I1、往账通存O0、往账通兑O1（第一位是大写字母，I代表来账、O代表往账；第二位是数字，0代表通存、1代表通兑）
		String rcvDeTotalNum = rcvTraceService.getRcvTotalNum(myLog, date, "0");
		String rcvDeTotalSum = rcvTraceService.getRcvTotalSum(myLog, date, "0");
		String rcvExTotalNum = rcvTraceService.getRcvTotalNum(myLog, date, "1");
		String rcvExTotalSum = rcvTraceService.getRcvTotalSum(myLog, date, "1");
		String sndDeTotalNum = sndTraceService.getSndTotalNum(myLog, date, "0");
		String sndDeTotalSum = sndTraceService.getSndTotalSum(myLog, date, "0");
		String sndExTotalNum = sndTraceService.getSndTotalNum(myLog, date, "1");
		String sndExTotalSum = sndTraceService.getSndTotalSum(myLog, date, "1");
		sb.append("I0").append("|");
		sb.append(rcvDeTotalNum).append("|");
		sb.append(null==rcvDeTotalSum?"0":rcvDeTotalSum).append("|");
		sb.append("\n"); 
		sb.append("I1").append("|");
		sb.append(rcvExTotalNum).append("|");
		sb.append(null==rcvExTotalSum?"0":rcvExTotalSum).append("|");
		sb.append("\n"); 
		sb.append("O0").append("|");
		sb.append(sndDeTotalNum).append("|");
		sb.append(null==sndDeTotalSum?"0":sndDeTotalSum).append("|");
		sb.append("\n"); 
		sb.append("O1").append("|");
		sb.append(sndExTotalNum).append("|");
		sb.append(null==sndExTotalSum?"0":sndExTotalSum).append("|");
		sb.append("\n"); 
		List<RcvTraceQueryModel> upRcvTraceList = rcvTraceService.getUploadCheckRcvTrace(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(RcvTraceQueryModel model : upRcvTraceList) {
			sb.append(model.getPlatDate()).append("|"); //平台日期
			sb.append(model.getPlatTrace()).append("|"); //平台流水
			sb.append(model.getDcFlag()).append("|"); //通存通兑标志
			sb.append("I").append("|");//来账标志
			sb.append(model.getSourceType()).append("|");//交易渠道
			sb.append(model.getTownBranch()).append("|");//村镇记账机构
			sb.append(model.getTxAmt()).append("|");//交易金额
			sb.append(model.getTxInd()).append("|");//现转标志
			sb.append(model.getTownDate()).append("|"); //村镇日期
			sb.append(model.getTownTraceno()).append("|");//村镇流水
			sb.append(null==model.getPayeeAcno()?"":model.getPayeeAcno()).append("|"); //收款人账号
			sb.append(null==model.getPayeeName()?"":model.getPayeeName()).append("|"); //收款人户名
			sb.append(null==model.getPayerAcno()?"":model.getPayerAcno()).append("|"); //付款人账号
			sb.append(null==model.getPayerName()?"":model.getPayerName()).append("|"); //付款人户名
			sb.append(model.getTownFlag()).append("|"); //村镇机构
			sb.append(null==model.getInfo()?"":model.getInfo()).append("|"); //摘要
			sb.append("\n"); 
		}
		
		List<SndTraceQueryModel> upSndTraceList = sndTraceService.getUploadCheckSndTrace(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(SndTraceQueryModel model : upSndTraceList) {
			sb.append(model.getPlatDate()).append("|"); //平台日期
			sb.append(model.getPlatTrace()).append("|"); //平台流水
			sb.append(model.getDcFlag()).append("|"); //通存通兑标志
			sb.append("O").append("|");//往账标志
			sb.append(model.getSourceType()).append("|");//交易渠道
			sb.append(model.getTownBranch()).append("|");//村镇记账机构
			sb.append(model.getTxAmt()).append("|");//交易金额
			sb.append(model.getTxInd()).append("|");//现转标志
			sb.append(model.getTownDate()).append("|"); //村镇日期
			sb.append(model.getTownTraceno()).append("|");//村镇流水
			sb.append(null==model.getPayeeAcno()?"":model.getPayeeAcno()).append("|"); //收款人账号
			sb.append(null==model.getPayeeName()?"":model.getPayeeName()).append("|"); //收款人户名
			sb.append(null==model.getPayerAcno()?"":model.getPayerAcno()).append("|"); //付款人账号
			sb.append(null==model.getPayerName()?"":model.getPayerName()).append("|"); //付款人户名
			sb.append(model.getTownFlag()).append("|"); //村镇机构
			sb.append(null==model.getInfo()?"":model.getInfo()).append("|"); //摘要
			sb.append("\n"); 
		}
		
		//生成本地对账文件并上传至FTP服务器
		String fileName="CheckTrace_"+date+dto.getSysTime()+".txt";
		String localFile = ftpUpload(myLog, fileName, sb.toString());
		
		//调用村镇接口，通知村镇对账
		ESB_REQ_TCHK01 esbReq_tchk01 = new ESB_REQ_TCHK01(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		esbReq_tchk01.getReqSysHead().setFilePath(localFile);
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_tchk01.getReqSysHead(),dto).setBranchId(txBrno).setUserId(txTel).build();
		esbReq_tchk01.setReqSysHead(reqSysHead);
		ESB_REQ_TCHK01.REQ_BODY esbReqBody_tchk01 = esbReq_tchk01.getReqBody();
		esbReqBody_tchk01.setFileName(fileName);
		esbReqBody_tchk01.setCollectDt(date);
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
	

	private String ftpUpload(MyLog myLog,String fileName,String str) throws TcexTradeExecuteException {
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
			System.out.println("生成村镇对账文件失败: "+e);
			myLog.error(logger,"生成村镇对账文件失败: "+e);
			TcexTradeExecuteException e1 = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
			throw e1;
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				logger.error("关闭临时文件异常", e);
			}
		}
		
		FtpUtil ftp = new FtpUtil();
		try {
			String host, port, user, password;
			try (Jedis jedis = myJedis.connect()) {
				host = jedis.get(COMMON_PREFIX+"ftp_ip");
				port = jedis.get(COMMON_PREFIX+"ftp_port");
				user = jedis.get(COMMON_PREFIX+"ftp_user");
				password = jedis.get(COMMON_PREFIX+"ftp_pass");
			}
			ftp.connect(host, Integer.parseInt(port), user, password);
			ftp.upload(fileName, file);
		}catch (Exception e) {
			System.out.println("FTP上传村镇对账文件失败: "+e);
			myLog.error(logger,"FTP上传村镇对账文件失败: "+e);
			TcexTradeExecuteException e1 = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
			throw e1;
		}finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP异常", e);
			}
		}
		
		return localPath+File.separator+fileName;
	}
	
	private void checkSndLog(MyLog myLog, DataTransObject dto, List<DayCheckLogInitModel> sndDayCheckLogList,
			String date) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:sndDayCheckLogList) {
			//根据核心对账数据取渠道往账数据
			SndTraceQueryModel sndTraceQueryModel = sndTraceService.getSndTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则补充
			if(sndTraceQueryModel == null) {
				SndTraceRepModel record = new SndTraceRepModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				record.setHostDate(model.getHostDate());
				record.setHostTraceno(model.getHostTraceno());
				record.setTxAmt(model.getTxAmt().toString());
				record.setHostState(model.getReversal().equals("Y")?"5":"1");
				record.setCheckFlag("2");
				
				sndTraceService.replenishSndTrace(record);
			}else {
				String dcFlag = sndTraceQueryModel.getDcFlag();//通存通兑标志
				String hostState = sndTraceQueryModel.getHostState(); //渠道记录的核心状态
				
				if(dcFlag.equals("0")) {
					//通存
					if(hostState.equals("1")) {
						//核心与渠道状态一致
						SndTraceUpdModel record = new SndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
					}else if(hostState.equals("3")||hostState.equals("5")||hostState.equals("6")||hostState.equals("7")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						rcvTraceService.rcvTraceUpd(record);
					}else {
						System.out.println("柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
						throw e;
					}
				}else if(dcFlag.equals("1")) {
					//通兑
					if(hostState.equals("1")) {
						//核心与渠道状态一致
						SndTraceUpdModel record = new SndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setCheckFlag("2");
						sndTraceService.sndTraceUpd(record);
					}else if(hostState.equals("3")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						rcvTraceService.rcvTraceUpd(record);
					}else {
						System.out.println("柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
						throw e;
					}
				}else {
					System.out.println("柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】通存通兑标志状态异常:【"+sndTraceQueryModel.getDcFlag()+"】");
					myLog.error(logger,"柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】通存通兑标志状态异常:【"+sndTraceQueryModel.getDcFlag()+"】");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}
		}
	}
	private void checkRcvLog(MyLog myLog,DataTransObject dto,List<DayCheckLogInitModel> rcvDayCheckLogList,String date) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:rcvDayCheckLogList) {
			//根据核心对账数据取渠道来账数据
			RcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getRcvTraceByKey(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则补充
			if(rcvTraceQueryModel == null) {
				RcvTraceRepModel record = new RcvTraceRepModel(myLog, model.getSettleDate(), dto.getSysTime(), model.getPlatTrace());
				record.setHostDate(model.getHostDate());
				record.setHostTraceno(model.getHostTraceno());
				record.setTxAmt(model.getTxAmt().toString());
				//TODO 待确认
				record.setHostState(model.getReversal().equals("Y")?"5":"1");
				record.setCheckFlag("2");
				
				rcvTraceService.replenishRcvTrace(record);
			}else {
				String hostState = rcvTraceQueryModel.getHostState(); //渠道记录的核心状态
				if(hostState.equals("1")) {
					//核心与渠道状态一致
					RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
				}else if(hostState.equals("3")||hostState.equals("4")||hostState.equals("5")||hostState.equals("6")||hostState.equals("7")) {
					//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
					RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setHostState("1");
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
					//TODO 输出日志 渠道日期 渠道流水 状态
				}else {
					System.out.println("柜面通【"+date+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					myLog.error(logger, "柜面通【"+date+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}
		}
	}
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto,String direction) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,date,txBrno,txTel,dto,direction);
		
		//对账文件入库
		InitCheckLog(localFile,myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(),direction);
		
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate(),direction);
			
		return 	dayCheckLogList;
	}
	
	private void InitCheckLog(String localFile, MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,String direction) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			dayCheckLogService.delete(direction);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"UTF-8"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length<11){
                    myLog.info(logger,"文件【"+localFile+"】内容缺失");
                    continue;
                }

                DayCheckLogInitModel model = new DayCheckLogInitModel(myLog, sysDate,sysTime,sysTraceno);
                model.setPlatDate(sysDate); //渠道日期
                model.setPlatTrace(Integer.parseInt(array[0].substring(14))); //渠道流水
                model.setSettleDate(Integer.parseInt(array[1]));//清算日期
                model.setSettleBranch(array[2]); //清算机构
                model.setHostDate(Integer.parseInt(array[3])); //核心交易日期
                model.setHostTraceno(array[4]); //核心流水号
                model.setCcy(array[5]); //交易币种
                BigDecimal bg = new BigDecimal(array[6]==null?"0":array[6]);
                model.setTxAmt(bg); //交易金额
                model.setAccountno(array[7]); //交易账户 
                model.setReversal(array[8]); //冲正标志
                model.setTxStatus(array[9]); //交易状态
                model.setDirection(direction); //来往账标识
                
        		dayCheckLogService.dayCheckLogInit(model);
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "核心对账信息入库结束");
		}
		
		
	}
	
	private String getEsbCheckFile(MyLog myLog, String date, String txBrno, String txTel,DataTransObject dto,String direction) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),dto).setBranchId(txBrno).setUserId(txTel).setSourceType("LV").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("LV");
		esbReqBody_50015000101.setStartDate(date);
		esbReqBody_50015000101.setEndDate(date);
		esbReqBody_50015000101.setDirection(direction);
		
		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101, esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+direction+"_"+fileName);
		return localPath+File.separator+direction+"_"+fileName;
	}
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		System.out.println("remoteFile  "+remoteFile);
		System.out.println("localFile  "+localFile);
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
