package com.fxbank.tpp.simu.mq;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.simu.config.LogPool;
import com.ibm.mq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: IBM MQ 连接方式QA，客户端类
 * @author     : 周勇沩
 * @Date       : 2020/2/21 11:06
 */
@Component
public class MqQaServer {

	private static Logger logger = LoggerFactory.getLogger(MqQaServer.class);

	private List<MqQaManager> qMgrList;

	@Resource
	private MqQaExecutor mqQaExecutor;

	@Resource
	private LogPool logPool;

	public String get(MqQaManager mqManager) throws MQException, IOException {

		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		MQMessage retrieve = new MQMessage();
		MQQueue queue = null;
		MQQueueManager qMgr = null;
		String message = null;

		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
		gmo.options = gmo.options + MQC.MQGMO_WAIT;
		gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.options = gmo.options + MQC.MQGMO_ACCEPT_TRUNCATED_MSG;
		gmo.waitInterval = mqManager.getWaitinterval();
		qMgr = mqManager.getqMgr();
		try {
			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = mqManager.connectManager();
			}
			queue = qMgr.accessQueue(mqManager.getQueue(), openOptions);
			retrieve.characterSet = 819;

			queue.get(retrieve, gmo,4096000);
			byte[] by = new byte[retrieve.getMessageLength()];
			retrieve.readFully(by);
			message = new String(by, "UTF-8");
		} finally {
			try {
				qMgr.commit();
				queue.close();
			} catch (MQException ex) {
				this.logger.error("关闭队列、提交事物异常",ex);
			}

		}
		return message;
	}

	public void listen() {
		ExecutorService main = Executors.newFixedThreadPool(qMgrList.size());
		ExecutorService child = Executors.newCachedThreadPool();
		for (MqQaManager mqManager : qMgrList) {
			main.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String message = get(mqManager);
							child.execute(new Runnable() {
								@Override
								public void run() {
									try {
										mqQaExecutor.execute(message);
									} catch (SysTradeExecuteException e) {
										e.printStackTrace();
									}
								}
							});
						} catch (MQException eMQ) {
							if (eMQ.reasonCode == 2033) {
								continue;
							} else {
								logger.error("通过MQ获取数据异常", eMQ);
								mqManager.connectManager();
							}
						} catch (Exception e) {
							logger.error("通过MQ获取数据异常", e);
							mqManager.connectManager();
						}
					}
				}
			});
		}
	}

	public List<MqQaManager> getqMgrList() {
		return qMgrList;
	}

	public void setqMgrList(List<MqQaManager> qMgrList) {
		this.qMgrList = qMgrList;
	}
}