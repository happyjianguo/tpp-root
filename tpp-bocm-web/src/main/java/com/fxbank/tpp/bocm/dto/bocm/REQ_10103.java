/**   
* @Title: REQ_10103.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 上午10:07:41 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_10103 
* @Description: 对账文件获取
* @author YePuLiang
* @date 2019年5月6日 上午10:07:41 
*  
*/
public class REQ_10103 extends REQ_BASE {
	
	@FixedField(order = 8, len = 28, desc = "文件名称")
	private String filNam;
	

	public String getFilNam() {
		return filNam;
	}

	public void setFilNam(String filNam) {
		this.filNam = filNam;
	}
	
	

}
