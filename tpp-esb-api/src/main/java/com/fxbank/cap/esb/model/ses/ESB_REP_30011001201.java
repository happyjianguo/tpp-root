package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30011001201 extends ESB_BASE {


	private static final long serialVersionUID = -4272558842635020887L;

	@Deprecated
	public ESB_REP_30011001201() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30011001201(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 202029883214270075L;
		@JSONField(name = "REFERENCE")
		private String reference;//交易参考号
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "PROD_TYPE")
		private String prodType;//产品类型
		@JSONField(name = "ACCT_SEQ_NO")
		private String acctSeqNo;//账户序号
		@JSONField(name = "ACTUAL_BAL")
		private String actualBal;//实际余额
		@JSONField(name = "REAL_RATE")
		private String realRate;//执行利率
		@JSONField(name = "ACCR_INT_ADJ")
		private String accrIntAdj;//计提利息调整
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		public String  getReference(){
			return reference;
		}
		public void setReference(String reference){
			this.reference = reference;
		}
		public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getCcy(){
			return ccy;
		}
		public void setCcy(String ccy){
			this.ccy = ccy;
		}
		public String  getProdType(){
			return prodType;
		}
		public void setProdType(String prodType){
			this.prodType = prodType;
		}
		public String  getAcctSeqNo(){
			return acctSeqNo;
		}
		public void setAcctSeqNo(String acctSeqNo){
			this.acctSeqNo = acctSeqNo;
		}
		public String  getActualBal(){
			return actualBal;
		}
		public void setActualBal(String actualBal){
			this.actualBal = actualBal;
		}
		public String  getRealRate(){
			return realRate;
		}
		public void setRealRate(String realRate){
			this.realRate = realRate;
		}
		public String  getAccrIntAdj(){
			return accrIntAdj;
		}
		public void setAccrIntAdj(String accrIntAdj){
			this.accrIntAdj = accrIntAdj;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
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
