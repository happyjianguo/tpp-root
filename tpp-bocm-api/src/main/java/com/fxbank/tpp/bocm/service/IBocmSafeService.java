package com.fxbank.tpp.bocm.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

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
	public String transPinToFX(MyLog myLog, String srcAccount,String dstAccount,String srcPinBlock) throws SysTradeExecuteException;
	
	/** 
	* @Title: transPin 
	* @Description: 密码转加密
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SafeModel    返回类型 
	* @throws 
	*/
	public String transPinToJH(MyLog myLog, String srcAccount,String dstAccount,String srcPinBlock) throws SysTradeExecuteException;
	
	/** 
	* @Title: verifyBocmMac
	* @Description: 校验交行请求mac 
	* @param @param myLog
	* @param @param dataToMAC
	* @param @param mac
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void verifyBocmMac(MyLog myLog,String dataToMAC,String mac) throws SysTradeExecuteException;
	
 	/**
   * @Description: 计算MAC
   * @Author: 周勇沩
   * @Date: 2019-06-26 14:18:51
   */
  public String calcBocmMac(MyLog myLog,String dataToMAC) throws SysTradeExecuteException;
	
	/**
	* @Title: updateMacKey 
	* @Description: MAC密钥更新
	* @param @param myLog
	* @param @param value
	* @param @param checkValue
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void updateMacKey(MyLog myLog,String keyValue,String checkValue) throws SysTradeExecuteException;
	
	/**
	* @Title: updatePinKey 
	* @Description: Pin密钥更新
	* @param @param myLog
	* @param @param value
	* @param @param checkValue
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void updatePinKey(MyLog myLog,String keyValue,String checkValue) throws SysTradeExecuteException;
	
}
