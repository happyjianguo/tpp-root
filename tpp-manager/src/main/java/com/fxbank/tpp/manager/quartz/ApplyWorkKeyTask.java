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
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.tpp.bocm.model.REP_10104_MAC;
import com.fxbank.tpp.bocm.model.REP_10104_PIN;
import com.fxbank.tpp.bocm.model.REQ_10103;
import com.fxbank.tpp.bocm.model.REQ_10104;
import com.fxbank.tpp.bocm.service.IBocmSafeService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30043003001;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30043003001;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: ApplyWorkKeyTask 
* @Description: 交行工作秘钥申请定时任务
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
	
    @Reference(version = "1.0.0")
    private IBocmSafeService safeService;
	
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
		
		Integer date = Integer.parseInt(df.format(cal.getTime()));
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
				
		//交行总行行号
		String JHNO = "";
		//阜新银行总行行号
		String FXNO = "";
		String KeyId = "";
		try(Jedis jedis = myJedis.connect()){
			//从redis中获取交行总行行号
			JHNO = jedis.get(COMMON_PREFIX+"JHNO");
			FXNO = jedis.get(COMMON_PREFIX+"FXNO");
			KeyId = jedis.get(COMMON_PREFIX+"KeyId");
        }
		
		//1.申请MAC key
		REQ_10104 reqMac10104 = new REQ_10104(myLog, date, sysTime, sysTraceno);
		reqMac10104.setSbnkNo(FXNO);
		reqMac10104.setRbnkNo(FXNO);
		//密钥ID
		reqMac10104.setKeyId(KeyId);
		//密钥类型
		reqMac10104.setKeyTyp(1);
		//密钥长度
		reqMac10104.setKeyLen(16);
		myLog.info(logger, "请求Mac密钥");
		REP_10104_MAC repMac10104 = forwardToBocmService.sendToBocm(reqMac10104, REP_10104_MAC.class);
		
		String macKeyValue = repMac10104.getBlkVal();
		String macCheckValue = repMac10104.getChkVal();
		
		myLog.info(logger, "Mac密钥密文值【"+macKeyValue+"】密钥校验值【"+macCheckValue+"】");
		//Mac密钥 更新
		safeService.updateMacKey(myLog, macKeyValue, macCheckValue);
		
		
		//1.申请Pin key
		REQ_10104 reqPin10104 = new REQ_10104(myLog, date, sysTime, sysTraceno);
		reqPin10104.setSbnkNo(FXNO);
		reqPin10104.setRbnkNo(FXNO);
		//密钥ID
		reqPin10104.setKeyId(KeyId);
		//密钥类型
		reqPin10104.setKeyTyp(0);
		//密钥长度
		reqPin10104.setKeyLen(32);
		myLog.info(logger, "请求Pin密钥");
		REP_10104_PIN repPin10104 = forwardToBocmService.sendToBocm(reqPin10104, REP_10104_PIN.class);
		
		String pinKeyValue = repPin10104.getBlkVal();
		String pinCheckValue = repPin10104.getChkVal();
		myLog.info(logger, "Pin密钥密文值【"+pinKeyValue+"】密钥校验值【"+pinCheckValue+"】");
		safeService.updatePinKey(myLog, pinKeyValue, pinCheckValue);
		myLog.info(logger, "密钥定时更新完毕");

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
