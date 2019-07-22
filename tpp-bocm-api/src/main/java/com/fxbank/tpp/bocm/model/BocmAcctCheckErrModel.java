package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmAcctCheckErrModel 
* @Description: 交通柜面通对账问题数据模型
* @author YePuLiang
* @date 2019年5月7日 上午9:12:14 
*  
*/
public class BocmAcctCheckErrModel extends ModelBase implements Serializable {

	private static final long serialVersionUID = 8412341436449310655L;
	
	public BocmAcctCheckErrModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	/**
     * 渠道日期
     */
	@NotNull(message = "platDate渠道日期不能为空")
    private Integer platDate;

    /**
     * 渠道流水
     */
	@NotNull(message = "platTrace渠道流水不能为空")
    private Integer platTrace;
	
	//交易来源
	private String txSource;
	
	//交易代码
	private String txCode;
	
	//主机日期
	private Integer hostDate;
	
	//主机流水
	private String hostTraceno;
	
	//交易时间
	private Integer txDate;
	
	//受理行人行行号
    private String sndBankno;
	
    //交易机构
    private String txBranch; 
    
    //交易柜员
    private String txTel;
    
    //交易模式
    private String txInd;
    
    //代理手续费手续方式
    private String proxyFlag;
    //代理手续费
    private BigDecimal proxyFee;
    
    private String payerBank;
    
    private String payeeBank;
    
    private String hostState;
    
    private String bocmState;

    /**
     * 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时
     */
    private String preHostState;

    /**
     * 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时
     */
    private String reHostState;

    /**
     * 通存通兑标志；0通存、1通兑
     */
    private String dcFlag;

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    private String checkFlag;
    //核心对账状态
    private String hostFlag;
    //交行对账状态
    private String bocmFlag;

    /**
     * 来往帐标志，I：来账，O：往帐
     */
    private String direction;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 付款人账户
     */
    private String payerAcno;

    /**
     * 付款人户名
     */
    private String payerName;

    /**
     * 收款人账户
     */
    private String payeeAcno;

    /**
     * 收款人户名
     */
    private String payeeName;

    /**
     * 描述
     */
    private String msg;

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

	public String getPreHostState() {
		return preHostState;
	}

	public void setPreHostState(String preHostState) {
		this.preHostState = preHostState;
	}

	public String getReHostState() {
		return reHostState;
	}

	public void setReHostState(String reHostState) {
		this.reHostState = reHostState;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTxSource() {
		return txSource;
	}

	public void setTxSource(String txSource) {
		this.txSource = txSource;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
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

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
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

	public String getTxInd() {
		return txInd;
	}

	public void setTxInd(String txInd) {
		this.txInd = txInd;
	}

	public String getProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(String proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public BigDecimal getProxyFee() {
		return proxyFee;
	}

	public void setProxyFee(BigDecimal proxyFee) {
		this.proxyFee = proxyFee;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
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

	
}
