package com.fxbank.tpp.bocm.nettty;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.model.REQ_BASE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Description: 交行来账应答组包
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:16:56
 */
public class BocmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvOutHandler.class);

	private MyLog myLog;

	public BocmPackConvOutHandler(MyLog myLog) {
		this.myLog = myLog;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		String fixPack = reqBase.creaFixPack();
		ctx.writeAndFlush(fixPack, promise);
	}

}
