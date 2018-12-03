package com.fxbank.cap.manager.quartz;

import java.text.SimpleDateFormat;
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
import com.fxbank.cap.manager.constant.CAP_MANAGER;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@Component
@EnableScheduling
public class EndOfDayTask {
	private static Logger logger = LoggerFactory.getLogger(EndOfDayTask.class);

	private static final String JOBNAME = "EndOfDay";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private JedisSentinelPool jedisPool;

	public void exec() {
		logger.info("日切");
		Integer sysDate = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		logger.debug("平台日切开始[" + sysDate + "]");
		publicService.updateSysDate(CAP_MANAGER.SYSTEM_ID, sysDate);
		logger.info("平台日切成功[" + sysDate + "]");
	}

	@Bean(name = "endOfDayJobDetail")
	public MethodInvokingJobDetailFactoryBean endOfDayJobDetail(EndOfDayTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "endOfDayJobTrigger")
	public CronTriggerFactoryBean endOfDayJobTrigger(
			@Qualifier("endOfDayJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = jedisPool.getResource()) {
			exp = jedis.get(QuartzJobConfigration.PAF_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 0 * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "endOfDayScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("endOfDayJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
