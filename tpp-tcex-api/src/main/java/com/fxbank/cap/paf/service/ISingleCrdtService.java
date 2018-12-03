package com.fxbank.cap.paf.service;

import javax.validation.Valid;

import com.fxbank.cap.paf.model.QrySingleCapTradeModel;
import com.fxbank.cap.paf.model.SinglCrdtCapUpdModel;
import com.fxbank.cap.paf.model.SinglCrdtInfoModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

public interface ISingleCrdtService {

	/** 
	* @Title: CrdtInfoInit 
	* @Description: 公积金单笔付款登记信息初始化
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void CrdtInfoInit(@Valid SinglCrdtInfoModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: CrdtInfoHostCapUpd 
	* @Description: 公积金单笔付款更新本金记账信息
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void CrdtInfoHostCapUpd(@Valid SinglCrdtCapUpdModel record) throws SysTradeExecuteException;
	
	/** 
	* @Title: CrdtInfoHostIntUpd 
	* @Description: 积金单笔付款更新利息记账信息
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void CrdtInfoHostIntUpd() throws SysTradeExecuteException;
	
	/** 
	* @Title: queryCrdtInfoByPk 
	* @Description: 根据主键查询单笔收款信息 
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return QrySingleCapTradeModel    返回类型 
	* @throws 
	*/
	QrySingleCapTradeModel queryCrdtInfoByPk(@Valid QrySingleCapTradeModel model) throws SysTradeExecuteException;
	
}
