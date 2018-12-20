package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;


/** 
* @ClassName: REQ_TS002 
* @Description: 商行通兑村镇 
* @author Duzhenduo
* @date 2018年12月11日 下午2:04:30 
*  
*/
public class REQ_30041001001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30041001001(){
		super.txDesc = "商行通兑村镇业务";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}


	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}


	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}


	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		@JSONField(name = "BRNO_FLAG")
		private String brnoFlag;    //村镇机构
		
		@JSONField(name = "PAYER_NAME")
		private String payerName;	//付款人名称

		@JSONField(name = "PAYER_ACCT_NO")
		private String payerAcctNo;	//付款人账号
		
		@JSONField(name = "PAY_PASSWORD")
		private String payPassword;		//付款账户密码
		
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;		//交易金额
		
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;		//交易渠道
		
		@JSONField(name = "NARRATIVE")
		private String narrative;		//摘要
		
		@JSONField(name = "DOC_CLASS")
		private String docClass;		//凭证种类
		
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;		//凭证号码
		
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;      //证件类型
		
		@JSONField(name = "DOCUMENT_ID")
		private String documentID;        //证件号码

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getPayerAcctNo() {
			return payerAcctNo;
		}

		public void setPayerAcctNo(String payerAcctNo) {
			this.payerAcctNo = payerAcctNo;
		}

		public String getPayPassword() {
			return payPassword;
		}

		public void setPayPassword(String payPassword) {
			this.payPassword = payPassword;
		}

		public String getTranAmt() {
			return tranAmt;
		}

		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}

		public String getChannelType() {
			return channelType;
		}

		public void setChannelType(String channelType) {
			this.channelType = channelType;
		}

		public String getNarrative() {
			return narrative;
		}

		public void setNarrative(String narrative) {
			this.narrative = narrative;
		}

		public String getDocClass() {
			return docClass;
		}

		public void setDocClass(String docClass) {
			this.docClass = docClass;
		}

		public String getVoucherNo() {
			return voucherNo;
		}

		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}

		public String getDocumentType() {
			return documentType;
		}

		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}

		public String getDocumentID() {
			return documentID;
		}

		public void setDocumentID(String documentID) {
			this.documentID = documentID;
		}

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}

		
	}
}
