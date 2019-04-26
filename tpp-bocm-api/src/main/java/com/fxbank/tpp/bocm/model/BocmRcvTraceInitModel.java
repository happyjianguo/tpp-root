/**   
* @Title: BocmRcvTraceInitModel.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年4月16日 上午8:31:45 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmRcvTraceInitModel 
* @Description: 接收日志流水表登记模型
* @author YePuLiang
* @date 2019年4月16日 上午8:31:45 
*  
*/
public class BocmRcvTraceInitModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -2295106283747182412L;
	
	public BocmRcvTraceInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@NotNull(message = "source_type交易渠道不能为空")
	@Size(max = 20,message = "source_type交易渠道最大长度是20")
	private String sourceType; //交易渠道
	
	@NotNull(message = "tx_branch交易机构不能为空")
	@Size(max = 20,message = "tx_branch交易机构最大长度是20")
	private String txBranch; //交易机构
	
	@NotNull(message = "tx_ind现转标志不能为空")
	@Size(max = 10,message = "tx_ind现转标志最大长度是10")
	private String txInd; //现转标志
	
	@Size(max = 10,message = "tx_code交易代码最大长度是10")
	private String txCode; //交易代码
	
	@NotNull(message = "dc_flag通存通兑标志不能为空")
	@Size(max = 10,message = "dc_flag通存通兑标志最大长度是10")
	private String dcFlag; //通存通兑标志
	
	@NotNull(message = "tx_amt交易金额不能为空")
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
	private String BocmBranch; //村镇机构
	
	@NotNull(message = "host_state核心记账状态不能为空")
	@Size(max = 10,message = "host_state核心记账状态最大长度是10")
	private String hostState; //核心记账状态
	
    private Integer hostDate;//核心日期

    private String hostTraceno;//核心流水
    
    private String retCode;//核心反馈响应码
    
    private String retMsg;//核心反馈响应信息

	@NotNull(message = "town_state村镇记账状态不能为空")
	@Size(max = 10,message = "town_state村镇记账状态最大长度是10")
	private String bocmState; //交行记账状态
	
	@NotNull(message = "tx_tel交易柜员不能为空")
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
	
	private String bocmDate;//村镇日期
	
	private String bocmTraceNo;//村镇流水
	
	private String bocmFlag;//村镇标志
	
	private String checkFlag;//对账标志
	

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
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
	
	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
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
		return BocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		BocmBranch = bocmBranch;
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

	public String getBocmFlag() {
		return bocmFlag;
	}

	public void setBocmFlag(String bocmFlag) {
		this.bocmFlag = bocmFlag;
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

}
