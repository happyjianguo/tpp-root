package com.fxbank.tpp.beps.trade.pmts;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.beps.dto.task.DATA_JSON;
import com.fxbank.tpp.beps.dto.task.REQ_TP35101A01;
import com.fxbank.tpp.beps.trade.task.TP35101A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @description: 协议付款方签约交易处理
 * @author     : 周勇沩
 * @Date       : 2020/2/21 18:06
 */
@Service("BEPS_351_001_01")
public class P35101 extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(P35101.class);

    @Resource
    private LogPool logPool;

    @Resource(name = "REQ_TP35101A01")
    private TP35101A tp35101A;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        //TODO 登记数据库 insert

        REQ_TP35101A01 taskReqDto = new REQ_TP35101A01();
        taskReqDto.setSysDate(dto.getSysDate());
        taskReqDto.setSysTime(dto.getSysTime());
        taskReqDto.setSysTraceno(dto.getSysTraceno());
        List<DATA_JSON> dataList = new ArrayList<DATA_JSON>();
        DATA_JSON dataJson = new DATA_JSON();
        //TODO 正常传递日期、流水
        dataJson.setName("key");
        dataJson.setValue("value");
        dataList.add(dataJson);
        taskReqDto.getReqBody().setData(dataList);
        return tp35101A.execute(taskReqDto);
    }

}