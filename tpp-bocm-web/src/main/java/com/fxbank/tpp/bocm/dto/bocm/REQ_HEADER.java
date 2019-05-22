package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;

/**
 * @Description: 交行通讯请求报文头
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:55:17
 */
public class REQ_HEADER {

	@FixedField(order = 1, len = 5, desc = "交易代码")
	private String tTxnCd;

	@FixedField(order = 2, len = 3, desc = "业务类型")
	private String bBusTyp;

	@FixedField(order = 3, len = 12, desc = "发起行行号")
	private String sBnkNo;

	@FixedField(order = 4, len = 12, desc = "接收行行号")
	private String rBnkNo;

	@FixedField(order = 5, len = 8, desc = "交易日期")
	private Integer tTxnDat;

	@FixedField(order = 6, len = 6, desc = "交易时间")
	private Integer tTxnTim;

	@FixedField(order = 7, len = 14, desc = "发起行交易流水号")
	private String sLogNo;

//	@Override
//	public String creaFixPack() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(String.format("%-5s", this.tTxnCd==null?"":this.tTxnCd));
//		sb.append(String.format("%-3s", this.bBusTyp==null?"":this.bBusTyp));
//		sb.append(String.format("%-12s", this.sBnkNo==null?"":this.sBnkNo));
//		sb.append(String.format("%-12s", this.rBnkNo==null?"":this.rBnkNo));
//		sb.append(String.format("%08d", this.tTxnDat==null?0:this.tTxnDat));
//		sb.append(String.format("%06d", this.tTxnTim==null?0:this.tTxnTim));
//		sb.append(String.format("%-14s", this.sLogNo==null?"":this.sLogNo));
//		return sb.toString();
//	}
//
//	@Override
//	public void chanFixPack(String pack) {
//		StringBuffer sb = new StringBuffer(pack);
//		int i=0;
//		this.tTxnCd = sb.substring(0, i=i+5).trim();
//		this.bBusTyp= sb.substring(i, i=i+3).trim();
//		this.sBnkNo= sb.substring(i, i=i+12).trim();
//		this.rBnkNo= sb.substring(i, i=i+12).trim();
//		this.tTxnDat= Integer.valueOf(sb.substring(i, i=i+8));
//		this.tTxnTim= Integer.valueOf(sb.substring(i, i=i+6));
//		this.sLogNo= sb.substring(i, i=i+14).trim();
//	}

	/**
	 * @return the tTxnCd
	 */
	public String getTTxnCd() {
		return tTxnCd;
	}

	/**
	 * @return the sLogNo
	 */
	public String getSLogNo() {
		return sLogNo;
	}

	/**
	 * @param sLogNo the sLogNo to set
	 */
	public void setSLogNo(String sLogNo) {
		this.sLogNo = sLogNo;
	}

	/**
	 * @return the tTxnTim
	 */
	public Integer getTTxnTim() {
		return tTxnTim;
	}

	/**
	 * @param tTxnTim the tTxnTim to set
	 */
	public void setTTxnTim(Integer tTxnTim) {
		this.tTxnTim = tTxnTim;
	}

	/**
	 * @return the tTxnDat
	 */
	public Integer getTTxnDat() {
		return tTxnDat;
	}

	/**
	 * @param tTxnDat the tTxnDat to set
	 */
	public void setTTxnDat(Integer tTxnDat) {
		this.tTxnDat = tTxnDat;
	}

	/**
	 * @return the rBnkNo
	 */
	public String getRBnkNo() {
		return rBnkNo;
	}

	/**
	 * @param rBnkNo the rBnkNo to set
	 */
	public void setRBnkNo(String rBnkNo) {
		this.rBnkNo = rBnkNo;
	}

	/**
	 * @return the sBnkNo
	 */
	public String getSBnkNo() {
		return sBnkNo;
	}

	/**
	 * @param sBnkNo the sBnkNo to set
	 */
	public void setSBnkNo(String sBnkNo) {
		this.sBnkNo = sBnkNo;
	}

	/**
	 * @return the bBusTyp
	 */
	public String getBBusTyp() {
		return bBusTyp;
	}

	/**
	 * @param bBusTyp the bBusTyp to set
	 */
	public void setBBusTyp(String bBusTyp) {
		this.bBusTyp = bBusTyp;
	}

	/**
	 * @param tTxnCd the tTxnCd to set
	 */
	public void setTTxnCd(String tTxnCd) {
		this.tTxnCd = tTxnCd;
	}
	
	public static void main(String[] args) {
		REQ_HEADER header = new REQ_HEADER();
		header.setTTxnCd("10000");
		header.setBBusTyp("000");
		header.setRBnkNo("rbnkno123456");
		header.setSBnkNo("sbnkno123456");
		header.setTTxnDat(20190520);
		header.setTTxnTim(101010);
		header.setSLogNo("20190520000001");
		String fixedStr = FixedUtil.toFixed(header); // 定长
		System.out.println("报文=[" + fixedStr + "]");
		REQ_HEADER modelRep = new FixedUtil(fixedStr).toBean(REQ_HEADER.class); // 定长
		
		System.out.println("报文： "+FixedUtil.toFixed(modelRep));
		
		String headerTxt = "100000003131310000083131310000072019052011221419052031490089";
		REQ_HEADER modelRepHead = new FixedUtil(headerTxt).toBean(REQ_HEADER.class); // 定长
		System.out.println("流水号："+modelRepHead.getSLogNo());
		System.out.println("交易码："+modelRepHead.getTTxnCd());
		System.out.println("请求报文： "+FixedUtil.toFixed(modelRepHead));
	}

}
