package com.fxbank.tpp.bocm.nettty;

import java.util.concurrent.TimeUnit;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.tpp.bocm.controller.TradeDispatcherHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	public static final String CODING="UTF-8";

	/*
	@Autowired
	private BocmLengthEncoder bocmLengthEncoder;
	
	@Autowired
	private BocmPackConvOutHandler bocmPackConvOutHandler;
	
	@Autowired
	private BocmPackConvInHandler bocmPackConvInHandler;
	*/
	
	@Autowired
	private TradeDispatcherHandler tradeDispatcherHandler;
	
	@Autowired
	LogPool logPool;
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		// 解码器，根据长度从SOCKET连接中读取数据
		//p.addLast(new BocmLenghtDecoder(logPool));
		//设置读取报文超时时间
		p.addLast(new ReadTimeoutHandler(10000,TimeUnit.MILLISECONDS));
		// 编码器，将String转成buff
		// p.addLast(bocmLengthEncoder);
		// 应答处理器
		// p.addLast(bocmPackConvOutHandler);
		// 请求处理器s
		// p.addLast(bocmPackConvInHandler);
		// 交易分发处理器
		p.addLast(tradeDispatcherHandler);
	}

}
