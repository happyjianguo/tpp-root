package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmSndTraceQueryModel 
* @Description: 往账流水表查询模型 
* @author YePuLiang
* @date 2019年4月25日 上午8:55:02 
*  
*/
public class BocmSndTraceQueryModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = -7276797586644413699L;

	public BocmSndTraceQueryModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

    //渠道日期
    private Integer platDate;
    //渠道流水
    private Integer platTrace;
    //交易时间
    private Integer platTime;
    //交易渠道
    private String sourceType;
    //交易机构
    private String txBranch;
    //现转标志
    private String txInd;
    //交易代码
    private String txCode;
    //交易类型
    private String tranType;
    //通存通兑标志
    private String dcFlag;
    //交易金额
    private BigDecimal txAmt;
    //交易时间
    private Integer txDate;
    //账户余额
    private BigDecimal actBal;
    //手续费收取方式
    private String feeFlag;
    //手续费
    private BigDecimal fee;
    //商行手续费收取方式
    private String proxy_flag;
    //商行手续费
    private BigDecimal proxy_fee;
    //发起行人行行号
    private String sndBankno;
    //接收行人行行号
    private String rcvBankno;
    //付款人开户行
    private String payerBank;
    //收款人开户行
    private String payerActtp;
    //收款人开户行
    private String payeeBank;
    //收款人开户行
    private String payeeActtp;
    //核心日期
    private Integer hostDate;
    //核心流水
    private String hostTraceno;
    //付款人账户
    private String payerAcno;
    //付款人户名
    private String payerName;
    //收款人账户
    private String payeeAcno;
    //收款人户名
    private String payeeName;
    //村镇机构
    private String bocmBranch;
    //村镇日期
    private Integer bocmDate;
    //村镇流水
    private String bocmTraceno;
    //对账标志
    private String checkFlag;
    //核心记账状态
    private String hostState;
    //村镇记账状态
    private String bocmState;
    //交易柜员
    private String txTel;
    //复核员
    private String chkTel;
    //授权员
    private String authTel;
    //打印次数
    private String print;
    //摘要
    private String info;

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

	public Integer getPlatTime() {
		return platTime;
	}

	public void setPlatTime(Integer platTime) {
		this.platTime = platTime;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTxBranch() {
		return txBranch;
	}

	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}

	public String getTxInd() {
		return txInd;
	}

	public void setTxInd(String txInd) {
		this.txInd = txInd;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
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

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getBocmBranch() {
		return bocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		this.bocmBranch = bocmBranch;
	}

	public Integer getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(Integer bocmDate) {
		this.bocmDate = bocmDate;
	}

	public String getBocmTraceno() {
		return bocmTraceno;
	}

	public void setBocmTraceno(String bocmTraceno) {
		this.bocmTraceno = bocmTraceno;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getRcvBankno() {
		return rcvBankno;
	}

	public void setRcvBankno(String rcvBankno) {
		this.rcvBankno = rcvBankno;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayerActtp() {
		return payerActtp;
	}

	public void setPayerActtp(String payerActtp) {
		this.payerActtp = payerActtp;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getPayeeActtp() {
		return payeeActtp;
	}

	public void setPayeeActtp(String payeeActtp) {
		this.payeeActtp = payeeActtp;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public String getProxy_flag() {
		return proxy_flag;
	}

	public void setProxy_flag(String proxy_flag) {
		this.proxy_flag = proxy_flag;
	}

	public BigDecimal getProxy_fee() {
		return proxy_fee;
	}

	public void setProxy_fee(BigDecimal proxy_fee) {
		this.proxy_fee = proxy_fee;
	}

	
}
