package com.fxbank.tpp.mivs.trade.mivs;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_346_001_01;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 企业信息联网核查查业务受理时间查询 人行应答
 * @Author: 王鹏
 * @Date: 2019/5/10 10:03
 */
@Service("MIVS_346_001_01")
public class RtrSysSts extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrTxPmtVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_346_001_01 mivs346 = (MIVS_346_001_01) dto;
        myLog.info(logger, "企业信息联网核查查业务受理时间查询应答报文,进行同步处理");
        byte[] b346 = SerializeUtil.serialize(mivs346);
        String channel = "346_" + mivs346.getRtrSysSts().getOrgnlQryInf().getMsgId();   //TODO 拼接原报文三要素
        myLog.info(logger, "346报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b346);
        myLog.info(logger, "发布至redis成功");
        return mivs346;
    }

}