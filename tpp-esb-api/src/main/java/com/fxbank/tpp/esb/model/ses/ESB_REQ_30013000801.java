package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.esb.common.ESB;

public class ESB_REQ_30013000801 extends ESB_BASE {

	private static final long serialVersionUID = 6659439168887602492L;

	public ESB_REQ_30013000801 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300110001", "03");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1224862836781943370L;
		@JSONField(name = "VILLAGE_BRNACH_ID")
		private String villageBrnachId;//村镇机构号
		
		public String  getVillageBrnachId(){
			return villageBrnachId;
		}
		public void setVillageBrnachId(String villageBrnachId){
			this.villageBrnachId = villageBrnachId;
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