package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30063001302 
* @Description: 柜面业务流水查询反馈
* @author YePuLiang
* @date 2019年7月12日 上午9:27:35 
*  
*/
public class REP_30063001302 extends REP_BASE {

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
		//柜面交易明细数组
		@JSONField(name = "A_T")
		private List<Trade>  tradeList;
		@JSONField(name = "TT_CNT_T3")
		private String tolCnt; 
		@JSONField(name = "OVRLL_AMT_T2")
		private String tolAmt;
		public List<Trade> getTradeList() {
			return tradeList;
		}
		public void setTradeList(List<Trade> tradeList) {
			this.tradeList = tradeList;
		}
		public String getTolCnt() {
			return tolCnt;
		}
		public void setTolCnt(String tolCnt) {
			this.tolCnt = tolCnt;
		}
		public String getTolAmt() {
			return tolAmt;
		}
		public void setTolAmt(String tolAmt) {
			this.tolAmt = tolAmt;
		} 
		
		
	}
	
	public static class Trade implements Serializable{

		private static final long serialVersionUID = 9142562601686748302L;
		
		@JSONField(name = "PROGRAM_ID_T4")
		private String txCode="281A";
		@JSONField(name = "TRN_SRC_T")
	    private String source;
		@JSONField(name = "PLTFRM_DT_T1")
	    private String platDate;
		@JSONField(name = "PLTFRM_SEQ_T1")
	    private String platTrace;
		@JSONField(name = "HOST_DT_T2")
	    private String hostDate;
		@JSONField(name = "HOST_SRL_NO_T1")
	    private String hostTrace;
		@JSONField(name = "TRAN_TM_T3")
	    private String txDate;
		@JSONField(name = "TRN_STR_BNK_T")
	    private String sndBank;
		@JSONField(name = "TRNS_BRCH_T2")
	    private String txBranch;
		@JSONField(name = "TRAN_TLR_T5")
	    private String txTel;
		@JSONField(name = "BUSI_MD_T")
	    private String txMod;
		@JSONField(name = "TRNS_AMT_T3")
	    private String txAmt;
		@JSONField(name = "BUSI_BNK_FEE_T")
	    private String proxyFee;
		@JSONField(name = "OPN_BNK_DL_T")
	    private String fee;
		@JSONField(name = "FEE_RCVE_WY_T")
	    private String feeFlag;
		@JSONField(name = "PYMT_OPN_BNK_T")
	    private String payerBank;
		@JSONField(name = "PYR_ACCT_NO_T1")
	    private String payerAcno;
		@JSONField(name = "PYR_NM_T6")
	    private String payerName;
		@JSONField(name = "RCPT_OPN_BNK_T2")
	    private String payeeBank;
		@JSONField(name = "RCPT_PR_ACCT_NO_T1")
	    private String payeeAcno;
		@JSONField(name = "RCPT_PR_NM_T9")
	    private String payeeName;
		@JSONField(name = "HOST_DL_ST_T1")
	    private String hostState;
		@JSONField(name = "HOST_CNST_ST_T1")
	    private String checkFlag;
		public String getTxCode() {
			return txCode;
		}
		public void setTxCode(String txCode) {
			this.txCode = txCode;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getPlatDate() {
			return platDate;
		}
		public void setPlatDate(String platDate) {
			this.platDate = platDate;
		}
		public String getPlatTrace() {
			return platTrace;
		}
		public void setPlatTrace(String platTrace) {
			this.platTrace = platTrace;
		}
		public String getHostDate() {
			return hostDate;
		}
		public void setHostDate(String hostDate) {
			this.hostDate = hostDate;
		}
		public String getHostTrace() {
			return hostTrace;
		}
		public void setHostTrace(String hostTrace) {
			this.hostTrace = hostTrace;
		}
		public String getTxDate() {
			return txDate;
		}
		public void setTxDate(String txDate) {
			this.txDate = txDate;
		}
		public String getSndBank() {
			return sndBank;
		}
		public void setSndBank(String sndBank) {
			this.sndBank = sndBank;
		}
		public String getTxBranch() {
			return txBranch;
		}
		public void setTxBranch(String txBranch) {
			this.txBranch = txBranch;
		}
		public String getTxTel() {
			return txTel;
		}
		public void setTxTel(String txTel) {
			this.txTel = txTel;
		}
		public String getTxMod() {
			return txMod;
		}
		public void setTxMod(String txMod) {
			this.txMod = txMod;
		}
		public String getTxAmt() {
			return txAmt;
		}
		public void setTxAmt(String txAmt) {
			this.txAmt = txAmt;
		}
		public String getProxyFee() {
			return proxyFee;
		}
		public void setProxyFee(String proxyFee) {
			this.proxyFee = proxyFee;
		}
		public String getFee() {
			return fee;
		}
		public void setFee(String fee) {
			this.fee = fee;
		}
		public String getFeeFlag() {
			return feeFlag;
		}
		public void setFeeFlag(String feeFlag) {
			this.feeFlag = feeFlag;
		}
		public String getPayerBank() {
			return payerBank;
		}
		public void setPayerBank(String payerBank) {
			this.payerBank = payerBank;
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
		public String getPayeeBank() {
			return payeeBank;
		}
		public void setPayeeBank(String payeeBank) {
			this.payeeBank = payeeBank;
		}
		public String getPayeeAcno() {
			return payeeAcno;
		}
		public void setPayeeAcno(String payeeAcno) {
			this.payeeAcno = payeeAcno;
		}
		public String getPayeeName() {
			return payeeName;
		}
		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}
		public String getHostState() {
			return hostState;
		}
		public void setHostState(String hostState) {
			this.hostState = hostState;
		}
		public String getCheckFlag() {
			return checkFlag;
		}
		public void setCheckFlag(String checkFlag) {
			this.checkFlag = checkFlag;
		}	
		
		
	}
}
