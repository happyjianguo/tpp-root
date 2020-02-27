package com.fxbank.tpp.simu.controller;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.tpp.simu.route.TradeMainFlowFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: TradeDispatcherController
 * @Description: 交易处理控制器
 * @author ZhouYongwei
 * @date 2018年4月3日 上午11:10:05
 * 
 */
@Component
public class TradeDispatcherBase {

	@Resource(name = "tradeMainFlowFacade")
	private TradeMainFlowFacade tradeMainFlowFacade;

	public ESB_BASE txMainFlowController(String txCode, ESB_BASE reqDto) throws SysTradeExecuteException {
		return tradeMainFlowFacade.invoke(txCode, reqDto);
	}

}
