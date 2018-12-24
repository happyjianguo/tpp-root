package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30014000101 extends ESB_BASE {


	private static final long serialVersionUID = 7318975751067450549L;

	public ESB_REQ_30014000101 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300140001", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -4786768538781789370L;
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
		
	public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getEventType(){
			return eventType;
		}
		public void setEventType(String eventType){
			this.eventType = eventType;
		}
		public String  getReversalReason(){
			return reversalReason;
		}
		public void setReversalReason(String reversalReason){
			this.reversalReason = reversalReason;
		}
		public String  getChannelSeqNo(){
			return channelSeqNo;
		}
		public void setChannelSeqNo(String channelSeqNo){
			this.channelSeqNo = channelSeqNo;
		}
		public String  getReversal(){
			return reversal;
		}
		public void setReversal(String reversal){
			this.reversal = reversal;
		}
		public String  getDocType(){
			return docType;
		}
		public void setDocType(String docType){
			this.docType = docType;
		}
		public String  getPrefix(){
			return prefix;
		}
		public void setPrefix(String prefix){
			this.prefix = prefix;
		}
		public String  getVoucherNo(){
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo){
			this.voucherNo = voucherNo;
		}
		public String  getSeqNo(){
			return seqNo;
		}
		public void setSeqNo(String seqNo){
			this.seqNo = seqNo;
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