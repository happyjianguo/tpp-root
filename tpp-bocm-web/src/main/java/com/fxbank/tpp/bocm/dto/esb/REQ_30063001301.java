/**   
* @Title: REQ_30063806801.java 
* @Package com.fxbank.tpp.bocm.dto.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:32:02 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063806801 
* @Description: 交行柜面账户信息快速查询 
* @author YePuLiang
* @date 2019年5月9日 上午10:32:02 
*  
*/
public class REQ_30063001301 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063001301(){
		super.txDesc = "交行柜面账户信息快速查询";
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
		//账号
		@JSONField(name = "ACCT_NO_T")
		private String acctNoT;	
		//转账金额
		@JSONField(name = "TRSR_AMT_T3")
		private String trsrAmtT3;
		//收取方式
		@JSONField(name = "RCVE_WY_T")
		private String rcveWyT;
		//账户类型
		@JSONField(name = "ACCT_TP_T")
		private String acctTpT;
		//交易类型
		@JSONField(name = "TRNS_TP_T8")
		private String trnsTpT8;
		
		public String getAcctNoT() {
			return acctNoT;
		}
		public void setAcctNoT(String acctNoT) {
			this.acctNoT = acctNoT;
		}
		public String getTrsrAmtT3() {
			return trsrAmtT3;
		}
		public void setTrsrAmtT3(String trsrAmtT3) {
			this.trsrAmtT3 = trsrAmtT3;
		}
		public String getRcveWyT() {
			return rcveWyT;
		}
		public void setRcveWyT(String rcveWyT) {
			this.rcveWyT = rcveWyT;
		}
		public String getAcctTpT() {
			return acctTpT;
		}
		public void setAcctTpT(String acctTpT) {
			this.acctTpT = acctTpT;
		}
		public String getTrnsTpT8() {
			return trnsTpT8;
		}
		public void setTrnsTpT8(String trnsTpT8) {
			this.trnsTpT8 = trnsTpT8;
		}


		
	
	}

}
