package com.fxbank.tpp.tcex.model;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: RcvTraceUpdModel 
* @Description: 接收日志流水表更新模型
* @author Duzhenduo
* @date 2018年12月11日 下午1:36:48 
*  
*/
public class RcvTraceUpdModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public RcvTraceUpdModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	private String townBranch;//村镇机构 
	
    private Integer hostDate;//核心日期
    
    private String hostState;//核心记账状态

    private String hostTraceno;//核心流水

    private Integer townDate;//村镇日期

    private String townTraceno;//村镇流水

    private String checkFlag;//对账标志

    private String townState;//村镇记账状态

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
	}

	public Integer getTownDate() {
		return townDate;
	}

	public void setTownDate(Integer townDate) {
		this.townDate = townDate;
	}

	public String getTownTraceno() {
		return townTraceno;
	}

	public void setTownTraceno(String townTraceno) {
		this.townTraceno = townTraceno;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getTownState() {
		return townState;
	}

	public void setTownState(String townState) {
		this.townState = townState;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
	}

	public String getTownBranch() {
		return townBranch;
	}

	public void setTownBranch(String townBranch) {
		this.townBranch = townBranch;
	}
    
}
