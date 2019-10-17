package com.fxbank.tpp.bocm.dto.esb;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;

/** 
* @ClassName: QR_Trace 
* @Description:柜面流水查询响应
* @author YePuLiang
* @date 2019年7月11日 下午1:39:55 
*  
*/
public class QR_TraceDto {
	
	@FixedField(order = 1, len = 4, scale = 0, desc = "交易代码")
	private String txCode="281A";
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
	@FixedField(order = 23, len = 4, scale = 0, desc = "主机对账状态")
    private String checkFlag;
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
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public static void main(String[] args) {
		QR_TraceDto trace = new QR_TraceDto();
		trace.setCheckFlag("1");
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(trace,"|",ServerInitializer.CODING));
		System.out.println(fixPack);
	}
}
