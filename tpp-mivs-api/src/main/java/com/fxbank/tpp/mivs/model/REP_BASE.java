package com.fxbank.tpp.mivs.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;
import com.fxbank.cip.base.model.ModelBase;

/**
 * @Description: 交行通讯应答基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:26:25
 */
public abstract class REP_BASE extends ModelBase implements Serializable,FIXP_SERIAL{
	
	private static final long serialVersionUID = 6311109021156971900L;

	public REP_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private REP_HEADER header = new REP_HEADER();

	public REP_HEADER getHeader() {
		return header;
	}

	public void setHeader(REP_HEADER header) {
		this.header = header;
	}
	
}
