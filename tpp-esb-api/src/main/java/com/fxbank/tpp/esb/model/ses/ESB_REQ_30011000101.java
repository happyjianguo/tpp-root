package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.tpp.esb.common.ESB;

/** 
* @ClassName: ESB_REQ_30011000101 
* @Description: 本场景用于实现所有外围系统的单笔记账
* @author ZhouYongwei
* @date 2018年9月27日 下午3:02:19 
*  
*/
public class ESB_REQ_30011000101 extends ESB_BASE {

	private static final long serialVersionUID = -1338120038203830314L;

	public ESB_REQ_30011000101(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = true;
		this.getReqSysHead().setMacValue(ESB.macDeginId + "|" + ESB.macNodeId + "|" + ESB.macKeyModelId + "|" + ESB.macPlaceHolder + "|");
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300110001", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -2483609859404922742L;

		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "TRAN_CCY")
		private String tranCcy;//交易币种
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "PREFIX")
		private String prefix;//前缀
		@JSONField(name = "DOC_TYPE")
		private String docType;//凭证类型
		@JSONField(name = "VOUCHER_NO")
		private String voucherNo;//凭证号码
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "OTH_BASE_ACCT_NO")
		private String othBaseAcctNo;//对方账号/卡号
		@JSONField(name = "OTH_BASE_ACCT_NAME")
		private String othBaseAcctName;//对方户名
		@JSONField(name = "OTH_BANK_NAME")
		private String othBankName;//对方银行名称
		@JSONField(name = "OTH_BANK_CODE")
		private String othBankCode;//对方银行行号
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "SETTLEMENT_DATE")
		private String settlementDate;//清算日期
		@JSONField(name = "COLLATE_FLAG")
		private String collateFlag;//对账标识
		@JSONField(name = "SERV_DETAIL")
		private List<ServDetail> servDetail;//服务费信息数组
		@JSONField(name = "ORIG_CHANNEL_SEQ_NO")
		private String origChannelSeqNo;//原渠道流水号
		@JSONField(name = "ORIG_CHANNEL_DATE")
		private String origChannelDate;//原渠道日期
		@JSONField(name = "FC_CODE")
		private String fcCode;//财政区划
		@JSONField(name = "TRAN_METHOD")
		private String tranMethod;//到账方式
		@JSONField(name = "TRAN_NOTE")
		private String tranNote;//交易备注
		@JSONField(name = "ISS_DATE")
		private String issDate;//签发日期
		@JSONField(name = "CHANGE_NO")
		private String changeNo;//交换号
		@JSONField(name = "CHANGE_REGION")
		private String changeRegion;//交换地区
		@JSONField(name = "OTH_CHANGE_NO")
		private String othChangeNo;//对方交换号
		@JSONField(name = "OTH_CHANGE_NAME")
		private String othChangeName;//对方交换行名称
		@JSONField(name = "TRUSTED_PAY_NO")
		private String trustedPayNo;//受托支付编号
		@JSONField(name = "SPECIAL_PAY_NO")
		private String specialPayNo;//特殊扣划流水
		public String getInternalAcct() {
			return internalAcct;
		}

		public void setInternalAcct(String internalAcct) {
			this.internalAcct = internalAcct;
		}

		@JSONField(name = "INTERNAL_ACCT")
		private String internalAcct;//内部账号

		public String getBaseAcctNo() {
			return baseAcctNo;
		}

		public void setBaseAcctNo(String baseAcctNo) {
			this.baseAcctNo = baseAcctNo;
		}

		public String getAcctName() {
			return acctName;
		}

		public void setAcctName(String acctName) {
			this.acctName = acctName;
		}

		public String getTranType() {
			return tranType;
		}

		public void setTranType(String tranType) {
			this.tranType = tranType;
		}

		public String getTranCcy() {
			return tranCcy;
		}

		public void setTranCcy(String tranCcy) {
			this.tranCcy = tranCcy;
		}

		public String getTranAmt() {
			return tranAmt;
		}

		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getDocType() {
			return docType;
		}

		public void setDocType(String docType) {
			this.docType = docType;
		}

		public String getVoucherNo() {
			return voucherNo;
		}

		public void setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
		}

		public String getWithdrawalType() {
			return withdrawalType;
		}

		public void setWithdrawalType(String withdrawalType) {
			this.withdrawalType = withdrawalType;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getOthBaseAcctNo() {
			return othBaseAcctNo;
		}

		public void setOthBaseAcctNo(String othBaseAcctNo) {
			this.othBaseAcctNo = othBaseAcctNo;
		}

		public String getOthBaseAcctName() {
			return othBaseAcctName;
		}

		public void setOthBaseAcctName(String othBaseAcctName) {
			this.othBaseAcctName = othBaseAcctName;
		}

		public String getOthBankName() {
			return othBankName;
		}

		public void setOthBankName(String othBankName) {
			this.othBankName = othBankName;
		}

		public String getOthBankCode() {
			return othBankCode;
		}

		public void setOthBankCode(String othBankCode) {
			this.othBankCode = othBankCode;
		}

		public String getNarrative() {
			return narrative;
		}

		public void setNarrative(String narrative) {
			this.narrative = narrative;
		}

		public String getChannelType() {
			return channelType;
		}

		public void setChannelType(String channelType) {
			this.channelType = channelType;
		}

		public String getSettlementDate() {
			return settlementDate;
		}

		public void setSettlementDate(String settlementDate) {
			this.settlementDate = settlementDate;
		}

		public String getCollateFlag() {
			return collateFlag;
		}

		public void setCollateFlag(String collateFlag) {
			this.collateFlag = collateFlag;
		}

		public List<ServDetail> getServDetail() {
			return servDetail;
		}

		public void setServDetail(List<ServDetail> servDetail) {
			this.servDetail = servDetail;
		}

		public String getOrigChannelSeqNo() {
			return origChannelSeqNo;
		}

		public void setOrigChannelSeqNo(String origChannelSeqNo) {
			this.origChannelSeqNo = origChannelSeqNo;
		}

		public String getOrigChannelDate() {
			return origChannelDate;
		}

		public void setOrigChannelDate(String origChannelDate) {
			this.origChannelDate = origChannelDate;
		}

		public String getFcCode() {
			return fcCode;
		}

		public void setFcCode(String fcCode) {
			this.fcCode = fcCode;
		}

		public String getTranMethod() {
			return tranMethod;
		}

		public void setTranMethod(String tranMethod) {
			this.tranMethod = tranMethod;
		}

		public String getTranNote() {
			return tranNote;
		}

		public void setTranNote(String tranNote) {
			this.tranNote = tranNote;
		}

		public String getIssDate() {
			return issDate;
		}

		public void setIssDate(String issDate) {
			this.issDate = issDate;
		}

		public String getChangeNo() {
			return changeNo;
		}

		public void setChangeNo(String changeNo) {
			this.changeNo = changeNo;
		}

		public String getChangeRegion() {
			return changeRegion;
		}

		public void setChangeRegion(String changeRegion) {
			this.changeRegion = changeRegion;
		}

		public String getOthChangeNo() {
			return othChangeNo;
		}

		public void setOthChangeNo(String othChangeNo) {
			this.othChangeNo = othChangeNo;
		}

		public String getOthChangeName() {
			return othChangeName;
		}

		public void setOthChangeName(String othChangeName) {
			this.othChangeName = othChangeName;
		}

		public String getTrustedPayNo() {
			return trustedPayNo;
		}

		public void setTrustedPayNo(String trustedPayNo) {
			this.trustedPayNo = trustedPayNo;
		}

		public String getSpecialPayNo() {
			return specialPayNo;
		}

		public void setSpecialPayNo(String specialPayNo) {
			this.specialPayNo = specialPayNo;
		}

	}

	public static class ServDetail implements Serializable {

		private static final long serialVersionUID = 7924171455926911041L;

		@JSONField(name = "FEE_TYPE")
		private String feeType;//服务费类型
		@JSONField(name = "FEE_AMT")
		private String feeAmt;//费用金额
		@JSONField(name = "ORIG_FEE_AMT")
		private String origFeeAmt;//原始服务费金额
		@JSONField(name = "DISC_FEE_AMT")
		private String discFeeAmt;//折扣金额
		@JSONField(name = "DISC_TYPE")
		private String discType;//折扣类型
		@JSONField(name = "DISC_RATE")
		private String discRate;//折扣率
		@JSONField(name = "CHARGE_MODE")
		private String chargeMode;//收取方式
		@JSONField(name = "TAX_TYPE")
		private String taxType;//税率类型
		@JSONField(name = "TAX_RATE")
		private String taxRate;//税率
		@JSONField(name = "TAX_AMT")
		private String taxAmt;//利息税金额

		public String getFeeType() {
			return feeType;
		}

		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}

		public String getFeeAmt() {
			return feeAmt;
		}

		public void setFeeAmt(String feeAmt) {
			this.feeAmt = feeAmt;
		}

		public String getOrigFeeAmt() {
			return origFeeAmt;
		}

		public void setOrigFeeAmt(String origFeeAmt) {
			this.origFeeAmt = origFeeAmt;
		}

		public String getDiscFeeAmt() {
			return discFeeAmt;
		}

		public void setDiscFeeAmt(String discFeeAmt) {
			this.discFeeAmt = discFeeAmt;
		}

		public String getDiscType() {
			return discType;
		}

		public void setDiscType(String discType) {
			this.discType = discType;
		}

		public String getDiscRate() {
			return discRate;
		}

		public void setDiscRate(String discRate) {
			this.discRate = discRate;
		}

		public String getChargeMode() {
			return chargeMode;
		}

		public void setChargeMode(String chargeMode) {
			this.chargeMode = chargeMode;
		}

		public String getTaxType() {
			return taxType;
		}

		public void setTaxType(String taxType) {
			this.taxType = taxType;
		}

		public String getTaxRate() {
			return taxRate;
		}

		public void setTaxRate(String taxRate) {
			this.taxRate = taxRate;
		}

		public String getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(String taxAmt) {
			this.taxAmt = taxAmt;
		}

	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

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

}
