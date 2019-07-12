package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/2 16:38
 */
public class REP_50023000209 extends REP_BASE {

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

        @JSONField(name = "OrigInstgPty")
        private String origInstgPty;

        @JSONField(name = "OrigBranchId")
        private String origBranchId;

        @JSONField(name = "OrigUserId")
        private String origUserId;

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
