package com.fxbank.tpp.mivs.entity.RegVrfctn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mivs_regvrfctn_fdbk")
public class MivsRegvrfctnFdbkEntity {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACE")
    private Integer platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Integer platTime;

    /**
     * null
     */
    @Column(name = "SYSTEM_ID")
    private String systemId;

    /**
     * null
     */
    @Column(name = "TRAN_DATE")
    private String tranDate;

    /**
     * null
     */
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     * null
     */
    @Column(name = "TRAN_TIME")
    private String tranTime;

    /**
     * null
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * null
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * null
     */
    @Column(name = "MIVS_STS")
    private String mivsSts;

    /**
     * null
     */
    @Column(name = "MSG_ID")
    private String msgId;

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
    @Column(name = "DRCT_PTY_NM")
    private String drctPtyNm;

    /**
     * null
     */
    @Column(name = "INSTG_PTY")
    private String instgPty;

    /**
     * null
     */
    @Column(name = "PTY_NM")
    private String ptyNm;

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
    @Column(name = "RCV_MSG_ID")
    private String rcvMsgId;

    /**
     * null
     */
    @Column(name = "RCV_CRE_DT_TM")
    private String rcvCreDtTm;

    /**
     * null
     */
    @Column(name = "SYS_IND")
    private String sysInd;

    /**
     * null
     */
    @Column(name = "ORIG_DLV_MSGID")
    private String origDlvMsgid;

    /**
     * null
     */
    @Column(name = "ORIG_RCV_MSGID")
    private String origRcvMsgid;

    /**
     * null
     */
    @Column(name = "UNI_SOC_CDT_CD")
    private String uniSocCdtCd;

    /**
     * null
     */
    @Column(name = "ENT_NM")
    private String entNm;

    /**
     * null
     */
    @Column(name = "NM_OF_LGL_PRSN")
    private String nmOfLglPrsn;

    /**
     * null
     */
    @Column(name = "ID_OF_LGL_PRSN")
    private String idOfLglPrsn;

    /**
     * null
     */
    @Column(name = "TRA_NM")
    private String traNm;

    /**
     * null
     */
    @Column(name = "NM")
    private String nm;

    /**
     * null
     */
    @Column(name = "ID")
    private String id;

    /**
     * null
     */
    @Column(name = "RSLT")
    private String rslt;

    /**
     * null
     */
    @Column(name = "DATA_RESRC_DT")
    private String dataResrcDt;

    /**
     * null
     */
    @Column(name = "CNTT")
    private String cntt;

    /**
     * null
     */
    @Column(name = "CONTACT_NM")
    private String contactNm;

    /**
     * null
     */
    @Column(name = "CONTACT_NB")
    private String contactNb;

    /**
     * null
     */
    @Column(name = "PROC_STS")
    private String procSts;

    /**
     * null
     */
    @Column(name = "PROC_CD")
    private String procCd;

    /**
     * null
     */
    @Column(name = "PTY_ID")
    private String ptyId;

    /**
     * null
     */
    @Column(name = "PTY_PRC_CD")
    private String ptyPrcCd;

    /**
     * null
     */
    @Column(name = "RJCT_INF")
    private String rjctInf;

    /**
     * null
     */
    @Column(name = "PRC_DT")
    private String prcDt;

    /**
     * null
     */
    @Column(name = "NETG_RND")
    private String netgRnd;

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
     */
    @Column(name = "BAS_INFO")
    private String basInfo;

    /**
     * null
     */
    @Column(name = "COS_INFO")
    private String cosInfo;

    /**
     * null
     */
    @Column(name = "DIR_INFO")
    private String dirInfo;

    /**
     * null
     */
    @Column(name = "CHNG_INFO")
    private String chngInfo;

    /**
     * null
     */
    @Column(name = "ABN_INFO")
    private String abnInfo;

    /**
     * null
     */
    @Column(name = "ILL_INFO")
    private String illInfo;

    /**
     * null
     */
    @Column(name = "LIC_INFO")
    private String licInfo;

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
     * @return SYSTEM_ID null
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * null
     * @param systemId null
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * null
     * @return TRAN_DATE null
     */
    public String getTranDate() {
        return tranDate;
    }

    /**
     * null
     * @param tranDate null
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    /**
     * null
     * @return SEQ_NO null
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * null
     * @param seqNo null
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * null
     * @return TRAN_TIME null
     */
    public String getTranTime() {
        return tranTime;
    }

    /**
     * null
     * @param tranTime null
     */
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    /**
     * null
     * @return USER_ID null
     */
    public String getUserId() {
        return userId;
    }

    /**
     * null
     * @param userId null
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * null
     * @return BRANCH_ID null
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * null
     * @param branchId null
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
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
     * @return DRCT_PTY_NM null
     */
    public String getDrctPtyNm() {
        return drctPtyNm;
    }

    /**
     * null
     * @param drctPtyNm null
     */
    public void setDrctPtyNm(String drctPtyNm) {
        this.drctPtyNm = drctPtyNm;
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
     * @return PTY_NM null
     */
    public String getPtyNm() {
        return ptyNm;
    }

    /**
     * null
     * @param ptyNm null
     */
    public void setPtyNm(String ptyNm) {
        this.ptyNm = ptyNm;
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
     * @return RCV_MSG_ID null
     */
    public String getRcvMsgId() {
        return rcvMsgId;
    }

    /**
     * null
     * @param rcvMsgId null
     */
    public void setRcvMsgId(String rcvMsgId) {
        this.rcvMsgId = rcvMsgId;
    }

    /**
     * null
     * @return RCV_CRE_DT_TM null
     */
    public String getRcvCreDtTm() {
        return rcvCreDtTm;
    }

    /**
     * null
     * @param rcvCreDtTm null
     */
    public void setRcvCreDtTm(String rcvCreDtTm) {
        this.rcvCreDtTm = rcvCreDtTm;
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
     * @return ORIG_DLV_MSGID null
     */
    public String getOrigDlvMsgid() {
        return origDlvMsgid;
    }

    /**
     * null
     * @param origDlvMsgid null
     */
    public void setOrigDlvMsgid(String origDlvMsgid) {
        this.origDlvMsgid = origDlvMsgid;
    }

    /**
     * null
     * @return ORIG_RCV_MSGID null
     */
    public String getOrigRcvMsgid() {
        return origRcvMsgid;
    }

    /**
     * null
     * @param origRcvMsgid null
     */
    public void setOrigRcvMsgid(String origRcvMsgid) {
        this.origRcvMsgid = origRcvMsgid;
    }

    /**
     * null
     * @return UNI_SOC_CDT_CD null
     */
    public String getUniSocCdtCd() {
        return uniSocCdtCd;
    }

    /**
     * null
     * @param uniSocCdtCd null
     */
    public void setUniSocCdtCd(String uniSocCdtCd) {
        this.uniSocCdtCd = uniSocCdtCd;
    }

    /**
     * null
     * @return ENT_NM null
     */
    public String getEntNm() {
        return entNm;
    }

    /**
     * null
     * @param entNm null
     */
    public void setEntNm(String entNm) {
        this.entNm = entNm;
    }

    /**
     * null
     * @return NM_OF_LGL_PRSN null
     */
    public String getNmOfLglPrsn() {
        return nmOfLglPrsn;
    }

    /**
     * null
     * @param nmOfLglPrsn null
     */
    public void setNmOfLglPrsn(String nmOfLglPrsn) {
        this.nmOfLglPrsn = nmOfLglPrsn;
    }

    /**
     * null
     * @return ID_OF_LGL_PRSN null
     */
    public String getIdOfLglPrsn() {
        return idOfLglPrsn;
    }

    /**
     * null
     * @param idOfLglPrsn null
     */
    public void setIdOfLglPrsn(String idOfLglPrsn) {
        this.idOfLglPrsn = idOfLglPrsn;
    }

    /**
     * null
     * @return TRA_NM null
     */
    public String getTraNm() {
        return traNm;
    }

    /**
     * null
     * @param traNm null
     */
    public void setTraNm(String traNm) {
        this.traNm = traNm;
    }

    /**
     * null
     * @return NM null
     */
    public String getNm() {
        return nm;
    }

    /**
     * null
     * @param nm null
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * null
     * @return ID null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * null
     * @return RSLT null
     */
    public String getRslt() {
        return rslt;
    }

    /**
     * null
     * @param rslt null
     */
    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    /**
     * null
     * @return DATA_RESRC_DT null
     */
    public String getDataResrcDt() {
        return dataResrcDt;
    }

    /**
     * null
     * @param dataResrcDt null
     */
    public void setDataResrcDt(String dataResrcDt) {
        this.dataResrcDt = dataResrcDt;
    }

    /**
     * null
     * @return CNTT null
     */
    public String getCntt() {
        return cntt;
    }

    /**
     * null
     * @param cntt null
     */
    public void setCntt(String cntt) {
        this.cntt = cntt;
    }

    /**
     * null
     * @return CONTACT_NM null
     */
    public String getContactNm() {
        return contactNm;
    }

    /**
     * null
     * @param contactNm null
     */
    public void setContactNm(String contactNm) {
        this.contactNm = contactNm;
    }

    /**
     * null
     * @return CONTACT_NB null
     */
    public String getContactNb() {
        return contactNb;
    }

    /**
     * null
     * @param contactNb null
     */
    public void setContactNb(String contactNb) {
        this.contactNb = contactNb;
    }

    /**
     * null
     * @return PROC_STS null
     */
    public String getProcSts() {
        return procSts;
    }

    /**
     * null
     * @param procSts null
     */
    public void setProcSts(String procSts) {
        this.procSts = procSts;
    }

    /**
     * null
     * @return PROC_CD null
     */
    public String getProcCd() {
        return procCd;
    }

    /**
     * null
     * @param procCd null
     */
    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }

    /**
     * null
     * @return PTY_ID null
     */
    public String getPtyId() {
        return ptyId;
    }

    /**
     * null
     * @param ptyId null
     */
    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
    }

    /**
     * null
     * @return PTY_PRC_CD null
     */
    public String getPtyPrcCd() {
        return ptyPrcCd;
    }

    /**
     * null
     * @param ptyPrcCd null
     */
    public void setPtyPrcCd(String ptyPrcCd) {
        this.ptyPrcCd = ptyPrcCd;
    }

    /**
     * null
     * @return RJCT_INF null
     */
    public String getRjctInf() {
        return rjctInf;
    }

    /**
     * null
     * @param rjctInf null
     */
    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    /**
     * null
     * @return PRC_DT null
     */
    public String getPrcDt() {
        return prcDt;
    }

    /**
     * null
     * @param prcDt null
     */
    public void setPrcDt(String prcDt) {
        this.prcDt = prcDt;
    }

    /**
     * null
     * @return NETG_RND null
     */
    public String getNetgRnd() {
        return netgRnd;
    }

    /**
     * null
     * @param netgRnd null
     */
    public void setNetgRnd(String netgRnd) {
        this.netgRnd = netgRnd;
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

    /**
     * null
     * @return BAS_INFO null
     */
    public String getBasInfo() {
        return basInfo;
    }

    /**
     * null
     * @param basInfo null
     */
    public void setBasInfo(String basInfo) {
        this.basInfo = basInfo;
    }

    /**
     * null
     * @return COS_INFO null
     */
    public String getCosInfo() {
        return cosInfo;
    }

    /**
     * null
     * @param cosInfo null
     */
    public void setCosInfo(String cosInfo) {
        this.cosInfo = cosInfo;
    }

    /**
     * null
     * @return DIR_INFO null
     */
    public String getDirInfo() {
        return dirInfo;
    }

    /**
     * null
     * @param dirInfo null
     */
    public void setDirInfo(String dirInfo) {
        this.dirInfo = dirInfo;
    }

    /**
     * null
     * @return CHNG_INFO null
     */
    public String getChngInfo() {
        return chngInfo;
    }

    /**
     * null
     * @param chngInfo null
     */
    public void setChngInfo(String chngInfo) {
        this.chngInfo = chngInfo;
    }

    /**
     * null
     * @return ABN_INFO null
     */
    public String getAbnInfo() {
        return abnInfo;
    }

    /**
     * null
     * @param abnInfo null
     */
    public void setAbnInfo(String abnInfo) {
        this.abnInfo = abnInfo;
    }

    /**
     * null
     * @return ILL_INFO null
     */
    public String getIllInfo() {
        return illInfo;
    }

    /**
     * null
     * @param illInfo null
     */
    public void setIllInfo(String illInfo) {
        this.illInfo = illInfo;
    }

    /**
     * null
     * @return LIC_INFO null
     */
    public String getLicInfo() {
        return licInfo;
    }

    /**
     * null
     * @param licInfo null
     */
    public void setLicInfo(String licInfo) {
        this.licInfo = licInfo;
    }
}