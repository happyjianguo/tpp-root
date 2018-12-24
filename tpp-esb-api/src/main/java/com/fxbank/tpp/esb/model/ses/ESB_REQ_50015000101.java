package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_50015000101 extends ESB_BASE {

	private static final long serialVersionUID = -5768389316381915283L;

	public ESB_REQ_50015000101 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("500150001", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -4123037922045089499L;
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "SETTLE_BRANCH")
		private String settleBranch;//结算分行
		@JSONField(name = "START_DATE")
		private String startDate;//起始日期
		@JSONField(name = "END_DATE")
		private String endDate;//终止日期
		@JSONField(name = "DIRECTION")
		private String direction;//来往账标识
		
	public String  getChannelType(){
			return channelType;
		}
		public void setChannelType(String channelType){
			this.channelType = channelType;
		}
		public String  getSettleBranch(){
			return settleBranch;
		}
		public void setSettleBranch(String settleBranch){
			this.settleBranch = settleBranch;
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
		public String  getDirection(){
			return direction;
		}
		public void setDirection(String direction){
			this.direction = direction;
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