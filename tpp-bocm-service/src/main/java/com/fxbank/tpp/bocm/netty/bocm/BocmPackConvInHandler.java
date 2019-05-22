package com.fxbank.tpp.bocm.netty.bocm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncSlot;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.model.REP_BASE;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 交行客户端应答拆包
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:22
 */
public class BocmPackConvInHandler<T> extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvInHandler.class);

	private MyLog myLog;

	private Class<T> clazz;

	public BocmPackConvInHandler(MyLog myLog, Class<T> clazz) {
		this.myLog = myLog;
		this.clazz = clazz;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			StringBuffer pack = new StringBuffer((String) msg);
			String mac = pack.substring(pack.length() - 16);
			this.myLog.info(logger, "mac=[" + mac + "]");
			// TODO 校验MAC
			
			String fixPack = pack.substring(0, pack.length() - 16);
			REP_BASE repBase = (REP_BASE) this.clazz.newInstance();			
			repBase = (REP_BASE)new FixedUtil(fixPack).toBean(repBase.getClass());		
			ctx.fireChannelRead(repBase);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<NettySyncSlot<T>> slotAttr = ctx.channel().attr(AttributeKey.valueOf(NettySyncClient.SLOTKEY));
		NettySyncSlot<T> slot = slotAttr.get();
		slot.setResponse(null);
		this.myLog.info(logger, "解析服务端应答异常", cause);
	}

}
