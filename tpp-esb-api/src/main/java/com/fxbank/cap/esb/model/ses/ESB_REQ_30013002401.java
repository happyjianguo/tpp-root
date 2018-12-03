package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30013002401 extends ESB_BASE {

	private static final long serialVersionUID = -6312122418567812901L;

	public ESB_REQ_30013002401 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300130024", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -4232123355870429525L;
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "NOTICE_DATE")
		private String noticeDate;//通知日期
		@JSONField(name = "NOTICE_NO")
		private String noticeNo;//通知唯一号
		@JSONField(name = "START_DATE")
		private String startDate;//起始日期
		@JSONField(name = "END_DATE")
		private String endDate;//终止日期
		@JSONField(name = "EVENT_TYPE")
		private String eventType;//事件类型
		@JSONField(name = "INQ_FLAG")
		private String inqFlag;//重新查询标识
		
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
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		public String  getNoticeDate(){
			return noticeDate;
		}
		public void setNoticeDate(String noticeDate){
			this.noticeDate = noticeDate;
		}
		public String  getNoticeNo(){
			return noticeNo;
		}
		public void setNoticeNo(String noticeNo){
			this.noticeNo = noticeNo;
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
		public String  getEventType(){
			return eventType;
		}
		public void setEventType(String eventType){
			this.eventType = eventType;
		}
		public String  getInqFlag(){
			return inqFlag;
		}
		public void setInqFlag(String inqFlag){
			this.inqFlag = inqFlag;
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