package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30013002801 extends ESB_BASE {


	private static final long serialVersionUID = 562159136726505490L;

	public ESB_REQ_30013002801 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300130028", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {


		private static final long serialVersionUID = 7508095663486526327L;
		@JSONField(name = "TERM")
		private String term;//期限
		@JSONField(name = "TERM_TYPE")
		private String termType;//期限类型
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "ACCT_OPEN_DATE")
		private String acctOpenDate;//开户日期
		
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
		public String  getAcctOpenDate(){
			return acctOpenDate;
		}
		public void setAcctOpenDate(String acctOpenDate){
			this.acctOpenDate = acctOpenDate;
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