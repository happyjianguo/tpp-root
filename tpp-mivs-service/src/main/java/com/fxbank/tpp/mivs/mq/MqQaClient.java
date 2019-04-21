package com.fxbank.tpp.mivs.mq;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
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

	@Resource(name = "mqManagerList")
	private List<MqQaManager> qMgrList;

	/**
	 * @Description: 通过随机方式获取队列管理器发送请求
	 * @Author: 周勇沩
	 * @Date: 2019-04-22 06:35:49
	 */
	private MqQaManager randomManager() {
		Integer index = new Random().nextInt(qMgrList.size());
		MqQaManager mqManager = qMgrList.get(index);
		MQQueueManager qMgr = mqManager.getqMgr();
		if (qMgr == null || !qMgr.isConnected()) {
			qMgr = mqManager.connectManager();
		}
		return mqManager;
	}

	public void put(String message) {
		MQQueue queue = null;
		MQQueueManager qMgr = null;
		try {
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			MqQaManager mqManager = randomManager();
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
				qMgr.commit();
				queue.close();
			} catch (MQException ex) {
			}
		}
	}

	public String get() {

		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		MQMessage retrieve = new MQMessage();
		MQQueue queue = null;
		MQQueueManager qMgr = null;
		String message = null;

		MqQaManager mqManager = randomManager();

		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
		gmo.options = gmo.options + MQC.MQGMO_WAIT;
		gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.waitInterval = mqManager.getWaitinterval();
		try {
			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = mqManager.connectManager();
			}
			queue = qMgr.accessQueue(mqManager.getQueue(), openOptions);
			retrieve.characterSet = 819;

			queue.get(retrieve, gmo);
			byte[] by = new byte[retrieve.getMessageLength()];
			retrieve.readFully(by);
			String str = new String(by);
			message = new String(by, "UTF-8");

		} catch (MQException eMQ) {
			throw new RuntimeException(eMQ);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				qMgr.commit();
				queue.close();
			} catch (MQException ex) {
			}

		}

		return message;

	}

	public void close() throws Exception {
		for (MqQaManager mqManager : qMgrList) {
			mqManager.getqMgr().disconnect();
		}
	}

}