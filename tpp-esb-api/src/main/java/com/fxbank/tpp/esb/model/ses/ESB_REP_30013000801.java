package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

/** 
* @ClassName: ESB_REP_30013000801 
* @Description: 村镇柜面查询村镇在商行头寸
* @author Duzhenduo
* @date 2019年1月31日 下午3:50:02 
*  
*/
public class ESB_REP_30013000801 extends ESB_BASE {


	private static final long serialVersionUID = 9021844981692559901L;

	@Deprecated
	public ESB_REP_30013000801() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30013000801(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8826190325118397917L;
		@JSONField(name = "BALANCE")
		private String balance;//余额
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
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
