package com.fxbank.tpp.manager.quartz;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.tpp.esb.model.tcex.ESB_REP_TCHK01;
import com.fxbank.tpp.esb.model.tcex.ESB_REQ_TCHK01;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.manager.utils.FtpUtil;
import com.fxbank.tpp.tcex.exception.TcexTradeExecuteException;
import com.fxbank.tpp.tcex.model.AcctCheckErrModel;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.service.IAcctCheckErrService;
import com.fxbank.tpp.tcex.service.IDayCheckLogService;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
import com.fxbank.tpp.tcex.service.ISndTraceService;

import redis.clients.jedis.Jedis;

@Configuration
@Component
@EnableScheduling
public class CityCheckAcctTask {
	private static Logger logger = LoggerFactory.getLogger(CityCheckAcctTask.class);

	private static final String JOBNAME = "CityCheckAcct";

	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
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

	@Reference(version = "1.0.0")
	private IAcctCheckErrService acctCheckErrService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "tcex_common.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try(Jedis jedis = myJedis.connect()){
			txBrno = jedis.get(COMMON_PREFIX+"TXBRNO");
			txTel = jedis.get(COMMON_PREFIX+"TXTEL");
        }
				
		myLog.info(logger, "核心与外围对账开始");
		
		Integer sysDate = publicService.getSysDate("CIP");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date d = df.parse(sysDate.toString());     
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, -1);  //减1天
		Integer date = Integer.parseInt(df.format(cal.getTime()));
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		
		acctCheckErrService.delete(date.toString());
		
		//核对来账
		List<DayCheckLogInitModel> rcvDayCheckLogList = getCheckLogList(myLog, date,sysTime,sysTraceno, txBrno, txTel, "I");
		checkRcvLog(myLog, rcvDayCheckLogList, date,sysTime,sysTraceno);
		
		//核对往帐
		List<DayCheckLogInitModel> SndDayCheckLogList = getCheckLogList(myLog, date,sysTime,sysTraceno, txBrno, txTel, "O");
		checkSndLog(myLog, SndDayCheckLogList, date,sysTime,sysTraceno);
		
		myLog.info(logger, "核心与外围对账结束");
		
		
		myLog.info(logger, "外围与核心对账开始");
		//获取未对账的来账信息
		List<RcvTraceQueryModel> rcvTraceList = rcvTraceService.getCheckRcvTrace(myLog,date,sysTime,sysTraceno, date.toString());
		//失败、超时、存款确认、冲正成功？
		for(RcvTraceQueryModel model : rcvTraceList) {
			RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
			record.setCheckFlag("4");
			rcvTraceService.rcvTraceUpd(record);
			
			if(model.getHostState().equals("1")) {
				myLog.error(logger,"柜面通【"+date+"】对账失败: 多出来账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
			}else {
				myLog.info(logger, "渠道多出来账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
			}
		}
		
		//获取未对账的往帐信息
		List<SndTraceQueryModel> sndTraceList = sndTraceService.getCheckSndTrace(myLog,sysDate,sysTime,sysTraceno, date.toString());
		for(SndTraceQueryModel model:sndTraceList) {
			SndTraceUpdModel record = new SndTraceUpdModel(myLog, model.getPlatDate(), model.getPlatTime(), model.getPlatTrace());
			record.setCheckFlag("4");
			sndTraceService.sndTraceUpd(record);
			
			if(model.getHostState().equals("1")) {
				myLog.error(logger,"柜面通【"+date+"】对账失败: 多出往账记录，渠道流水号【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
			}else {
				myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getPlatDate()+"】，渠道流水【"+model.getPlatTrace()+"】，核心状态【"+model.getHostState()+"】，通存通兑标志【"+model.getDcFlag()+"】");
			}
		}
		
		myLog.info(logger, "外围与核心对账结束");
		
		StringBuffer sb = new StringBuffer();
		//来账通存I0、来账通兑I1、往账通存O0、往账通兑O1（第一位是大写字母，I代表来账、O代表往账；第二位是数字，0代表通存、1代表通兑）
		String rcvDeTotalNum = rcvTraceService.getRcvTotalNum(myLog, date.toString(), "0");
		String rcvDeTotalSum = rcvTraceService.getRcvTotalSum(myLog, date.toString(), "0");
		String rcvExTotalNum = rcvTraceService.getRcvTotalNum(myLog, date.toString(), "1");
		String rcvExTotalSum = rcvTraceService.getRcvTotalSum(myLog, date.toString(), "1");
		String sndDeTotalNum = sndTraceService.getSndTotalNum(myLog, date.toString(), "0");
		String sndDeTotalSum = sndTraceService.getSndTotalSum(myLog, date.toString(), "0");
		String sndExTotalNum = sndTraceService.getSndTotalNum(myLog, date.toString(), "1");
		String sndExTotalSum = sndTraceService.getSndTotalSum(myLog, date.toString(), "1");
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
		List<RcvTraceQueryModel> upRcvTraceList = rcvTraceService.getUploadCheckRcvTrace(myLog, date,sysTime,sysTraceno, date.toString());
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
			sb.append(null==model.getInfo()?" ":model.getInfo()).append("|"); //摘要
			sb.append("\n"); 
		}
		
		List<SndTraceQueryModel> upSndTraceList = sndTraceService.getUploadCheckSndTrace(myLog, date,sysTime,sysTraceno, date.toString());
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
			sb.append(null==model.getInfo()?" ":model.getInfo()).append("|"); //摘要
			sb.append("\n"); 
		}
		
		//生成本地对账文件并上传至FTP服务器
		String fileName="CheckTrace_"+date+sysTime+".txt";
		String localFile = ftpUpload(myLog, fileName, sb.toString());
		
		//调用村镇接口，通知村镇对账
		ESB_REQ_TCHK01 esbReq_tchk01 = new ESB_REQ_TCHK01(myLog, date,sysTime,sysTraceno);
		esbReq_tchk01.getReqSysHead().setFilePath(localFile);
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSysDate(date);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		reqDto.setSourceType("LV");
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_tchk01.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).build();
		esbReq_tchk01.setReqSysHead(reqSysHead);
		ESB_REQ_TCHK01.REQ_BODY esbReqBody_tchk01 = esbReq_tchk01.getReqBody();
		esbReqBody_tchk01.setFileName(fileName);
		esbReqBody_tchk01.setCollectDt(date.toString());
		ESB_REP_TCHK01 esbRep_tchk01 = forwardToTownService.sendToTown(esbReq_tchk01, esbReqBody_tchk01, ESB_REP_TCHK01.class);
		if("000000".equals(esbRep_tchk01.getRepSysHead().getRet().get(0).getRetCode())) {
			myLog.info(logger, "柜面通【"+date+"】对账成功");
		}else {
			myLog.error(logger, "柜面通【"+date+"】对账失败: "+esbRep_tchk01.getRepSysHead().getRet().get(0).getRetMsg());
			TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
			throw e;
		}
		
		String rcvCheckFlag2 = rcvTraceService.getTraceNum(date.toString(), "2");
		String rcvCheckFlag3 = rcvTraceService.getTraceNum(date.toString(), "3");
		String rcvCheckFlag4 = rcvTraceService.getTraceNum(date.toString(), "4");
		int rcvTotal = Integer.parseInt(rcvCheckFlag2)+Integer.parseInt(rcvCheckFlag3)+Integer.parseInt(rcvCheckFlag4);
		
		String sndCheckFlag2 = sndTraceService.getTraceNum(date.toString(), "2");
		String sndCheckFlag3 = sndTraceService.getTraceNum(date.toString(), "3");
		String sndCheckFlag4 = sndTraceService.getTraceNum(date.toString(), "4");
		int sndTotal = Integer.parseInt(sndCheckFlag2)+Integer.parseInt(sndCheckFlag3)+Integer.parseInt(sndCheckFlag4);
		String s = "柜面通【"+date+"】对账统计：来账共【"+rcvTotal+"】笔，其中已对账【"+rcvCheckFlag2+"】笔，核心多出【"+rcvCheckFlag3+"】笔，渠道多出【"+rcvCheckFlag4+"】笔;"
				+ "往账共【"+sndTotal+"】笔，其中已对账【"+sndCheckFlag2+"】笔，核心多出【"+sndCheckFlag3+"】笔，渠道多出【"+sndCheckFlag4+"】笔";

		myLog.info(logger, s);
		

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
	
	private String getEsbCheckFile(MyLog myLog, Integer date,Integer sysTime,Integer sysTraceno, String txBrno, String txTel,String direction) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, date,sysTime,sysTraceno);
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSysDate(date);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),reqDto).setBranchId(txBrno).setUserId(txTel).setSourceType("LV").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("LV");
		esbReqBody_50015000101.setStartDate(date.toString());
		esbReqBody_50015000101.setEndDate(date.toString());
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

	
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog, Integer date,Integer sysTime,Integer sysTraceno, String txBrno, String txTel,String direction) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,date,sysTime,sysTraceno,txBrno,txTel,direction);
		
		//对账文件入库
		InitCheckLog(localFile,myLog,date,sysTime,sysTraceno,direction);
		
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, sysTime, sysTraceno, date,direction);
			
		return 	dayCheckLogList;
	}
	
	
	
	private void InitCheckLog(String localFile, MyLog myLog,Integer date,Integer sysTime,Integer sysTraceno, String direction) throws SysTradeExecuteException {
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
                
                DayCheckLogInitModel model = new DayCheckLogInitModel(myLog, date,0,0);
                model.setPlatDate(date); //渠道日期
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
	
	private void checkSndLog(MyLog myLog, List<DayCheckLogInitModel> sndDayCheckLogList,
			Integer date,Integer sysTime,Integer sysTraceno) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:sndDayCheckLogList) {
			//根据核心对账数据取渠道往账数据
			SndTraceQueryModel sndTraceQueryModel = sndTraceService.getSndTraceByKey(myLog, sysTime, sysTraceno, date,model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则补充
			if(sndTraceQueryModel == null) {
				SndTraceRepModel record = new SndTraceRepModel(myLog, model.getSettleDate(), sysTime, model.getPlatTrace());
				
				AcctCheckErrModel aceModel = new AcctCheckErrModel(myLog, model.getSettleDate(), model.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("O");
				aceModel.setMsg("渠道补充往账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				acctCheckErrService.insert(aceModel);
			
				myLog.error(logger, "柜面通【"+date+"】往帐对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
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
					}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("6")||hostState.equals("7")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						rcvTraceService.rcvTraceUpd(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");

						AcctCheckErrModel aceModel = new AcctCheckErrModel(myLog, model.getSettleDate(), model.getSysTime(), model.getPlatTrace());
						aceModel.setPlatDate(model.getSettleDate());
						aceModel.setPlatTrace(model.getPlatTrace());
						aceModel.setPreHostState(hostState);
						aceModel.setReHostState("1");
						aceModel.setDcFlag(dcFlag);
						aceModel.setCheckFlag("2");
						aceModel.setDirection("O");
						aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
						aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
						aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
						aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
						aceModel.setPayerName(sndTraceQueryModel.getPayerName());
						aceModel.setMsg("渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
						acctCheckErrService.insert(aceModel);
					
					}else {
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
					}else if(hostState.equals("0")||hostState.equals("3")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
						record.setHostState("1");
						record.setCheckFlag("2");
						rcvTraceService.rcvTraceUpd(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");

						AcctCheckErrModel aceModel = new AcctCheckErrModel(myLog, model.getSettleDate(), model.getSysTime(), model.getPlatTrace());
						aceModel.setPlatDate(model.getSettleDate());
						aceModel.setPlatTrace(model.getPlatTrace());
						aceModel.setPreHostState(hostState);
						aceModel.setReHostState("1");
						aceModel.setDcFlag(dcFlag);
						aceModel.setCheckFlag("2");
						aceModel.setDirection("O");
						aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
						aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
						aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
						aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
						aceModel.setPayerName(sndTraceQueryModel.getPayerName());
						aceModel.setMsg("渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
						acctCheckErrService.insert(aceModel);
					}else {
						myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与核心记账状态不符");
						TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
						throw e;
					}
				}else {
					myLog.error(logger,"柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】通存通兑标志状态异常:【"+sndTraceQueryModel.getDcFlag()+"】");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}
		}
	}

	private void checkRcvLog(MyLog myLog,List<DayCheckLogInitModel> rcvDayCheckLogList,
			Integer date,Integer sysTime,Integer sysTraceno) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:rcvDayCheckLogList) {
			//根据核心对账数据取渠道来账数据
			RcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getRcvTraceByKey(myLog, sysTime, sysTraceno, date,model.getSettleDate(),model.getPlatTrace());
			//若渠道缺少数据则补充
			if(rcvTraceQueryModel == null) {
				RcvTraceRepModel record = new RcvTraceRepModel(myLog, model.getSettleDate(), sysTime, model.getPlatTrace());
				
				rcvTraceService.replenishRcvTrace(record);
				myLog.info(logger, "渠道补充来账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				
				AcctCheckErrModel aceModel = new AcctCheckErrModel(myLog, model.getSettleDate(), model.getSysTime(), model.getPlatTrace());
				aceModel.setPlatDate(model.getSettleDate());
				aceModel.setPlatTrace(model.getPlatTrace());
				aceModel.setPreHostState("");
				aceModel.setReHostState("1");
				aceModel.setDcFlag("");
				aceModel.setCheckFlag("3");
				aceModel.setDirection("I");
				aceModel.setTxAmt(model.getTxAmt());
				aceModel.setMsg("渠道补充来账数据，渠道日期【"+model.getSettleDate()+"】，渠道流水【"+model.getPlatTrace()+"】");
				acctCheckErrService.insert(aceModel);
			
				myLog.error(logger, "柜面通【"+date+"】来帐对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getSysDate()+"】");
				TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
				throw e;
				
			}else {
				String hostState = rcvTraceQueryModel.getHostState(); //渠道记录的核心状态
				if(hostState.equals("1")) {
					//核心与渠道状态一致
					RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
				}else if(hostState.equals("0")||hostState.equals("3")||hostState.equals("4")||hostState.equals("6")||hostState.equals("7")) {
					//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
					RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setHostState("1");
					record.setCheckFlag("2");
					rcvTraceService.rcvTraceUpd(record);
					myLog.info(logger,"渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");

					AcctCheckErrModel aceModel = new AcctCheckErrModel(myLog, model.getSettleDate(), model.getSysTime(), model.getPlatTrace());
					aceModel.setPlatDate(model.getSettleDate());
					aceModel.setPlatTrace(model.getPlatTrace());
					aceModel.setPreHostState(rcvTraceQueryModel.getHostState());
					aceModel.setReHostState("1");
					aceModel.setDcFlag(rcvTraceQueryModel.getDcFlag());
					aceModel.setCheckFlag("2");
					aceModel.setDirection("I");
					aceModel.setTxAmt(rcvTraceQueryModel.getTxAmt());
					aceModel.setPayeeAcno(rcvTraceQueryModel.getPayeeAcno());
					aceModel.setPayeeName(rcvTraceQueryModel.getPayeeName());
					aceModel.setPayerAcno(rcvTraceQueryModel.getPayerAcno());
					aceModel.setPayerName(rcvTraceQueryModel.getPayerName());
					aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+hostState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
					acctCheckErrService.insert(aceModel);
				}else {
					myLog.error(logger, "柜面通【"+date+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与核心记账状态不符");
					TcexTradeExecuteException e = new TcexTradeExecuteException(TcexTradeExecuteException.TCEX_E_10003);
					throw e;
				}
			}
		}
	}
	
	@Bean(name = "cityCheckAcctJobDetail")
	public MethodInvokingJobDetailFactoryBean cityCheckAcctJobDetail(CityCheckAcctTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cityCheckAcctJobTrigger")
	public CronTriggerFactoryBean cityCheckAcctJobTrigger(
			@Qualifier("cityCheckAcctJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.TPP_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 30 7 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "cityCheckAcctScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cityCheckAcctJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
