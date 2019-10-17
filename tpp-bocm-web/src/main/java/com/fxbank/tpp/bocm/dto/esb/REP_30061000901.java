package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30061000901 
* @Description: 本行柜面交行卡存现金
* @author Duzhenduo
* @date 2019年4月15日 下午4:42:09 
*  
*/
public class REP_30061000901 extends REP_BASE {


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
		//开户行手续费
		@JSONField(name = "OPN_ACCT_BNK_FEE_T")
		private String opnAcctBnkFeeT;
		//账户余额
		@JSONField(name = "ACCT_BAL_T2")
		private String acctBalT2;
		//核心流水号
		@JSONField(name = "HOST_TRACE_NO")
		private String hostTraceNo;
		
		public String  getOpnAcctBnkFeeT(){
			return opnAcctBnkFeeT;
		}
		public void setOpnAcctBnkFeeT(String opnAcctBnkFeeT){
			this.opnAcctBnkFeeT = opnAcctBnkFeeT;
		}
		public String  getAcctBalT2(){
			return acctBalT2;
		}
		public void setAcctBalT2(String acctBalT2){
			this.acctBalT2 = acctBalT2;
		}
		public String getHostTraceNo() {
			return hostTraceNo;
		}
		public void setHostTraceNo(String hostTraceNo) {
			this.hostTraceNo = hostTraceNo;
		}
		
		
	}

}
