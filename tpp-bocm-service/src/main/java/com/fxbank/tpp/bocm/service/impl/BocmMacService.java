package com.fxbank.tpp.bocm.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.common.BOCM;
import com.fxbank.tpp.bocm.common.ESB;
import com.fxbank.tpp.bocm.service.IBocmMacService;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;

@Service(version = "1.0.0")
public class BocmMacService implements IBocmMacService{

	private static Logger logger = LoggerFactory.getLogger(BocmMacService.class);

	@Resource
	private HisuTSSCAPI hisuTSSCAPI;

	@Override
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
	
	@Override
	public String calcBOCM(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(BOCM.macDeginId, BOCM.macNodeId,
					BOCM.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
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
