package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @Description: 企业信息联网核查查业务受理时间查询ESB应答报文
 * @Author: 王鹏
 * @Date: 2019/4/30 11:16
 */
public class REP_50023000203 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_50023000203.REP_BODY repBody = new REP_50023000203.REP_BODY();

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



    public REP_50023000203.REP_BODY getRepBody() {
        return repBody;
    }



    public void setRepBody(REP_50023000203.REP_BODY repBody) {
        this.repBody = repBody;
    }



    public class REP_BODY {

        @JSONField(name = "OrgnlQueDt")
        private String orgnlQueDt;//纳税核查结果

        @JSONField(name = "ProcSts")
        private String procSts;//申请报文处理状态

        @JSONField(name = "ProcCd")
        private String procCd;//申请报文处理码

        @JSONField(name = "RjctInf")
        private String rjctInf;//申请报文拒绝信息

        @JSONField(name = "SysInd")
        private String sysInd;//核查系统标识

        @JSONField(name = "SvcInd")
        private String svcInd;//

        @JSONField(name = "SysOpTm")
        private String sysOpTm;//

        @JSONField(name = "SysClTm")
        private String sysClTm;//

        public String getOrgnlQueDt() {
            return orgnlQueDt;
        }

        public void setOrgnlQueDt(String orgnlQueDt) {
            this.orgnlQueDt = orgnlQueDt;
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

        public String getRjctInf() {
            return rjctInf;
        }

        public void setRjctInf(String rjctInf) {
            this.rjctInf = rjctInf;
        }

        public String getSysInd() {
            return sysInd;
        }

        public void setSysInd(String sysInd) {
            this.sysInd = sysInd;
        }

        public String getSvcInd() {
            return svcInd;
        }

        public void setSvcInd(String svcInd) {
            this.svcInd = svcInd;
        }
        public String getSysOpTm() {
            return sysOpTm;
        }

        public void setSysOpTm(String sysOpTm) {
            this.sysOpTm = sysOpTm;
        }
        public String getSysClTm() {
            return sysClTm;
        }

        public void setSysClTm(String sysClTm) {
            this.sysClTm = sysClTm;
        }
    }
}
