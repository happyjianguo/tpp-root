package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_323_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 纳税信息联网核查应答报文
 * @Author: 王鹏
 * @Date: 2019/4/30 7:50
 */
@Service("MIVS_323_001_01")
public class RtrTxPmtVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrTxPmtVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_323_001_01 mivs323 = (MIVS_323_001_01) dto;
        myLog.info(logger, "收到人行纳税信息联网核查应答报文,进行同步处理");
        byte[] b323 = SerializeUtil.serialize(mivs323);
        String channel = "323_" + mivs323.getHead().getMesgID();   //TODO 拼接原报文三要素
        myLog.info(logger, "323报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b323);
        myLog.info(logger, "发布至redis成功");
        return mivs323;
    }

}