package com.fxbank.tpp.mivs.entity.SysStsNtfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_sysstsntfctn_info")
public class MivsSysstsntfctnInfoEntity {
    /**
     * null
     */
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Column(name = "PLAT_TRACE")
    private Integer platTrace;

    /**
     * null
     */
    @Id
    @Column(name = "MSG_ID")
    private String msgId;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Integer platTime;

    /**
     * null
     */
    @Column(name = "CHECK_TYPE")
    private String checkType;

    /**
     * null
     */
    @Column(name = "MIVS_STS")
    private String mivsSts;

    /**
     * null
     */
    @Column(name = "CRE_DT_TM")
    private String creDtTm;

    /**
     * null
     */
    @Column(name = "INSTG_DRCT_PTY")
    private String instgDrctPty;

    /**
     * null
     */
    @Column(name = "INSTG_PTY")
    private String instgPty;

    /**
     * null
     */
    @Column(name = "INSTD_DRCT_PTY")
    private String instdDrctPty;

    /**
     * null
     */
    @Column(name = "INSTD_PTY")
    private String instdPty;

    /**
     * null
     */
    @Column(name = "CUR_SYS_DT")
    private String curSysDt;

    /**
     * null
     */
    @Column(name = "NXT_SYS_DT")
    private String nxtSysDt;

    /**
     * null
     */
    @Id
    @Column(name = "SYS_IND")
    private String sysInd;

    /**
     * null
     */
    @Column(name = "SVC_IND")
    private String svcInd;

    /**
     * null
     */
    @Column(name = "NXT_SYS_OP_TM")
    private String nxtSysOpTm;

    /**
     * null
     */
    @Column(name = "NXT_SYS_CL_TM")
    private String nxtSysClTm;

    /**
     * null
     */
    @Column(name = "REMARK1")
    private String remark1;

    /**
     * null
     */
    @Column(name = "REMARK2")
    private String remark2;

    /**
     * null
     */
    @Column(name = "REMARK3")
    private String remark3;

    /**
     * null
     * @return PLAT_DATE null
     */
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Integer getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Integer platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return MSG_ID null
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * null
     * @param msgId null
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Integer getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Integer platTime) {
        this.platTime = platTime;
    }

    /**
     * null
     * @return CHECK_TYPE null
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * null
     * @param checkType null
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    /**
     * null
     * @return MIVS_STS null
     */
    public String getMivsSts() {
        return mivsSts;
    }

    /**
     * null
     * @param mivsSts null
     */
    public void setMivsSts(String mivsSts) {
        this.mivsSts = mivsSts;
    }

    /**
     * null
     * @return CRE_DT_TM null
     */
    public String getCreDtTm() {
        return creDtTm;
    }

    /**
     * null
     * @param creDtTm null
     */
    public void setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
    }

    /**
     * null
     * @return INSTG_DRCT_PTY null
     */
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    /**
     * null
     * @param instgDrctPty null
     */
    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    /**
     * null
     * @return INSTG_PTY null
     */
    public String getInstgPty() {
        return instgPty;
    }

    /**
     * null
     * @param instgPty null
     */
    public void setInstgPty(String instgPty) {
        this.instgPty = instgPty;
    }

    /**
     * null
     * @return INSTD_DRCT_PTY null
     */
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    /**
     * null
     * @param instdDrctPty null
     */
    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    /**
     * null
     * @return INSTD_PTY null
     */
    public String getInstdPty() {
        return instdPty;
    }

    /**
     * null
     * @param instdPty null
     */
    public void setInstdPty(String instdPty) {
        this.instdPty = instdPty;
    }

    /**
     * null
     * @return CUR_SYS_DT null
     */
    public String getCurSysDt() {
        return curSysDt;
    }

    /**
     * null
     * @param curSysDt null
     */
    public void setCurSysDt(String curSysDt) {
        this.curSysDt = curSysDt;
    }

    /**
     * null
     * @return NXT_SYS_DT null
     */
    public String getNxtSysDt() {
        return nxtSysDt;
    }

    /**
     * null
     * @param nxtSysDt null
     */
    public void setNxtSysDt(String nxtSysDt) {
        this.nxtSysDt = nxtSysDt;
    }

    /**
     * null
     * @return SYS_IND null
     */
    public String getSysInd() {
        return sysInd;
    }

    /**
     * null
     * @param sysInd null
     */
    public void setSysInd(String sysInd) {
        this.sysInd = sysInd;
    }

    /**
     * null
     * @return SVC_IND null
     */
    public String getSvcInd() {
        return svcInd;
    }

    /**
     * null
     * @param svcInd null
     */
    public void setSvcInd(String svcInd) {
        this.svcInd = svcInd;
    }

    /**
     * null
     * @return NXT_SYS_OP_TM null
     */
    public String getNxtSysOpTm() {
        return nxtSysOpTm;
    }

    /**
     * null
     * @param nxtSysOpTm null
     */
    public void setNxtSysOpTm(String nxtSysOpTm) {
        this.nxtSysOpTm = nxtSysOpTm;
    }

    /**
     * null
     * @return NXT_SYS_CL_TM null
     */
    public String getNxtSysClTm() {
        return nxtSysClTm;
    }

    /**
     * null
     * @param nxtSysClTm null
     */
    public void setNxtSysClTm(String nxtSysClTm) {
        this.nxtSysClTm = nxtSysClTm;
    }

    /**
     * null
     * @return REMARK1 null
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * null
     * @param remark1 null
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    /**
     * null
     * @return REMARK2 null
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * null
     * @param remark2 null
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    /**
     * null
     * @return REMARK3 null
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * null
     * @param remark3 null
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}