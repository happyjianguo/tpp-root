package com.fxbank.tpp.beps.dto.task;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

import java.io.Serializable;
import java.util.List;

public class REQ_TP35101A01 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public REQ_TP35101A01(){
		super.txDesc = "协议付款方签约交易处理";
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

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 8701013477815342767L;

		@JSONField(name = "DATA")
		private List<DATA_JSON> data;

		public List<DATA_JSON> getData() {
			return data;
		}

		public void setData(List<DATA_JSON> data) {
			this.data = data;
		}
	}

}
