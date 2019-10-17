package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @Description: 本行柜面交行卡余额查询
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:18:38
 */
public class REP_30063001201 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();
	
	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();
	
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

	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		@JSONField(name = "BAL_T")
		private Double bal;	//余额
		
		@JSONField(name = "NM_T")
		private String acctName;	//名称

		 /**
         * @return the bal
         */
        public Double getBal() {
            return bal;
        }

        /**
         * @param bal the bal to set
         */
        public void setBal(Double bal) {
            this.bal = bal;
        }
		
        /**
         * @return the acctName
         */
        public String getAcctName() {
            return acctName;
        }

        /**
         * @param acctName the acctName to set
         */
        public void setAcctName(String acctName) {
            this.acctName = acctName;
        }

	}

}