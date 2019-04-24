package com.fxbank.tpp.mivs.mq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: IBM MQ 通过QA方式连接队列管理器配置类
 * @Author: 周勇沩
 * @Date: 2019-04-22 06:33:17
 */
@Configuration
@ConditionalOnProperty(name = "pmts.mq.enable", havingValue = "true")
@ConfigurationProperties(prefix = "pmts.mq")
public class MqQaConfig {

    private String qmanager1;
    private String queue1;
    private String hostname1;
    private String channel1;
    private Integer ccsid1;
    private Integer port1;
    private Integer waitinteval1;

    private String qmanager2;
    private String queue2;
    private String hostname2;
    private String channel2;
    private Integer ccsid2;
    private Integer port2;
    private Integer waitinteval2;

    private MqQaManager genMqManager(String qmanager, String queue, String hostname, String channel, Integer ccsid,
            Integer port, Integer waitinteval) {
        MqQaManager mqManager = new MqQaManager();
        mqManager.setQManager(qmanager);
        mqManager.setQueue(queue);
        mqManager.setHostname(hostname);
        mqManager.setChannel(channel);
        mqManager.setCcsid(ccsid);
        mqManager.setPort(port);
        mqManager.setWaitinterval(waitinteval);
        mqManager.connectManager(); //连接队列管理器
        return mqManager;
    }

    @Bean(name = "mqManagerList")
    public List<MqQaManager> mqManagerList() {
        List<MqQaManager> mqManagerList = new ArrayList<MqQaManager>();
        List<MqQaManager> mqManagerList2 = mqManagerList;
        mqManagerList2.add(genMqManager(this.qmanager1, this.queue1, this.hostname1, this.channel1, this.ccsid1,
                this.port1, this.waitinteval1));
        mqManagerList2.add(genMqManager(this.qmanager2, this.queue2, this.hostname2, this.channel2, this.ccsid2,
                this.port2, this.waitinteval2));
        return mqManagerList2;
    }


    /**
     * @return the qmanager1
     */
    public String getQmanager1() {
        return qmanager1;
    }

    /**
     * @return the waitinteval2
     */
    public Integer getWaitinteval2() {
        return waitinteval2;
    }

    /**
     * @param waitinteval2 the waitinteval2 to set
     */
    public void setWaitinteval2(Integer waitinteval2) {
        this.waitinteval2 = waitinteval2;
    }

    /**
     * @return the port2
     */
    public Integer getPort2() {
        return port2;
    }

    /**
     * @param port2 the port2 to set
     */
    public void setPort2(Integer port2) {
        this.port2 = port2;
    }

    /**
     * @return the ccsid2
     */
    public Integer getCcsid2() {
        return ccsid2;
    }

    /**
     * @param ccsid2 the ccsid2 to set
     */
    public void setCcsid2(Integer ccsid2) {
        this.ccsid2 = ccsid2;
    }

    /**
     * @return the channel2
     */
    public String getChannel2() {
        return channel2;
    }

    /**
     * @param channel2 the channel2 to set
     */
    public void setChannel2(String channel2) {
        this.channel2 = channel2;
    }

    /**
     * @return the hostname2
     */
    public String getHostname2() {
        return hostname2;
    }

    /**
     * @param hostname2 the hostname2 to set
     */
    public void setHostname2(String hostname2) {
        this.hostname2 = hostname2;
    }

    /**
     * @return the queue2
     */
    public String getQueue2() {
        return queue2;
    }

    /**
     * @param queue2 the queue2 to set
     */
    public void setQueue2(String queue2) {
        this.queue2 = queue2;
    }

    /**
     * @return the qmanager2
     */
    public String getQmanager2() {
        return qmanager2;
    }

    /**
     * @param qmanager2 the qmanager2 to set
     */
    public void setQmanager2(String qmanager2) {
        this.qmanager2 = qmanager2;
    }

    /**
     * @return the waitinteval1
     */
    public Integer getWaitinteval1() {
        return waitinteval1;
    }

    /**
     * @param waitinteval1 the waitinteval1 to set
     */
    public void setWaitinteval1(Integer waitinteval1) {
        this.waitinteval1 = waitinteval1;
    }

    /**
     * @return the port1
     */
    public Integer getPort1() {
        return port1;
    }

    /**
     * @param port1 the port1 to set
     */
    public void setPort1(Integer port1) {
        this.port1 = port1;
    }

    /**
     * @return the ccsid1
     */
    public Integer getCcsid1() {
        return ccsid1;
    }

    /**
     * @param ccsid1 the ccsid1 to set
     */
    public void setCcsid1(Integer ccsid1) {
        this.ccsid1 = ccsid1;
    }

    /**
     * @return the channel1
     */
    public String getChannel1() {
        return channel1;
    }

    /**
     * @param channel1 the channel1 to set
     */
    public void setChannel1(String channel1) {
        this.channel1 = channel1;
    }

    /**
     * @return the hostname1
     */
    public String getHostname1() {
        return hostname1;
    }

    /**
     * @param hostname1 the hostname1 to set
     */
    public void setHostname1(String hostname1) {
        this.hostname1 = hostname1;
    }

    /**
     * @return the queue1
     */
    public String getQueue1() {
        return queue1;
    }

    /**
     * @param queue1 the queue1 to set
     */
    public void setQueue1(String queue1) {
        this.queue1 = queue1;
    }

    /**
     * @param qmanager1 the qmanager1 to set
     */
    public void setQmanager1(String qmanager1) {
        this.qmanager1 = qmanager1;
    }
}