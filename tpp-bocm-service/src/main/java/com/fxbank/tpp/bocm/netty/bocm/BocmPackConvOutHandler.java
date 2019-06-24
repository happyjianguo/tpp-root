package com.fxbank.tpp.bocm.netty.bocm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REQ_BASE;
import com.fxbank.tpp.bocm.service.IBocmSafeService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * @Description: 交行客户端请求组包
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:43
 */
public class BocmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvOutHandler.class);

	private MyLog myLog;
	
	private IBocmSafeService safeService;

	public BocmPackConvOutHandler(MyLog myLog, IBocmSafeService safeService) {
		this.myLog = myLog;
		this.safeService = safeService;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(reqBase,BocmClient.CODING));
		myLog.info(logger, "组包发送交行报文");	

		String mac = safeService.calcBocm(myLog, fixPack.toString());		
		myLog.info(logger, "调用加密平台生成Mac： 【"+mac+"】");	
		fixPack.append(mac);
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
