package com.fxbank.cap.esb.model.cnaps2;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30041000801 extends ESB_BASE {

	private static final long serialVersionUID = -8745129357064837575L;

	@Deprecated
	public ESB_REP_30041000801() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30041000801(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = 7660385764696694495L;
		@JSONField(name = "PLTFRM_DT_T2")
		private String pltfrmDtT2;//平台日期
		@JSONField(name = "PLTFRM_SEQ_T3")
		private String pltfrmSeqT3;//平台流水
		@JSONField(name = "PD_TRNS_SRL_NO_T")
		private String pdTrnsSrlNoT;//支付交易序号
		public String  getPltfrmDtT2(){
			return pltfrmDtT2;
		}
		public String getPdTrnsSrlNoT() {
			return pdTrnsSrlNoT;
		}
		public void setPdTrnsSrlNoT(String pdTrnsSrlNoT) {
			this.pdTrnsSrlNoT = pdTrnsSrlNoT;
		}
		public void setPltfrmDtT2(String pltfrmDtT2){
			this.pltfrmDtT2 = pltfrmDtT2;
		}
		public String  getPltfrmSeqT3(){
			return pltfrmSeqT3;
		}
		public void setPltfrmSeqT3(String pltfrmSeqT3){
			this.pltfrmSeqT3 = pltfrmSeqT3;
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
