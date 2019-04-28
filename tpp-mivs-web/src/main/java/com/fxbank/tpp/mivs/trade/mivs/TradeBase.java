package com.fxbank.tpp.mivs.trade.mivs;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * @Description: 人行发起交易基类
 * @Author: 周勇沩
 * @Date: 2019-04-28 09:54:54
 */
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Resource
	private MyJedis myJedis;

	public void jedisPublish(MyLog myLog, byte[] key, byte[] value) {
		if (!new String(key).equals("990")) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				myLog.error(logger, "发布等待异常", e);
			}
		}
		try (Jedis jedis = myJedis.connect()) {
			jedis.publish(key, value);
		}
	}
}
