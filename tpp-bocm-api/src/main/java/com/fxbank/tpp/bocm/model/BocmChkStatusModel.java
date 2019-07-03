package com.fxbank.tpp.bocm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmChkStatusModel 
* @Description: 对账状态Model
* @author YePuLiang
* @date 2019年7月2日 上午9:19:10 
*  
*/
public class BocmChkStatusModel implements Serializable{

	private static final long serialVersionUID = -1804273320664907651L;
	
	//对账日期
    private Integer chkDate;
    //与核心对账状态
    private Integer hostStatus;
    //与交行对账状态
    private Integer bocmStatus;
    //我行对账状态
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
