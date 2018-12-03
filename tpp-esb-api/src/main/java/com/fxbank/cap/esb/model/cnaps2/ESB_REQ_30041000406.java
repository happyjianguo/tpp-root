package com.fxbank.cap.esb.model.cnaps2;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cap.esb.common.ESB;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

/** 
* @ClassName: ESB_REQ_300410004 
* @Description: 本场景用于实现所有外围系统的单笔跨行记账
* @author DuZhenduo
* @date 2018年9月29日 下午3:02:19 
*  
*/
public class ESB_REQ_30041000406 extends ESB_BASE {

	private static final long serialVersionUID = -1338120038203830314L;

	public ESB_REQ_30041000406(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = true;
		this.getReqSysHead().setMacValue(ESB.macDeginId + "|" + ESB.macNodeId + "|" + ESB.macKeyModelId + "|" + ESB.macPlaceHolder + "|");
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300410004", "06");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -2483609859404922742L;

		@JSONField(name = "TRAN_METHOD")
		private String tranMethod;//到账方式
		@JSONField(name = "SOURCE_SYS")
		private String sourceSys;//来源系统
		@JSONField(name = "PAYER_ACCT_NO")
		private String payerAcctNo;//付款人账号
		@JSONField(name = "PAYER_NAME")
		private String payerName;//付款人名称
		@JSONField(name = "PAYER_ADDR")
		private String payerAddr;//付款人地址
		@JSONField(name = "AMT")
		private String amt;//金额
		@JSONField(name = "PAYEE_ACCT_NO")
		private String payeeAcctNo;//收款人账号
		@JSONField(name = "PAYEE_NAME")
		private String payeeName;//收款人名称
		@JSONField(name = "PAYEE_ADDR")
		private String payeeAddr;//收款人地址
		@JSONField(name = "SYS_TP_T")
		private String sysTpT;//系统类型
		@JSONField(name = "RCV_BANK_BANK_NO")
		private String rcvBankNo;//接收行行号
		@JSONField(name = "RCV_BANK_NAME")
		private String rcvBankName;//接收行行名
		@JSONField(name = "POSTSCRIPT")
		private String postScript;//附言
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withDrawalType;//支取方式
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "ACTUAL_PAYER_ACCT_TP")
		private String actualPayerAcctTp;//付款账户类型
		@JSONField(name = "FEE_AMT")
		private String feeAmt;//费用金额
		@JSONField(name = "FEE_TYPE")
		private String feeType;//费用类型
		public String getTranMethod() {
			return tranMethod;
		}
		public void setTranMethod(String tranMethod) {
			this.tranMethod = tranMethod;
		}
		public String getSourceSys() {
			return sourceSys;
		}
		public void setSourceSys(String sourceSys) {
			this.sourceSys = sourceSys;
		}
		public String getPayerAcctNo() {
			return payerAcctNo;
		}
		public void setPayerAcctNo(String payerAcctNo) {
			this.payerAcctNo = payerAcctNo;
		}
		public String getPayerName() {
			return payerName;
		}
		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}
		public String getPayerAddr() {
			return payerAddr;
		}
		public void setPayerAddr(String payerAddr) {
			this.payerAddr = payerAddr;
		}
		public String getAmt() {
			return amt;
		}
		public void setAmt(String amt) {
			this.amt = amt;
		}
		public String getPayeeAcctNo() {
			return payeeAcctNo;
		}
		public void setPayeeAcctNo(String payeeAcctNo) {
			this.payeeAcctNo = payeeAcctNo;
		}
		public String getPayeeName() {
			return payeeName;
		}
		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}
		public String getPayeeAddr() {
			return payeeAddr;
		}
		public void setPayeeAddr(String payeeAddr) {
			this.payeeAddr = payeeAddr;
		}
		public String getSysTpT() {
			return sysTpT;
		}
		public void setSysTpT(String sysTpT) {
			this.sysTpT = sysTpT;
		}
		public String getRcvBankNo() {
			return rcvBankNo;
		}
		public void setRcvBankNo(String rcvBankNo) {
			this.rcvBankNo = rcvBankNo;
		}
		public String getRcvBankName() {
			return rcvBankName;
		}
		public void setRcvBankName(String rcvBankName) {
			this.rcvBankName = rcvBankName;
		}
		public String getPostScript() {
			return postScript;
		}
		public void setPostScript(String postScript) {
			this.postScript = postScript;
		}
		public String getWithDrawalType() {
			return withDrawalType;
		}
		public void setWithDrawalType(String withDrawalType) {
			this.withDrawalType = withDrawalType;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getActualPayerAcctTp() {
			return actualPayerAcctTp;
		}
		public void setActualPayerAcctTp(String actualPayerAcctTp) {
			this.actualPayerAcctTp = actualPayerAcctTp;
		}
		public String getFeeAmt() {
			return feeAmt;
		}
		public void setFeeAmt(String feeAmt) {
			this.feeAmt = feeAmt;
		}
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
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
