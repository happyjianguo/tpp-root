package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_TR002
* @Description: 村镇通存商行业务
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_TR001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_TR001(){
		super.txDesc = "村镇通存商行业务";
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

		@JSONField(name = "TX_AMT")
		private String txAmt;	//金额

		@JSONField(name = "PAYEE_NAME")
		private String payeeName;	//收款人名称
		
		@JSONField(name = "PAYEE_ACC")
		private String payeeAcc;	//收款人账号
		
		@JSONField(name = "INFO")
		private String info;	//摘要
		
		@JSONField(name = "BRNO")
		private String brno;	//村镇记账机构
		
		@JSONField(name = "TOWN_DATE")
		private String townDate;	//村镇日期
		
		@JSONField(name = "TOWN_TRACENO")
		private String townTraceNo;	//村镇流水

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
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

		public String getBrno() {
			return brno;
		}

		public void setBrno(String brno) {
			this.brno = brno;
		}

		public String getTownDate() {
			return townDate;
		}

		public void setTownDate(String townDate) {
			this.townDate = townDate;
		}

		public String getTownTraceNo() {
			return townTraceNo;
		}

		public void setTownTraceNo(String townTraceNo) {
			this.townTraceNo = townTraceNo;
		}
		
	}
}
