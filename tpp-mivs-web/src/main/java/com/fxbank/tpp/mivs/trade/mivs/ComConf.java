package com.fxbank.tpp.mivs.trade.mivs;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_990_001_02;
import com.fxbank.tpp.mivs.util.SerializeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("ccms_990_001_02")
public class ComConf implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private MyJedis myJedis;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        CCMS_990_001_02 ccms990 = (CCMS_990_001_02) dto;
        try(Jedis jedis=myJedis.connect()){
            byte[] bCcms990 = SerializeUtil.serialize(ccms990.getComConf());
            String msgId = ccms990.getComConf().getConfInf().getMsgId();
            jedis.publish("990".getBytes(), bCcms990);
        }
        return ccms990;
    }

}