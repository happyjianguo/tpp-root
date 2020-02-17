package com.fxbank.tpp.beps.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.beps.model.bepsmodel.CcmsFreeinfoModel;

public interface ICcmsFreeinfoService {
	/**
	 * 
	 * @描述:   自由格式登记
	 * @方法名: ccmsFreeinfoIns
	 * @param ccmsFreeinfoModel
	 * @return
	 * @throws SysTradeExecuteException
	 * @返回类型 int
	 * @创建人 lit
	 * @创建时间 2020年1月17日上午10:16:47
	 * @修改人 lit
	 * @修改时间 2020年1月17日上午10:16:47
	 * @修改备注
	 * @since
	 * @throws
	 */
	public int ccmsFreeinfoIns(CcmsFreeinfoModel ccmsFreeinfoModel) throws SysTradeExecuteException;

}
