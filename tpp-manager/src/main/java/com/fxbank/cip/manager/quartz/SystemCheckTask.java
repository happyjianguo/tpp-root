package com.fxbank.cip.manager.quartz;

import java.nio.charset.Charset;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.esb.model.sms.ESB_REP_50022000401;
import com.fxbank.cip.esb.model.sms.ESB_REQ_50022000401;
import com.fxbank.cip.esb.service.IForwardToESBService;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@Component
@EnableScheduling
public class SystemCheckTask {
	private static Logger logger = LoggerFactory.getLogger(SystemCheckTask.class);

	private static final String JOBNAME = "SystemCheck";

	private static final String serviceKey = "ecc_common.systemCheckUrl";

	private static final String servicePack = "ecc_common.systemCheckPack";

	private static final String mobNosKey = "ecc_common.systemCheckMobNos";

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private JedisSentinelPool jedisPool;

	HttpClient httpClient = HttpClients.custom().build();

	public void exec() {
		String url;
		String jsonReq;
		String mobNos;
		String message = "";
		try (Jedis jedis = jedisPool.getResource()) {
			url = jedis.get(serviceKey);
			jsonReq = jedis.get(servicePack);
			mobNos = jedis.get(mobNosKey);
		}

		HttpPost post = null;
		try {

			post = new HttpPost(url);
			// 构造消息头
			post.setHeader("Content-type", "application/json; charset=utf-8");
			post.setHeader("Connection", "Close");

			// 构建消息实体
			StringEntity entity = new StringEntity(jsonReq.toString(), Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			entity.setContentType("application/json");
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);
			boolean isSend = false;
			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				logger.info("请求成功: " + statusCode);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity,"UTF-8");
				EntityUtils.consume(resEntity);
				REP_BASE repBase = JsonUtil.toBean(result, REP_BASE.class);
				if(!"000000".equals(repBase.getRepSysHead().getRet().get(0).getRetCode())){
					isSend = true;
					message = "系统状态检查时收到应答信息为：" + repBase.getRepSysHead().getRet().get(0).getRetMsg();
					logger.debug("接收应答：[" + result + "]");
				}
			} else {
				logger.info("请求出错: " + statusCode);
				isSend = true;
				message = "系统状态检查时收到应答码为：" + statusCode;
			}
			if (!isSend) {
				// 每天8：30、20：30发送系统运行正常通知
				if ((Calendar.getInstance().get(Calendar.HOUR) == 8 && Calendar.getInstance().get(Calendar.MINUTE) > 30
						&& Calendar.getInstance().get(Calendar.MINUTE) < 36)
						|| (Calendar.getInstance().get(Calendar.HOUR) == 20
								&& Calendar.getInstance().get(Calendar.MINUTE) > 30
								&& Calendar.getInstance().get(Calendar.MINUTE) < 36)) {
					isSend = true;
					message = "系统监控功能正在运行";
					logger.debug("系统平安通知");
				}
			}
			if (isSend) {
				sendMessage(mobNos, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				sendMessage(mobNos, "系统监控出现异常：" + e.getMessage());
			} catch (SysTradeExecuteException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (post != null) {
				try {
					post.releaseConnection();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		logger.debug("平台日切开始[" + 1111 + "]");
	}

	@Bean(name = "systemCheckJobDetail")
	public MethodInvokingJobDetailFactoryBean endOfDayJobDetail(SystemCheckTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "systemCheckJobTrigger")
	public CronTriggerFactoryBean endOfDayJobTrigger(
			@Qualifier("systemCheckJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = jedisPool.getResource()) {
			exp = jedis.get(QuartzJobConfigration.CIP_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 0 * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "systemCheckScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("systemCheckJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}

	private void sendMessage(String mobNo, String message) throws SysTradeExecuteException {
		// 调用消息发布平台发送短信
		int date = publicService.getSysDate("CIP");
		int time = publicService.getSysTime();
		int tranceNo = (int) Calendar.getInstance().getTimeInMillis();
		DataTransObject dto = new DataTransObject();
		dto.setSourceType("CIP");
		dto.setSysDate(date);
		dto.setSysTime(time);
		dto.setSysTraceno(tranceNo);
		ESB_REQ_50022000401 req_50022000401 = new ESB_REQ_50022000401(null, date, time, tranceNo);
		// 请求报文头赋值
		req_50022000401.setReqSysHead(new EsbReqHeaderBuilder(req_50022000401.getReqSysHead(), dto).setUserId("")
				.setBranchId("00001").build());
		// ESB报文体赋值
		ESB_REQ_50022000401.REQ_BODY reqBody = req_50022000401.getReqBody();
		reqBody.setSendtype("100");// 只发短信
		reqBody.setMid("SMS_FJR003");
		reqBody.setPacket("620001");// 720001-动账类交易 620001-营销类交易
		reqBody.setSrvid("6201");
		reqBody.setChanno(CIP.SYSTEM_ID); // TODO 上线时需在消息发布平台增加系统编号
		reqBody.setTranscode("qdyw");
		reqBody.setImmediaflag("1");// 1-实时，0-预约
		reqBody.setSendtime("" + date + time);
		reqBody.setBranchno("00000");// 00000-阜新总行
		reqBody.setRegNo(mobNo);
		logger.debug("开始发送短信...，手机号【" + mobNo + "】");
		reqBody.setData("|||||||||||||"+message + "《CIP》|");

		// 调用ESB服务
		forwardToESBService.sendToESB(req_50022000401, ESB_REP_50022000401.class);
	}
}
