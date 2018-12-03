package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30012002301 extends ESB_BASE {
	private static final long serialVersionUID = 660747432531350433L;

	public ESB_REQ_30012002301 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300120023", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

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
	public REQ_BODY getReqBody() {
		return reqBody;
	}
	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}
	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 4653722563013492930L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "CARD_NO")
		private String cardNo;//卡号
		@JSONField(name = "SPEC_ACCT_FLAG")
		private String specAcctFlag;//定制标识
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "ACCT_CCY")
		private String acctCcy;//账户币种
		@JSONField(name = "ACCT_OPEN_DATE")
		private String acctOpenDate;//开户日期
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "ALT_ACCT_NAME")
		private String altAcctName;//账户英文名称
		@JSONField(name = "ACCT_DESC")
		private String acctDesc;//账户描述
		@JSONField(name = "ACCT_EXEC")
		private String acctExec;//客户经理
		@JSONField(name = "PROFIT_CENTRE")
		private String profitCentre;//利润中心
		@JSONField(name = "ACCT_NATURE")
		private String acctNature;//账户属性
		@JSONField(name = "ACCT_STATUS")
		private String acctStatus;//账户状态
		@JSONField(name = "ALL_DRA_IND")
		private String allDraInd;//通兑标志
		@JSONField(name = "ALL_DEP_IND")
		private String allDepInd;//通存标志
		@JSONField(name = "REGION_FLAG")
		private String regionFlag;//区域内外标识
		@JSONField(name = "OWNERSHIP_TYPE")
		private String ownershipType;//归属种类
		@JSONField(name = "HOME_BRANCH")
		private String homeBranch;//账户归属机构
		@JSONField(name = "BRANCH")
		private String branch;//机构
		@JSONField(name = "APPLY_BRANCH")
		private String applyBranch;//预约机构
		@JSONField(name = "APPR_LETTER_NO")
		private String apprLetterNo;//核准件编号
		@JSONField(name = "ACCT_LICENSE_NO")
		private String acctLicenseNo;//账户开户许可证号
		@JSONField(name = "ACCT_LICENSE_DATE")
		private String acctLicenseDate;//账户许可证签发日期
		@JSONField(name = "CONTRACT_NO")
		private String contractNo;//合同号
		@JSONField(name = "SCHED_MODE")
		private String schedMode;//计划方式
		@JSONField(name = "PRE_PAY_RATE")
		private String prePayRate;//提前还本的补偿金率
		@JSONField(name = "INT_ARRAY")
		private List<Int>  intArray;//利息明细数组
		@JSONField(name = "TERM")
		private String term;//期限
		@JSONField(name = "TERM_TYPE")
		private String termType;//期限类型
		@JSONField(name = "MATURITY_DATE")
		private String maturityDate;//到期日
		@JSONField(name = "PRINCIPAL_AMT")
		private String principalAmt;//本金金额
		@JSONField(name = "COMMISSION_CLIENT_NO")
		private String commissionClientNo;//代办人客户号
		@JSONField(name = "COMMISSION_CLIENT_NAME")
		private String commissionClientName;//代办人姓名
		@JSONField(name = "COMMISSION_DOC_TYPE")
		private String commissionDocType;//代办人证件类型
		@JSONField(name = "COMMISSION_DOC_ID")
		private String commissionDocId;//代办人证件号码
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "EFFECT_DATE")
		private String effectDate;//生效日期
		@JSONField(name = "AUTO_RENEW_ROLLOVER")
		private String autoRenewRollover;//自动转存标志
		@JSONField(name = "RENEW_NO")
		private String renewNo;//自动本金转存次数
		@JSONField(name = "ROLLOVER_NO")
		private String rolloverNo;//转存的次数
		@JSONField(name = "PARTIAL_RENEW_ROLL")
		private String partialRenewRoll;//是否部分允许本金转存
		@JSONField(name = "ADDTL_PRINCIPAL")
		private String addtlPrincipal;//是否允许增加本金
		@JSONField(name = "NOTICE_PERIOD")
		private String noticePeriod;//通知期限
		@JSONField(name = "PASSWORD_ARRAY")
		private List<Password>  passwordArray;//密码数组
		@JSONField(name = "JOINT_ACCT_ARRAY")
		private List<JointAcct>  jointAcctArray;//联名账户数组
		@JSONField(name = "CONTACT_ARRAY")
		private List<Contact>  contactArray;//账户联系人信息数组
		@JSONField(name = "RELATED_ACCT_ARRAY")
		private List<RelatedAcct>  relatedAcctArray;//关联账户数组
		@JSONField(name = "LINKED_ACCT_ARRAY")
		private List<LinkedAcct>  linkedAcctArray;//链接账户数组
		@JSONField(name = "WITHDRAW_ARRAY")
		private List<Withdraw>  withdrawArray;//支取方式数组
		@JSONField(name = "SETTLE_ARRAY")
		private List<Settle>  settleArray;//结算明细信息
		@JSONField(name = "TRAN_ARRAY")
		private List<Tran>  tranArray;//交易数组
		@JSONField(name = "SERV_ARRAY")
		private List<Serv>  servArray;//服务费数组
		@JSONField(name = "REASON_CODE")
		private String reasonCode;//账户用途
		@JSONField(name = "NEXT_DEAL_DATE")
		private String nextDealDate;//下一处理日期
		
	public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getCardNo(){
			return cardNo;
		}
		public void setCardNo(String cardNo){
			this.cardNo = cardNo;
		}
		public String  getSpecAcctFlag(){
			return specAcctFlag;
		}
		public void setSpecAcctFlag(String specAcctFlag){
			this.specAcctFlag = specAcctFlag;
		}
		public String  getClientNo(){
			return clientNo;
		}
		public void setClientNo(String clientNo){
			this.clientNo = clientNo;
		}
		public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
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
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getAltAcctName(){
			return altAcctName;
		}
		public void setAltAcctName(String altAcctName){
			this.altAcctName = altAcctName;
		}
		public String  getAcctDesc(){
			return acctDesc;
		}
		public void setAcctDesc(String acctDesc){
			this.acctDesc = acctDesc;
		}
		public String  getAcctExec(){
			return acctExec;
		}
		public void setAcctExec(String acctExec){
			this.acctExec = acctExec;
		}
		public String  getProfitCentre(){
			return profitCentre;
		}
		public void setProfitCentre(String profitCentre){
			this.profitCentre = profitCentre;
		}
		public String  getAcctNature(){
			return acctNature;
		}
		public void setAcctNature(String acctNature){
			this.acctNature = acctNature;
		}
		public String  getAcctStatus(){
			return acctStatus;
		}
		public void setAcctStatus(String acctStatus){
			this.acctStatus = acctStatus;
		}
		public String  getAllDraInd(){
			return allDraInd;
		}
		public void setAllDraInd(String allDraInd){
			this.allDraInd = allDraInd;
		}
		public String  getAllDepInd(){
			return allDepInd;
		}
		public void setAllDepInd(String allDepInd){
			this.allDepInd = allDepInd;
		}
		public String  getRegionFlag(){
			return regionFlag;
		}
		public void setRegionFlag(String regionFlag){
			this.regionFlag = regionFlag;
		}
		public String  getOwnershipType(){
			return ownershipType;
		}
		public void setOwnershipType(String ownershipType){
			this.ownershipType = ownershipType;
		}
		public String  getHomeBranch(){
			return homeBranch;
		}
		public void setHomeBranch(String homeBranch){
			this.homeBranch = homeBranch;
		}
		public String  getBranch(){
			return branch;
		}
		public void setBranch(String branch){
			this.branch = branch;
		}
		public String  getApplyBranch(){
			return applyBranch;
		}
		public void setApplyBranch(String applyBranch){
			this.applyBranch = applyBranch;
		}
		public String  getApprLetterNo(){
			return apprLetterNo;
		}
		public void setApprLetterNo(String apprLetterNo){
			this.apprLetterNo = apprLetterNo;
		}
		public String  getAcctLicenseNo(){
			return acctLicenseNo;
		}
		public void setAcctLicenseNo(String acctLicenseNo){
			this.acctLicenseNo = acctLicenseNo;
		}
		public String  getAcctLicenseDate(){
			return acctLicenseDate;
		}
		public void setAcctLicenseDate(String acctLicenseDate){
			this.acctLicenseDate = acctLicenseDate;
		}
		public String  getContractNo(){
			return contractNo;
		}
		public void setContractNo(String contractNo){
			this.contractNo = contractNo;
		}
		public String  getSchedMode(){
			return schedMode;
		}
		public void setSchedMode(String schedMode){
			this.schedMode = schedMode;
		}
		public String  getPrePayRate(){
			return prePayRate;
		}
		public void setPrePayRate(String prePayRate){
			this.prePayRate = prePayRate;
		}
		public List<Int>  getIntArray(){
			return intArray;
		}
		public void setIntArray(List<Int> intArray){
			this.intArray = intArray;
		}
		public String  getTerm(){
			return term;
		}
		public void setTerm(String term){
			this.term = term;
		}
		public String  getTermType(){
			return termType;
		}
		public void setTermType(String termType){
			this.termType = termType;
		}
		public String  getMaturityDate(){
			return maturityDate;
		}
		public void setMaturityDate(String maturityDate){
			this.maturityDate = maturityDate;
		}
		public String  getPrincipalAmt(){
			return principalAmt;
		}
		public void setPrincipalAmt(String principalAmt){
			this.principalAmt = principalAmt;
		}
		public String  getCommissionClientNo(){
			return commissionClientNo;
		}
		public void setCommissionClientNo(String commissionClientNo){
			this.commissionClientNo = commissionClientNo;
		}
		public String  getCommissionClientName(){
			return commissionClientName;
		}
		public void setCommissionClientName(String commissionClientName){
			this.commissionClientName = commissionClientName;
		}
		public String  getCommissionDocType(){
			return commissionDocType;
		}
		public void setCommissionDocType(String commissionDocType){
			this.commissionDocType = commissionDocType;
		}
		public String  getCommissionDocId(){
			return commissionDocId;
		}
		public void setCommissionDocId(String commissionDocId){
			this.commissionDocId = commissionDocId;
		}
		public String  getDocType(){
			return docType;
		}
		public void setDocType(String docType){
			this.docType = docType;
		}
		public String  getPrefix(){
			return prefix;
		}
		public void setPrefix(String prefix){
			this.prefix = prefix;
		}
		public String  getVoucherNo(){
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo){
			this.voucherNo = voucherNo;
		}
		public String  getEffectDate(){
			return effectDate;
		}
		public void setEffectDate(String effectDate){
			this.effectDate = effectDate;
		}
		public String  getAutoRenewRollover(){
			return autoRenewRollover;
		}
		public void setAutoRenewRollover(String autoRenewRollover){
			this.autoRenewRollover = autoRenewRollover;
		}
		public String  getRenewNo(){
			return renewNo;
		}
		public void setRenewNo(String renewNo){
			this.renewNo = renewNo;
		}
		public String  getRolloverNo(){
			return rolloverNo;
		}
		public void setRolloverNo(String rolloverNo){
			this.rolloverNo = rolloverNo;
		}
		public String  getPartialRenewRoll(){
			return partialRenewRoll;
		}
		public void setPartialRenewRoll(String partialRenewRoll){
			this.partialRenewRoll = partialRenewRoll;
		}
		public String  getAddtlPrincipal(){
			return addtlPrincipal;
		}
		public void setAddtlPrincipal(String addtlPrincipal){
			this.addtlPrincipal = addtlPrincipal;
		}
		public String  getNoticePeriod(){
			return noticePeriod;
		}
		public void setNoticePeriod(String noticePeriod){
			this.noticePeriod = noticePeriod;
		}
		public List<Password>  getPasswordArray(){
			return passwordArray;
		}
		public void setPasswordArray(List<Password> passwordArray){
			this.passwordArray = passwordArray;
		}
		public List<JointAcct>  getJointAcctArray(){
			return jointAcctArray;
		}
		public void setJointAcctArray(List<JointAcct> jointAcctArray){
			this.jointAcctArray = jointAcctArray;
		}
		public List<Contact>  getContactArray(){
			return contactArray;
		}
		public void setContactArray(List<Contact> contactArray){
			this.contactArray = contactArray;
		}
		public List<RelatedAcct>  getRelatedAcctArray(){
			return relatedAcctArray;
		}
		public void setRelatedAcctArray(List<RelatedAcct> relatedAcctArray){
			this.relatedAcctArray = relatedAcctArray;
		}
		public List<LinkedAcct>  getLinkedAcctArray(){
			return linkedAcctArray;
		}
		public void setLinkedAcctArray(List<LinkedAcct> linkedAcctArray){
			this.linkedAcctArray = linkedAcctArray;
		}
		public List<Withdraw>  getWithdrawArray(){
			return withdrawArray;
		}
		public void setWithdrawArray(List<Withdraw> withdrawArray){
			this.withdrawArray = withdrawArray;
		}
		public List<Settle>  getSettleArray(){
			return settleArray;
		}
		public void setSettleArray(List<Settle> settleArray){
			this.settleArray = settleArray;
		}
		public List<Tran>  getTranArray(){
			return tranArray;
		}
		public void setTranArray(List<Tran> tranArray){
			this.tranArray = tranArray;
		}
		public List<Serv>  getServArray(){
			return servArray;
		}
		public void setServArray(List<Serv> servArray){
			this.servArray = servArray;
		}
		public String  getReasonCode(){
			return reasonCode;
		}
		public void setReasonCode(String reasonCode){
			this.reasonCode = reasonCode;
		}
		public String  getNextDealDate(){
			return nextDealDate;
		}
		public void setNextDealDate(String nextDealDate){
			this.nextDealDate = nextDealDate;
		}
		
	}
	
	public static class Int implements Serializable {
		private static final long serialVersionUID = -910522751813977426L;
		@JSONField(name = "INT_CLASS")
		private String intClass;//利息分类
		@JSONField(name = "INT_TYPE")
		private String intType;//利率类型
		@JSONField(name = "ACTUAL_RATE")
		private String actualRate;//行内利率
		@JSONField(name = "FLOAT_RATE")
		private String floatRate;//浮动利率
		@JSONField(name = "SPREAD_RATE")
		private String spreadRate;//浮动点数
		@JSONField(name = "SPREAD_PERCENT")
		private String spreadPercent;//浮动百分比
		@JSONField(name = "ACCT_SPREAD_RATE")
		private String acctSpreadRate;//分户级浮动百分点
		@JSONField(name = "ACCT_PERCENT_RATE")
		private String acctPercentRate;//分户级浮动百分比
		@JSONField(name = "ACCT_FIXED_RATE")
		private String acctFixedRate;//分户级固定利率
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "CYCLE_FREQ")
		private String cycleFreq;//结息频率
		@JSONField(name = "NEXT_CYCLE_DATE")
		private String nextCycleDate;//下一结息日期
		@JSONField(name = "INT_DAY")
		private String intDay;//结息日
		@JSONField(name = "YEAR_BASIS")
		private String yearBasis;//年基准天数
		@JSONField(name = "MONTH_BASIS")
		private String monthBasis;//月基准天数
		@JSONField(name = "MIN_INT_RATE")
		private String minIntRate;//最低利率
		@JSONField(name = "MAX_INT_RATE")
		private String maxIntRate;//最高利率
		@JSONField(name = "INT_APPL_TYPE")
		private String intApplType;//利率启用方式
		@JSONField(name = "INT_CAP")
		private String intCap;//是否资本化
		@JSONField(name = "PENALTY_ODI_RATE_TYPE")
		private String penaltyOdiRateType;//罚息利率使用方式
		@JSONField(name = "CALC_BEGIN_DATE")
		private String calcBeginDate;//利息计算起始日
		@JSONField(name = "CALC_END_DATE")
		private String calcEndDate;//利息计算截止日
		@JSONField(name = "ROLL_DATE")
		private String rollDate;//利率变更日期
		@JSONField(name = "ROLL_FREQ")
		private String rollFreq;//利率变更周期
		@JSONField(name = "ROLL_DAY")
		private String rollDay;//利率变更日
		
		public String  getIntClass(){
			return intClass;
		}
		public void setIntClass(String intClass){
			this.intClass = intClass;
		}
		public String  getIntType(){
			return intType;
		}
		public void setIntType(String intType){
			this.intType = intType;
		}
		public String  getActualRate(){
			return actualRate;
		}
		public void setActualRate(String actualRate){
			this.actualRate = actualRate;
		}
		public String  getFloatRate(){
			return floatRate;
		}
		public void setFloatRate(String floatRate){
			this.floatRate = floatRate;
		}
		public String  getSpreadRate(){
			return spreadRate;
		}
		public void setSpreadRate(String spreadRate){
			this.spreadRate = spreadRate;
		}
		public String  getSpreadPercent(){
			return spreadPercent;
		}
		public void setSpreadPercent(String spreadPercent){
			this.spreadPercent = spreadPercent;
		}
		public String  getAcctSpreadRate(){
			return acctSpreadRate;
		}
		public void setAcctSpreadRate(String acctSpreadRate){
			this.acctSpreadRate = acctSpreadRate;
		}
		public String  getAcctPercentRate(){
			return acctPercentRate;
		}
		public void setAcctPercentRate(String acctPercentRate){
			this.acctPercentRate = acctPercentRate;
		}
		public String  getAcctFixedRate(){
			return acctFixedRate;
		}
		public void setAcctFixedRate(String acctFixedRate){
			this.acctFixedRate = acctFixedRate;
		}
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getCycleFreq(){
			return cycleFreq;
		}
		public void setCycleFreq(String cycleFreq){
			this.cycleFreq = cycleFreq;
		}
		public String  getNextCycleDate(){
			return nextCycleDate;
		}
		public void setNextCycleDate(String nextCycleDate){
			this.nextCycleDate = nextCycleDate;
		}
		public String  getIntDay(){
			return intDay;
		}
		public void setIntDay(String intDay){
			this.intDay = intDay;
		}
		public String  getYearBasis(){
			return yearBasis;
		}
		public void setYearBasis(String yearBasis){
			this.yearBasis = yearBasis;
		}
		public String  getMonthBasis(){
			return monthBasis;
		}
		public void setMonthBasis(String monthBasis){
			this.monthBasis = monthBasis;
		}
		public String  getMinIntRate(){
			return minIntRate;
		}
		public void setMinIntRate(String minIntRate){
			this.minIntRate = minIntRate;
		}
		public String  getMaxIntRate(){
			return maxIntRate;
		}
		public void setMaxIntRate(String maxIntRate){
			this.maxIntRate = maxIntRate;
		}
		public String  getIntApplType(){
			return intApplType;
		}
		public void setIntApplType(String intApplType){
			this.intApplType = intApplType;
		}
		public String  getIntCap(){
			return intCap;
		}
		public void setIntCap(String intCap){
			this.intCap = intCap;
		}
		public String  getPenaltyOdiRateType(){
			return penaltyOdiRateType;
		}
		public void setPenaltyOdiRateType(String penaltyOdiRateType){
			this.penaltyOdiRateType = penaltyOdiRateType;
		}
		public String  getCalcBeginDate(){
			return calcBeginDate;
		}
		public void setCalcBeginDate(String calcBeginDate){
			this.calcBeginDate = calcBeginDate;
		}
		public String  getCalcEndDate(){
			return calcEndDate;
		}
		public void setCalcEndDate(String calcEndDate){
			this.calcEndDate = calcEndDate;
		}
		public String  getRollDate(){
			return rollDate;
		}
		public void setRollDate(String rollDate){
			this.rollDate = rollDate;
		}
		public String  getRollFreq(){
			return rollFreq;
		}
		public void setRollFreq(String rollFreq){
			this.rollFreq = rollFreq;
		}
		public String  getRollDay(){
			return rollDay;
		}
		public void setRollDay(String rollDay){
			this.rollDay = rollDay;
		}
		}
public static class Password implements Serializable {
	private static final long serialVersionUID = -1199079588732458946L;
		@JSONField(name = "PWD_TYPE")
		private String pwdType;//密码的类型
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "PASSWORD_EFFECT_DATE")
		private String passwordEffectDate;//密码生效日期
		public String getPwdType() {
			return pwdType;
		}
		public void setPwdType(String pwdType) {
			this.pwdType = pwdType;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPasswordEffectDate() {
			return passwordEffectDate;
		}
		public void setPasswordEffectDate(String passwordEffectDate) {
			this.passwordEffectDate = passwordEffectDate;
		}
		
		
		}
public static class JointAcct implements Serializable {
	private static final long serialVersionUID = 1289865032632471161L;
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "CLIENT_SHORT")
		private String clientShort;//客户简称
		public String getClientNo() {
			return clientNo;
		}
		public void setClientNo(String clientNo) {
			this.clientNo = clientNo;
		}
		public String getClientShort() {
			return clientShort;
		}
		public void setClientShort(String clientShort) {
			this.clientShort = clientShort;
		}
		
		
		}
public static class Contact implements Serializable {
	private static final long serialVersionUID = 8023710369042416865L;
		@JSONField(name = "LINKMAN_TYPE")
		private String linkmanType;//联系人类型
		@JSONField(name = "LINKMAN_NAME")
		private String linkmanName;//联系人姓名
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;//证件类型
		@JSONField(name = "DOCUMENT_ID")
		private String documentId;//证件号码
		@JSONField(name = "PHONE_NO1")
		private String phoneNo1;//联系人电话1
		@JSONField(name = "PHONE_NO2")
		private String phoneNo2;//联系人电话2
		public String getLinkmanType() {
			return linkmanType;
		}
		public void setLinkmanType(String linkmanType) {
			this.linkmanType = linkmanType;
		}
		public String getLinkmanName() {
			return linkmanName;
		}
		public void setLinkmanName(String linkmanName) {
			this.linkmanName = linkmanName;
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
		public String getPhoneNo1() {
			return phoneNo1;
		}
		public void setPhoneNo1(String phoneNo1) {
			this.phoneNo1 = phoneNo1;
		}
		public String getPhoneNo2() {
			return phoneNo2;
		}
		public void setPhoneNo2(String phoneNo2) {
			this.phoneNo2 = phoneNo2;
		}
		
		
		}
public static class RelatedAcct implements Serializable {
	private static final long serialVersionUID = 5786280417514166280L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "OTH_ACCT_SEQ_NO")
		private String othAcctSeqNo;//对方账户序号
		
		
		}
public static class LinkedAcct implements Serializable {
	private static final long serialVersionUID = 2629502127791911920L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		
		
		}
public static class Withdraw implements Serializable {
	private static final long serialVersionUID = -7204883498934022975L;
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "CHANNEL_MUSTER")
		private String channelMuster;//开户渠道
		
		
		}
public static class Settle implements Serializable {
	private static final long serialVersionUID = -6322883794635166033L;
		@JSONField(name = "SETTLE_BRANCH")
		private String settleBranch;//结算分行
		@JSONField(name = "SETTLE_CLIENT")
		private String settleClient;//结算客户号
		@JSONField(name = "SETTLE_ACCT_CLASS")
		private String settleAcctClass;//结算账户分类
		@JSONField(name = "SETTLE_METHOD")
		private String settleMethod;//结算方式
		@JSONField(name = "PAY_REC_IND")
		private String payRecInd;//收付款标志
		@JSONField(name = "AMT_TYPE")
		private String amtType;//金额类型
		@JSONField(name = "SETTLE_ACCT_INTERNAL_KEY")
		private String settleAcctInternalKey;//结算账户主键
		@JSONField(name = "SETTLE_BASE_ACCT_NO")
		private String settleBaseAcctNo;//结算账号
		@JSONField(name = "SETTLE_PROD_TYPE")
		private String settleProdType;//结算账户产品类型
		@JSONField(name = "SETTLE_ACCT_CCY")
		private String settleAcctCcy;//结算账户币种
		@JSONField(name = "SETTLE_ACCT_SEQ_NO")
		private String settleAcctSeqNo;//结算账户序号
		@JSONField(name = "SETTLE_CCY")
		private String settleCcy;//结算币种
		@JSONField(name = "SETTLE_AMT")
		private String settleAmt;//结算金额
		@JSONField(name = "SETTLE_XRATE")
		private String settleXrate;//结算汇率
		@JSONField(name = "SETTLE_XRATE_ID")
		private String settleXrateId;//结算汇兑方式
		@JSONField(name = "AUTO_BLOCKING")
		private String autoBlocking;//自动锁定标记
		@JSONField(name = "PRIORITY")
		private String priority;//优先级
		@JSONField(name = "SETTLE_WEIGHT")
		private String settleWeight;//结算权重
		@JSONField(name = "TRUSTED_PAY_NO")
		private String trustedPayNo;//受托支付编号
		@JSONField(name = "SETTLE_NO")
		private String settleNo;//结算编号
		public String getSettleBranch() {
			return settleBranch;
		}
		public void setSettleBranch(String settleBranch) {
			this.settleBranch = settleBranch;
		}
		public String getSettleClient() {
			return settleClient;
		}
		public void setSettleClient(String settleClient) {
			this.settleClient = settleClient;
		}
		public String getSettleAcctClass() {
			return settleAcctClass;
		}
		public void setSettleAcctClass(String settleAcctClass) {
			this.settleAcctClass = settleAcctClass;
		}
		public String getSettleMethod() {
			return settleMethod;
		}
		public void setSettleMethod(String settleMethod) {
			this.settleMethod = settleMethod;
		}
		public String getPayRecInd() {
			return payRecInd;
		}
		public void setPayRecInd(String payRecInd) {
			this.payRecInd = payRecInd;
		}
		public String getAmtType() {
			return amtType;
		}
		public void setAmtType(String amtType) {
			this.amtType = amtType;
		}
		public String getSettleAcctInternalKey() {
			return settleAcctInternalKey;
		}
		public void setSettleAcctInternalKey(String settleAcctInternalKey) {
			this.settleAcctInternalKey = settleAcctInternalKey;
		}
		public String getSettleBaseAcctNo() {
			return settleBaseAcctNo;
		}
		public void setSettleBaseAcctNo(String settleBaseAcctNo) {
			this.settleBaseAcctNo = settleBaseAcctNo;
		}
		public String getSettleProdType() {
			return settleProdType;
		}
		public void setSettleProdType(String settleProdType) {
			this.settleProdType = settleProdType;
		}
		public String getSettleAcctCcy() {
			return settleAcctCcy;
		}
		public void setSettleAcctCcy(String settleAcctCcy) {
			this.settleAcctCcy = settleAcctCcy;
		}
		public String getSettleAcctSeqNo() {
			return settleAcctSeqNo;
		}
		public void setSettleAcctSeqNo(String settleAcctSeqNo) {
			this.settleAcctSeqNo = settleAcctSeqNo;
		}
		public String getSettleCcy() {
			return settleCcy;
		}
		public void setSettleCcy(String settleCcy) {
			this.settleCcy = settleCcy;
		}
		public String getSettleAmt() {
			return settleAmt;
		}
		public void setSettleAmt(String settleAmt) {
			this.settleAmt = settleAmt;
		}
		public String getSettleXrate() {
			return settleXrate;
		}
		public void setSettleXrate(String settleXrate) {
			this.settleXrate = settleXrate;
		}
		public String getSettleXrateId() {
			return settleXrateId;
		}
		public void setSettleXrateId(String settleXrateId) {
			this.settleXrateId = settleXrateId;
		}
		public String getAutoBlocking() {
			return autoBlocking;
		}
		public void setAutoBlocking(String autoBlocking) {
			this.autoBlocking = autoBlocking;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		public String getSettleWeight() {
			return settleWeight;
		}
		public void setSettleWeight(String settleWeight) {
			this.settleWeight = settleWeight;
		}
		public String getTrustedPayNo() {
			return trustedPayNo;
		}
		public void setTrustedPayNo(String trustedPayNo) {
			this.trustedPayNo = trustedPayNo;
		}
		public String getSettleNo() {
			return settleNo;
		}
		public void setSettleNo(String settleNo) {
			this.settleNo = settleNo;
		}
		
	
		}
public static class Tran implements Serializable {
	private static final long serialVersionUID = -7564619722113146406L;
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "OTH_BASE_ACCT_NO")
		private String othBaseAcctNo;//对方账号/卡号
		@JSONField(name = "OTH_PROD_TYPE")
		private String othProdType;//对方产品类型
		@JSONField(name = "OTH_ACCT_CCY")
		private String othAcctCcy;//对方账户币种
		@JSONField(name = "OTH_ACCT_SEQ_NO")
		private String othAcctSeqNo;//对方账户序号
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "OTH_BAL_TYPE")
		private String othBalType;//对方余额类型
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "BAL_TYPE")
		private String balType;//余额类型
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "EFFECT_DATE")
		private String effectDate;//生效日期
		@JSONField(name = "CASH_ITEM")
		private String cashItem;//现金项目
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "EXCHANGE_TRAN_CODE")
		private String exchangeTranCode;//收入方结售汇交易编码
		@JSONField(name = "EXCHANGE_TRAN_CODET")
		private String exchangeTranCodet;//支出方结售汇交易编码
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "SIGN_DATE")
		private String signDate;//签发的日期
		public String getTranType() {
			return tranType;
		}
		public void setTranType(String tranType) {
			this.tranType = tranType;
		}
		public String getOthBaseAcctNo() {
			return othBaseAcctNo;
		}
		public void setOthBaseAcctNo(String othBaseAcctNo) {
			this.othBaseAcctNo = othBaseAcctNo;
		}
		public String getOthProdType() {
			return othProdType;
		}
		public void setOthProdType(String othProdType) {
			this.othProdType = othProdType;
		}
		public String getOthAcctCcy() {
			return othAcctCcy;
		}
		public void setOthAcctCcy(String othAcctCcy) {
			this.othAcctCcy = othAcctCcy;
		}
		public String getOthAcctSeqNo() {
			return othAcctSeqNo;
		}
		public void setOthAcctSeqNo(String othAcctSeqNo) {
			this.othAcctSeqNo = othAcctSeqNo;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getOthBalType() {
			return othBalType;
		}
		public void setOthBalType(String othBalType) {
			this.othBalType = othBalType;
		}
		public String getWithdrawalType() {
			return withdrawalType;
		}
		public void setWithdrawalType(String withdrawalType) {
			this.withdrawalType = withdrawalType;
		}
		public String getBalType() {
			return balType;
		}
		public void setBalType(String balType) {
			this.balType = balType;
		}
		public String getTranAmt() {
			return tranAmt;
		}
		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}
		public String getEffectDate() {
			return effectDate;
		}
		public void setEffectDate(String effectDate) {
			this.effectDate = effectDate;
		}
		public String getCashItem() {
			return cashItem;
		}
		public void setCashItem(String cashItem) {
			this.cashItem = cashItem;
		}
		public String getNarrative() {
			return narrative;
		}
		public void setNarrative(String narrative) {
			this.narrative = narrative;
		}
		public String getExchangeTranCode() {
			return exchangeTranCode;
		}
		public void setExchangeTranCode(String exchangeTranCode) {
			this.exchangeTranCode = exchangeTranCode;
		}
		public String getExchangeTranCodet() {
			return exchangeTranCodet;
		}
		public void setExchangeTranCodet(String exchangeTranCodet) {
			this.exchangeTranCodet = exchangeTranCodet;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getVoucherNo() {
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getSignDate() {
			return signDate;
		}
		public void setSignDate(String signDate) {
			this.signDate = signDate;
		}
		
		
		}
public static class Serv implements Serializable {
	private static final long serialVersionUID = -1944503835559261477L;
		@JSONField(name = "FEE_TYPE")
		private String feeType;//服务费类型
		@JSONField(name = "FEE_CCY")
		private String feeCcy;//收费币种
		@JSONField(name = "FEE_AMT")
		private String feeAmt;//费用金额
		@JSONField(name = "ORIG_FEE_AMT")
		private String origFeeAmt;//原始服务费金额
		@JSONField(name = "DISC_FEE_AMT")
		private String discFeeAmt;//折扣金额
		@JSONField(name = "DISC_TYPE")
		private String discType;//折扣类型
		@JSONField(name = "DISC_RATE")
		private String discRate;//折扣率
		@JSONField(name = "BO_CLASS")
		private String boClass;//日终标志
		@JSONField(name = "CHARGE_MODE")
		private String chargeMode;//收取方式
		@JSONField(name = "CHARGE_TO_BASE_ACCT_NO")
		private String chargeToBaseAcctNo;//服务费收取账户账号
		@JSONField(name = "CHARGE_TO_CCY")
		private String chargeToCcy;//服务费收取账户币种
		@JSONField(name = "CHARGE_TO_PROD_TYPE")
		private String chargeToProdType;//服务费收取账户产品类型
		@JSONField(name = "CHARGE_TO_ACCT_SEQ_NO")
		private String chargeToAcctSeqNo;//服务费收取账户序号
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "TAX_TYPE")
		private String taxType;//税率类型
		@JSONField(name = "TAX_RATE")
		private String taxRate;//税率
		@JSONField(name = "TAX_AMT")
		private String taxAmt;//利息税金额
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "VOUCHER_START_NO")
		private String voucherStartNo;//凭证起始号码
		@JSONField(name = "VOUCHER_END_NO")
		private String voucherEndNo;//凭证结束号码
		@JSONField(name = "VOUCHER_NUM")
		private String voucherNum;//凭证数量
		@JSONField(name = "UNIT_PRICE")
		private String unitPrice;//单价
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
		public String getOrigFeeAmt() {
			return origFeeAmt;
		}
		public void setOrigFeeAmt(String origFeeAmt) {
			this.origFeeAmt = origFeeAmt;
		}
		public String getDiscFeeAmt() {
			return discFeeAmt;
		}
		public void setDiscFeeAmt(String discFeeAmt) {
			this.discFeeAmt = discFeeAmt;
		}
		public String getDiscType() {
			return discType;
		}
		public void setDiscType(String discType) {
			this.discType = discType;
		}
		public String getDiscRate() {
			return discRate;
		}
		public void setDiscRate(String discRate) {
			this.discRate = discRate;
		}
		public String getBoClass() {
			return boClass;
		}
		public void setBoClass(String boClass) {
			this.boClass = boClass;
		}
		public String getChargeMode() {
			return chargeMode;
		}
		public void setChargeMode(String chargeMode) {
			this.chargeMode = chargeMode;
		}
		public String getChargeToBaseAcctNo() {
			return chargeToBaseAcctNo;
		}
		public void setChargeToBaseAcctNo(String chargeToBaseAcctNo) {
			this.chargeToBaseAcctNo = chargeToBaseAcctNo;
		}
		public String getChargeToCcy() {
			return chargeToCcy;
		}
		public void setChargeToCcy(String chargeToCcy) {
			this.chargeToCcy = chargeToCcy;
		}
		public String getChargeToProdType() {
			return chargeToProdType;
		}
		public void setChargeToProdType(String chargeToProdType) {
			this.chargeToProdType = chargeToProdType;
		}
		public String getChargeToAcctSeqNo() {
			return chargeToAcctSeqNo;
		}
		public void setChargeToAcctSeqNo(String chargeToAcctSeqNo) {
			this.chargeToAcctSeqNo = chargeToAcctSeqNo;
		}
		public String getWithdrawalType() {
			return withdrawalType;
		}
		public void setWithdrawalType(String withdrawalType) {
			this.withdrawalType = withdrawalType;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getTaxType() {
			return taxType;
		}
		public void setTaxType(String taxType) {
			this.taxType = taxType;
		}
		public String getTaxRate() {
			return taxRate;
		}
		public void setTaxRate(String taxRate) {
			this.taxRate = taxRate;
		}
		public String getTaxAmt() {
			return taxAmt;
		}
		public void setTaxAmt(String taxAmt) {
			this.taxAmt = taxAmt;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getVoucherStartNo() {
			return voucherStartNo;
		}
		public void setVoucherStartNo(String voucherStartNo) {
			this.voucherStartNo = voucherStartNo;
		}
		public String getVoucherEndNo() {
			return voucherEndNo;
		}
		public void setVoucherEndNo(String voucherEndNo) {
			this.voucherEndNo = voucherEndNo;
		}
		public String getVoucherNum() {
			return voucherNum;
		}
		public void setVoucherNum(String voucherNum) {
			this.voucherNum = voucherNum;
		}
		public String getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(String unitPrice) {
			this.unitPrice = unitPrice;
		}
		
}

}