package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_10103_FEE 
* @Description: 手续费对账文件应答 
* @author YePuLiang
* @date 2019年6月22日 下午5:05:16 
*  
*/
public class REP_10103_FEE extends REP_BASE {
	
	private static final long serialVersionUID = -3159939908759734651L;
	
    @Deprecated
	public REP_10103_FEE() {
		super(null, 0, 0, 0);
        super.setCheckMac(false);
	}

    public REP_10103_FEE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.setCheckMac(false);
    }

	@FixedField(order = 5, len = 8, desc = "文件长度")
    private Integer filLen;

	@FixedField(order = 6, len = 7, cyc = "filTxt" ,desc = "总笔数")
    private Integer tolCnt;

	@FixedField(order = 7, len = 15, desc = "总金额")
    private Double tolAmt;
	
	@FixedField(order = 8, desc = "循环内容")
    private List<Detail> filTxt = new ArrayList<Detail>();
	
    public static class Detail implements Serializable{

		private static final long serialVersionUID = -4336857650958576705L;
		//本方交易流水号填农商行流水号，对方交易流水号填交行流水号（如果存在），发起方流水号必输
    	@FixedField(order = 81, len = 14, desc = "交易流水号")
    	private String TlogNo;
    	//对方交易流水号
    	@FixedField(order = 82, len = 14, desc = "交易流水号")
    	private String logNo;
    	//交易代码
    	@FixedField(order = 83, len = 5, desc = "交易代码")
    	private String thdCod;	
    	//业务类型
    	@FixedField(order = 84, len = 3, desc = "业务类型")
    	private String BbusTyp;
    	//发起行行号
    	@FixedField(order = 85, len = 12, desc = "发起行行号")
    	private String SbnkNo;
    	//接收行行号
    	@FixedField(order = 86, len = 12, desc = "接收行行号")
    	private String RbnkNo;
    	//第三方交易日期
    	@FixedField(order = 87, len = 8, desc = "第三方交易日期")
    	private String TactDt;	
    	//币种
    	@FixedField(order = 88, len = 3, desc = "币种")
    	private String ccyCod="CNY";
    	//交易金额
    	@FixedField(order = 89, len = 15, desc = "交易金额")
    	private Double txnAmt;		
    	//手续费收取方式
    	@FixedField(order = 810, len = 1, desc = "手续费手续方式")
    	private String feeFlg;
    	//开户行手续费
    	@FixedField(order = 811, len = 15, desc = "开户行手续费")
    	private Double fee;   	
    	//代理手续费
    	@FixedField(order = 812, len = 15, desc = "代理手续费")
    	private Double ProxyFee;    	
    	//代理手续费收取标志
    	@FixedField(order = 813, len = 15, desc = "代理手续费收取标志")
    	private String ProxyFlg;   	
    	//通存通兑业务模式、
    	@FixedField(order = 814, len = 1, desc = "通存通兑业务模式")
    	private String txnMod;
    	//付款人开户行行号
    	@FixedField(order = 815, len = 12, desc = "付款人开户行行号")
    	private String payBnk;
    	//付款人账户类型
    	@FixedField(order = 816, len = 1, desc = "付款人账户类型")
    	private String PactTp;
    	//付款人账号
    	@FixedField(order = 817, len = 32, desc = "付款人账号")
    	private String PactNo;
    	//付款人名称
    	@FixedField(order = 818, len = 30, desc = "付款人名称")
    	private String payNam;
    	//收款人开户行行号
    	@FixedField(order = 819, len = 12, desc = "收款人开户行行号")
    	private String recBnk;
    	//收款人账户类型
    	@FixedField(order = 820, len = 1, desc = "收款人账户类型")
    	private String RactTp;
    	//收款人账号
    	@FixedField(order = 821, len = 32, desc = "收款人账号")
    	private String RactNo;
    	//收款人名称
    	@FixedField(order = 822, len = 30, desc = "收款人名称")
    	private String RecNam;
    	//交易状态
    	@FixedField(order = 823, len = 1, desc = "交易状态")
    	private String txnSts;
		public String getTlogNo() {
			return TlogNo;
		}
		public void setTlogNo(String tlogNo) {
			TlogNo = tlogNo;
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
		public String getBbusTyp() {
			return BbusTyp;
		}
		public void setBbusTyp(String bbusTyp) {
			BbusTyp = bbusTyp;
		}
		public String getSbnkNo() {
			return SbnkNo;
		}
		public void setSbnkNo(String sbnkNo) {
			SbnkNo = sbnkNo;
		}
		public String getRbnkNo() {
			return RbnkNo;
		}
		public void setRbnkNo(String rbnkNo) {
			RbnkNo = rbnkNo;
		}
		public String getTactDt() {
			return TactDt;
		}
		public void setTactDt(String tactDt) {
			TactDt = tactDt;
		}
		public String getCcyCod() {
			return ccyCod;
		}
		public void setCcyCod(String ccyCod) {
			this.ccyCod = ccyCod;
		}
		public Double getTxnAmt() {
			return txnAmt;
		}
		public void setTxnAmt(Double txnAmt) {
			this.txnAmt = txnAmt;
		}
		public String getFeeFlg() {
			return feeFlg;
		}
		public void setFeeFlg(String feeFlg) {
			this.feeFlg = feeFlg;
		}
		public Double getFee() {
			return fee;
		}
		public void setFee(Double fee) {
			this.fee = fee;
		}
		public Double getProxyFee() {
			return ProxyFee;
		}
		public void setProxyFee(Double proxyFee) {
			ProxyFee = proxyFee;
		}
		public String getProxyFlg() {
			return ProxyFlg;
		}
		public void setProxyFlg(String proxyFlg) {
			ProxyFlg = proxyFlg;
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
		public String getPactTp() {
			return PactTp;
		}
		public void setPactTp(String pactTp) {
			PactTp = pactTp;
		}
		public String getPactNo() {
			return PactNo;
		}
		public void setPactNo(String pactNo) {
			PactNo = pactNo;
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
		public String getRactTp() {
			return RactTp;
		}
		public void setRactTp(String ractTp) {
			RactTp = ractTp;
		}
		public String getRactNo() {
			return RactNo;
		}
		public void setRactNo(String ractNo) {
			RactNo = ractNo;
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

	public Integer getFilLen() {
		return filLen;
	}

	public void setFilLen(Integer filLen) {
		this.filLen = filLen;
	}

	public Integer getTolCnt() {
		return tolCnt;
	}

	public void setTolCnt(Integer tolCnt) {
		this.tolCnt = tolCnt;
	}

	public Double getTolAmt() {
		return tolAmt;
	}

	public void setTolAmt(Double tolAmt) {
		this.tolAmt = tolAmt;
	}

	public List<Detail> getFilTxt() {
		return filTxt;
	}

	public void setFilTxt(List<Detail> filTxt) {
		this.filTxt = filTxt;
	}


}