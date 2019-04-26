/**   
* @Title: IBocmRcvTraceService.java 
* @Package com.fxbank.tpp.bocm.service 
* @Description: 交行来账流水保存
* @author YePuLiang
* @date 2019年4月16日 上午8:28:39 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.service;

import javax.validation.Valid;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmRcvTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmRcvTraceQueryModel;
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
	public BocmRcvTraceQueryModel getConfirmTrace(MyLog myLog,String townDate,String townTraceno)throws SysTradeExecuteException;

}
