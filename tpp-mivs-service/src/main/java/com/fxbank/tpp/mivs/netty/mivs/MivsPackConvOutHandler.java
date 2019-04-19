package com.fxbank.tpp.mivs.netty.mivs;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.mivs.model.REQ_BASE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Description: 交行客户端请求组包
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:43
 */
public class MivsPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(MivsPackConvOutHandler.class);

	private MyLog myLog;

	public MivsPackConvOutHandler(MyLog myLog) {
		this.myLog = myLog;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		StringBuffer fixPack = new StringBuffer(reqBase.creaFixPack());
		//TODO 生成MAC
		fixPack.append("FFFFFFFFFFFFFFFF");
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
