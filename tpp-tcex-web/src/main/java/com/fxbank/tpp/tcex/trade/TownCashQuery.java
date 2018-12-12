package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR005;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR005;

/**
 * 村镇头寸查询
 * @author liye
 *
 */
public class TownCashQuery implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityDcHistory.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		REQ_TR005 reqDto = (REQ_TR005) dto;
		String brno = reqDto.getReqBody().getBrnoFlag();
		//调用核心接口查询头寸余额
		
		REP_TR005 repDto = new REP_TR005();
		repDto.getRepBody().setBal("10000");
		
		return repDto;
	}

}
