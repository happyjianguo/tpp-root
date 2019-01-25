package com.fxbank.tpp.tcex.model;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
/**
 * 对账补录来账model
 * @author liye
 *
 */
public class RcvTraceRepModel extends ModelBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4232794744853754877L;

	public RcvTraceRepModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		// TODO Auto-generated constructor stub
	}
	
	@Size(max = 20,message = "source_type交易渠道最大长度是20")
	private String sourceType; //交易渠道
	
	@Size(max = 20,message = "tx_branch交易机构最大长度是20")
	private String txBranch; //交易机构
	
	@Size(max = 10,message = "tx_ind现转标志最大长度是10")
	private String txInd; //现转标志
	
	@Size(max = 10,message = "dc_flag通存通兑标志最大长度是10")
	private String dcFlag; //通存通兑标志
	
	@Digits(integer = 14, fraction = 2,message = "tx_amt交易金额格式不正确")
	private String txAmt;//交易金额
	
	@Size(max = 50,message = "payer_acno付款人账户最大长度是50")
	private String payerAcno; //付款人账户
	
	@Size(max = 100,message = "payer_name付款人户名最大长度是100")
	private String payerName; //付款人户名
	
	@Size(max = 50,message = "payee_acno收款人账户最大长度是50")
	private String payeeAcno; //收款人账户
	
	@Size(max = 100,message = "payee_name收款人户名最大长度是100")
	private String payeeName; //收款人户名
	
	@Size(max = 20,message = "town_branch村镇机构最大长度是20")
	private String townBranch; //村镇机构
	
	@Size(max = 10,message = "host_state核心记账状态最大长度是10")
	private String hostState; //核心记账状态
	
//	@NotNull(message = "town_state村镇记账状态不能为空")
	@Size(max = 10,message = "town_state村镇记账状态最大长度是10")
	private String townState; //村镇记账状态
	
	@Size(max = 30,message = "tx_tel交易柜员最大长度是30")
	private String txTel; //交易柜员
	
	@Size(max = 30,message = "chk_tel复核员最大长度是30")
	private String chkTel; //复核员
	
	@Size(max = 30,message = "auth_tel授权员最大长度是30")
	private String authTel; //授权员
	
	@Size(max = 10,message = "print打印次数最大长度是10")
	private String print; //打印次数
	
	@Size(max = 255,message = "info摘要最大长度是255")
	private String info; //摘要
	
	private String townDate;//村镇日期
	
	private String townTraceNo;//村镇流水
	
	private String townFlag;//村镇标志
	
	private String ret_code; //核心反馈响应码
	
	private String ret_msg; //核心反馈响应信息
	
	private String town_flag; //村镇标志
	
	private Integer hostDate; //核心日期

	private String hostTraceno; //核心流水
	
	private String checkFlag; //对账标志

	



	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
	}

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getTown_flag() {
		return town_flag;
	}

	public void setTown_flag(String town_flag) {
		this.town_flag = town_flag;
	}

	public String getTownFlag() {
		return townFlag;
	}

	public void setTownFlag(String townFlag) {
		this.townFlag = townFlag;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTxBranch() {
		return txBranch;
	}

	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}

	public String getTxInd() {
		return txInd;
	}

	public void setTxInd(String txInd) {
		this.txInd = txInd;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
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

	public String getTownBranch() {
		return townBranch;
	}

	public void setTownBranch(String townBranch) {
		this.townBranch = townBranch;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
	}

	public String getTxTel() {
		return txTel;
	}

	public void setTxTel(String txTel) {
		this.txTel = txTel;
	}

	public String getChkTel() {
		return chkTel;
	}

	public void setChkTel(String chkTel) {
		this.chkTel = chkTel;
	}

	public String getAuthTel() {
		return authTel;
	}

	public void setAuthTel(String authTel) {
		this.authTel = authTel;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTownState() {
		return townState;
	}

	public void setTownState(String townState) {
		this.townState = townState;
	}

	public String getTownDate() {
		return townDate;
	}

	public void setTownDate(String townDate) {
		this.townDate = townDate;
	}

	public String getTownTraceNo() {
		return townTraceNo;
	}

	public void setTownTraceNo(String townTraceNo) {
		this.townTraceNo = townTraceNo;
	}
	


}
