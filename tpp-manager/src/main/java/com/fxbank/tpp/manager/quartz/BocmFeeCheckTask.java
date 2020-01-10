package com.fxbank.tpp.manager.quartz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10103_FEE;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.service.IBocmAcctCheckErrService;
import com.fxbank.tpp.bocm.service.IBocmChkStatusService;
import com.fxbank.tpp.bocm.service.IBocmDayCheckLogService;
import com.fxbank.tpp.bocm.service.IBocmRcvTraceService;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: BocmFeeCheckTask 
* @Description: 交行手续费获取定时
* @author YePuLiang
* @date 2019年7月16日 上午10:32:48 
*  
*/
@Configuration
@Component
@EnableScheduling
public class BocmFeeCheckTask {
	private static Logger logger = LoggerFactory.getLogger(BocmFeeCheckTask.class);

	private static final String JOBNAME = "BocmFeeCheck";

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
	
	@Reference(version = "1.0.0")
	private IBocmChkStatusService chkStatusService;
	
	private String txDate = "";
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "bocm.";
	
	public void exec() throws Exception {
		MyLog myLog = new MyLog();				
		myLog.info(logger, "获取交行记录手续费对账文件信息");
		txDate = publicService.getSysDate("CIP")+"";
		//String settlementDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		myLog.info(logger, "对账日期："+txDate);
		Integer date = Integer.parseInt(txDate);
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		Integer sysDate = date;
		
		//阜新银行总行行号
		String FXNO = "";
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			FXNO = jedis.get(COMMON_PREFIX+"FXNO");
        }
				
		REQ_10103 req10103 = null;
		REP_10103_FEE rep10103 = null;
		
		req10103 = new REQ_10103(myLog, date, sysTime, sysTraceno);
		req10103.setSbnkNo(FXNO);
		req10103.setRbnkNo(FXNO);
		
		req10103.setFilNam("BFEE"+FXNO+date+".dat");	
		//获取交行对账文件
		myLog.info(logger, "获取交行手续费对账文件");
		//获取交行交易流水信息
		rep10103 = forwardToBocmService.sendToBocm(req10103, 
				REP_10103_FEE.class);	
		
		List<REP_10103_FEE.Detail> tradList = rep10103.getFilTxt();
		//拆分对账文件与渠道对账		
		for(REP_10103_FEE.Detail bocmTrace : tradList){
			//获取交行交易流水号
			String bocmTraceno = bocmTrace.getTlogNo();		
			//交易业务码
			String thdCod = bocmTrace.getThdCod();
			//通存通兑业务模式 0现金 1转账
			String txnMod = bocmTrace.getTxnMod();
			//交易发起行行号
			String SbnkNo = bocmTrace.getSbnkNo();			
			myLog.info(logger, "手续费流水获取,交行流水号【"+bocmTraceno+"】发起行行号【"+SbnkNo+"】交易代码【"+thdCod+"】业务模式【"+txnMod+"】");
			//规则：交易受理方收取代理手续费  0 （交行支付代理手续费）1 （交行收取代理手续费）
			String proxy_flag = bocmTrace.getProxyFlg();
			//代理手续费
			String proxyFee = NumberUtil.removePointToString(bocmTrace.getProxyFee());
			//交行对账文件客户手续费收取方式
			String bocmFeeFlag = bocmTrace.getFeeFlg();
			//交行对账文件客户手续费
			String bocmFee = NumberUtil.removePointToString(bocmTrace.getFee());
			if(proxy_flag.equals("0")){
				myLog.info(logger, "交行支付代理手续费："+bocmTrace.getProxyFee()+"转换后： "+proxyFee);
			}
			if(proxy_flag.equals("1")){
				myLog.info(logger, "交行收取代理手续费："+bocmTrace.getProxyFee()+"转换后： "+proxyFee);
			}
			//判断交易发起方人行行号，如果为本行行号说明本条对账文件对应的我方往账记录
			if(SbnkNo.substring(0, 3).equals("313")){
				//根据交行核心对账数据取渠道往账数据
				BocmSndTraceQueryModel sndTraceQueryModel = sndTraceService.getBocmSndTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,bocmTraceno);
				if(sndTraceQueryModel!=null){
					BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, sndTraceQueryModel.getPlatDate(), 
							sndTraceQueryModel.getPlatTime(), sndTraceQueryModel.getPlatTrace());
					record.setBocmFeeFlag(bocmFeeFlag);
					record.setBocmFee(new BigDecimal(bocmFee));
					record.setProxyFlag(proxy_flag);
					record.setProxyFee(new BigDecimal(proxyFee));
					sndTraceService.sndTraceUpd(record);
				}
			}else if(SbnkNo.substring(0, 3).equals("301")){
				//判断交易发起方人行行号，如果不是本行行号说明本条对账文件对应的我方来账记录
				//根据交行对账数据取渠道来账数据
				BocmRcvTraceQueryModel rcvTraceQueryModel = rcvTraceService.getBocmRcvTraceByKey(myLog, sysTime, 
						sysTraceno, sysDate,bocmTraceno);	
				if(rcvTraceQueryModel!=null){
					BocmRcvTraceUpdModel record = new BocmRcvTraceUpdModel(myLog, rcvTraceQueryModel.getPlatDate(), 
							rcvTraceQueryModel.getPlatTime(), rcvTraceQueryModel.getPlatTrace());
					record.setBocmFeeFlag(bocmFeeFlag);
					record.setBocmFee(new BigDecimal(bocmFee));
					record.setProxyFlag(proxy_flag);
					record.setProxyFee(new BigDecimal(proxyFee));
					rcvTraceService.rcvTraceUpd(record);
				}
			}
		}

	}
	
	@Bean(name = "bocmFeeCheckJobDetail")
	public MethodInvokingJobDetailFactoryBean cityCheckAcctJobDetail(BocmFeeCheckTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "bocmFeeCheckJobTrigger")
	public CronTriggerFactoryBean cityCheckAcctJobTrigger(
			@Qualifier("bocmFeeCheckJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.TPP_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 35 18 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "bocmFeeCheckScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("bocmFeeCheckJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
