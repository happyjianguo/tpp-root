/**   
* @Title: REP_20102.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月9日 上午10:47:29 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REP_20102 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月9日 上午10:47:29 
*  
*/
public class REP_20102 extends REP_BASE {
	
	private static final long serialVersionUID = -1624248808632530084L;

	@Deprecated
	public REP_20102() {
		super(null, 0, 0, 0);
	}

    public REP_20102(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }
    
	private String ccyCod;
	private String actBnk;
	private String actTyp;
	private String actNam;
	private String actNo;
	private BigDecimal fee;
	private String actAdr;
	private String amtLmt;
	
    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-3s", this.ccyCod==null?"":this.ccyCod));
        sb.append(String.format("%-12s", this.actBnk==null?"":this.actBnk));
        sb.append(String.format("%-1s", this.actTyp==null?"":this.actTyp));
        sb.append(String.format("%-30s", this.actNam==null?"":this.actNam));
        sb.append(String.format("%-32s", this.actNo==null?"":this.actNo));
        sb.append(String.format("%015.0f", this.fee==null?0.0:this.fee.movePointRight(2)));
        sb.append(String.format("%-60s", this.actAdr==null?"":this.actAdr));
        sb.append(String.format("%-1s", this.amtLmt==null?"":this.amtLmt));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
        this.ccyCod = sb.substring(i, i=i+3).trim();
        this.actBnk = sb.substring(i, i=i+12).trim();
        this.actTyp = sb.substring(i, i=i+1).trim();
        this.actNam = sb.substring(i, i=i+30).trim();
        this.actNo = sb.substring(i, i=i+32).trim();
        this.fee= new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.actAdr = sb.substring(i, i=i+60).trim();
        this.amtLmt = sb.substring(i, i=i+1).trim();
    }
	
	
	public String getCcyCod() {
		return ccyCod;
	}

	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public String getActBnk() {
		return actBnk;
	}

	public void setActBnk(String actBnk) {
		this.actBnk = actBnk;
	}

	public String getActTyp() {
		return actTyp;
	}

	public void setActTyp(String actTyp) {
		this.actTyp = actTyp;
	}

	public String getActNam() {
		return actNam;
	}

	public void setActNam(String actNam) {
		this.actNam = actNam;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getActAdr() {
		return actAdr;
	}

	public void setActAdr(String actAdr) {
		this.actAdr = actAdr;
	}

	public String getAmtLmt() {
		return amtLmt;
	}

	public void setAmtLmt(String amtLmt) {
		this.amtLmt = amtLmt;
	}
	
	
}
