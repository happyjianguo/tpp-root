package com.fxbank.tpp.bocm.service;

import java.util.List;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceRepModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceUpdModel;

/** 
* @ClassName: IBocmRcvTraceService 
* @Description: 交行来账处理
* @author YePuLiang
* @date 2019年4月16日 上午8:28:39 
*  
*/
public interface IBocmRcvTraceService {
	
	/**
	 * 
	* @Title: rcvTraceInit 
	* @Description: 来账登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void rcvTraceInit(@Valid BocmRcvTraceInitModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: rcvTraceUpd 
	* @Description: 来账更新 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void rcvTraceUpd(@Valid BocmRcvTraceUpdModel record) throws SysTradeExecuteException;
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
	public BocmRcvTraceQueryModel getConfirmTrace(MyLog myLog,int townDate,String townTraceno)throws SysTradeExecuteException;
	
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
	public List<BocmRcvTraceQueryModel> getRcvTrace(MyLog myLog,String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag) throws SysTradeExecuteException;

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
	public List<BocmRcvTraceQueryModel> getCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno, String date)throws SysTradeExecuteException;

	/** 
	* @Title: replenishRcvTrace 
	* @Description: 来账补账 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void replenishRcvTrace(BocmRcvTraceRepModel record)throws SysTradeExecuteException;

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
	BocmRcvTraceQueryModel getRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
			Integer settleDate, Integer platTrace)throws SysTradeExecuteException;
	
	/** 
	* @Title: getBocmRcvTraceByKey 
	* @Description: 通过交行日期流水查询来账 
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
	BocmRcvTraceQueryModel getBocmRcvTraceByKey(MyLog myLog, Integer sysTime, Integer sysTraceno, Integer sysDate,
		 String bocmTrace)throws SysTradeExecuteException;

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
	List<BocmRcvTraceQueryModel> getUploadCheckRcvTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date)throws SysTradeExecuteException;

	/**
	 * 获取来账统计信息
	 * @param date
	 * @param checkFlag
	 * @return
	 * @throws SysTradeExecuteException
	 */
	String getTraceNum(String date,String checkFlag)throws SysTradeExecuteException;
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
	String getRcvTotalChkSum(MyLog myLog, String date) throws SysTradeExecuteException;
	
	

}
