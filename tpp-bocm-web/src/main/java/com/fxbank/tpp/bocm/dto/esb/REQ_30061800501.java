/**   
* @Title: REQ_30061800501.java 
* @Package com.fxbank.tpp.bocm.dto.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 下午5:20:40 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061800501 
* @Description: 交行柜面通三方对账				 
* @author YePuLiang
* @date 2019年5月6日 下午5:20:40 
*  
*/
public class REQ_30061800501  extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30061800501(){
		super.txDesc = "交行柜面通三方对账";
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
		//对账日期
		@JSONField(name = "STMT_DT_T2")
		private String stmtDtT2;

		public String getStmtDtT2() {
			return stmtDtT2;
		}

		public void setStmtDtT2(String stmtDtT2) {
			this.stmtDtT2 = stmtDtT2;
		}

		
	}

}