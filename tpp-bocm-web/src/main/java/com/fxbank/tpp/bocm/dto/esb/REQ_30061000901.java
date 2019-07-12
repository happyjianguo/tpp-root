package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061800301 
* @Description: 本行柜面交行卡存现金
* @author Duzhenduo
* @date 2019年4月15日 下午4:42:46 
*  
*/
public class REQ_30061000901 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30061000901(){
		super.txDesc = "交行卡存现金";
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
		@JSONField(name = "NA_T1")
		private String naT1;//姓名
		@JSONField(name = "CARD_NO_T3")
		private String cardNoT3;//卡号
		@JSONField(name = "DPS_AMT_T")
		private String dpsAmtT;//存款金额
		@JSONField(name = "FEE_T3")
		private String feeT3;//手续费
		@JSONField(name = "ACCT_BAL_T3")
		private String acctBalT3;//账户余额
		@JSONField(name = "HNDL_PYMNT_FEE_T5")
		private String hndlPymntFeeT5;//应收手续费
		@JSONField(name = "HLDR_GLBL_ID_T")
		private String hldrGlblIdT;//证件号码
		@JSONField(name = "AGENT_CRTF_NO_T")
		private String agentCrtfNoT;//代理证件号码
		@JSONField(name = "RCVE_WY_T")
		private String rcveWyT;//收取方式
		@JSONField(name = "ACCT_TP_T")
		private String acctTpT;//账户类型
		@JSONField(name = "BUSI_MD_T1")
		private String busiMdT1;//业务模式
		@JSONField(name = "RD_CARD_WY_T")
		private String rdCardWyT;//读卡方式
		@JSONField(name = "OPN_ACCT_BNK_NO_T8")
		private String opnAcctBnkNoT8;//开户行号
		@JSONField(name = "ID_TP_T2")
		private String idTpT2;//证件类型
		@JSONField(name = "AGENT_CRTF_T")
		private String agentCrtfT;//代理证件
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
		public String  getNaT1(){
			return naT1;
		}
		public void setNaT1(String naT1){
			this.naT1 = naT1;
		}
		public String  getCardNoT3(){
			return cardNoT3;
		}
		public void setCardNoT3(String cardNoT3){
			this.cardNoT3 = cardNoT3;
		}
		public String  getDpsAmtT(){
			return dpsAmtT;
		}
		public void setDpsAmtT(String dpsAmtT){
			this.dpsAmtT = dpsAmtT;
		}
		public String  getFeeT3(){
			return feeT3;
		}
		public void setFeeT3(String feeT3){
			this.feeT3 = feeT3;
		}
		public String  getAcctBalT3(){
			return acctBalT3;
		}
		public void setAcctBalT3(String acctBalT3){
			this.acctBalT3 = acctBalT3;
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
		public String  getAgentCrtfNoT(){
			return agentCrtfNoT;
		}
		public void setAgentCrtfNoT(String agentCrtfNoT){
			this.agentCrtfNoT = agentCrtfNoT;
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
		public String  getRdCardWyT(){
			return rdCardWyT;
		}
		public void setRdCardWyT(String rdCardWyT){
			this.rdCardWyT = rdCardWyT;
		}
		public String  getOpnAcctBnkNoT8(){
			return opnAcctBnkNoT8;
		}
		public void setOpnAcctBnkNoT8(String opnAcctBnkNoT8){
			this.opnAcctBnkNoT8 = opnAcctBnkNoT8;
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