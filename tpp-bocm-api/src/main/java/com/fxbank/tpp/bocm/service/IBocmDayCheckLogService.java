/**   
* @Title: IBocmDayCheckLogService.java 
* @Package com.fxbank.tpp.bocm.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 上午9:25:41 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmDayCheckLogInitModel;

/** 
* @ClassName: IBocmDayCheckLogService 
* @Description: 对账
* @author YePuLiang
* @date 2019年5月7日 上午9:25:41 
*  
*/
public interface IBocmDayCheckLogService {

	/** 
	* @Title: dayCheckLogInit 
	* @Description: 对账登记 
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void dayCheckLogInit(@Valid BocmDayCheckLogInitModel model) throws SysTradeExecuteException; 
	
	/** 
	* @Title: getDayCheckLog 
	* @Description: 查询对账日志 
	* @param @param myLog
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param platDate
	* @param @param direction
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<DayCheckLogInitModel>    返回类型 
	* @throws 
	*/
	List<BocmDayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate,String direction) throws SysTradeExecuteException;

	/** 
	* @Title: delete 
	* @Description: 删除对账日志
	* @param @param direction
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(String direction)throws SysTradeExecuteException; 
}
