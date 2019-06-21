package com.fxbank.tpp.bocm.netty.bocm;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * @Description: 交行客户端通讯主程序
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:59:18
 */
@Component
public class BocmClient {

    private static Logger logger = LoggerFactory.getLogger(BocmClient.class);

    public static final String CODING = "GBK";
    public static final String PREFIX = "bocm-service.";
    public static final String BOCM_IP_KEY = PREFIX + "bocm_ip";
    public static final String BOCM_PORT_KEY = PREFIX + "bocm_port";
    public static final String BOCM_TIMEOUT_KEY = PREFIX + "bocm_timeout";

    @Resource
    private MyJedis myJedis;

    public <T> T comm(MyLog myLog, Object req, Class<T> clazz ,IBocmSafeService safeService) throws SysTradeExecuteException {
        String ip = null;
        Integer port = 0;
        Integer timeOut = 0;
        try (Jedis jedis = myJedis.connect()) {
            ip = jedis.get(BOCM_IP_KEY);
            String sport = jedis.get(BOCM_PORT_KEY);
            String stimeOut = jedis.get(BOCM_TIMEOUT_KEY);
            if (ip == null || sport == null || stimeOut == null) {
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", ip, sport, stimeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
            port = Integer.valueOf(sport);
            timeOut = Integer.valueOf(stimeOut);
            if (port == 0 || timeOut == 0) {
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", port, timeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
        }
        myLog.info(logger, "连接信息：IP[" + ip + "],PORT[" + port + "],TIMEOUT[" + timeOut + "]");

        BocmInitializer<T> initializer = new BocmInitializer<T>(myLog, req, clazz, safeService);
        NettySyncClient<T> clientSync = new NettySyncClient<T>(myLog, initializer);
        T repData = clientSync.comm(ip, port, timeOut);
        return repData;
    }

}