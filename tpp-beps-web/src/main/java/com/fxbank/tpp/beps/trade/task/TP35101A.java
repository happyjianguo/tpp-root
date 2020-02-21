package com.fxbank.tpp.beps.trade.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.beps.dto.task.REP_TP35101A01;
import com.fxbank.tpp.beps.dto.task.REQ_TP35101A01;
import com.fxbank.tpp.beps.pmts.BEPS_352_001_01;
import com.fxbank.tpp.beps.service.IForwardToPmtsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 协议付款方签约交易处理
 * @author     : 周勇沩
 * @Date       : 2020/2/21 18:04
 */
@Service("REQ_TP35101A01")
public class TP35101A extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(TP35101A.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToPmtsService forwardToPmtsService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		myLog.info(logger, "协议付款方签约交易处理");
		REQ_TP35101A01 reqDto = (REQ_TP35101A01) dto;
		REQ_TP35101A01.REQ_BODY reqBody = reqDto.getReqBody();
		REP_TP35101A01 repDto = new REP_TP35101A01();

		//TODO 数据处理

		BEPS_352_001_01 beps352 = new BEPS_352_001_01(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		forwardToPmtsService.sendToPmtsRtnWait(beps352);

		return repDto;
	}
}
