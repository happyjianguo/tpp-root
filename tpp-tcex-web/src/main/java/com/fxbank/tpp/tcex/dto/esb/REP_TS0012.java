package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_TS0012 
* @Description: 模拟商行通兑村镇 
* @author Duzhenduo
* @date 2019年1月31日 上午10:00:41 
*  
*/
public class REP_TS0012 extends REP_BASE {

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
		@JSONField(name = "BRNO")
		private String brno;	//村镇记账机构
		
		@JSONField(name = "TOWN_DATE")
		private String townDate;	//村镇日期
		
		@JSONField(name = "TOWN_TRACENO")
		private String townTraceno;	//村镇流水
		
		@JSONField(name = "BACK_TAL")
		private String backTal; //取款后余额

		public String getBrno() {
			return brno;
		}

		public void setBrno(String brno) {
			this.brno = brno;
		}

		public String getTownDate() {
			return townDate;
		}

		public void setTownDate(String townDate) {
			this.townDate = townDate;
		}

		public String getTownTraceno() {
			return townTraceno;
		}

		public void setTownTraceno(String townTraceno) {
			this.townTraceno = townTraceno;
		}

		public String getBackTal() {
			return backTal;
		}

		public void setBackTal(String backTal) {
			this.backTal = backTal;
		}
		

	}
}
