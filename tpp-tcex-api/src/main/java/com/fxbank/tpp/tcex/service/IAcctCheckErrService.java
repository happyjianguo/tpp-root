package com.fxbank.tpp.tcex.service;

import java.util.List;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.model.AcctCheckErrModel;

/** 
* @ClassName: IAcctCheckErrService 
* @Description: 对账问题数据
* @author Duzhenduo
* @date 2019年1月31日 上午10:11:23 
*  
*/
public interface IAcctCheckErrService {

	List<AcctCheckErrModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno, String date)throws SysTradeExecuteException;
	
	void insert(AcctCheckErrModel model)throws SysTradeExecuteException;
	
	void delete(String date)throws SysTradeExecuteException;
}
