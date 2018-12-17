package com.fxbank.tpp.tcex.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * tpp_rcv_trace_log
 */
@Table(name = "tpp_rcv_trace_log")
@Alias("tppRcvTraceLog")
public class TppRcvTraceLog {
    /**
     * 渠道日期
     */
    @Id
    @Column(name = "plat_date")
    private Integer platDate;

    /**
     * 渠道流水
     */
    @Id
    @Column(name = "plat_trace")
    private Integer platTrace;

    /**
     * 交易时间
     */
    @Column(name = "plat_time")
    private Integer platTime;

    /**
     * 交易渠道
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 交易机构
     */
    @Column(name = "tx_branch")
    private String txBranch;

    /**
     * 现转标志
     */
    @Column(name = "tx_ind")
    private String txInd;

    /**
     * 通存通兑标志
     */
    @Column(name = "dc_flag")
    private String dcFlag;

    /**
     * 交易金额
     */
    @Column(name = "tx_amt")
    private BigDecimal txAmt;

    /**
     * 核心日期
     */
    @Column(name = "host_date")
    private Integer hostDate;

    /**
     * 核心流水
     */
    @Column(name = "host_traceno")
    private String hostTraceno;

    /**
     * 付款人账户
     */
    @Column(name = "payer_acno")
    private String payerAcno;

    /**
     * 付款人户名
     */
    @Column(name = "payer_name")
    private String payerName;

    /**
     * 收款人账户
     */
    @Column(name = "payee_acno")
    private String payeeAcno;

    /**
     * 收款人户名
     */
    @Column(name = "payee_name")
    private String payeeName;

    /**
     * 村镇机构
     */
    @Column(name = "town_branch")
    private String townBranch;

    /**
     * 村镇日期
     */
    @Column(name = "town_date")
    private Integer townDate;

    /**
     * 村镇流水
     */
    @Column(name = "town_traceno")
    private String townTraceno;

    /**
     * 对账标志
     */
    @Column(name = "check_flag")
    private String checkFlag;

    /**
     * 核心记账状态
     */
    @Column(name = "host_state")
    private String hostState;

    /**
     * 村镇记账状态
     */
    @Column(name = "town_state")
    private String townState;

    /**
     * 交易柜员
     */
    @Column(name = "tx_tel")
    private String txTel;

    /**
     * 复核员
     */
    @Column(name = "chk_tel")
    private String chkTel;

    /**
     * 授权员
     */
    @Column(name = "auth_tel")
    private String authTel;

    /**
     * 打印次数
     */
    @Column(name = "print")
    private String print;

    /**
     * 摘要
     */
    @Column(name = "info")
    private String info;
    
    /**
     * 核心反馈响应码
     */
    @Column(name = "ret_code")
    private String retCode;
    
    /**
     * 核心反馈响应信息
     */
    @Column(name = "ret_msg")
    private String retMsg;

    public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	/**
     * 渠道日期
     * @return plat_date 渠道日期
     */
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * 渠道日期
     * @param platDate 渠道日期
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    /**
     * 渠道流水
     * @return plat_trace 渠道流水
     */
    public Integer getPlatTrace() {
        return platTrace;
    }

    /**
     * 渠道流水
     * @param platTrace 渠道流水
     */
    public void setPlatTrace(Integer platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * 交易时间
     * @return plat_time 交易时间
     */
    public Integer getPlatTime() {
        return platTime;
    }

    /**
     * 交易时间
     * @param platTime 交易时间
     */
    public void setPlatTime(Integer platTime) {
        this.platTime = platTime;
    }

    /**
     * 交易渠道
     * @return source_type 交易渠道
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 交易渠道
     * @param sourceType 交易渠道
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 交易机构
     * @return tx_branch 交易机构
     */
    public String getTxBranch() {
        return txBranch;
    }

    /**
     * 交易机构
     * @param txBranch 交易机构
     */
    public void setTxBranch(String txBranch) {
        this.txBranch = txBranch;
    }

    /**
     * 现转标志
     * @return tx_ind 现转标志
     */
    public String getTxInd() {
        return txInd;
    }

    /**
     * 现转标志
     * @param txInd 现转标志
     */
    public void setTxInd(String txInd) {
        this.txInd = txInd;
    }

    /**
     * 通存通兑标志
     * @return dc_flag 通存通兑标志
     */
    public String getDcFlag() {
        return dcFlag;
    }

    /**
     * 通存通兑标志
     * @param dcFlag 通存通兑标志
     */
    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    /**
     * 交易金额
     * @return tx_amt 交易金额
     */
    public BigDecimal getTxAmt() {
        return txAmt;
    }

    /**
     * 交易金额
     * @param txAmt 交易金额
     */
    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
    }

    /**
     * 核心日期
     * @return host_date 核心日期
     */
    public Integer getHostDate() {
        return hostDate;
    }

    /**
     * 核心日期
     * @param hostDate 核心日期
     */
    public void setHostDate(Integer hostDate) {
        this.hostDate = hostDate;
    }

    /**
     * 核心流水
     * @return host_traceno 核心流水
     */
    public String getHostTraceno() {
        return hostTraceno;
    }

    /**
     * 核心流水
     * @param hostTraceno 核心流水
     */
    public void setHostTraceno(String hostTraceno) {
        this.hostTraceno = hostTraceno;
    }

    /**
     * 付款人账户
     * @return payer_acno 付款人账户
     */
    public String getPayerAcno() {
        return payerAcno;
    }

    /**
     * 付款人账户
     * @param payerAcno 付款人账户
     */
    public void setPayerAcno(String payerAcno) {
        this.payerAcno = payerAcno;
    }

    /**
     * 付款人户名
     * @return payer_name 付款人户名
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * 付款人户名
     * @param payerName 付款人户名
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    /**
     * 收款人账户
     * @return payee_acno 收款人账户
     */
    public String getPayeeAcno() {
        return payeeAcno;
    }

    /**
     * 收款人账户
     * @param payeeAcno 收款人账户
     */
    public void setPayeeAcno(String payeeAcno) {
        this.payeeAcno = payeeAcno;
    }

    /**
     * 收款人户名
     * @return payee_name 收款人户名
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * 收款人户名
     * @param payeeName 收款人户名
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * 村镇机构
     * @return town_branch 村镇机构
     */
    public String getTownBranch() {
        return townBranch;
    }

    /**
     * 村镇机构
     * @param townBranch 村镇机构
     */
    public void setTownBranch(String townBranch) {
        this.townBranch = townBranch;
    }

    /**
     * 村镇日期
     * @return town_date 村镇日期
     */
    public Integer getTownDate() {
        return townDate;
    }

    /**
     * 村镇日期
     * @param townDate 村镇日期
     */
    public void setTownDate(Integer townDate) {
        this.townDate = townDate;
    }

    /**
     * 村镇流水
     * @return town_traceno 村镇流水
     */
    public String getTownTraceno() {
        return townTraceno;
    }

    /**
     * 村镇流水
     * @param townTraceno 村镇流水
     */
    public void setTownTraceno(String townTraceno) {
        this.townTraceno = townTraceno;
    }

    /**
     * 对账标志
     * @return check_flag 对账标志
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * 对账标志
     * @param checkFlag 对账标志
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * 核心记账状态
     * @return host_state 核心记账状态
     */
    public String getHostState() {
        return hostState;
    }

    /**
     * 核心记账状态
     * @param hostState 核心记账状态
     */
    public void setHostState(String hostState) {
        this.hostState = hostState;
    }

    /**
     * 村镇记账状态
     * @return town_state 村镇记账状态
     */
    public String getTownState() {
        return townState;
    }

    /**
     * 村镇记账状态
     * @param townState 村镇记账状态
     */
    public void setTownState(String townState) {
        this.townState = townState;
    }

    /**
     * 交易柜员
     * @return tx_tel 交易柜员
     */
    public String getTxTel() {
        return txTel;
    }

    /**
     * 交易柜员
     * @param txTel 交易柜员
     */
    public void setTxTel(String txTel) {
        this.txTel = txTel;
    }

    /**
     * 复核员
     * @return chk_tel 复核员
     */
    public String getChkTel() {
        return chkTel;
    }

    /**
     * 复核员
     * @param chkTel 复核员
     */
    public void setChkTel(String chkTel) {
        this.chkTel = chkTel;
    }

    /**
     * 授权员
     * @return auth_tel 授权员
     */
    public String getAuthTel() {
        return authTel;
    }

    /**
     * 授权员
     * @param authTel 授权员
     */
    public void setAuthTel(String authTel) {
        this.authTel = authTel;
    }

    /**
     * 打印次数
     * @return print 打印次数
     */
    public String getPrint() {
        return print;
    }

    /**
     * 打印次数
     * @param print 打印次数
     */
    public void setPrint(String print) {
        this.print = print;
    }

    /**
     * 摘要
     * @return info 摘要
     */
    public String getInfo() {
        return info;
    }

    /**
     * 摘要
     * @param info 摘要
     */
    public void setInfo(String info) {
        this.info = info;
    }
}