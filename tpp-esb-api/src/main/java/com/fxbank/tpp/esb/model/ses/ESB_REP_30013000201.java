package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

/** 
* @ClassName: ESB_REP_30013000201 
* @Description: 查询账户信息 
* @author Duzhenduo
* @date 2019年1月31日 下午3:49:22 
*  
*/
public class ESB_REP_30013000201 extends ESB_BASE {

	private static final long serialVersionUID = 1781162973584615434L;

	@Deprecated
	public ESB_REP_30013000201() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013000201(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 4364572330132816483L;
		@JSONField(name = "INTERNAL_KEY")
		private String internalKey;//账户主键
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "PROD_TYPE_DESC")
		private String prodTypeDesc;//产品类型描述
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "ACCT_OPEN_DATE")
		private String acctOpenDate;//开户日期
		@JSONField(name = "ACCT_STATUS")
		private String acctStatus;//账户状态
		@JSONField(name = "ACCT_STATUS_PREV")
		private String acctStatusPrev;//前次账户状态
		@JSONField(name = "ACCT_STATUS_UPD_DATE")
		private String acctStatusUpdDate;//账户状态变更日期
		@JSONField(name = "ACCOUNTING_STATUS")
		private String accountingStatus;//账户核算状态
		@JSONField(name = "ACCOUNTING_STATUS_PREV")
		private String accountingStatusPrev;//前次核算状态
		@JSONField(name = "ACCOUNT_STATUS_UPD_DATE")
		private String accountStatusUpdDate;//核算状态变更日期
		@JSONField(name = "ACCT_STATUS_DESC")
		private String acctStatusDesc;//账户状态描述
		@JSONField(name = "BRANCH")
		private String branch;//机构
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "CLIENT_TYPE")
		private String clientType;//客户类型
		@JSONField(name = "CLIENT_SHORT")
		private String clientShort;//客户简称
		@JSONField(name = "CLIENT_NAME")
		private String clientName;//客户英文名称
		@JSONField(name = "CH_CLIENT_NAME")
		private String chClientName;//客户中文名称
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;//证件类型
		@JSONField(name = "DOCUMENT_TYPE_DESC")
		private String documentTypeDesc;//证件类型描述
		@JSONField(name = "DOCUMENT_ID")
		private String documentId;//证件号码
		@JSONField(name = "ACCT_EXEC")
		private String acctExec;//客户经理
		@JSONField(name = "ACCT_EXEC_NAME")
		private String acctExecName;//客户经理名称
		@JSONField(name = "ALT_ACCT_NAME")
		private String altAcctName;//账户英文名称
		@JSONField(name = "CATEGORY_TYPE")
		private String categoryType;//分类类别
		@JSONField(name = "INTERNAL_IND")
		private String internalInd;//内部客户标志
		@JSONField(name = "CLIENT_IND_DESC")
		private String clientIndDesc;//客户分类描述
		@JSONField(name = "OWNERSHIP_TYPE")
		private String ownershipType;//归属种类
		@JSONField(name = "OWNERSHIP_TYPE_DESC")
		private String ownershipTypeDesc;//账户归属种类描述
		@JSONField(name = "PROFIT_CENTRE")
		private String profitCentre;//利润中心
		@JSONField(name = "USER_ID")
		private String userId;//交易柜员
		@JSONField(name = "LAST_CHANGE_DATE")
		private String lastChangeDate;//最后更改日期
		@JSONField(name = "LAST_CHANGE_USER_ID")
		private String lastChangeUserId;//最后修改柜员
		@JSONField(name = "APPROVAL_STATUS")
		private String approvalStatus;//复核状态
		@JSONField(name = "APPROVAL_STATUS_DESC")
		private String approvalStatusDesc;//账户复核状态描述
		@JSONField(name = "TERMINAL_ID")
		private String terminalId;//终端编号
		@JSONField(name = "APPR_USER_ID")
		private String apprUserId;//复核柜员
		@JSONField(name = "CLIENT_STATUS")
		private String clientStatus;//客户状态
		@JSONField(name = "ADDRESS")
		private String address;//地址
		@JSONField(name = "CLASS_LEVEL")
		private String classLevel;//客户等级
		@JSONField(name = "ACCT_TYPE")
		private String acctType;//账户类型
		@JSONField(name = "ACCT_TYPE_DESC")
		private String acctTypeDesc;//账户类型描述
		@JSONField(name = "ACCT_DESC")
		private String acctDesc;//账户描述
		@JSONField(name = "BALANCE")
		private String balance;//余额
		@JSONField(name = "CCY_DESC")
		private String ccyDesc;//币种描述
		@JSONField(name = "OSA_FLAG")
		private String osaFlag;//离岸标记
		@JSONField(name = "REGION_FLAG")
		private String regionFlag;//区域内外标识
		@JSONField(name = "ACCT_NATURE")
		private String acctNature;//账户属性
		@JSONField(name = "ACCT_NATURE_DESC")
		private String acctNatureDesc;//账户属性描述
		@JSONField(name = "OPEN_TRAN_DATE")
		private String openTranDate;//开户交易日期
		@JSONField(name = "MULTI_BAL_TYPE")
		private String multiBalType;//多余额账户标识
		@JSONField(name = "LEAD_ACCT_FLAG")
		private String leadAcctFlag;//是否顶层账户
		@JSONField(name = "TERM")
		private String term;//期限
		@JSONField(name = "TERM_TYPE")
		private String termType;//期限类型
		@JSONField(name = "ALL_DEP_IND")
		private String allDepInd;//通存标志
		@JSONField(name = "ISS_COUNTRY")
		private String issCountry;//签发国家
		@JSONField(name = "ALL_DRA_IND")
		private String allDraInd;//通兑标志
		@JSONField(name = "HOME_BRANCH")
		private String homeBranch;//账户归属机构
		@JSONField(name = "REASON_CODE")
		private String reasonCode;//账户用途
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "DAY_NUM")
		private String dayNum;//每期天数
		@JSONField(name = "MATURITY_DATE")
		private String maturityDate;//到期日
		@JSONField(name = "ACTUAL_BAL")
		private String actualBal;//实际余额
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "ACTUAL_RATE")
		private String actualRate;//行内利率
		@JSONField(name = "FLOAT_RATE")
		private String floatRate;//浮动利率
		@JSONField(name = "INT_TYPE")
		private String intType;//利率类型
		@JSONField(name = "TAX_POSTED")
		private String taxPosted;//利息税累计金额
		@JSONField(name = "TAX_ACCRUED")
		private String taxAccrued;//利息税累计计提金额
		@JSONField(name = "INT_POSTED")
		private String intPosted;//结息利息金额
		@JSONField(name = "INT_ACCRUED")
		private String intAccrued;//累计计提利息
		@JSONField(name = "INT_ADJ")
		private String intAdj;//利息调整(累计)
		@JSONField(name = "NET_INTEREST_AMT")
		private String netInterestAmt;//净利息
		@JSONField(name = "FIXED_CALL")
		private String fixedCall;//定期账户细类
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "APPLY_BRANCH")
		private String applyBranch;//预约机构
		@JSONField(name = "AUTO_RENEW_ROLLOVER")
		private String autoRenewRollover;//自动转存标志
		@JSONField(name = "INT_CLASS")
		private String intClass;//利息分类
		@JSONField(name = "ACCT_STOP_PAY")
		private String acctStopPay;//账户余额止付标志
		@JSONField(name = "RES_FLAG")
		private String resFlag;//冻结标志
		@JSONField(name = "IS_INDIVIDUAL")
		private String isIndividual;//是否对私客户
		@JSONField(name = "SETTLE_ARRAY")
		private List<Settle>  settleArray;//结算明细信息
		@JSONField(name = "CONTACT_ARRAY")
		private List<Contact>  contactArray;//账户联系人信息数组
		@JSONField(name = "ACCT_DUE_DATE")
		private String acctDueDate;//账户到期日期
		@JSONField(name = "ACCT_CLASS")
		private String acctClass;//账户类别
		@JSONField(name = "GL_TYPE")
		private String glType;//总账类型
		@JSONField(name = "AUTO_DEP")
		private String autoDep;//是否自动续存
		@JSONField(name = "PARTIAL_RENEW_ROLL")
		private String partialRenewRoll;//是否部分允许本金转存
		@JSONField(name = "AMOUNT")
		private String amount;//金额值
		@JSONField(name = "DEP_BASE_ACCT_NO")
		private String depBaseAcctNo;//存入账号
		@JSONField(name = "DOC_CLASS")
		private String docClass;//凭证种类
		@JSONField(name = "ACCT_PROOF_STATUS")
		private String acctProofStatus;//账户验证状态
		@JSONField(name = "CARD_STATUS")
		private String cardStatus;//卡状态
		@JSONField(name = "APP_FLAG")
		private String appFlag;//主附卡标志
		@JSONField(name = "FTA_FLAG")
		private String ftaFlag;//自贸区标识
		@JSONField(name = "JUDICIAL_FREEZE_FLAG")
		private String judicialFreezeFlag;//司法冻结标识
		@JSONField(name = "DEAL_DAY")
		private String dealDay;//处理日
		@JSONField(name = "NEXT_DEAL_DATE")
		private String nextDealDate;//下一处理日期
		@JSONField(name = "AUP_BASE_ACCT_NO")
		private String aupBaseAcctNo;//转出账号
		@JSONField(name = "AMT_TYPE")
		private String amtType;//金额类型
		@JSONField(name = "LOST_STATUS")
		private String lostStatus;//挂失状态
		@JSONField(name = "PREV_DAY_BALANCE")
		private String prevDayBalance;//上日余额
		@JSONField(name = "BAL_TYPE")
		private String balType;//余额类型
		@JSONField(name = "BRANCH_NAME")
		private String branchName;//机构名称
		@JSONField(name = "BANK_CODE")
		private String bankCode;//开户行行号
		public String  getInternalKey(){
			return internalKey;
		}
		public void setInternalKey(String internalKey){
			this.internalKey = internalKey;
		}
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
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
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getAcctOpenDate(){
			return acctOpenDate;
		}
		public void setAcctOpenDate(String acctOpenDate){
			this.acctOpenDate = acctOpenDate;
		}
		public String  getAcctStatus(){
			return acctStatus;
		}
		public void setAcctStatus(String acctStatus){
			this.acctStatus = acctStatus;
		}
		public String  getAcctStatusPrev(){
			return acctStatusPrev;
		}
		public void setAcctStatusPrev(String acctStatusPrev){
			this.acctStatusPrev = acctStatusPrev;
		}
		public String  getAcctStatusUpdDate(){
			return acctStatusUpdDate;
		}
		public void setAcctStatusUpdDate(String acctStatusUpdDate){
			this.acctStatusUpdDate = acctStatusUpdDate;
		}
		public String  getAccountingStatus(){
			return accountingStatus;
		}
		public void setAccountingStatus(String accountingStatus){
			this.accountingStatus = accountingStatus;
		}
		public String  getAccountingStatusPrev(){
			return accountingStatusPrev;
		}
		public void setAccountingStatusPrev(String accountingStatusPrev){
			this.accountingStatusPrev = accountingStatusPrev;
		}
		public String  getAccountStatusUpdDate(){
			return accountStatusUpdDate;
		}
		public void setAccountStatusUpdDate(String accountStatusUpdDate){
			this.accountStatusUpdDate = accountStatusUpdDate;
		}
		public String  getAcctStatusDesc(){
			return acctStatusDesc;
		}
		public void setAcctStatusDesc(String acctStatusDesc){
			this.acctStatusDesc = acctStatusDesc;
		}
		public String  getBranch(){
			return branch;
		}
		public void setBranch(String branch){
			this.branch = branch;
		}
		public String  getCcy(){
			return ccy;
		}
		public void setCcy(String ccy){
			this.ccy = ccy;
		}
		public String  getClientNo(){
			return clientNo;
		}
		public void setClientNo(String clientNo){
			this.clientNo = clientNo;
		}
		public String  getClientType(){
			return clientType;
		}
		public void setClientType(String clientType){
			this.clientType = clientType;
		}
		public String  getClientShort(){
			return clientShort;
		}
		public void setClientShort(String clientShort){
			this.clientShort = clientShort;
		}
		public String  getClientName(){
			return clientName;
		}
		public void setClientName(String clientName){
			this.clientName = clientName;
		}
		public String  getChClientName(){
			return chClientName;
		}
		public void setChClientName(String chClientName){
			this.chClientName = chClientName;
		}
		public String  getDocumentType(){
			return documentType;
		}
		public void setDocumentType(String documentType){
			this.documentType = documentType;
		}
		public String  getDocumentTypeDesc(){
			return documentTypeDesc;
		}
		public void setDocumentTypeDesc(String documentTypeDesc){
			this.documentTypeDesc = documentTypeDesc;
		}
		public String  getDocumentId(){
			return documentId;
		}
		public void setDocumentId(String documentId){
			this.documentId = documentId;
		}
		public String  getAcctExec(){
			return acctExec;
		}
		public void setAcctExec(String acctExec){
			this.acctExec = acctExec;
		}
		public String  getAcctExecName(){
			return acctExecName;
		}
		public void setAcctExecName(String acctExecName){
			this.acctExecName = acctExecName;
		}
		public String  getAltAcctName(){
			return altAcctName;
		}
		public void setAltAcctName(String altAcctName){
			this.altAcctName = altAcctName;
		}
		public String  getCategoryType(){
			return categoryType;
		}
		public void setCategoryType(String categoryType){
			this.categoryType = categoryType;
		}
		public String  getInternalInd(){
			return internalInd;
		}
		public void setInternalInd(String internalInd){
			this.internalInd = internalInd;
		}
		public String  getClientIndDesc(){
			return clientIndDesc;
		}
		public void setClientIndDesc(String clientIndDesc){
			this.clientIndDesc = clientIndDesc;
		}
		public String  getOwnershipType(){
			return ownershipType;
		}
		public void setOwnershipType(String ownershipType){
			this.ownershipType = ownershipType;
		}
		public String  getOwnershipTypeDesc(){
			return ownershipTypeDesc;
		}
		public void setOwnershipTypeDesc(String ownershipTypeDesc){
			this.ownershipTypeDesc = ownershipTypeDesc;
		}
		public String  getProfitCentre(){
			return profitCentre;
		}
		public void setProfitCentre(String profitCentre){
			this.profitCentre = profitCentre;
		}
		public String  getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String  getLastChangeDate(){
			return lastChangeDate;
		}
		public void setLastChangeDate(String lastChangeDate){
			this.lastChangeDate = lastChangeDate;
		}
		public String  getLastChangeUserId(){
			return lastChangeUserId;
		}
		public void setLastChangeUserId(String lastChangeUserId){
			this.lastChangeUserId = lastChangeUserId;
		}
		public String  getApprovalStatus(){
			return approvalStatus;
		}
		public void setApprovalStatus(String approvalStatus){
			this.approvalStatus = approvalStatus;
		}
		public String  getApprovalStatusDesc(){
			return approvalStatusDesc;
		}
		public void setApprovalStatusDesc(String approvalStatusDesc){
			this.approvalStatusDesc = approvalStatusDesc;
		}
		public String  getTerminalId(){
			return terminalId;
		}
		public void setTerminalId(String terminalId){
			this.terminalId = terminalId;
		}
		public String  getApprUserId(){
			return apprUserId;
		}
		public void setApprUserId(String apprUserId){
			this.apprUserId = apprUserId;
		}
		public String  getClientStatus(){
			return clientStatus;
		}
		public void setClientStatus(String clientStatus){
			this.clientStatus = clientStatus;
		}
		public String  getAddress(){
			return address;
		}
		public void setAddress(String address){
			this.address = address;
		}
		public String  getClassLevel(){
			return classLevel;
		}
		public void setClassLevel(String classLevel){
			this.classLevel = classLevel;
		}
		public String  getAcctType(){
			return acctType;
		}
		public void setAcctType(String acctType){
			this.acctType = acctType;
		}
		public String  getAcctTypeDesc(){
			return acctTypeDesc;
		}
		public void setAcctTypeDesc(String acctTypeDesc){
			this.acctTypeDesc = acctTypeDesc;
		}
		public String  getAcctDesc(){
			return acctDesc;
		}
		public void setAcctDesc(String acctDesc){
			this.acctDesc = acctDesc;
		}
		public String  getBalance(){
			return balance;
		}
		public void setBalance(String balance){
			this.balance = balance;
		}
		public String  getCcyDesc(){
			return ccyDesc;
		}
		public void setCcyDesc(String ccyDesc){
			this.ccyDesc = ccyDesc;
		}
		public String  getOsaFlag(){
			return osaFlag;
		}
		public void setOsaFlag(String osaFlag){
			this.osaFlag = osaFlag;
		}
		public String  getRegionFlag(){
			return regionFlag;
		}
		public void setRegionFlag(String regionFlag){
			this.regionFlag = regionFlag;
		}
		public String  getAcctNature(){
			return acctNature;
		}
		public void setAcctNature(String acctNature){
			this.acctNature = acctNature;
		}
		public String  getAcctNatureDesc(){
			return acctNatureDesc;
		}
		public void setAcctNatureDesc(String acctNatureDesc){
			this.acctNatureDesc = acctNatureDesc;
		}
		public String  getOpenTranDate(){
			return openTranDate;
		}
		public void setOpenTranDate(String openTranDate){
			this.openTranDate = openTranDate;
		}
		public String  getMultiBalType(){
			return multiBalType;
		}
		public void setMultiBalType(String multiBalType){
			this.multiBalType = multiBalType;
		}
		public String  getLeadAcctFlag(){
			return leadAcctFlag;
		}
		public void setLeadAcctFlag(String leadAcctFlag){
			this.leadAcctFlag = leadAcctFlag;
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
		public String  getAllDepInd(){
			return allDepInd;
		}
		public void setAllDepInd(String allDepInd){
			this.allDepInd = allDepInd;
		}
		public String  getIssCountry(){
			return issCountry;
		}
		public void setIssCountry(String issCountry){
			this.issCountry = issCountry;
		}
		public String  getAllDraInd(){
			return allDraInd;
		}
		public void setAllDraInd(String allDraInd){
			this.allDraInd = allDraInd;
		}
		public String  getHomeBranch(){
			return homeBranch;
		}
		public void setHomeBranch(String homeBranch){
			this.homeBranch = homeBranch;
		}
		public String  getReasonCode(){
			return reasonCode;
		}
		public void setReasonCode(String reasonCode){
			this.reasonCode = reasonCode;
		}
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getDayNum(){
			return dayNum;
		}
		public void setDayNum(String dayNum){
			this.dayNum = dayNum;
		}
		public String  getMaturityDate(){
			return maturityDate;
		}
		public void setMaturityDate(String maturityDate){
			this.maturityDate = maturityDate;
		}
		public String  getActualBal(){
			return actualBal;
		}
		public void setActualBal(String actualBal){
			this.actualBal = actualBal;
		}
		public String  getDocType(){
			return docType;
		}
		public void setDocType(String docType){
			this.docType = docType;
		}
		public String  getVoucherNo(){
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo){
			this.voucherNo = voucherNo;
		}
		public String  getWithdrawalType(){
			return withdrawalType;
		}
		public void setWithdrawalType(String withdrawalType){
			this.withdrawalType = withdrawalType;
		}
		public String  getPrefix(){
			return prefix;
		}
		public void setPrefix(String prefix){
			this.prefix = prefix;
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
		public String  getIntType(){
			return intType;
		}
		public void setIntType(String intType){
			this.intType = intType;
		}
		public String  getTaxPosted(){
			return taxPosted;
		}
		public void setTaxPosted(String taxPosted){
			this.taxPosted = taxPosted;
		}
		public String  getTaxAccrued(){
			return taxAccrued;
		}
		public void setTaxAccrued(String taxAccrued){
			this.taxAccrued = taxAccrued;
		}
		public String  getIntPosted(){
			return intPosted;
		}
		public void setIntPosted(String intPosted){
			this.intPosted = intPosted;
		}
		public String  getIntAccrued(){
			return intAccrued;
		}
		public void setIntAccrued(String intAccrued){
			this.intAccrued = intAccrued;
		}
		public String  getIntAdj(){
			return intAdj;
		}
		public void setIntAdj(String intAdj){
			this.intAdj = intAdj;
		}
		public String  getNetInterestAmt(){
			return netInterestAmt;
		}
		public void setNetInterestAmt(String netInterestAmt){
			this.netInterestAmt = netInterestAmt;
		}
		public String  getFixedCall(){
			return fixedCall;
		}
		public void setFixedCall(String fixedCall){
			this.fixedCall = fixedCall;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getApplyBranch(){
			return applyBranch;
		}
		public void setApplyBranch(String applyBranch){
			this.applyBranch = applyBranch;
		}
		public String  getAutoRenewRollover(){
			return autoRenewRollover;
		}
		public void setAutoRenewRollover(String autoRenewRollover){
			this.autoRenewRollover = autoRenewRollover;
		}
		public String  getIntClass(){
			return intClass;
		}
		public void setIntClass(String intClass){
			this.intClass = intClass;
		}
		public String  getAcctStopPay(){
			return acctStopPay;
		}
		public void setAcctStopPay(String acctStopPay){
			this.acctStopPay = acctStopPay;
		}
		public String  getResFlag(){
			return resFlag;
		}
		public void setResFlag(String resFlag){
			this.resFlag = resFlag;
		}
		public String  getIsIndividual(){
			return isIndividual;
		}
		public void setIsIndividual(String isIndividual){
			this.isIndividual = isIndividual;
		}
		public List<Settle>  getSettleArray(){
			return settleArray;
		}
		public void setSettleArray(List<Settle> settleArray){
			this.settleArray = settleArray;
		}
		public List<Contact>  getContactArray(){
			return contactArray;
		}
		public void setContactArray(List<Contact> contactArray){
			this.contactArray = contactArray;
		}
		public String  getAcctDueDate(){
			return acctDueDate;
		}
		public void setAcctDueDate(String acctDueDate){
			this.acctDueDate = acctDueDate;
		}
		public String  getAcctClass(){
			return acctClass;
		}
		public void setAcctClass(String acctClass){
			this.acctClass = acctClass;
		}
		public String  getGlType(){
			return glType;
		}
		public void setGlType(String glType){
			this.glType = glType;
		}
		public String  getAutoDep(){
			return autoDep;
		}
		public void setAutoDep(String autoDep){
			this.autoDep = autoDep;
		}
		public String  getPartialRenewRoll(){
			return partialRenewRoll;
		}
		public void setPartialRenewRoll(String partialRenewRoll){
			this.partialRenewRoll = partialRenewRoll;
		}
		public String  getAmount(){
			return amount;
		}
		public void setAmount(String amount){
			this.amount = amount;
		}
		public String  getDepBaseAcctNo(){
			return depBaseAcctNo;
		}
		public void setDepBaseAcctNo(String depBaseAcctNo){
			this.depBaseAcctNo = depBaseAcctNo;
		}
		public String  getDocClass(){
			return docClass;
		}
		public void setDocClass(String docClass){
			this.docClass = docClass;
		}
		public String  getAcctProofStatus(){
			return acctProofStatus;
		}
		public void setAcctProofStatus(String acctProofStatus){
			this.acctProofStatus = acctProofStatus;
		}
		public String  getCardStatus(){
			return cardStatus;
		}
		public void setCardStatus(String cardStatus){
			this.cardStatus = cardStatus;
		}
		public String  getAppFlag(){
			return appFlag;
		}
		public void setAppFlag(String appFlag){
			this.appFlag = appFlag;
		}
		public String  getFtaFlag(){
			return ftaFlag;
		}
		public void setFtaFlag(String ftaFlag){
			this.ftaFlag = ftaFlag;
		}
		public String  getJudicialFreezeFlag(){
			return judicialFreezeFlag;
		}
		public void setJudicialFreezeFlag(String judicialFreezeFlag){
			this.judicialFreezeFlag = judicialFreezeFlag;
		}
		public String  getDealDay(){
			return dealDay;
		}
		public void setDealDay(String dealDay){
			this.dealDay = dealDay;
		}
		public String  getNextDealDate(){
			return nextDealDate;
		}
		public void setNextDealDate(String nextDealDate){
			this.nextDealDate = nextDealDate;
		}
		public String  getAupBaseAcctNo(){
			return aupBaseAcctNo;
		}
		public void setAupBaseAcctNo(String aupBaseAcctNo){
			this.aupBaseAcctNo = aupBaseAcctNo;
		}
		public String  getAmtType(){
			return amtType;
		}
		public void setAmtType(String amtType){
			this.amtType = amtType;
		}
		public String  getLostStatus(){
			return lostStatus;
		}
		public void setLostStatus(String lostStatus){
			this.lostStatus = lostStatus;
		}
		public String  getPrevDayBalance(){
			return prevDayBalance;
		}
		public void setPrevDayBalance(String prevDayBalance){
			this.prevDayBalance = prevDayBalance;
		}
		public String  getBalType(){
			return balType;
		}
		public void setBalType(String balType){
			this.balType = balType;
		}
		public String  getBranchName(){
			return branchName;
		}
		public void setBranchName(String branchName){
			this.branchName = branchName;
		}
		public String  getBankCode(){
			return bankCode;
		}
		public void setBankCode(String bankCode){
			this.bankCode = bankCode;
		}
		
	}
	
	public static class Settle implements Serializable {
		private static final long serialVersionUID = -1165934873393382556L;
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
		@JSONField(name = "SORT_PRIORITY")
		private String sortPriority;//排序优先级
		@JSONField(name = "SETTLE_WEIGHT")
		private String settleWeight;//结算权重
		@JSONField(name = "TRUSTED_PAY_NO")
		private String trustedPayNo;//受托支付编号
		@JSONField(name = "SETTLE_NO")
		private String settleNo;//结算编号
		@JSONField(name = "SETTLE_BANK_FLAG")
		private String settleBankFlag;//资金转移账户银行标识
		@JSONField(name = "SETTLE_ACCT_NAME")
		private String settleAcctName;//结算账户名称
		@JSONField(name = "SETTLE_BANK_NAME")
		private String settleBankName;//结算行名称
		@JSONField(name = "SETTLE_MOBILE_PHONE")
		private String settleMobilePhone;//结算账户手机号
		
		public String  getSettleBranch(){
			return settleBranch;
		}
		public void setSettleBranch(String settleBranch){
			this.settleBranch = settleBranch;
		}
		public String  getSettleClient(){
			return settleClient;
		}
		public void setSettleClient(String settleClient){
			this.settleClient = settleClient;
		}
		public String  getSettleAcctClass(){
			return settleAcctClass;
		}
		public void setSettleAcctClass(String settleAcctClass){
			this.settleAcctClass = settleAcctClass;
		}
		public String  getSettleMethod(){
			return settleMethod;
		}
		public void setSettleMethod(String settleMethod){
			this.settleMethod = settleMethod;
		}
		public String  getPayRecInd(){
			return payRecInd;
		}
		public void setPayRecInd(String payRecInd){
			this.payRecInd = payRecInd;
		}
		public String  getAmtType(){
			return amtType;
		}
		public void setAmtType(String amtType){
			this.amtType = amtType;
		}
		public String  getSettleAcctInternalKey(){
			return settleAcctInternalKey;
		}
		public void setSettleAcctInternalKey(String settleAcctInternalKey){
			this.settleAcctInternalKey = settleAcctInternalKey;
		}
		public String  getSettleBaseAcctNo(){
			return settleBaseAcctNo;
		}
		public void setSettleBaseAcctNo(String settleBaseAcctNo){
			this.settleBaseAcctNo = settleBaseAcctNo;
		}
		public String  getSettleProdType(){
			return settleProdType;
		}
		public void setSettleProdType(String settleProdType){
			this.settleProdType = settleProdType;
		}
		public String  getSettleAcctCcy(){
			return settleAcctCcy;
		}
		public void setSettleAcctCcy(String settleAcctCcy){
			this.settleAcctCcy = settleAcctCcy;
		}
		public String  getSettleAcctSeqNo(){
			return settleAcctSeqNo;
		}
		public void setSettleAcctSeqNo(String settleAcctSeqNo){
			this.settleAcctSeqNo = settleAcctSeqNo;
		}
		public String  getSettleCcy(){
			return settleCcy;
		}
		public void setSettleCcy(String settleCcy){
			this.settleCcy = settleCcy;
		}
		public String  getSettleAmt(){
			return settleAmt;
		}
		public void setSettleAmt(String settleAmt){
			this.settleAmt = settleAmt;
		}
		public String  getSettleXrate(){
			return settleXrate;
		}
		public void setSettleXrate(String settleXrate){
			this.settleXrate = settleXrate;
		}
		public String  getSettleXrateId(){
			return settleXrateId;
		}
		public void setSettleXrateId(String settleXrateId){
			this.settleXrateId = settleXrateId;
		}
		public String  getAutoBlocking(){
			return autoBlocking;
		}
		public void setAutoBlocking(String autoBlocking){
			this.autoBlocking = autoBlocking;
		}
		public String  getSortPriority(){
			return sortPriority;
		}
		public void setSortPriority(String sortPriority){
			this.sortPriority = sortPriority;
		}
		public String  getSettleWeight(){
			return settleWeight;
		}
		public void setSettleWeight(String settleWeight){
			this.settleWeight = settleWeight;
		}
		public String  getTrustedPayNo(){
			return trustedPayNo;
		}
		public void setTrustedPayNo(String trustedPayNo){
			this.trustedPayNo = trustedPayNo;
		}
		public String  getSettleNo(){
			return settleNo;
		}
		public void setSettleNo(String settleNo){
			this.settleNo = settleNo;
		}
		public String  getSettleBankFlag(){
			return settleBankFlag;
		}
		public void setSettleBankFlag(String settleBankFlag){
			this.settleBankFlag = settleBankFlag;
		}
		public String  getSettleAcctName(){
			return settleAcctName;
		}
		public void setSettleAcctName(String settleAcctName){
			this.settleAcctName = settleAcctName;
		}
		public String  getSettleBankName(){
			return settleBankName;
		}
		public void setSettleBankName(String settleBankName){
			this.settleBankName = settleBankName;
		}
		public String  getSettleMobilePhone(){
			return settleMobilePhone;
		}
		public void setSettleMobilePhone(String settleMobilePhone){
			this.settleMobilePhone = settleMobilePhone;
		}
		}
public static class Contact implements Serializable {
	private static final long serialVersionUID = -3664489887921965974L;
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
		
		public String  getLinkmanType(){
			return linkmanType;
		}
		public void setLinkmanType(String linkmanType){
			this.linkmanType = linkmanType;
		}
		public String  getLinkmanName(){
			return linkmanName;
		}
		public void setLinkmanName(String linkmanName){
			this.linkmanName = linkmanName;
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
		public String  getPhoneNo1(){
			return phoneNo1;
		}
		public void setPhoneNo1(String phoneNo1){
			this.phoneNo1 = phoneNo1;
		}
		public String  getPhoneNo2(){
			return phoneNo2;
		}
		public void setPhoneNo2(String phoneNo2){
			this.phoneNo2 = phoneNo2;
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
