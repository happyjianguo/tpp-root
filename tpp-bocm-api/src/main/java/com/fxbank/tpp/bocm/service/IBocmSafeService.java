/**   
* @Title: IBocmSafeService.java 
* @Package com.fxbank.tpp.bocm.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:53:50 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmSafeModel;

/** 
* @ClassName: IBocmSafeService 
* @Description: 加密服务
* @author YePuLiang
* @date 2019年5月23日 下午3:53:50 
*  
*/
public interface IBocmSafeService {

	/** 
	* @Title: transPin 
	* @Description: 密码转加密
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public BocmSafeModel transPin(BocmSafeModel model) throws SysTradeExecuteException;
	
	/** 
	* @Title: encryptPwd 
	* @Description: 密码加密 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public BocmSafeModel encryptPwd(BocmSafeModel model) throws SysTradeExecuteException;
	
	/** 
	* @Title: genKey 
	* @Description: 获取工作密钥 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public BocmSafeModel genKey(BocmSafeModel model) throws SysTradeExecuteException;
	
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
	public void verifyBocmMac(MyLog myLog,byte[] dataToMAC,String mac) throws SysTradeExecuteException;

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
	public String calcBocm(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
	
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
