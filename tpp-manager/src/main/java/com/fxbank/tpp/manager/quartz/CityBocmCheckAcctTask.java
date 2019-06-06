/**   
* @Title: CityBocmCheckAcctTask.java 
* @Package com.fxbank.tpp.manager.quartz 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月24日 上午9:57:05 
* @version V1.0   
*/
package com.fxbank.tpp.manager.quartz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10103;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: CityBocmCheckAcctTask 
* @Description: 渠道与交行对账定时任务
* @author YePuLiang
* @date 2019年5月24日 上午9:57:05 
*  
*/
@Configuration
@Component
@EnableScheduling
public class CityBocmCheckAcctTask {
	private static Logger logger = LoggerFactory.getLogger(CityHostCheckAcctTasK.class);

	private static final String JOBNAME = "CityBocmCheckAcct";

	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IBocmDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private IBocmSndTraceService sndTraceService;
	
	@Reference(version = "1.0.0")
	private IBocmRcvTraceService rcvTraceService;

	@Reference(version = "1.0.0")
	private IBocmAcctCheckErrService acctCheckErrService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "bocm_common.";
	
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
		//TODO  调试期间用当天后期修改
//		cal.add(Calendar.DATE, -1);  //减1天
		
		Integer date = Integer.parseInt(df.format(cal.getTime()));
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		
		acctCheckErrService.delete(date.toString());
		
		//获取交行对账文件
		myLog.info(logger, "获取交行对账文件");
		REQ_10103 req10103 = null;
		REP_10103 rep10103 = null;
		
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSysDate(date);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		req10103 = new REQ_10103(myLog, date, sysTime, sysTraceno);
		req10103 = setBankno(myLog, date, sysTime, sysTraceno, txBrno, req10103);//设置报文头中的行号信息
		req10103.setFilNam("BUPS"+req10103.getSbnkNo()+date+".dat");	
		
		rep10103 = forwardToBocmService.sendToBocm(req10103, 
				REP_10103.class);		
		
		//对账文件长度
		int filLen = 0;
		//交易笔数
		int tolCnt = 0;
		//对账文件明细
		String filTxt = "";

		//获取对账文件
		filLen = rep10103.getFilLen();
		tolCnt = rep10103.getTolCnt();
		Double tolAmt = rep10103.getTolAmt();
		List<REP_10103.Detail> tradList = rep10103.getFilTxt();
		
		//拆分对账文件与渠道对账		
		for(REP_10103.Detail bocmTrace : tradList){
			String bocmTraceno = bocmTrace.getTlogNo();
			String bocmDate = bocmTrace.getTactDt();
			//交易业务码
			String thdCod = bocmTrace.getThdCod();
			String txnMod = bocmTrace.getTxnMod();
			//通存通兑业务模式 0现金 1转账
			String source = getTraceSrc(thdCod,txnMod);
			if("RCV".equals(source)){
				//根据交行对账数据取渠道来账数据
				BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getBocmRcvTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,Integer.parseInt(bocmDate),bocmTraceno);
				checkBocmRcvLog(myLog, sysDate, sysTime , rcvTraceQueryModel, bocmTrace, bocmDate);
				
			}
			if("SND".equals(source)){
				//根据交行核心对账数据取渠道往账数据
				BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getBocmSndTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,Integer.parseInt(bocmDate),bocmTraceno);
				
				checkBocmSndLog(myLog, sysDate, sysTime , sndTraceQueryModel, bocmTrace, bocmDate);
			}
			if(source==null){
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013,"交行交易码不存在");
				throw e;
			}

		}

		
		myLog.info(logger, "交行与外围对账结束");
		
		
//		int sndTotal = Integer.parseInt(sndCheckFlag2)+Integer.parseInt(sndCheckFlag3)+Integer.parseInt(sndCheckFlag4);
//		String s = "柜面通【"+date+"】对账统计：来账共【"+rcvTotal+"】笔，其中已对账【"+rcvCheckFlag2+"】笔，核心多出【"+rcvCheckFlag3+"】笔，渠道多出【"+rcvCheckFlag4+"】笔;"
//				+ "往账共【"+sndTotal+"】笔，其中已对账【"+sndCheckFlag2+"】笔，核心多出【"+sndCheckFlag3+"】笔，渠道多出【"+sndCheckFlag4+"】笔";

		myLog.info(logger, "交行与外围对账成功");
	}
	
	public REQ_10103 setBankno(MyLog myLog, int sysDate, int sysTime, int sysTraceno, String branchId, REQ_10103 reqBase) throws SysTradeExecuteException {
		if(branchId == null){
			myLog.error(logger, "发起机构号不能为空");
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger,e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		}
		
		
		//行号查询
		ESB_REQ_30043003001 esbReq_30043003001 = new ESB_REQ_30043003001(myLog, sysDate, sysTime,
				sysTraceno);
//		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043003001.getReqSysHead(), dto).build();
//		reqSysHead.setBranchId(branchId);
		esbReq_30043003001.getReqSysHead().setBranchId(branchId);
//		esbReq_30043003001.setReqSysHead(reqSysHead);
		ESB_REQ_30043003001.REQ_BODY reqBody_30043003001 = esbReq_30043003001.getReqBody();
		reqBody_30043003001.setBrchNoT4(branchId);
		
		
		/**
		
		myLog.info(logger, "TradeBase通过本行机构号查询人行行号");
		ESB_REP_30043003001 esbRep_30043003001 = forwardToESBService.sendToESB(esbReq_30043003001, reqBody_30043003001,				
				ESB_REP_30043003001.class);
		//发起行人行行号
		String BANK_NUMBER = esbRep_30043003001.getRepBody().getBankNumber();
		//人行清算行号
		String SETTLEMENT_BANK_NO = esbRep_30043003001.getRepBody().getSettlementBankNo();
		// SBnkNo指合作银行总行行号（12位） RBnkNo指合作银行发起交易网点行号
		// 交通银行发起交易时，SBnkNo指交行发起交易网点号（12位）     RBnkNo指合作银行总行行号
		//注意：合作银行发起交易时，SBnkNo指合作银行总行行号（12位）
        //                 RBnkNo指合作银行发起交易网点行号
		//发起行行号
		reqHeader.setsBnkNo(SETTLEMENT_BANK_NO); // 总行 取上面接口返回值
		//接收行行号 办理业务网点的总行行号
		reqHeader.setrBnkNo(BANK_NUMBER); // 网点 取上面接口返回值
		
		*/
		
//		//发起行行号  313229000660
		reqBase.setSbnkNo("313229000660"); // 总行 取上面接口返回值
//		//接收行行号 办理业务网点的总行行号
		reqBase.setRbnkNo("313229000660"); // 网点 取上面接口返回值
		return reqBase;
	}
	
	private String getTraceSrc(String thdCod, String txnMod){
		//来账
		String RCV = "RCV";
		//往账
		String SND = "SND";
		//核对来账
		//磁条卡通存
		if("10000".equals(thdCod)){
			return RCV;
		}
		//IC卡通存
		if("20000".equals(thdCod)){
			return RCV;
		}
		//磁条卡现金通兑
		if("10001".equals(thdCod)&&"0".equals(txnMod)){
			return RCV;
		}
		//IC卡现金通兑
		if("20001".equals(thdCod)&&"0".equals(txnMod)){
			return RCV;
		}
		
		//核对往账
		//磁条卡现金通兑
		if("10001".equals(thdCod)&&"1".equals(txnMod)){
			return SND;
		}
		//IC卡现金通兑
		if("20001".equals(thdCod)&&"1".equals(txnMod)){
			return SND;
		}
		return null;
	}
	
	
	private void checkBocmRcvLog(MyLog myLog,int sysDate,int sysTime,BocmRcvTraceQueryModel rcvTraceQueryModel,
			REP_10103.Detail bocmTrace,String date) throws SysTradeExecuteException{
		
		String bocmTraceno = bocmTrace.getTlogNo();
		int platTraceno = Integer.parseInt(bocmTrace.getLogNo());
		int platDate = Integer.parseInt(bocmTrace.getTactDt());
		Double txAmt = bocmTrace.getTxnAmt();
		//若渠道缺少数据则报错
		if(rcvTraceQueryModel == null) {
			BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, platDate, sysTime, platTraceno);
			aceModel.setPlatDate(platDate);
			aceModel.setPlatTrace(platTraceno);
			aceModel.setPreHostState("");
			aceModel.setReHostState("1");
			aceModel.setDcFlag("");
			aceModel.setCheckFlag("3");
			aceModel.setDirection("I");
			aceModel.setTxAmt(new BigDecimal(txAmt.toString()));
			aceModel.setMsg("渠道补充来账数据，渠道日期【"+platDate+"】，渠道流水【"+platTraceno+"】");
			acctCheckErrService.insert(aceModel);		
			myLog.error(logger, "柜面通【"+sysDate+"】来帐对账失败,渠道数据丢失: 交行流水号【"+bocmTraceno+"】核心日期为【"+platDate+"】");
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
			throw e;
		}else {
			//渠道记录的交行记账状态
			String bocmState = rcvTraceQueryModel.getBocmState(); 
			if(bocmState.equals("1")) {
				//交行 状态与渠道状态一致  更新对账状态
				BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
				record.setCheckFlag("2");
				rcvTraceService.rcvTraceUpd(record);
			}else if(bocmState.equals("0")||bocmState.equals("3")||bocmState.equals("5")||bocmState.equals("6")) {
				//交行记账成功，渠道状态为超时、冲正超时、冲正失败，修改交行记账状态为成功
				BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
				record.setBocmState("1");
				record.setCheckFlag("2");
				rcvTraceService.rcvTraceUpd(record);
				myLog.info(logger,"渠道调整来账数据交行记账状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+bocmState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");

				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
				aceModel.setPlatDate(platDate);
				aceModel.setPlatTrace(platTraceno);
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
				aceModel.setMsg("渠道调整来账数据核心状态，渠道日期【"+rcvTraceQueryModel.getPlatDate()+"】，渠道流水【"+rcvTraceQueryModel.getPlatTrace()+"】，调整前状态【"+bocmState+"】，调整后状态【1】，通存通兑标志【"+rcvTraceQueryModel.getDcFlag()+"】");
				acctCheckErrService.insert(aceModel);
			}else {
				////交行核心记账成功，渠道交行记账状态为2.失败、4.冲正成功 则对账失败
				myLog.error(logger, "柜面通【"+sysDate+"】对账失败: 渠道流水号【"+rcvTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+rcvTraceQueryModel.getHostState()+"】,与交行记账状态不符");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}
		}
	}
	
	private void checkBocmSndLog(MyLog myLog,int sysDate,int sysTime,BocmSndTraceQueryModel sndTraceQueryModel,
			REP_10103.Detail bocmTrace,String date) throws SysTradeExecuteException {
		
		String bocmTraceno = bocmTrace.getTlogNo();
		int platTraceno = Integer.parseInt(bocmTrace.getLogNo());
		int platDate = Integer.parseInt(bocmTrace.getTactDt());
		Double txAmt = bocmTrace.getTxnAmt();
		
		//若渠道缺少数据则报错
		if(sndTraceQueryModel == null) {
			BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, sysDate, sysTime, platTraceno);
			aceModel.setPlatDate(sysDate);
			aceModel.setPlatTrace(platTraceno);
			aceModel.setPreHostState("");
			aceModel.setReHostState("1");
			aceModel.setDcFlag("");
			aceModel.setCheckFlag("3");
			aceModel.setDirection("O");
			aceModel.setMsg("渠道补充往账数据，渠道日期【"+sysDate+"】，渠道流水【"+platTraceno+"】");
			acctCheckErrService.insert(aceModel);
			
			myLog.error(logger, "柜面通【"+date+"】往帐对账失败,渠道数据丢失: 交行流水号【"+bocmTraceno+"】交行记账日期为【"+sysDate+"】");
			BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
			throw e;
		}else {
			String dcFlag = sndTraceQueryModel.getDcFlag();//通存通兑标志
			String bocmState = sndTraceQueryModel.getBocmState(); //渠道记录的核心状态
			
			if(bocmState.equals("1")) {
				//核心与渠道状态一致
				BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
				record.setCheckFlag("2");
				sndTraceService.sndTraceUpd(record);
			}else if(bocmState.equals("0")||bocmState.equals("3")||bocmState.equals("5")||bocmState.equals("6")) {
				//交行核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
				BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
				record.setHostState("1");
				record.setCheckFlag("2");
				sndTraceService.sndTraceUpd(record);
				myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+bocmState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
			
				BocmAcctCheckErrModel aceModel = new BocmAcctCheckErrModel(myLog, platDate, sysTime, platTraceno);
				aceModel.setPlatDate(platDate);
				aceModel.setPlatTrace(platTraceno);
				aceModel.setPreHostState(bocmState);
				aceModel.setReHostState("1");
				aceModel.setDcFlag(dcFlag);
				aceModel.setCheckFlag("2");
				aceModel.setDirection("O");
				aceModel.setTxAmt(sndTraceQueryModel.getTxAmt());
				aceModel.setPayeeAcno(sndTraceQueryModel.getPayeeAcno());
				aceModel.setPayeeName(sndTraceQueryModel.getPayeeName());
				aceModel.setPayerAcno(sndTraceQueryModel.getPayerAcno());
				aceModel.setPayerName(sndTraceQueryModel.getPayerName());
				aceModel.setMsg("渠道调整往账数据交行核心状态，渠道日期【"+sndTraceQueryModel.getPlatDate()+"】，渠道流水【"+sndTraceQueryModel.getPlatTrace()+"】，调整前状态【"+bocmState+"】，调整后状态【1】，通存通兑标志【"+dcFlag+"】");
				acctCheckErrService.insert(aceModel);
			}else {
				myLog.error(logger, "柜面通【"+date+"】往帐对账失败: 渠道流水号【"+sndTraceQueryModel.getPlatTrace()+"】记录核心状态为【"+sndTraceQueryModel.getHostState()+"】,与交行记账状态不符");
				BocmTradeExecuteException e = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10013);
				throw e;
			}

		}
	}

	
	@Bean(name = "cityBocmCheckAcctJobDetail")
	public MethodInvokingJobDetailFactoryBean cityCheckAcctJobDetail(CityBocmCheckAcctTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cityBocmCheckAcctJobTrigger")
	public CronTriggerFactoryBean cityCheckAcctJobTrigger(
			@Qualifier("cityBocmCheckAcctJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
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

	@Bean(name = "cityBocmCheckAcctScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cityBocmCheckAcctJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
