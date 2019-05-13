package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @Description: 手机号码联网核查应答
 * @Author: 周勇沩
 * @Date: 2019-04-23 21:27:51
 */
public class REP_50023000201 extends REP_BASE {

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
		 @JSONField(name = "MobNb")
	     private String mobNb;//手机号码
		 
		 @JSONField(name = "Rslt")
	     private String rslt;//手机号码核查结果
		 
		 @JSONField(name = "MobCrr")
	     private String mobCrr;//手机运营商
		 
		 @JSONField(name = "LocMobNb")
	     private String locMobNb;//手机号归属地代码
		 
		 @JSONField(name = "LocNmMobNb")
	     private String locNmMobNb;//手机号归属地名称
		 
		 @JSONField(name = "CdTp")
	     private String cdTp;//客户类型
		 
		 @JSONField(name = "Sts")
	     private String sts;//手机号码状态

		public String getMobNb() {
			return mobNb;
		}

		public void setMobNb(String mobNb) {
			this.mobNb = mobNb;
		}

		public String getRslt() {
			return rslt;
		}

		public void setRslt(String rslt) {
			this.rslt = rslt;
		}

		public String getMobCrr() {
			return mobCrr;
		}

		public void setMobCrr(String mobCrr) {
			this.mobCrr = mobCrr;
		}

		public String getLocMobNb() {
			return locMobNb;
		}

		public void setLocMobNb(String locMobNb) {
			this.locMobNb = locMobNb;
		}

		public String getLocNmMobNb() {
			return locNmMobNb;
		}

		public void setLocNmMobNb(String locNmMobNb) {
			this.locNmMobNb = locNmMobNb;
		}

		public String getCdTp() {
			return cdTp;
		}

		public void setCdTp(String cdTp) {
			this.cdTp = cdTp;
		}

		public String getSts() {
			return sts;
		}

		public void setSts(String sts) {
			this.sts = sts;
		}




	}
}
