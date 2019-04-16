package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;



/** 
* @ClassName: REP_20000 
* @Description:IC卡通存 
* @author Duzhenduo
* @date 2019年4月16日 上午9:03:52 
*  
*/
public class REP_20001 extends REP_BASE {

	private static final long serialVersionUID = 7762922925444902496L;

	private String oTxnAmt;

    private String fee;

    private String actBal;

    @Deprecated
	public REP_20001() {
		super(null, 0, 0, 0);
	}

    public REP_20001(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-15s", this.oTxnAmt==null?"":this.oTxnAmt));
        sb.append(String.format("%-15s", this.fee==null?"":this.fee));
        sb.append(String.format("%-15s", this.actBal==null?"":this.actBal));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
        this.oTxnAmt = sb.substring(i, i=i+15).trim();
        this.fee= sb.substring(i, i=i+15).trim();
        this.actBal= sb.substring(i, i=i+15).trim();
    }

	public String getoTxnAmt() {
		return oTxnAmt;
	}

	public void setoTxnAmt(String oTxnAmt) {
		this.oTxnAmt = oTxnAmt;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getActBal() {
		return actBal;
	}

	public void setActBal(String actBal) {
		this.actBal = actBal;
	}

    

}