package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description:
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
        @JSONField(name = "ResultList")
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

        @JSONField(name = "OrigTranDate")
        private String origTranDate;

        @JSONField(name = "OrigSeqNo")
        private String origSeqNo;

        @JSONField(name = "OrigTranTime")
        private String origTranTime;

        @JSONField(name = "OrgnlDlvrgMsgId")
        private String orgnlDlvrgMsgId;

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name = "OrigBranchId")
        private String origBranchId;

        @JSONField(name = "OrigUserId")
        private String origUserId;

        @JSONField(name = "CoNm")
        private String coNm;

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd;

        @JSONField(name = "TxpyrIdNb")
        private String txpyrIdNb;

        @JSONField(name = "Rslt")
        private String rslt;

        @JSONField(name = "DataResrcDt")
        private String dataResrcDt;

        //循环开始TXPYR_INFO_ARRAY
        @JSONField(name = "TXPYR_INFO_ARRAY")
        private List<TXPYR_INFO_ARRAY> txpmtInf;//纳税信息数组

        @JSONField(name = "ProcSts")
        private String procSts;

        @JSONField(name = "ProcCd")
        private String procCd;

        @JSONField(name = "Rjctinf")
        private String rjctinf;

        @JSONField(name = "Remarks1")
        private String remarks1;

        @JSONField(name = "Remarks2")
        private String remarks2;

        @JSONField(name = "Remarks3")
        private String remarks3;

        public static class TXPYR_INFO_ARRAY {
            @JSONField(name = "TXPMT_INF_NB")
            private Integer txpmtInfNb;//税务机关代码

            @JSONField(name = "TAX_JDCY_CODE")
            private String txAuthCd;//税务机关代码

            @JSONField(name = "TAX_JDCY_NAME")
            private String txAuthNm;//税务机关名称

            @JSONField(name = "TXPYR_STATUS")
            private String txpyrSts;//纳税人状态

            public Integer getTxpmtInfNb() {
                return txpmtInfNb;
            }

            public void setTxpmtInfNb(Integer txpmtInfNb) {
                this.txpmtInfNb = txpmtInfNb;
            }

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

        public List<TXPYR_INFO_ARRAY> getTxpmtInf() {
            return txpmtInf;
        }

        public void setTxpmtInf(List<TXPYR_INFO_ARRAY> txpmtInf) {
            this.txpmtInf = txpmtInf;
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

        @Override
        public String toString() {
            return "resultList{" +
                    "origTranDate='" + origTranDate + '\'' +
                    ", origSeqNo='" + origSeqNo + '\'' +
                    ", origTranTime='" + origTranTime + '\'' +
                    ", orgnlDlvrgMsgId='" + orgnlDlvrgMsgId + '\'' +
                    ", orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", coNm='" + coNm + '\'' +
                    ", uniSocCdtCd='" + uniSocCdtCd + '\'' +
                    ", txpyrIdNb='" + txpyrIdNb + '\'' +
                    ", rslt='" + rslt + '\'' +
                    ", dataResrcDt='" + dataResrcDt + '\'' +
                    ", txpmtInf=" + txpmtInf +
                    ", procSts='" + procSts + '\'' +
                    ", procCd='" + procCd + '\'' +
                    ", rjctinf='" + rjctinf + '\'' +
                    ", remarks1='" + remarks1 + '\'' +
                    ", remarks2='" + remarks2 + '\'' +
                    ", remarks3='" + remarks3 + '\'' +
                    '}';
        }
    }
}