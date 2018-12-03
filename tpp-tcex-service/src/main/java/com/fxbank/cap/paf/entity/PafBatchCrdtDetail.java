package com.fxbank.cap.paf.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Table(name = "paf_batch_crdt_detail")
@Alias("PafBatchCrdtDetail")
public class PafBatchCrdtDetail {
    /**
     * 批量编号
     */
    @Id
    @Column(name = "batch_no")
    private String batchNo;

    /**
     * 序号
     */
    @Id
    @Column(name = "seq_no")
    private String seqNo;

    /**
     * 金额
     */
    @Column(name = "tx_amt")
    private BigDecimal txAmt;

    /**
     * 收方账号
     */
    @Column(name = "cr_acctno")
    private String crAcctno;

    /**
     * 收方户名 
     */
    @Column(name = "cr_acctname")
    private String crAcctname;

    /**
     * 收方联行号 
     */
    @Column(name = "cr_chgno")
    private String crChgno;

    /**
     * 收方开户行联行号 
     */
    @Column(name = "cr_opnchgno")
    private String crOpnchgno;

    /**
     * 收方地址 
     */
    @Column(name = "cr_addr")
    private String crAddr;

    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 业务明细账号
     */
    @Column(name = "ref_acctno")
    private String refAcctno;

    /**
     * 业务明细流水号 1
     */
    @Column(name = "ref_seqno1")
    private String refSeqno1;

    /**
     * 业务明细流水号 2
     */
    @Column(name = "ref_seqno2")
    private String refSeqno2;

    /**
     * 本金发生额
     */
    @Column(name = "cap_amt")
    private BigDecimal capAmt;

    /**
     * 利息发生额
     */
    @Column(name = "int_amt")
    private BigDecimal intAmt;

    /**
     * 交易状态：0、登记；1、记账成功；2、记账失败
     */
    @Column(name = "tx_status")
    private String txStatus;

    /**
     * 核心系统流水号
     */
    @Column(name = "host_seqno")
    private String hostSeqno;

    /**
     * 核心系统响应代码
     */
    @Column(name = "host_rspcode")
    private String hostRspcode;

    /**
     * 核心系统响应信息
     */
    @Column(name = "host_rspmsg")
    private String hostRspmsg;

    /**
     * 批量编号
     * @return batch_no 批量编号
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 批量编号
     * @param batchNo 批量编号
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 序号
     * @return seq_no 序号
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * 序号
     * @param seqNo 序号
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * 金额
     * @return tx_amt 金额
     */
    public BigDecimal getTxAmt() {
        return txAmt;
    }

    /**
     * 金额
     * @param txAmt 金额
     */
    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
    }

    /**
     * 收方账号
     * @return cr_acctno 收方账号
     */
    public String getCrAcctno() {
        return crAcctno;
    }

    /**
     * 收方账号
     * @param crAcctno 收方账号
     */
    public void setCrAcctno(String crAcctno) {
        this.crAcctno = crAcctno;
    }

    /**
     * 收方户名 
     * @return cr_acctname 收方户名 
     */
    public String getCrAcctname() {
        return crAcctname;
    }

    /**
     * 收方户名 
     * @param crAcctname 收方户名 
     */
    public void setCrAcctname(String crAcctname) {
        this.crAcctname = crAcctname;
    }

    /**
     * 收方联行号 
     * @return cr_chgno 收方联行号 
     */
    public String getCrChgno() {
        return crChgno;
    }

    /**
     * 收方联行号 
     * @param crChgno 收方联行号 
     */
    public void setCrChgno(String crChgno) {
        this.crChgno = crChgno;
    }

    /**
     * 收方开户行联行号 
     * @return cr_opnchgno 收方开户行联行号 
     */
    public String getCrOpnchgno() {
        return crOpnchgno;
    }

    /**
     * 收方开户行联行号 
     * @param crOpnchgno 收方开户行联行号 
     */
    public void setCrOpnchgno(String crOpnchgno) {
        this.crOpnchgno = crOpnchgno;
    }

    /**
     * 收方地址 
     * @return cr_addr 收方地址 
     */
    public String getCrAddr() {
        return crAddr;
    }

    /**
     * 收方地址 
     * @param crAddr 收方地址 
     */
    public void setCrAddr(String crAddr) {
        this.crAddr = crAddr;
    }

    /**
     * 摘要
     * @return summary 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 摘要
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 业务明细账号
     * @return ref_acctno 业务明细账号
     */
    public String getRefAcctno() {
        return refAcctno;
    }

    /**
     * 业务明细账号
     * @param refAcctno 业务明细账号
     */
    public void setRefAcctno(String refAcctno) {
        this.refAcctno = refAcctno;
    }

    /**
     * 业务明细流水号 1
     * @return ref_seqno1 业务明细流水号 1
     */
    public String getRefSeqno1() {
        return refSeqno1;
    }

    /**
     * 业务明细流水号 1
     * @param refSeqno1 业务明细流水号 1
     */
    public void setRefSeqno1(String refSeqno1) {
        this.refSeqno1 = refSeqno1;
    }

    /**
     * 业务明细流水号 2
     * @return ref_seqno2 业务明细流水号 2
     */
    public String getRefSeqno2() {
        return refSeqno2;
    }

    /**
     * 业务明细流水号 2
     * @param refSeqno2 业务明细流水号 2
     */
    public void setRefSeqno2(String refSeqno2) {
        this.refSeqno2 = refSeqno2;
    }

    /**
     * 本金发生额
     * @return cap_amt 本金发生额
     */
    public BigDecimal getCapAmt() {
        return capAmt;
    }

    /**
     * 本金发生额
     * @param capAmt 本金发生额
     */
    public void setCapAmt(BigDecimal capAmt) {
        this.capAmt = capAmt;
    }

    /**
     * 利息发生额
     * @return int_amt 利息发生额
     */
    public BigDecimal getIntAmt() {
        return intAmt;
    }

    /**
     * 利息发生额
     * @param intAmt 利息发生额
     */
    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    /**
     * 交易状态：0、登记；1、记账成功；2、记账失败
     * @return tx_status 交易状态：0、登记；1、记账成功；2、记账失败
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * 交易状态：0、登记；1、记账成功；2、记账失败
     * @param txStatus 交易状态：0、登记；1、记账成功；2、记账失败
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }

    /**
     * 核心系统流水号
     * @return host_seqno 核心系统流水号
     */
    public String getHostSeqno() {
        return hostSeqno;
    }

    /**
     * 核心系统流水号
     * @param hostSeqno 核心系统流水号
     */
    public void setHostSeqno(String hostSeqno) {
        this.hostSeqno = hostSeqno;
    }

    /**
     * 核心系统响应代码
     * @return host_rspcode 核心系统响应代码
     */
    public String getHostRspcode() {
        return hostRspcode;
    }

    /**
     * 核心系统响应代码
     * @param hostRspcode 核心系统响应代码
     */
    public void setHostRspcode(String hostRspcode) {
        this.hostRspcode = hostRspcode;
    }

    /**
     * 核心系统响应信息
     * @return host_rspmsg 核心系统响应信息
     */
    public String getHostRspmsg() {
        return hostRspmsg;
    }

    /**
     * 核心系统响应信息
     * @param hostRspmsg 核心系统响应信息
     */
    public void setHostRspmsg(String hostRspmsg) {
        this.hostRspmsg = hostRspmsg;
    }
}