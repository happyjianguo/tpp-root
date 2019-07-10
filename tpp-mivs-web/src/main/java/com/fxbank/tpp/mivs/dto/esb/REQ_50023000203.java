package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

import java.util.List;

/**
 * @Description: ESB“手机号码核查结果疑义反馈”请求报文
 * @Author: 王鹏
 * @Date: 2019/5/5 8:25
 */
public class REQ_50023000203 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000203.REQ_BODY reqBody;

    public REQ_50023000203(){
        super.txDesc = "手机号码核查结果疑义反馈";
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

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01或mivs.322.001.01 的报文标识号

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.321.001.01或mivs.323.001.01 的报文标识号

        @JSONField(name = "MOBILE_PHONE")
        private String mobNb; // 手机号码

        @JSONField(name = "NAME")
        private String nm; // 姓名

        @JSONField(name = "DOCUMENT_TYPE")
        private String idTp; // 证件类型

        @JSONField(name = "DOCUMENT_ID")
        private String id; // 证件号码

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd; // 统一社会信用代码

        @JSONField(name = "BIZ_REG_NO")
        private String bizRegNb; // 工商注册号

        @JSONField(name = "MOBILE_VER_RESULT")
        private String rslt; // 手机号码核查结果

        @JSONField(name = "RESPONSE_INFO")
        private String cntt; // 疑似反馈内容

        @JSONField(name = "CONTACT_NAME")
        private String contactNm; // 联系人姓名

        @JSONField(name = "CONTACT_TEL")
        private String contactNb; // 联系人电话

        @JSONField(name = "RESERVE_FIELD1")
        private String remark1; // 备用字段1

        @JSONField(name = "RESERVE_FIELD2")
        private String remark2; // 备用字段2

        @JSONField(name = "RESERVE_FIELD3")
        private String remark3; // 备用字段3

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

        public String getRslt() {
            return rslt;
        }

        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        public String getCntt() {
            return cntt;
        }

        public void setCntt(String cntt) {
            this.cntt = cntt;
        }

        public String getContactNm() {
            return contactNm;
        }

        public void setContactNm(String contactNm) {
            this.contactNm = contactNm;
        }

        public String getContactNb() {
            return contactNb;
        }

        public void setContactNb(String contactNb) {
            this.contactNb = contactNb;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public String getRemark3() {
            return remark3;
        }

        public void setRemark3(String remark3) {
            this.remark3 = remark3;
        }
    }
}