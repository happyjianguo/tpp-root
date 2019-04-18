package com.fxbank.tpp.bocm.dto.bocm;

import java.math.BigDecimal;

/** 
* @ClassName: REP_10001 
* @Description: 磁条卡通兑
* @author Duzhenduo
* @date 2019年4月16日 上午9:26:08 
*  
*/
public class REP_10001 extends REP_BASE {

	private BigDecimal oTxnAmt;

    private BigDecimal fee;

    private BigDecimal actBal;

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%015.0f", this.oTxnAmt==null?0.0:this.oTxnAmt.movePointRight(2)));
        sb.append(String.format("%015.0f", this.fee==null?0.0:this.fee.movePointRight(2)));
        sb.append(String.format("%015.0f", this.actBal==null?0.0:this.actBal.movePointRight(2)));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
        this.oTxnAmt = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.fee= new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.actBal= new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
    }

	public BigDecimal getoTxnAmt() {
		return oTxnAmt;
	}

	public void setoTxnAmt(BigDecimal oTxnAmt) {
		this.oTxnAmt = oTxnAmt;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	
}