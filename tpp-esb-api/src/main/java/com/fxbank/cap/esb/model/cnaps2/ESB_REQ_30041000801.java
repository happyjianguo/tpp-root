package com.fxbank.cap.esb.model.cnaps2;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cap.esb.common.ESB;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30041000801 extends ESB_BASE {

	private static final long serialVersionUID = 6552179889253835098L;

	public ESB_REQ_30041000801 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = true;
		this.getReqSysHead().setMacValue(ESB.macDeginId + "|" + ESB.macNodeId + "|" + ESB.macKeyModelId + "|" + ESB.macPlaceHolder + "|");
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300410008", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 6844620207455677216L;
		
		@JSONField(name = "BUS_VR_T3")
		private String busVrT3;//业务种类
		@JSONField(name = "PYR_NM_T")
		private String pyrNmT;//付款人名称
		@JSONField(name = "PYR_ADDR_T")
		private String pyrAddrT;//付款人地址
		@JSONField(name = "PYR_ACCT_NO_T2")
		private String pyrAcctNoT2;//付款人账号
		@JSONField(name = "RCPT_PR_ACCT_NO_T2")
		private String rcptPrAcctNoT2;//收款人账号
		@JSONField(name = "TRNS_AMT_T2")
		private String trnsAmtT2;//交易金额
		@JSONField(name = "RCVNG_BNK_NO_T6")
		private String rcvngBnkNoT6;//接收行行号
		@JSONField(name = "BUS_TP_T10")
		private String busTpT10;//业务类型
		@JSONField(name = "RCPT_PR_NM_T3")
		private String rcptPrNmT3;//收款人名称
		@JSONField(name = "RCPT_PR_ADDR_T1")
		private String rcptPrAddrT1;//收款人地址
		@JSONField(name = "NOTE_T2")
		private String noteT2;//附言
		
	public String  getBusVrT3(){
			return busVrT3;
		}
		public void setBusVrT3(String busVrT3){
			this.busVrT3 = busVrT3;
		}
		public String  getPyrNmT(){
			return pyrNmT;
		}
		public void setPyrNmT(String pyrNmT){
			this.pyrNmT = pyrNmT;
		}
		public String  getPyrAddrT(){
			return pyrAddrT;
		}
		public void setPyrAddrT(String pyrAddrT){
			this.pyrAddrT = pyrAddrT;
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
		public String  getTrnsAmtT2(){
			return trnsAmtT2;
		}
		public void setTrnsAmtT2(String trnsAmtT2){
			this.trnsAmtT2 = trnsAmtT2;
		}
		public String  getRcvngBnkNoT6(){
			return rcvngBnkNoT6;
		}
		public void setRcvngBnkNoT6(String rcvngBnkNoT6){
			this.rcvngBnkNoT6 = rcvngBnkNoT6;
		}
		public String  getBusTpT10(){
			return busTpT10;
		}
		public void setBusTpT10(String busTpT10){
			this.busTpT10 = busTpT10;
		}
		public String  getRcptPrNmT3(){
			return rcptPrNmT3;
		}
		public void setRcptPrNmT3(String rcptPrNmT3){
			this.rcptPrNmT3 = rcptPrNmT3;
		}
		public String  getRcptPrAddrT1(){
			return rcptPrAddrT1;
		}
		public void setRcptPrAddrT1(String rcptPrAddrT1){
			this.rcptPrAddrT1 = rcptPrAddrT1;
		}
		public String  getNoteT2(){
			return noteT2;
		}
		public void setNoteT2(String noteT2){
			this.noteT2 = noteT2;
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