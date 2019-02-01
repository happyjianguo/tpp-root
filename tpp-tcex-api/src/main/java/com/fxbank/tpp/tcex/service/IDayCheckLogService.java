package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.DayCheckLogInitModel;

/** 
* @ClassName: IDayCheckLogService 
* @Description: 对账 
* @author Duzhenduo
* @date 2019年1月31日 上午10:11:23 
*  
*/
public interface IDayCheckLogService {

	/** 
	* @Title: dayCheckLogInit 
	* @Description: 对账登记 
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException; 
	
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
	List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate,String direction) throws SysTradeExecuteException;

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
