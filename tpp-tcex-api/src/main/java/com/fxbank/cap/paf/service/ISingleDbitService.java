package com.fxbank.cap.paf.service;

import javax.validation.Valid;

import com.fxbank.cap.paf.model.QrySingleCapTradeModel;
import com.fxbank.cap.paf.model.SinglDbitCapUpdModel;
import com.fxbank.cap.paf.model.SinglDbitInfoModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

public interface ISingleDbitService {

	/** 
	* @Title: DbitInfoInit 
	* @Description: 公积金单笔付款登记信息初始化
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void DbitInfoInit(@Valid SinglDbitInfoModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: DbitInfoHostCapUpd 
	* @Description: 公积金单笔付款更新本金记账信息
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void DbitInfoHostCapUpd(@Valid SinglDbitCapUpdModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: DbitInfoHostIntUpd 
	* @Description: 积金单笔付款更新利息记账信息
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void DbitInfoHostIntUpd() throws SysTradeExecuteException;
	
	/** 
	* @Title: queryDbitInfoByPk 
	* @Description: 根据主键查询单笔付款信息 
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return QrySingleCapTradeModel    返回类型 
	* @throws 
	*/
	QrySingleCapTradeModel queryDbitInfoByPk(@Valid QrySingleCapTradeModel model) throws SysTradeExecuteException;
}
