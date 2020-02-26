package com.fxbank.tpp.beps.trade.pmts;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.beps.dto.pmts.CCMS_911_001_02;
import com.fxbank.tpp.beps.trade.RtnTradeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @description: 支付协议管理应答报文911回执
 * @author     : 周勇沩
 * @Date       : 2020/2/21 17:47
 */
@Service("CCMS_911_001_02_BEPS_35201")
public class P91102_35201 extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(P91102_35201.class);

    @Resource
    private LogPool logPool;

    @Resource
    private RtnTradeHelper rtnTradeHelper;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_911_001_02 ccms911 = (CCMS_911_001_02) dto;
        //TODO 数据库操作
        jedisPublishRtn(myLog, ccms911.getDscrdMsgNtfctn().getDscrdInf().getMsgId(), ccms911.getDscrdMsgNtfctn());
        return ccms911;
    }

}