package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: SndTraceUpdModel 
* @Description: 往账更新 
* @author YePuLiang
* @date 2019年04月15日 下午1:38:29 
*  
*/
public class BocmSndTraceUpdModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -4406865550814118889L;

	public BocmSndTraceUpdModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	//交易渠道
	@Size(max = 20,message = "source_type交易渠道最大长度是20")
	private String sourceType; 
	//交易机构
	@Size(max = 20,message = "tx_branch交易机构最大长度是20")
	private String txBranch; 
	//现转标志
	@Size(max = 10,message = "tx_ind现转标志最大长度是10")
	private String txInd; 
	//通存通兑标志
	@Size(max = 10,message = "dc_flag通存通兑标志最大长度是10")
	private String dcFlag; 
	//交易金额
	@Digits(integer = 14, fraction = 2,message = "tx_amt交易金额格式不正确")
	private String txAmt;
    
    /**
     * 付款行人行行号
     */
    private String sndBankno;
    /**
     * 收款人行行号
     */
    private String rcvBankno;
	
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 账户余额
     */
    private BigDecimal actBal;
	
	//付款人账户
	@Size(max = 50,message = "payer_acno付款人账户最大长度是50")
	private String payerAcno; 
	//付款人户名
	@Size(max = 100,message = "payer_name付款人户名最大长度是100")
	private String payerName; 
	//收款人账户
	@Size(max = 50,message = "payee_acno收款人账户最大长度是50")
	private String payeeAcno; 
	//收款人户名
	@Size(max = 100,message = "payee_name收款人户名最大长度是100")
	private String payeeName; 
	//村镇机构
	@Size(max = 20,message = "bocm_branch村镇机构最大长度是20")
	private String bocmBranch; 
	//核心记账状态
	@Size(max = 10,message = "host_state核心记账状态最大长度是10")
	private String hostState; 
	//交易柜员
	@Size(max = 30,message = "tx_tel交易柜员最大长度是30")
	private String txTel; 
	//复核员
	@Size(max = 30,message = "chk_tel复核员最大长度是30")
	private String chkTel; 
	//授权员
	@Size(max = 30,message = "auth_tel授权员最大长度是30")
	private String authTel; 
	//打印次数
	@Size(max = 10,message = "print打印次数最大长度是10")
	private String print; 
	//摘要
	@Size(max = 255,message = "info摘要最大长度是255")
	private String info; 
	//渠道日期
    private Integer platDate; 
    //渠道流水
    private Integer platTrace;
    //交易时间
    private Integer platTime;
    //核心日期
    private Integer hostDate;
    //核心流水
    private String hostTraceno;
    //交行日期
    private Integer bocmDate;
    //交行时间
    private Integer bocmTime;
    //交行流水
    private String bocmTraceno;
    //对账标志
    private String checkFlag;
    //交行记账状态
    private String bocmState;
    //核心反馈响应码
    private String retCode;
    //核心反馈响应信息
    private String retMsg;
    //核心记账机构
    private String hostBranch;

	public String getHostBranch() {
		return hostBranch;
	}

	public void setHostBranch(String hostBranch) {
		this.hostBranch = hostBranch;
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

	public Integer getPlatTime() {
		return platTime;
	}

	public void setPlatTime(Integer platTime) {
		this.platTime = platTime;
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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}


	
	
}
