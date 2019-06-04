/**   
* @Title: ApplyWorkKeyTask.java 
* @Package com.fxbank.tpp.manager.quartz 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月26日 下午8:30:04 
* @version V1.0   
*/
package com.fxbank.tpp.manager.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.fxbank.tpp.bocm.model.REP_10104;
import com.fxbank.tpp.bocm.model.REQ_10104;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: ApplyWorkKeyTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月26日 下午8:30:04 
*  
*/
@Configuration
@Component
@EnableScheduling
public class ApplyWorkKeyTask {
	
	private static Logger logger = LoggerFactory.getLogger(ApplyWorkKeyTask.class);

	private static final String JOBNAME = "ApplyWorkKey";
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	private final static String COMMON_PREFIX = "bocm_common.";
	
	@Resource
	private MyJedis myJedis;
	
	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		myLog.info(logger, "密钥定时更新");
		
		
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
		
		
		REQ_10104 req10104 = new REQ_10104(myLog, date, sysTime, sysTraceno);
		//密钥ID
		req10104.setKeyId("RZAK");
		//密钥类型
		req10104.setKeyTyp(1);
		//密钥长度
		req10104.setKeyLen(1);
		REP_10104 rep10104 = forwardToBocmService.sendToBocm(req10104, REP_10104.class);
		
		String keyValue = rep10104.getBlkVal();
		String checkValue = rep10104.getChkVal();
		
		try(Jedis jedis = myJedis.connect()){
			//把从交行请求的key存储在redis中
			jedis.set(COMMON_PREFIX+"BLKVALUE", keyValue);
			//把从交行请求的校验码存储在redis中
			jedis.set(COMMON_PREFIX+"CHKVALUE", checkValue);
        }
		myLog.info(logger, "密钥定时完毕");
		myLog.info(logger, "新申请的加密密钥："+keyValue);
		myLog.info(logger, "新申请的校验码："+checkValue);
	}
	
	
	@Bean(name = "applyWorkKeyJobDetail")
	public MethodInvokingJobDetailFactoryBean applyWorkKeyJobDetail(ApplyWorkKeyTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "applyWorkKeyJobTrigger")
	public CronTriggerFactoryBean applyWorkKeyJobTrigger(
			@Qualifier("applyWorkKeyJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
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

	@Bean(name = "applyWorkKeyScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("applyWorkKeyJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}

}
