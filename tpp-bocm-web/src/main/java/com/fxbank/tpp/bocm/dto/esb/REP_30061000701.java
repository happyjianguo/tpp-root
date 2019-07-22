package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30061800101 
* @Description: 本行柜面本行卡付款转账
* @author Duzhenduo
* @date 2019年4月16日 上午10:46:39 
*  
*/
public class REP_30061000701 extends REP_BASE {


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
		//手续费
		@JSONField(name = "CHARGE_AMT")
		private String chargeAmt;
		//余额
		@JSONField(name = "BALANCE")
		private String balance;
		//核心流水号
		@JSONField(name = "HOST_TRACE_NO")
		private String hostTraceNo;
		public String getChargeAmt() {
			return chargeAmt;
		}
		public void setChargeAmt(String chargeAmt) {
			this.chargeAmt = chargeAmt;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getHostTraceNo() {
			return hostTraceNo;
		}
		public void setHostTraceNo(String hostTraceNo) {
			this.hostTraceNo = hostTraceNo;
		}
	
		
	
	}
	

}
