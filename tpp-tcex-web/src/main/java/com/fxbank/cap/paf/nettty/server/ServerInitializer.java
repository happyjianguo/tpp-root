package com.fxbank.cap.paf.nettty.server;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.nettty.SocketLenghtDecoder;
import com.fxbank.cap.paf.nettty.SocketLengthEncoder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;


@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private SslContext sslCtx;
	
	@Autowired
	SocketLengthEncoder socketLengthEncoder;
	
	@Autowired
	ServerPackConOutHandler serverPackConvOutHandler;
	
	@Autowired
	ServerPackConvInHandler serverPackConvInHandler;
	
	@Autowired
	TradeDispatcherHandler tradeDispatcherHandler;
	
	@Autowired
	LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	public ServerInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}

	public ServerInitializer() {
		this.sslCtx = null;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		if (sslCtx != null) {
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		// 解码器，根据长度从SOCKET连接中读取数据
		p.addLast(new SocketLenghtDecoder(logPool,myJedis));
		//设置读取报文超时时间
		p.addLast(new ReadTimeoutHandler(10000,TimeUnit.MILLISECONDS));
		// 编码器，将String转成buff
		p.addLast(socketLengthEncoder);
		// 应答处理器
		p.addLast(serverPackConvOutHandler);
		// 请求处理器
		p.addLast(serverPackConvInHandler);
		// 交易分发处理器
		p.addLast(tradeDispatcherHandler);
	}

	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
}
