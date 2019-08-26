package com.fxbank.tpp.frms.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.frms.model.REQ_FRMS;

/** 
* @ClassName: IForwardToFRMSService 
* @Description: 风控系统调用接口
* @author YePuLiang
* @date 2019年8月23日 下午4:19:08 
*  
*/
public interface IForwardToFRMSService {
	/**
	 * 
	* @Title: sendToFRMS 
	* @Description: 调用风控系统接口
	* @param @param request
	* @param @param clazz
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return T    返回类型 
	* @throws
	 */
	public <T> T sendToFRMS(REQ_FRMS request,Class<T> clazz) throws SysTradeExecuteException;
}
