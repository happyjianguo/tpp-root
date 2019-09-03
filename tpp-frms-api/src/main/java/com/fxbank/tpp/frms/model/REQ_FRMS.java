/**   
* @Title: REQ_FRMS.java 
* @Package com.fxbank.tpp.frms.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年8月23日 下午2:05:21 
* @version V1.0   
*/
package com.fxbank.tpp.frms.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: REQ_FRMS 
* @Description: 风险监控报文 
* @author YePuLiang
* @date 2019年8月23日 下午2:05:21 
*  
*/
public class REQ_FRMS extends ModelBase implements Serializable{

	private static final long serialVersionUID = -1822979191871266424L;

	public REQ_FRMS(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	/**
	 * 流水号
	 */
	@JSONField(name="frms_serial_id")
	private String serialId;
	
	/**
	 * 业务渠道
	 * B-手机，C-微信
	 */
	@JSONField(name="frms_biz_channel")
	private String bizChannel;
	
	/**
	 * 业务类型
	 * 002-二三类账户开户
	 */
	@JSONField(name="frms_biz_code")
	private String bizCode;
	
	/**
	 * 操作时间
	 */
	@JSONField(name="frms_oper_time")
	private String operTime;
	
	/**
	 * 操作金额
	 */
	@JSONField(name="frms_oper_amount")
	private Long operAmount;
	
	/**
	 * 操作金额
	 */
	@JSONField(name="frms_rec_acct")
	private String recAcct;
		
	/**
	 * 账户号（银行卡号）
	 */
	@JSONField(name="frms_card_no")
	private String cardNo;
	
	/**
	 * 由谁来实现短信通知      String  01-风控系统，02-业务系统
	 */
	@JSONField(name="frms_who_report")
	private String whoReport;
	
	/**
	 * 操作状态    01-成功，02-失败
	 */
	@JSONField(name="frms_oper_status")
	private String operStatus;
	
	/**
	 * 应答码  55-密码输错，51-余额不足，00-交易成功
	 */
	@JSONField(name="frms_resp_code")
	private String respCode;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getBizChannel() {
		return bizChannel;
	}

	public void setBizChannel(String bizChannel) {
		this.bizChannel = bizChannel;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public Long getOperAmount() {
		return operAmount;
	}

	public void setOperAmount(Long operAmount) {
		this.operAmount = operAmount;
	}

	public String getRecAcct() {
		return recAcct;
	}

	public void setRecAcct(String recAcct) {
		this.recAcct = recAcct;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getWhoReport() {
		return whoReport;
	}

	public void setWhoReport(String whoReport) {
		this.whoReport = whoReport;
	}

	
}
