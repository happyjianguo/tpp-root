package com.fxbank.tpp.bocm.trade.bocm;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.bocm.REP_10101;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * 网银互联行名行号的查询
 * */
@Service("REQ_10101")
public class QR_FxBal implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(QR_FxBal.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_10101 req = (REQ_10101) dto;
		//调用ESB余额查询
		REP_10101 rep = new REP_10101();
		return rep;
	}
}