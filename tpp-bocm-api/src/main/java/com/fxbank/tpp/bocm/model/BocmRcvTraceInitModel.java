package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BocmRcvTraceInitModel 
* @Description: 来账流水表登记模型
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
	
    private String tranType;
	
	@NotNull(message = "tx_ind现转标志不能为空")
	@Size(max = 10,message = "tx_ind现转标志最大长度是10")
	private String txInd; //现转标志
	
	@Size(max = 10,message = "tx_code交易代码最大长度是10")
	private String txCode; //交易代码
	
	@NotNull(message = "dc_flag通存通兑标志不能为空")
	@Size(max = 10,message = "dc_flag通存通兑标志最大长度是10")
	private String dcFlag; //通存通兑标志
	
    /**
     * 交易金额
     */
    private BigDecimal txAmt;
    
    private Integer txDate;
    
    /**
     * 账户余额
     */
    private BigDecimal actBal;
	
    /**
     * 手续费收取方式
     */
    private String feeFlag;
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 付款行人行行号
     */
    private String sndBankno;
    /**
     * 收款人行行号
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
	//交行日期
	private int bocmDate;
	//交行时间
	private int bocmTime;
	
	private String bocmTraceNo;//村镇流水
	
	private String bocmFlag;//村镇标志
	
	private String checkFlag;//对账标志
	
    //交行对账文件客户手续费手续方式
    private String bocmFeeFlag;
    //交行记账文件客户手续费
    private BigDecimal bocmFee;
	

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



	public int getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(int bocmDate) {
		this.bocmDate = bocmDate;
	}

	public int getBocmTime() {
		return bocmTime;
	}

	public void setBocmTime(int bocmTime) {
		this.bocmTime = bocmTime;
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

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public String getBocmFeeFlag() {
		return bocmFeeFlag;
	}

	public void setBocmFeeFlag(String bocmFeeFlag) {
		this.bocmFeeFlag = bocmFeeFlag;
	}

	public BigDecimal getBocmFee() {
		return bocmFee;
	}

	public void setBocmFee(BigDecimal bocmFee) {
		this.bocmFee = bocmFee;
	}


	

}