package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30061800501 
* @Description: 本行柜面第三方对账
* @author YePuLiang
* @date 2019年5月6日 下午5:37:44 
*  
*/
public class REP_30063001304  extends REP_BASE {

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
		
		@JSONField(name = "A_T")
		private List<Chk>  chkList;

		public List<Chk> getChkList() {
			return chkList;
		}

		public void setChkList(List<Chk> chkList) {
			this.chkList = chkList;
		}

		
		
	}
	
	public static class Chk implements Serializable{

		private static final long serialVersionUID = -2346508842863649894L;
		@JSONField(name = "ACCTG_DT_T")
		private String txDate="";
		@JSONField(name = "STMT_DT_T2")
	    private String chkDate;
		@JSONField(name = "CNST_TM_T")
		private String chkTime;
		@JSONField(name = "CNST_BR_T")
		private String chkBranch="";
		@JSONField(name = "CNST_TLR_T")
	    private String chkTel;
		@JSONField(name = "STMT_STS_T9")
		private String chkState;
		public String getTxDate() {
			return txDate;
		}
		public void setTxDate(String txDate) {
			this.txDate = txDate;
		}
		public String getChkDate() {
			return chkDate;
		}
		public void setChkDate(String chkDate) {
			this.chkDate = chkDate;
		}
		public String getChkTime() {
			return chkTime;
		}
		public void setChkTime(String chkTime) {
			this.chkTime = chkTime;
		}
		public String getChkBranch() {
			return chkBranch;
		}
		public void setChkBranch(String chkBranch) {
			this.chkBranch = chkBranch;
		}
		public String getChkTel() {
			return chkTel;
		}
		public void setChkTel(String chkTel) {
			this.chkTel = chkTel;
		}
		public String getChkState() {
			return chkState;
		}
		public void setChkState(String chkState) {
			this.chkState = chkState;
		}
		
		
		
	}
}
