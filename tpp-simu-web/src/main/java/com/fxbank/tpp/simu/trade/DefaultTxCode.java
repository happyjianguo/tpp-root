package com.fxbank.tpp.simu.trade;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.tpp.simu.SimuApp;
import com.fxbank.tpp.simu.config.ESBConfig;
import com.fxbank.tpp.simu.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.simu.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：周勇沩
 * @date ：创建于 2019/12/26 15:38
 * @description：默认交易处理
 */
@Service("ESB_REQ_COMMON")
public class DefaultTxCode implements TradeExecutionStrategy {
    private static Logger logger = LoggerFactory.getLogger(DefaultTxCode.class);

    @Resource
    private ESBConfig esbConfig;

    @Override
    public ESB_BASE execute(String txCode, ESB_BASE dto) throws SysTradeExecuteException {
        txCode = txCode.replace("REQ","REP");

        Class<?> esbClass = null;
        for (String systemId : SimuApp.systemIds) {
            String className = esbConfig.getDtoPath() + "." + systemId + "." + txCode;
            try {
                esbClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error("查找类文件[" + className + "]");
                continue;
            }
        }
        if (esbClass == null) {
            logger.error("交易码[" + txCode + "]对应的类文件未定义");
            return null;
        }

        return BeanUtil.toBean(esbClass);

    }
}
