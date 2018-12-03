package com.fxbank.cap.esb.model.ses;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

import java.io.Serializable;
import java.util.List;

public class ESB_REQ_30015700901 extends ESB_BASE {


    private static final long serialVersionUID = -981622786855136816L;

    public ESB_REQ_30015700901(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.macEnable = false;
    }

    @JSONField(name = "APP_HEAD")
    private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300157009", "01");
    @JSONField(name = "BODY")
    private ESB_REQ_30015700901.REQ_BODY reqBody = new ESB_REQ_30015700901.REQ_BODY();

    public class REQ_BODY implements Serializable {
        private static final long serialVersionUID = -3428060788563231854L;
        @JSONField(name = "GJ_QUERY_ARRAY")
        private List<QUERY> queryArray;//公积金账户信息数组

        public List<QUERY> getQueryArray() {
            return queryArray;
        }

        public void setQueryArray(List<QUERY> queryArray) {
            this.queryArray = queryArray;
        }
    }

    public static class QUERY implements Serializable {
        private static final long serialVersionUID = 859369496326341467L;
        @JSONField(name = "REFERENCE")
        private String reference;//核心流水号
        @JSONField(name = "BASE_ACCT_NO")
        private String acctNo;//账号
        @JSONField(name = "ACCT_SEQ_NO")
        private String acctSeqNo;//账号

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getAcctNo() {
            return acctNo;
        }

        public void setAcctNo(String acctNo) {
            this.acctNo = acctNo;
        }

        public String getAcctSeqNo() {
            return acctSeqNo;
        }

        public void setAcctSeqNo(String acctSeqNo) {
            this.acctSeqNo = acctSeqNo;
        }
    }

    public ESB_REQ_30015700901.REQ_BODY getReqBody() {
        return reqBody;
    }

    public void setReqBody(ESB_REQ_30015700901.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public ESB_REQ_APP_HEAD getReqAppHead() {
        return reqAppHead;
    }

    public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
        this.reqAppHead = reqAppHead;
    }

    public ESB_REQ_SYS_HEAD getReqSysHead() {
        return reqSysHead;
    }

    public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
        this.reqSysHead = reqSysHead;
    }

}
