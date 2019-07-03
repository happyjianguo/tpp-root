package com.fxbank.tpp.bocm.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class BocmChkStatus {
    /**
     * null
     */
    @Id
    @Column(name = "CHK_DATE")
    private Integer chkDate;

    /**
     * null
     */
    @Id
    @Column(name = "HOST_STATUS")
    private Integer hostStatus;

       /**
     * null
     */
    @Id
    @Column(name = "BOCM_STATUS")
    private Integer bocmStatus;
	
	/**
     * null
     */
    @Id
    @Column(name = "PLAT_STATUS")
    private Integer platStatus;

	public Integer getChkDate() {
		return chkDate;
	}

	public void setChkDate(Integer chkDate) {
		this.chkDate = chkDate;
	}

	public Integer getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(Integer hostStatus) {
		this.hostStatus = hostStatus;
	}

	public Integer getBocmStatus() {
		return bocmStatus;
	}

	public void setBocmStatus(Integer bocmStatus) {
		this.bocmStatus = bocmStatus;
	}

	public Integer getPlatStatus() {
		return platStatus;
	}

	public void setPlatStatus(Integer platStatus) {
		this.platStatus = platStatus;
	}



}