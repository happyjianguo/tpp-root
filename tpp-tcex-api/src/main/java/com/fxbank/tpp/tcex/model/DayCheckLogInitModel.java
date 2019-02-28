package com.fxbank.tpp.tcex.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: DayCheckLogInitModel 
* @Description: 对账登记
* @author Duzhenduo
* @date 2019年1月31日 上午10:09:47 
*  
*/
public class DayCheckLogInitModel extends ModelBase implements Serializable {

	public DayCheckLogInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8850394891802837L;
	
	/**
     * 渠道日期
     */
	@NotNull(message = "platDate渠道日期不能为空")
    private Integer platDate;

    /**
     * 渠道流水
     */
	@NotNull(message = "platTrace渠道流水不能为空")
    private Integer platTrace;

    /**
     * 清算日期
     */
    private Integer settleDate;

    /**
     * 清算机构
     */
    private String settleBranch;

    /**
     * 核心交易日期
     */
    private Integer hostDate;

    /**
     * 核心流水号
     */
    private String hostTraceno;

    /**
     * 交易币种
     */
    private String ccy;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 交易账户
     */
    private String accountno;

    /**
     * 冲正标志
     */
    private String reversal;

    /**
     * 交易状态；00-成功 02-冲正
     */
    private String txStatus;
    
    /**
     * 来往账标识
     */
    private String direction;

    
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

	public Integer getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Integer settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleBranch() {
		return settleBranch;
	}

	public void setSettleBranch(String settleBranch) {
		this.settleBranch = settleBranch;
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

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getReversal() {
		return reversal;
	}

	public void setReversal(String reversal) {
		this.reversal = reversal;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
    
    

}
