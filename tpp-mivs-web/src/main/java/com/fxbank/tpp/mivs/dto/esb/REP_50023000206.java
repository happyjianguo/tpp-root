package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 纳税信息核查结果明细查询应答
 * @Author: 王鹏
 * @Date: 2019/7/11 7:14
 */
public class REP_50023000206 extends REP_BASE {

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

    public static class REP_BODY {
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
    }
}