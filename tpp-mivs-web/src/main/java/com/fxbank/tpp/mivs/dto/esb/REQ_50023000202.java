package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 手机号联网核查结果查询请求报文
 * @Author: 王鹏
 * @Date: 2019/6/24 11:20
 */
public class REQ_50023000202 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000202(){
        super.txDesc = "手机号码核查信息结果查询";
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
        @JSONField(name = "CHK_START_DATE")
        private String startDt;    // 核查开始时间

        @JSONField(name = "CHK_END_DATE")
        private String endDt; //核查结束时间

        @JSONField(name = "ORIG_CHK_ORG_ID")
        private String origBranchId; //原核查机构号

        @JSONField(name = "ORIG_CHK_TELLER_ID")
        private String origUserId; // 原核查柜员号

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01或mivs.322.001.01 的报文标识号

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.321.001.01或mivs.323.001.01 的报文标识号

        @JSONField(name = "MOBILE_PHONE")
        private String mobNb; // 手机号

        @JSONField(name = "NAME")
        private String nm;		//姓名

        @JSONField(name = "DOCUMENT_TYPE")
        private String idTp;		//证件类型

        @JSONField(name = "DOCUMENT_ID")
        private String id;		//证件号码

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;		//统一社会信用代码

        @JSONField(name = "BIZ_REG_NO")
        private String bizRegNb;		//工商注册号

        public String getStartDt() {
            return startDt;
        }

        public void setStartDt(String startDt) {
            this.startDt = startDt;
        }

        public String getEndDt() {
            return endDt;
        }

        public void setEndDt(String endDt) {
            this.endDt = endDt;
        }

        public String getOrigBranchId() {
            return origBranchId;
        }

        public void setOrigBranchId(String origBranchId) {
            this.origBranchId = origBranchId;
        }

        public String getOrigUserId() {
            return origUserId;
        }

        public void setOrigUserId(String origUserId) {
            this.origUserId = origUserId;
        }

        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }

        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public String getMobNb() {
            return mobNb;
        }

        public void setMobNb(String mobNb) {
            this.mobNb = mobNb;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getIdTp() {
            return idTp;
        }

        public void setIdTp(String idTp) {
            this.idTp = idTp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getBizRegNb() {
            return bizRegNb;
        }

        public void setBizRegNb(String bizRegNb) {
            this.bizRegNb = bizRegNb;
        }

        @Override
        public String toString() {
            return "REQ_BODY{" +
                    "startDt='" + startDt + '\'' +
                    ", endDt='" + endDt + '\'' +
                    ", origBranchId='" + origBranchId + '\'' +
                    ", origUserId='" + origUserId + '\'' +
                    ", mobNb='" + mobNb + '\'' +
                    ", nm='" + nm + '\'' +
                    ", idTp='" + idTp + '\'' +
                    ", id='" + id + '\'' +
                    ", uniSocCdtCd='" + uniSocCdtCd + '\'' +
                    ", bizRegNb='" + bizRegNb + '\'' +
                    '}';
        }
    }

}