package com.fxbank.tpp.bocm.nettty;

import java.util.Map;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

@Component("serverPackConOutHandler")
@Sharable
public class ServerPackConOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ServerPackConOutHandler.class);
	
	@Resource
	private LogPool logPool;
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		MyLog logUtil = logPool.get();
		Map<String, DataTransObject> dtoMap = (Map<String, DataTransObject>) msg;
		DataTransObject dto = dtoMap.get("repDto");
		
		String pack="abc";

		logUtil.info(logger, "返回报文体=[" + pack.toString() + "]");
		ctx.writeAndFlush(pack.toString(), promise);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		MyLog logUtil = logPool.get();
		logUtil.error(logger, "PackConvertOutHandler process error!", cause);
		ctx.writeAndFlush("abc");
		ctx.close();
	}
}
