package com.fxbank.tpp.bocm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @Description: 交行通讯应答基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:26:25
 */
public abstract class REP_BASE extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 6311109021156971900L;

	public REP_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@FixedField(order = 1, len = 1, desc = "响应类型")
	private String tmsgTyp;
	
	@FixedField(order = 2, len = 6, desc = "响应码")
	private String trspCd;
	
	@FixedField(order = 3, len = 30, desc = "响应信息")
	private String trspMsg;
	
	@FixedField(order = 4, len = 14, desc = "响应信息")
	private String rlogNo;
	
	public String getTmsgTyp() {
		return tmsgTyp;
	}
	public void setTmsgTyp(String tmsgTyp) {
		this.tmsgTyp = tmsgTyp;
	}
	public String getTrspCd() {
		return trspCd;
	}
	public void setTrspCd(String trspCd) {
		this.trspCd = trspCd;
	}
	public String getTrspMsg() {
		return trspMsg;
	}
	public void setTrspMsg(String trspMsg) {
		this.trspMsg = trspMsg;
	}
	public String getRlogNo() {
		return rlogNo;
	}
	public void setRlogNo(String rlogNo) {
		this.rlogNo = rlogNo;
	}
}
