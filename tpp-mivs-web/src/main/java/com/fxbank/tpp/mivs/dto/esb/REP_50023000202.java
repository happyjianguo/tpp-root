package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 纳税信息联网核ESB应答报文
 * @Author: 王鹏
 * @Date: 2019/4/29 9:29.
 */
public class REP_50023000202 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_50023000202.REP_BODY repBody = new REP_50023000202.REP_BODY();

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

    public REP_50023000202.REP_BODY getRepBody() {
        return repBody;
    }

    public void setRepBody(REP_50023000202.REP_BODY repBody) {
        this.repBody = repBody;
    }

    public class REP_BODY {

        @JSONField(name = "TXPYR_INFO_VER_RESULT")
        private String rslt;//纳税核查结果
        public String getRslt() {
            return rslt;
        }
        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        //“数据源日期”为税务总局所返回数据的实际收集日期，目前为一般为 T-3
        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcD;//数据源日期
        public String getDataResrcD() {
            return dataResrcD;
        }
        public void setDataResrcD(String dataResrcD) {
            this.dataResrcD = dataResrcD;
        }

        //循环开始TXPYR_INFO_ARRAY
        @JSONField(name = "TXPYR_INFO_ARRAY")
        private List<TXPYR_INFO_ARRAY> arrayMsg;//纳税信息数组

        public List<TXPYR_INFO_ARRAY> getArrayMsg() {
            return arrayMsg;
        }
        public void setArrayMsg(List<TXPYR_INFO_ARRAY> arrayMsg) {
            this.arrayMsg = arrayMsg;
        }

        //以下信息 当“纳税信息核查结果”为非“MCHD” 时填
        @JSONField(name = "MSG_RFS_STATUS")
        private String procSts;//申请报文拒绝状态
        public String getProcSts() {
            return procSts;
        }
        public void setProcSts(String procSts) {
            this.procSts = procSts;
        }

        @JSONField(name = "MSG_RFS_CODE")
        private String procCd;//申请报文拒绝码
        public String getProcCd() {
            return procCd;
        }

        public void setProcCd(String procCd) {
            this.procCd = procCd;
        }

        @JSONField(name = "MSG_RFS_INFO")
        private String rjctinf;//申请报文拒绝信息
        public String getRjctinf() {
            return rjctinf;
        }
        public void setRjctinf(String rjctinf) {
            this.rjctinf = rjctinf;
        }

    }

    //以下信息 当“纳税信息核查结果”为“MCHD” 时填
    public static class TXPYR_INFO_ARRAY {
        @JSONField(name = "TAX_JDCY_CODE")
        private String txAuthCd;//税务机关代码
        public String getTxAuthCd() {
            return txAuthCd;
        }
        public void setTxAuthCd(String txAuthCd) {
            this.txAuthCd = txAuthCd;
        }

        @JSONField(name = "TAX_JDCY_NAME")
        private String txAuthNm;//税务机关名称
        public String getTxAuthNm() {
            return txAuthNm;
        }
        public void setTxAuthNm(String txAuthNm) {
            this.txAuthNm = txAuthNm;
        }

        @JSONField(name = "TXPYR_STATUS")
        private String txpyrSts;//纳税人状态
        public String getTxpyrSts() {
            return txpyrSts;
        }
        public void setTxpyrSts(String txpyrSts) {
            this.txpyrSts = txpyrSts;
        }
    }
    //循环结束
}
