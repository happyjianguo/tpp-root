package com.fxbank.tpp.bocm.nettty;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.dto.bocm.REQ_10000;
import com.fxbank.tpp.bocm.dto.bocm.REQ_BASE;
import com.fxbank.tpp.bocm.dto.bocm.REQ_HEADER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 交行来账请求拆包
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:17:31
 */
@Component("bocmPackConvInHandler")
@Sharable
public class BocmPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvInHandler.class);

	@Resource
	private LogPool logPool;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		try {
			StringBuffer pack = new StringBuffer((String) msg);
			String fixPack = pack.substring(0, pack.length() - 16);
			String mac = pack.substring(pack.length() - 16);
			myLog.info(logger, "mac=[" + mac + "]");
			// 校验MAC TODO
			String txCode = "REQ_" + pack.substring(0, 5);
			myLog.info(logger, "交易代码=[" + txCode + "]");
			REQ_BASE reqBase = null;
			Class<?> bocmClass = null;
			String className = "com.fxbank.tpp.bocm.dto.bocm." + txCode;
			try {
				bocmClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				myLog.error(logger, "类文件[" + className + "]未定义", e);
				throw e;
			}
			
			
			reqBase = (REQ_BASE) bocmClass.newInstance();			
			reqBase = (REQ_BASE)new FixedUtil(fixPack).toBean(reqBase.getClass());			
			reqBase.setTxCode(txCode);
			reqBase.setSourceType("BU");
			ctx.fireChannelRead(reqBase);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = logPool.get();
		myLog.info(logger, "解析客户端请求异常", cause);
		ctx.close();
	}

}
