package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_TRK001 
* @Description: 模拟村镇通兑快捷查询账户信息 
* @author Duzhenduo
* @date 2019年1月31日 上午9:59:31 
*  
*/
public class REP_TRK001 extends REP_BASE {

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
		@JSONField(name = "PAYER_ACNO")
		private String payerAcno;//账/卡号

		@JSONField(name = "PAYER_NAME")
		private String payerName;//账户姓名

		@JSONField(name = "ACNO_SEQ")
		private String acnoSeq;//账户序号

		@JSONField(name = "BAL")
		private String bal;//账户余额

		public String getBal() {
			return bal;
		}

		public void setBal(String bal) {
			this.bal = bal;
		}

		public String getPayerAcno() {
			return payerAcno;
		}

		public void setPayerAcno(String payerAcno) {
			this.payerAcno = payerAcno;
		}

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getAcnoSeq() {
			return acnoSeq;
		}

		public void setAcnoSeq(String acnoSeq) {
			this.acnoSeq = acnoSeq;
		}

		
	}
}
