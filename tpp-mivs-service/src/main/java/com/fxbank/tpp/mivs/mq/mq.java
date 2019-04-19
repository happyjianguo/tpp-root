package com.fxbank.tpp.mivs.mq;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class mq {
	private String QManager = null; 

	private String QRemote = null; 

	private String QLocal = null; 
	
	private String QBusilist = null; 

	private MQQueueManager qMgr; 

	private int waitinterval = 0;

	public mq() throws Exception {
		this.QManager = "QMUMBFEA";
		this.QRemote = "MSGTOBANK_9";
		this.QLocal = "MSGTOBANK_9";
		this.QBusilist = "";
		MQEnvironment.hostname = "57.25.2.205";
		MQEnvironment.channel = "SVCONNCH";
		MQEnvironment.CCSID = 819;
		MQEnvironment.port = 1424;
		this.waitinterval = 60*1000;
	}

	public void connect() throws Exception {
		qMgr = new MQQueueManager(QManager);
	}

	public void put(String message){
		MQQueue queue = null;
		try {
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;

			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = new MQQueueManager(QManager);
			}
			queue = qMgr.accessQueue(QRemote, openOptions);

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
		String message = null;

		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
		gmo.options = gmo.options + MQC.MQGMO_WAIT;
		gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.waitInterval = this.waitinterval * 1000;
		try {
			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = new MQQueueManager(QManager);
			}
			queue = qMgr.accessQueue(QLocal, openOptions);
			retrieve.characterSet = 819; 

			queue.get(retrieve, gmo);
			byte[] by = new byte[retrieve.getMessageLength()];
			retrieve.readFully(by);	
			String str = new String(by); 
			message = new String(by, "UTF-8") ;

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

	public String get_QLocal() {
		return this.QLocal;
	}
	
	public Integer BusiChk(String busi) {
		
		return this.QBusilist.indexOf(busi);
		
	}

	public void close() throws Exception {

		this.qMgr.disconnect();

	}

	public static void main(String[] args) {
		try {
			mq mq = new mq();
			mq.connect();
			mq.put("周勇沩");
			System.out.println("读取消息="+mq.get());
		} catch (Exception e) {

		}

	}

}

