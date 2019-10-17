package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30014000101 
* @Description: 模拟核心冲正 
* @author YePuLiang
* @date 2019年4月29日 上午8:41:37 
*  
*/
public class REQ_30014000101 extends REQ_BASE {
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30014000101(){
		super.txDesc = "模拟核心冲正";
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

		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "EVENT_TYPE")
		private String eventType;//事件类型
		@JSONField(name = "REVERSAL_REASON")
		private String reversalReason;//冲销原因
		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo;//渠道流水号
		@JSONField(name = "REVERSAL")
		private String reversal;//冲正标志
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "SEQ_NO")
		private String seqNo;//序号
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public String getReversalReason() {
			return reversalReason;
		}
		public void setReversalReason(String reversalReason) {
			this.reversalReason = reversalReason;
		}
		public String getChannelSeqNo() {
			return channelSeqNo;
		}
		public void setChannelSeqNo(String channelSeqNo) {
			this.channelSeqNo = channelSeqNo;
		}
		public String getReversal() {
			return reversal;
		}
		public void setReversal(String reversal) {
			this.reversal = reversal;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getVoucherNo() {
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}
		public String getSeqNo() {
			return seqNo;
		}
		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}	
	}
}

