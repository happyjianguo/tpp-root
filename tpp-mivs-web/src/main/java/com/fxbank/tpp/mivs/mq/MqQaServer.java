package com.fxbank.tpp.mivs.mq;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: IBM MQ 连接方式QA，客户端类
 * @Author: 周勇沩
 * @Date: 2019-04-22 06:36:16
 */
@Component
public class MqQaServer {

	private static Logger logger = LoggerFactory.getLogger(MqQaServer.class);

	@Resource(name = "mqManagerList")
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
		gmo.waitInterval = mqManager.getWaitinterval();
		qMgr = mqManager.getqMgr();
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

		} finally {
			try {
				qMgr.commit();
				queue.close();
			} catch (MQException ex) {
			}

		}

		return message;

	}

	@Autowired
	public void serve() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try {
					listen();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void listen() throws UnknownHostException {
		MyLog myLog = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
		logPool.init(myLog);
		ExecutorService main = Executors.newFixedThreadPool(qMgrList.size());
		ExecutorService child = Executors.newCachedThreadPool();
		for(MqQaManager mqManager:qMgrList){
			main.execute(new Runnable(){
				@Override
				public void run() {
					while (true) {
						try {
							String message = get(mqManager);
							child.execute(new Runnable(){
								@Override
								public void run() {
									mqQaExecutor.execute(message);
								}
							});
						} catch (MQException eMQ) {
							if (eMQ.reasonCode == 2033) {
								continue;
							} else
							{
								//applog.cnaps2.error(eMQ.getMessage());
							}
						}catch (Exception e){

						}
				}
			}
			});
		};
	}
}