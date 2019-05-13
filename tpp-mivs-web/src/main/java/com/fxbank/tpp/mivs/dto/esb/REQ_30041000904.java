package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: ESB“手机号码核查结果疑义反馈”请求报文
 * @Author: 王鹏
 * @Date: 2019/5/5 8:25
 */
public class REQ_30041000904 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_30041000904.REQ_BODY reqBody;

    public REQ_30041000904(){
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

    public REQ_30041000904.REQ_BODY getReqBody() {
        return reqBody;
    }

    public void setReqBody(REQ_30041000904.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {
        @JSONField(name = "SysInd")
        private String sysInd;    // 核查系统标识 ：固定填写“MIIT”
        public String getSysInd() {
            return sysInd;
        }
        public void setSysInd(String sysInd) {
            this.sysInd = sysInd;
        }

        @JSONField(name = "OrgnlDlvrgMsgId")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01 的报文标识号
        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }
        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        @JSONField(name = "MobNb")
        private String mobNb; // 手机号码
        public String getMobNb() {
            return mobNb;
        }
        public void setMobNb(String mobNb) {
            this.mobNb = mobNb;
        }

        @JSONField(name = "Nm")
        private String nm; // 姓名
        public String getNm() {
            return nm;
        }
        public void setNm(String nm) {
            this.nm = nm;
        }

        @JSONField(name = "IdTp")
        private String idTp; // 证件类型
        public String getIdTp() {
            return idTp;
        }
        public void setIdTp(String idTp) {
            this.idTp = idTp;
        }

        @JSONField(name = "Id")
        private String id; // 证件号码
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd; // 统一社会信用代码
        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }
        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        @JSONField(name = "BizRegNb")
        private String bizRegNb; // 工商注册号
        public String getBizRegNb() {
            return bizRegNb;
        }
        public void setBizRegNb(String bizRegNb) {
            this.bizRegNb = bizRegNb;
        }

        @JSONField(name = "Rslt")
        private String rslt; // 手机号码核查结果
        public String getRslt() {
            return rslt;
        }
        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        @JSONField(name = "Cntt")
        private String cntt; // 疑似反馈内容
        public String getCntt() {
            return cntt;
        }
        public void setCntt(String cntt) {
            this.cntt = cntt;
        }

        @JSONField(name = "ContactNm")
        private String contactNm; // 联系人姓名
        public String getContactNm() {
            return contactNm;
        }
        public void setContactNm(String contactNm) {
            this.contactNm = contactNm;
        }

        @JSONField(name = "ContactNb")
        private String contactNb; // 联系人电话
        public String getContactNb() {
            return contactNb;
        }
        public void setContactNb(String contactNb) {
            this.contactNb = contactNb;
        }
    }
}