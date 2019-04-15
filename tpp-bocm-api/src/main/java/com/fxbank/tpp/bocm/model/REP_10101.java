package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;

/**
* @Description: 交行余额查询应答
* @author 周勇沩
* @date 20190415
*/
public class REP_10101 extends REP_BASE {

    private static final long serialVersionUID = 5293911485350387070L;

    private String ccyCod = "CNY";

    private String actNam;

    private String actNo;

    private BigDecimal actBal;

    @Deprecated
	public REP_10101() {
		super(null, 0, 0, 0);
	}

    public REP_10101(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-3s", this.ccyCod));
        sb.append(String.format("%-30s", this.actNam));
        sb.append(String.format("%-32s", this.actNo));
        sb.append(String.format("%015.0f", this.actBal.multiply(new BigDecimal(100))));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
        this.ccyCod = sb.substring(i, i=i+3).trim();
        this.actNam= sb.substring(i, i=i+30).trim();
        this.actNo= sb.substring(i, i=i+32).trim();
        this.actBal= new BigDecimal(sb.substring(i, i=i+15).trim()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
    }

    /**
	 * @return the actBal
	 */
	public BigDecimal getActBal() {
		return actBal;
	}

	/**
	 * @param actBal the actBal to set
	 */
	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	/**
     * @return the actNam
     */
    public String getActNam() {
        return actNam;
    }

    /**
     * @param actNam the actNam to set
     */
    public void setActNam(String actNam) {
        this.actNam = actNam;
    }

    /**
     * @return the ccyCod
     */
    public String getCcyCod() {
        return ccyCod;
    }

    /**
     * @return the actNo
     */
    public String getActNo() {
        return actNo;
    }

    /**
     * @param actNo the actNo to set
     */
    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    /**
     * @param ccyCod the ccyCod to set
     */
    public void setCcyCod(String ccyCod) {
        this.ccyCod = ccyCod;
    }

}