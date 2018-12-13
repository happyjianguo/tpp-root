package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

public class REP_30043002701 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
    private REP_BODY repBody = new REP_BODY();

    public class REP_BODY{
		@JSONField(name = "GC_TRAN_INFO_ARRAY")
        private List<TMSG> arrayMsg;//公积金账户信息数组
		
		public List<TMSG> getArrayMsg() {
			return arrayMsg;
		}
		public void setArrayMsg(List<TMSG> arrayMsg) {
			this.arrayMsg = arrayMsg;
		}
		
		
    }

    public class TMSG{
		@JSONField(name = "SYSTEM_DATE")
        private String platDate;//平台日期
        @JSONField(name = "SYSTEM_REFERENCE")
        private String platTraceno;//平台流水
        @JSONField(name = "DEP_DRA_IND")
        private String dcFlag;//通存通兑标志
        @JSONField(name = "CHANNEL_TYPE")
        private String chnl;//交易渠道
        @JSONField(name = "VILLAGE_BRNACH_ID")
        private String townBrno;//村镇记账机构
        @JSONField(name = "HOST_SEQ_NO")
        private String hostTraceno;//主机流水号
        @JSONField(name = "HOST_DT")
        private String hostDate;//主机日期
        @JSONField(name = "CBS_TRAN_STS")
        private String ourState;//核心记账状态
        @JSONField(name = "VILLAGE_TRAN_STS")
        private String townState;//村镇记账状态
        @JSONField(name = "COLLATE_STS")
        private String chkState;//对账状态
        @JSONField(name = "TRAN_AMT")
        private String txAmt;//交易金额
        @JSONField(name = "MFFLG")
        private String txInd;//现转标志
        @JSONField(name = "PAYEE_ACCT_NO")
        private String payeeAcno;//收款人账号
        @JSONField(name = "PAYEE_ACCT_NAME")
        private String payeeName;//收款人户名
        @JSONField(name = "PAYER_ACCT_NO")
        private String payerAcno;//付款人账号
        @JSONField(name = "PAYER_NAME")
        private String payerName;//付款人户名
        @JSONField(name = "OFFICER_ID")
        private String txTel;//交易柜员
        @JSONField(name = "APPR_USER_ID")
        private String chkTel;//复核员
        @JSONField(name = "AUTH_USER_ID")
        private String authTel;//授权员
        @JSONField(name = "PRINT_COUNT")
        private String print;//打印次数
        @JSONField(name = "NARRATIVE")
        private String info;//摘要
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
		public String getDcFlag() {
			return dcFlag;
		}
		public void setDcFlag(String dcFlag) {
			this.dcFlag = dcFlag;
		}
		public String getChnl() {
			return chnl;
		}
		public void setChnl(String chnl) {
			this.chnl = chnl;
		}
		public String getTownBrno() {
			return townBrno;
		}
		public void setTownBrno(String townBrno) {
			this.townBrno = townBrno;
		}
		public String getHostTraceno() {
			return hostTraceno;
		}
		public void setHostTraceno(String hostTraceno) {
			this.hostTraceno = hostTraceno;
		}
		public String getHostDate() {
			return hostDate;
		}
		public void setHostDate(String hostDate) {
			this.hostDate = hostDate;
		}
		public String getOurState() {
			return ourState;
		}
		public void setOurState(String ourState) {
			this.ourState = ourState;
		}
		public String getTownState() {
			return townState;
		}
		public void setTownState(String townState) {
			this.townState = townState;
		}
		public String getChkState() {
			return chkState;
		}
		public void setChkState(String chkState) {
			this.chkState = chkState;
		}
		public String getTxAmt() {
			return txAmt;
		}
		public void setTxAmt(String txAmt) {
			this.txAmt = txAmt;
		}
		public String getTxInd() {
			return txInd;
		}
		public void setTxInd(String txInd) {
			this.txInd = txInd;
		}
		public String getPayeeAcno() {
			return payeeAcno;
		}
		public void setPayeeAcno(String payeeAcno) {
			this.payeeAcno = payeeAcno;
		}
		public String getPayeeName() {
			return payeeName;
		}
		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}
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
		public String getTxTel() {
			return txTel;
		}
		public void setTxTel(String txTel) {
			this.txTel = txTel;
		}
		public String getChkTel() {
			return chkTel;
		}
		public void setChkTel(String chkTel) {
			this.chkTel = chkTel;
		}
		public String getAuthTel() {
			return authTel;
		}
		public void setAuthTel(String authTel) {
			this.authTel = authTel;
		}
		public String getPrint() {
			return print;
		}
		public void setPrint(String print) {
			this.print = print;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
      
    }


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


}