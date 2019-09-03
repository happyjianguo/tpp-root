/**   
* @Title: REQ_30011000104.java 
* @Package com.fxbank.tpp.bocm.dto.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月25日 上午10:29:54 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30043003001 
* @Description: 模拟人行行号查询
* @author YePuLiang
* @date 2019年4月25日 上午10:29:54 
*  
*/
public class REQ_30043003001 extends REQ_BASE {
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30043003001(){
		super.txDesc = "模拟人行行号查询";
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

		@JSONField(name = "BRCH_NO_T4")
		private String brchNoT4;//机构号
		
		public String  getBrchNoT4(){
			return brchNoT4;
		}
		public void setBrchNoT4(String brchNoT4){
			this.brchNoT4 = brchNoT4;
		}
		
	}
}
