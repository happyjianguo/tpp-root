/**   
* @Title: REQ_10103.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 下午5:56:30 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REQ_10103 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月7日 下午5:56:30 
*  
*/
public class REQ_10103 extends REQ_BASE {

	private static final long serialVersionUID = 7852301380425987607L;

	private String filNam;

    @Deprecated
	public REQ_10103() {
		super(null, 0, 0, 0);
	}

    public REQ_10103(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.getHeader().settTxnCd("10103");
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-28s", this.filNam==null?"":this.filNam));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+60));
        this.filNam = sb.substring(i, i=i+28).trim();
    }

	public String getFilNam() {
		return filNam;
	}

	public void setFilNam(String filNam) {
		this.filNam = filNam;
	}

 
}
