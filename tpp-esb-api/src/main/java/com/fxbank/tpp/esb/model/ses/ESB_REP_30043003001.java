package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30043003001 extends ESB_BASE {


	//private static final long serialVersionUID = -8845727634828642523L;

	@Deprecated
	public ESB_REP_30043003001() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30043003001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

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
	
	

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public ESB_REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(ESB_REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public ESB_REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(ESB_REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

}
