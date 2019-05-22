/**   
* @Title: BocmAcctCheckErrModel.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 上午9:12:14 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmAcctCheckErrModel 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月7日 上午9:12:14 
*  
*/
public class BocmAcctCheckErrModel extends ModelBase implements Serializable {

	private static final long serialVersionUID = 8412341436449310655L;
	
	public BocmAcctCheckErrModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
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
     * 调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时
     */
    private String preHostState;

    /**
     * 调整后核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时
     */
    private String reHostState;

    /**
     * 通存通兑标志；0通存、1通兑
     */
    private String dcFlag;

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    private String checkFlag;

    /**
     * 来往帐标志，I：来账，O：往帐
     */
    private String direction;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 付款人账户
     */
    private String payerAcno;

    /**
     * 付款人户名
     */
    private String payerName;

    /**
     * 收款人账户
     */
    private String payeeAcno;

    /**
     * 收款人户名
     */
    private String payeeName;

    /**
     * 描述
     */
    private String msg;

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

	public String getPreHostState() {
		return preHostState;
	}

	public void setPreHostState(String preHostState) {
		this.preHostState = preHostState;
	}

	public String getReHostState() {
		return reHostState;
	}

	public void setReHostState(String reHostState) {
		this.reHostState = reHostState;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getPayerAcno() {
		return payerAcno;
	}

	public void setPayerAcno(String payerAcno) {
		this.payerAcno = payerAcno;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeAcno() {
		return payeeAcno;
	}

	public void setPayeeAcno(String payeeAcno) {
		this.payeeAcno = payeeAcno;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
