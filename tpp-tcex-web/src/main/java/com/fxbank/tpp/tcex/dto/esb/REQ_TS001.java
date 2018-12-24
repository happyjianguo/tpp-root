package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30012002001 
* @Description: 商行通存村镇业务
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_TS001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_TS001(){
		super.txDesc = "商行通存村镇业务";
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

		@JSONField(name = "PAYER_NAME")
		private String payerName;	//付款人名称

		@JSONField(name = "PAYER_ACC")
		private String payerAcc;	//付款人账号
		
		@JSONField(name = "PAYER_PWD")
		private String payerPwd;		//付款账户密码
		
		@JSONField(name = "PAYEE_NAME")
		private String payeeName;		//收款名称
		
		@JSONField(name = "PAYEE_ACC")
		private String payeeAcc;		//收款账号
		
		@JSONField(name = "TX_AMT")
		private String txAmt;		//交易金额
		
		@JSONField(name = "CHNL")
		private String chnl;		//交易渠道
		
		@JSONField(name = "TXIND")
		private String txInd;		//现转标志
		
		@JSONField(name = "INFO")
		private String info;		//摘要
		
		@JSONField(name = "NOTE_TYPE")
		private String noteType;		//凭证种类
		
		@JSONField(name = "NOTE_NO")
		private String noteNo;		//凭证号码

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getPayerAcc() {
			return payerAcc;
		}

		public void setPayerAcc(String payerAcc) {
			this.payerAcc = payerAcc;
		}

		public String getPayerPwd() {
			return payerPwd;
		}

		public void setPayerPwd(String payerPwd) {
			this.payerPwd = payerPwd;
		}

		public String getPayeeName() {
			return payeeName;
		}

		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}

		public String getPayeeAcc() {
			return payeeAcc;
		}

		public void setPayeeAcc(String payeeAcc) {
			this.payeeAcc = payeeAcc;
		}

		public String getTxAmt() {
			return txAmt;
		}

		public void setTxAmt(String txAmt) {
			this.txAmt = txAmt;
		}

		public String getChnl() {
			return chnl;
		}

		public void setChnl(String chnl) {
			this.chnl = chnl;
		}

		public String getTxInd() {
			return txInd;
		}

		public void setTxInd(String txInd) {
			this.txInd = txInd;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public String getNoteType() {
			return noteType;
		}

		public void setNoteType(String noteType) {
			this.noteType = noteType;
		}

		public String getNoteNo() {
			return noteNo;
		}

		public void setNoteNo(String noteNo) {
			this.noteNo = noteNo;
		}

		
	
	}
}
