package com.fxbank.tpp.tcex.trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import com.fxbank.tpp.tcex.dto.esb.REP_TESTTS003;
import com.fxbank.tpp.tcex.dto.esb.REP_TESTTS004;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 商行通存记账确认
 * @author liye
 *
 */
@Service("REQ_TESTTS003")
public class TEST_TS003 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REP_TESTTS003 repDto = new REP_TESTTS003();
		repDto.getRepBody().setTownDate(sdf1.format(new Date()));
		repDto.getRepBody().setTownTraceNo(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		repDto.getRepBody().setSts("1");
		return repDto;
	}
}
