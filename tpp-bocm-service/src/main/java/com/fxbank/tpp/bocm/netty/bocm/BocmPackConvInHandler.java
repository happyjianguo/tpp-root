package com.fxbank.tpp.bocm.netty.bocm;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.dto.bocm.Req_1001;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class BocmPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvInHandler.class);

	private LogPool logPool;

	private MyLog myLog;

	public BocmPackConvInHandler(MyLog myLog){
		this.myLog = myLog;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			/*
			ByteBuf bb = (ByteBuf) msg;
			byte[] reqByte = new byte[bb.readableBytes()];
			bb.readBytes(reqByte);
			String reqStr = new String(reqByte);
			logger.info("接收到客户端请求"+reqStr);
			*/
			String data = (String) msg;
			this.myLog.info(logger,"接收到客户端请求"+data);
			Req_1001 req = new Req_1001();
			req.setTxcode(data.substring(0, 6));
			req.setData(data.substring(7));
			ctx.fireChannelRead(req);
		} finally {
			//ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.myLog.debug(logger,"接收到客户端请求");
	}

}
