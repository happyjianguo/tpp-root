package com.fxbank.tpp.beps.trade.pmts;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.constant.CONST;
import com.fxbank.tpp.beps.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : 周勇沩
 * @description: 人行来账交易基类
 * @Date : 2020/2/21 11:18
 */
public class TradeBase {
    private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

    @Resource
    private MyJedis myJedis;


    /**
     * @description: 发布990
     * @author : 周勇沩
     * @Date : 2020/2/21 11:49
     */
    public void jedisPublish990(MyLog myLog, String channel, Object obj) {
        jedisPublishByString(myLog, "990", channel, obj, CONST.TIMEOUT_990);
    }


    /**
     * @description: 发布回执
     * @author : 周勇沩
     * @Date : 2020/2/21 11:49
     */
    public void jedisPublishRtn(MyLog myLog, String channel, Object obj) {
        jedisPublishByString(myLog, "rtn", channel, obj, CONST.TIMEOUT_RTN);
    }

    /**
     * @description: 向redis发布异步回执，发布前检查是否有订阅通道
     * @author : 周勇沩
     * @Date : 2020/2/21 11:21
     */
    private void jedisPublishByString(MyLog myLog, String tag, String channel, Object obj, String timeoutkey) {
        String key = tag + "_" + channel;
        myLog.info(logger, "异步报文同步通道编号=[" + key + "]");
        this.jedisPublishByByte(myLog, key.getBytes(), SerializeUtil.serialize(obj), timeoutkey);
        myLog.info(logger, "发布至redis成功");
    }

    private void jedisPublishByByte(MyLog myLog, byte[] key, byte[] value, String timeoutkey) {
        Integer timeout = 0;
        try (Jedis jedis = myJedis.connect()) {
            String stimeout = jedis.get(CONST.PREFIX + "." + timeoutkey);
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
