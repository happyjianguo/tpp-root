package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_TR0012 
* @Description: 模拟村镇通兑商行
* @author Duzhenduo
* @date 2019年1月31日 上午9:57:52 
*  
*/
public class REP_TR0012 extends REP_BASE {

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
		@JSONField(name = "PLAT_DATE")
		private String platDate;//平台日期

		@JSONField(name = "PLAT_TRACENO")
		private String platTraceno;//平台流水

		@JSONField(name = "STS")
		private String sts;//处理状态

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

		public String getSts() {
			return sts;
		}

		public void setSts(String sts) {
			this.sts = sts;
		}

		
	}
}
