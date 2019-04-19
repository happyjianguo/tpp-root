package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30033000203 extends ESB_BASE {

	private static final long serialVersionUID = -4290237187326200457L;

	public ESB_REQ_30033000203 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300330002", "02");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 7800773932395648729L;
		@JSONField(name = "CARD_NO")
		private String cardNo;//卡号
		@JSONField(name = "SCD_TRK")
		private String scdTrk;//二磁道
		@JSONField(name = "CUR_DATE")
		private String curDate;//当前日期
		
	public String  getCardNo(){
			return cardNo;
		}
		public void setCardNo(String cardNo){
			this.cardNo = cardNo;
		}
		public String  getScdTrk(){
			return scdTrk;
		}
		public void setScdTrk(String scdTrk){
			this.scdTrk = scdTrk;
		}
		public String  getCurDate(){
			return curDate;
		}
		public void setCurDate(String curDate){
			this.curDate = curDate;
		}
		
	}
	
	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public ESB_REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public ESB_REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

}
