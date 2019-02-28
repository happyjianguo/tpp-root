package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

/** 
* @ClassName: ESB_REQ_30043000101 
* @Description: 记账状态查询 
* @author Duzhenduo
* @date 2019年1月31日 下午3:53:30 
*  
*/
public class ESB_REQ_30043000101 extends ESB_BASE {

	private static final long serialVersionUID = -981820169279642493L;

	public ESB_REQ_30043000101 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300430001", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -1444625099600287649L;
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo;//渠道流水号
		
	public String  getChannelType(){
			return channelType;
		}
		public void setChannelType(String channelType){
			this.channelType = channelType;
		}
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getChannelSeqNo(){
			return channelSeqNo;
		}
		public void setChannelSeqNo(String channelSeqNo){
			this.channelSeqNo = channelSeqNo;
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