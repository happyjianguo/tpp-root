package com.fxbank.cap.paf.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Table(name = "paf_single_crdt")
@Alias("pafSingleCrdt")
public class PafSingleCrdt {
    /**
     * 平台日期
     */
    @Id
    @Column(name = "plat_date")
    private Integer platDate;

    /**
     * 平台流水
     */
    @Id
    @Column(name = "plat_trace")
    private Integer platTrace;

    /**
     * 交易机构
     */
    @Column(name = "tx_brno")
    private String txBrno;

    /**
     * 平台时间
     */
    @Column(name = "plat_time")
    private Integer platTime;

    /**
     * 上级机构
     */
    @Column(name = "up_brno")
    private String upBrno;

    /**
     * 日志ID
     */
    @Column(name = "log_id")
    private String logId;

    /**
     * 日志设备
     */
    @Column(name = "log_dev")
    private String logDev;

    /**
     * 发送方日期
     */
    @Column(name = "snd_date")
    private String sndDate;

    /**
     * 发送方时间
     */
    @Column(name = "snd_time")
    private String sndTime;

    /**
     * 发送方流水
     */
    @Column(name = "snd_seqno")
    private String sndSeqno;

    /**
     * 公积金机构号
     */
    @Column(name = "snd_unitno")
    private String sndUnitno;

    /**
     * 发送方节点号
     */
    @Column(name = "snd_node")
    private String sndNode;

    /**
     * 接收方节点号
     */
    @Column(name = "rcv_node")
    private String rcvNode;

    /**
     * 结算系统日期
     */
    @Column(name = "bdc_date")
    private String bdcDate;

    /**
     * 结算系统时间
     */
    @Column(name = "bdc_time")
    private String bdcTime;

    /**
     * 结算系统流水
     */
    @Column(name = "bdc_seqno")
    private String bdcSeqno;

    /**
     * 银行客户编号
     */
    @Column(name = "cus_no")
    private String cusNo;

    /**
     * 操作员编号
     */
    @Column(name = "opr_no")
    private String oprNo;

    /**
     * 交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败
     */
    @Column(name = "tx_status")
    private String txStatus;

    /**
     * 本金核心系统流水号
     */
    @Column(name = "cap_seqno")
    private String capSeqno;

    /**
     * 本金核心系统响应代码
     */
    @Column(name = "cap_hostcode")
    private String capHostcode;

    /**
     * 本金核心系统响应信息
     */
    @Column(name = "cap_hostmsg")
    private String capHostmsg;

    /**
     * 利息核心系统流水号
     */
    @Column(name = "int_seqno")
    private String intSeqno;

    /**
     * 利息核心系统响应代码
     */
    @Column(name = "int_hostcode")
    private String intHostcode;

    /**
     * 利息核心系统响应信息
     */
    @Column(name = "int_hostmsg")
    private String intHostmsg;

    /**
     * 币种，156
     */
    @Column(name = "curr_no")
    private String currNo;

    /**
     * 钞汇鉴别：1、钞；2、汇
     */
    @Column(name = "curr_iden")
    private String currIden;

    /**
     * 结算模式：1、同行；2、跨行实时；3、跨行非实时
     */
    @Column(name = "settle_type")
    private String settleType;

    /**
     * 业务类型
     */
    @Column(name = "bus_type")
    private String busType;

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
     * 付方账户类别：1、对私；2、对公
     */
    @Column(name = "de_acctclass")
    private String deAcctclass;

    /**
     * 本金发生额
     */
    @Column(name = "cap_amt")
    private BigDecimal capAmt;

    /**
     * 付息户账号 
     */
    @Column(name = "de_intacctno")
    private String deIntacctno;

    /**
     * 付息户户名 
     */
    @Column(name = "de_intacctname")
    private String deIntacctname;

    /**
     * 付息户类别:1、对私；2、对公
     */
    @Column(name = "de_intacctclass")
    private String deIntacctclass;

    /**
     * 利息收方账号
     */
    @Column(name = "de_intcracct")
    private String deIntcracct;

    /**
     * 利息发生额
     */
    @Column(name = "int_amt")
    private BigDecimal intAmt;

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
     * 收方账户类别:1、对私；2、对公
     */
    @Column(name = "cr_acctclass")
    private String crAcctclass;

    /**
     * 收方账户行别:0、本行；1、跨行
     */
    @Column(name = "cr_bankclass")
    private String crBankclass;

    /**
     * 收方行名 
     */
    @Column(name = "cr_bankname")
    private String crBankname;

    /**
     * 收方联行号 
     */
    @Column(name = "cr_chgno")
    private String crChgno;

    /**
     * 交易金额
     */
    @Column(name = "amt")
    private BigDecimal amt;

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
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 平台日期
     * @return plat_date 平台日期
     */
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * 平台日期
     * @param platDate 平台日期
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    /**
     * 平台流水
     * @return plat_trace 平台流水
     */
    public Integer getPlatTrace() {
        return platTrace;
    }

    /**
     * 平台流水
     * @param platTrace 平台流水
     */
    public void setPlatTrace(Integer platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * 交易机构
     * @return tx_brno 交易机构
     */
    public String getTxBrno() {
        return txBrno;
    }

    /**
     * 交易机构
     * @param txBrno 交易机构
     */
    public void setTxBrno(String txBrno) {
        this.txBrno = txBrno;
    }

    /**
     * 平台时间
     * @return plat_time 平台时间
     */
    public Integer getPlatTime() {
        return platTime;
    }

    /**
     * 平台时间
     * @param platTime 平台时间
     */
    public void setPlatTime(Integer platTime) {
        this.platTime = platTime;
    }

    /**
     * 上级机构
     * @return up_brno 上级机构
     */
    public String getUpBrno() {
        return upBrno;
    }

    /**
     * 上级机构
     * @param upBrno 上级机构
     */
    public void setUpBrno(String upBrno) {
        this.upBrno = upBrno;
    }

    /**
     * 日志ID
     * @return log_id 日志ID
     */
    public String getLogId() {
        return logId;
    }

    /**
     * 日志ID
     * @param logId 日志ID
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
     * 日志设备
     * @return log_dev 日志设备
     */
    public String getLogDev() {
        return logDev;
    }

    /**
     * 日志设备
     * @param logDev 日志设备
     */
    public void setLogDev(String logDev) {
        this.logDev = logDev;
    }

    /**
     * 发送方日期
     * @return snd_date 发送方日期
     */
    public String getSndDate() {
        return sndDate;
    }

    /**
     * 发送方日期
     * @param sndDate 发送方日期
     */
    public void setSndDate(String sndDate) {
        this.sndDate = sndDate;
    }

    /**
     * 发送方时间
     * @return snd_time 发送方时间
     */
    public String getSndTime() {
        return sndTime;
    }

    /**
     * 发送方时间
     * @param sndTime 发送方时间
     */
    public void setSndTime(String sndTime) {
        this.sndTime = sndTime;
    }

    /**
     * 发送方流水
     * @return snd_seqno 发送方流水
     */
    public String getSndSeqno() {
        return sndSeqno;
    }

    /**
     * 发送方流水
     * @param sndSeqno 发送方流水
     */
    public void setSndSeqno(String sndSeqno) {
        this.sndSeqno = sndSeqno;
    }

    /**
     * 公积金机构号
     * @return snd_unitno 公积金机构号
     */
    public String getSndUnitno() {
        return sndUnitno;
    }

    /**
     * 公积金机构号
     * @param sndUnitno 公积金机构号
     */
    public void setSndUnitno(String sndUnitno) {
        this.sndUnitno = sndUnitno;
    }

    /**
     * 发送方节点号
     * @return snd_node 发送方节点号
     */
    public String getSndNode() {
        return sndNode;
    }

    /**
     * 发送方节点号
     * @param sndNode 发送方节点号
     */
    public void setSndNode(String sndNode) {
        this.sndNode = sndNode;
    }

    /**
     * 接收方节点号
     * @return rcv_node 接收方节点号
     */
    public String getRcvNode() {
        return rcvNode;
    }

    /**
     * 接收方节点号
     * @param rcvNode 接收方节点号
     */
    public void setRcvNode(String rcvNode) {
        this.rcvNode = rcvNode;
    }

    /**
     * 结算系统日期
     * @return bdc_date 结算系统日期
     */
    public String getBdcDate() {
        return bdcDate;
    }

    /**
     * 结算系统日期
     * @param bdcDate 结算系统日期
     */
    public void setBdcDate(String bdcDate) {
        this.bdcDate = bdcDate;
    }

    /**
     * 结算系统时间
     * @return bdc_time 结算系统时间
     */
    public String getBdcTime() {
        return bdcTime;
    }

    /**
     * 结算系统时间
     * @param bdcTime 结算系统时间
     */
    public void setBdcTime(String bdcTime) {
        this.bdcTime = bdcTime;
    }

    /**
     * 结算系统流水
     * @return bdc_seqno 结算系统流水
     */
    public String getBdcSeqno() {
        return bdcSeqno;
    }

    /**
     * 结算系统流水
     * @param bdcSeqno 结算系统流水
     */
    public void setBdcSeqno(String bdcSeqno) {
        this.bdcSeqno = bdcSeqno;
    }

    /**
     * 银行客户编号
     * @return cus_no 银行客户编号
     */
    public String getCusNo() {
        return cusNo;
    }

    /**
     * 银行客户编号
     * @param cusNo 银行客户编号
     */
    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    /**
     * 操作员编号
     * @return opr_no 操作员编号
     */
    public String getOprNo() {
        return oprNo;
    }

    /**
     * 操作员编号
     * @param oprNo 操作员编号
     */
    public void setOprNo(String oprNo) {
        this.oprNo = oprNo;
    }

    /**
     * 交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败
     * @return tx_status 交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * 交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败
     * @param txStatus 交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }

    /**
     * 本金核心系统流水号
     * @return cap_seqno 本金核心系统流水号
     */
    public String getCapSeqno() {
        return capSeqno;
    }

    /**
     * 本金核心系统流水号
     * @param capSeqno 本金核心系统流水号
     */
    public void setCapSeqno(String capSeqno) {
        this.capSeqno = capSeqno;
    }

    /**
     * 本金核心系统响应代码
     * @return cap_hostcode 本金核心系统响应代码
     */
    public String getCapHostcode() {
        return capHostcode;
    }

    /**
     * 本金核心系统响应代码
     * @param capHostcode 本金核心系统响应代码
     */
    public void setCapHostcode(String capHostcode) {
        this.capHostcode = capHostcode;
    }

    /**
     * 本金核心系统响应信息
     * @return cap_hostmsg 本金核心系统响应信息
     */
    public String getCapHostmsg() {
        return capHostmsg;
    }

    /**
     * 本金核心系统响应信息
     * @param capHostmsg 本金核心系统响应信息
     */
    public void setCapHostmsg(String capHostmsg) {
        this.capHostmsg = capHostmsg;
    }

    /**
     * 利息核心系统流水号
     * @return int_seqno 利息核心系统流水号
     */
    public String getIntSeqno() {
        return intSeqno;
    }

    /**
     * 利息核心系统流水号
     * @param intSeqno 利息核心系统流水号
     */
    public void setIntSeqno(String intSeqno) {
        this.intSeqno = intSeqno;
    }

    /**
     * 利息核心系统响应代码
     * @return int_hostcode 利息核心系统响应代码
     */
    public String getIntHostcode() {
        return intHostcode;
    }

    /**
     * 利息核心系统响应代码
     * @param intHostcode 利息核心系统响应代码
     */
    public void setIntHostcode(String intHostcode) {
        this.intHostcode = intHostcode;
    }

    /**
     * 利息核心系统响应信息
     * @return int_hostmsg 利息核心系统响应信息
     */
    public String getIntHostmsg() {
        return intHostmsg;
    }

    /**
     * 利息核心系统响应信息
     * @param intHostmsg 利息核心系统响应信息
     */
    public void setIntHostmsg(String intHostmsg) {
        this.intHostmsg = intHostmsg;
    }

    /**
     * 币种，156
     * @return curr_no 币种，156
     */
    public String getCurrNo() {
        return currNo;
    }

    /**
     * 币种，156
     * @param currNo 币种，156
     */
    public void setCurrNo(String currNo) {
        this.currNo = currNo;
    }

    /**
     * 钞汇鉴别：1、钞；2、汇
     * @return curr_iden 钞汇鉴别：1、钞；2、汇
     */
    public String getCurrIden() {
        return currIden;
    }

    /**
     * 钞汇鉴别：1、钞；2、汇
     * @param currIden 钞汇鉴别：1、钞；2、汇
     */
    public void setCurrIden(String currIden) {
        this.currIden = currIden;
    }

    /**
     * 结算模式：1、同行；2、跨行实时；3、跨行非实时
     * @return settle_type 结算模式：1、同行；2、跨行实时；3、跨行非实时
     */
    public String getSettleType() {
        return settleType;
    }

    /**
     * 结算模式：1、同行；2、跨行实时；3、跨行非实时
     * @param settleType 结算模式：1、同行；2、跨行实时；3、跨行非实时
     */
    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    /**
     * 业务类型
     * @return bus_type 业务类型
     */
    public String getBusType() {
        return busType;
    }

    /**
     * 业务类型
     * @param busType 业务类型
     */
    public void setBusType(String busType) {
        this.busType = busType;
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
     * 付方账户类别：1、对私；2、对公
     * @return de_acctclass 付方账户类别：1、对私；2、对公
     */
    public String getDeAcctclass() {
        return deAcctclass;
    }

    /**
     * 付方账户类别：1、对私；2、对公
     * @param deAcctclass 付方账户类别：1、对私；2、对公
     */
    public void setDeAcctclass(String deAcctclass) {
        this.deAcctclass = deAcctclass;
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
     * 付息户账号 
     * @return de_intacctno 付息户账号 
     */
    public String getDeIntacctno() {
        return deIntacctno;
    }

    /**
     * 付息户账号 
     * @param deIntacctno 付息户账号 
     */
    public void setDeIntacctno(String deIntacctno) {
        this.deIntacctno = deIntacctno;
    }

    /**
     * 付息户户名 
     * @return de_intacctname 付息户户名 
     */
    public String getDeIntacctname() {
        return deIntacctname;
    }

    /**
     * 付息户户名 
     * @param deIntacctname 付息户户名 
     */
    public void setDeIntacctname(String deIntacctname) {
        this.deIntacctname = deIntacctname;
    }

    /**
     * 付息户类别:1、对私；2、对公
     * @return de_intacctclass 付息户类别:1、对私；2、对公
     */
    public String getDeIntacctclass() {
        return deIntacctclass;
    }

    /**
     * 付息户类别:1、对私；2、对公
     * @param deIntacctclass 付息户类别:1、对私；2、对公
     */
    public void setDeIntacctclass(String deIntacctclass) {
        this.deIntacctclass = deIntacctclass;
    }

    /**
     * 利息收方账号
     * @return de_intcracct 利息收方账号
     */
    public String getDeIntcracct() {
        return deIntcracct;
    }

    /**
     * 利息收方账号
     * @param deIntcracct 利息收方账号
     */
    public void setDeIntcracct(String deIntcracct) {
        this.deIntcracct = deIntcracct;
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
     * 收方账户类别:1、对私；2、对公
     * @return cr_acctclass 收方账户类别:1、对私；2、对公
     */
    public String getCrAcctclass() {
        return crAcctclass;
    }

    /**
     * 收方账户类别:1、对私；2、对公
     * @param crAcctclass 收方账户类别:1、对私；2、对公
     */
    public void setCrAcctclass(String crAcctclass) {
        this.crAcctclass = crAcctclass;
    }

    /**
     * 收方账户行别:0、本行；1、跨行
     * @return cr_bankclass 收方账户行别:0、本行；1、跨行
     */
    public String getCrBankclass() {
        return crBankclass;
    }

    /**
     * 收方账户行别:0、本行；1、跨行
     * @param crBankclass 收方账户行别:0、本行；1、跨行
     */
    public void setCrBankclass(String crBankclass) {
        this.crBankclass = crBankclass;
    }

    /**
     * 收方行名 
     * @return cr_bankname 收方行名 
     */
    public String getCrBankname() {
        return crBankname;
    }

    /**
     * 收方行名 
     * @param crBankname 收方行名 
     */
    public void setCrBankname(String crBankname) {
        this.crBankname = crBankname;
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
     * 交易金额
     * @return amt 交易金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 交易金额
     * @param amt 交易金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}