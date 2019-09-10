package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 纳税信息核查结果汇总查询
 * @Author: 王鹏
 * @Date: 2019/6/24 10:31
 */
public class REQ_50023000216 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000216(){
        super.txDesc = "纳税信息核查结果汇总查询";
    }

    public REQ_APP_HEAD getReqAppHead() {
        return reqAppHead;
    }

    public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
        this.reqAppHead = reqAppHead;
    }

    public REQ_SYS_HEAD getReqSysHead() {
        return reqSysHead;
    }

    public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
        this.reqSysHead = reqSysHead;
    }

    public REQ_BODY getReqBody() {
        return reqBody;
    }

    public void setReqBody(REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "QUERY_DATE")
        private String queDt;    //

        @JSONField(name = "MSG_ID")
        private String msgId; //

        public String getQueDt() {
            return queDt;
        }

        public void setQueDt(String queDt) {
            this.queDt = queDt;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }
    }
}