package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061800201 
* @Description: 交行柜面交行卡付款转账
* @author Duzhenduo
* @date 2019年4月16日 上午10:50:54 
*  
*/
public class REQ_30061000801 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30061000801(){
		super.txDesc = "交行卡付款转账";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}


	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}


	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}


	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		@JSONField(name = "BCM_CARD_USE_CCY_T")
		private String bcmCardUseCcyT;//交行卡使用的币种
		@JSONField(name = "BNK_CARD_USE_CCY_T")
		private String bnkCardUseCcyT;//本行卡使用的币种
		@JSONField(name = "PYR_NA_T")
		private String pyrNaT;//付款人姓名
		@JSONField(name = "RCPT_PR_NM_T7")
		private String rcptPrNmT7;//收款人姓名
		@JSONField(name = "PY_OPN_BR_NO_T")
		private String pyOpnBrNoT;//收款人开户机构号
		@JSONField(name = "PYR_ACCT_NO_T2")
		private String pyrAcctNoT2;//付款人账号
		@JSONField(name = "RCPT_PR_ACCT_NO_T2")
		private String rcptPrAcctNoT2;//收款人账号
		@JSONField(name = "TRSR_AMT_T3")
		private String trsrAmtT3;//转账金额
		@JSONField(name = "FEE_AMT_T3")
		private String feeAmtT3;//手续费金额
		@JSONField(name = "BAL_T")
		private String balT;//余额
		@JSONField(name = "HNDL_PYMNT_FEE_T5")
		private String hndlPymntFeeT5;//应收手续费
		@JSONField(name = "BNK_CARD_ACCT_VCHR_NO_T")
		private String bnkCardAcctVchrNoT;//本行卡账户凭证号码
		@JSONField(name = "HLDR_GLBL_ID_T")
		private String hldrGlblIdT;//证件号码
		@JSONField(name = "CMSN_HLDR_GLBL_ID_T")
		private String cmsnHldrGlblIdT;//代理人证件号码
		@JSONField(name = "PYR_ACCT_TP_T")
		private String pyrAcctTpT;//付款人账户类型
		@JSONField(name = "FEE_RCVE_WY_T1")
		private String feeRcveWyT1;//手续费收取方式
		@JSONField(name = "PY_ACCT_TP_T")
		private String pyAcctTpT;//收款人账户类型
		@JSONField(name = "BUSI_MD_T1")
		private String busiMdT1;//业务模式
		@JSONField(name = "CARD_IN_WY_T")
		private String cardInWyT;//卡输入方式
		@JSONField(name = "SCD_TRK_T")
		private String scdTrkT;//二磁道
		@JSONField(name = "PWD_T")
		private String pwdT;//密码
		@JSONField(name = "NOTE_T2")
		private String noteT2;//附言
		@JSONField(name = "PYR_OPN_BNK_NO_T2")
		private String pyrOpnBnkNoT2;//付款人开户行号
		@JSONField(name = "ID_TP_T2")
		private String idTpT2;//证件类型
		@JSONField(name = "AGENT_CRTF_T")
		private String agentCrtfT;//代理证件
		@JSONField(name = "ACCT_SQ_NO_T2")
		private String acctSqNoT2;//账户序号
		@JSONField(name = "RTN_SCD_TRK_T")
		private String rtnScdTrkT;//存二磁
		@JSONField(name = "PYEE_OPN_BNK_NO_T6")
		private String pyeeOpnBnkNoT6;//收款人开户行行号
		@JSONField(name = "FST_TRK_INFO_T1")
		private String fstTrkInfoT1;//一磁道信息
		@JSONField(name = "THR_TRK_INFO_T1")
		private String thrTrkInfoT1;//三磁道信息
		@JSONField(name = "IC_CARD_SEQ_NO_T1")
		private String icCardSeqNoT1;//IC卡顺序号
		@JSONField(name = "IC_CARD_91_T")
		private String icCard91T;//IC卡发卡行认证
		@JSONField(name = "IC_CARD_9F09_T")
		private String icCard9f09T;//IC卡应用编号
		@JSONField(name = "IC_CARD_AVAI_DT_T")
		private String icCardAvaiDtT;//IC卡有效期
		@JSONField(name = "IC_CARD_F55_T")
		private String icCardF55T;//IC卡数据域(55域)
		@JSONField(name = "IC_CARD_FLG_T4")
		private String icCardFlgT4;//IC卡磁条卡标志
		
	public String  getBcmCardUseCcyT(){
			return bcmCardUseCcyT;
		}
		public void setBcmCardUseCcyT(String bcmCardUseCcyT){
			this.bcmCardUseCcyT = bcmCardUseCcyT;
		}
		public String  getBnkCardUseCcyT(){
			return bnkCardUseCcyT;
		}
		public void setBnkCardUseCcyT(String bnkCardUseCcyT){
			this.bnkCardUseCcyT = bnkCardUseCcyT;
		}
		public String  getPyrNaT(){
			return pyrNaT;
		}
		public void setPyrNaT(String pyrNaT){
			this.pyrNaT = pyrNaT;
		}
		public String  getRcptPrNmT7(){
			return rcptPrNmT7;
		}
		public void setRcptPrNmT7(String rcptPrNmT7){
			this.rcptPrNmT7 = rcptPrNmT7;
		}
		public String  getPyOpnBrNoT(){
			return pyOpnBrNoT;
		}
		public void setPyOpnBrNoT(String pyOpnBrNoT){
			this.pyOpnBrNoT = pyOpnBrNoT;
		}
		public String  getPyrAcctNoT2(){
			return pyrAcctNoT2;
		}
		public void setPyrAcctNoT2(String pyrAcctNoT2){
			this.pyrAcctNoT2 = pyrAcctNoT2;
		}
		public String  getRcptPrAcctNoT2(){
			return rcptPrAcctNoT2;
		}
		public void setRcptPrAcctNoT2(String rcptPrAcctNoT2){
			this.rcptPrAcctNoT2 = rcptPrAcctNoT2;
		}
		public String  getTrsrAmtT3(){
			return trsrAmtT3;
		}
		public void setTrsrAmtT3(String trsrAmtT3){
			this.trsrAmtT3 = trsrAmtT3;
		}
		public String  getFeeAmtT3(){
			return feeAmtT3;
		}
		public void setFeeAmtT3(String feeAmtT3){
			this.feeAmtT3 = feeAmtT3;
		}
		public String  getBalT(){
			return balT;
		}
		public void setBalT(String balT){
			this.balT = balT;
		}
		public String  getHndlPymntFeeT5(){
			return hndlPymntFeeT5;
		}
		public void setHndlPymntFeeT5(String hndlPymntFeeT5){
			this.hndlPymntFeeT5 = hndlPymntFeeT5;
		}
		public String  getBnkCardAcctVchrNoT(){
			return bnkCardAcctVchrNoT;
		}
		public void setBnkCardAcctVchrNoT(String bnkCardAcctVchrNoT){
			this.bnkCardAcctVchrNoT = bnkCardAcctVchrNoT;
		}
		public String  getHldrGlblIdT(){
			return hldrGlblIdT;
		}
		public void setHldrGlblIdT(String hldrGlblIdT){
			this.hldrGlblIdT = hldrGlblIdT;
		}
		public String  getCmsnHldrGlblIdT(){
			return cmsnHldrGlblIdT;
		}
		public void setCmsnHldrGlblIdT(String cmsnHldrGlblIdT){
			this.cmsnHldrGlblIdT = cmsnHldrGlblIdT;
		}
		public String  getPyrAcctTpT(){
			return pyrAcctTpT;
		}
		public void setPyrAcctTpT(String pyrAcctTpT){
			this.pyrAcctTpT = pyrAcctTpT;
		}
		public String  getFeeRcveWyT1(){
			return feeRcveWyT1;
		}
		public void setFeeRcveWyT1(String feeRcveWyT1){
			this.feeRcveWyT1 = feeRcveWyT1;
		}
		public String  getPyAcctTpT(){
			return pyAcctTpT;
		}
		public void setPyAcctTpT(String pyAcctTpT){
			this.pyAcctTpT = pyAcctTpT;
		}
		public String  getBusiMdT1(){
			return busiMdT1;
		}
		public void setBusiMdT1(String busiMdT1){
			this.busiMdT1 = busiMdT1;
		}
		public String  getCardInWyT(){
			return cardInWyT;
		}
		public void setCardInWyT(String cardInWyT){
			this.cardInWyT = cardInWyT;
		}
		public String  getScdTrkT(){
			return scdTrkT;
		}
		public void setScdTrkT(String scdTrkT){
			this.scdTrkT = scdTrkT;
		}
		public String  getPwdT(){
			return pwdT;
		}
		public void setPwdT(String pwdT){
			this.pwdT = pwdT;
		}
		public String  getNoteT2(){
			return noteT2;
		}
		public void setNoteT2(String noteT2){
			this.noteT2 = noteT2;
		}
		public String  getPyrOpnBnkNoT2(){
			return pyrOpnBnkNoT2;
		}
		public void setPyrOpnBnkNoT2(String pyrOpnBnkNoT2){
			this.pyrOpnBnkNoT2 = pyrOpnBnkNoT2;
		}
		public String  getIdTpT2(){
			return idTpT2;
		}
		public void setIdTpT2(String idTpT2){
			this.idTpT2 = idTpT2;
		}
		public String  getAgentCrtfT(){
			return agentCrtfT;
		}
		public void setAgentCrtfT(String agentCrtfT){
			this.agentCrtfT = agentCrtfT;
		}
		public String  getAcctSqNoT2(){
			return acctSqNoT2;
		}
		public void setAcctSqNoT2(String acctSqNoT2){
			this.acctSqNoT2 = acctSqNoT2;
		}
		public String  getRtnScdTrkT(){
			return rtnScdTrkT;
		}
		public void setRtnScdTrkT(String rtnScdTrkT){
			this.rtnScdTrkT = rtnScdTrkT;
		}
		public String  getPyeeOpnBnkNoT6(){
			return pyeeOpnBnkNoT6;
		}
		public void setPyeeOpnBnkNoT6(String pyeeOpnBnkNoT6){
			this.pyeeOpnBnkNoT6 = pyeeOpnBnkNoT6;
		}
		public String  getFstTrkInfoT1(){
			return fstTrkInfoT1;
		}
		public void setFstTrkInfoT1(String fstTrkInfoT1){
			this.fstTrkInfoT1 = fstTrkInfoT1;
		}
		public String  getThrTrkInfoT1(){
			return thrTrkInfoT1;
		}
		public void setThrTrkInfoT1(String thrTrkInfoT1){
			this.thrTrkInfoT1 = thrTrkInfoT1;
		}
		public String  getIcCardSeqNoT1(){
			return icCardSeqNoT1;
		}
		public void setIcCardSeqNoT1(String icCardSeqNoT1){
			this.icCardSeqNoT1 = icCardSeqNoT1;
		}
		public String  getIcCard91T(){
			return icCard91T;
		}
		public void setIcCard91T(String icCard91T){
			this.icCard91T = icCard91T;
		}
		public String  getIcCard9f09T(){
			return icCard9f09T;
		}
		public void setIcCard9f09T(String icCard9f09T){
			this.icCard9f09T = icCard9f09T;
		}
		public String  getIcCardAvaiDtT(){
			return icCardAvaiDtT;
		}
		public void setIcCardAvaiDtT(String icCardAvaiDtT){
			this.icCardAvaiDtT = icCardAvaiDtT;
		}
		public String  getIcCardF55T(){
			return icCardF55T;
		}
		public void setIcCardF55T(String icCardF55T){
			this.icCardF55T = icCardF55T;
		}
		public String  getIcCardFlgT4(){
			return icCardFlgT4;
		}
		public void setIcCardFlgT4(String icCardFlgT4){
			this.icCardFlgT4 = icCardFlgT4;
		}
		
	}
}