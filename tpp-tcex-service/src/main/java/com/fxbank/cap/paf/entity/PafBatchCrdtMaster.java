package com.fxbank.cap.paf.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Table(name = "paf_batch_crdt_master")
@Alias("PafBatchCrdtMaster")
public class PafBatchCrdtMaster {
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
     * 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
     */
    @Column(name = "tx_status")
    private String txStatus;

    /**
     * 交易处理信息
     */
    @Column(name = "tx_msg")
    private String txMsg;

    /**
     * 成功明细核心系统流水号
     */
    @Column(name = "succ_hostseqno")
    private String succHostseqno;

    /**
     * 成功明细核心系统响应代码
     */
    @Column(name = "succ_hostrspcode")
    private String succHostrspcode;

    /**
     * 成功明细核心系统响应信息
     */
    @Column(name = "succ_hostrspmsg")
    private String succHostrspmsg;

    /**
     * 回退明细核心系统流水号
     */
    @Column(name = "roll_hostseqno")
    private String rollHostseqno;

    /**
     * 回退明细核心系统响应代码
     */
    @Column(name = "roll_hostrspcode")
    private String rollHostrspcode;

    /**
     * 回退明细核心系统响应信息
     */
    @Column(name = "roll_hostrspmsg")
    private String rollHostrspmsg;

    /**
     * 批量文件名
     */
    @Column(name = "batch_filename")
    private String batchFilename;

    /**
     * 批量编号CRDT开头，贷款是LOAN开头
     */
    @Column(name = "batch_no")
    private String batchNo;

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
     * 文件类型：1、同行；2、跨行；3、混合
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 业务类型
     */
    @Column(name = "bus_type")
    private String busType;

    /**
     * 批量项目编号
     */
    @Column(name = "batch_prjno")
    private Integer batchPrjno;

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
     * 银行内部过渡户
     */
    @Column(name = "bank_accno")
    private String bankAccno;

    /**
     * 总笔数
     */
    @Column(name = "total_num")
    private Integer totalNum;

    /**
     * 总金额
     */
    @Column(name = "total_amt")
    private BigDecimal totalAmt;

    /**
     * 成功总笔数
     */
    @Column(name = "succ_num")
    private Integer succNum;

    /**
     * 成功总金额
     */
    @Column(name = "succ_amt")
    private BigDecimal succAmt;

    /**
     * 失败总笔数
     */
    @Column(name = "fail_num")
    private Integer failNum;

    /**
     * 失败总金额
     */
    @Column(name = "fail_amt")
    private BigDecimal failAmt;

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
     * 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
     * @return tx_status 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
     * @param txStatus 交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }

    /**
     * 交易处理信息
     * @return tx_msg 交易处理信息
     */
    public String getTxMsg() {
        return txMsg;
    }

    /**
     * 交易处理信息
     * @param txMsg 交易处理信息
     */
    public void setTxMsg(String txMsg) {
        this.txMsg = txMsg;
    }

    /**
     * 成功明细核心系统流水号
     * @return succ_hostseqno 成功明细核心系统流水号
     */
    public String getSuccHostseqno() {
        return succHostseqno;
    }

    /**
     * 成功明细核心系统流水号
     * @param succHostseqno 成功明细核心系统流水号
     */
    public void setSuccHostseqno(String succHostseqno) {
        this.succHostseqno = succHostseqno;
    }

    /**
     * 成功明细核心系统响应代码
     * @return succ_hostrspcode 成功明细核心系统响应代码
     */
    public String getSuccHostrspcode() {
        return succHostrspcode;
    }

    /**
     * 成功明细核心系统响应代码
     * @param succHostrspcode 成功明细核心系统响应代码
     */
    public void setSuccHostrspcode(String succHostrspcode) {
        this.succHostrspcode = succHostrspcode;
    }

    /**
     * 成功明细核心系统响应信息
     * @return succ_hostrspmsg 成功明细核心系统响应信息
     */
    public String getSuccHostrspmsg() {
        return succHostrspmsg;
    }

    /**
     * 成功明细核心系统响应信息
     * @param succHostrspmsg 成功明细核心系统响应信息
     */
    public void setSuccHostrspmsg(String succHostrspmsg) {
        this.succHostrspmsg = succHostrspmsg;
    }

    /**
     * 回退明细核心系统流水号
     * @return roll_hostseqno 回退明细核心系统流水号
     */
    public String getRollHostseqno() {
        return rollHostseqno;
    }

    /**
     * 回退明细核心系统流水号
     * @param rollHostseqno 回退明细核心系统流水号
     */
    public void setRollHostseqno(String rollHostseqno) {
        this.rollHostseqno = rollHostseqno;
    }

    /**
     * 回退明细核心系统响应代码
     * @return roll_hostrspcode 回退明细核心系统响应代码
     */
    public String getRollHostrspcode() {
        return rollHostrspcode;
    }

    /**
     * 回退明细核心系统响应代码
     * @param rollHostrspcode 回退明细核心系统响应代码
     */
    public void setRollHostrspcode(String rollHostrspcode) {
        this.rollHostrspcode = rollHostrspcode;
    }

    /**
     * 回退明细核心系统响应信息
     * @return roll_hostrspmsg 回退明细核心系统响应信息
     */
    public String getRollHostrspmsg() {
        return rollHostrspmsg;
    }

    /**
     * 回退明细核心系统响应信息
     * @param rollHostrspmsg 回退明细核心系统响应信息
     */
    public void setRollHostrspmsg(String rollHostrspmsg) {
        this.rollHostrspmsg = rollHostrspmsg;
    }

    /**
     * 批量文件名
     * @return batch_filename 批量文件名
     */
    public String getBatchFilename() {
        return batchFilename;
    }

    /**
     * 批量文件名
     * @param batchFilename 批量文件名
     */
    public void setBatchFilename(String batchFilename) {
        this.batchFilename = batchFilename;
    }

    /**
     * 批量编号CRDT开头，贷款是LOAN开头
     * @return batch_no 批量编号CRDT开头，贷款是LOAN开头
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 批量编号CRDT开头，贷款是LOAN开头
     * @param batchNo 批量编号CRDT开头，贷款是LOAN开头
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
     * 文件类型：1、同行；2、跨行；3、混合
     * @return file_type 文件类型：1、同行；2、跨行；3、混合
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型：1、同行；2、跨行；3、混合
     * @param fileType 文件类型：1、同行；2、跨行；3、混合
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
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
     * 批量项目编号
     * @return batch_prjno 批量项目编号
     */
    public Integer getBatchPrjno() {
        return batchPrjno;
    }

    /**
     * 批量项目编号
     * @param batchPrjno 批量项目编号
     */
    public void setBatchPrjno(Integer batchPrjno) {
        this.batchPrjno = batchPrjno;
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
     * 银行内部过渡户
     * @return bank_accno 银行内部过渡户
     */
    public String getBankAccno() {
        return bankAccno;
    }

    /**
     * 银行内部过渡户
     * @param bankAccno 银行内部过渡户
     */
    public void setBankAccno(String bankAccno) {
        this.bankAccno = bankAccno;
    }

    /**
     * 总笔数
     * @return total_num 总笔数
     */
    public Integer getTotalNum() {
        return totalNum;
    }

    /**
     * 总笔数
     * @param totalNum 总笔数
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 总金额
     * @return total_amt 总金额
     */
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    /**
     * 总金额
     * @param totalAmt 总金额
     */
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * 成功总笔数
     * @return succ_num 成功总笔数
     */
    public Integer getSuccNum() {
        return succNum;
    }

    /**
     * 成功总笔数
     * @param succNum 成功总笔数
     */
    public void setSuccNum(Integer succNum) {
        this.succNum = succNum;
    }

    /**
     * 成功总金额
     * @return succ_amt 成功总金额
     */
    public BigDecimal getSuccAmt() {
        return succAmt;
    }

    /**
     * 成功总金额
     * @param succAmt 成功总金额
     */
    public void setSuccAmt(BigDecimal succAmt) {
        this.succAmt = succAmt;
    }

    /**
     * 失败总笔数
     * @return fail_num 失败总笔数
     */
    public Integer getFailNum() {
        return failNum;
    }

    /**
     * 失败总笔数
     * @param failNum 失败总笔数
     */
    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    /**
     * 失败总金额
     * @return fail_amt 失败总金额
     */
    public BigDecimal getFailAmt() {
        return failAmt;
    }

    /**
     * 失败总金额
     * @param failAmt 失败总金额
     */
    public void setFailAmt(BigDecimal failAmt) {
        this.failAmt = failAmt;
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