package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_330_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 机构异常核查通知交易
 * @Author: 王鹏
 * @Date: 2019/6/6 15:52
 */
@Service("MIVS_330_001_01")
public class AbnmlAgtInfoVrfctnInfNtfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_330_001_01 mivs330 = (MIVS_330_001_01) dto;
        myLog.info(logger, "收到人行机构异常核查通知交易");
        byte[] b330 = SerializeUtil.serialize(mivs330);
        String channel = "330_" + mivs330.getHead().getMesgID();   //TODO 拼接原报文三要素
        myLog.info(logger, "330报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b330);
        myLog.info(logger, "发布至redis成功");
        return mivs330;
    }

}