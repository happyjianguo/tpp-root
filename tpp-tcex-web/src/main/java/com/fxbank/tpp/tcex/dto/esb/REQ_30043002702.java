package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30012002002 
* @Description: 村镇柜面通来账交易信息查询
* @author Duzhenduo
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_30043002702 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30043002702(){
		super.txDesc = "村镇柜面通来账交易信息查询";
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

		@JSONField(name = "START_DATE")
		private String startDate;	//起始日期

		@JSONField(name = "END_DATE")
		private String endDate;	//终止日期
		
		@JSONField(name = "MIN_AMT")
		private String minAmt;		//最小金额
		
		@JSONField(name = "MAX_AMT")
		private String maxAmt;		//最大金额
		
		@JSONField(name = "VILLAGE_BRNACH_FLAG")
		private String brnoFlag;		//交易机构


		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getMinAmt() {
			return minAmt;
		}

		public void setMinAmt(String minAmt) {
			this.minAmt = minAmt;
		}

		public String getMaxAmt() {
			return maxAmt;
		}

		public void setMaxAmt(String maxAmt) {
			this.maxAmt = maxAmt;
		}

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}


	}
}
