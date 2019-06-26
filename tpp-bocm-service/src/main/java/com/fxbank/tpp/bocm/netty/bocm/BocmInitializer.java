package com.fxbank.tpp.bocm.netty.bocm;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncHandler;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description: 交行客户端通讯初始化
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:59:34
 */
public class BocmInitializer<T> extends ChannelInitializer<SocketChannel> {

    private MyLog myLog;
    private Object reqData;
    private Class<T> clazz;
    private IBocmSafeService safeService;

    public BocmInitializer(MyLog myLog, Object reqData, Class<T> clazz, IBocmSafeService safeService) {
        this.myLog = myLog;
        this.reqData = reqData;
        this.clazz = clazz;
        this.safeService = safeService;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new BocmLenghtDecoder<T>(this.myLog));
        p.addLast(new BocmLengthEncoder(this.myLog));
        p.addLast(new BocmPackConvInHandler<T>(this.myLog,clazz,safeService));
        p.addLast(new BocmPackConvOutHandler(this.myLog,safeService));
        p.addLast(new NettySyncHandler<T>(this.myLog,this.reqData));
    }

}