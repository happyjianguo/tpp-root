/**   
* @Title: IMacService.java 
* @Package com.fxbank.tpp.bocm.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月27日 下午3:54:30 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: IMacService 
* @Description: MAC生成接口
* @author YePuLiang
* @date 2019年5月27日 下午3:54:30 
*  
*/
public interface IBocmMacService {
	
	public String calc(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
	
	public String calcBOCM(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;

}
