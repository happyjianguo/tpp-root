package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

import java.util.List;

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

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01或mivs.322.001.01 的报文标识号

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.321.001.01或mivs.323.001.01 的报文标识号

        @JSONField(name = "COMPANY_NAME")
        private String companyName;    //单位名称

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;		//统一社会信用代码

        @JSONField(name = "TXPYR_DIST_NO")
        private String txpyrIdNb;		//纳税人识别号

        @JSONField(name = "TXPYR_INFO_VER_RESULT")
        private String rslt;//纳税核查结果

        //“数据源日期”为税务总局所返回数据的实际收集日期，目前为一般为 T-3
        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcDt;//数据源日期

        //循环开始TXPYR_INFO_ARRAY
        @JSONField(name = "TXPYR_INFO_ARRAY")
        private List<txpyrInfoArray> txpyrInfoArrayMsg;//纳税信息数组

        //以下信息 当“纳税信息核查结果”为非“MCHD” 时填
        @JSONField(name = "MSG_RFS_STATUS")
        private String procSts;//申请报文拒绝状态

        @JSONField(name = "MSG_RFS_CODE")
        private String procCd;//申请报文拒绝码

        @JSONField(name = "MSG_RFS_INFO")
        private String rjctinf;//申请报文拒绝信息

        @JSONField(name = "RESPONSE_INFO")
        private String cntt; // 疑似反馈内容

        @JSONField(name = "CONTACT_NAME")
        private String contactNm; // 联系人姓名

        @JSONField(name = "CONTACT_TEL")
        private String contactNb; // 联系人电话

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

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getTxpyrIdNb() {
            return txpyrIdNb;
        }

        public void setTxpyrIdNb(String txpyrIdNb) {
            this.txpyrIdNb = txpyrIdNb;
        }

        public String getRslt() {
            return rslt;
        }

        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        public String getDataResrcDt() {
            return dataResrcDt;
        }

        public void setDataResrcDt(String dataResrcDt) {
            this.dataResrcDt = dataResrcDt;
        }

        public List<txpyrInfoArray> getTxpyrInfoArrayMsg() {
            return txpyrInfoArrayMsg;
        }

        public void setTxpyrInfoArrayMsg(List<txpyrInfoArray> txpyrInfoArrayMsg) {
            this.txpyrInfoArrayMsg = txpyrInfoArrayMsg;
        }

        public String getProcSts() {
            return procSts;
        }

        public void setProcSts(String procSts) {
            this.procSts = procSts;
        }

        public String getProcCd() {
            return procCd;
        }

        public void setProcCd(String procCd) {
            this.procCd = procCd;
        }

        public String getRjctinf() {
            return rjctinf;
        }

        public void setRjctinf(String rjctinf) {
            this.rjctinf = rjctinf;
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
    }

    public static class txpyrInfoArray{
        @JSONField(name = "TAX_JDCY_CODE")
        private String txAuthCd;//税务机关代码

        @JSONField(name = "TAX_JDCY_NAME")
        private String txAuthNm;//税务机关名称

        @JSONField(name = "TXPYR_STATUS")
        private String txpyrSts;//纳税人状态

        public String getTxAuthCd() {
            return txAuthCd;
        }

        public void setTxAuthCd(String txAuthCd) {
            this.txAuthCd = txAuthCd;
        }

        public String getTxAuthNm() {
            return txAuthNm;
        }

        public void setTxAuthNm(String txAuthNm) {
            this.txAuthNm = txAuthNm;
        }

        public String getTxpyrSts() {
            return txpyrSts;
        }

        public void setTxpyrSts(String txpyrSts) {
            this.txpyrSts = txpyrSts;
        }
    }
}