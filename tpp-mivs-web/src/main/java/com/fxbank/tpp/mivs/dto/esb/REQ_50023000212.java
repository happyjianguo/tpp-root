package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 公告信息确认
 * @Author: 王鹏
 * @Date: 2019/7/4 7:12
 */
public class REQ_50023000212 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000212(){
        super.txDesc = "公告信息确认";
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
        @JSONField(name = "OrigMsgId")
        private String origMsgId;    // 原报文标识号

        @JSONField(name = "OrigInstgDrctPty")
        private String origInstgDrctPty; //原发起直接参与机构

        @JSONField(name = "OrigInstgPty")
        private String origInstgPty;        //原发起参与机构

        @JSONField(name = "MsgCntt")
        private String msgCntt; //附加信息

        public String getOrigMsgId() {
            return origMsgId;
        }

        public void setOrigMsgId(String origMsgId) {
            this.origMsgId = origMsgId;
        }

        public String getOrigInstgDrctPty() {
            return origInstgDrctPty;
        }

        public void setOrigInstgDrctPty(String origInstgDrctPty) {
            this.origInstgDrctPty = origInstgDrctPty;
        }

        public String getOrigInstgPty() {
            return origInstgPty;
        }

        public void setOrigInstgPty(String origInstgPty) {
            this.origInstgPty = origInstgPty;
        }

        public String getMsgCntt() {
            return msgCntt;
        }

        public void setMsgCntt(String msgCntt) {
            this.msgCntt = msgCntt;
        }
    }
}
