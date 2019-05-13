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
public class REQ_30041000902 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_30041000902.REQ_BODY reqBody;

    public REQ_30041000902(){
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


    public REQ_30041000902.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_30041000902.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "CompanyName")
        private String companyName;    //单位名称

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd;		//统一社会信用代码

        @JSONField(name = "TaxPayerId")
        private String taxPayerId;		//纳税人识别号

        @JSONField(name = "OpNm")
        private String opNm;		//操作员姓名

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