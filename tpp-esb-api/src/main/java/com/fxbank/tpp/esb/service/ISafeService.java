package com.fxbank.tpp.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.model.tcex.SafeModel;

/** 
* @ClassName: ISafeService 
* @Description: 加密平台服务
* @author Duzhenduo
* @date 2019年1月31日 下午3:44:38 
*  
*/
public interface ISafeService {

	/** 
	* @Title: transPin 
	* @Description: 密码转加密
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public SafeModel transPin(SafeModel model) throws SysTradeExecuteException;
	
	/** 
	* @Title: encryptPwd 
	* @Description: 密码加密 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public SafeModel encryptPwd(SafeModel model) throws SysTradeExecuteException;
	
	/** 
	* @Title: genKey 
	* @Description: 获取工作密钥 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public SafeModel genKey(SafeModel model) throws SysTradeExecuteException;
	
	/** 
	* @Title: verifyTownMac 
	* @Description: 校验村镇请求mac 
	* @param @param myLog
	* @param @param dataToMAC
	* @param @param mac
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void verifyTownMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;

	/** 
	* @Title: verifyCityMac 
	* @Description: 校验柜面请求mac
	* @param @param myLog
	* @param @param dataToMAC
	* @param @param mac
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void verifyCityMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;
	
	/** 
	* @Title: calcTOWN 
	* @Description: 计算村镇请求mac 
	* @param @param myLog
	* @param @param dataToMAC
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String calcTOWN(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
	
	/** 
	* @Title: calcCITY 
	* @Description: 计算柜面请求mac
	* @param @param myLog
	* @param @param dataToMAC
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String calcCITY(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
}
