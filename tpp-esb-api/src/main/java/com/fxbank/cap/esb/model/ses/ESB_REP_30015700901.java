package com.fxbank.cap.esb.model.ses;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

import java.io.Serializable;
import java.util.List;

public class ESB_REP_30015700901 extends ESB_BASE {

    private static final long serialVersionUID = 2148118368970572509L;

    @Deprecated
    public ESB_REP_30015700901() {
        super(null, 0, 0, 0);
    }

    public ESB_REP_30015700901(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @JSONField(name = "APP_HEAD")
    private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private ESB_REP_30015700901.REP_BODY repBody;

    public class REP_BODY implements Serializable {
        private static final long serialVersionUID = -5152429692352575767L;
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
        private static final long serialVersionUID = 2467024056465339366L;
        @JSONField(name = "BASE_ACCT_NO")
        private String acctNo;//账号
        @JSONField(name = "REF_MAX")
        private String refMax;//核心最大流水号

        public String getRefMax() {
			return refMax;
		}

		public void setRefMax(String refMax) {
			this.refMax = refMax;
		}

        public String getAcctNo() {
            return acctNo;
        }

        public void setAcctNo(String acctNo) {
            this.acctNo = acctNo;
        }
    }

    public ESB_REP_APP_HEAD getRepAppHead() {
        return repAppHead;
    }

    public void setRepAppHead(ESB_REP_APP_HEAD repAppHead) {
        this.repAppHead = repAppHead;
    }

    public ESB_REP_SYS_HEAD getRepSysHead() {
        return repSysHead;
    }

    public void setRepSysHead(ESB_REP_SYS_HEAD repSysHead) {
        this.repSysHead = repSysHead;
    }

    public REP_BODY getRepBody() {
        return repBody;
    }

    public void setRepBody(REP_BODY repBody) {
        this.repBody = repBody;
    }
}
