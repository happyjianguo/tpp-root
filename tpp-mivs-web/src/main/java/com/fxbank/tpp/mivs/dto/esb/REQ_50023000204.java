package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 纳税信息联网核查请求报文
 * @Author: 王鹏
 * @Date: 2019/4/29 9:22
 */
public class REQ_50023000204 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000204.REQ_BODY reqBody;

    public REQ_50023000204(){
        super.txDesc = "纳税信息联网核查";
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


    public REQ_50023000204.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_50023000204.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "COMPANY_NAME")
        private String companyName = null;    //单位名称

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd = null;		//统一社会信用代码

        @JSONField(name = "TXPYR_DIST_NO")
        private String taxPayerId = null;		//纳税人识别号

        @JSONField(name = "OPRTR_NAME")
        private String opNm = null;		//操作员姓名

        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1 = null;		//备用字段1
        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2 = null;		//备用字段2
        @JSONField(name = "RESERVE_FIELD3")
        private String remarks3 = null;		//备用字段3
        public String getRemarks1() {
            return remarks1 = null;
        }
        public void setRemarks1(String remarks1) {
            this.remarks1 = remarks1;
        }
        public String getRemarks2() {
            return remarks2;
        }
        public void setRemarks2(String remarks2) {
            this.remarks2 = remarks2;
        }
        public String getRemarks3() {
            return remarks3;
        }
        public void setRemarks3(String remarks3) {
            this.remarks3 = remarks3;
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

        public String getOpNm() {
            return opNm;
        }

        public void setOpNm(String opNm) {
            this.opNm = opNm;
        }


    }
}