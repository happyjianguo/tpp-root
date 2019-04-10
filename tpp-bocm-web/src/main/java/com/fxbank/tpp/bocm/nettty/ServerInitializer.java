package com.fxbank.tpp.bocm.nettty;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.tpp.bocm.controller.TradeDispatcherHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private SocketLengthEncoder socketLengthEncoder;

	@Autowired
	private ServerPackConOutHandler serverPackConvOutHandler;

	@Autowired
	private ServerPackConvInHandler serverPackConvInHandler;

	@Autowired
	private TradeDispatcherHandler tradeDispatcherHandler;

	@Autowired
	private LogPool logPool;

	@Resource
	private MyJedis myJedis;

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		// 解码器，根据长度从SOCKET连接中读取数据
		//p.addLast(new SocketLenghtDecoder(logPool, myJedis));
		// 设置读取报文超时时间
		//p.addLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
		// 编码器，将String转成buff
		//p.addLast(socketLengthEncoder);
		// 应答处理器
		//p.addLast(serverPackConvOutHandler);
		// 请求处理器
		p.addLast(serverPackConvInHandler);
		// 交易分发处理器
		//p.addLast(tradeDispatcherHandler);
	}

}
