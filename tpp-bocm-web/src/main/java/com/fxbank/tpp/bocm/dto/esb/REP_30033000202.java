/**   
* @Title: REP_30033000202.java 
* @Package com.fxbank.tpp.bocm.dto.esb.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月24日 上午8:26:05 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30033000202 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年4月24日 上午8:26:05 
*  
*/
public class REP_30033000202 extends REP_BASE {
	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();
	
	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();
	
	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

	public REP_BODY getRepBody() {
		return repBody;
	}


	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public class REP_BODY {

		@JSONField(name = "TRAN_ID")
		private String tranId;//卡号		
		@JSONField(name = "CARD_NO")
		private String cardNo;//55域内容
		@JSONField(name = "IC_CARD_DATA_FIELD")
		private String icCardDataField;//IC卡序列号
		public String getTranId() {
			return tranId;
		}
		public void setTranId(String tranId) {
			this.tranId = tranId;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getIcCardDataField() {
			return icCardDataField;
		}
		public void setIcCardDataField(String icCardDataField) {
			this.icCardDataField = icCardDataField;
		}
		

		
	}
}
