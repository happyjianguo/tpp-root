package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30013000305 extends ESB_BASE {

	private static final long serialVersionUID = 7050033068708969216L;

	public ESB_REQ_30013000305 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300130003", "05");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -5462959311400142179L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "START_DATE")
		private String startDate;//起始日期
		@JSONField(name = "END_DATE")
		private String endDate;//终止日期
		@JSONField(name = "CURR_IDEN ")
		private String currIden ;//钞汇鉴别
		@JSONField(name = "ACCT_TYPE")
		private String acctType;//账户类别
		
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
		public String  getCurrIden (){
			return currIden ;
		}
		public void setCurrIden (String currIden ){
			this.currIden  = currIden ;
		}
		public String  getAcctType(){
			return acctType;
		}
		public void setAcctType(String acctType){
			this.acctType = acctType;
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