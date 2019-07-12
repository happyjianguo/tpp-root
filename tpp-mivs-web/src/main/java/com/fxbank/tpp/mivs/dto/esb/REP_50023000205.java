package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 纳税信息汇总查询应答
 * @Author: 王鹏
 * @Date: 2019/6/24 10:31
 */
public class REP_50023000205 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_BODY repBody = new REP_BODY();

    public REP_APP_HEAD getRepAppHead() {
        return repAppHead;
    }


    public void setRepAppHead(REP_APP_HEAD repAppHead) {
        this.repAppHead = repAppHead;
    }

    public REP_SYS_HEAD getRepSysHead() {
        return repSysHead;
    }



    public void setRepSysHead(REP_SYS_HEAD repSysHead) {
        this.repSysHead = repSysHead;
    }



    public REP_BODY getRepBody() {
        return repBody;
    }



    public void setRepBody(REP_BODY repBody) {
        this.repBody = repBody;
    }



    public class REP_BODY {
        //循环开始
        @JSONField(name = "TXPYR_INFO_ARRAY")
        private List<resultList> resultList;//查询结果信息

        public List<resultList> getResultList() {
            return resultList;
        }

        public void setResultList(List<resultList> resultList) {
            this.resultList = resultList;
        }

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "resultList=" + resultList +
                    '}';
        }

    }

    public static class resultList {

        @JSONField(name = "ORIG_CHK_DATE")
        private String origTranDate;

        @JSONField(name = "ORIG_CHK_SEQ_NO")
        private String origSeqNo;

        @JSONField(name = "ORIG_CHK_TIME")
        private String origTranTime;

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId;

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId;

        @JSONField(name = "ORIG_INSTG_PTY")
        private String origInstgPty;

        @JSONField(name = "ORIG_CHK_ORG_ID")
        private String origBranchId;

        @JSONField(name = "ORIG_CHK_TELLER_ID")
        private String origUserId;

        @JSONField(name = "COMPANY_NAME")
        private String coNm;

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;

        @JSONField(name = "TXPYR_DIST_NO")
        private String txpyrIdNb;

        @JSONField(name = "MOBILE_VER_RESULT")
        private String rslt;

        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcDt;

        @JSONField(name = "TXPMT_INF_CNT")
        private Integer txpmtInfCnt;

        @JSONField(name = "MSG_RFS_STATUS")
        private String procSts;

        @JSONField(name = "MSG_RFS_CODE")
        private String procCd;

        @JSONField(name = "MSG_RFS_INFO")
        private String rjctinf;

        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1;

        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2;

        @JSONField(name = "RESERVE_FIELD3")
        private String remarks3;

        public String getOrigTranDate() {
            return origTranDate;
        }

        public void setOrigTranDate(String origTranDate) {
            this.origTranDate = origTranDate;
        }

        public String getOrigSeqNo() {
            return origSeqNo;
        }

        public void setOrigSeqNo(String origSeqNo) {
            this.origSeqNo = origSeqNo;
        }

        public String getOrigTranTime() {
            return origTranTime;
        }

        public void setOrigTranTime(String origTranTime) {
            this.origTranTime = origTranTime;
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

        public String getOrigInstgPty() {
            return origInstgPty;
        }

        public void setOrigInstgPty(String origInstgPty) {
            this.origInstgPty = origInstgPty;
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

        public String getCoNm() {
            return coNm;
        }

        public void setCoNm(String coNm) {
            this.coNm = coNm;
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

        public Integer getTxpmtInfCnt() {
            return txpmtInfCnt;
        }

        public void setTxpmtInfCnt(Integer txpmtInfCnt) {
            this.txpmtInfCnt = txpmtInfCnt;
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