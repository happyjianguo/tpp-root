/**   
* @Title: REQ_30063001304.java 
* @Package com.fxbank.tpp.bocm.dto.esb 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年7月12日 上午8:59:57 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001304 
* @Description: 柜面通对账查询
* @author YePuLiang
* @date 2019年7月12日 上午8:59:57 
*  
*/
public class REQ_30063001304 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063001304(){
		super.txDesc = "交行柜面通三方对账";
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
		//平台起始日期
		@JSONField(name = "PLTFRM_BGN_DT_T1")
		private String pltfrmBgnDtT1;
		
		//平台结束日期
		@JSONField(name = "PLTFRM_END_DT_T")
		private String pltfrmEndDtT;
		
		//对账状态
		@JSONField(name = "STMT_STS_T")
		private String stmtStsT;

		public String getPltfrmBgnDtT1() {
			return pltfrmBgnDtT1;
		}

		public void setPltfrmBgnDtT1(String pltfrmBgnDtT1) {
			this.pltfrmBgnDtT1 = pltfrmBgnDtT1;
		}

		public String getPltfrmEndDtT() {
			return pltfrmEndDtT;
		}

		public void setPltfrmEndDtT(String pltfrmEndDtT) {
			this.pltfrmEndDtT = pltfrmEndDtT;
		}

		public String getStmtStsT() {
			return stmtStsT;
		}

		public void setStmtStsT(String stmtStsT) {
			this.stmtStsT = stmtStsT;
		}

		
		
	}
}
