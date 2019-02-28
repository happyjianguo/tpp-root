package com.fxbank.tpp.tcex.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/** 
* @ClassName: REP_30043002701 
* @Description: 村镇业务交易信息查询
* @author Duzhenduo
* @date 2019年1月31日 上午9:53:50 
*  
*/
public class REP_30043002701 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
    private REP_BODY repBody = new REP_BODY();

    public class REP_BODY{
		@JSONField(name = "GC_TRAN_INFO_ARRAY")
        private List<TMSGS> arrayMsg;//公积金账户信息数组
		
		public List<TMSGS> getArrayMsg() {
			return arrayMsg;
		}
		public void setArrayMsg(List<TMSGS> arrayMsg) {
			this.arrayMsg = arrayMsg;
		}
		
		
    }

    public static class TMSGS{
		@JSONField(name = "SYSTEM_DATE")
        private String systemDate;//平台日期
		
        @JSONField(name = "SYSTEM_REFERENCE")
        private String systemReference;//平台流水
        
        @JSONField(name = "DEP_DRA_IND")
        private String depDraInd;//通存通兑标志
        
        @JSONField(name = "CHANNEL_TYPE")
        private String channelType;//交易渠道
        
        @JSONField(name = "VILLAGE_BRNACH_ID")
        private String villageBrnachId;//村镇记账机构
        
        @JSONField(name = "HOST_SEQ_NO")
        private String hostSeqNo;//主机流水号
        
        @JSONField(name = "HOST_DT")
        private String hostDt;//主机日期
        
        @JSONField(name = "CBS_TRAN_STS")
        private String cbsTranSts;//核心记账状态
        
        @JSONField(name = "VILLAGE_TRAN_STS")
        private String villageTranSts;//村镇记账状态
        
        @JSONField(name = "COLLATE_STS")
        private String collateSts;//对账状态
        
        @JSONField(name = "TRAN_AMT")
        private String tranAmt;//交易金额
        
        @JSONField(name = "MFFLG")
        private String mfflg;//现转标志
        
        @JSONField(name = "PAYEE_ACCT_NO")
        private String payeeAcctNo;//收款人账号
        
        @JSONField(name = "PAYEE_ACCT_NAME")
        private String payeeAcctName;//收款人户名
        
        @JSONField(name = "PAYER_ACCT_NO")
        private String payerAcctNo;//付款人账号
        
        @JSONField(name = "PAYER_NAME")
        private String payerName;//付款人户名
        
        @JSONField(name = "OFFICER_ID")
        private String officerId;//交易柜员
        
        @JSONField(name = "APPR_USER_ID")
        private String apprUserId;//复核员
        
        @JSONField(name = "AUTH_USER_ID")
        private String authUserId;//授权员
        
        @JSONField(name = "PRINT_COUNT")
        private String printCount;//打印次数
        
        @JSONField(name = "NARRATIVE")
        private String narrative;//摘要

		public String getSystemDate() {
			return systemDate;
		}

		public void setSystemDate(String systemDate) {
			this.systemDate = systemDate;
		}

		public String getSystemReference() {
			return systemReference;
		}

		public void setSystemReference(String systemReference) {
			this.systemReference = systemReference;
		}

		public String getDepDraInd() {
			return depDraInd;
		}

		public void setDepDraInd(String depDraInd) {
			this.depDraInd = depDraInd;
		}

		public String getChannelType() {
			return channelType;
		}

		public void setChannelType(String channelType) {
			this.channelType = channelType;
		}

		public String getVillageBrnachId() {
			return villageBrnachId;
		}

		public void setVillageBrnachId(String villageBrnachId) {
			this.villageBrnachId = villageBrnachId;
		}

		public String getHostSeqNo() {
			return hostSeqNo;
		}

		public void setHostSeqNo(String hostSeqNo) {
			this.hostSeqNo = hostSeqNo;
		}

		public String getHostDt() {
			return hostDt;
		}

		public void setHostDt(String hostDt) {
			this.hostDt = hostDt;
		}

		public String getCbsTranSts() {
			return cbsTranSts;
		}

		public void setCbsTranSts(String cbsTranSts) {
			this.cbsTranSts = cbsTranSts;
		}

		public String getVillageTranSts() {
			return villageTranSts;
		}

		public void setVillageTranSts(String villageTranSts) {
			this.villageTranSts = villageTranSts;
		}

		public String getCollateSts() {
			return collateSts;
		}

		public void setCollateSts(String collateSts) {
			this.collateSts = collateSts;
		}

		public String getTranAmt() {
			return tranAmt;
		}

		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}

		public String getMfflg() {
			return mfflg;
		}

		public void setMfflg(String mfflg) {
			this.mfflg = mfflg;
		}

		public String getPayeeAcctNo() {
			return payeeAcctNo;
		}

		public void setPayeeAcctNo(String payeeAcctNo) {
			this.payeeAcctNo = payeeAcctNo;
		}

		public String getPayeeAcctName() {
			return payeeAcctName;
		}

		public void setPayeeAcctName(String payeeAcctName) {
			this.payeeAcctName = payeeAcctName;
		}

		public String getPayerAcctNo() {
			return payerAcctNo;
		}

		public void setPayerAcctNo(String payerAcctNo) {
			this.payerAcctNo = payerAcctNo;
		}

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getOfficerId() {
			return officerId;
		}

		public void setOfficerId(String officerId) {
			this.officerId = officerId;
		}

		public String getApprUserId() {
			return apprUserId;
		}

		public void setApprUserId(String apprUserId) {
			this.apprUserId = apprUserId;
		}

		public String getAuthUserId() {
			return authUserId;
		}

		public void setAuthUserId(String authUserId) {
			this.authUserId = authUserId;
		}

		public String getPrintCount() {
			return printCount;
		}

		public void setPrintCount(String printCount) {
			this.printCount = printCount;
		}

		public String getNarrative() {
			return narrative;
		}

		public void setNarrative(String narrative) {
			this.narrative = narrative;
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
