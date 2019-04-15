package com.fxbank.tpp.bocm.nettty;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.bocm.dto.bocm.REQ_BASE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

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
			String fixPack = pack.substring(0,pack.length()-16);
			String mac = pack.substring(pack.length()-16);
			myLog.info(logger, "mac=[" + mac + "]");
			String txCode = pack.substring(0, 5);
			myLog.info(logger, "交易代码=[" + txCode + "]");
			REQ_BASE reqBase = null;
			Class<?> bocmClass = null;
			String className = "com.fxbank.tpp.bocm.dto.bocm."+"REQ_" + txCode;
			try {
				bocmClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				myLog.error(logger, "类文件[" + className + "]未定义",e);
				throw e;
			}
			reqBase = (REQ_BASE) bocmClass.newInstance();
			reqBase.chanFixPack(fixPack);
			reqBase.setTxCode(txCode);
			reqBase.setSourceType("JH");
			reqBase.setOthDate(reqBase.getHeader().gettTxnDat());
			reqBase.setOthTraceno(reqBase.getHeader().getsLogNo());
			ctx.fireChannelRead(reqBase);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.myLog.info(logger, "解析客户端请求异常", cause);
		ctx.close();
	}

}
