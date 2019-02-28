package com.fxbank.tpp.esb.model.tcex;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

/** 
* @ClassName: ESB_REP_TSK001 
* @Description: 商行通兑快捷查询账户信息 
* @author Duzhenduo
* @date 2019年1月31日 下午3:56:20 
*  
*/
public class ESB_REP_TSK001 extends ESB_BASE {

	private static final long serialVersionUID = -2496078829437573719L;

	@Deprecated
	public ESB_REP_TSK001() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_TSK001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
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

		@JSONField(name = "PAYER_ACNO")
		private String payerAcno;	//账/卡号
		
		@JSONField(name = "PAYER_NAME")
		private String payerName;	//账户姓名
		
		@JSONField(name = "ACNO_SEQ")
		private String acnoSeq;	//账户序号
		
		@JSONField(name = "BAL")
		private String bal;	//账户余额

		public String getPayerAcno() {
			return payerAcno;
		}

		public void setPayerAcno(String payerAcno) {
			this.payerAcno = payerAcno;
		}

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getAcnoSeq() {
			return acnoSeq;
		}

		public void setAcnoSeq(String acnoSeq) {
			this.acnoSeq = acnoSeq;
		}

		public String getBal() {
			return bal;
		}

		public void setBal(String bal) {
			this.bal = bal;
		}


	}
}
