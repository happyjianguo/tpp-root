package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;


/** 
* @ClassName: REQ_30041000901 
* @Description: 商行通存村镇 
* @author Lit
* @date 2018年12月11日 下午2:04:30 
*  
*/
public class REQ_30041000901 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30041000901(){
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

		@JSONField(name = "BRNO_FLAG")
		private String brnoFlag;    //村镇机构
				
		@JSONField(name = "PAYEE_ACCT_NAME")
		private String payeeAcctName;		//收款名称
		
		@JSONField(name = "PAYEE_ACCT_NO")
		private String payeeAcctNo;		//收款账号
		
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
		
	  
        public String getPayeeAcctName() {
			return payeeAcctName;
		}

		public void setPayeeAcctName(String payeeAcctName) {
			this.payeeAcctName = payeeAcctName;
		}

		public String getPayeeAcctNo() {
			return payeeAcctNo;
		}

		public void setPayeeAcctNo(String payeeAcctNo) {
			this.payeeAcctNo = payeeAcctNo;
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

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}

		
	}
}
