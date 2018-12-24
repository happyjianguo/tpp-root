package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30011000103 extends ESB_BASE {


	private static final long serialVersionUID = -2806490986315438335L;

	@Deprecated
	public ESB_REP_30011000103() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30011000103(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 6804012674359932651L;
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "ACCT_BRANCH")
		private String acctBranch;//开户机构
		@JSONField(name = "ACCOUNTING_BRANCH")
		private String accountingBranch;//记账机构
		@JSONField(name = "BALANCE")
		private String balance;//余额
		@JSONField(name = "ACCT_RESULT")
		private String acctResult;//记账结果
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getAcctBranch(){
			return acctBranch;
		}
		public void setAcctBranch(String acctBranch){
			this.acctBranch = acctBranch;
		}
		public String  getAccountingBranch(){
			return accountingBranch;
		}
		public void setAccountingBranch(String accountingBranch){
			this.accountingBranch = accountingBranch;
		}
		public String  getBalance(){
			return balance;
		}
		public void setBalance(String balance){
			this.balance = balance;
		}
		public String getAcctResult() {
			return acctResult;
		}
		public void setAcctResult(String acctResult) {
			this.acctResult = acctResult;
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
