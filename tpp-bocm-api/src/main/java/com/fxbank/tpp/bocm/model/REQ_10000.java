package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REQ_10000 
* @Description: 磁条卡通存 
* @author Duzhenduo
* @date 2019年4月15日 下午5:02:02 
*  
*/
public class REQ_10000 extends REQ_BASE {

	private static final long serialVersionUID = 4990422436703924474L;

	private String ccyCod = "CNY";
    
    private BigDecimal txnAmt;

    private String feeFlg;

    private BigDecimal fee;
    
    private String oprFlg;
    
    private String txnMod;
    
    private String payBnk;
    
    private String pActTp;
    
    private String pActNo;
    
    private String payNam;
    
    private String payAdr;
    
    private String recBnk;
    
    private String rActTp;
    
    private String rActNo;
    
    private String recNam;
    
    private String recAdr;
    
    private String cuIdTp;
    
    private String cuIdNo;
    
    private String agIdTp;
    
    private String agIdNo;
    
    private String agtNam;
    
    private String secMag;
    
    private String thdMag;
    
    private String remark;
    
    @Deprecated
	public REQ_10000() {
		super(null, 0, 0, 0);
	}

    public REQ_10000(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.getHeader().settTxnCd("10000");
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-3s", this.ccyCod==null?"":this.ccyCod));
        sb.append(String.format("%015.0f", this.txnAmt==null?0.0:this.txnAmt.movePointRight(2)));
        sb.append(String.format("%-1s", this.feeFlg==null?"":this.feeFlg));
        sb.append(String.format("%015.0f", this.fee==null?0.0:this.fee.movePointRight(2)));
        sb.append(String.format("%-1s", this.oprFlg==null?"":this.oprFlg));
        sb.append(String.format("%-1s", this.txnMod==null?"":this.txnMod));
        sb.append(String.format("%-12s", this.payBnk==null?"":this.payBnk));
        sb.append(String.format("%-1s", this.pActTp==null?"":this.pActTp));
        sb.append(String.format("%-32s", this.pActNo==null?"":this.pActNo));
        sb.append(String.format("%-30s", this.payNam==null?"":this.payNam));
        sb.append(String.format("%-60s", this.payAdr==null?"":this.payAdr));
        sb.append(String.format("%-12s", this.recBnk==null?"":this.recBnk));
        sb.append(String.format("%-1s", this.rActTp==null?"":this.rActTp));
        sb.append(String.format("%-32s", this.rActNo==null?"":this.rActNo));
        sb.append(String.format("%-30s", this.recNam==null?"":this.recNam));
        sb.append(String.format("%-60s", this.recAdr==null?"":this.recAdr));
        sb.append(String.format("%-2s", this.cuIdTp==null?"":this.cuIdTp));
        sb.append(String.format("%-30s", this.cuIdNo==null?"":this.cuIdNo));
        sb.append(String.format("%-2s", this.agIdTp==null?"":this.agIdTp));
        sb.append(String.format("%-30s", this.agIdNo==null?"":this.agIdNo));
        sb.append(String.format("%-30s", this.agtNam==null?"":this.agtNam));
        sb.append(String.format("%-37s", this.secMag==null?"":this.secMag));
        sb.append(String.format("%-104s", this.thdMag==null?"":this.thdMag));
        sb.append(String.format("%-60s", this.remark==null?"":this.remark));
        
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+60));
        this.ccyCod = sb.substring(i, i=i+3).trim();
        this.txnAmt = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.feeFlg = sb.substring(i, i=i+1).trim();
        this.fee = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.oprFlg = sb.substring(i, i=i+1).trim();
        this.txnMod = sb.substring(i, i=i+1).trim();
        this.payBnk = sb.substring(i, i=i+12).trim();
        this.pActTp = sb.substring(i, i=i+1).trim();
        this.pActNo = sb.substring(i, i=i+32).trim();
        this.payNam = sb.substring(i, i=i+30).trim();
        this.payAdr = sb.substring(i, i=i+60).trim();
        this.recBnk = sb.substring(i, i=i+12).trim();
        this.rActTp = sb.substring(i, i=i+1).trim();
        this.rActNo = sb.substring(i, i=i+32).trim();
        this.recNam = sb.substring(i, i=i+30).trim();
        this.recAdr = sb.substring(i, i=i+60).trim();
        this.cuIdTp = sb.substring(i, i=i+2).trim();
        this.cuIdNo = sb.substring(i, i=i+30).trim();
        this.agIdTp = sb.substring(i, i=i+2).trim();
        this.agIdNo = sb.substring(i, i=i+30).trim();
        this.agtNam = sb.substring(i, i=i+30).trim();
        this.secMag = sb.substring(i, i=i+37).trim();
        this.thdMag = sb.substring(i, i=i+104).trim();
        this.remark = sb.substring(i, i=i+60).trim();
    }

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getFeeFlg() {
		return feeFlg;
	}

	public void setFeeFlg(String feeFlg) {
		this.feeFlg = feeFlg;
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

	public String getpActTp() {
		return pActTp;
	}

	public void setpActTp(String pActTp) {
		this.pActTp = pActTp;
	}

	public String getpActNo() {
		return pActNo;
	}

	public void setpActNo(String pActNo) {
		this.pActNo = pActNo;
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

	public String getrActTp() {
		return rActTp;
	}

	public void setrActTp(String rActTp) {
		this.rActTp = rActTp;
	}

	public String getrActNo() {
		return rActNo;
	}

	public void setrActNo(String rActNo) {
		this.rActNo = rActNo;
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

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

   

}