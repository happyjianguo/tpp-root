package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_50013000104 extends ESB_BASE {


	private static final long serialVersionUID = -4108801252918371475L;
	@Deprecated
	public ESB_REP_50013000104() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_50013000104(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -4916118932756824849L;
		@JSONField(name = "ATTR_ARRAY")
		private List<Attr>  attrArray;//属性数组
		@JSONField(name = "RATE_ARRAY")
		private List<Rate>  rateArray;//利率数组
		@JSONField(name = "BATCH_FEE_ARRAY")
		private List<BatchFee>  batchFeeArray;//批量服务费信息数组
		@JSONField(name = "AGREEMENT_PREFERENCE_ARRAY")
		private List<AgreementPreference>  agreementPreferenceArray;//利率优惠信息
		public List<Attr>  getAttrArray(){
			return attrArray;
		}
		public void setAttrArray(List<Attr> attrArray){
			this.attrArray = attrArray;
		}
		public List<Rate>  getRateArray(){
			return rateArray;
		}
		public void setRateArray(List<Rate> rateArray){
			this.rateArray = rateArray;
		}
		public List<BatchFee>  getBatchFeeArray(){
			return batchFeeArray;
		}
		public void setBatchFeeArray(List<BatchFee> batchFeeArray){
			this.batchFeeArray = batchFeeArray;
		}
		public List<AgreementPreference>  getAgreementPreferenceArray(){
			return agreementPreferenceArray;
		}
		public void setAgreementPreferenceArray(List<AgreementPreference> agreementPreferenceArray){
			this.agreementPreferenceArray = agreementPreferenceArray;
		}
		
	}
	
	public static class Attr implements Serializable {
		private static final long serialVersionUID = 915643991049238821L;
		@JSONField(name = "ATTR_KEY")
		private String attrKey;//属性ID
		@JSONField(name = "ATTR_DESC")
		private String attrDesc;//属性描述
		@JSONField(name = "ATTR_VALUE")
		private String attrValue;//属性值
		
		public String  getAttrKey(){
			return attrKey;
		}
		public void setAttrKey(String attrKey){
			this.attrKey = attrKey;
		}
		public String  getAttrDesc(){
			return attrDesc;
		}
		public void setAttrDesc(String attrDesc){
			this.attrDesc = attrDesc;
		}
		public String  getAttrValue(){
			return attrValue;
		}
		public void setAttrValue(String attrValue){
			this.attrValue = attrValue;
		}
		}
public static class Rate implements Serializable {
	private static final long serialVersionUID = 7550797499008222039L;
		@JSONField(name = "ACTUAL_RATE")
		private String actualRate;//行内利率
		@JSONField(name = "FLOAT_RATE")
		private String floatRate;//浮动利率
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "BALANCE")
		private String balance;//余额
		@JSONField(name = "PERIOD_FREQ")
		private String periodFreq;//频率
		@JSONField(name = "DAY_NUM")
		private String dayNum;//每期天数
		@JSONField(name = "INT_TYPE")
		private String intType;//利率类型
		@JSONField(name = "INT_TYPE_DESC")
		private String intTypeDesc;//利率类型描述
		@JSONField(name = "EFFECT_DATE")
		private String effectDate;//生效日期
		@JSONField(name = "INT_CLASS")
		private String intClass;//利息分类
		@JSONField(name = "INT_CLASS_DESC")
		private String intClassDesc;//利息分类描述
		@JSONField(name = "EVENT_TYPE")
		private String eventType;//事件类型
		@JSONField(name = "YEAR_BASIS")
		private String yearBasis;//年基准天数
		@JSONField(name = "MONTH_BASIS")
		private String monthBasis;//月基准天数
		
		
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
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getBalance(){
			return balance;
		}
		public void setBalance(String balance){
			this.balance = balance;
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
		public String  getEffectDate(){
			return effectDate;
		}
		public void setEffectDate(String effectDate){
			this.effectDate = effectDate;
		}
		public String  getIntClass(){
			return intClass;
		}
		public void setIntClass(String intClass){
			this.intClass = intClass;
		}
		public String  getIntClassDesc(){
			return intClassDesc;
		}
		public void setIntClassDesc(String intClassDesc){
			this.intClassDesc = intClassDesc;
		}
		public String  getEventType(){
			return eventType;
		}
		public void setEventType(String eventType){
			this.eventType = eventType;
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
		}
public static class BatchFee implements Serializable {
	private static final long serialVersionUID = -5561405712307101587L;
		@JSONField(name = "FEE_TYPE")
		private String feeType;//服务费类型
		@JSONField(name = "CHARGE_PERIOD_FREQ")
		private String chargePeriodFreq;//收费频率
		@JSONField(name = "CHARGE_DAY")
		private String chargeDay;//收费日
		@JSONField(name = "NEXT_CHARGE_DATE")
		private String nextChargeDate;//下一收费日期
		
		
		public String  getFeeType(){
			return feeType;
		}
		public void setFeeType(String feeType){
			this.feeType = feeType;
		}
		public String  getChargePeriodFreq(){
			return chargePeriodFreq;
		}
		public void setChargePeriodFreq(String chargePeriodFreq){
			this.chargePeriodFreq = chargePeriodFreq;
		}
		public String  getChargeDay(){
			return chargeDay;
		}
		public void setChargeDay(String chargeDay){
			this.chargeDay = chargeDay;
		}
		public String  getNextChargeDate(){
			return nextChargeDate;
		}
		public void setNextChargeDate(String nextChargeDate){
			this.nextChargeDate = nextChargeDate;
		}
		}
public static class AgreementPreference implements Serializable {
	private static final long serialVersionUID = 3941087243943768876L;
		@JSONField(name = "PREFERENCE_VALUE")
		private String preferenceValue;//优惠值定义
		@JSONField(name = "PREFERENCE_SPREAD_RATE")
		private String preferenceSpreadRate;//利率优惠浮动百分比
		@JSONField(name = "PREFERENCE_PERCENT_RATE")
		private String preferencePercentRate;//利率优惠浮动百分点
		public String getPreferenceValue() {
			return preferenceValue;
		}
		public void setPreferenceValue(String preferenceValue) {
			this.preferenceValue = preferenceValue;
		}
		public String getPreferenceSpreadRate() {
			return preferenceSpreadRate;
		}
		public void setPreferenceSpreadRate(String preferenceSpreadRate) {
			this.preferenceSpreadRate = preferenceSpreadRate;
		}
		public String getPreferencePercentRate() {
			return preferencePercentRate;
		}
		public void setPreferencePercentRate(String preferencePercentRate) {
			this.preferencePercentRate = preferencePercentRate;
		}
		

}

}
