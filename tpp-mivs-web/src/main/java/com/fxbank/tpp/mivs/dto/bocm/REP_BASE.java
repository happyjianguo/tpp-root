package com.fxbank.tpp.mivs.dto.bocm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;

/**
 * @Description: 交行通讯应答基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:55:52
 */
public abstract class REP_BASE extends DataTransObject implements FIXP_SERIAL{
	
	private REP_HEADER header = new REP_HEADER();

	public REP_HEADER getHeader() {
		return header;
	}

	public void setHeader(REP_HEADER header) {
		this.header = header;
	}
	
}
