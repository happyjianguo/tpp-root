package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_331_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业异常核查通知报文
 * @Author: 王鹏
 * @Date: 2019/6/6 16:23
 */
@Service("MIVS_331_001_01")
public class AbnmlCoInfoVrfctnInfNtfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_331_001_01 mivs331 = (MIVS_331_001_01) dto;
        myLog.info(logger, "收到人行企业异常核查通知报文");
        byte[] b331 = SerializeUtil.serialize(mivs331);
        String channel = "331_" + mivs331.getHead().getMesgID();   //TODO 拼接原报文三要素
        myLog.info(logger, "331报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b331);
        myLog.info(logger, "发布至redis成功");
        return mivs331;
    }

}