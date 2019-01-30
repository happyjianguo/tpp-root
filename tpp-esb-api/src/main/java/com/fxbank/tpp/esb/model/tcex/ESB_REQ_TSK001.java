package com.fxbank.tpp.esb.model.tcex;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;


/** 
* @ClassName: TSK01 
* @Description: 商行通兑快捷查询村镇账户信息
* @author Duzhenduo
* @date 2018年12月11日 下午2:04:30 
*  
*/
public class ESB_REQ_TSK001 extends ESB_BASE {
	
	private static final long serialVersionUID = -813981987125918717L;

	public ESB_REQ_TSK001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("TSK001", "");
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

		@JSONField(name = "PAYER_ACNO")
		private String payerAcno;	//账/卡号
		
		@JSONField(name = "BRNO_FLAG")
		private String brnoFlag;	//村镇机构

		public String getPayerAcno() {
			return payerAcno;
		}

		public void setPayerAcno(String payerAcno) {
			this.payerAcno = payerAcno;
		}

		public String getBrnoFlag() {
			return brnoFlag;
		}

		public void setBrnoFlag(String brnoFlag) {
			this.brnoFlag = brnoFlag;
		}
		
		
	
	}
}
