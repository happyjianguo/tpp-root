package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 企业联网核查结果查询请求报文
 * @Author: 王鹏
 * @Date: 2019/5/15 14:39
 */
public class REQ_50023000206 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000206.REQ_BODY reqBody;

    public REQ_50023000206(){
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


    public REQ_50023000206.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_50023000206.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "CHK_SYSTEM_ID")
        private String sysInd;    //核查系统标识
        public String getSysInd() {
            return sysInd;
        }
        public void setSysInd(String sysInd) {
            this.sysInd = sysInd;
        }

        @JSONField(name = "START_DATE")
        private String startDt;		//查询开始日期
        public String getStartDt() {
            return startDt;
        }
        public void setStartDt(String startDt) {
            this.startDt = startDt;
        }

        @JSONField(name = "END_DATE")
        private String endDt;		//查询结束日期
        public String getEndDt() {
            return endDt;
        }
        public void setEndDt(String endDt) {
            this.endDt = endDt;
        }
    }
}