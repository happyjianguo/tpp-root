package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30043000101 extends ESB_BASE {


	private static final long serialVersionUID = 9021844981692559901L;

	@Deprecated
	public ESB_REP_30043000101() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30043000101(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 3509864394012212153L;
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo;//渠道流水号
		@JSONField(name = "ACCT_OPEN_BRANCH")
		private String acctOpenBranch;//账户开户机构
		@JSONField(name = "TRAN_DATE")
		private String tranDate;//交易日期
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "USER_ID")
		private String userId;//交易柜员
		@JSONField(name = "AUTH_USER_ID")
		private String authUserId;//授权柜员
		@JSONField(name = "CLIENT_NO")
		private String clientNo;//客户号
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "TRAN_CCY")
		private String tranCcy;//交易币种
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "ACCT_RESULT")
		private String acctResult;//记账结果
		@JSONField(name = "ACCT_NAME")
		private String acctName;//客户名称
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "OTH_ACCT_NO")
		private String othAcctNo;//对方账号
		@JSONField(name = "OTH_ACCT_NAME")
		private String othAcctName;//对方客户
		public String  getChannelType(){
			return channelType;
		}
		public void setChannelType(String channelType){
			this.channelType = channelType;
		}
		public String  getChannelSeqNo(){
			return channelSeqNo;
		}
		public void setChannelSeqNo(String channelSeqNo){
			this.channelSeqNo = channelSeqNo;
		}
		public String  getAcctOpenBranch(){
			return acctOpenBranch;
		}
		public void setAcctOpenBranch(String acctOpenBranch){
			this.acctOpenBranch = acctOpenBranch;
		}
		public String  getTranDate(){
			return tranDate;
		}
		public void setTranDate(String tranDate){
			this.tranDate = tranDate;
		}
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getUserId(){
			return userId;
		}
		public void setUserId(String userId){
			this.userId = userId;
		}
		public String  getAuthUserId(){
			return authUserId;
		}
		public void setAuthUserId(String authUserId){
			this.authUserId = authUserId;
		}
		public String  getClientNo(){
			return clientNo;
		}
		public void setClientNo(String clientNo){
			this.clientNo = clientNo;
		}
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getTranCcy(){
			return tranCcy;
		}
		public void setTranCcy(String tranCcy){
			this.tranCcy = tranCcy;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		public String  getAcctResult(){
			return acctResult;
		}
		public void setAcctResult(String acctResult){
			this.acctResult = acctResult;
		}
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getTranType(){
			return tranType;
		}
		public void setTranType(String tranType){
			this.tranType = tranType;
		}
		public String  getOthAcctNo(){
			return othAcctNo;
		}
		public void setOthAcctNo(String othAcctNo){
			this.othAcctNo = othAcctNo;
		}
		public String  getOthAcctName(){
			return othAcctName;
		}
		public void setOthAcctName(String othAcctName){
			this.othAcctName = othAcctName;
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
