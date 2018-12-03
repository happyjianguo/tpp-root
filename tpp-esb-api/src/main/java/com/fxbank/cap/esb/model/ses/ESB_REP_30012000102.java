package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30012000102 extends ESB_BASE {

	private static final long serialVersionUID = 526868370631021831L;

	@Deprecated
	public ESB_REP_30012000102() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30012000102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 9207068847111171237L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "PROD_TYPE_DESC")
		private String prodTypeDesc;//产品类型描述
		@JSONField(name = "ACCT_CCY")
		private String acctCcy;//账户币种
		@JSONField(name = "ACCT_OPEN_DATE")
		private String acctOpenDate;//开户日期
		@JSONField(name = "MATURITY_DATE")
		private String maturityDate;//到期日
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "REASON_CODE")
		private String reasonCode;//账户用途
		@JSONField(name = "ACTUAL_BAL")
		private String actualBal;//实际余额
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		
		public String getAcctName() {
			return acctName;
		}
		public void setAcctName(String acctName) {
			this.acctName = acctName;
		}
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
		}
		public String  getProdTypeDesc(){
			return prodTypeDesc;
		}
		public void setProdTypeDesc(String prodTypeDesc){
			this.prodTypeDesc = prodTypeDesc;
		}
		public String  getAcctCcy(){
			return acctCcy;
		}
		public void setAcctCcy(String acctCcy){
			this.acctCcy = acctCcy;
		}
		public String  getAcctOpenDate(){
			return acctOpenDate;
		}
		public void setAcctOpenDate(String acctOpenDate){
			this.acctOpenDate = acctOpenDate;
		}
		public String  getMaturityDate(){
			return maturityDate;
		}
		public void setMaturityDate(String maturityDate){
			this.maturityDate = maturityDate;
		}
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getReasonCode(){
			return reasonCode;
		}
		public void setReasonCode(String reasonCode){
			this.reasonCode = reasonCode;
		}
		public String  getActualBal(){
			return actualBal;
		}
		public void setActualBal(String actualBal){
			this.actualBal = actualBal;
		}
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
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
