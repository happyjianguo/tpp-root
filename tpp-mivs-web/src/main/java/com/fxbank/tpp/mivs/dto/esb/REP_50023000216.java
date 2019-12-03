package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 纳税信息汇总查询应答
 * @Author: 王鹏
 * @Date: 2019/6/24 10:31
 */
public class REP_50023000216 extends REP_BASE {

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
        @JSONField(name = "ACPT_INFO_ARRAY")
        private List<resultList> resultList;//查询结果信息

        public List<resultList> getResultList() {
            return resultList;
        }

        public void setResultList(List<resultList> resultList) {
            this.resultList = resultList;
        }

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "resultList=" + resultList +
                    '}';
        }

    }

    public static class resultList {

        @JSONField(name = "MSG_ID")
        private String msgId;

        @JSONField(name = "CNAPS_MSG_TIME")
        private String creDtTm;

        @JSONField(name = "INSTG_DRCT_PTY")
        private String instgDrctPty;

        @JSONField(name = "INSTG_PTY")
        private String instgPty;

        @JSONField(name = "INSTD_DRCT_PTY")
        private String instdDrctPty;

        @JSONField(name = "INSTD_PTY")
        private String instdPty;

        @JSONField(name = "CNAPS_CUR_DATE")
        private String curSysDt;

        @JSONField(name = "CNAPS_NEXT_DATE")
        private String nxtSysDt;

        @JSONField(name = "CHK_SYSTEM_ID")
        private String sysInd;

        @JSONField(name = "BUSI_STS")
        private String svcInd;

        @JSONField(name = "ACPT_START_TM")
        private String sysOpTm;

        @JSONField(name = "ACPT_END_TM")
        private String sysClTm;

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

        public String getCurSysDt() {
            return curSysDt;
        }

        public void setCurSysDt(String curSysDt) {
            this.curSysDt = curSysDt;
        }

        public String getNxtSysDt() {
            return nxtSysDt;
        }

        public void setNxtSysDt(String nxtSysDt) {
            this.nxtSysDt = nxtSysDt;
        }

        public String getSysInd() {
            return sysInd;
        }

        public void setSysInd(String sysInd) {
            this.sysInd = sysInd;
        }

        public String getSvcInd() {
            return svcInd;
        }

        public void setSvcInd(String svcInd) {
            this.svcInd = svcInd;
        }

        public String getSysOpTm() {
            return sysOpTm;
        }

        public void setSysOpTm(String sysOpTm) {
            this.sysOpTm = sysOpTm;
        }

        public String getSysClTm() {
            return sysClTm;
        }

        public void setSysClTm(String sysClTm) {
            this.sysClTm = sysClTm;
        }
    }
}