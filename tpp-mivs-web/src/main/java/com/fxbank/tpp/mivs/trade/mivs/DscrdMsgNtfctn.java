package com.fxbank.tpp.mivs.trade.mivs;

import javax.annotation.Resource;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.CCMS_911_001_02;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DscrdMsgNtfctn
 * @Description: 报文丢弃通知报文
 * @author Duzhenduo
 * @date 2019年4月25日 下午2:50:50
 * 
 */
@Service("CCMS_911_001_02")
public class DscrdMsgNtfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(DscrdMsgNtfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        CCMS_911_001_02 ccms911 = (CCMS_911_001_02) dto;
        myLog.info(logger, "收到人行报文丢弃通知报文,进行同步处理");
        byte[] b911 = SerializeUtil.serialize(ccms911);
        String msgType = ccms911.getDscrdMsgNtfctn().getDscrdInf().getMT().substring(5, 8);
        if(msgType.equals("320")){
            msgType = "321";
        }else if(msgType.equals("322")){
            msgType = "323";
        }else if(msgType.equals("324")){
            msgType = "325";
        }else if(msgType.equals("326")){
            msgType = "900";
        }else if(msgType.equals("333")){
            msgType = "900";
        }else if(msgType.equals("345")){
            msgType = "346";
        }else if(msgType.equals("347") || msgType.equals("348") || msgType.equals("349")){
            msgType = "900";
        }
        String channel = msgType + "_" + ccms911.getDscrdMsgNtfctn().getDscrdInf().getMsgId(); // TODO 拼接原报文三要素
        myLog.info(logger, "911报文同步通道编号=[" + channel + "]");
        super.jedisPublish(myLog,channel.getBytes(), b911);
        myLog.info(logger, "发布至redis成功");
        return ccms911;
    }

}