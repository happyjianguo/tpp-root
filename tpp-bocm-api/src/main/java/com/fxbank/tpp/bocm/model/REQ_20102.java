/**   
* @Title: REQ_20102.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:43:14 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REQ_20102 
* @Description: 账户信息查询
* @author YePuLiang
* @date 2019年5月9日 上午10:43:14 
*  
*/
public class REQ_20102 extends REQ_BASE {
	
	private static final long serialVersionUID = -83548845382386681L;
	
    @Deprecated
	public REQ_20102() {
		super(null, 0, 0, 0);
	}

    public REQ_20102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.getHeader().settTxnCd("20102");
    }

	//币种
	private String ccyCod = "CNY";
	
	private String actTyp;
	
	private String actNo;
	
	private String txnTyp;
	
	private String actBnk;
	
	private String feeFlg;
	
	private BigDecimal txnAmt;
	
	@Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-3s", this.ccyCod==null?"":this.ccyCod));
        sb.append(String.format("%-1s", this.actTyp==null?"":this.actTyp));
        sb.append(String.format("%-32s", this.actNo==null?"":this.actNo));
        sb.append(String.format("%-2s", this.txnTyp==null?"":this.txnTyp));
        sb.append(String.format("%-12s", this.actBnk==null?"":this.actBnk));
        sb.append(String.format("%-1s", this.feeFlg==null?"":this.feeFlg));
        sb.append(String.format("%015.0f", this.txnAmt==null?0.0:this.txnAmt.movePointRight(2)));        
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+60));
        this.ccyCod = sb.substring(i, i=i+3).trim();
        this.actTyp = sb.substring(i,i=i+1).trim();
        this.actNo = sb.substring(i, i+i+32).trim();
        this.txnTyp = sb.substring(i, i+2);
        this.actBnk = sb.substring(i, i+12);
        this.feeFlg = sb.substring(i, i+1); 
        this.txnAmt = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);      
    }
	

	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getActTyp() {
		return actTyp;
	}

	public void setActTyp(String actTyp) {
		this.actTyp = actTyp;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getTxnTyp() {
		return txnTyp;
	}

	public void setTxnTyp(String txnTyp) {
		this.txnTyp = txnTyp;
	}

	public String getActBnk() {
		return actBnk;
	}

	public void setActBnk(String actBnk) {
		this.actBnk = actBnk;
	}

	public String getFeeFlg() {
		return feeFlg;
	}

	public void setFeeFlg(String feeFlg) {
		this.feeFlg = feeFlg;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}
	


}
