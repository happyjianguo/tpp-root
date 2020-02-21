package com.fxbank.tpp.beps;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.tpp.beps.constant.CONST;
import com.fxbank.tpp.beps.mq.MqQaClient;
import com.fxbank.tpp.beps.mq.MqQaConfig;
import com.fxbank.tpp.beps.mq.MqQaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.fxbank")
public class BepsServer implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(BepsServer.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private MqQaClient mqQaClient;

    @Resource
    private MqQaConfig mqQaConfig;

    public static void main(String[] args) {
        SpringApplication.run(BepsServer.class, args);
    }

    @Override
    public void run(String... strings) {
        try (Jedis jedis = myJedis.connect()) {
            mqQaConfig.setQmanager1(jedis.get(CONST.PREFIX + "pmts_mq_qmanager1"));
            mqQaConfig.setQueue1(jedis.get(CONST.PREFIX + "pmts_mq_squeue1"));
            mqQaConfig.setHostname1(jedis.get(CONST.PREFIX + "pmts_mq_hostname1"));
            mqQaConfig.setChannel1(jedis.get(CONST.PREFIX + "pmts_mq_channel1"));
            mqQaConfig.setCcsid1(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_ccsid1")));
            mqQaConfig.setPort1(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_port1")));
            mqQaConfig.setWaitinteval1(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_waitinteval1")));
            mqQaConfig.setQmanager2(jedis.get(CONST.PREFIX + "pmts_mq_qmanager2"));
            mqQaConfig.setQueue2(jedis.get(CONST.PREFIX + "pmts_mq_squeue2"));
            mqQaConfig.setHostname2(jedis.get(CONST.PREFIX + "pmts_mq_hostname2"));
            mqQaConfig.setChannel2(jedis.get(CONST.PREFIX + "pmts_mq_channel2"));
            mqQaConfig.setCcsid2(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_ccsid2")));
            mqQaConfig.setPort2(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_port2")));
            mqQaConfig.setWaitinteval2(Integer.valueOf(jedis.get(CONST.PREFIX + "pmts_mq_waitinteval2")));
        }
        List<MqQaManager> mqQaManagerList = mqQaConfig.mqManagerList();
        for (MqQaManager MqQaManager : mqQaManagerList) {
            logger.info("MQ连接信息" + MqQaManager);
        }
        mqQaClient.setqMgrList(mqQaManagerList);
    }

}
