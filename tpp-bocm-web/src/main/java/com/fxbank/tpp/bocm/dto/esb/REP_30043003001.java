package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30043003001 
* @Description: 模拟人行行号查询返回
* @author YePuLiang
* @date 2019年4月25日 上午10:34:44 
*  
*/
public class REP_30043003001 extends REP_BASE {
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

	public class REP_BODY implements Serializable {

				//private static final long serialVersionUID = 4285318811572896212L;
		@JSONField(name = "BANK_NUMBER")
		private String bankNumber;//行号
		@JSONField(name = "BNK_NM_T")
		private String bnkNmT;//行名
		@JSONField(name = "SETTLEMENT_BANK_NO")
		private String settlementBankNo;//清算行号
		@JSONField(name = "LQTN_BNK_NM_T1")
		private String lqtnBnkNmT1;//清算行名
		public String  getBankNumber(){
			return bankNumber;
		}
		public void setBankNumber(String bankNumber){
			this.bankNumber = bankNumber;
		}
		public String  getBnkNmT(){
			return bnkNmT;
		}
		public void setBnkNmT(String bnkNmT){
			this.bnkNmT = bnkNmT;
		}
		public String  getSettlementBankNo(){
			return settlementBankNo;
		}
		public void setSettlementBankNo(String settlementBankNo){
			this.settlementBankNo = settlementBankNo;
		}
		public String  getLqtnBnkNmT1(){
			return lqtnBnkNmT1;
		}
		public void setLqtnBnkNmT1(String lqtnBnkNmT1){
			this.lqtnBnkNmT1 = lqtnBnkNmT1;
		}
		
	}
	
}
