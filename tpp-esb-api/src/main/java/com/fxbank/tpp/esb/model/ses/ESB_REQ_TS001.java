package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.esb.common.TOWN;


/** 
* @ClassName: REQ_TS002 
* @Description: 商行通兑村镇 
* @author Duzhenduo
* @date 2018年12月11日 下午2:04:30 
*  
*/
public class ESB_REQ_TS001 extends ESB_BASE {
	
	private static final long serialVersionUID = -813981987125918717L;

	public ESB_REQ_TS001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = true;
		this.getReqSysHead().setMacValue(TOWN.macDeginId + "|" + TOWN.macNodeId + "|" + TOWN.macKeyModelId + "|" + TOWN.macPlaceHolder + "|");
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("TS001", "");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

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

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY implements Serializable{

		private static final long serialVersionUID = -8415585719972566411L;
		
		@JSONField(name = "BRNO_FLAG")
		private String brnoFlag;    //村镇机构

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}

		@JSONField(name = "PLAT_DATE")
		private String platDate;	//平台日期

		@JSONField(name = "PLAT_TRACENO")
		private String platTraceno;	//平台流水
		
		@JSONField(name = "TX_AMT")
		private String txAmt;		//金额
		
		@JSONField(name = "PAYEE_NAME")
		private String payeeName;		//收款名称
		
		@JSONField(name = "PAYEE_ACC")
		private String payeeAcc;		//收款账号
		
		@JSONField(name = "INFO")
		private String info;		//摘要

		public String getPlatDate() {
			return platDate;
		}

		public void setPlatDate(String platDate) {
			this.platDate = platDate;
		}

		public String getPlatTraceno() {
			return platTraceno;
		}

		public void setPlatTraceno(String platTraceno) {
			this.platTraceno = platTraceno;
		}

		public String getTxAmt() {
			return txAmt;
		}

		public void setTxAmt(String txAmt) {
			this.txAmt = txAmt;
		}

		public String getPayeeName() {
			return payeeName;
		}

		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}

		public String getPayeeAcc() {
			return payeeAcc;
		}

		public void setPayeeAcc(String payeeAcc) {
			this.payeeAcc = payeeAcc;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
		
		
	
	}
}
