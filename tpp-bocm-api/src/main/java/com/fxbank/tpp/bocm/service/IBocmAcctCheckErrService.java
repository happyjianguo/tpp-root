/**   
* @Title: IBocmAcctCheckErrService.java 
* @Package com.fxbank.tpp.bocm.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 上午9:11:29 
* @version V1.0   
*/
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
	
	List<BocmAcctCheckErrModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno, String date)throws SysTradeExecuteException;
	
	void insert(BocmAcctCheckErrModel model)throws SysTradeExecuteException;
	
	void delete(String date)throws SysTradeExecuteException;
}
