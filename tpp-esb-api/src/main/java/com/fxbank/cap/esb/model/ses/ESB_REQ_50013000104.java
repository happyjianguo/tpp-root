package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_50013000104 extends ESB_BASE {

	private static final long serialVersionUID = -981820169279642493L;

	public ESB_REQ_50013000104 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("500130001", "04");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -7582142565952656972L;
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "EVENT_TYPE")
		private String eventType;//事件类型
		@JSONField(name = "INT_IND")
		private String intInd;//是否计息
		@JSONField(name = "TERM")
		private String term;//期限
		@JSONField(name = "TERM_TYPE")
		private String termType;//期限类型
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "EFFECT_DATE")
		private String effectDate;//生效日期
		@JSONField(name = "FEE_QUERY_FLAG")
		private String feeQueryFlag;//是否查询费用标志
		@JSONField(name = "COMMON_FLAG")
		private String commonFlag;//通用查询标志
		
	public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
		}
		public String  getEventType(){
			return eventType;
		}
		public void setEventType(String eventType){
			this.eventType = eventType;
		}
		public String  getIntInd(){
			return intInd;
		}
		public void setIntInd(String intInd){
			this.intInd = intInd;
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
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
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
		public String  getEffectDate(){
			return effectDate;
		}
		public void setEffectDate(String effectDate){
			this.effectDate = effectDate;
		}
		public String  getFeeQueryFlag(){
			return feeQueryFlag;
		}
		public void setFeeQueryFlag(String feeQueryFlag){
			this.feeQueryFlag = feeQueryFlag;
		}
		public String  getCommonFlag(){
			return commonFlag;
		}
		public void setCommonFlag(String commonFlag){
			this.commonFlag = commonFlag;
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