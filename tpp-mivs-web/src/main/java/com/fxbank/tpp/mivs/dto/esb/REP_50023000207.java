package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @Description: ESB“纳税信息核查结果疑义反馈”应答报文 mivs.348.001.01
 * @Author: 王鹏
 * @Date: 2019/5/15 14:40
 */
public class REP_50023000207 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_50023000207.REP_BODY repBody = new REP_50023000207.REP_BODY();

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



    public REP_50023000207.REP_BODY getRepBody() {
        return repBody;
    }



    public void setRepBody(REP_50023000207.REP_BODY repBody) {
        this.repBody = repBody;
    }



    public class REP_BODY {

        @JSONField(name = "TRADE_STATUS")
        private String procSts;//申请报文处理状态

        @JSONField(name = "BUSI_PROCESS_CODE")
        private String procCd;//申请报文处理码

        @JSONField(name="RFS_BUSI_BANK_NO")
        private String ptyId;//拒绝业务的参与机构行号

        @JSONField(name = "BUSI_RFS_CODE")
        private String ptyPrcCd;//参与机构业务拒绝码

        @JSONField(name = "BUSI_RFS_INFO")
        private String rjctInf;//申请报文拒绝信息

        @JSONField(name = "DEAL_DATE")
        private String prcDt;//处理日期（终态日期）

        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1;

        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2;

        @JSONField(name = "RESERVE_FIELD3")
        private String remarks3;

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

        public String getPtyId() {
            return ptyId;
        }

        public void setPtyId(String ptyId) {
            this.ptyId = ptyId;
        }

        public String getPtyPrcCd() {
            return ptyPrcCd;
        }

        public void setPtyPrcCd(String ptyPrcCd) {
            this.ptyPrcCd = ptyPrcCd;
        }

        public String getRjctInf() {
            return rjctInf;
        }

        public void setRjctInf(String rjctInf) {
            this.rjctInf = rjctInf;
        }

        public String getPrcDt() {
            return prcDt;
        }

        public void setPrcDt(String prcDt) {
            this.prcDt = prcDt;
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