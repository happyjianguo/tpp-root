package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30013000305 extends ESB_BASE {

	private static final long serialVersionUID = 3688897270642946010L;

	@Deprecated
	public ESB_REP_30013000305() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013000305(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

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

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -5953041013783324074L;
		@JSONField(name = "TRAN_HIST_ARRAY")
		private List<TranHist>  tranHistArray;//账户交易历史数组
		public List<TranHist>  getTranHistArray(){
			return tranHistArray;
		}
		public void setTranHistArray(List<TranHist> tranHistArray){
			this.tranHistArray = tranHistArray;
		}
		
	}
	
	public static class TranHist implements Serializable {
		private static final long serialVersionUID = -5083125042216358383L;
		@JSONField(name = "TRAN_DATE")
		private String tranDate;//交易日期
		@JSONField(name = "TRAN_TIME")
		private String tranTime;//交易时间
		@JSONField(name = "ACCT_DETAIL_NO")
		private String acctDetailNo;//明细记录顺序号
		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "CCY")
		private String ccy;//币种
		@JSONField(name = "CURR_IDEN")
		private String currIden ;//钞汇鉴别
		@JSONField(name = "CR_AMOUNT")
		private String crAmount;//贷方发生额
		@JSONField(name = "DR_AMOUNT")
		private String drAmount;//借方发生额
		@JSONField(name = "ACCT_BAL")
		private String acctBal;//余额
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//发生额
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "REMARK")
		private String remark;//备注
		@JSONField(name = "REFERENCE")
		private String reference;//银行主机流水
		@JSONField(name = "TRAN_NOTE")
		private String tranNote;//摘要
		public String getTranDate() {
			return tranDate;
		}
		public void setTranDate(String tranDate) {
			this.tranDate = tranDate;
		}
		public String getTranTime() {
			return tranTime;
		}
		public void setTranTime(String tranTime) {
			this.tranTime = tranTime;
		}
		public String getAcctDetailNo() {
			return acctDetailNo;
		}
		public void setAcctDetailNo(String acctDetailNo) {
			this.acctDetailNo = acctDetailNo;
		}
		public String getBaseAcctNo() {
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo) {
			this.baseAcctNo = baseAcctNo;
		}
		public String getAcctName() {
			return acctName;
		}
		public void setAcctName(String acctName) {
			this.acctName = acctName;
		}
		public String getCcy() {
			return ccy;
		}
		public void setCcy(String ccy) {
			this.ccy = ccy;
		}
		public String getCurrIden() {
			return currIden;
		}
		public void setCurrIden(String currIden) {
			this.currIden = currIden;
		}
		public String getCrAmount() {
			return crAmount;
		}
		public void setCrAmount(String crAmount) {
			this.crAmount = crAmount;
		}
		public String getDrAmount() {
			return drAmount;
		}
		public void setDrAmount(String drAmount) {
			this.drAmount = drAmount;
		}
		public String getAcctBal() {
			return acctBal;
		}
		public void setAcctBal(String acctBal) {
			this.acctBal = acctBal;
		}
		public String getTranAmt() {
			return tranAmt;
		}
		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getVoucherNo() {
			return voucherNo;
		}
		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getTranNote() {
			return tranNote;
		}
		public void setTranNote(String tranNote) {
			this.tranNote = tranNote;
		}
		
		
	}
	

}
