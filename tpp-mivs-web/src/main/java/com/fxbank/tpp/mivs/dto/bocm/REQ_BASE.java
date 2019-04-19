package com.fxbank.tpp.mivs.dto.bocm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;

/**
 * @Description: 交行通讯请求基础类
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:54:57
 */
public abstract class REQ_BASE extends DataTransObject implements FIXP_SERIAL{
	
	private REQ_HEADER header = new REQ_HEADER();

	public REQ_HEADER getHeader() {
		return header;
	}

	public void setHeader(REQ_HEADER header) {
		this.header = header;
	}

}
