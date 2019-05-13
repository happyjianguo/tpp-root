/**   
* @Title: REP_10103.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 下午5:51:44 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REP_10103 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月7日 下午5:51:44 
*  
*/
public class REP_10103 extends REP_BASE {
	
	private static final long serialVersionUID = -3159939908759734651L;

	private String filLen;

    private String tolCnt;

    private BigDecimal tolAmt;

    private String filTxt;
	
    @Deprecated
	public REP_10103() {
		super(null, 0, 0, 0);
	}

    public REP_10103(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-8s", this.filLen==null?"":this.filLen));
        sb.append(String.format("%-7s", this.tolCnt==null?"":this.tolCnt));
        sb.append(String.format("%015.0f", this.tolAmt==null?0.0:this.tolAmt.movePointRight(2)));
        sb.append(String.format("%-"+filLen+"s", this.filTxt==null?"":this.filTxt));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
        this.filLen = sb.substring(i, i=i+8).trim();
        this.tolCnt= sb.substring(i, i=i+7).trim();
        this.tolAmt= new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.filTxt= sb.substring(i, i=i+Integer.parseInt(this.filLen)).trim();
    }

	public String getFilLen() {
		return filLen;
	}

	public void setFilLen(String filLen) {
		this.filLen = filLen;
	}

	public String getTolCnt() {
		return tolCnt;
	}

	public void setTolCnt(String tolCnt) {
		this.tolCnt = tolCnt;
	}

	public BigDecimal getTolAmt() {
		return tolAmt;
	}

	public void setTolAmt(BigDecimal tolAmt) {
		this.tolAmt = tolAmt;
	}

	public String getFilTxt() {
		return filTxt;
	}

	public void setFilTxt(String filTxt) {
		this.filTxt = filTxt;
	}
    
    
}
