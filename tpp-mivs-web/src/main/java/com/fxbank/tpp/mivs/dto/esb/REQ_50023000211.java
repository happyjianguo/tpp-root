package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 企业通知查询请求
 * @Author: 王鹏
 * @Date: 2019/7/3 17:21
 */
public class REQ_50023000211 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000211(){
        super.txDesc = "企业通知查询请求";
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
        @JSONField(name = "StartDt")
        private Integer startDt;    // 核查开始时间

        @JSONField(name = "EndDt")
        private Integer endDt; //核查结束时间

        @JSONField(name = "RplyFlag")
        private String rplyFlag;        //经营者姓名

        public Integer getStartDt() {
            return startDt;
        }

        public void setStartDt(Integer startDt) {
            this.startDt = startDt;
        }

        public Integer getEndDt() {
            return endDt;
        }

        public void setEndDt(Integer endDt) {
            this.endDt = endDt;
        }

        public String getRplyFlag() {
            return rplyFlag;
        }

        public void setRplyFlag(String rplyFlag) {
            this.rplyFlag = rplyFlag;
        }
    }

}