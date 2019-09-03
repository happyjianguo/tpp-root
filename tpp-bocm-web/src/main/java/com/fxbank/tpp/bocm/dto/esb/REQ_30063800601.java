package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063800601 
* @Description: 代理交行业务流水信息查询
* @author YePuLiang
* @date 2019年7月9日 上午8:26:19 
*  
*/
public class REQ_30063800601 extends REQ_BASE{
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063800601(){
		super.txDesc = "代理交行业务流水信息查询";
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
		//交易金额
		@JSONField(name = "TRNS_AMT_T3")
		private String trnsAmtT3;	
		//平台起始日期
		@JSONField(name = "PLTFRM_BGN_DT_T1")
		private String pltfrmBgnDtT1;
		//平台结束日期
		@JSONField(name = "PLTFRM_END_DT_T")
		private String pltfrmEndDtT;
		//平台起始流水
		@JSONField(name = "PLTFRM_FRM_SEQ_T")
		private String pltfrmFrmSeqT;
		//平台结束流水
		@JSONField(name = "PLTFRM_END_SEQ_T1")
		private String pltfrmEndSeqT1;
		//行内处理状态
		@JSONField(name = "INTBNK_CNST_STS_T6")
		private String intbnkCnstStsT6;
		public String getTrnsAmtT3() {
			return trnsAmtT3;
		}
		public void setTrnsAmtT3(String trnsAmtT3) {
			this.trnsAmtT3 = trnsAmtT3;
		}
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
		public String getPltfrmFrmSeqT() {
			return pltfrmFrmSeqT;
		}
		public void setPltfrmFrmSeqT(String pltfrmFrmSeqT) {
			this.pltfrmFrmSeqT = pltfrmFrmSeqT;
		}
		public String getPltfrmEndSeqT1() {
			return pltfrmEndSeqT1;
		}
		public void setPltfrmEndSeqT1(String pltfrmEndSeqT1) {
			this.pltfrmEndSeqT1 = pltfrmEndSeqT1;
		}
		public String getIntbnkCnstStsT6() {
			return intbnkCnstStsT6;
		}
		public void setIntbnkCnstStsT6(String intbnkCnstStsT6) {
			this.intbnkCnstStsT6 = intbnkCnstStsT6;
		}

	}


}
