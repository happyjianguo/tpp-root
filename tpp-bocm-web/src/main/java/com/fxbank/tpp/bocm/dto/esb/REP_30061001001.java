package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30061800401 
* @Description: 本行柜面交行卡取现金
* @author Duzhenduo
* @date 2019年4月16日 上午9:21:25 
*  
*/
public class REP_30061001001 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();
	
	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();
	
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

	public REP_BODY getRepBody() {
		return repBody;
	}


	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public class REP_BODY {
		@JSONField(name = "FEE_T3")
		private String feeT3;//手续费
		@JSONField(name = "BALANCE3_T")
		private String balance3T;//余额
		public String  getFeeT3(){
			return feeT3;
		}
		public void setFeeT3(String feeT3){
			this.feeT3 = feeT3;
		}
		public String  getBalance3T(){
			return balance3T;
		}
		public void setBalance3T(String balance3T){
			this.balance3T = balance3T;
		}
		
	}
}
