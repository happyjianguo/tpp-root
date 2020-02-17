package com.fxbank.tpp.beps.entity;

import java.math.BigDecimal;

import javax.persistence.Column;

public class CcmsFreeInfo {
    /**
     * null
     */
    @Column(name = "SYSCODE")
    private String syscode;

    /**
     * null
     */
    @Column(name = "BRANCHNO")
    private String branchno;

    /**
     * null
     */
    @Column(name = "PLATDATE")
    private Integer platdate;

    /**
     * null
     */
    @Column(name = "PLATTRACE")
    private Integer plattrace;

    /**
     * null
     */
    @Column(name = "MSGID")
    private String msgid;

    /**
     * null
     */
    @Column(name = "DTLSND_BKNO")
    private String dtlsndBkno;

    /**
     * null
     */
    @Column(name = "CMTNO")
    private String cmtno;

    /**
     * null
     */
    @Column(name = "TSSNO")
    private Integer tssno;

    /**
     * null
     */
    @Column(name = "SNDDATE")
    private Integer snddate;

    /**
     * null
     */
    @Column(name = "SNDCCPC")
    private String sndccpc;

    /**
     * null
     */
    @Column(name = "SNDSABKNO")
    private String sndsabkno;

    /**
     * null
     */
    @Column(name = "SNDBANKNO")
    private String sndbankno;

    /**
     * null
     */
    @Column(name = "RCVCCPC")
    private String rcvccpc;

    /**
     * null
     */
    @Column(name = "RCVSABKNO")
    private String rcvsabkno;

    /**
     * null
     */
    @Column(name = "RCVBANKNO")
    private String rcvbankno;

    /**
     * null
     */
    @Column(name = "BUSITYPE")
    private String busitype;

    /**
     * null
     */
    @Column(name = "BUSIKIND")
    private String busikind;

    /**
     * null
     */
    @Column(name = "INFOTYPE")
    private String infotype;

    /**
     * null
     */
    @Column(name = "PROCSTATE")
    private String procstate;

    /**
     * null
     */
    @Column(name = "INFOSOURCE")
    private String infosource;

    /**
     * null
     */
    @Column(name = "INFOTITLE")
    private String infotitle;

    /**
     * null
     */
    @Column(name = "INFORMATION")
    private String information;

    /**
     * null
     */
    @Column(name = "ATTACHLEN")
    private Integer attachlen;

    /**
     * null
     */
    @Column(name = "ATTACHNAME")
    private String attachname;

    /**
     * null
     */
    @Column(name = "AGENCY")
    private String agency;

    /**
     * null
     */
    @Column(name = "REPLYINFO")
    private String replyinfo;

    /**
     * null
     */
    @Column(name = "NPCSTS")
    private String npcsts;

    /**
     * null
     */
    @Column(name = "NPCCODE")
    private String npccode;

    /**
     * null
     */
    @Column(name = "NPCMSG")
    private String npcmsg;

    /**
     * null
     */
    @Column(name = "NPCDATE")
    private Integer npcdate;

    /**
     * null
     */
    @Column(name = "PUTUSER")
    private String putuser;

    /**
     * null
     */
    @Column(name = "FRCHNL")
    private String frchnl;

    /**
     * null
     */
    @Column(name = "IFTOWN")
    private String iftown;

    /**
     * null
     */
    @Column(name = "PRINTNO")
    private Integer printno;
    
    public Integer getPlatdate() {
		return platdate;
	}

	public void setPlatdate(Integer platdate) {
		this.platdate = platdate;
	}

	public Integer getPlattrace() {
		return plattrace;
	}

	public void setPlattrace(Integer plattrace) {
		this.plattrace = plattrace;
	}

	public Integer getTssno() {
		return tssno;
	}

	public void setTssno(Integer tssno) {
		this.tssno = tssno;
	}

	public Integer getSnddate() {
		return snddate;
	}

	public void setSnddate(Integer snddate) {
		this.snddate = snddate;
	}

	public Integer getAttachlen() {
		return attachlen;
	}

	public void setAttachlen(Integer attachlen) {
		this.attachlen = attachlen;
	}

	public Integer getNpcdate() {
		return npcdate;
	}

	public void setNpcdate(Integer npcdate) {
		this.npcdate = npcdate;
	}

	public Integer getPrintno() {
		return printno;
	}

	public void setPrintno(Integer printno) {
		this.printno = printno;
	}

	/**
     * null
     * @return SYSCODE null
     */
    public String getSyscode() {
        return syscode;
    }

    /**
     * null
     * @param syscode null
     */
    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    /**
     * null
     * @return BRANCHNO null
     */
    public String getBranchno() {
        return branchno;
    }

    /**
     * null
     * @param branchno null
     */
    public void setBranchno(String branchno) {
        this.branchno = branchno;
    }

    /**
     * null
     * @return MSGID null
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * null
     * @param msgid null
     */
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    /**
     * null
     * @return DTLSND_BKNO null
     */
    public String getDtlsndBkno() {
        return dtlsndBkno;
    }

    /**
     * null
     * @param dtlsndBkno null
     */
    public void setDtlsndBkno(String dtlsndBkno) {
        this.dtlsndBkno = dtlsndBkno;
    }

    /**
     * null
     * @return CMTNO null
     */
    public String getCmtno() {
        return cmtno;
    }

    /**
     * null
     * @param cmtno null
     */
    public void setCmtno(String cmtno) {
        this.cmtno = cmtno;
    }

    /**
     * null
     * @return SNDCCPC null
     */
    public String getSndccpc() {
        return sndccpc;
    }

    /**
     * null
     * @param sndccpc null
     */
    public void setSndccpc(String sndccpc) {
        this.sndccpc = sndccpc;
    }

    /**
     * null
     * @return SNDSABKNO null
     */
    public String getSndsabkno() {
        return sndsabkno;
    }

    /**
     * null
     * @param sndsabkno null
     */
    public void setSndsabkno(String sndsabkno) {
        this.sndsabkno = sndsabkno;
    }

    /**
     * null
     * @return SNDBANKNO null
     */
    public String getSndbankno() {
        return sndbankno;
    }

    /**
     * null
     * @param sndbankno null
     */
    public void setSndbankno(String sndbankno) {
        this.sndbankno = sndbankno;
    }

    /**
     * null
     * @return RCVCCPC null
     */
    public String getRcvccpc() {
        return rcvccpc;
    }

    /**
     * null
     * @param rcvccpc null
     */
    public void setRcvccpc(String rcvccpc) {
        this.rcvccpc = rcvccpc;
    }

    /**
     * null
     * @return RCVSABKNO null
     */
    public String getRcvsabkno() {
        return rcvsabkno;
    }

    /**
     * null
     * @param rcvsabkno null
     */
    public void setRcvsabkno(String rcvsabkno) {
        this.rcvsabkno = rcvsabkno;
    }

    /**
     * null
     * @return RCVBANKNO null
     */
    public String getRcvbankno() {
        return rcvbankno;
    }

    /**
     * null
     * @param rcvbankno null
     */
    public void setRcvbankno(String rcvbankno) {
        this.rcvbankno = rcvbankno;
    }

    /**
     * null
     * @return BUSITYPE null
     */
    public String getBusitype() {
        return busitype;
    }

    /**
     * null
     * @param busitype null
     */
    public void setBusitype(String busitype) {
        this.busitype = busitype;
    }

    /**
     * null
     * @return BUSIKIND null
     */
    public String getBusikind() {
        return busikind;
    }

    /**
     * null
     * @param busikind null
     */
    public void setBusikind(String busikind) {
        this.busikind = busikind;
    }

    /**
     * null
     * @return INFOTYPE null
     */
    public String getInfotype() {
        return infotype;
    }

    /**
     * null
     * @param infotype null
     */
    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    /**
     * null
     * @return PROCSTATE null
     */
    public String getProcstate() {
        return procstate;
    }

    /**
     * null
     * @param procstate null
     */
    public void setProcstate(String procstate) {
        this.procstate = procstate;
    }

    /**
     * null
     * @return INFOSOURCE null
     */
    public String getInfosource() {
        return infosource;
    }

    /**
     * null
     * @param infosource null
     */
    public void setInfosource(String infosource) {
        this.infosource = infosource;
    }

    /**
     * null
     * @return INFOTITLE null
     */
    public String getInfotitle() {
        return infotitle;
    }

    /**
     * null
     * @param infotitle null
     */
    public void setInfotitle(String infotitle) {
        this.infotitle = infotitle;
    }

    /**
     * null
     * @return INFORMATION null
     */
    public String getInformation() {
        return information;
    }

    /**
     * null
     * @param information null
     */
    public void setInformation(String information) {
        this.information = information;
    }


    /**
     * null
     * @return ATTACHNAME null
     */
    public String getAttachname() {
        return attachname;
    }

    /**
     * null
     * @param attachname null
     */
    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    /**
     * null
     * @return AGENCY null
     */
    public String getAgency() {
        return agency;
    }

    /**
     * null
     * @param agency null
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }

    /**
     * null
     * @return REPLYINFO null
     */
    public String getReplyinfo() {
        return replyinfo;
    }

    /**
     * null
     * @param replyinfo null
     */
    public void setReplyinfo(String replyinfo) {
        this.replyinfo = replyinfo;
    }

    /**
     * null
     * @return NPCSTS null
     */
    public String getNpcsts() {
        return npcsts;
    }

    /**
     * null
     * @param npcsts null
     */
    public void setNpcsts(String npcsts) {
        this.npcsts = npcsts;
    }

    /**
     * null
     * @return NPCCODE null
     */
    public String getNpccode() {
        return npccode;
    }

    /**
     * null
     * @param npccode null
     */
    public void setNpccode(String npccode) {
        this.npccode = npccode;
    }

    /**
     * null
     * @return NPCMSG null
     */
    public String getNpcmsg() {
        return npcmsg;
    }

    /**
     * null
     * @param npcmsg null
     */
    public void setNpcmsg(String npcmsg) {
        this.npcmsg = npcmsg;
    }


    /**
     * null
     * @return PUTUSER null
     */
    public String getPutuser() {
        return putuser;
    }

    /**
     * null
     * @param putuser null
     */
    public void setPutuser(String putuser) {
        this.putuser = putuser;
    }

    /**
     * null
     * @return FRCHNL null
     */
    public String getFrchnl() {
        return frchnl;
    }

    /**
     * null
     * @param frchnl null
     */
    public void setFrchnl(String frchnl) {
        this.frchnl = frchnl;
    }

    /**
     * null
     * @return IFTOWN null
     */
    public String getIftown() {
        return iftown;
    }

    /**
     * null
     * @param iftown null
     */
    public void setIftown(String iftown) {
        this.iftown = iftown;
    }

}