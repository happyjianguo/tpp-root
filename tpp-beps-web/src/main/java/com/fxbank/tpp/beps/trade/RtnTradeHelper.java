package com.fxbank.tpp.beps.trade;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionContext;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : 周勇沩
 * @description: 回执交易执行
 * @Date : 2020/2/21 17:27
 */
@Service("rtnTradeHelper")
public class RtnTradeHelper {

    private static Logger logger = LoggerFactory.getLogger(RtnTradeHelper.class);

    @Resource(name = "tradeExecutionFactory")
    private TradeExecutionStrategyFactory tradeExecutionFactory;

    @Resource
    private LogPool logPool;

    public void invoke(DataTransObject reqDto, String mesgType) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        String txCode = reqDto.getTxCode() + "_"
                + mesgType.substring(0, 8).replaceAll("\\.", "_").toUpperCase()
                + mesgType.substring(mesgType.length() - 2);
        myLog.info(logger, "回执交易执行交易码[" + txCode + "]");
        try {
            TradeExecutionStrategy tradeExecutionStrategy = tradeExecutionFactory.getTradeExecution(txCode);
            TradeExecutionContext tradeExecutionContext = new TradeExecutionContext(tradeExecutionStrategy);
            tradeExecutionContext.execute(reqDto);
        } catch (Exception e) {
            myLog.error(logger, "交易执行异常，获取交易执行类失败", e);
            throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
        }
    }

}
