package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

import java.util.List;

/**
 * @Description: 纳税信息核查结果汇总查询
 * @Author: 王鹏
 * @Date: 2019/6/24 10:31
 */
public class REQ_50023000205 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000205(){
        super.txDesc = "纳税信息核查结果汇总查询";
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

        @JSONField(name = "COMPANY_NAME")
        private String companyName;    //单位名称

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;        //统一社会信用代码

        @JSONField(name = "TXPYR_DIST_NO")
        private String taxPayerId;        //纳税人识别号

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

        public String getTaxPayerId() {
            return taxPayerId;
        }

        public void setTaxPayerId(String taxPayerId) {
            this.taxPayerId = taxPayerId;
        }
    }
}