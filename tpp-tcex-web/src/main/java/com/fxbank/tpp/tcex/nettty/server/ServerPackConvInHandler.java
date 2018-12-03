package com.fxbank.tpp.tcex.nettty.server;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.tcex.constant.TCEX;
import com.fxbank.tpp.tcex.utils.JAXBUtils;
import com.fxbank.tpp.tcex.utils.StringUtils;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

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
			String strMsg = (String) msg;
			ctx.fireChannelRead(strMsg);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.debug("接收到客户端请求");
	}

}
