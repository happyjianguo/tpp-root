package com.fxbank.tpp.beps.mq;

import java.util.List;
import java.util.Random;

import com.fxbank.cip.base.log.MyLog;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: IBM MQ 连接方式QA，客户端类
 * @Author: 周勇沩
 * @Date: 2019-04-22 06:36:16
 */
@Component
public class MqQaClient {

	private static Logger logger = LoggerFactory.getLogger(MqQaClient.class);

	private List<MqQaManager> qMgrList;

	/**
	 * @Description: 通过随机方式获取队列管理器发送请求
	 * @Author: 周勇沩
	 * @Date: 2019-04-22 06:35:49
	 */
	private MqQaManager randomManager(MyLog myLog) {
		Integer index = new Random().nextInt(qMgrList.size());
		MqQaManager mqManager = qMgrList.get(index);
		myLog.info(logger, "随机获取队列管理器" + mqManager);
		MQQueueManager qMgr = mqManager.getqMgr();
		if (qMgr == null || !qMgr.isConnected()) {
			myLog.info(logger, "开始重连队列管理器" + mqManager);
			qMgr = mqManager.connectManager();
			myLog.info(logger, "成功重连队列管理器" + mqManager);
		}
		return mqManager;
	}

	public void put(MyLog myLog, String message) {
		MQQueue queue = null;
		MQQueueManager qMgr = null;
		try {
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			MqQaManager mqManager = randomManager(myLog);
			qMgr = mqManager.getqMgr();
			queue = qMgr.accessQueue(mqManager.getQueue(), openOptions);
			MQMessage putMessage = new MQMessage();
			putMessage.characterSet = 819;
			putMessage.write(message.getBytes("UTF-8"));
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			queue.put(putMessage, pmo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (qMgr != null)
					qMgr.commit();
				if (queue != null)
					queue.close();
			} catch (MQException ex) {
				myLog.error(logger, "关闭队列、提交事物异常",ex);
			}
		}
	}

	/**
	 * @return the qMgrList
	 */
	public List<MqQaManager> getqMgrList() {
		return qMgrList;
	}

	/**
	 * @param qMgrList the qMgrList to set
	 */
	public void setqMgrList(List<MqQaManager> qMgrList) {
		this.qMgrList = qMgrList;
	}
}