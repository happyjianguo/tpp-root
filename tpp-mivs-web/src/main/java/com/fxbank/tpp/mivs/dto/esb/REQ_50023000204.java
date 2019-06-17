package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 企业开销户状态反馈ESB请求报文
 * @Author: 王鹏
 * @Date: 2019/6/6 14:38
 */
public class REQ_50023000204 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000204.REQ_BODY reqBody;

    public REQ_50023000204(){
        super.txDesc = "企业开销户状态反馈";
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
        @JSONField(name = "ENT_NAME")
        private String entNm;    //企业名称
        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;        //统一社会信用代码
        @JSONField(name = "ACCT_STS")
        private String acctSts;        //账户状态标识
        @JSONField(name = "ID_OF_LGL_PRSN")
        private String idOfLglPrsn;    //法定代表人或单位负责人身份证件号
        @JSONField(name = "CHNG_DT")
        private String chngDt;    //变更日期
        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1;        //备用字段1
        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2;        //备用字段2
        @JSONField(name = "RESERVE_FIELD3")
        private String remarks3;        //备用字段3

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

        public String getAcctSts() {
            return acctSts;
        }

        public void setAcctSts(String acctSts) {
            this.acctSts = acctSts;
        }

        public String getIdOfLglPrsn() {
            return idOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            this.idOfLglPrsn = idOfLglPrsn;
        }

        public String getChngDt() {
            return chngDt;
        }

        public void setChngDt(String chngDt) {
            this.chngDt = chngDt;
        }

        public String getRemarks1() {
            return remarks1;
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
    }

}