package com.fxbank.tpp.bocm.nettty;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Component("serverPackConvInHandler")
@Sharable
public class ServerPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ServerPackConvInHandler.class);

	@Resource
	private LogPool logPool;

	@Resource
	private MyJedis myJedis;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog logUtil = logPool.get();
		try {
			/*
			ByteBuf bb = (ByteBuf) msg;
			byte[] reqByte = new byte[bb.readableBytes()];
			bb.readBytes(reqByte);
			String reqStr = new String(reqByte);
			logger.info("接收到客户端请求"+reqStr);
			*/
			logger.info("接收到客户端请求");
			ctx.writeAndFlush(msg);
		} finally {
			//ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.debug("接收到客户端请求");
	}

}
