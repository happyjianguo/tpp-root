package com.fxbank.tpp.beps.mq;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: IBM MQ 通过QA方式连接队列管理器配置类
 * @author     : 周勇沩
 * @Date       : 2020/2/21 11:05
 */
@Component
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

    public List<MqQaManager> mqManagerList() {
        List<MqQaManager> mqManagerList = new ArrayList<MqQaManager>();
        mqManagerList.add(genMqManager(this.qmanager1, this.queue1, this.hostname1, this.channel1, this.ccsid1,
                this.port1, this.waitinteval1));
        mqManagerList.add(genMqManager(this.qmanager2, this.queue2, this.hostname2, this.channel2, this.ccsid2,
                this.port2, this.waitinteval2));
        return mqManagerList;
    }

    public String getQmanager1() {
        return qmanager1;
    }

    public Integer getWaitinteval2() {
        return waitinteval2;
    }

    public void setWaitinteval2(Integer waitinteval2) {
        this.waitinteval2 = waitinteval2;
    }

    public Integer getPort2() {
        return port2;
    }

    public void setPort2(Integer port2) {
        this.port2 = port2;
    }

    public Integer getCcsid2() {
        return ccsid2;
    }

    public void setCcsid2(Integer ccsid2) {
        this.ccsid2 = ccsid2;
    }

    public String getChannel2() {
        return channel2;
    }

    public void setChannel2(String channel2) {
        this.channel2 = channel2;
    }

    public String getHostname2() {
        return hostname2;
    }

    public void setHostname2(String hostname2) {
        this.hostname2 = hostname2;
    }

    public String getQueue2() {
        return queue2;
    }

    public void setQueue2(String queue2) {
        this.queue2 = queue2;
    }

    public String getQmanager2() {
        return qmanager2;
    }

    public void setQmanager2(String qmanager2) {
        this.qmanager2 = qmanager2;
    }

    public Integer getWaitinteval1() {
        return waitinteval1;
    }

    public void setWaitinteval1(Integer waitinteval1) {
        this.waitinteval1 = waitinteval1;
    }

    public Integer getPort1() {
        return port1;
    }

    public void setPort1(Integer port1) {
        this.port1 = port1;
    }

    public Integer getCcsid1() {
        return ccsid1;
    }

    public void setCcsid1(Integer ccsid1) {
        this.ccsid1 = ccsid1;
    }

    public String getChannel1() {
        return channel1;
    }

    public void setChannel1(String channel1) {
        this.channel1 = channel1;
    }

    public String getHostname1() {
        return hostname1;
    }

    public void setHostname1(String hostname1) {
        this.hostname1 = hostname1;
    }

    public String getQueue1() {
        return queue1;
    }

    public void setQueue1(String queue1) {
        this.queue1 = queue1;
    }

    public void setQmanager1(String qmanager1) {
        this.qmanager1 = qmanager1;
    }
}