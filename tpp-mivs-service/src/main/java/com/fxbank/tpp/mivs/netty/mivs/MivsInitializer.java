package com.fxbank.tpp.mivs.netty.mivs;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncHandler;
import com.fxbank.cip.base.netty.NettySyncSlot;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description: 交行客户端通讯初始化
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:59:34
 */
public class MivsInitializer<T> extends ChannelInitializer<SocketChannel> {

    private MyLog myLog;
    private NettySyncSlot<T> slot;
    private Object reqData;
    private Class<T> clazz;

    public MivsInitializer(MyLog myLog, NettySyncSlot<T> slot, Object reqData, Class<T> clazz) {
        this.myLog = myLog;
        this.slot= slot;
        this.reqData = reqData;
        this.clazz = clazz;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new MivsLenghtDecoder<T>(this.myLog,this.slot));
        p.addLast(new MivsLengthEncoder(this.myLog));
        p.addLast(new MivsPackConvInHandler<T>(this.myLog,this.slot,clazz));
        p.addLast(new MivsPackConvOutHandler(this.myLog));
        p.addLast(new NettySyncHandler<T>(this.myLog,this.slot,this.reqData));
    }

}