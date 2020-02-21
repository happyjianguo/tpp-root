package com.fxbank.tpp.beps.trade.pmts;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.beps.dto.pmts.CCMS_900_001_02;
import com.fxbank.tpp.beps.trade.RtnTradeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 通讯级确认应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-23 21:45:09
 */
@Service("CCMS_900_001_02")
public class P90002 extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(P90002.class);

    @Resource
    private LogPool logPool;

    @Resource
    private RtnTradeHelper rtnTradeHelper;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_900_001_02 ccms900 = (CCMS_900_001_02) dto;
        rtnTradeHelper.invoke(dto, ccms900.getCmonConf().getOrgnlGrpHdr().getOrgnlMT());
        super.jedisPublishRtn(myLog, ccms900.getCmonConf().getOrgnlGrpHdr().getOrgnlMsgId(), ccms900.getCmonConf());
        return ccms900;
    }

}