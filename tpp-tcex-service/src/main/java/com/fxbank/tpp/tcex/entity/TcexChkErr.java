package com.fxbank.tpp.tcex.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * tcex_chk_err
 */
@Table(name = "tcex_chk_err")
@Alias("tcexChkErr")
public class TcexChkErr {
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
     * 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    @Column(name = "pre_host_state")
    private String preHostState;

    /**
     * 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    @Column(name = "re_host_state")
    private String reHostState;

    /**
     * 通存通兑标志；0通存、1通兑
     */
    @Column(name = "dc_flag")
    private String dcFlag;

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    @Column(name = "check_flag")
    private String checkFlag;

    /**
     * 来往帐标志，I：来账，O：往帐
     */
    @Column(name = "direction")
    private String direction;

    /**
     * 交易金额
     */
    @Column(name = "tx_amt")
    private BigDecimal txAmt;

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
     * 描述
     */
    @Column(name = "msg")
    private String msg;

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
     * 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     * @return pre_host_state 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    public String getPreHostState() {
        return preHostState;
    }

    /**
     * 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     * @param preHostState 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    public void setPreHostState(String preHostState) {
        this.preHostState = preHostState;
    }

    /**
     * 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     * @return re_host_state 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    public String getReHostState() {
        return reHostState;
    }

    /**
     * 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     * @param reHostState 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时
     */
    public void setReHostState(String reHostState) {
        this.reHostState = reHostState;
    }

    /**
     * 通存通兑标志；0通存、1通兑
     * @return dc_flag 通存通兑标志；0通存、1通兑
     */
    public String getDcFlag() {
        return dcFlag;
    }

    /**
     * 通存通兑标志；0通存、1通兑
     * @param dcFlag 通存通兑标志；0通存、1通兑
     */
    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     * @return check_flag 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     * @param checkFlag 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * 来往帐标志，I：来账，O：往帐
     * @return direction 来往帐标志，I：来账，O：往帐
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 来往帐标志，I：来账，O：往帐
     * @param direction 来往帐标志，I：来账，O：往帐
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     * 描述
     * @return msg 描述
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 描述
     * @param msg 描述
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}