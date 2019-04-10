package com.fxbank.tpp.bocm.netty.bocm;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncParam;
import com.fxbank.cip.base.netty.NettySyncSlot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BocmClient {

    private static Logger logger = LoggerFactory.getLogger(BocmClient.class);

    // 之后从redis取
    private String ip = "127.0.0.1";

    // 之后从redis取
    private Integer port = 6000;

    // 之后从redis取
    private Integer timeOut = 60;

    public <T> T comm(Object req) throws SysTradeExecuteException {
        MyLog myLog = new MyLog();
        NettySyncParam syncParam = new NettySyncParam(ip, port, timeOut);
        NettySyncSlot<T> slot = new NettySyncSlot<T>();
        BocmInitializer<T> initializer = new BocmInitializer<T>(myLog, slot, req);
        NettySyncClient<T> clientSync = new NettySyncClient<T>(myLog, syncParam, initializer, slot);
        T repData = clientSync.comm();
        return repData;
    }

}