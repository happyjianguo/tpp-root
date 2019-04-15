package com.fxbank.tpp.bocm.netty.bocm;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncHandler;
import com.fxbank.cip.base.netty.NettySyncSlot;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class BocmInitializer<T> extends ChannelInitializer<SocketChannel> {

    private MyLog myLog;
    private NettySyncSlot<T> slot;
    private Object reqData;
    private Class<T> clazz;

    public BocmInitializer(MyLog myLog, NettySyncSlot<T> slot, Object reqData, Class<T> clazz) {
        this.myLog = myLog;
        this.slot= slot;
        this.reqData = reqData;
        this.clazz = clazz;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new BocmLenghtDecoder<T>(this.myLog,this.slot));
        p.addLast(new BocmLengthEncoder(this.myLog));
        p.addLast(new BocmPackConvInHandler<T>(this.myLog,this.slot,clazz));
        p.addLast(new BocmPackConvOutHandler(this.myLog));
        p.addLast(new NettySyncHandler<T>(this.myLog,this.slot,this.reqData));
    }

}