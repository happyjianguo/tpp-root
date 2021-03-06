package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30041001001 
* @Description: 村镇业务借记往账
* @author Duzhenduo
* @date 2019年1月31日 上午9:52:45 
*  
*/
public class REP_30041001001 extends REP_BASE {

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
		 @JSONField(name = "BALANCE")
	     private String balance;//余额
		 
		 @JSONField(name = "HOST_TRACE_NO")
	     private String hostTraceNo;//核心流水号

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
