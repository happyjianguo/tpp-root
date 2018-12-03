package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cap.esb.common.ESB;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30011001201 extends ESB_BASE {

	private static final long serialVersionUID = -981820169279642493L;

	public ESB_REQ_30011001201 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = true;
		this.getReqSysHead().setMacValue(ESB.macDeginId + "|" + ESB.macNodeId + "|" + ESB.macKeyModelId + "|" + ESB.macPlaceHolder + "|");
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300110012", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -5648106305101989937L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "LOST_NO")
		private String lostNo;//挂失编号
		@JSONField(name = "PB_FLAG")
		private String pbFlag;//存折携带标记
		@JSONField(name = "DEPOSIT_CERT_NO")
		private String depositCertNo;//存款证明编号
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "PRETERM_AMT")
		private String pretermAmt;//支取金额
		@JSONField(name = "IS_CLOSE")
		private String isClose;//是否销一本通户
		@JSONField(name = "CLOSE_REASON")
		private String closeReason;//销户原因
		@JSONField(name = "PAY_INTEREST")
		private String payInterest;//应付利息
		@JSONField(name = "INT_PAST_DUE")
		private String intPastDue;//逾期利息
		@JSONField(name = "PAST_INTEREST")
		private String pastInterest;//已付利息
		@JSONField(name = "TAX_ACCRUED")
		private String taxAccrued;//利息税累计计提金额
		@JSONField(name = "NET_INTEREST_AMT")
		private String netInterestAmt;//净利息
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "AMT_WDRAWN")
		private String amtWdrawn;//支取标记
		@JSONField(name = "NEW_INT_RATE_IND")
		private String newIntRateInd;//剩余余额是否使用新利率标记
		@JSONField(name = "ACCR_INT_ADJ")
		private String accrIntAdj;//计提利息调整
		@JSONField(name = "NEW_PREFIX")
		private String newPrefix;//新前缀
		@JSONField(name = "NEW_DOC_TYPE")
		private String newDocType;//新凭证类型
		@JSONField(name = "NEW_VOUCHER_NO")
		private String newVoucherNo;//新凭证号码
		@JSONField(name = "COMMISSION_CLIENT_NO")
		private String commissionClientNo;//代办人客户号
		@JSONField(name = "COMMISSION_CLIENT_NAME")
		private String commissionClientName;//代办人姓名
		@JSONField(name = "COMMISSION_DOC_TYPE")
		private String commissionDocType;//代办人证件类型
		@JSONField(name = "COMMISSION_DOC_ID")
		private String commissionDocId;//代办人证件号码
		@JSONField(name = "CERTIFICATE_NO_ACT")
		private String certificateNoAct;//存款证实书号
		@JSONField(name = "TRAN_ARRAY")
		private List<Tran>  tranArray;//交易数组
		@JSONField(name = "SERV_DETAIL")
		private List<Serv>  servDetail;//服务费信息数组
		@JSONField(name = "INT_ARRAY")
		private List<Int>  intArray;//利息明细数组
		
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
		public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getLostNo(){
			return lostNo;
		}
		public void setLostNo(String lostNo){
			this.lostNo = lostNo;
		}
		public String  getPbFlag(){
			return pbFlag;
		}
		public void setPbFlag(String pbFlag){
			this.pbFlag = pbFlag;
		}
		public String  getDepositCertNo(){
			return depositCertNo;
		}
		public void setDepositCertNo(String depositCertNo){
			this.depositCertNo = depositCertNo;
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
		public String  getNarrative(){
			return narrative;
		}
		public void setNarrative(String narrative){
			this.narrative = narrative;
		}
		public String  getPretermAmt(){
			return pretermAmt;
		}
		public void setPretermAmt(String pretermAmt){
			this.pretermAmt = pretermAmt;
		}
		public String  getIsClose(){
			return isClose;
		}
		public void setIsClose(String isClose){
			this.isClose = isClose;
		}
		public String  getCloseReason(){
			return closeReason;
		}
		public void setCloseReason(String closeReason){
			this.closeReason = closeReason;
		}
		public String  getPayInterest(){
			return payInterest;
		}
		public void setPayInterest(String payInterest){
			this.payInterest = payInterest;
		}
		public String  getIntPastDue(){
			return intPastDue;
		}
		public void setIntPastDue(String intPastDue){
			this.intPastDue = intPastDue;
		}
		public String  getPastInterest(){
			return pastInterest;
		}
		public void setPastInterest(String pastInterest){
			this.pastInterest = pastInterest;
		}
		public String  getTaxAccrued(){
			return taxAccrued;
		}
		public void setTaxAccrued(String taxAccrued){
			this.taxAccrued = taxAccrued;
		}
		public String  getNetInterestAmt(){
			return netInterestAmt;
		}
		public void setNetInterestAmt(String netInterestAmt){
			this.netInterestAmt = netInterestAmt;
		}
		public String  getPassword(){
			return password;
		}
		public void setPassword(String password){
			this.password = password;
		}
		public String  getAmtWdrawn(){
			return amtWdrawn;
		}
		public void setAmtWdrawn(String amtWdrawn){
			this.amtWdrawn = amtWdrawn;
		}
		public String  getNewIntRateInd(){
			return newIntRateInd;
		}
		public void setNewIntRateInd(String newIntRateInd){
			this.newIntRateInd = newIntRateInd;
		}
		public String  getAccrIntAdj(){
			return accrIntAdj;
		}
		public void setAccrIntAdj(String accrIntAdj){
			this.accrIntAdj = accrIntAdj;
		}
		public String  getNewPrefix(){
			return newPrefix;
		}
		public void setNewPrefix(String newPrefix){
			this.newPrefix = newPrefix;
		}
		public String  getNewDocType(){
			return newDocType;
		}
		public void setNewDocType(String newDocType){
			this.newDocType = newDocType;
		}
		public String  getNewVoucherNo(){
			return newVoucherNo;
		}
		public void setNewVoucherNo(String newVoucherNo){
			this.newVoucherNo = newVoucherNo;
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
		public String  getCertificateNoAct(){
			return certificateNoAct;
		}
		public void setCertificateNoAct(String certificateNoAct){
			this.certificateNoAct = certificateNoAct;
		}
		public List<Tran>  getTranArray(){
			return tranArray;
		}
		public void setTranArray(List<Tran> tranArray){
			this.tranArray = tranArray;
		}
		public List<Serv>  getServDetail(){
			return servDetail;
		}
		public void setServDetail(List<Serv> servDetail){
			this.servDetail = servDetail;
		}
		public List<Int>  getIntArray(){
			return intArray;
		}
		public void setIntArray(List<Int> intArray){
			this.intArray = intArray;
		}
		
	}
	
	public static class Tran implements Serializable {
		private static final long serialVersionUID = 2973907598430292494L;
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "CHANGE_QUOTE_TYPE")
		private String changeQuoteType;//是否找零
		@JSONField(name = "CHANGE_CNY_AMOUNT")
		private String changeCnyAmount;//找零金额
		@JSONField(name = "COIN_PAY")
		private String coinPay;//找出金额
		@JSONField(name = "EXCHANGE_TRAN_CODET")
		private String exchangeTranCodet;//支出方结售汇交易编码
		@JSONField(name = "EXCHANGE_TRAN_CODE")
		private String exchangeTranCode;//收入方结售汇交易编码
		@JSONField(name = "EXCHANGE_ITEM_CODE")
		private String exchangeItemCode;//结售汇项目编码
		@JSONField(name = "CASH_ITEM")
		private String cashItem;//现金项目
		@JSONField(name = "OTH_BASE_ACCT_NO")
		private String othBaseAcctNo;//对方账号/卡号
		@JSONField(name = "OTH_ACCT_SEQ_NO")
		private String othAcctSeqNo;//对方账户序号
		@JSONField(name = "OTH_ACCT_CCY")
		private String othAcctCcy;//对方账户币种
		@JSONField(name = "OTH_PROD_TYPE")
		private String othProdType;//对方产品类型
		
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		public String  getTranType(){
			return tranType;
		}
		public void setTranType(String tranType){
			this.tranType = tranType;
		}
		public String  getNarrative(){
			return narrative;
		}
		public void setNarrative(String narrative){
			this.narrative = narrative;
		}
		public String  getChangeQuoteType(){
			return changeQuoteType;
		}
		public void setChangeQuoteType(String changeQuoteType){
			this.changeQuoteType = changeQuoteType;
		}
		public String  getChangeCnyAmount(){
			return changeCnyAmount;
		}
		public void setChangeCnyAmount(String changeCnyAmount){
			this.changeCnyAmount = changeCnyAmount;
		}
		public String  getCoinPay(){
			return coinPay;
		}
		public void setCoinPay(String coinPay){
			this.coinPay = coinPay;
		}
		public String  getExchangeTranCodet(){
			return exchangeTranCodet;
		}
		public void setExchangeTranCodet(String exchangeTranCodet){
			this.exchangeTranCodet = exchangeTranCodet;
		}
		public String  getExchangeTranCode(){
			return exchangeTranCode;
		}
		public void setExchangeTranCode(String exchangeTranCode){
			this.exchangeTranCode = exchangeTranCode;
		}
		public String  getExchangeItemCode(){
			return exchangeItemCode;
		}
		public void setExchangeItemCode(String exchangeItemCode){
			this.exchangeItemCode = exchangeItemCode;
		}
		public String  getCashItem(){
			return cashItem;
		}
		public void setCashItem(String cashItem){
			this.cashItem = cashItem;
		}
		public String  getOthBaseAcctNo(){
			return othBaseAcctNo;
		}
		public void setOthBaseAcctNo(String othBaseAcctNo){
			this.othBaseAcctNo = othBaseAcctNo;
		}
		public String  getOthAcctSeqNo(){
			return othAcctSeqNo;
		}
		public void setOthAcctSeqNo(String othAcctSeqNo){
			this.othAcctSeqNo = othAcctSeqNo;
		}
		public String  getOthAcctCcy(){
			return othAcctCcy;
		}
		public void setOthAcctCcy(String othAcctCcy){
			this.othAcctCcy = othAcctCcy;
		}
		public String  getOthProdType(){
			return othProdType;
		}
		public void setOthProdType(String othProdType){
			this.othProdType = othProdType;
		}
		}
public static class Serv implements Serializable {
	private static final long serialVersionUID = -986927962915537439L;
		@JSONField(name = "CHARGE_TYPE")
		private String chargeType;//收取费用类型
		@JSONField(name = "FEE_CCY")
		private String feeCcy;//收费币种
		@JSONField(name = "FEE_AMT")
		private String feeAmt;//费用金额
		@JSONField(name = "ORIG_FEE_AMT")
		private String origFeeAmt;//原始服务费金额
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
		public String getChargeType() {
			return chargeType;
		}
		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
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
		
		
		}
public static class Int implements Serializable {
	private static final long serialVersionUID = -7958849307132388788L;
		@JSONField(name = "COUNTER")
		private String counter;//编号
		@JSONField(name = "INT_CLASS")
		private String intClass;//利息分类
		@JSONField(name = "START_DATE")
		private String startDate;//起始日期
		@JSONField(name = "END_DATE")
		private String endDate;//终止日期
		@JSONField(name = "INT_AMT")
		private String intAmt;//利息金额
		@JSONField(name = "DAYS")
		private String days;//天数
		@JSONField(name = "FLOAT_RATE")
		private String floatRate;//浮动利率
		@JSONField(name = "ACTUAL_RATE")
		private String actualRate;//行内利率
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "YEAR_BASIS")
		private String yearBasis;//年基准天数
		@JSONField(name = "SUB_INTEREST")
		private String subInterest;//明细利息
		@JSONField(name = "TAX")
		private String tax;//利息税
		@JSONField(name = "INT_ADJ_CTD")
		private String intAdjCtd;//计提日利息调整
		@JSONField(name = "RATE_AMT")
		private String rateAmt;//靠档金额
		@JSONField(name = "INT_TYPE")
		private String intType;//利率类型
		@JSONField(name = "INT_TYPE_DESC")
		private String intTypeDesc;//利率类型描述
		@JSONField(name = "TAX_TYPE")
		private String taxType;//税率类型
		@JSONField(name = "TAX_RATE")
		private String taxRate;//税率
		@JSONField(name = "TAX_TYPE_DESC")
		private String taxTypeDesc;//税率类型描述
		public String getCounter() {
			return counter;
		}
		public void setCounter(String counter) {
			this.counter = counter;
		}
		public String getIntClass() {
			return intClass;
		}
		public void setIntClass(String intClass) {
			this.intClass = intClass;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getIntAmt() {
			return intAmt;
		}
		public void setIntAmt(String intAmt) {
			this.intAmt = intAmt;
		}
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
		}
		public String getFloatRate() {
			return floatRate;
		}
		public void setFloatRate(String floatRate) {
			this.floatRate = floatRate;
		}
		public String getActualRate() {
			return actualRate;
		}
		public void setActualRate(String actualRate) {
			this.actualRate = actualRate;
		}
		public String getRealRate() {
			return realRate;
		}
		public void setRealRate(String realRate) {
			this.realRate = realRate;
		}
		public String getYearBasis() {
			return yearBasis;
		}
		public void setYearBasis(String yearBasis) {
			this.yearBasis = yearBasis;
		}
		public String getSubInterest() {
			return subInterest;
		}
		public void setSubInterest(String subInterest) {
			this.subInterest = subInterest;
		}
		public String getTax() {
			return tax;
		}
		public void setTax(String tax) {
			this.tax = tax;
		}
		public String getIntAdjCtd() {
			return intAdjCtd;
		}
		public void setIntAdjCtd(String intAdjCtd) {
			this.intAdjCtd = intAdjCtd;
		}
		public String getRateAmt() {
			return rateAmt;
		}
		public void setRateAmt(String rateAmt) {
			this.rateAmt = rateAmt;
		}
		public String getIntType() {
			return intType;
		}
		public void setIntType(String intType) {
			this.intType = intType;
		}
		public String getIntTypeDesc() {
			return intTypeDesc;
		}
		public void setIntTypeDesc(String intTypeDesc) {
			this.intTypeDesc = intTypeDesc;
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
		public String getTaxTypeDesc() {
			return taxTypeDesc;
		}
		public void setTaxTypeDesc(String taxTypeDesc) {
			this.taxTypeDesc = taxTypeDesc;
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