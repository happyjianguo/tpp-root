package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061800401 
* @Description: 本行柜面交行卡取现金 
* @author Duzhenduo
* @date 2019年4月16日 上午9:19:53 
*  
*/
public class REQ_30061001001 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30061001001(){
		super.txDesc = "交行卡取现金";
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

		@JSONField(name = "CCY_T")
		private String ccyT;//币种
		@JSONField(name = "NM_T")
		private String nmT;//名称
		@JSONField(name = "CARD_NO_T3")
		private String cardNoT3;//卡号
		@JSONField(name = "WTHR_AMT_T")
		private String wthrAmtT;//取款金额
		@JSONField(name = "FEE_AMT_T3")
		private String feeAmtT3;//手续费金额
		@JSONField(name = "BAL_T")
		private String balT;//余额
		@JSONField(name = "HNDL_PYMNT_FEE_T5")
		private String hndlPymntFeeT5;//应收手续费
		@JSONField(name = "HLDR_GLBL_ID_T")
		private String hldrGlblIdT;//证件号码
		@JSONField(name = "CMSN_HLDR_GLBL_ID_T")
		private String cmsnHldrGlblIdT;//代理人证件号码
		@JSONField(name = "ACCT_NO_TP_T")
		private String acctNoTpT;//账号类型
		@JSONField(name = "FEE_RCVE_WY_T1")
		private String feeRcveWyT1;//手续费收取方式
		@JSONField(name = "BUS_TP_T13")
		private String busTpT13;//业务类型
		@JSONField(name = "CARD_IN_WY_T")
		private String cardInWyT;//卡输入方式
		@JSONField(name = "PWD_T")
		private String pwdT;//密码
		@JSONField(name = "OPN_ACCT_BNK_NO_T7")
		private String opnAcctBnkNoT7;//开户行行号
		@JSONField(name = "ID_TP_T2")
		private String idTpT2;//证件类型
		@JSONField(name = "AGENT_ID_TP_T3")
		private String agentIdTpT3;//代理人证件类型
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
		
	public String  getCcyT(){
			return ccyT;
		}
		public void setCcyT(String ccyT){
			this.ccyT = ccyT;
		}
		public String  getNmT(){
			return nmT;
		}
		public void setNmT(String nmT){
			this.nmT = nmT;
		}
		public String  getCardNoT3(){
			return cardNoT3;
		}
		public void setCardNoT3(String cardNoT3){
			this.cardNoT3 = cardNoT3;
		}
		public String  getWthrAmtT(){
			return wthrAmtT;
		}
		public void setWthrAmtT(String wthrAmtT){
			this.wthrAmtT = wthrAmtT;
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
		public String  getAcctNoTpT(){
			return acctNoTpT;
		}
		public void setAcctNoTpT(String acctNoTpT){
			this.acctNoTpT = acctNoTpT;
		}
		public String  getFeeRcveWyT1(){
			return feeRcveWyT1;
		}
		public void setFeeRcveWyT1(String feeRcveWyT1){
			this.feeRcveWyT1 = feeRcveWyT1;
		}
		public String  getBusTpT13(){
			return busTpT13;
		}
		public void setBusTpT13(String busTpT13){
			this.busTpT13 = busTpT13;
		}
		public String  getCardInWyT(){
			return cardInWyT;
		}
		public void setCardInWyT(String cardInWyT){
			this.cardInWyT = cardInWyT;
		}
		public String  getPwdT(){
			return pwdT;
		}
		public void setPwdT(String pwdT){
			this.pwdT = pwdT;
		}
		public String  getOpnAcctBnkNoT7(){
			return opnAcctBnkNoT7;
		}
		public void setOpnAcctBnkNoT7(String opnAcctBnkNoT7){
			this.opnAcctBnkNoT7 = opnAcctBnkNoT7;
		}
		public String  getIdTpT2(){
			return idTpT2;
		}
		public void setIdTpT2(String idTpT2){
			this.idTpT2 = idTpT2;
		}
		public String  getAgentIdTpT3(){
			return agentIdTpT3;
		}
		public void setAgentIdTpT3(String agentIdTpT3){
			this.agentIdTpT3 = agentIdTpT3;
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