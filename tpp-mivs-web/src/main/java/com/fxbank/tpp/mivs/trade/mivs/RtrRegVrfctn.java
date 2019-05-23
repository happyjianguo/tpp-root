package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_325_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/20 16:28
 */
@Service("MIVS_325_001_01")
public class RtrRegVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_325_001_01 mivs325 = (MIVS_325_001_01) dto;
        myLog.info(logger, "收到登记信息联网核查应答报文,进行同步处理");
        byte[] b321 = SerializeUtil.serialize(mivs325);
        String channel = "325_" + mivs325.getHead().getMesgID();   //TODO 拼接原报文三要素
        myLog.info(logger, "325报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b321);
        myLog.info(logger, "发布至redis成功");
        return mivs325;
    }

}