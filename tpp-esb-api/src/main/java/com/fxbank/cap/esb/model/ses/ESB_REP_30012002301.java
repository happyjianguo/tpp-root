package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30012002301 extends ESB_BASE {

	private static final long serialVersionUID = 2475786657083291409L;

	@Deprecated
	public ESB_REP_30012002301() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30012002301(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 4376458192293097825L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "REASON_CODE")
		private String reasonCode;//账户用途
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getReasonCode(){
			return reasonCode;
		}
		public void setReasonCode(String reasonCode){
			this.reasonCode = reasonCode;
		}
		
	}
	
	

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
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

}
