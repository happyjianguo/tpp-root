package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30012000102 extends ESB_BASE {

	private static final long serialVersionUID = 3985843087092939396L;

	public ESB_REQ_30012000102 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300120001", "02");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 1627903918425334658L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "TERM")
		private String term;//期限
		@JSONField(name = "TERM_TYPE")
		private String termType;//期限类型
		@JSONField(name = "NOTICE_PERIOD")
		private String noticePeriod;//通知期限
		@JSONField(name = "AUTO_RENEW_ROLLOVER")
		private String autoRenewRollover;//自动转存标志
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "SETTLE_BASE_ACCT_NO")
		private String settleBaseAcctNo;//结算账户
		@JSONField(name = "CYCLE_FREQ")
		private String cycleFreq;//结息频率
		@JSONField(name = "INT_DAY")
		private String intDay;//结息日
		@JSONField(name = "NEXT_CYCLE_DATE")
		private String nextCycleDate;//下一结息日期
		@JSONField(name = "XC_FLAG")
		private String xcFlag;//续存标志
		@JSONField(name = "SETTLE_ACCT_SEQ_NO")
		private String settleAcctSeqNo;//结算账户序号
		@JSONField(name = "CYCLE_INT_FLAG")
		private String cycleIntFlag;//按频率付息标志
		@JSONField(name = "INT_ARRAY")
		private List<Int>  intArray;//利息明细数组
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "OTH_BASE_ACCT_NO")
		private String othBaseAcctNo;//对方账号/卡号
		
	public String getChannelType() {
			return channelType;
		}
		public void setChannelType(String channelType) {
			this.channelType = channelType;
		}
		public String getOthBaseAcctNo() {
			return othBaseAcctNo;
		}
		public void setOthBaseAcctNo(String othBaseAcctNo) {
			this.othBaseAcctNo = othBaseAcctNo;
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
		public String  getCcy(){
			return ccy;
		}
		public void setCcy(String ccy){
			this.ccy = ccy;
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
		public String  getNoticePeriod(){
			return noticePeriod;
		}
		public void setNoticePeriod(String noticePeriod){
			this.noticePeriod = noticePeriod;
		}
		public String  getAutoRenewRollover(){
			return autoRenewRollover;
		}
		public void setAutoRenewRollover(String autoRenewRollover){
			this.autoRenewRollover = autoRenewRollover;
		}
		public String  getTranType(){
			return tranType;
		}
		public void setTranType(String tranType){
			this.tranType = tranType;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		public String  getPassword(){
			return password;
		}
		public void setPassword(String password){
			this.password = password;
		}
		public String  getNarrative(){
			return narrative;
		}
		public void setNarrative(String narrative){
			this.narrative = narrative;
		}
		public String  getSettleBaseAcctNo(){
			return settleBaseAcctNo;
		}
		public void setSettleBaseAcctNo(String settleBaseAcctNo){
			this.settleBaseAcctNo = settleBaseAcctNo;
		}
		public String  getCycleFreq(){
			return cycleFreq;
		}
		public void setCycleFreq(String cycleFreq){
			this.cycleFreq = cycleFreq;
		}
		public String  getIntDay(){
			return intDay;
		}
		public void setIntDay(String intDay){
			this.intDay = intDay;
		}
		public String  getNextCycleDate(){
			return nextCycleDate;
		}
		public void setNextCycleDate(String nextCycleDate){
			this.nextCycleDate = nextCycleDate;
		}
		public String  getXcFlag(){
			return xcFlag;
		}
		public void setXcFlag(String xcFlag){
			this.xcFlag = xcFlag;
		}
		public String  getSettleAcctSeqNo(){
			return settleAcctSeqNo;
		}
		public void setSettleAcctSeqNo(String settleAcctSeqNo){
			this.settleAcctSeqNo = settleAcctSeqNo;
		}
		public String  getCycleIntFlag(){
			return cycleIntFlag;
		}
		public void setCycleIntFlag(String cycleIntFlag){
			this.cycleIntFlag = cycleIntFlag;
		}
		public List<Int>  getIntArray(){
			return intArray;
		}
		public void setIntArray(List<Int> intArray){
			this.intArray = intArray;
		}
		
	}
	
	public static class Int implements Serializable {
		private static final long serialVersionUID = 373808569383349301L;
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