package com.fxbank.tpp.mivs.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.REP_BASE;
import com.fxbank.tpp.mivs.model.REQ_BASE;
import com.fxbank.tpp.mivs.service.IForwardToMivsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(version = "1.0.0")
public class ForwardToMivsService implements IForwardToMivsService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToMivsService.class);

	@Override
	public <T extends REP_BASE> T sendToMivs(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		reqBase.getHeader().settTxnDat(reqBase.getSysDate());
		reqBase.getHeader().settTxnTim(reqBase.getSysTime());
		reqBase.getHeader()
				.setsLogNo(String.format("%06d%08d", reqBase.getSysDate() % 1000000, reqBase.getSysTraceno()));
		T repModel = null;
		/**
		try {
			repModel = mivsClient.comm(myLog, reqBase, clazz);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} catch (Exception e) {
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
					e.getMessage());
			myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e);
			throw e1;
		}
		**/
		REP_BASE repBase = repModel;
		if (repBase == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} else {
			String rspCode = repBase.getHeader().gettRspCd();
			String rspMsg = repBase.getHeader().gettRspMsg();
			if (repBase.getHeader().gettMsgTyp().equals("E") || !rspCode.equals("JH0000")) { // 交行返回失败
				SysTradeExecuteException e = new SysTradeExecuteException(rspCode, rspMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		return repModel;
	}
}
