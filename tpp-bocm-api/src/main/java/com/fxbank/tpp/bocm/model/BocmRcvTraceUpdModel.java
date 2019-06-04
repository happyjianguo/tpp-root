/**   
* @Title: BocmRcvTraceUpdModel.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: 接收日志流水表更新模型
* @author YePuLiang
* @date 2019年4月16日 上午8:32:07 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmRcvTraceUpdModel 
* @Description: 接收日志流水表更新模型
* @author YePuLiang
* @date 2019年4月16日 上午8:32:07 
*  
*/
public class BocmRcvTraceUpdModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = 4753680692238800720L;

	public BocmRcvTraceUpdModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private String bocmBranch;//交行机构 
	
    private Integer hostDate;//核心日期
    
    private String hostState;//核心记账状态

    private String hostTraceno;//核心流水

    private Integer bocmDate;//交行日期
    
    private Integer bocmTime;//交行时间

    private String bocmTraceno;//交行流水
    
    /**
     * 账户余额
     */
    private BigDecimal actBal;

    private String checkFlag;//对账标志

    private String bocmState;//交行记账状态
    
    private String retCode;//核心反馈响应码
    
    private String retMsg;//核心反馈响应信息
    
    private String hostBranch;//核心记账机构

	public String getHostBranch() {
		return hostBranch;
	}

	public void setHostBranch(String hostBranch) {
		this.hostBranch = hostBranch;
	}

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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getBocmBranch() {
		return bocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		this.bocmBranch = bocmBranch;
	}

	public Integer getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(Integer bocmDate) {
		this.bocmDate = bocmDate;
	}

	public String getBocmTraceno() {
		return bocmTraceno;
	}

	public void setBocmTraceno(String bocmTraceno) {
		this.bocmTraceno = bocmTraceno;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public Integer getBocmTime() {
		return bocmTime;
	}

	public void setBocmTime(Integer bocmTime) {
		this.bocmTime = bocmTime;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}
	
	
}
