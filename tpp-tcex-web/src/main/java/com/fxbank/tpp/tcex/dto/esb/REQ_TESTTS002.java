package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_TR003
* @Description: 村镇通存记账确认
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_TOWN002 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_TOWN002(){
		super.txDesc = "村镇通存记账确认";
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

		@JSONField(name = "BRNO")
		private String brno;	//村镇记账机构

		@JSONField(name = "TOWN_DATE")
		private String townDate;	//村镇日期
		
		@JSONField(name = "TOWN_TRACENO")
		private String townTraceno;		//村镇流水
		
		@JSONField(name = "DCFLAG")
		private String dcFlag;		//通存通兑标志

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

		public String getTownTraceno() {
			return townTraceno;
		}

		public void setTownTraceno(String townTraceno) {
			this.townTraceno = townTraceno;
		}

		public String getDcFlag() {
			return dcFlag;
		}

		public void setDcFlag(String dcFlag) {
			this.dcFlag = dcFlag;
		}
		
	
	}
}
