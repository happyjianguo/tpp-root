package com.fxbank.tpp.mivs.trade.mivs;

import java.util.List;
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

	private static final String TIMEOUT_911 = "mivs.timeout_911";

	@Resource
	private MyJedis myJedis;

	public void jedisPublish(MyLog myLog, byte[] key, byte[] value) {
		Integer timeout = 0;
		try (Jedis jedis = myJedis.connect()) {
			String stimeout = jedis.get(TIMEOUT_911);
			if (stimeout == null) {
				timeout = 60;
			} else {
				try {
					timeout = Integer.valueOf(stimeout);
				} catch (Exception e) {
					myLog.error(logger, "ccms911报文同步等待超时时间配置异常，取默认值60");
					timeout = 60;
				}
			}
			myLog.info(logger, "异步回执总超时时间=[" + timeout + "]");

			String sKey = new String(key);
			myLog.info(logger, "发布的key=[" + sKey + "]");
			while (true) {
				// 查看通道是否备订阅，如果没有，则等待一段时间后，重新检查
				List<String> channels = jedis.pubsubChannels(sKey);
				if (channels.size() > 0 || timeout == 0) {
					break;
				} else {
					timeout--;
					myLog.info(logger, "未找到订阅通道,等待1s后再试,一共还剩[" + timeout + "]");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						myLog.error(logger, "发布等待异常", e);
					}
				}
			}
			jedis.publish(key, value);
		}
	}
}
