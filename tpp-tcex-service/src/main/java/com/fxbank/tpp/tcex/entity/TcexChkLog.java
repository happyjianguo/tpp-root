package com.fxbank.tpp.tcex.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * tcex_chk_log
 */
@Table(name = "tcex_chk_log")
@Alias("tcexChkLog")
public class TcexChkLog {
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
     * 清算日期
     */
    @Column(name = "settle_date")
    private Integer settleDate;

    /**
     * 清算机构
     */
    @Column(name = "settle_branch")
    private String settleBranch;

    /**
     * 核心交易日期
     */
    @Column(name = "host_date")
    private Integer hostDate;

    /**
     * 核心流水号
     */
    @Column(name = "host_traceno")
    private String hostTraceno;

    /**
     * 交易币种
     */
    @Column(name = "ccy")
    private String ccy;

    /**
     * 交易金额
     */
    @Column(name = "tx_amt")
    private BigDecimal txAmt;

    /**
     * 交易账户
     */
    @Column(name = "accountno")
    private String accountno;

    /**
     * 冲正标志
     */
    @Column(name = "reversal")
    private String reversal;

    /**
     * 交易状态；00-成功 02-冲正
     */
    @Column(name = "tx_status")
    private String txStatus;

    /**
     * 来往账标识
     */
    @Column(name = "direction")
    private String direction;

    
    public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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
     * 清算日期
     * @return settle_date 清算日期
     */
    public Integer getSettleDate() {
        return settleDate;
    }

    /**
     * 清算日期
     * @param settleDate 清算日期
     */
    public void setSettleDate(Integer settleDate) {
        this.settleDate = settleDate;
    }

    /**
     * 清算机构
     * @return settle_branch 清算机构
     */
    public String getSettleBranch() {
        return settleBranch;
    }

    /**
     * 清算机构
     * @param settleBranch 清算机构
     */
    public void setSettleBranch(String settleBranch) {
        this.settleBranch = settleBranch;
    }

    /**
     * 核心交易日期
     * @return host_date 核心交易日期
     */
    public Integer getHostDate() {
        return hostDate;
    }

    /**
     * 核心交易日期
     * @param hostDate 核心交易日期
     */
    public void setHostDate(Integer hostDate) {
        this.hostDate = hostDate;
    }

    /**
     * 核心流水号
     * @return host_traceno 核心流水号
     */
    public String getHostTraceno() {
        return hostTraceno;
    }

    /**
     * 核心流水号
     * @param hostTraceno 核心流水号
     */
    public void setHostTraceno(String hostTraceno) {
        this.hostTraceno = hostTraceno;
    }

    /**
     * 交易币种
     * @return ccy 交易币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 交易币种
     * @param ccy 交易币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
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
     * 交易账户
     * @return accountno 交易账户
     */
    public String getAccountno() {
        return accountno;
    }

    /**
     * 交易账户
     * @param accountno 交易账户
     */
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    /**
     * 冲正标志
     * @return reversal 冲正标志
     */
    public String getReversal() {
        return reversal;
    }

    /**
     * 冲正标志
     * @param reversal 冲正标志
     */
    public void setReversal(String reversal) {
        this.reversal = reversal;
    }

    /**
     * 交易状态；00-成功 02-冲正
     * @return tx_status 交易状态；00-成功 02-冲正
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * 交易状态；00-成功 02-冲正
     * @param txStatus 交易状态；00-成功 02-冲正
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }
}