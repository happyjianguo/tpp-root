package com.fxbank.tpp.mivs.dto.bocm;

import com.fxbank.cip.base.model.FIXP_SERIAL;

/**
 * @Description: 交行通讯请求报文头
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:55:17
 */
public class REQ_HEADER implements FIXP_SERIAL {

	private String tTxnCd;

	private String bBusTyp;

	private String sBnkNo;

	private String rBnkNo;

	private Integer tTxnDat;

	private Integer tTxnTim;

	private String sLogNo;

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%-5s", this.tTxnCd==null?"":this.tTxnCd));
		sb.append(String.format("%-3s", this.bBusTyp==null?"":this.bBusTyp));
		sb.append(String.format("%-12s", this.sBnkNo==null?"":this.sBnkNo));
		sb.append(String.format("%-12s", this.rBnkNo==null?"":this.rBnkNo));
		sb.append(String.format("%08d", this.tTxnDat==null?0:this.tTxnDat));
		sb.append(String.format("%06d", this.tTxnTim==null?0:this.tTxnTim));
		sb.append(String.format("%-14s", this.sLogNo==null?"":this.sLogNo));
		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		StringBuffer sb = new StringBuffer(pack);
		int i=0;
		this.tTxnCd = sb.substring(0, i=i+5).trim();
		this.bBusTyp= sb.substring(i, i=i+3).trim();
		this.sBnkNo= sb.substring(i, i=i+12).trim();
		this.rBnkNo= sb.substring(i, i=i+12).trim();
		this.tTxnDat= Integer.valueOf(sb.substring(i, i=i+8));
		this.tTxnTim= Integer.valueOf(sb.substring(i, i=i+6));
		this.sLogNo= sb.substring(i, i=i+14).trim();
	}

	/**
	 * @return the tTxnCd
	 */
	public String gettTxnCd() {
		return tTxnCd;
	}

	/**
	 * @return the sLogNo
	 */
	public String getsLogNo() {
		return sLogNo;
	}

	/**
	 * @param sLogNo the sLogNo to set
	 */
	public void setsLogNo(String sLogNo) {
		this.sLogNo = sLogNo;
	}

	/**
	 * @return the tTxnTim
	 */
	public Integer gettTxnTim() {
		return tTxnTim;
	}

	/**
	 * @param tTxnTim the tTxnTim to set
	 */
	public void settTxnTim(Integer tTxnTim) {
		this.tTxnTim = tTxnTim;
	}

	/**
	 * @return the tTxnDat
	 */
	public Integer gettTxnDat() {
		return tTxnDat;
	}

	/**
	 * @param tTxnDat the tTxnDat to set
	 */
	public void settTxnDat(Integer tTxnDat) {
		this.tTxnDat = tTxnDat;
	}

	/**
	 * @return the rBnkNo
	 */
	public String getrBnkNo() {
		return rBnkNo;
	}

	/**
	 * @param rBnkNo the rBnkNo to set
	 */
	public void setrBnkNo(String rBnkNo) {
		this.rBnkNo = rBnkNo;
	}

	/**
	 * @return the sBnkNo
	 */
	public String getsBnkNo() {
		return sBnkNo;
	}

	/**
	 * @param sBnkNo the sBnkNo to set
	 */
	public void setsBnkNo(String sBnkNo) {
		this.sBnkNo = sBnkNo;
	}

	/**
	 * @return the bBusTyp
	 */
	public String getbBusTyp() {
		return bBusTyp;
	}

	/**
	 * @param bBusTyp the bBusTyp to set
	 */
	public void setbBusTyp(String bBusTyp) {
		this.bBusTyp = bBusTyp;
	}

	/**
	 * @param tTxnCd the tTxnCd to set
	 */
	public void settTxnCd(String tTxnCd) {
		this.tTxnCd = tTxnCd;
	}

}
