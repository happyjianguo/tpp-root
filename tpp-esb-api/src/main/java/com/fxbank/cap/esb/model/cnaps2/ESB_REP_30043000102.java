package com.fxbank.cap.esb.model.cnaps2;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30043000102 extends ESB_BASE {

	private static final long serialVersionUID = 3907460170639316507L;

	@Deprecated
	public ESB_REP_30043000102() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30043000102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -1495817318336514407L;

		@JSONField(name = "TRAN_STATUS")
		private String tranStatus;//交易状态
		@JSONField(name = "HOST_DATE")
		private String hostDate;//核心日期
		@JSONField(name = "HOST_TRACE_NO")
		private String hostTraceNo;//核心流水

		public String getHostDate() {
			return hostDate;
		}

		public void setHostDate(String hostDate) {
			this.hostDate = hostDate;
		}

		public String getHostTraceNo() {
			return hostTraceNo;
		}

		public void setHostTraceNo(String hostTraceNo) {
			this.hostTraceNo = hostTraceNo;
		}

		public String getTranStatus() {
			return tranStatus;
		}

		public void setTranStatus(String tranStatus) {
			this.tranStatus = tranStatus;
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
