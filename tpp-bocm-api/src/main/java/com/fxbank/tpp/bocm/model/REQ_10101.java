package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 交行余额查询请求
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:20:40
 */
public class REQ_10101 extends REQ_BASE {

    private static final long serialVersionUID = 4718668418571598941L;

    private String ccyCod = "CNY";

    private String actTyp;

    private String actNo;

    private String pin;

    @Deprecated
	public REQ_10101() {
		super(null, 0, 0, 0);
	}

    public REQ_10101(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.getHeader().settTxnCd("10101");
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-3s", this.ccyCod));
        sb.append(String.format("%-1s", this.actTyp));
        sb.append(String.format("%-32s", this.actNo));
        sb.append(String.format("%-20s", this.pin));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+60));
        this.ccyCod = sb.substring(i, i=i+3).trim();
        this.actTyp = sb.substring(i, i=i+1).trim();
        this.actNo= sb.substring(i, i=i+32).trim();
        this.pin= sb.substring(i, i=i+20).trim();
    }

    /**
     * @return the ccyCod
     */
    public String getCcyCod() {
        return ccyCod;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
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
     * @return the actTyp
     */
    public String getActTyp() {
        return actTyp;
    }

    /**
     * @param actTyp the actTyp to set
     */
    public void setActTyp(String actTyp) {
        this.actTyp = actTyp;
    }

    /**
     * @param ccyCod the ccyCod to set
     */
    public void setCcyCod(String ccyCod) {
        this.ccyCod = ccyCod;
    }

}