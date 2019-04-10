package com.fxbank.tpp.bocm.netty.bocm;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class BocmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvOutHandler.class);

	private MyLog myLog;

	public BocmPackConvOutHandler(MyLog myLog) {
		this.myLog = myLog;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		this.myLog.info(logger, "返回报文体=[" + msg.toString() + "]");
		ctx.writeAndFlush(msg.toString(), promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		this.myLog.error(logger, "PackConvertOutHandler process error!", cause);
		ctx.writeAndFlush("abc");
		ctx.close();
	}
}
