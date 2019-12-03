package com.fxbank.tpp.mivs.mq;

import java.util.Hashtable;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: IBM MQ队列管理器管理类
 * @Author: 周勇沩
 * @Date: 2019-04-22 06:34:03
 */
public class MqQaManager {

    private static Logger logger = LoggerFactory.getLogger(MqQaManager.class);

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
            logger.info("创建队列管理器连接成功."+this);
            return this.qMgr;
        } catch (MQException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("队列管理器["+this.QManager+"],");
        sb.append("队列["+this.Queue+"],");
        sb.append("主机["+this.hostname+"],");
        sb.append("端口["+this.port+"],");
        sb.append("服务通道["+this.channel+"],");
        sb.append("CCSID["+this.ccsid+"],");
        sb.append("WAITINTERVAL["+this.waitinterval+"]");
        return sb.toString();
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