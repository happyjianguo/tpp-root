package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_TEST_TSK01
* @Description: 村镇账户信息
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_TEST_TSK01 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_TEST_TSK01(){
		super.txDesc = "模拟村镇账户信息";
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

		@JSONField(name = "PAYER_ACNO")
		private String payerAcno;	//账/卡号

		public String getPayerAcno() {
			return payerAcno;
		}

		public void setPayerAcno(String payerAcno) {
			this.payerAcno = payerAcno;
		}


		
	
	}
}
