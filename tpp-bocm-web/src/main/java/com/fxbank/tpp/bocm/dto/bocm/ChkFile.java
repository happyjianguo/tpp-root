/**   
* @Title: ChkFile.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月8日 上午10:40:52 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: ChkFile 
* @Description: 交行对账记录流水(254)
* @author YePuLiang
* @date 2019年5月8日 上午10:40:52 
*  
*/
public class ChkFile {
	//本方交易流水号填农商行流水号，对方交易流水号填交行流水号（如果存在），发起方流水号必输
	private String tLogNo;
	//对方交易流水号
	private String logNo;
	//交易代码
	private String thdCod;	
	//业务类型
	private String bBusTyp;
	//发起行行号
	private String sBnkNo;
	//接收行行号
	private String rBnkNo;
	//第三方交易日期
	private String tActDt;	
	//币种
	private String ccyCod="CNY";
	//交易金额
	private BigDecimal txnAmt;		
	//手续费收取方式
	private String feeFlg;
	//开户行手续费
	private BigDecimal fee;
	//通存通兑业务模式
	private String txnMod;
	//付款人开户行行号
	private String payBnk;
	//付款人账户类型
	private String pActTp;
	//付款人账号
	private String pActNo;
	//付款人名称
	private String payNam;
	//收款人开户行行号
	private String recBnk;
	//收款人账户类型
	private String rActTp;
	//收款人账号
	private String rActNo;
	//收款人名称
	private String RecNam;
	//交易状态
	private String txnSts;
	
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%14s", this.tLogNo==null?"":this.tLogNo).replaceAll(" ", "0"));
        sb.append(String.format("%14s", this.logNo==null?"":this.logNo));
        sb.append(String.format("%-5s", this.thdCod==null?"":this.thdCod));
        sb.append(String.format("%-3s", this.bBusTyp==null?"":this.bBusTyp));
        sb.append(String.format("%-12s", this.sBnkNo==null?"":this.sBnkNo));
        sb.append(String.format("%-12s", this.rBnkNo==null?"":this.rBnkNo));
        sb.append(String.format("%-8s", this.tActDt==null?"":this.tActDt));
        sb.append(String.format("%-3s", this.ccyCod==null?"":this.ccyCod));
        sb.append(String.format("%015.0f", this.txnAmt==null?0.0:this.txnAmt.movePointRight(2)));
        sb.append(String.format("%-1s", this.feeFlg==null?"":this.feeFlg));
        sb.append(String.format("%015.0f", this.fee==null?0.0:this.fee.movePointRight(2)));
        sb.append(String.format("%-1s", this.txnMod==null?"":this.txnMod));
        sb.append(String.format("%-12s", this.payBnk==null?"":this.payBnk));
        sb.append(String.format("%-1s", this.pActTp==null?"":this.pActTp));
        sb.append(String.format("%-32s", this.pActNo==null?"":this.pActNo));
        sb.append(String.format("%-30s", this.payNam==null?"":this.payNam));
        sb.append(String.format("%-12s", this.recBnk==null?"":this.recBnk));
        sb.append(String.format("%-1s", this.rActTp==null?"":this.rActTp));
        sb.append(String.format("%-32s", this.rActNo==null?"":this.rActNo));
        sb.append(String.format("%-30s", this.RecNam==null?"":this.RecNam));
        sb.append(String.format("%-1s", this.txnSts==null?"":this.txnSts));
        return sb.toString();
    }

    public void chanFixPack(String pack) {
    	StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        this.tLogNo= sb.substring(i, i=i+14).trim();
        this.logNo= sb.substring(i, i=i+14).trim();
        this.thdCod= sb.substring(i, i=i+5).trim();
        this.bBusTyp= sb.substring(i, i=i+3).trim();
        this.sBnkNo= sb.substring(i, i=i+12).trim();
        this.rBnkNo= sb.substring(i, i=i+12).trim();
        this.tActDt= sb.substring(i, i=i+8).trim();
        this.ccyCod= sb.substring(i, i=i+3).trim();
        this.txnAmt = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.feeFlg= sb.substring(i, i=i+1).trim();
        this.fee = new BigDecimal(sb.substring(i, i=i+15).trim()).movePointLeft(2);
        this.txnMod= sb.substring(i, i=i+1).trim();
        this.payBnk= sb.substring(i, i=i+12).trim();
        this.pActTp= sb.substring(i, i=i+1).trim();
        this.pActNo= sb.substring(i, i=i+32).trim();
        this.payNam= sb.substring(i, i=i+30).trim();
        this.recBnk= sb.substring(i, i=i+12).trim();
        this.rActTp= sb.substring(i, i=i+1).trim();
        this.rActNo= sb.substring(i, i=i+32).trim();
        this.RecNam= sb.substring(i, i=i+30).trim();
        this.txnSts= sb.substring(i, i=i+1).trim();
    }
	/**
	* @Title: getChkTraceList 
	* @Description: 把报文中的文件转换成交易记录数组（每个交易记录为254个字符）
	* @param @param fileTxt
	* @param @param filLen
	* @param @return    设定文件 
	* @return List<ChkFile>    返回类型 
	* @throws
	 */
	public static List<ChkFile> getChkTraceList(String fileTxt,int filLen){
		List<ChkFile> list = new ArrayList<ChkFile>();
		int size = filLen / 254;
		int index = 0;
		for(int i=0;i<size;i++){
			System.out.println(index+"   "+(index+254)+"   "+fileTxt.length());
			String pack = fileTxt.substring(index, index = index+254);
			ChkFile chkTrace = new ChkFile();
			chkTrace.chanFixPack(pack);
			list.add(chkTrace);
		}	
		return list;
	}



	public String gettLogNo() {
		return tLogNo;
	}

	public void settLogNo(String tLogNo) {
		this.tLogNo = tLogNo;
	}

	public String getLogNo() {
		return logNo;
	}
	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}
	public String getThdCod() {
		return thdCod;
	}
	public void setThdCod(String thdCod) {
		this.thdCod = thdCod;
	}
	public String getbBusTyp() {
		return bBusTyp;
	}
	public void setbBusTyp(String bBusTyp) {
		this.bBusTyp = bBusTyp;
	}
	public String getsBnkNo() {
		return sBnkNo;
	}
	public void setsBnkNo(String sBnkNo) {
		this.sBnkNo = sBnkNo;
	}
	public String getrBnkNo() {
		return rBnkNo;
	}
	public void setrBnkNo(String rBnkNo) {
		this.rBnkNo = rBnkNo;
	}
	public String gettActDt() {
		return tActDt;
	}
	public void settActDt(String tActDt) {
		this.tActDt = tActDt;
	}
	public String getCcyCod() {
		return ccyCod;
	}
	public void setCcyCod(String ccyCod) {
		this.ccyCod = ccyCod;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getFeeFlg() {
		return feeFlg;
	}
	public void setFeeFlg(String feeFlg) {
		this.feeFlg = feeFlg;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getTxnMod() {
		return txnMod;
	}
	public void setTxnMod(String txnMod) {
		this.txnMod = txnMod;
	}
	public String getPayBnk() {
		return payBnk;
	}

	public void setPayBnk(String payBnk) {
		this.payBnk = payBnk;
	}

	public String getpActTp() {
		return pActTp;
	}
	public void setpActTp(String pActTp) {
		this.pActTp = pActTp;
	}
	public String getpActNo() {
		return pActNo;
	}
	public void setpActNo(String pActNo) {
		this.pActNo = pActNo;
	}
	public String getPayNam() {
		return payNam;
	}
	public void setPayNam(String payNam) {
		this.payNam = payNam;
	}
	public String getRecBnk() {
		return recBnk;
	}
	public void setRecBnk(String recBnk) {
		this.recBnk = recBnk;
	}
	public String getrActTp() {
		return rActTp;
	}
	public void setrActTp(String rActTp) {
		this.rActTp = rActTp;
	}
	public String getrActNo() {
		return rActNo;
	}
	public void setrActNo(String rActNo) {
		this.rActNo = rActNo;
	}
	public String getRecNam() {
		return RecNam;
	}
	public void setRecNam(String recNam) {
		RecNam = recNam;
	}
	public String getTxnSts() {
		return txnSts;
	}
	public void setTxnSts(String txnSts) {
		this.txnSts = txnSts;
	}
	
	

}
