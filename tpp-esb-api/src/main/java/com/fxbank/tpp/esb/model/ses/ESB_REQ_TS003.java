package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;


/** 
* @ClassName: REQ_TS002 
* @Description: 商行通兑村镇 
* @author Duzhenduo
* @date 2018年12月11日 下午2:04:30 
*  
*/
public class ESB_REQ_TS003 extends ESB_BASE {
	
	private static final long serialVersionUID = -813981987125918717L;

	public ESB_REQ_TS003(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("TS003", "");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

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

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY implements Serializable{

		private static final long serialVersionUID = -8415585719972566411L;

		@JSONField(name = "DCFLAG")
		private String dcFlag;	//通存通兑标志

		@JSONField(name = "PLAT_DATE")
		private String platDate;	//平台日期
		
		@JSONField(name = "PLAT_TRACENO")
		private String platTraceno;		//平台流水

		public String getDcFlag() {
			return dcFlag;
		}

		public void setDcFlag(String dcFlag) {
			this.dcFlag = dcFlag;
		}

		public String getPlatDate() {
			return platDate;
		}

		public void setPlatDate(String platDate) {
			this.platDate = platDate;
		}

		public String getPlatTraceno() {
			return platTraceno;
		}

		public void setPlatTraceno(String platTraceno) {
			this.platTraceno = platTraceno;
		}
		
	
	}
}
