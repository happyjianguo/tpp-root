package com.fxbank.cap.esb.model.cnaps2;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30043000102 extends ESB_BASE {

	private static final long serialVersionUID = -5460242548712030340L;

	public ESB_REQ_30043000102 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300430001", "02");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 1325467841270126248L;
		@JSONField(name = "ORIG_CHANNEL_DATE")
		private String origChannelDate;//原渠道日期
		@JSONField(name = "ORIG_CHANNEL_SEQ_NO")
		private String origChannelSeqNo;//原渠道流水号
		@JSONField(name = "ORIG_SYS_ID")
		private String origSysId;//原渠道系统ID
		
	public String  getOrigChannelDate(){
			return origChannelDate;
		}
		public void setOrigChannelDate(String origChannelDate){
			this.origChannelDate = origChannelDate;
		}
		public String  getOrigChannelSeqNo(){
			return origChannelSeqNo;
		}
		public void setOrigChannelSeqNo(String origChannelSeqNo){
			this.origChannelSeqNo = origChannelSeqNo;
		}
		public String  getOrigSysId(){
			return origSysId;
		}
		public void setOrigSysId(String origSysId){
			this.origSysId = origSysId;
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