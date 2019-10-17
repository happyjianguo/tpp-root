package com.fxbank.tpp.bocm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmRcvTraceRepModel 
* @Description: 来账补账模型
* @author YePuLiang
* @date 2019年5月7日 上午11:06:13 
*  
*/
public class BocmRcvTraceRepModel extends ModelBase implements Serializable {

	private static final long serialVersionUID = -4453764486238172587L;

	public BocmRcvTraceRepModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	//交易渠道
	private String sourceType; 
	//交易机构
	private String txBranch; 
	//现转标志
	private String txInd; 
	//通存通兑标志
	private String dcFlag; 
	//交易金额
	private String txAmt;
	//付款人账户
	private String payerAcno; 
	//付款人户名
	private String payerName; 
	//收款人账户
	private String payeeAcno; 
	//收款人户名
	private String payeeName; 
	//村镇机构
	private String bocmBranch; 
	//核心记账状态
	private String hostState; 
	//村镇记账状态
	private String bocmState; 
	//交易柜员
	private String txTel; 
	//复核员
	private String chkTel; 
	//授权员
	private String authTel; 
	//打印次数
	private String print; 
	//摘要
	private String info; 
	//交行日期
	private String bocmDate;
	//交行流水
	private String bocmTraceNo;
	//核心反馈响应码
	private String ret_code; 
	//核心反馈响应信息
	private String ret_msg; 
	//核心日期
	private Integer hostDate; 
	//核心流水
	private String hostTraceno; 
	//对账标志
	private String checkFlag; 

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

	public String getBocmBranch() {
		return bocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		this.bocmBranch = bocmBranch;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public String getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(String bocmDate) {
		this.bocmDate = bocmDate;
	}

	public String getBocmTraceNo() {
		return bocmTraceNo;
	}

	public void setBocmTraceNo(String bocmTraceNo) {
		this.bocmTraceNo = bocmTraceNo;
	}



	

}
