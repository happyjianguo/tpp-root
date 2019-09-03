package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061800101 
* @Description: 本行柜面本行卡付款转账
* @author Duzhenduo
* @date 2019年4月16日 上午10:43:50 
*  
*/
public class REQ_30061000701 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30061000701(){
		super.txDesc = "本行卡付款转账";
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

		@JSONField(name = "BNK_CARD_USE_CCY_T")
		private String bnkCardUseCcyT;//本行卡使用的币种
		@JSONField(name = "BCM_CARD_USE_CCY_T")
		private String bcmCardUseCcyT;//交行卡使用的币种
		@JSONField(name = "BNK_CARD_ACCT_NA_T")
		private String bnkCardAcctNaT;//本行卡账户姓名
		@JSONField(name = "BCM_CARD_ACCT_NA_T")
		private String bcmCardAcctNaT;//交行卡账户姓名
		@JSONField(name = "ACCT_BRANCH_T1")
		private String acctBranchT1;//开户机构
		@JSONField(name = "BNK_CARD_ACCT_NO_T")
		private String bnkCardAcctNoT;//本行卡账号
		@JSONField(name = "BCM_CARD_ACCT_NO_T")
		private String bcmCardAcctNoT;//交行卡账号
		@JSONField(name = "TRSR_AMT_T3")
		private String trsrAmtT3;//转账金额
		@JSONField(name = "FEE_T3")
		private String feeT3;//手续费
		@JSONField(name = "HNDL_PYMNT_FEE_T5")
		private String hndlPymntFeeT5;//应收手续费
		@JSONField(name = "ACCT_SQ_NO_T2")
		private String acctSqNoT2;//账户序号
		@JSONField(name = "VCHR_NO_T1")
		private String vchrNoT1;//凭证号码
		@JSONField(name = "BCM_CARD_ACCT_CRTF_NO_T")
		private String bcmCardAcctCrtfNoT;//交行卡账户证件号码
		@JSONField(name = "CMSN_HLDR_GLBL_ID_T")
		private String cmsnHldrGlblIdT;//代理人证件号码
		@JSONField(name = "PYR_ACCT_TP_T")
		private String pyrAcctTpT;//付款人账户类型
		@JSONField(name = "RCVE_WY_T")
		private String rcveWyT;//收取方式
		@JSONField(name = "ACCT_TP_T")
		private String acctTpT;//账户类型
		@JSONField(name = "BUSI_MD_T1")
		private String busiMdT1;//业务模式
		@JSONField(name = "IN_WY_T")
		private String inWyT;//输入方式
		@JSONField(name = "PWD_T")
		private String pwdT;//密码
		@JSONField(name = "NOTE_T2")
		private String noteT2;//附言
		@JSONField(name = "PYR_OPN_BNK_NO_T2")
		private String pyrOpnBnkNoT2;//付款人开户行号
		@JSONField(name = "PYEE_OPN_BNK_NO_T1")
		private String pyeeOpnBnkNoT1;//收款人开户行号
		@JSONField(name = "HM_FEE_T")
		private String hmFeeT;//本行手续费
		@JSONField(name = "BCM_CARD_ACCT_CRTF_TP_T")
		private String bcmCardAcctCrtfTpT;//交行卡账户证件类型
		@JSONField(name = "AGENT_CRTF_T")
		private String agentCrtfT;//代理证件
		@JSONField(name = "IS_VCHR_PSWD_T")
		private String isVchrPswdT;//是否凭密
		@JSONField(name = "HLDR_GLBL_ID_T")
		private String hldrGlblIdT;//证件号码
		@JSONField(name = "ID_TP_T2")
		private String idTpT2;//证件类型
		@JSONField(name = "BRCH_NO_T8")
		private String brchNoT8;//机构号1
		@JSONField(name = "BRCH_NO_T9")
		private String brchNoT9;//机构号2
		@JSONField(name = "FST_TRK_INFO_T1")
		private String fstTrkInfoT1;//一磁道信息
		@JSONField(name = "SCD_TRK_INFO_T2")
		private String scdTrkInfoT2;//二磁道信息
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
		
	public String  getBnkCardUseCcyT(){
			return bnkCardUseCcyT;
		}
		public void setBnkCardUseCcyT(String bnkCardUseCcyT){
			this.bnkCardUseCcyT = bnkCardUseCcyT;
		}
		public String  getBcmCardUseCcyT(){
			return bcmCardUseCcyT;
		}
		public void setBcmCardUseCcyT(String bcmCardUseCcyT){
			this.bcmCardUseCcyT = bcmCardUseCcyT;
		}
		public String  getBnkCardAcctNaT(){
			return bnkCardAcctNaT;
		}
		public void setBnkCardAcctNaT(String bnkCardAcctNaT){
			this.bnkCardAcctNaT = bnkCardAcctNaT;
		}
		public String  getBcmCardAcctNaT(){
			return bcmCardAcctNaT;
		}
		public void setBcmCardAcctNaT(String bcmCardAcctNaT){
			this.bcmCardAcctNaT = bcmCardAcctNaT;
		}
		public String  getAcctBranchT1(){
			return acctBranchT1;
		}
		public void setAcctBranchT1(String acctBranchT1){
			this.acctBranchT1 = acctBranchT1;
		}
		public String  getBnkCardAcctNoT(){
			return bnkCardAcctNoT;
		}
		public void setBnkCardAcctNoT(String bnkCardAcctNoT){
			this.bnkCardAcctNoT = bnkCardAcctNoT;
		}
		public String  getBcmCardAcctNoT(){
			return bcmCardAcctNoT;
		}
		public void setBcmCardAcctNoT(String bcmCardAcctNoT){
			this.bcmCardAcctNoT = bcmCardAcctNoT;
		}
		public String  getTrsrAmtT3(){
			return trsrAmtT3;
		}
		public void setTrsrAmtT3(String trsrAmtT3){
			this.trsrAmtT3 = trsrAmtT3;
		}
		public String  getFeeT3(){
			return feeT3;
		}
		public void setFeeT3(String feeT3){
			this.feeT3 = feeT3;
		}
		public String  getHndlPymntFeeT5(){
			return hndlPymntFeeT5;
		}
		public void setHndlPymntFeeT5(String hndlPymntFeeT5){
			this.hndlPymntFeeT5 = hndlPymntFeeT5;
		}
		public String  getAcctSqNoT2(){
			return acctSqNoT2;
		}
		public void setAcctSqNoT2(String acctSqNoT2){
			this.acctSqNoT2 = acctSqNoT2;
		}
		public String  getVchrNoT1(){
			return vchrNoT1;
		}
		public void setVchrNoT1(String vchrNoT1){
			this.vchrNoT1 = vchrNoT1;
		}
		public String  getBcmCardAcctCrtfNoT(){
			return bcmCardAcctCrtfNoT;
		}
		public void setBcmCardAcctCrtfNoT(String bcmCardAcctCrtfNoT){
			this.bcmCardAcctCrtfNoT = bcmCardAcctCrtfNoT;
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
		public String  getRcveWyT(){
			return rcveWyT;
		}
		public void setRcveWyT(String rcveWyT){
			this.rcveWyT = rcveWyT;
		}
		public String  getAcctTpT(){
			return acctTpT;
		}
		public void setAcctTpT(String acctTpT){
			this.acctTpT = acctTpT;
		}
		public String  getBusiMdT1(){
			return busiMdT1;
		}
		public void setBusiMdT1(String busiMdT1){
			this.busiMdT1 = busiMdT1;
		}
		public String  getInWyT(){
			return inWyT;
		}
		public void setInWyT(String inWyT){
			this.inWyT = inWyT;
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
		public String  getPyeeOpnBnkNoT1(){
			return pyeeOpnBnkNoT1;
		}
		public void setPyeeOpnBnkNoT1(String pyeeOpnBnkNoT1){
			this.pyeeOpnBnkNoT1 = pyeeOpnBnkNoT1;
		}
		public String  getHmFeeT(){
			return hmFeeT;
		}
		public void setHmFeeT(String hmFeeT){
			this.hmFeeT = hmFeeT;
		}
		public String  getBcmCardAcctCrtfTpT(){
			return bcmCardAcctCrtfTpT;
		}
		public void setBcmCardAcctCrtfTpT(String bcmCardAcctCrtfTpT){
			this.bcmCardAcctCrtfTpT = bcmCardAcctCrtfTpT;
		}
		public String  getAgentCrtfT(){
			return agentCrtfT;
		}
		public void setAgentCrtfT(String agentCrtfT){
			this.agentCrtfT = agentCrtfT;
		}
		public String  getIsVchrPswdT(){
			return isVchrPswdT;
		}
		public void setIsVchrPswdT(String isVchrPswdT){
			this.isVchrPswdT = isVchrPswdT;
		}
		public String  getHldrGlblIdT(){
			return hldrGlblIdT;
		}
		public void setHldrGlblIdT(String hldrGlblIdT){
			this.hldrGlblIdT = hldrGlblIdT;
		}
		public String  getIdTpT2(){
			return idTpT2;
		}
		public void setIdTpT2(String idTpT2){
			this.idTpT2 = idTpT2;
		}
		public String  getBrchNoT8(){
			return brchNoT8;
		}
		public void setBrchNoT8(String brchNoT8){
			this.brchNoT8 = brchNoT8;
		}
		public String  getBrchNoT9(){
			return brchNoT9;
		}
		public void setBrchNoT9(String brchNoT9){
			this.brchNoT9 = brchNoT9;
		}
		public String  getFstTrkInfoT1(){
			return fstTrkInfoT1;
		}
		public void setFstTrkInfoT1(String fstTrkInfoT1){
			this.fstTrkInfoT1 = fstTrkInfoT1;
		}
		public String  getScdTrkInfoT2(){
			return scdTrkInfoT2;
		}
		public void setScdTrkInfoT2(String scdTrkInfoT2){
			this.scdTrkInfoT2 = scdTrkInfoT2;
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
