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
        super.txDesc = "手机号码/纳税信息核查结果疑义反馈";
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
        @JSONField(name = "CHK_SYSTEM_ID")
        private String sysInd;    // 核查系统标识 ：手机号为“MIIT”，纳税信息为“CSAT”
        public String getSysInd() {
            return sysInd;
        }
        public void setSysInd(String sysInd) {
            this.sysInd = sysInd;
        }

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01或mivs.322.001.01 的报文标识号
        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }
        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.321.001.01或mivs.323.001.01 的报文标识号
        public String getOrgnlRcvgMsgId() {
            return orgnlDlvrgMsgId;
        }
        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        @JSONField(name = "MOBILE_PHONE")
        private String mobNb; // 手机号码
        public String getMobNb() {
            return mobNb;
        }
        public void setMobNb(String mobNb) {
            this.mobNb = mobNb;
        }

        @JSONField(name = "NAME")
        private String nm; // 姓名
        public String getNm() {
            return nm;
        }
        public void setNm(String nm) {
            this.nm = nm;
        }

        @JSONField(name = "DOCUMENT_TYPE")
        private String idTp; // 证件类型
        public String getIdTp() {
            return idTp;
        }
        public void setIdTp(String idTp) {
            this.idTp = idTp;
        }

        @JSONField(name = "DOCUMENT_ID")
        private String id; // 证件号码
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        @JSONField(name = "COMPANY_NAME")
        private String coNm; // 单位名称
        public String getCoNm() {
            return coNm;
        }
        public void setCoNm(String coNm) {
            this.coNm = coNm;
        }

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd; // 统一社会信用代码
        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }
        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        @JSONField(name = "BIZ_REG_NO")
        private String bizRegNb; // 工商注册号
        public String getBizRegNb() {
            return bizRegNb;
        }
        public void setBizRegNb(String bizRegNb) {
            this.bizRegNb = bizRegNb;
        }

        @JSONField(name = "MOBILE_VER_RESULT")
        private String rslt; // 手机号码核查结果
        public String getRslt() {
            return rslt;
        }
        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcDt; // 数据源日期
        public String getDataResrcDt() {
            return dataResrcDt;
        }
        public void setDataResrcDt(String dataResrcDt) {
            this.dataResrcDt = dataResrcDt;
        }

        @JSONField(name = "TXPYR_INFO_ARRAY")
        private List<TMSGS> arrayMsg;//纳税信息数组

        public List<TMSGS> getArrayMsg() {
            return arrayMsg;
        }
        public void setArrayMsg(List<TMSGS> arrayMsg) {
            this.arrayMsg = arrayMsg;
        }
        public class TMSGS{
            @JSONField(name = "TAX_JDCY_CODE")
            private String txAuthCd;//税务机关代码
            public String getTxAuthCd() {
                return txAuthCd;
            }
            public void setTxAuthCd(String txAuthCd) {
                this.txAuthCd = txAuthCd;
            }

            @JSONField(name = "TAX_JDCY_NAME")
            private String txAuthNm;//税务机关名称
            public String getTxAuthNm() {
                return txAuthNm;
            }
            public void setTxAuthNm(String txAuthNm) {
                this.txAuthNm = txAuthNm;
            }

            @JSONField(name = "TXPYR_STATUS")
            private String txpyrSts;//纳税人状态
            public String getTxpyrSts() {
                return txpyrSts;
            }
            public void setTxpyrSts(String txpyrSts) {
                this.txpyrSts = txpyrSts;
            }
        }

        @JSONField(name = "RESPONSE_INFO")
        private String cntt; // 疑似反馈内容
        public String getCntt() {
            return cntt;
        }
        public void setCntt(String cntt) {
            this.cntt = cntt;
        }

        @JSONField(name = "CONTACT_NAME")
        private String contactNm; // 联系人姓名
        public String getContactNm() {
            return contactNm;
        }
        public void setContactNm(String contactNm) {
            this.contactNm = contactNm;
        }

        @JSONField(name = "CONTACT_TEL")
        private String contactNb; // 联系人电话
        public String getContactNb() {
            return contactNb;
        }
        public void setContactNb(String contactNb) {
            this.contactNb = contactNb;
        }
    }
}