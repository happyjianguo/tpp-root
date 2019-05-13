package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: SndTraceInitModel 
* @Description: 发送流水表登记模型 
* @author YePuLiang
* @date 2019年04月15日 下午1:38:29 
*  
*/
public class BocmSndTraceInitModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -7927287552811072315L;

	public BocmSndTraceInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
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
	
    /**
     * 手续费收取方式
     */
    private String feeFlag;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 发起行人行行号
     */
    private String sndBankno;
    /**
     * 接收行人行行号
     */
    private String rcvBankno;
    /**
     * 付款人开户行
     */
    private String payerBank;
    /**
     * 收款人开户行
     */
    private String payerActtp;
    /**
     * 收款人开户行
     */
    private String payeeBank;
    /**
     * 收款人开户行
     */
    private String payeeActtp;
	
	@Size(max = 50,message = "payer_acno付款人账户最大长度是50")
	private String payerAcno; //付款人账户
	
	@Size(max = 100,message = "payer_name付款人户名最大长度是100")
	private String payerName; //付款人户名
	
	@Size(max = 50,message = "payee_acno收款人账户最大长度是50")
	private String payeeAcno; //收款人账户
	
	@Size(max = 100,message = "payee_name收款人户名最大长度是100")
	private String payeeName; //收款人户名
	
	@Size(max = 20,message = "bocm_branch村镇机构最大长度是20")
	private String bocmBranch; //交行机构
	
	@NotNull(message = "host_state核心记账状态不能为空")
	@Size(max = 10,message = "host_state核心记账状态最大长度是10")
	private String hostState; //核心记账状态
	
    private Integer hostDate;//核心日期

    private String hostTraceno;//核心流水
    
    private String retCode;//核心反馈响应码
    
    private String retMsg;//核心反馈响应信息
	
	@NotNull(message = "bocm_state交行记账状态不能为空")
	@Size(max = 10,message = "bocm_state交行记账状态最大长度是10")
	private String bocmState; //村镇记账状态
	
    //交行日期
    private Integer bocmDate;
    //交行时间
    private Integer bocmTime;
    //交行流水
    private String bocmTraceno;
	
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
	
	private String checkFlag;//对账标志

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

	public String getBocmBranch() {
		return bocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		this.bocmBranch = bocmBranch;
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

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
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

	public Integer getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(Integer bocmDate) {
		this.bocmDate = bocmDate;
	}

	public Integer getBocmTime() {
		return bocmTime;
	}

	public void setBocmTime(Integer bocmTime) {
		this.bocmTime = bocmTime;
	}

	public String getBocmTraceno() {
		return bocmTraceno;
	}

	public void setBocmTraceno(String bocmTraceno) {
		this.bocmTraceno = bocmTraceno;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getRcvBankno() {
		return rcvBankno;
	}

	public void setRcvBankno(String rcvBankno) {
		this.rcvBankno = rcvBankno;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayerActtp() {
		return payerActtp;
	}

	public void setPayerActtp(String payerActtp) {
		this.payerActtp = payerActtp;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getPayeeActtp() {
		return payeeActtp;
	}

	public void setPayeeActtp(String payeeActtp) {
		this.payeeActtp = payeeActtp;
	}
	
}
