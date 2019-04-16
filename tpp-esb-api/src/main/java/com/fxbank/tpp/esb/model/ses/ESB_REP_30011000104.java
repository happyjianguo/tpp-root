package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30011000104 extends ESB_BASE {

	private static final long serialVersionUID = 4602016668313419025L;

	@Deprecated
	public ESB_REP_30011000104() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30011000104(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 2180776007618303784L;
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "ACCT_BRANCH")
		private String acctBranch;//开户机构
		@JSONField(name = "AVAIL_BAL")
		private String availBal;//可用余额
		@JSONField(name = "FEE_DETAIL")
		private List<Fee>  feeDetail;//费用明细数组
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
		public String  getAvailBal(){
			return availBal;
		}
		public void setAvailBal(String availBal){
			this.availBal = availBal;
		}
		public List<Fee>  getFeeDetail(){
			return feeDetail;
		}
		public void setFeeDetail(List<Fee> feeDetail){
			this.feeDetail = feeDetail;
		}
		
	}
	
	public static class Fee implements Serializable {
		@JSONField(name = "FEE_TYPE")
		private String feeType;//服务费类型
		@JSONField(name = "FEE_CCY")
		private String feeCcy;//收费币种
		@JSONField(name = "FEE_AMT")
		private String feeAmt;//费用金额
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
