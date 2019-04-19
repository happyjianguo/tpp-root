package com.fxbank.tpp.mivs.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;
import com.fxbank.cip.base.model.ModelBase;

/**
 * @Description: 交行通讯请求基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:26:12
 */
public abstract class REQ_BASE extends ModelBase implements Serializable,FIXP_SERIAL{
	
	private static final long serialVersionUID = -6652288226005628489L;

	public REQ_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private REQ_HEADER header = new REQ_HEADER();

	public REQ_HEADER getHeader() {
		return header;
	}

	public void setHeader(REQ_HEADER header) {
		this.header = header;
	}

}
