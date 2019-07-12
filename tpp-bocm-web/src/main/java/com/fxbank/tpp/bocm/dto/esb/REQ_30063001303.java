package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001303 
* @Description: 调账明细查询
* @author YePuLiang
* @date 2019年7月12日 上午8:54:23 
*  
*/
public class REQ_30063001303 extends REQ_BASE{
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063001303(){
		super.txDesc = "调账明细查询";
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
		//查询起始日期
		@JSONField(name = "QUE_STR_DT_T")
		private String queStrDtT;	
		//查询结束日期
		@JSONField(name = "QUE_END_DT_T")
		private String queEndDtT;
		public String getQueStrDtT() {
			return queStrDtT;
		}
		public void setQueStrDtT(String queStrDtT) {
			this.queStrDtT = queStrDtT;
		}
		public String getQueEndDtT() {
			return queEndDtT;
		}
		public void setQueEndDtT(String queEndDtT) {
			this.queEndDtT = queEndDtT;
		}	
	}
}