package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceInitModel;
import com.fxbank.tpp.tcex.model.SndTraceUpdModel;
import com.fxbank.tpp.tcex.model.SndTraceQueryModel;
import com.fxbank.tpp.tcex.model.SndTraceRepModel;

/** 
* @ClassName: ISndTraceService 
* @Description: 往账处理
* @author Duzhenduo
* @date 2019年1月31日 上午10:26:13 
*  
*/
public interface ISndTraceService {

	/** 
	* @Title: sndTraceInit 
	* @Description: 往账登记
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void sndTraceInit(@Valid SndTraceInitModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: sndTraceUpd 
	* @Description: 往账更新 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void sndTraceUpd(@Valid SndTraceUpdModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: getSndTrace 
	* @Description: 查询往账流水 
	* @param @param myLog
	* @param @param begDate
	* @param @param endDate
	* @param @param minAmt
	* @param @param maxAmt
	* @param @param brnoFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<SndTraceQueryModel>    返回类型 
	* @throws 
	*/
	public List<SndTraceQueryModel> getSndTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException;

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
	SndTraceQueryModel getSndTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace) throws SysTradeExecuteException;

	/** 
	* @Title: replenishSndTrace 
	* @Description: 往账补账 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void replenishSndTrace(SndTraceRepModel record) throws SysTradeExecuteException;

	/** 
	* @Title: getCheckSndTrace 
	* @Description: 查询往账对账
	* @param @param myLog
	* @param @param sysDate
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<SndTraceQueryModel>    返回类型 
	* @throws 
	*/
	List<SndTraceQueryModel> getCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;

	/** 
	* @Title: getUploadCheckSndTrace 
	* @Description: 查询下载的往账对账文件
	* @param @param myLog
	* @param @param sysDate
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<SndTraceQueryModel>    返回类型 
	* @throws 
	*/
	List<SndTraceQueryModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;
	/** 
	* @Title: getSndTotalNum 
	* @Description: 查询往账对账总数
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getSndTotalNum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;
	/** 
	* @Title: getSndTotalSum 
	* @Description: 查询往账对账总金额
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getSndTotalSum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;
	/**
	 * 统计往帐条数
	 * @param date
	 * @param checkFlag
	 * @return
	 * @throws SysTradeExecuteException
	 */
	String getTraceNum(String date, String checkFlag) throws SysTradeExecuteException;

}
