package com.fxbank.cap.manager.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "paf_acct_mst_report")
public class PafAccMstReport extends BaseData{


    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "depart_code")
    private String departCode;

    @Column(name = "center_no")
    private String centerNo;

    @Column(name = "report_time")
    private String reportTime;

    @Column(name = "report_user")
    private Integer reportUser;

    @Column(name = "num")
    private String num;

    @Column(name = "acct_no")
    private String acctNo;

    @Column(name = "reference")
    private String reference;

    @Column(name = "tran_code")
    private String tranCode;

    @Column(name = "oth_acct_no")
    private String othAcctNo;

    @Column(name = "oth_acct_name")
    private String othAcctName;

    @Column(name = "tran_amt")
    private BigDecimal tranAmt;

    @Column(name = "tran_date")
    private String tranDate;

    @Column(name = "tran_time")
    private String tranTime;

    @Column(name = "available_amt")
    private BigDecimal availableAmt;

    @Column(name = "branch")
    private String branch;

    @Column(name = "remark")
    private String remark;

    @Column(name = "ccy")
    private String ccy;

    @Column(name = "amt_type")
    private String amtType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "odt_balance")
    private BigDecimal odtBalance;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "oth_branch")
    private String othBranch;

    @Column(name = "narrative")
    private String narrative;

    @Column(name = "reversak")
    private String reversak;

    @Column(name = "serial_num")
    private String serialNum;

    @Column(name = "volume_num")
    private String volumeNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getReportUser() {
        return reportUser;
    }

    public void setReportUser(Integer reportUser) {
        this.reportUser = reportUser;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getOthAcctNo() {
        return othAcctNo;
    }

    public void setOthAcctNo(String othAcctNo) {
        this.othAcctNo = othAcctNo;
    }

    public String getOthAcctName() {
        return othAcctName;
    }

    public void setOthAcctName(String othAcctName) {
        this.othAcctName = othAcctName;
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public BigDecimal getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(BigDecimal availableAmt) {
        this.availableAmt = availableAmt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOdtBalance() {
        return odtBalance;
    }

    public void setOdtBalance(BigDecimal odtBalance) {
        this.odtBalance = odtBalance;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getOthBranch() {
        return othBranch;
    }

    public void setOthBranch(String othBranch) {
        this.othBranch = othBranch;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getReversak() {
        return reversak;
    }

    public void setReversak(String reversak) {
        this.reversak = reversak;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getVolumeNum() {
        return volumeNum;
    }

    public void setVolumeNum(String volumeNum) {
        this.volumeNum = volumeNum;
    }
}
