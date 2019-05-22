/**   
* @Title: REP_30063806801.java 
* @Package com.fxbank.tpp.bocm.dto.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:36:35 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30063806801 
* @Description: 交行柜面账户信息快速查询返回
* @author YePuLiang
* @date 2019年5月9日 上午10:36:35 
*  
*/
public class REP_30063001301 extends REP_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();
	
	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();
	
	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {
		//姓名
		@JSONField(name = "NA_T1")
		private String naT1;	
		//手续费
		@JSONField(name = "FEE_T3")
		private String feeT3;	
		//应收手续费
		@JSONField(name = "HNDL_PYMNT_FEE_T5")
		private String hndlPymntFeeT5;	
		//收款人开户行号
		@JSONField(name = "PYEE_OPN_BNK_NO_T1")
		private String pyeeOpnBnkNoT1;	
		//账户类型
		@JSONField(name = "ACCT_TP_T")
		private String acctTpT;
		
		public String getNaT1() {
			return naT1;
		}
		public void setNaT1(String naT1) {
			this.naT1 = naT1;
		}
		public String getFeeT3() {
			return feeT3;
		}
		public void setFeeT3(String feeT3) {
			this.feeT3 = feeT3;
		}
		public String getHndlPymntFeeT5() {
			return hndlPymntFeeT5;
		}
		public void setHndlPymntFeeT5(String hndlPymntFeeT5) {
			this.hndlPymntFeeT5 = hndlPymntFeeT5;
		}
		public String getPyeeOpnBnkNoT1() {
			return pyeeOpnBnkNoT1;
		}
		public void setPyeeOpnBnkNoT1(String pyeeOpnBnkNoT1) {
			this.pyeeOpnBnkNoT1 = pyeeOpnBnkNoT1;
		}
		public String getAcctTpT() {
			return acctTpT;
		}
		public void setAcctTpT(String acctTpT) {
			this.acctTpT = acctTpT;
		}	
		


	}
}
