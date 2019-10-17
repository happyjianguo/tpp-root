package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30063000103 extends ESB_BASE {

	//private static final long serialVersionUID = -981820169279642493L;

	public ESB_REQ_30063000103 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300630001", "03");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		//private static final long serialVersionUID = 2812572356338595288L;

		@JSONField(name = "CHARGE_SENECE")
		private String chargeSenece;//收费场景
		@JSONField(name = "OTH_BANK_CODE")
		private String othBankCode;//对方银行行号
		@JSONField(name = "TRAN_CCY")
		private String tranCcy;//交易币种
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		
	public String  getChargeSenece(){
			return chargeSenece;
		}
		public void setChargeSenece(String chargeSenece){
			this.chargeSenece = chargeSenece;
		}
		public String  getOthBankCode(){
			return othBankCode;
		}
		public void setOthBankCode(String othBankCode){
			this.othBankCode = othBankCode;
		}
		public String  getTranCcy(){
			return tranCcy;
		}
		public void setTranCcy(String tranCcy){
			this.tranCcy = tranCcy;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		
	}
	
	

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public ESB_REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public ESB_REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

}