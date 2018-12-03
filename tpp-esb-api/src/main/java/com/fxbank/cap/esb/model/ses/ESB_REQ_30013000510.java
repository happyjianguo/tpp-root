package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30013000510 extends ESB_BASE {

	private static final long serialVersionUID = 3399186431822742910L;

	public ESB_REQ_30013000510 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300130005", "10");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 6545592317314810903L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "ACCT_TYPE")
		private String acctType;//账户类型
		@JSONField(name = "CURR_IDEN ")
		private String currIden ;//钞汇鉴别
		@JSONField(name = "FIXED_TYPE")
		private String fixedType;//定期类型
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;//证件类型
		@JSONField(name = "DOCUMENT_ID")
		private String documentId;//证件号码
		@JSONField(name = "ISS_COUNTRY")
		private String issCountry;//签发国家
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "BRANCH")
		private String branch;//开户机构
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "ACCT_OPEN_START_DATE")
		private String acctOpenStartDate;//开户起始日期
		@JSONField(name = "ACCT_OPEN_END_DATE")
		private String acctOpenEndDate;//开户终止日期
		@JSONField(name = "ACCT_MATURITY_START_DATE")
		private String acctMaturityStartDate;//到期起始日期
		@JSONField(name = "ACCT_MATURITY_END_DATE")
		private String acctMaturityEndDate;//到期终止日期
		@JSONField(name = "IS_INDIVIDUAL")
		private String isIndividual;//是否对私客户
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序列号
		
	public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getCcy(){
			return ccy;
		}
		public void setCcy(String ccy){
			this.ccy = ccy;
		}
		public String  getAcctType(){
			return acctType;
		}
		public void setAcctType(String acctType){
			this.acctType = acctType;
		}
		public String  getCurrIden (){
			return currIden ;
		}
		public void setCurrIden (String currIden ){
			this.currIden  = currIden ;
		}
		public String  getFixedType(){
			return fixedType;
		}
		public void setFixedType(String fixedType){
			this.fixedType = fixedType;
		}
		public String  getDocumentType(){
			return documentType;
		}
		public void setDocumentType(String documentType){
			this.documentType = documentType;
		}
		public String  getDocumentId(){
			return documentId;
		}
		public void setDocumentId(String documentId){
			this.documentId = documentId;
		}
		public String  getIssCountry(){
			return issCountry;
		}
		public void setIssCountry(String issCountry){
			this.issCountry = issCountry;
		}
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getBranch(){
			return branch;
		}
		public void setBranch(String branch){
			this.branch = branch;
		}
		public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
		}
		public String  getAcctOpenStartDate(){
			return acctOpenStartDate;
		}
		public void setAcctOpenStartDate(String acctOpenStartDate){
			this.acctOpenStartDate = acctOpenStartDate;
		}
		public String  getAcctOpenEndDate(){
			return acctOpenEndDate;
		}
		public void setAcctOpenEndDate(String acctOpenEndDate){
			this.acctOpenEndDate = acctOpenEndDate;
		}
		public String  getAcctMaturityStartDate(){
			return acctMaturityStartDate;
		}
		public void setAcctMaturityStartDate(String acctMaturityStartDate){
			this.acctMaturityStartDate = acctMaturityStartDate;
		}
		public String  getAcctMaturityEndDate(){
			return acctMaturityEndDate;
		}
		public void setAcctMaturityEndDate(String acctMaturityEndDate){
			this.acctMaturityEndDate = acctMaturityEndDate;
		}
		public String  getIsIndividual(){
			return isIndividual;
		}
		public void setIsIndividual(String isIndividual){
			this.isIndividual = isIndividual;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		
	}
	
	

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
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