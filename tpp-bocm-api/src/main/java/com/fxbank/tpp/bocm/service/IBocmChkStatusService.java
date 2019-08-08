package com.fxbank.tpp.bocm.service;

import java.util.List;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.BocmChkStatusModel;

/** 
* @ClassName: IBocmChkStatusService 
* @Description: 对账状态处理
* @author YePuLiang
* @date 2019年7月2日 上午9:17:54 
*  
*/
public interface IBocmChkStatusService {
	/** 
	* @Title: chkStatusInit 
	* @Description: 初始化数据
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void chkStatusInit(BocmChkStatusModel record) throws SysTradeExecuteException; 
	/** 
	* @Title: chkStatusUpd 
	* @Description: 更新对账状态
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void chkStatusUpd(BocmChkStatusModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: selectByDate 
	* @Description: 通过日期获取对账状态
	* @param @param date
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public BocmChkStatusModel selectByDate(String date) throws SysTradeExecuteException; 
	
	/** 
	* @Title: selectByDate 
	* @Description: 通过日期获取对账状态
	* @param @param date
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public List<BocmChkStatusModel> selectByDate(MyLog myLog,String begDate,String endDate,String state) throws SysTradeExecuteException; 
}
