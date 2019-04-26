package com.fxbank.tpp.bocm.service;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;

/** 
* @ClassName: ISndTraceService 
* @Description: 交行往账处理
* @author YePuLiang
* @date 2019年4月15日 上午10:26:13 
*  
*/
public interface IBocmSndTraceService {
	/** 
	* @Title: sndTraceInit 
	* @Description: 往账登记
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void sndTraceInit(@Valid BocmSndTraceInitModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: sndTraceUpd 
	* @Description: 往账更新 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void sndTraceUpd(@Valid BocmSndTraceUpdModel record) throws SysTradeExecuteException;
	/** 
	* @Title: getSndTraceByKey 
	* @Description: 通过主键查询往账流水
	* @param @param myLog
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param sysDate
	* @param @param settleDate
	* @param @param platTrace
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return SndTraceQueryModel    返回类型 
	* @throws 
	*/
	BocmSndTraceQueryModel getSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException;

}
