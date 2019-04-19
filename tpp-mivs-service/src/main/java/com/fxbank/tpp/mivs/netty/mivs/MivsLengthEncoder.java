package com.fxbank.tpp.mivs.netty.mivs;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description: 交行客户端发送程序
 * @Author: 周勇沩
 * @Date: 2019-04-15 12:00:05
 */
public class MivsLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(MivsLengthEncoder.class);

	private MyLog myLog;

	public MivsLengthEncoder(MyLog myLog) {
		this.myLog = myLog;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		Integer msgStrLen = msgStr.getBytes(MivsClient.CODING).length;
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%08d", msgStrLen));
		sb.append(msgStr);
		String reqPack = sb.toString();
		this.myLog.info(logger, "发送请求报文=[" + reqPack);
		out.writeBytes(reqPack.getBytes(MivsClient.CODING));
	}

}
