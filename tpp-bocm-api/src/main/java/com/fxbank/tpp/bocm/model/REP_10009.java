package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REP_10009 
* @Description: 个人储蓄抹帐业务
* @author Duzhenduo
* @date 2019年4月16日 上午10:18:39 
*  
*/
public class REP_10009 extends REP_BASE {

	private static final long serialVersionUID = -8881773892007776221L;

    @Deprecated
	public REP_10009() {
		super(null, 0, 0, 0);
	}

    public REP_10009(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+51));
    }

}