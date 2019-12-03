package com.fxbank.tpp.mivs.trade.mivs;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_990_001_02;
import com.fxbank.tpp.mivs.util.SerializeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 通讯级确认应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-23 21:45:09
 */
@Service("CCMS_990_001_02")
public class ComConf extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_990_001_02 ccms990 = (CCMS_990_001_02) dto;
        myLog.info(logger, "收到人行通讯级应答报文,进行同步处理");
        byte[] b990 = SerializeUtil.serialize(ccms990.getComConf());
        String channel = "990_" + ccms990.getComConf().getConfInf().getMsgId();
        myLog.info(logger, "990报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b990);
        myLog.info(logger, "发布至redis成功");
        return ccms990;
    }

}