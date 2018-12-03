package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30013002801 extends ESB_BASE {

	private static final long serialVersionUID = 6555976099282261462L;

	@Deprecated
	public ESB_REP_30013002801() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013002801(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -4341044545849150156L;
		@JSONField(name = "MATURITY_DATE")
		private String maturityDate;//到期日
		@JSONField(name = "NOTICE_DATE")
		private String noticeDate;//通知日期
		public String  getMaturityDate(){
			return maturityDate;
		}
		public void setMaturityDate(String maturityDate){
			this.maturityDate = maturityDate;
		}
		public String  getNoticeDate(){
			return noticeDate;
		}
		public void setNoticeDate(String noticeDate){
			this.noticeDate = noticeDate;
		}
		
	}
	
	

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public ESB_REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(ESB_REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public ESB_REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(ESB_REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

}
