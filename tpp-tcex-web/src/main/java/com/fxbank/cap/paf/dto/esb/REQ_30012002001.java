package com.fxbank.cap.paf.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30012002001 
* @Description: 统一签约
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:31:28 
*  
*/
public class REQ_30012002001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30012002001(){
		super.txDesc = "无卡协议签约";
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

		@JSONField(name = "AGREEMENT_TYPE")
		private String agreementType;	//协议类型

		@JSONField(name = "PRO_BELONG_BRANCH")
		private String proBelongBranch;	//协议所属机构
		
		@JSONField(name = "PRO_INITR_BRANCH")
		private String proInitrBranch;		//协议发起机构
		
		@JSONField(name = "PRO_RCV_BRANCH")
		private String proRcvBranch;		//协议接收机构
		
		@JSONField(name = "ACCT_TYPE")
		private String accType;		//账户类型
		
		@JSONField(name = "ACCT_NO")
		private String accNo;		//账号
		
		@JSONField(name = "ACCT_NAME")
		private String accName;		//账户名称
		
		@JSONField(name = "DOCUMENT_TYPE")
		private String documentType;		//证件类型
		
		@JSONField(name = "DOCUMENT_ID")
		private String documentId;		//证件号码
		
		@JSONField(name = "OBLIGATE_PHONE")
		private String obligatePhone;		//预留手机号
		
		@JSONField(name = "SDER_ACCT_ID")
		private String sderAcctId;		//发起方账户

		public String getAgreementType() {
			return agreementType;
		}

		public void setAgreementType(String agreementType) {
			this.agreementType = agreementType;
		}

		public String getProBelongBranch() {
			return proBelongBranch;
		}

		public void setProBelongBranch(String proBelongBranch) {
			this.proBelongBranch = proBelongBranch;
		}

		public String getProInitrBranch() {
			return proInitrBranch;
		}

		public void setProInitrBranch(String proInitrBranch) {
			this.proInitrBranch = proInitrBranch;
		}

		public String getProRcvBranch() {
			return proRcvBranch;
		}

		public void setProRcvBranch(String proRcvBranch) {
			this.proRcvBranch = proRcvBranch;
		}

		public String getAccType() {
			return accType;
		}

		public void setAccType(String accType) {
			this.accType = accType;
		}

		public String getAccNo() {
			return accNo;
		}

		public void setAccNo(String accNo) {
			this.accNo = accNo;
		}

		public String getAccName() {
			return accName;
		}

		public void setAccName(String accName) {
			this.accName = accName;
		}

		public String getDocumentType() {
			return documentType;
		}

		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}

		public String getDocumentId() {
			return documentId;
		}

		public void setDocumentId(String documentId) {
			this.documentId = documentId;
		}

		public String getObligatePhone() {
			return obligatePhone;
		}

		public void setObligatePhone(String obligatePhone) {
			this.obligatePhone = obligatePhone;
		}

		public String getSderAcctId() {
			return sderAcctId;
		}

		public void setSderAcctId(String sderAcctId) {
			this.sderAcctId = sderAcctId;
		}
	
	}
}
