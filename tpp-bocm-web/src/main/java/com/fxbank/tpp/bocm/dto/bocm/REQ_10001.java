package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.cip.base.pkg.tag.TagAnno.TagField;

/** 
* @ClassName: REQ_10001 
* @Description: 磁条卡通兑 
* @author Duzhenduo
* @date 2019年4月16日 上午9:23:02 
*  
*/
public class REQ_10001 extends REQ_BASE {
	
    public REQ_10001() {
        super.txDesc = "磁条卡通兑";
        super.log = false;
	}
	
	@TagField(name = "10001")
    @FixedField(order = 8, len = 3, desc = "币种")
	private String ccyCod = "CNY";
    
	@FixedField(order = 9, len = 15, scale = 0, desc = "交易金额")
    private Double txnAmt;
    
	@FixedField(order = 10, len = 20, desc = "交易密码")
    private String pin;

	@FixedField(order = 11, len = 1, desc = "卡输入方式")
    private String oprFlg;
    
	@FixedField(order = 12, len = 1, desc = "业务模式")
    private String txnMod;
    
	@FixedField(order = 13, len = 12, desc = "付款人开户行行号")
    private String payBnk;
    
	@FixedField(order = 14, len = 1, desc = "付款人账户类型")
    private String pactTp;
    
	@FixedField(order = 15, len = 32, desc = "付款人账号")
    private String pactNo;
    
	@FixedField(order = 16, len = 30, desc = "付款人名称")
    private String payNam;
    
	@FixedField(order = 17, len = 60, desc = "付款人地址")
    private String payAdr;
    
	@FixedField(order = 18, len = 12, desc = "收款人开户行行号")
    private String recBnk;
    
	@FixedField(order = 19, len = 1, desc = "收款人账户类型")
    private String ractTp;
    
	@FixedField(order = 20, len = 32, desc = "收款人账号")
    private String ractNo;
    
	@FixedField(order = 21, len = 30, desc = "收款人名称")
    private String recNam;
    
	@FixedField(order = 22, len = 60, desc = "收款人地址")
    private String recAdr;
    
	@FixedField(order = 23, len = 2, desc = "客户证件种类")
    private String cuIdTp;
    
	@FixedField(order = 24, len = 30, desc = "客户证件号码")
    private String cuIdNo;
    
	@FixedField(order = 25, len = 2, desc = "代理人证件种类")
    private String agIdTp;
    
	@FixedField(order = 26, len = 30, desc = "代理人证件号码")
    private String agIdNo;
    
	@FixedField(order = 27, len = 30, desc = "代理人姓名")
    private String agtNam;
    
	@FixedField(order = 28, len = 37, desc = "第二磁道")
    private String secMag;
    
	@FixedField(order = 29, len = 104, desc = "第三磁道")
    private String thdMag;
    
	@FixedField(order = 30, len = 60, desc = "附言")
    private String remark;

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public Double getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getOprFlg() {
		return oprFlg;
	}

	public void setOprFlg(String oprFlg) {
		this.oprFlg = oprFlg;
	}

	public String getTxnMod() {
		return txnMod;
	}

	public void setTxnMod(String txnMod) {
		this.txnMod = txnMod;
	}

	public String getPayBnk() {
		return payBnk;
	}

	public void setPayBnk(String payBnk) {
		this.payBnk = payBnk;
	}

	public String getPactTp() {
		return pactTp;
	}

	public void setPactTp(String pactTp) {
		this.pactTp = pactTp;
	}

	public String getPactNo() {
		return pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	public String getPayNam() {
		return payNam;
	}

	public void setPayNam(String payNam) {
		this.payNam = payNam;
	}

	public String getPayAdr() {
		return payAdr;
	}

	public void setPayAdr(String payAdr) {
		this.payAdr = payAdr;
	}

	public String getRecBnk() {
		return recBnk;
	}

	public void setRecBnk(String recBnk) {
		this.recBnk = recBnk;
	}

	public String getRactTp() {
		return ractTp;
	}

	public void setRactTp(String ractTp) {
		this.ractTp = ractTp;
	}

	public String getRactNo() {
		return ractNo;
	}

	public void setRactNo(String ractNo) {
		this.ractNo = ractNo;
	}

	public String getRecNam() {
		return recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public String getRecAdr() {
		return recAdr;
	}

	public void setRecAdr(String recAdr) {
		this.recAdr = recAdr;
	}

	public String getCuIdTp() {
		return cuIdTp;
	}

	public void setCuIdTp(String cuIdTp) {
		this.cuIdTp = cuIdTp;
	}

	public String getCuIdNo() {
		return cuIdNo;
	}

	public void setCuIdNo(String cuIdNo) {
		this.cuIdNo = cuIdNo;
	}

	public String getAgIdTp() {
		return agIdTp;
	}

	public void setAgIdTp(String agIdTp) {
		this.agIdTp = agIdTp;
	}

	public String getAgIdNo() {
		return agIdNo;
	}

	public void setAgIdNo(String agIdNo) {
		this.agIdNo = agIdNo;
	}

	public String getAgtNam() {
		return agtNam;
	}

	public void setAgtNam(String agtNam) {
		this.agtNam = agtNam;
	}

	public String getSecMag() {
		return secMag;
	}

	public void setSecMag(String secMag) {
		this.secMag = secMag;
	}

	public String getThdMag() {
		return thdMag;
	}

	public void setThdMag(String thdMag) {
		this.thdMag = thdMag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}