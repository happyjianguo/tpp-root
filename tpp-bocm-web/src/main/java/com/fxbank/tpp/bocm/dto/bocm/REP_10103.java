/**   
* @Title: REP_10103.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 下午3:47:32 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import java.math.BigDecimal;

/** 
* @ClassName: REP_10103 
* @Description: 对账文件获取
* @author YePuLiang
* @date 2019年5月6日 下午3:47:32 
*  
*/
public class REP_10103 extends REP_BASE {
	
    private String filLen;

    private String tolCnt;

    private BigDecimal tolAmt;

    private String filTxt;
    
    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        int len = Integer.parseInt(this.filLen);
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-8s", this.filLen==null?"":this.filLen));
        sb.append(String.format("%-7s", this.tolCnt==null?"":this.tolCnt));
        sb.append(String.format("%015.0f", this.tolAmt==null?0.0:this.tolAmt.movePointRight(2)));
        sb.append(String.format("%-"+len+"s", this.filTxt==null?"":this.filTxt));
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
        this.filTxt= sb.substring(i, i=Integer.parseInt(this.filLen)).trim();
    }





	public String getFilLen() {
		return filLen;
	}

	public void setFilLen(String filLen) {
		this.filLen = filLen;
	}

	public BigDecimal getTolAmt() {
		return tolAmt;
	}

	public void setTolAmt(BigDecimal tolAmt) {
		this.tolAmt = tolAmt;
	}

	public String getTolCnt() {
		return tolCnt;
	}

	public void setTolCnt(String tolCnt) {
		this.tolCnt = tolCnt;
	}

	public String getFilTxt() {
		return filTxt;
	}

	public void setFilTxt(String filTxt) {
		this.filTxt = filTxt;
	}
    
    


}
