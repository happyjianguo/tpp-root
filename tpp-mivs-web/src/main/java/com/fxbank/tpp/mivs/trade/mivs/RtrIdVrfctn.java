package com.fxbank.tpp.mivs.trade.mivs;

import javax.annotation.Resource;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_321_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 手机号码联网核查应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-23 21:45:28
 */
@Service("MIVS_321_001_01")
public class RtrIdVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrIdVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_321_001_01 mivs321 = (MIVS_321_001_01) dto;
        myLog.info(logger, "收到人行手机号码联网核查应答报文,进行同步处理");
        byte[] b321 = SerializeUtil.serialize(mivs321);
        String channel = "321_" + mivs321.getRtrIdVrfctn().getOrgnlBizQry().getMsgId();   //TODO 拼接原报文三要素
        myLog.info(logger, "321报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b321);
        myLog.info(logger, "发布至redis成功");
        return mivs321;
    }

}