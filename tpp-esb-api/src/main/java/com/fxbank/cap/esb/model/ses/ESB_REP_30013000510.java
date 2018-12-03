package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30013000510 extends ESB_BASE {

	private static final long serialVersionUID = 6346767611394103138L;

	@Deprecated
	public ESB_REP_30013000510() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013000510(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

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

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -8363195927870772248L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//户名
		@JSONField(name = "ACCT_BAL")
		private String acctBal;//账户余额
		@JSONField(name = "ACCT_REST_BAL")
		private String acctRestBal;//账户可用余额
		@JSONField(name = "ACCT_OVER_BAL")
		private String acctOverBal;//账户透支金额
		@JSONField(name = "ACCT_STATUS")
		private String acctStatus;//账户状态
		@JSONField(name = "TOTAL_NUM")
		private String totalNum;//总笔数
		@JSONField(name = "ACCT_INFO_ARRAY")
		private List<AcctInfo>  acctInfoArray;//账户详细信息数组
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getAcctBal(){
			return acctBal;
		}
		public void setAcctBal(String acctBal){
			this.acctBal = acctBal;
		}
		public String  getAcctRestBal(){
			return acctRestBal;
		}
		public void setAcctRestBal(String acctRestBal){
			this.acctRestBal = acctRestBal;
		}
		public String  getAcctOverBal(){
			return acctOverBal;
		}
		public void setAcctOverBal(String acctOverBal){
			this.acctOverBal = acctOverBal;
		}
		public String  getAcctStatus(){
			return acctStatus;
		}
		public void setAcctStatus(String acctStatus){
			this.acctStatus = acctStatus;
		}
		public String  getTotalNum(){
			return totalNum;
		}
		public void setTotalNum(String totalNum){
			this.totalNum = totalNum;
		}
		public List<AcctInfo>  getAcctInfoArray(){
			return acctInfoArray;
		}
		public void setAcctInfoArray(List<AcctInfo> acctInfoArray){
			this.acctInfoArray = acctInfoArray;
		}
		
	}
	
	public static class AcctInfo implements Serializable {
		private static final long serialVersionUID = 4093560184007132294L;
		@JSONField(name = "INTERNAL_KEY")
		private String internalKey;//账户主键
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序列号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;//证件类型
		@JSONField(name = "DOCUMENT_ID")
		private String documentId;//证件号码
		@JSONField(name = "ISS_COUNTRY")
		private String issCountry;//签发国家
		@JSONField(name = "ACCT_NATURE")
		private String acctNature;//账户属性
		@JSONField(name = "REASON_CODE")
		private String reasonCode;//账户用途
		@JSONField(name = "ACCT_CLASS")
		private String acctClass;//账户类别
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "PREFIX")
		private String prefix;//凭证前缀
		@JSONField(name = "VOUCHER_STATUS")
		private String voucherStatus;//平状态
		@JSONField(name = "APP_FLAG")
		private String appFlag;//主附卡标志
		@JSONField(name = "ACCT_STATUS")
		private String acctStatus;//账户状态
		@JSONField(name = "BALANCE")
		private String balance;//账户余额
		@JSONField(name = "PLEDGED_AMT")
		private String pledgedAmt;//限制金额
		@JSONField(name = "ACTUAL_BAL")
		private String actualBal;//可用金额
		@JSONField(name = "RENEW_METHOD")
		private String renewMethod;//转存方式
		@JSONField(name = "INT_TYPE")
		private String intType;//利率类型
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "ACCT_OPEN_DATE")
		private String acctOpenDate;//开户日期
		@JSONField(name = "EFFECT_DATE")
		private String effectDate;//起息日期
		@JSONField(name = "MATURITY_DATE")
		private String maturityDate;//到期日期
		@JSONField(name = "DEL_ACCT_DATE")
		private String delAcctDate;//销户日期
		@JSONField(name = "OPEN_BAL")
		private String openBal;//开户金额
		@JSONField(name = "INTEREST")
		private String interest;//结清利息
		@JSONField(name = "FREEZE_TYPE")
		private String freezeType;//冻结状态
		@JSONField(name = "LOSS_FLAG")
		private String lossFlag;//挂失标志
		@JSONField(name = "TERM")
		private String term;//存期
		@JSONField(name = "TERM_TYPE")
		private String termType;//存期类型
		public String getTerm() {
			return term;
		}
		public void setTerm(String term) {
			this.term = term;
		}
		public String getTermType() {
			return termType;
		}
		public void setTermType(String termType) {
			this.termType = termType;
		}
		public String getInternalKey() {
			return internalKey;
		}
		public void setInternalKey(String internalKey) {
			this.internalKey = internalKey;
		}
		public String getBaseAcctNo() {
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo) {
			this.baseAcctNo = baseAcctNo;
		}
		public String getAcctSeqNo() {
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo) {
			this.acctSeqNo = acctSeqNo;
		}
		public String getProdType() {
			return prodType;
		}
		public void setProdType(String prodType) {
			this.prodType = prodType;
		}
		public String getCcy() {
			return ccy;
		}
		public void setCcy(String ccy) {
			this.ccy = ccy;
		}
		public String getAcctName() {
			return acctName;
		}
		public void setAcctName(String acctName) {
			this.acctName = acctName;
		}
		public String getClientNo() {
			return clientNo;
		}
		public void setClientNo(String clientNo) {
			this.clientNo = clientNo;
		}
		public String getDocumentType() {
			return documentType;
		}
		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}
		public String getDocumentId() {
			return documentId;
		}
		public void setDocumentId(String documentId) {
			this.documentId = documentId;
		}
		public String getIssCountry() {
			return issCountry;
		}
		public void setIssCountry(String issCountry) {
			this.issCountry = issCountry;
		}
		public String getAcctNature() {
			return acctNature;
		}
		public void setAcctNature(String acctNature) {
			this.acctNature = acctNature;
		}
		public String getReasonCode() {
			return reasonCode;
		}
		public void setReasonCode(String reasonCode) {
			this.reasonCode = reasonCode;
		}
		public String getAcctClass() {
			return acctClass;
		}
		public void setAcctClass(String acctClass) {
			this.acctClass = acctClass;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getVoucherNo() {
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getVoucherStatus() {
			return voucherStatus;
		}
		public void setVoucherStatus(String voucherStatus) {
			this.voucherStatus = voucherStatus;
		}
		public String getAppFlag() {
			return appFlag;
		}
		public void setAppFlag(String appFlag) {
			this.appFlag = appFlag;
		}
		public String getAcctStatus() {
			return acctStatus;
		}
		public void setAcctStatus(String acctStatus) {
			this.acctStatus = acctStatus;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getPledgedAmt() {
			return pledgedAmt;
		}
		public void setPledgedAmt(String pledgedAmt) {
			this.pledgedAmt = pledgedAmt;
		}
		public String getActualBal() {
			return actualBal;
		}
		public void setActualBal(String actualBal) {
			this.actualBal = actualBal;
		}
		public String getRenewMethod() {
			return renewMethod;
		}
		public void setRenewMethod(String renewMethod) {
			this.renewMethod = renewMethod;
		}
		public String getIntType() {
			return intType;
		}
		public void setIntType(String intType) {
			this.intType = intType;
		}
		public String getRealRate() {
			return realRate;
		}
		public void setRealRate(String realRate) {
			this.realRate = realRate;
		}
		public String getAcctOpenDate() {
			return acctOpenDate;
		}
		public void setAcctOpenDate(String acctOpenDate) {
			this.acctOpenDate = acctOpenDate;
		}
		public String getEffectDate() {
			return effectDate;
		}
		public void setEffectDate(String effectDate) {
			this.effectDate = effectDate;
		}
		public String getMaturityDate() {
			return maturityDate;
		}
		public void setMaturityDate(String maturityDate) {
			this.maturityDate = maturityDate;
		}
		public String getDelAcctDate() {
			return delAcctDate;
		}
		public void setDelAcctDate(String delAcctDate) {
			this.delAcctDate = delAcctDate;
		}
		public String getOpenBal() {
			return openBal;
		}
		public void setOpenBal(String openBal) {
			this.openBal = openBal;
		}
		public String getInterest() {
			return interest;
		}
		public void setInterest(String interest) {
			this.interest = interest;
		}
		public String getFreezeType() {
			return freezeType;
		}
		public void setFreezeType(String freezeType) {
			this.freezeType = freezeType;
		}
		public String getLossFlag() {
			return lossFlag;
		}
		public void setLossFlag(String lossFlag) {
			this.lossFlag = lossFlag;
		}
		

	}
}
