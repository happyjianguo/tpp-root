package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @Description: 交行通讯请求基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:54:57
 */
public abstract class REQ_BASE extends DataTransObject {
	
	@FixedField(order = 1, len = 5, desc = "交易代码")
	private String ttxnCd;

	@FixedField(order = 2, len = 3, desc = "业务类型")
	private String bbusTyp;

	@FixedField(order = 3, len = 12, desc = "发起行行号")
	private String sbnkNo;

	@FixedField(order = 4, len = 12, desc = "接收行行号")
	private String rbnkNo;

	@FixedField(order = 5, len = 8, desc = "交易日期")
	private Integer ttxnDat;

	@FixedField(order = 6, len = 6, desc = "交易时间")
	private Integer ttxnTim;

	@FixedField(order = 7, len = 14, desc = "发起行交易流水号")
	private String slogNo;

	public String getTtxnCd() {
		return ttxnCd;
	}

	public void setTtxnCd(String ttxnCd) {
		this.ttxnCd = ttxnCd;
	}

	public String getBbusTyp() {
		return bbusTyp;
	}

	public void setBbusTyp(String bbusTyp) {
		this.bbusTyp = bbusTyp;
	}



	public String getSbnkNo() {
		return sbnkNo;
	}

	public void setSbnkNo(String sbnkNo) {
		this.sbnkNo = sbnkNo;
	}

	public String getRbnkNo() {
		return rbnkNo;
	}

	public void setRbnkNo(String rbnkNo) {
		this.rbnkNo = rbnkNo;
	}

	public Integer getTtxnDat() {
		return ttxnDat;
	}

	public void setTtxnDat(Integer ttxnDat) {
		this.ttxnDat = ttxnDat;
	}

	public Integer getTtxnTim() {
		return ttxnTim;
	}

	public void setTtxnTim(Integer ttxnTim) {
		this.ttxnTim = ttxnTim;
	}

	public String getSlogNo() {
		return slogNo;
	}

	public void setSlogNo(String slogNo) {
		this.slogNo = slogNo;
	}


	
	
	

}
