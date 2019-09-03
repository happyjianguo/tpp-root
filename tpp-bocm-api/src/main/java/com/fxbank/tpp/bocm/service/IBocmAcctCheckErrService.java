package com.fxbank.tpp.bocm.service;

import java.util.List;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmAcctCheckErrModel;

/** 
* @ClassName: IBocmAcctCheckErrService 
* @Description: 对账错误日志处理
* @author YePuLiang
* @date 2019年5月7日 上午9:11:29 
*  
*/
public interface IBocmAcctCheckErrService {
	/**
	* @Title: getListByDate 
	* @Description: 获取错误信息列表
	* @param @param myLog
	* @param @param sysTime
	* @param @param sysDate
	* @param @param sysTraceno
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<BocmAcctCheckErrModel>    返回类型 
	* @throws
	 */
	List<BocmAcctCheckErrModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno,String begDate,String endDate)throws SysTradeExecuteException;
	/**
	* @Title: insert 
	* @Description: 插入日志数据
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void insert(BocmAcctCheckErrModel model)throws SysTradeExecuteException;
	/**
	* @Title: delete 
	* @Description: 删除日志
	* @param @param date
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void delete(String date)throws SysTradeExecuteException;
}
