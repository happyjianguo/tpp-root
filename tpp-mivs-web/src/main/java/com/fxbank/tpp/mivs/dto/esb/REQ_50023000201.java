package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 手机号码联网核查申请
 * @Author: 周勇沩
 * @Date: 2019-04-23 21:27:18
 */
public class REQ_50023000201 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_50023000201(){
		super.txDesc = "手机号码联网核查";
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

		@JSONField(name = "MOBILE_PHONE")
		private String mobNb;    //手机号码
				
		@JSONField(name = "NAME")
		private String nm;		//姓名
		
		@JSONField(name = "DOCUMENT_TYPE")
		private String idTp;		//证件类型
		
		@JSONField(name = "DOCUMENT_ID")
		private String id;		//证件号码
		
		@JSONField(name = "SOCIAL_CODE")
		private String uniSocCdtCd;		//统一社会信用代码
		
		@JSONField(name = "BIZ_REG_NO")
		private String bizRegNb;		//工商注册号
		
		@JSONField(name = "OPRTR_NAME")
		private String opNm;		//操作员姓名

		@JSONField(name = "RESERVE_FIELD1")
		private String remarks1;		//备用字段1
		@JSONField(name = "RESERVE_FIELD2")
		private String remarks2;		//备用字段2
		@JSONField(name = "RESERVE_FIELD3")
		private String remarks3;		//备用字段3
		public String getRemarks1() {
			return remarks1;
		}
		public void setRemarks1(String remarks1) {
			this.remarks1 = remarks1;
		}
		public String getRemarks2() {
			return remarks2;
		}
		public void setRemarks2(String remarks2) {
			this.remarks2 = remarks2;
		}
		public String getRemarks3() {
			return remarks3;
		}
		public void setRemarks3(String remarks3) {
			this.remarks3 = remarks3;
		}

		public String getMobNb() {
			return mobNb;
		}

		public void setMobNb(String mobNb) {
			this.mobNb = mobNb;
		}

		public String getNm() {
			return nm;
		}

		public void setNm(String nm) {
			this.nm = nm;
		}

		public String getIdTp() {
			return idTp;
		}

		public void setIdTp(String idTp) {
			this.idTp = idTp;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUniSocCdtCd() {
			return uniSocCdtCd;
		}

		public void setUniSocCdtCd(String uniSocCdtCd) {
			this.uniSocCdtCd = uniSocCdtCd;
		}

		public String getBizRegNb() {
			return bizRegNb;
		}

		public void setBizRegNb(String bizRegNb) {
			this.bizRegNb = bizRegNb;
		}

		public String getOpNm() {
			return opNm;
		}

		public void setOpNm(String opNm) {
			this.opNm = opNm;
		}
		
		
	}
}
