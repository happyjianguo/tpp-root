package com.fxbank.tpp.mivs.sync;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.util.SerializeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

@Component
public class SyncCom {

    private static Logger logger = LoggerFactory.getLogger(SyncCom.class);

    @Resource
	private MyJedis myJedis;

    public <T> T get(MyLog myLog, String msgId,long timeout, TimeUnit unit) {
		SyncSlot<byte[]> slot = new SyncSlot<byte[]>();
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = myJedis.connect()) {
					jedis.subscribe(new BinaryJedisPubSub() {
						@Override
						public void onMessage(byte[] channel, byte[] message) {
							String sChannel = null;
							try {
								sChannel = new String(channel, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							myLog.info(logger, "通道[" + sChannel + "]收到消息[" + message + "]");
							slot.setResponse(message);
							super.unsubscribe(channel);
						}

						@Override
						public void onSubscribe(byte[] channel, int subscribedChannels) {
							String sChannel = null;
							try {
								sChannel = new String(channel, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							myLog.info(logger, "订阅通道成功[" + sChannel + "][" + subscribedChannels + "]");
						}

						@Override
						public void onUnsubscribe(byte[] channel, int subscribedChannels) {
							String sChannel = null;
							try {
								sChannel = new String(channel, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							myLog.info(logger, "取消订阅通道成功[" + sChannel + "][" + subscribedChannels + "]");
						}
					}, "990".getBytes()); // 通过subscribe 的api去订阅，入参是订阅者和频道名
				}
				myLog.info(logger, "订阅子进程退出");
			}
		});

		T t = null;
		try {
			myLog.info(logger, "实时等待990应答...");
			byte[] repData = slot.get(timeout, unit);
			t = (T) SerializeUtil.unserialize(repData);
			myLog.info(logger, "收到实时应答" + t);
		} catch (InterruptedException e) {
			myLog.error(logger, "等待服务端应答超时", e);
		} finally {
			try (Jedis jedis = myJedis.connect()) {
				jedis.publish("990", "QUIT"); // 确保子进程退出
			}
		}
		if (slot.isCancelled()) {
			myLog.error(logger, "发送失败");
		}
		if (!slot.isDone()) {
			myLog.error(logger, "等待服务端应答超时");
		}
		return t;
	}

}