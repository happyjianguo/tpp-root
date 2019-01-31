package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;

/** 
* @ClassName: IForwardToESBService 
* @Description: 商行核心通信
* @author Duzhenduo
* @date 2019年1月31日 下午3:45:05 
*  
*/
public interface IForwardToESBService {
	
	/** 
	* @Title: sendToESB 
	* @Description: 发送请求给商行核心
	* @param @param esbModel
	* @param @param macData
	* @param @param clazz
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	public <T> T sendToESB(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException;

}
