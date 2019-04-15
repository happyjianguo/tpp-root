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

/**
 * @Description: netty服务端处理初始化
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:16:32
 */
@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	public static final String CODING="UTF-8";

	@Autowired
	private BocmLengthEncoder bocmLengthEncoder;
	
	@Autowired
	private BocmPackConvOutHandler bocmPackConvOutHandler;
	
	@Autowired
	private BocmPackConvInHandler bocmPackConvInHandler;
	
	@Autowired
	private TradeDispatcherHandler tradeDispatcherHandler;
	
	@Autowired
	private LogPool logPool;
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new BocmLenghtDecoder(logPool));
		p.addLast(new ReadTimeoutHandler(10000,TimeUnit.MILLISECONDS));
		p.addLast(this.bocmLengthEncoder);
		p.addLast(this.bocmPackConvOutHandler);
		p.addLast(this.bocmPackConvInHandler);
		p.addLast(this.tradeDispatcherHandler);
	}

}
