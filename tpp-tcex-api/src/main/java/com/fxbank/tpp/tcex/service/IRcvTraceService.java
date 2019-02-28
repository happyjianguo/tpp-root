package com.fxbank.tpp.tcex.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceRepModel;
/** 
* @ClassName: IRcvTraceService 
* @Description: 来账处理 
* @author Duzhenduo
* @date 2019年1月31日 上午10:18:51 
*  
*/
public interface IRcvTraceService {

	/** 
	* @Title: rcvTraceInit 
	* @Description: 来账登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void rcvTraceInit(@Valid RcvTraceInitModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: rcvTraceUpd 
	* @Description: 来账更新 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void rcvTraceUpd(@Valid RcvTraceUpdModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: getRcvTrace 
	* @Description: 来账查询
	* @param @param myLog
	* @param @param begDate
	* @param @param endDate
	* @param @param minAmt
	* @param @param maxAmt
	* @param @param brnoFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<RcvTraceQueryModel>    返回类型 
	* @throws 
	*/
	public List<RcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException;
	
	/** 
	* @Title: getConfirmTrace 
	* @Description: 来账确认查询 
	* @param @param myLog
	* @param @param townDate
	* @param @param townTraceno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return RcvTraceQueryModel    返回类型 
	* @throws 
	*/
	public RcvTraceQueryModel getConfirmTrace(MyLog myLog,String townDate,String townTraceno)throws SysTradeExecuteException;

	/** 
	* @Title: getCheckRcvTrace 
	* @Description: 来账对账查询 
	* @param @param myLog
	* @param @param sysDate
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<RcvTraceQueryModel>    返回类型 
	* @throws 
	*/
	public List<RcvTraceQueryModel> getCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno, String date)throws SysTradeExecuteException;

	/** 
	* @Title: replenishRcvTrace 
	* @Description: 来账补账 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void replenishRcvTrace(RcvTraceRepModel record)throws SysTradeExecuteException;

	/** 
	* @Title: getRcvTraceByKey 
	* @Description: 通过主键查询来账 
	* @param @param myLog
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param sysDate
	* @param @param settleDate
	* @param @param platTrace
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return RcvTraceQueryModel    返回类型 
	* @throws 
	*/
	RcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace)throws SysTradeExecuteException;

	/** 
	* @Title: getUploadCheckRcvTrace 
	* @Description: 查询下载的来账对账文件
	* @param @param myLog
	* @param @param sysDate
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<RcvTraceQueryModel>    返回类型 
	* @throws 
	*/
	List<RcvTraceQueryModel> getUploadCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date)throws SysTradeExecuteException;
	/** 
	* @Title: getRcvTotalNum 
	* @Description: 查询来账对账总数 
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getRcvTotalNum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;
	/** 
	* @Title: getRcvTotalSum 
	* @Description: 查询来账对账总金额 
	* @param @param myLog
	* @param @param date
	* @param @param dcFlag
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getRcvTotalSum(MyLog myLog, String date,String dcFlag) throws SysTradeExecuteException;
	/**
	 * 获取来账统计信息
	 * @param date
	 * @param checkFlag
	 * @return
	 * @throws SysTradeExecuteException
	 */
	String getTraceNum(String date,String checkFlag)throws SysTradeExecuteException;

}
