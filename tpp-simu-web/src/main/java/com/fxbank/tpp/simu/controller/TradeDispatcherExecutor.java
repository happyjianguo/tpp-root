package com.fxbank.tpp.simu.controller;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.tpp.simu.config.LogPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author : 周勇沩
 * @description: 交易处理入口类
 * @Date : 2019/4/23 21:12
 */
@Controller
public class TradeDispatcherExecutor {

    private static Logger logger = LoggerFactory.getLogger(TradeDispatcherExecutor.class);

    @Resource
    private TradeDispatcherBase tradeDispatcherBase;

    @Resource
    private LogPool logPool;

    public ESB_BASE txMainFlowController(String txCode,ESB_BASE dtoBase) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        myLog.debug(logger, "交易流程执行开始...");
        ESB_BASE reqDto = dtoBase;
        ESB_BASE repDto = tradeDispatcherBase.txMainFlowController(txCode,reqDto);
        myLog.debug(logger, "交易流程执行完毕...");
        return repDto;
    }

}
