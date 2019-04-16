package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30061800201 extends ESB_BASE {


	//private static final long serialVersionUID = -8845727634828642523L;

	@Deprecated
	public ESB_REP_30061800201() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30061800201(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		//private static final long serialVersionUID = 4285318811572896212L;
		@JSONField(name = "FEE_T3")
		private String feeT3;//手续费
		public String  getFeeT3(){
			return feeT3;
		}
		public void setFeeT3(String feeT3){
			this.feeT3 = feeT3;
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
