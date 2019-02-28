package com.fxbank.tpp.esb.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.esb.common.ESB;
import com.fxbank.tpp.esb.common.TOWN;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;

@Service
public class MacService {

	private static Logger logger = LoggerFactory.getLogger(MacService.class);

	@Resource
	private HisuTSSCAPI hisuTSSCAPI;

	public String calc(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(ESB.macDeginId, ESB.macNodeId,
					ESB.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
			if (result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台计算MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return result.getMAC();
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台计算MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}
	
	public String calcTOWN(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(TOWN.macDeginId, TOWN.macNodeId,
					TOWN.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
			if (result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台计算MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return result.getMAC();
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台计算MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

}
