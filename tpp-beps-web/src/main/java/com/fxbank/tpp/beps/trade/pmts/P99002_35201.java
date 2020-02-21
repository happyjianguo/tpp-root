package com.fxbank.tpp.beps.trade.pmts;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.beps.dto.pmts.CCMS_990_001_02;
import com.fxbank.tpp.beps.trade.RtnTradeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 支付协议管理应答报文990回执
 * @author     : 周勇沩
 * @Date       : 2020/2/21 17:45
 */
@Service("CCMS_990_001_02_BEPS_35201")
public class P99002_35201 extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(P99002_35201.class);

    @Resource
    private LogPool logPool;

    @Resource
    private RtnTradeHelper rtnTradeHelper;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_990_001_02 ccms990 = (CCMS_990_001_02) dto;
        //TODO 数据库操作
        return ccms990;
    }

}