package com.fxbank.tpp.bocm.dto.esb;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: QR_ChkErr 
* @Description: 调账信息查询 
* @author YePuLiang
* @date 2019年7月11日 下午5:48:49 
*  
*/
public class QR_ChkErr {
	
	@FixedField(order = 1, len = 4, scale = 0, desc = "交易代码")
	private String txCode="281B";
	@FixedField(order = 2, len = 4, scale = 0, desc = "交易来源")
    private String source;
	@FixedField(order = 3, len = 8, scale = 0, desc = "平台日期")
    private String platDate;
	@FixedField(order = 4, len = 32, scale = 0, desc = "平台流水")
    private String platTrace;
	@FixedField(order = 5, len = 8, scale = 0, desc = "主机日期")
    private String hostDate;
	@FixedField(order = 6, len = 32, scale = 0, desc = "主机流水")
    private String hostTrace;
	@FixedField(order = 7, len = 8, scale = 0, desc = "交易时间")
    private String txDate;
	@FixedField(order = 8, len = 12, scale = 0, desc = "发起行")
    private String sndBank;
	@FixedField(order = 9, len = 10, scale = 0, desc = "交易机构")
    private String txBranch;
	@FixedField(order = 10, len = 10, scale = 0, desc = "交易柜员")
    private String txTel;
	@FixedField(order = 11, len = 4, scale = 0, desc = "业务模式")
    private String txMod;
	@FixedField(order = 12, len = 32, scale = 0, desc = "交易金额")
    private String txAmt;
	@FixedField(order = 13, len = 32, scale = 0, desc = "商行手续费")
    private String proxyFee;
	@FixedField(order = 14, len = 32, scale = 0, desc = "开户行手续费")
    private String fee;
	@FixedField(order = 15, len = 4, scale = 0, desc = "手续费收取方式")
    private String proxyFlag;
	@FixedField(order = 16, len = 12, scale = 0, desc = "付款人开户行")
    private String payerBank;
	@FixedField(order = 17, len = 32, scale = 0, desc = "付款人账号")
    private String payerAcno;
	@FixedField(order = 18, len = 32, scale = 0, desc = "付款人名称")
    private String payerName;
	@FixedField(order = 19, len = 12, scale = 0, desc = "收款人开户行")
    private String payeeBank;
	@FixedField(order = 20, len = 32, scale = 0, desc = "收款人账号")
    private String payeeAcno;
	@FixedField(order = 21, len = 32, scale = 0, desc = "收款人名称")
    private String payeeName;
	@FixedField(order = 22, len = 4, scale = 0, desc = "主机处理状态")
    private String hostState;
	@FixedField(order = 23, len = 4, scale = 0, desc = "交行处理状态")
    private String bocmState;
	@FixedField(order = 24, len = 4, scale = 0, desc = "主机对账状态")
    private String hostFlag;
	@FixedField(order = 25, len = 4, scale = 0, desc = "交行对账状态")
    private String bocmFlag;
	@FixedField(order = 26, len = 20, scale = 0, desc = "对账以谁为主")
    private String checkMain;
	@FixedField(order = 27, len = 34, scale = 0, desc = "调账信息")
    private String msg;
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPlatDate() {
		return platDate;
	}
	public void setPlatDate(String platDate) {
		this.platDate = platDate;
	}
	public String getPlatTrace() {
		return platTrace;
	}
	public void setPlatTrace(String platTrace) {
		this.platTrace = platTrace;
	}
	public String getHostDate() {
		return hostDate;
	}
	public void setHostDate(String hostDate) {
		this.hostDate = hostDate;
	}
	public String getHostTrace() {
		return hostTrace;
	}
	public void setHostTrace(String hostTrace) {
		this.hostTrace = hostTrace;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getSndBank() {
		return sndBank;
	}
	public void setSndBank(String sndBank) {
		this.sndBank = sndBank;
	}
	public String getTxBranch() {
		return txBranch;
	}
	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}
	public String getTxTel() {
		return txTel;
	}
	public void setTxTel(String txTel) {
		this.txTel = txTel;
	}
	public String getTxMod() {
		return txMod;
	}
	public void setTxMod(String txMod) {
		this.txMod = txMod;
	}
	public String getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}
	public String getProxyFee() {
		return proxyFee;
	}
	public void setProxyFee(String proxyFee) {
		this.proxyFee = proxyFee;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getProxyFlag() {
		return proxyFlag;
	}
	public void setProxyFlag(String proxyFlag) {
		this.proxyFlag = proxyFlag;
	}
	public String getPayerBank() {
		return payerBank;
	}
	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
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
	public String getPayeeBank() {
		return payeeBank;
	}
	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
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
	public String getHostState() {
		return hostState;
	}
	public void setHostState(String hostState) {
		this.hostState = hostState;
	}
	public String getBocmState() {
		return bocmState;
	}
	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}
	public String getHostFlag() {
		return hostFlag;
	}
	public void setHostFlag(String hostFlag) {
		this.hostFlag = hostFlag;
	}
	public String getBocmFlag() {
		return bocmFlag;
	}
	public void setBocmFlag(String bocmFlag) {
		this.bocmFlag = bocmFlag;
	}
	public String getCheckMain() {
		return checkMain;
	}
	public void setCheckMain(String checkMain) {
		this.checkMain = checkMain;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
