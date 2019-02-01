package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;

/** 
* @ClassName: IForwardToTownService 
* @Description: 村镇通信
* @author Duzhenduo
* @date 2019年1月31日 下午3:45:54 
*  
*/
public interface IForwardToTownService {
	
	/** 
	* @Title: sendToTown 
	* @Description: 发送请求给村镇 
	* @param @param esbModel
	* @param @param macData
	* @param @param clazz
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	public <T> T sendToTown(ESB_BASE esbModel,Object macData,Class<T> clazz) throws SysTradeExecuteException;
}
