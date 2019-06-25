package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30011000104 
* @Description: 核心记账返回 
* @author YePuLiang
* @date 2019年4月25日 上午10:34:44 
*  
*/
public class REP_30011000104 extends REP_BASE {
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
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public String getFeeCcy() {
			return feeCcy;
		}
		public void setFeeCcy(String feeCcy) {
			this.feeCcy = feeCcy;
		}
		public String getFeeAmt() {
			return feeAmt;
		}
		public void setFeeAmt(String feeAmt) {
			this.feeAmt = feeAmt;
		}
		
	}	
}
