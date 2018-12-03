package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30013002401 extends ESB_BASE {


	private static final long serialVersionUID = 4097754215886315470L;

	@Deprecated
	public ESB_REP_30013002401() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013002401(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 7977092149619812437L;
		@JSONField(name = "NET_INTEREST_AMT")
		private String netInterestAmt;//净利息
		@JSONField(name = "PAY_INTEREST")
		private String payInterest;//应付利息
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "INTERNAL_KEY")
		private String internalKey;//账户主键
		@JSONField(name = "INT_ADJ")
		private String intAdj;//利息调整(累计)
		@JSONField(name = "TAX_SC")
		private String taxSc;//利息税金额合计
		@JSONField(name = "INT_OUTSTANDING")
		private String intOutstanding;//应收未收利息
		@JSONField(name = "PRE_INTEREST")
		private String preInterest;//违约利息
		@JSONField(name = "PAST_INTEREST")
		private String pastInterest;//已付利息
		@JSONField(name = "INT_PAST_DUE")
		private String intPastDue;//逾期利息
		@JSONField(name = "INTEREST_ARRAY")
		private List<Interest>  interestArray;//利息数组
		@JSONField(name = "INT_ARRAY")
		private List<Int>  intArray;//利息明细数组
		public String  getNetInterestAmt(){
			return netInterestAmt;
		}
		public void setNetInterestAmt(String netInterestAmt){
			this.netInterestAmt = netInterestAmt;
		}
		public String  getPayInterest(){
			return payInterest;
		}
		public void setPayInterest(String payInterest){
			this.payInterest = payInterest;
		}
		public String  getCcy(){
			return ccy;
		}
		public void setCcy(String ccy){
			this.ccy = ccy;
		}
		public String  getInternalKey(){
			return internalKey;
		}
		public void setInternalKey(String internalKey){
			this.internalKey = internalKey;
		}
		public String  getIntAdj(){
			return intAdj;
		}
		public void setIntAdj(String intAdj){
			this.intAdj = intAdj;
		}
		public String  getTaxSc(){
			return taxSc;
		}
		public void setTaxSc(String taxSc){
			this.taxSc = taxSc;
		}
		public String  getIntOutstanding(){
			return intOutstanding;
		}
		public void setIntOutstanding(String intOutstanding){
			this.intOutstanding = intOutstanding;
		}
		public String  getPreInterest(){
			return preInterest;
		}
		public void setPreInterest(String preInterest){
			this.preInterest = preInterest;
		}
		public String  getPastInterest(){
			return pastInterest;
		}
		public void setPastInterest(String pastInterest){
			this.pastInterest = pastInterest;
		}
		public String  getIntPastDue(){
			return intPastDue;
		}
		public void setIntPastDue(String intPastDue){
			this.intPastDue = intPastDue;
		}
		public List<Interest>  getInterestArray(){
			return interestArray;
		}
		public void setInterestArray(List<Interest> interestArray){
			this.interestArray = interestArray;
		}
		public List<Int>  getIntArray(){
			return intArray;
		}
		public void setIntArray(List<Int> intArray){
			this.intArray = intArray;
		}
		
	}
	
	public static class Interest implements Serializable {
		private static final long serialVersionUID = -1684759151672052330L;
		@JSONField(name = "START_DATE")
		private String startDate;//起始日期
		@JSONField(name = "END_DATE")
		private String endDate;//终止日期
		@JSONField(name = "BASE_RATE")
		private String baseRate;//等值本币汇率
		@JSONField(name = "FLOAT_RATE")
		private String floatRate;//浮动利率
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "INT_ADJ_CTD")
		private String intAdjCtd;//计提日利息调整
		@JSONField(name = "INT_OUTSTANDING")
		private String intOutstanding;//应收未收利息
		@JSONField(name = "PERIOD_FREQ")
		private String periodFreq;//频率
		@JSONField(name = "DAY_NUM")
		private String dayNum;//每期天数
		@JSONField(name = "TAX_SC")
		private String taxSc;//利息税金额合计
		@JSONField(name = "TAX_RATE")
		private String taxRate;//税率
		@JSONField(name = "TAX_TYPE")
		private String taxType;//税率类型
		@JSONField(name = "PRE_INTEREST")
		private String preInterest;//违约利息
		@JSONField(name = "INT_CLASS")
		private String intClass;//利息分类
		@JSONField(name = "INT_PAST_DUE")
		private String intPastDue;//逾期利息
		@JSONField(name = "ACTUAL_RATE")
		private String actualRate;//行内利率
		
		public String  getStartDate(){
			return startDate;
		}
		public void setStartDate(String startDate){
			this.startDate = startDate;
		}
		public String  getEndDate(){
			return endDate;
		}
		public void setEndDate(String endDate){
			this.endDate = endDate;
		}
		public String  getBaseRate(){
			return baseRate;
		}
		public void setBaseRate(String baseRate){
			this.baseRate = baseRate;
		}
		public String  getFloatRate(){
			return floatRate;
		}
		public void setFloatRate(String floatRate){
			this.floatRate = floatRate;
		}
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getIntAdjCtd(){
			return intAdjCtd;
		}
		public void setIntAdjCtd(String intAdjCtd){
			this.intAdjCtd = intAdjCtd;
		}
		public String  getIntOutstanding(){
			return intOutstanding;
		}
		public void setIntOutstanding(String intOutstanding){
			this.intOutstanding = intOutstanding;
		}
		public String  getPeriodFreq(){
			return periodFreq;
		}
		public void setPeriodFreq(String periodFreq){
			this.periodFreq = periodFreq;
		}
		public String  getDayNum(){
			return dayNum;
		}
		public void setDayNum(String dayNum){
			this.dayNum = dayNum;
		}
		public String  getTaxSc(){
			return taxSc;
		}
		public void setTaxSc(String taxSc){
			this.taxSc = taxSc;
		}
		public String  getTaxRate(){
			return taxRate;
		}
		public void setTaxRate(String taxRate){
			this.taxRate = taxRate;
		}
		public String  getTaxType(){
			return taxType;
		}
		public void setTaxType(String taxType){
			this.taxType = taxType;
		}
		public String  getPreInterest(){
			return preInterest;
		}
		public void setPreInterest(String preInterest){
			this.preInterest = preInterest;
		}
		public String  getIntClass(){
			return intClass;
		}
		public void setIntClass(String intClass){
			this.intClass = intClass;
		}
		public String  getIntPastDue(){
			return intPastDue;
		}
		public void setIntPastDue(String intPastDue){
			this.intPastDue = intPastDue;
		}
		public String  getActualRate(){
			return actualRate;
		}
		public void setActualRate(String actualRate){
			this.actualRate = actualRate;
		}
		}
public static class Int implements Serializable {
	private static final long serialVersionUID = -4290421918966837444L;
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
		
		
		public String  getYearBasis(){
			return yearBasis;
		}
		public void setYearBasis(String yearBasis){
			this.yearBasis = yearBasis;
		}
		public String  getSubInterest(){
			return subInterest;
		}
		public void setSubInterest(String subInterest){
			this.subInterest = subInterest;
		}
		public String  getTax(){
			return tax;
		}
		public void setTax(String tax){
			this.tax = tax;
		}
		public String  getIntAdjCtd(){
			return intAdjCtd;
		}
		public void setIntAdjCtd(String intAdjCtd){
			this.intAdjCtd = intAdjCtd;
		}
		public String  getRateAmt(){
			return rateAmt;
		}
		public void setRateAmt(String rateAmt){
			this.rateAmt = rateAmt;
		}
		public String  getIntType(){
			return intType;
		}
		public void setIntType(String intType){
			this.intType = intType;
		}
		public String  getIntTypeDesc(){
			return intTypeDesc;
		}
		public void setIntTypeDesc(String intTypeDesc){
			this.intTypeDesc = intTypeDesc;
		}
		public String  getTaxType(){
			return taxType;
		}
		public void setTaxType(String taxType){
			this.taxType = taxType;
		}
		public String  getTaxRate(){
			return taxRate;
		}
		public void setTaxRate(String taxRate){
			this.taxRate = taxRate;
		}
		public String  getTaxTypeDesc(){
			return taxTypeDesc;
		}
		public void setTaxTypeDesc(String taxTypeDesc){
			this.taxTypeDesc = taxTypeDesc;
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
