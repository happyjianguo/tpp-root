package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 企业信息联网核查查业务受理时间查询ESB请求报文
 * @Author: 王鹏
 * @Date: 2019/4/30 10:15
 */
public class REQ_50023000203 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000203.REQ_BODY reqBody;

    public REQ_50023000203(){
        super.txDesc = "企业信息联网核查查业务受理时间查询";
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


    public REQ_50023000203.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_50023000203.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "SysInd")
        private String SysInd;    //核查系统标识

        @JSONField(name = "QueDt")
        private String QueDt;		//查询日期

        public String getSysInd() {
            return SysInd;
        }

        public void setSysInd(String sysInd) {
            this.SysInd = sysInd;
        }

        public String getQueDt() {
            return QueDt;
        }

        public void setQueDt(String queDt) {
            this.QueDt = queDt;
        }
    }
}