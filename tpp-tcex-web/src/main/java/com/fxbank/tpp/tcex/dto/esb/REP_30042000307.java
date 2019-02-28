package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30042000307 
* @Description: 对手行账户信息获取
* @author Duzhenduo
* @date 2019年1月31日 上午9:53:06 
*  
*/
public class REP_30042000307 extends REP_BASE {

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
		 @JSONField(name = "BASR_ACCT_NO")
	     private String basrAcctNo;//账/卡号
		 
		 @JSONField(name = "ACCT_NAME")
	     private String acctName;//账户姓名
		 
		 @JSONField(name = "ACCT_SQ_NO_T")
	     private String acctSqNoT;//账户序号
		 
		 @JSONField(name = "BALANCE")
	     private String balance;//账户余额

		public String getBasrAcctNo() {
			return basrAcctNo;
		}

		public void setBasrAcctNo(String basrAcctNo) {
			this.basrAcctNo = basrAcctNo;
		}

		public String getAcctName() {
			return acctName;
		}

		public void setAcctName(String acctName) {
			this.acctName = acctName;
		}

		public String getAcctSqNoT() {
			return acctSqNoT;
		}

		public void setAcctSqNoT(String acctSqNoT) {
			this.acctSqNoT = acctSqNoT;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}
		 
		

	}
}
