package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @Description: 企业联网核查结果查询应答报文
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

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId;//原申请报文标识号
        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }
        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId;//数据源日期
        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }
        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        //循环开始TXPYR_INFO_ARRAY
        //以下信息 当“纳税信息核查结果”为“MCHD” 时填
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

        @JSONField(name = "TXPYR_STATUS")
        private String txpyrSts;//纳税人状态
        //循环结束

        //以下信息 当“纳税信息核查结果”为非“MCHD” 时填
        @JSONField(name = "MSG_RFS_STATUS")
        private String procSts;//申请报文拒绝状态

        @JSONField(name = "MSG_RFS_CODE")
        private String procCd;//申请报文拒绝码

        @JSONField(name = "MSG_RFS_INFO")
        private String rjctinf;//申请报文拒绝信息

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

    }
}
