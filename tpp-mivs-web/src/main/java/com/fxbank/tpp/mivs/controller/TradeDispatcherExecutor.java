package com.fxbank.tpp.mivs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.controller.TradeDispatcherBase;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.dto.mivs.DTO_BASE;

@Controller
public class TradeDispatcherExecutor {

	private static Logger logger = LoggerFactory.getLogger(TradeDispatcherExecutor.class);

	@Resource
	private TradeDispatcherBase tradeDispatcherBase;
	
	@Resource
	private LogPool logPool;
	
	public DataTransObject txMainFlowController(DTO_BASE dtoBase) {
		MyLog myLog = logPool.get();
		myLog.debug(logger, "交易流程执行开始...");
		DataTransObject reqDto = dtoBase;
		DataTransObject repDto = tradeDispatcherBase.txMainFlowController(reqDto);
		myLog.debug(logger, "交易流程执行完毕...");
		return repDto;
	}

}
