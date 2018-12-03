package com.fxbank.cap.paf.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * paf_batch_loan_detail
 */
@Table(name = "paf_batch_loan_detail")
@Alias("pafBatchLoanDetail")
public class PafBatchLoanDetail {
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
     * 预计扣款金额
     */
    @Column(name = "tx_amt")
    private BigDecimal txAmt;
    
    /**
     * 实际扣款金额
     */
    @Column(name = "su_amt")
    private BigDecimal suAmt;

    /**
     * 付方账号
     */
    @Column(name = "de_acctno")
    private String deAcctno;

    /**
     * 付方户名 
     */
    @Column(name = "de_acctname")
    private String deAcctname;

    /**
     * 付方联行号 
     */
    @Column(name = "de_chgno")
    private String deChgno;

    /**
     * 付方开户行联行号 
     */
    @Column(name = "de_opnchgno")
    private String deOpnchgno;

    /**
     * 付方地址 
     */
    @Column(name = "de_addr")
    private String deAddr;

    /**
     * 多方协议号 
     */
    @Column(name = "pro_no")
    private String proNo;

    /**
     * 足额标识0、足额；1、非足额 
     */
    @Column(name = "engh_flag")
    private String enghFlag;

    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 交易状态：0、登记；1、处理中；2、记账成功；3、记账失败
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
     * 付方账号
     * @return de_acctno 付方账号
     */
    public String getDeAcctno() {
        return deAcctno;
    }

    /**
     * 付方账号
     * @param deAcctno 付方账号
     */
    public void setDeAcctno(String deAcctno) {
        this.deAcctno = deAcctno;
    }

    /**
     * 付方户名 
     * @return de_acctname 付方户名 
     */
    public String getDeAcctname() {
        return deAcctname;
    }

    /**
     * 付方户名 
     * @param deAcctname 付方户名 
     */
    public void setDeAcctname(String deAcctname) {
        this.deAcctname = deAcctname;
    }

    /**
     * 付方联行号 
     * @return de_chgno 付方联行号 
     */
    public String getDeChgno() {
        return deChgno;
    }

    /**
     * 付方联行号 
     * @param deChgno 付方联行号 
     */
    public void setDeChgno(String deChgno) {
        this.deChgno = deChgno;
    }

    /**
     * 付方开户行联行号 
     * @return de_opnchgno 付方开户行联行号 
     */
    public String getDeOpnchgno() {
        return deOpnchgno;
    }

    /**
     * 付方开户行联行号 
     * @param deOpnchgno 付方开户行联行号 
     */
    public void setDeOpnchgno(String deOpnchgno) {
        this.deOpnchgno = deOpnchgno;
    }

    /**
     * 付方地址 
     * @return de_addr 付方地址 
     */
    public String getDeAddr() {
        return deAddr;
    }

    /**
     * 付方地址 
     * @param deAddr 付方地址 
     */
    public void setDeAddr(String deAddr) {
        this.deAddr = deAddr;
    }

    /**
     * 多方协议号 
     * @return pro_no 多方协议号 
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 多方协议号 
     * @param proNo 多方协议号 
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 足额标识0、足额；1、非足额 
     * @return engh_flag 足额标识0、足额；1、非足额 
     */
    public String getEnghFlag() {
        return enghFlag;
    }

    /**
     * 足额标识0、足额；1、非足额 
     * @param enghFlag 足额标识0、足额；1、非足额 
     */
    public void setEnghFlag(String enghFlag) {
        this.enghFlag = enghFlag;
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
     * 交易状态：0、登记；1、处理中；2、记账成功；3、记账失败
     * @return tx_status 交易状态：0、登记；1、处理中；2、记账成功；3、记账失败
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * 交易状态：0、登记；1、处理中；2、记账成功；3、记账失败
     * @param txStatus 交易状态：0、登记；1、处理中；2、记账成功；3、记账失败
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

	public BigDecimal getSuAmt() {
		return suAmt;
	}

	public void setSuAmt(BigDecimal suAmt) {
		this.suAmt = suAmt;
	}
    
}