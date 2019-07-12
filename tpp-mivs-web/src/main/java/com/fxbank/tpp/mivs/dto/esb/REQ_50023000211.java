package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 登记信息核查反馈
 * @Author: 王鹏
 * @Date: 2019/7/2 20:10
 */
public class REQ_50023000211 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000211.REQ_BODY reqBody;

    public REQ_50023000211(){
        super.txDesc = "登记信息核查结果反馈";
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


    public REQ_50023000211.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_50023000211.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.324.001.01

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.325.001.01

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;        //统一社会信用代码

        @JSONField(name = "CORP_NAME")
        private String entNm;    //企业名称

        @JSONField(name = "COMPANY_NM")
        private String nmOfLglPrsn;        //法定代表人或单位负责人姓名

        @JSONField(name = "REP_DOCUMENT_ID")
        private String idOfLglPrsn;        //法定代表人或单位负责人身份证件号

        @JSONField(name = "FONT_NAME")
        private String traNm;        //字号名称

        @JSONField(name = "OPRTNG_NAME")
        private String nm;        //经营者姓名

        @JSONField(name = "OPRTNG_DOCUMENT_ID")
        private String id;        //经营者证件号

        @JSONField(name = "ITEM_INFO_RESULT")
        private String rslt;        //登记信息核查结果

        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcDt;//数据源日期

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

        public String getEntNm() {
            return entNm;
        }

        public void setEntNm(String entNm) {
            this.entNm = entNm;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getNmOfLglPrsn() {
            return nmOfLglPrsn;
        }

        public void setNmOfLglPrsn(String nmOfLglPrsn) {
            this.nmOfLglPrsn = nmOfLglPrsn;
        }

        public String getIdOfLglPrsn() {
            return idOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            this.idOfLglPrsn = idOfLglPrsn;
        }

        public String getTraNm() {
            return traNm;
        }

        public void setTraNm(String traNm) {
            this.traNm = traNm;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}