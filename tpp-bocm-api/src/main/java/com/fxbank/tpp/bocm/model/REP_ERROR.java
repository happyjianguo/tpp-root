package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;

/**
 * @Description: 用于生成错误应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-15 14:44:19
 */
public class REP_ERROR extends REP_BASE {

	private static final long serialVersionUID = 1793661948627769203L;

	@Deprecated
	public REP_ERROR() {
		super(null, 0, 0, 0);
	}
    
	public REP_ERROR(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}



}