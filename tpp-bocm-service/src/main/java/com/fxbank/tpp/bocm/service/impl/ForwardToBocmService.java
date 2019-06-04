package com.fxbank.tpp.bocm.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_BASE;
import com.fxbank.tpp.bocm.model.REQ_BASE;
import com.fxbank.tpp.bocm.netty.bocm.BocmClient;
import com.fxbank.tpp.bocm.service.IBocmMacService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;

@Service(version = "1.0.0")
public class ForwardToBocmService implements IForwardToBocmService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToBocmService.class);

	@Resource
	private BocmClient bocmClient;
	
	@Autowired
    private IBocmMacService macService;


	@Override
	public <T extends REP_BASE> T sendToBocm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		reqBase.setTtxnDat(reqBase.getSysDate());
		reqBase.setTtxnTim(reqBase.getSysTime());
		reqBase.setSlogNo(String.format("%06d%08d", reqBase.getSysDate() % 1000000, reqBase.getSysTraceno()));
		T repModel = null;
		
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(reqBase,"UTF-8"));
		myLog.info(logger, "组包发送交行报文");
		String jsonReq = fixPack.toString();
		//需要生成MAC
//		byte[] macBytes = jsonReq.getBytes();
//		String mac = macService.calcBOCM(reqBase.getMylog(),macBytes);
//		myLog.info(logger, "组包发送交行报文MAC:"+mac);
		String mac = "FFFFFFFFFFFFFFFF";
		try {
			repModel = bocmClient.comm(myLog, reqBase, clazz,mac);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} catch (Exception e) {
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
					e.getMessage());
			myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e);
			throw e1;
		}
		REP_BASE repBase = repModel;
		if (repBase == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} else {
			String rspCode = repBase.getTrspCd();
			String rspMsg = repBase.getTrspMsg();
			//TODO 本行模拟交行交易返回状态码判断
			if(rspCode.equals("FX0000")){
				return repModel;
			}
			//结束
			if (repBase.getTmsgTyp().equals("E") || !rspCode.equals("JH0000")) { // 交行返回失败
				SysTradeExecuteException e = new SysTradeExecuteException(rspCode, rspMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		return repModel;
	}
}
