package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_900_001_02;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/16 16:26
 */
@Service("CCMS_900_001_02")
public class ComnConf extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComnConf.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_900_001_02 ccms900 = (CCMS_900_001_02) dto;
        myLog.info(logger, "收到人行900应答报文,进行同步处理");
        byte[] b900 = SerializeUtil.serialize(ccms900);
        String channel = "900_" + ccms900.getCmonConf().getGrpHdr().getMsgId();
        myLog.info(logger, "900报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b900);
        myLog.info(logger, "发布至redis成功");
        return ccms900;
    }

}