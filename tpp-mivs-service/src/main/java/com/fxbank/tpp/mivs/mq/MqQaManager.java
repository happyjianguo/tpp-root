package com.fxbank.tpp.mivs.mq;

import java.util.Hashtable;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

/**
 * @Description: IBM MQ队列管理器管理类
 * @Author: 周勇沩
 * @Date: 2019-04-22 06:34:03
 */
public class MqQaManager {
    private MQQueueManager qMgr;

    private String QManager;

    private String Queue;

    private String hostname;

    private String channel;

    private Integer ccsid;

    private Integer port;

    private Integer waitinterval;

    public MQQueueManager connectManager() {
        Hashtable<Object, Object> prop = new Hashtable<Object, Object>();
        prop.put(MQC.PORT_PROPERTY, this.port);
        prop.put(MQC.HOST_NAME_PROPERTY, this.hostname);
        prop.put(MQC.CHANNEL_PROPERTY, this.channel);
        prop.put(MQC.CCSID_PROPERTY, this.ccsid);
        try {
            this.qMgr = new MQQueueManager(this.QManager, prop);
            return this.qMgr;
        } catch (MQException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param qManager the qManager to set
     */
    public void setQManager(String qManager) {
        QManager = qManager;
    }

    /**
     * @return the qManager
     */
    public String getQManager() {
        return QManager;
    }

    /**
     * @param queue the queue to set
     */
    public void setQueue(String queue) {
        Queue = queue;
    }

    /**
     * @return the queue
     */
    public String getQueue() {
        return Queue;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param ccsid the ccsid to set
     */
    public void setCcsid(Integer ccsid) {
        this.ccsid = ccsid;
    }

    /**
     * @return the ccsid
     */
    public Integer getCcsid() {
        return ccsid;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param waitinterval the waitinterval to set
     */
    public void setWaitinterval(Integer waitinterval) {
        this.waitinterval = waitinterval;
    }

    /**
     * @return the waitinterval
     */
    public Integer getWaitinterval() {
        return waitinterval;
    }

    /**
     * @return the qMgr
     */
    public MQQueueManager getqMgr() {
        return qMgr;
    }

    /**
     * @param qMgr the qMgr to set
     */
    public void setqMgr(MQQueueManager qMgr) {
        this.qMgr = qMgr;
    }
}