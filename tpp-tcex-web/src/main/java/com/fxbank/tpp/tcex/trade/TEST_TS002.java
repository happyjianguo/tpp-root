package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TESTTS002;
import com.fxbank.tpp.tcex.dto.esb.REP_TS002;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS002;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 商行通兑业务
 * @author liye
 *
 */
@Service("REQ_TESTTS002")
public class TEST_TS002 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REP_TESTTS002 repDto = new REP_TESTTS002();
		repDto.getRepBody().setBrno("1");
		repDto.getRepBody().setTownDate("2");
		repDto.getRepBody().setTownTraceno("3");
		return repDto;
	}
}
