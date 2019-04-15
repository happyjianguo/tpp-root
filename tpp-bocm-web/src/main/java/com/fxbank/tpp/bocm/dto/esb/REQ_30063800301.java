package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063800301
* @Description: 交行卡账户余额查询
* @author zhouyongwei zyw_unix@126.com
* @date 2018年4月5日 下午11:32:26 
*  
*/
public class REQ_30063800301 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063800301(){
		super.txDesc = "交行卡账户余额查询";
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

		@JSONField(name = "CCY_T")
		private String curNo;	//币种
		
		@JSONField(name = "NM_T")
		private String acctName;	//名称
		
		@JSONField(name = "CARD_NO_T3")
		private String cardNo;	//卡号
		
		@JSONField(name = "ACCT_NO_TP_T")
		private String acctType;	//账号类型

		@JSONField(name = "PWD_T")
		private String pwd;	//密码


        /**
         * @return the curNo
         */
        public String getCurNo() {
            return curNo;
        }

        /**
         * @return the pwd
         */
        public String getPwd() {
            return pwd;
        }

        /**
         * @param pwd the pwd to set
         */
        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        /**
         * @return the acctType
         */
        public String getAcctType() {
            return acctType;
        }

        /**
         * @param acctType the acctType to set
         */
        public void setAcctType(String acctType) {
            this.acctType = acctType;
        }

        /**
         * @return the cardNo
         */
        public String getCardNo() {
            return cardNo;
        }

        /**
         * @param cardNo the cardNo to set
         */
        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        /**
         * @return the acctName
         */
        public String getAcctName() {
            return acctName;
        }

        /**
         * @param acctName the acctName to set
         */
        public void setAcctName(String acctName) {
            this.acctName = acctName;
        }

        /**
         * @param curNo the curNo to set
         */
        public void setCurNo(String curNo) {
            this.curNo = curNo;
        }
	}
}