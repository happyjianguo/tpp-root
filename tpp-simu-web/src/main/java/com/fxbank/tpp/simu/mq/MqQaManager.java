package com.fxbank.tpp.simu.mq;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

/**
 * @description: IBM MQ队列管理器管理类
 * @author     : 周勇沩
 * @Date       : 2020/2/21 11:04
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

    public void setQManager(String qManager) {
        QManager = qManager;
    }

    public String getQManager() {
        return QManager;
    }

    public void setQueue(String queue) {
        Queue = queue;
    }

    public String getQueue() {
        return Queue;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setCcsid(Integer ccsid) {
        this.ccsid = ccsid;
    }

    public Integer getCcsid() {
        return ccsid;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void setWaitinterval(Integer waitinterval) {
        this.waitinterval = waitinterval;
    }

    public Integer getWaitinterval() {
        return waitinterval;
    }

    public MQQueueManager getqMgr() {
        return qMgr;
    }

    public void setqMgr(MQQueueManager qMgr) {
        this.qMgr = qMgr;
    }
}