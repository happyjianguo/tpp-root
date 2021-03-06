package com.fxbank.tpp.esb.model.tcex;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

/** 
* @ClassName: ESB_REP_TS0013 
* @Description: 商行通存记账确认
* @author Duzhenduo
* @date 2019年1月31日 下午3:55:37 
*  
*/
public class ESB_REP_TS0013 extends ESB_BASE {

	private static final long serialVersionUID = -2496078829437573719L;

	@Deprecated
	public ESB_REP_TS0013() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_TS0013(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;


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

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}


	public class REP_BODY implements Serializable{
		private static final long serialVersionUID = -4029732750907650659L;

		@JSONField(name = "STS")
		private String sts;	//处理状态
		
		@JSONField(name = "TOWN_DATE")
		private String townDate;	//村镇日期
		
		@JSONField(name = "TOWN_TRACENO")
		private String townTraceNo;	//村镇流水

		public String getSts() {
			return sts;
		}

		public void setSts(String sts) {
			this.sts = sts;
		}

		public String getTownDate() {
			return townDate;
		}

		public void setTownDate(String townDate) {
			this.townDate = townDate;
		}

		public String getTownTraceNo() {
			return townTraceNo;
		}

		public void setTownTraceNo(String townTraceNo) {
			this.townTraceNo = townTraceNo;
		}

	}
}
