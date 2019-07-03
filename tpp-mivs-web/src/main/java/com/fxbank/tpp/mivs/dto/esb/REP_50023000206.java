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
public class REP_50023000206 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_50023000206.REP_BODY repBody = new REP_50023000206.REP_BODY();

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



    public REP_50023000206.REP_BODY getRepBody() {
        return repBody;
    }



    public void setRepBody(REP_50023000206.REP_BODY repBody) {
        this.repBody = repBody;
    }



    public class REP_BODY {

        @JSONField(name = "ProcSts")
        private String procSts;//申请报文处理状态

        @JSONField(name = "ProcCd")
        private String procCd;//申请报文处理码

        @JSONField(name="PtyId")
        private String ptyId;//拒绝业务的参与机构行号

        @JSONField(name = "PtyPrcCd")
        private String ptyPrcCd;//参与机构业务拒绝码

        @JSONField(name = "RjctInf")
        private String rjctInf;//申请报文拒绝信息

        @JSONField(name = "PrcDt")
        private String prcDt;//处理日期（终态日期）

        @JSONField(name = "NetgRnd")
        private String netgRnd;//轧差场次

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

        public String getNetgRnd() {
            return netgRnd;
        }

        public void setNetgRnd(String netgRnd) {
            this.netgRnd = netgRnd;
        }
    }
}