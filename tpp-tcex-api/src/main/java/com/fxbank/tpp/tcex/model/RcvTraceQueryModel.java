package com.fxbank.tpp.tcex.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: RcvTraceQueryModel 
* @Description: 查询来账信息
* @author Duzhenduo
* @date 2019年1月31日 上午10:10:11 
*  
*/
public class RcvTraceQueryModel extends ModelBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8931250505802693546L;

	public RcvTraceQueryModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	/**
     * 渠道日期
     */
    private Integer platDate;

    /**
     * 渠道流水
     */
    private Integer platTrace;

    /**
     * 交易时间
     */
    private Integer platTime;

    /**
     * 交易渠道
     */
    private String sourceType;

    /**
     * 交易机构
     */
    private String txBranch;

    /**
     * 现转标志
     */
    private String txInd;

    /**
     * 通存通兑标志
     */
    private String dcFlag;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 核心日期
     */
    private Integer hostDate;

    /**
     * 核心流水
     */
    private String hostTraceno;

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
     * 村镇机构
     */
    private String townBranch;

    /**
     * 村镇日期
     */
    private Integer townDate;

    /**
     * 村镇流水
     */
    private String townTraceno;

    /**
     * 对账标志
     */
    private String checkFlag;

    /**
     * 核心记账状态
     */
    private String hostState;

    /**
     * 村镇记账状态
     */
    private String townState;

    /**
     * 交易柜员
     */
    private String txTel;

    /**
     * 复核员
     */
    private String chkTel;

    /**
     * 授权员
     */
    private String authTel;

    /**
     * 打印次数
     */
    private String print;

    /**
     * 摘要
     */
    private String info;
    
    private String townFlag;
    
    

	public String getTownFlag() {
		return townFlag;
	}

	public void setTownFlag(String townFlag) {
		this.townFlag = townFlag;
	}

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

	public String getTownBranch() {
		return townBranch;
	}

	public void setTownBranch(String townBranch) {
		this.townBranch = townBranch;
	}

	public Integer getTownDate() {
		return townDate;
	}

	public void setTownDate(Integer townDate) {
		this.townDate = townDate;
	}

	public String getTownTraceno() {
		return townTraceno;
	}

	public void setTownTraceno(String townTraceno) {
		this.townTraceno = townTraceno;
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

	public String getTownState() {
		return townState;
	}

	public void setTownState(String townState) {
		this.townState = townState;
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
