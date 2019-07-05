package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 企业通知查询应答
 * @Author: 王鹏
 * @Date: 2019/7/3 17:53
 */
public class REP_50023000211  extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_BODY repBody = new REP_BODY();

    public REP_APP_HEAD getRepAppHead() {
        return repAppHead;
    }

    public void setRepAppHead(REP_APP_HEAD repAppHead) {
        this.repAppHead = repAppHead;
    }

    public REP_SYS_HEAD getRepSysHead() {
        return repSysHead;
    }

    public void setRepSysHead(REP_SYS_HEAD repSysHead) {
        this.repSysHead = repSysHead;
    }

    public REP_BODY getRepBody() {
        return repBody;
    }

    public void setRepBody(REP_BODY repBody) {
        this.repBody = repBody;
    }

    public class REP_BODY {
        //循环开始
        @JSONField(name = "ResultList")
        private List<resultList> resultList;//查询结果信息

        public List<REP_50023000211.resultList> getResultList() {
            return resultList;
        }

        public void setResultList(List<REP_50023000211.resultList> resultList) {
            this.resultList = resultList;
        }

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "resultList=" + resultList +
                    '}';
        }
    }

    public static class resultList{

        @JSONField(name = "platDate")
        private Integer platDate;

        @JSONField(name = "platTrace")
        private Integer platTrace;

        @JSONField(name = "platTime")
        private Integer platTime;

        @JSONField(name = "msgid")
        private String msgId;

        @JSONField(name = "cre_dt_tm")
        private String creDtTm;

        @JSONField(name = "instg_drct_pty")
        private String instgDrctPty;

        @JSONField(name = "instg_pty")
        private String instgPty;

        @JSONField(name = "instd_drct_pty")
        private String instdDrctPty;

        @JSONField(name = "instd_pty")
        private String instdPty;

        @JSONField(name = "rply_flag")
        private String rplyFlag;

        @JSONField(name = "msg_cntt")
        private String msgCntt;

        @JSONField(name = "isornot_rsp")
        private String isornotRsp;

        public Integer getPlatDate() {
            return platDate;
        }

        public void setPlatDate(Integer platDate) {
            this.platDate = platDate;
        }

        public Integer getPlatTrace() {
            return platTrace;
        }

        public void setPlatTrace(Integer platTrace) {
            this.platTrace = platTrace;
        }

        public Integer getPlatTime() {
            return platTime;
        }

        public void setPlatTime(Integer platTime) {
            this.platTime = platTime;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getCreDtTm() {
            return creDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            this.creDtTm = creDtTm;
        }

        public String getInstgDrctPty() {
            return instgDrctPty;
        }

        public void setInstgDrctPty(String instgDrctPty) {
            this.instgDrctPty = instgDrctPty;
        }

        public String getInstgPty() {
            return instgPty;
        }

        public void setInstgPty(String instgPty) {
            this.instgPty = instgPty;
        }

        public String getInstdDrctPty() {
            return instdDrctPty;
        }

        public void setInstdDrctPty(String instdDrctPty) {
            this.instdDrctPty = instdDrctPty;
        }

        public String getInstdPty() {
            return instdPty;
        }

        public void setInstdPty(String instdPty) {
            this.instdPty = instdPty;
        }

        public String getRplyFlag() {
            return rplyFlag;
        }

        public void setRplyFlag(String rplyFlag) {
            this.rplyFlag = rplyFlag;
        }

        public String getMsgCntt() {
            return msgCntt;
        }

        public void setMsgCntt(String msgCntt) {
            this.msgCntt = msgCntt;
        }

        public String getIsornotRsp() {
            return isornotRsp;
        }

        public void setIsornotRsp(String isornotRsp) {
            this.isornotRsp = isornotRsp;
        }

        @Override
        public String toString() {
            return "resultList{" +
                    "platDate='" + platDate + '\'' +
                    ", platTrace='" + platTrace + '\'' +
                    ", platTime='" + platTime + '\'' +
                    ", msgId='" + msgId + '\'' +
                    ", creDtTm='" + creDtTm + '\'' +
                    ", instgDrctPty='" + instgDrctPty + '\'' +
                    ", instgPty='" + instgPty + '\'' +
                    ", instdDrctPty='" + instdDrctPty + '\'' +
                    ", instdPty='" + instdPty + '\'' +
                    ", rplyFlag='" + rplyFlag + '\'' +
                    ", msgCntt='" + msgCntt + '\'' +
                    ", isornotRsp='" + isornotRsp + '\'' +
                    '}';
        }
    }
}