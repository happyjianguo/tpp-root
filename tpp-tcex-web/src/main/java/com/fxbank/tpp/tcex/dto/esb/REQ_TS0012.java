package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_TESTTS002
* @Description: 商行通兑村镇业务
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_TS0012 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_TS0012(){
		super.txDesc = "商行通兑村镇业务";
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
		
		@JSONField(name = "BRNO_FLAG")
		private String brnoFlag;    //村镇机构

		@JSONField(name = "PAYER_NAME")
		private String payerName;	//付款人名称

		@JSONField(name = "PAYER_ACC")
		private String payerAcc;	//付款人账号
		
		@JSONField(name = "PAYER_PWD")
		private String payerPwd;		//付款账户密码
		
		@JSONField(name = "PLAT_DATE")
		private String platDate;		//平台日期
		
		@JSONField(name = "PLAT_TRACENO")
		private String platTraceno;		//平台流水
		
		@JSONField(name = "TX_AMT")
		private String txAmt;		//交易金额
		
		@JSONField(name = "INFO")
		private String info;		//摘要
		
		@JSONField(name = "ID_TYPE")
		private String idType;      //证件类型
		
		@JSONField(name = "ID_NO")
		private String idNo;        //证件号码

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getPayerAcc() {
			return payerAcc;
		}

		public void setPayerAcc(String payerAcc) {
			this.payerAcc = payerAcc;
		}

		public String getPayerPwd() {
			return payerPwd;
		}

		public void setPayerPwd(String payerPwd) {
			this.payerPwd = payerPwd;
		}

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

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}

		public String getIdType() {
			return idType;
		}

		public void setIdType(String idType) {
			this.idType = idType;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}

	

		
	
	}
}
