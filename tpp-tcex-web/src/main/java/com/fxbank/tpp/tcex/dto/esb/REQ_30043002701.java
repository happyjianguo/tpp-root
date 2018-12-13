package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30012002001 
* @Description: 商行通存村镇业务
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_30043002701 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30043002701(){
		super.txDesc = "商行通存村镇业务";
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
		private String begDate;	//起始日期

		@JSONField(name = "END_DATE")
		private String endDate;	//终止日期
		
		@JSONField(name = "MIN_AMT")
		private String minAmt;		//最小金额
		
		@JSONField(name = "MAX_AMT")
		private String maxAmt;		//最大金额
		
		@JSONField(name = "TRAN_BRANCH")
		private String txBrno;		//交易机构

		@JSONField(name = "DEP_DRA_IND")
		private String depDraInd;		//通存通兑标志

		public String getDepDraInd() {
			return depDraInd;
		}

		public void setDepDraInd(String depDraInd) {
			this.depDraInd = depDraInd;
		}

		public String getBegDate() {
			return begDate;
		}

		public void setBegDate(String begDate) {
			this.begDate = begDate;
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

		public String getTxBrno() {
			return txBrno;
		}

		public void setTxBrno(String txBrno) {
			this.txBrno = txBrno;
		}
		

		
	
	}
}
