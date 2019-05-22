/**   
* @Title: REQ_30033000202.java 
* @Package com.fxbank.tpp.bocm.dto.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月24日 上午8:22:50 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30033000202 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年4月24日 上午8:22:50 
*  
*/
public class REQ_30033000203 extends REQ_BASE {
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30033000203(){
		super.txDesc = "磁条卡验证";
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
		@JSONField(name = "CARD_NO")
		private String cardNo;//卡号
		@JSONField(name = "SCD_TRK")
		private String scdTrk;//余额
		@JSONField(name = "CUR_DATE")
		private String curDate;//IC卡序列号
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getScdTrk() {
			return scdTrk;
		}
		public void setScdTrk(String scdTrk) {
			this.scdTrk = scdTrk;
		}
		public String getCurDate() {
			return curDate;
		}
		public void setCurDate(String curDate) {
			this.curDate = curDate;
		}
		
		
	}
}
